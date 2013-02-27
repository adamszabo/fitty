package com.acme.fitness.products.simple;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.MembershipType;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.ProductImage;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.products.MembershipService;
import com.acme.fitness.products.MembershipTypeService;
import com.acme.fitness.products.ProductImageService;
import com.acme.fitness.products.ProductService;
import com.acme.fitness.products.TrainingService;
import com.acme.fitness.products.TrainingTypeService;

@Service
public class SimpleGeneralProductsService implements GeneralProductsService {

	private ProductService productService;
	private MembershipService membershipService;
	private TrainingService trainingService;
	private MembershipTypeService membershipTypeService;
	private ProductImageService imageService;
	private TrainingTypeService trainingTypeService;
	
	@Autowired
	public SimpleGeneralProductsService(ProductService productService, MembershipService membershipService, TrainingService trainingService, 
			MembershipTypeService membershipTypeService, ProductImageService imageService, TrainingTypeService trainingTypeService){
		this.productService=productService;
		this.membershipService=membershipService;
		this.trainingService=trainingService;
		this.membershipTypeService=membershipTypeService;
		this.imageService=imageService;
		this.trainingTypeService = trainingTypeService;
	}

	@Override
	public Product addProduct(String name, String details, double price, String manufacturer, Date creation, ProductImage productImage) {
		return productService.addProduct(name, details, price, manufacturer, creation, productImage);
	}

	@Override
	public void deleteProduct(Product product) {
		productService.deleteProduct(product);
	}

	@Override
	public void updateProduct(Product product) {
		productService.updateProduct(product);
	}

	@Override
	public List<Product> getAllProduct() {
		return productService.getAllProducts();
	}

	@Override
	public Product getProductById(long id) throws FitnessDaoException {
		return productService.getProductById(id);
	}
	
	@Override
	public List<Product> getProductsByName(String name) {
		return productService.getProductsByName(name);
	}

	@Override
	public List<Product> getProductsByManufacturer(String manufacturer) {
		return productService.getProductsByManufacturer(manufacturer);
	}

	@Override
	public List<Product> getProductsByPriceInterval(double fromPrice, double toPrice) {
		return productService.getProductsByPriceInterval(fromPrice, toPrice);
	}

	@Override
	public void goOnHoliday(User trainer, Date date) {
		trainingService.goOnHoliday(trainer, date);
	}

	@Override
	public Training newTraining(User trainer, User client, Date date) throws FitnessDaoException {
		return trainingService.newTraining(trainer, client, date);
	}

	@Override
	public void deleteTraining(Training training) {
		trainingService.deleteTraining(training);
	}

	@Override
	public void updateTraining(Training training) {
		trainingService.updateTraining(training);
	}

	@Override
	public void recordTrainingResults(Training training, int burnedCalories, String review) {
		trainingService.recordTrainingResults(training, burnedCalories, review);
	}

	@Override
	public List<Training> getTrainingsByTrainer(User trainer) {
		return trainingService.getTrainingsByTrainer(trainer);
	}

	@Override
	public List<Training> getTrainingsByClient(User client) {
		return trainingService.getTrainingsByClient(client);
	}

	@Override
	public List<Training> getTrainingsByBasket(Basket basket) {
		return trainingService.getTrainingsByBasket(basket);
	}
	
	@Override
	public boolean isDateReserved(User trainer, Date date) {
		return trainingService.isDateReserved(trainer, date);
	}
	
	@Override
	public List<Training> getTrainingsOnWeekByTrainer(User trainer, Date monday) {
		return trainingService.getTrainingsOnWeekByTrainer(trainer, monday);
	}
	
	@Override
	public Membership newMemberShip(boolean isIntervally, String type, int maxEntries, Date startDate, Date expireDate, double price) {
		return membershipService.newMemberShip(isIntervally, type, maxEntries, startDate, expireDate, price);
	}

	@Override
	public void deleteMembership(Membership membership) {
		membershipService.deleteMembership(membership);
	}

	@Override
	public void updateMembership(Membership membership) {
		membershipService.updateMembership(membership);
	}

	@Override
	public boolean isValid(Membership membership, Date date) {
		return membershipService.isValid(membership, date);
	}

	@Override
	public Membership getMembershipById(long id) throws FitnessDaoException {
		return membershipService.getMembershipById(id);
	}

	@Override
	public List<Membership> getMembershipByBasket(Basket basket) {
		return membershipService.getMembershipByBasket(basket);
	}

	@Override
	public List<Membership> getMembershipByUser(User user) {
		return membershipService.getMembershipByUser(user);
	}

	@Override
	public void increaseClientEntries(Membership membership) {
		membershipService.increaseClientEntries(membership);
	}

	@Override
	public MembershipType newMembershipType(String type, boolean isIntervally, 
			int maxNumberOfEntries, int expireDateInDays, double price) {
		return membershipTypeService.newMembershipType(type, isIntervally, maxNumberOfEntries, expireDateInDays, price);
	}
	
	@Override
	public void saveMembershipType(MembershipType membershipType) {
		membershipTypeService.saveMembershipType(membershipType);
	}

	@Override
	public void deleteMembershipType(MembershipType membershipType) {
		membershipTypeService.deleteMembershipType(membershipType);
	}

	@Override
	public void updateMembershipType(MembershipType membershipType) {
		membershipTypeService.updateMembershipType(membershipType);
	}

	@Override
	public MembershipType getMembershipTypeById(long id)
			throws FitnessDaoException {
		return membershipTypeService.getMembershipTypeById(id);
	}

	@Override
	public List<MembershipType> getAllMembershipTypes() {
		return membershipTypeService.getAllMembershipTypes();
	}

	@Override
	public List<Membership> getValidMembershipsByUser(User user, Date date) {
		return membershipService.getValidMembershipsByUser(user, date);
	}

	@Override
	public List<ProductImage> getAllProductImage() {
		return imageService.getAllProductImages();
	}

	@Override
	public ProductImage getProductImageById(long id) throws FitnessDaoException {
		return imageService.getProductImageById(id);
	}

	@Override
	public void goOnHolidayToAllDay(User trainer, Date date) {
		trainingService.goOnHolidayToAllDay(trainer, date);
	}
	
	@Override
	public TrainingType newTrainingType(User trainer, String detail, double price) {
		return trainingTypeService.newTrainingType(trainer, detail, price);
	}
	
	@Override
	public void saveTrainingType(TrainingType trainingType) {
		trainingTypeService.saveTrainingType(trainingType);
	}
	
	@Override
	public List<TrainingType> getAllTrainingTypes() {
		return trainingTypeService.getAllTrainingTypes();
	}

	@Override
	public TrainingType getTrainingTypeById(long id) throws FitnessDaoException {
		return trainingTypeService.getTrainingTypeById(id);
	}
	
	@Override
	public TrainingType getTrainingTypeByTrainer(User trainer) throws FitnessDaoException {
		return trainingTypeService.getTrainingTypeByTrainer(trainer);
	}

	@Override
	public void updateTrainingType(TrainingType trainingType) {
		trainingTypeService.updateTrainingType(trainingType);
	}

	@Override
	public void deleteTrainingType(TrainingType trainingType) {
		trainingTypeService.deleteTrainingType(trainingType);
	}
	
	@Override
	public void updateProductAndSaveImage(Product product, ProductImage image) {
		productService.updateProductAndSaveImage(product, image);
	}
}
