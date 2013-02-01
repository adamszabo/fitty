package com.acme.fitness.webmvc.basket;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.fitness.domain.exceptions.FitnessDaoException;
import com.acme.fitness.domain.orders.Basket;
import com.acme.fitness.domain.products.Membership;
import com.acme.fitness.domain.products.MembershipType;
import com.acme.fitness.products.GeneralProductsService;
import com.acme.fitness.webmvc.cookie.CookieManager;
import com.acme.fitness.webmvc.user.UserManager;

@Service
public class MembershipManager extends ItemManager {

	@Autowired
	private GeneralProductsService gps;

	@Override
	public void loadBasketWithItem(Map<String, String> item, Basket basket) {
		try {
			Membership membership = parseMembership(item);
			basket.addMembership(membership);
			membership.setBasket(basket);
		} catch (FitnessDaoException e) {
			item = new HashMap<String, String>();
			e.printStackTrace();
		}
	}

	public void addNewMembership(long membershipId, HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		Map<String, String> memeberships = new HashMap<String, String>();
		addMembershipToMapById(membershipId, request, memeberships);
		addMapToCookie(response, request, mapper, memeberships);
	}
	
	public void addNewMembershipFromAnonymousBasket(HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper) {
		Map<String, String> memberships = readFromCookies(request, mapper, "membershipsInBasket");
		addMapToCookie(response, request, mapper, memberships);
	}
	
	public void removeMembership(long id, HttpServletResponse response, HttpServletRequest request) {
		String userName = new UserManager().getLoggedInUserName();
		if(userName.equals("anonymousUser")) {
			new CookieManager().removeTheCookieByName(request, response, "membershipsInBasket");
		} else {
			Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, new ObjectMapper());
			Map<String, Map<String, String>> basket = loadBasketByUserName(users, userName);
			if (users.containsKey(userName)) {
				basket = users.get(userName);
				basket.remove("membershipsInBasket");
			}
			if(basket.size() == 0) {
				users.remove(userName);
			}
			writeMapToCookie(response, new ObjectMapper(), "userNames", users);
		}
	}

	private void addMapToCookie(HttpServletResponse response, HttpServletRequest request, ObjectMapper mapper, Map<String, String> memeberships) {
		String userName = new UserManager().getLoggedInUserName();
		if (userName.equals("anonymousUser")) {
			writeMapToCookie(response, mapper, "membershipsInBasket", memeberships);
		} else {
			Map<String, Map<String, Map<String, String>>> users = loadUserNamesCookieValue(request, mapper);
			Map<String, Map<String, String>> basket = new HashMap<String, Map<String, String>>();
			if (users.containsKey(userName)) {
				basket = users.get(userName);
			}
			basket.put("membershipsInBasket", memeberships);
			users.put(userName, basket);
			writeMapToCookie(response, mapper, "userNames", users);
		}
	}

	private void addMembershipToMapById(long membershipId, HttpServletRequest request, Map<String, String> memeberships) {
		if (isMembershipTypeInterval(membershipId)) {
			memeberships.put(Long.toString(membershipId), validateDatePicker(request.getParameter("datepicker")));
		} else {
			memeberships.put(Long.toString(membershipId), null);
		}
	}

	private String validateDatePicker(String datePicker) {
		if(datePicker == null || datePicker.equals("")) {
			datePicker = getTodayInString();
		}
		return datePicker;
	}

	private String getTodayInString() {
		String datePicker;
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.YEAR) + "-");
		//Month is 0-based.
		sb.append((calendar.get(Calendar.MONTH)+1) + "-");
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));
		datePicker = sb.toString();
		return datePicker;
	}
	
	private boolean isMembershipTypeInterval(long membershipId) {
		boolean isInterval = false;
		try {
			MembershipType type = gps.getMembershipTypeById(membershipId);
			if (type.getMaxNumberOfEntries() == 0) {
				isInterval = true;
			}
		} catch (FitnessDaoException e) {
			e.printStackTrace();
		}
		return isInterval;
	}
	
	private Membership parseMembership(Map<String, String> memberships) throws FitnessDaoException {
		Long membershipTypeId = getMembershipId(memberships);
		MembershipType membershipType = gps.getMembershipTypeById(membershipTypeId);
		return setMembershipDependentOnMembershipType(memberships, membershipType);
	}

	private Membership setMembershipDependentOnMembershipType(Map<String, String> memberships, MembershipType membershipType) {
		Membership membership = gps.newMemberShip(membershipType.getIsIntervally(), membershipType.getDetail(), 0, null, null, membershipType.getPrice());
		if (membershipType.getIsIntervally()) {
			setMembershipWithIntervallySpecificDatas(getStartDate(memberships, membershipType), membership, membershipType);
		} else {
			membership.setMaxNumberOfEntries(membershipType.getMaxNumberOfEntries());
		}
		return membership;
	}

	private Long getMembershipId(Map<String, String> memberships) {
		Long membershipId = null;
		for (String s : memberships.keySet()) {
			membershipId = Long.parseLong(s);
		}
		return membershipId;
	}

	private Date getStartDate(Map<String, String> memberships, MembershipType membershipType) {
		return parseStringIntoDate(memberships.get(new Long(membershipType.getId()).toString()));
	}

	private Date parseStringIntoDate(String string) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = (Date) formatter.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private void setMembershipWithIntervallySpecificDatas(Date startDate, Membership membership, MembershipType membershipType) {
		membership.setStartDate(startDate);
		membership.setExpireDate(new Date(startDate.getTime() + (long) membershipType.getExpireDateInDays() * 1000 * 60 * 60 * 24));
	}
}
