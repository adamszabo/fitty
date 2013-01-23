package com.acme.fitness.products.simple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.dao.products.MembershipTypeDao;
import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.products.MembershipType;
import com.acme.fitness.products.MembershipTypeService;

@Service
public class SimpleMembershipTypeService implements MembershipTypeService {

	private MembershipTypeDao membershipTypeDao;

	@Autowired
	public SimpleMembershipTypeService(MembershipTypeDao membershipTypeDao) {
		this.membershipTypeDao = membershipTypeDao;
	}

	@Override
	public MembershipType newMembershipType(String type,
			int maxNumberOfEntries, int expireDateInDays, double price) {
		return new MembershipType(type, maxNumberOfEntries, expireDateInDays,
				price);
	}

	@Override
	public void saveMembershipType(MembershipType membershipType) {
		membershipTypeDao.save(membershipType);
	}

	@Override
	public void deleteMembershipType(MembershipType membershipType) {
		membershipTypeDao.delete(membershipType);
	}

	@Override
	public void updateMembershipType(MembershipType membershipType) {
		membershipTypeDao.update(membershipType);

	}

	@Override
	public MembershipType getMembershipTypeById(long id)
			throws FitnessDaoException {
		return membershipTypeDao.getMembershipTypeById(id);
	}

	@Override
	public List<MembershipType> getAllMembershipTypes() {
		return membershipTypeDao.getAllMembershipTypes();
	}

}
