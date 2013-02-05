package com.acme.fitness.products.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.products.MembershipDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.users.User;
import com.acme.fitness.products.MembershipService;

@Service
public class SimpleMembershipService implements MembershipService {
	private static final long ONE_SEC_IN_MILLIS = 1000;
	private final static long CONST_23_HOURS_IN_MILLIS=1000*60*60*23;
	
	private MembershipDao membershipDao;

	@Autowired
	public SimpleMembershipService(MembershipDao membershipDao) {
		this.membershipDao = membershipDao;
	}

	@Override
	public Membership newMemberShip(boolean isIntervally, String type, int maxEntries, Date startDate, Date expireDate, double price) {
		Membership membership = new Membership(isIntervally, type, 0, maxEntries, startDate, expireDate, price, null);
		return membership;
	}

	@Override
	public Membership saveMemberShip(Basket basket, Membership membership) {
		membership.setBasket(basket);
		membershipDao.save(membership);
		return membership;
	}

	@Override
	public Membership saveNewMemberShip(boolean isIntervally, Basket basket, String type, int maxEntries, Date startDate, Date expireDate, double price) {
		Membership membership = new Membership(isIntervally, type, 0, maxEntries, startDate, expireDate, price, basket);
		membershipDao.save(membership);
		return membership;
	}

	@Override
	public void deleteMembership(Membership membership) {
		membershipDao.delete(membership);
	}

	@Override
	public void updateMembership(Membership membership) {
		membershipDao.update(membership);
	}

	@Override
	public boolean isValid(Membership membership, Date date) {
		boolean result = false;

		if (isDelivered(membership)) {
			result = validateByType(membership, date);
		}

		return result;
	}

	private boolean validateByType(Membership membership, Date date) {
		boolean result = false;
		if (membership.getIsIntervally()) {
			result = isTodayBetweenStartAndEndDates(membership, date);
		} else {
			result = isActualEntriesLessThanMaxEntires(membership);
		}
		return result;
	}

	private boolean isDelivered(Membership membership) {
		return membership.getBasket().isDelivered();
	}

	private boolean isActualEntriesLessThanMaxEntires(Membership membership) {
		return membership.getNumberOfEntries() < membership.getMaxNumberOfEntries();
	}
	
	//valid date between startDate before 1 sec and expireDate's 23:00 
	boolean isTodayBetweenStartAndEndDates(Membership membership, Date today) {
		Date startDate = new Date(membership.getStartDate().getTime() - ONE_SEC_IN_MILLIS);
		Date expireDate = new Date(membership.getExpireDate().getTime() + CONST_23_HOURS_IN_MILLIS);
		return expireDate.after(today) && startDate.before(today);
	}

	@Override
	public Membership getMembershipById(long id) throws FitnessDaoException {
		return membershipDao.getMembershipById(id);
	}

	@Override
	public List<Membership> getMembershipByBasket(Basket basket) {
		return membershipDao.getMembershipsByOrder(basket);
	}

	@Override
	public List<Membership> getMembershipByUser(User user) {
		return membershipDao.getMembershipsByUser(user);
	}

	@Override
	public void increaseClientEntries(Membership membership) {
		membership.setNumberOfEntries(membership.getNumberOfEntries() + 1);
		membershipDao.update(membership);
	}

	@Override
	public List<Membership> getValidMembershipsByUser(User user, Date date) {
		List<Membership> memberships = membershipDao.getMembershipsByUser(user);
		List<Membership> validMemberships = new ArrayList<Membership>();

		for (Membership membership : memberships) {
			if (isValid(membership, date)) {
				validMemberships.add(membership);
			}
		}

		return validMemberships;
	}

}
