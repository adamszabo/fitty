package com.acme.fitness.products;

import java.util.Date;
import java.util.List;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.MembershipType;
import com.acme.fitness.domain.products.Product;
import com.acme.fitness.domain.products.ProductImage;
import com.acme.fitness.domain.products.Training;
import com.acme.fitness.domain.products.TrainingType;
import com.acme.fitness.domain.users.User;

public interface GeneralProductsService {
	Product addProduct(String name, String details, double price, String manufacturer, Date creation, ProductImage productImage);
	void deleteProduct(Product product);
	void updateProduct(Product product);
	List<Product> getAllProduct();
	List<Product> getProductsByName(String name);
	List<Product> getProductsByManufacturer(String manufacturer);
	List<Product> getProductsByPriceInterval(double fromPrice, double toPrice);
	
	void goOnHoliday(User trainer, Date date);
	void goOnHolidayToAllDay(User trainer, Date date);
	Training newTraining(User trainer, User client, Date date) throws FitnessDaoException;
	void deleteTraining(Training training);
	void updateTraining(Training training);
	void recordTrainingResults(Training training, int burnedCalories, String review);
	List<Training> getTrainingsByTrainer(User trainer);
	List<Training> getTrainingsByClient(User client);
	List<Training> getTrainingsByBasket(Basket basket);
	boolean isDateReserved(User trainer, Date date);
	
	Membership newMemberShip(boolean isIntervally, String type, int maxEntries, Date startDate, Date expireDate, double price);
	void deleteMembership(Membership membership);
	void updateMembership(Membership membership);
	boolean isValid(Membership membership, Date date);
	Membership getMembershipById(long id) throws FitnessDaoException;
	List<Membership> getMembershipByBasket(Basket basket);
	List<Membership> getMembershipByUser(User user);
	List<Membership> getValidMembershipsByUser(User user, Date date);
	void increaseClientEntries(Membership membership);
	Product getProductById(long id) throws FitnessDaoException;
	
	MembershipType newMembershipType(String type, boolean isIntervally, int maxNumberOfEntries, int expireDateInDays, double price);
	void deleteMembershipType(MembershipType membershipType);
	void updateMembershipType(MembershipType membershipType);
	MembershipType getMembershipTypeById(long id) throws FitnessDaoException;
	List<MembershipType> getAllMembershipTypes();
	void saveMembershipType(MembershipType membershipType);
	
	List<ProductImage> getAllProductImage();
	ProductImage getProductImageById(long id) throws FitnessDaoException;
	void updateProductAndSaveImage(Product product, ProductImage image);
	
	List<Training> getTrainingsOnWeekByTrainer(User trainer, Date monday);
	
	TrainingType newTrainingType(User trainer, String detail, double price);
	void saveTrainingType(TrainingType trainingType);
	void updateTrainingType(TrainingType trainingType);
	void deleteTrainingType(TrainingType trainingType);
	List<TrainingType> getAllTrainingTypes();
	TrainingType getTrainingTypeById(long id) throws FitnessDaoException;
	TrainingType getTrainingTypeByTrainer(User trainer) throws FitnessDaoException;
}
