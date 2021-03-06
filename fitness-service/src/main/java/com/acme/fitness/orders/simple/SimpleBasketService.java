package com.acme.fitness.orders.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.orders.BasketDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.exceptions.StoreQuantityException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.orders.OrderItem;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.orders.BasketService;
import com.acme.fitness.orders.OrderItemService;
import com.acme.fitness.orders.StoreService;
import com.acme.fitness.products.MembershipService;
import com.acme.fitness.products.TrainingService;

@Service
public class SimpleBasketService implements BasketService {

	private BasketDao basketDao;
	private MembershipService membershipService;
	private TrainingService trainingService;
	private OrderItemService orderItemService;
	private StoreService storeService;

	private Logger logger = LoggerFactory.getLogger(SimpleBasketService.class);

	@Autowired
	public SimpleBasketService(BasketDao basketDao, MembershipService membershipService, TrainingService trainingService, OrderItemService orderItemService,
			StoreService storeService) {
		this.basketDao = basketDao;
		this.membershipService = membershipService;
		this.trainingService = trainingService;
		this.orderItemService = orderItemService;
		this.storeService = storeService;
	}

	@Override
	public Basket newBasket(User client) {
		Basket basket = new Basket(false, client);
		return basket;
	}

	@Override
	public void deleteBasket(Basket basket) {
		basketDao.delete(basket);
	}

	@Override
	public void updateBasket(Basket basket) {
		basketDao.update(basket);
	}

	@Override
	public void addMembershipToBasket(Basket basket, Membership membership) {
		basket.addMembership(membership);
	}

	@Override
	public void addTrainingToBasket(Basket basket, Training training) {
		basket.addTraining(training);
	}

	@Override
	public void addOrderItemToBasket(Basket basket, OrderItem orderItem) {
		boolean isProductExists = false;
		for (OrderItem oi : basket.getOrderItems()) {
			if (oi.getProduct().equals(orderItem.getProduct())) {
				isProductExists = true;
				oi.setQuantity(oi.getQuantity() + orderItem.getQuantity());
			}
		}
		if (!isProductExists) {
			basket.addOrderItem(orderItem);
			orderItem.setBasket(basket);
		}
	}

	@Override
	public List<Membership> getMemberships(Basket basket) {
		return basket.getMemberships();
	}

	@Override
	public List<Training> getTrainings(Basket basket) {
		return basket.getTrainings();
	}

	@Override
	public List<OrderItem> getOrderItems(Basket basket) {
		return basket.getOrderItems();
	}

	@Override
	public void checkOutBasket(Basket basket) throws StoreQuantityException, TrainingDateReservedException {
		basket.setCreationDate(new Date());
		basketDao.save(basket);
		saveMemberships(basket);
		saveTrainings(basket);
		saveProducts(basket);
		logger.info("Basket checked out with id: " + basket.getId());
	}

	@Override
	public void deliver(Basket basket) {
		basket.setDelivered(true);
		basketDao.update(basket);
	}

	@Override
	public List<Basket> getBasketsByUser(User user) {
		List<Basket> baskets=basketDao.getBasketsByUser(user);
		setBasketsMembershipsAndTrainings(baskets);
		return baskets;
	}

	@Override
	public Basket getBasketById(long id) throws FitnessDaoException {
		return basketDao.getBasketById(id);
	}

	@Override
	public List<Basket> getBasketsByUserAndDeliveredStatus(User user, boolean isDelviered) {
		List<Basket> baskets=basketDao.getBasketsByUserAndDeliveredStatus(user, isDelviered);
		setBasketsMembershipsAndTrainings(baskets);
		return baskets;
	}

	private void setBasketsMembershipsAndTrainings(List<Basket> baskets) {
		for(Basket basket : baskets){
			List<Membership> memberships=membershipService.getMembershipByBasket(basket);
			List<Training> trainings=trainingService.getTrainingsByBasket(basket);
			
			basket.setMemberships(memberships);
			basket.setTrainings(trainings);
		}
	}

	private void saveProducts(Basket basket) throws StoreQuantityException {
		List<Product> missingProducts = new ArrayList<Product>();
		for (OrderItem o : basket.getOrderItems()) {
			try {
				takeOutProduct(basket, missingProducts, o);
			} catch (FitnessDaoException e) {
				logger.info(e.getMessage());
				missingProducts.add(o.getProduct());
			}
		}
		if (missingProducts.size() > 0) {
			throw new StoreQuantityException(missingProducts);
		}
	}

	private void takeOutProduct(Basket basket, List<Product> missingProducts, OrderItem o) throws FitnessDaoException {
		Product product = o.getProduct();
		if (storeService.takeOutProduct(product, o.getQuantity())) {
			orderItemService.updateOrderItem(o);
			logger.info("Product has taken with id: " + product.getId() + " name: " + product.getName());
		} else {
			missingProducts.add(o.getProduct());
			logger.info("Product misses with id: " + product.getId() + " name: " + product.getName());
		}
	}

	private void saveTrainings(Basket basket) throws TrainingDateReservedException {
		List<Training> reservedTrainings = new ArrayList<Training>();
		for (Training t : basket.getTrainings()) {
			if (!trainingService.isDateReserved(t.getTrainer(), t.getDate())) {
				trainingService.saveTraining(basket, t);
			} else {
				reservedTrainings.add(t);
			}
		}
		if (reservedTrainings.size() > 0) {
			throw new TrainingDateReservedException(reservedTrainings);
		}
	}

	private void saveMemberships(Basket basket) {
		for (Membership m : basket.getMemberships()) {
			membershipService.saveMemberShip(basket, m);
		}
	}
}
