package com.acme.fitness.webmvc.calendar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchStatus;
import com.google.gdata.data.batch.BatchUtils;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.calendar.WebContent;
import com.google.gdata.data.extensions.Recurrence;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class CalendarConnect {

	private static final String METAFEED_URL_BASE = "https://www.google.com/calendar/feeds/";

	private static final String ALLCALENDARS_FEED_URL_SUFFIX = "/allcalendars/full";

	private static final String OWNCALENDARS_FEED_URL_SUFFIX = "/owncalendars/full";

	private static URL metafeedUrl = null;

	private static URL allcalendarsFeedUrl = null;

	private static URL owncalendarsFeedUrl = null;

	private static final String DOODLES_CALENDAR_ID = "c4o4i7m2lbamc4k26sc2vokh5g%40group.calendar.google.com";

	private static final String RED = "#A32929";
	private static final String BLUE = "#2952A3";
	private static final String GREEN = "#0D7813";

	private static URL eventFeedUrl = null;

	private static final String EVENT_FEED_URL_SUFFIX = "/private/full";

	public List<CalendarEventEntry> getAllEvent(CalendarService service) throws IOException, ServiceException {
			CalendarEventFeed resultFeed = service.getFeed(eventFeedUrl,
			        CalendarEventFeed.class);
	    List<CalendarEventEntry> events = new ArrayList<CalendarEventEntry>();
	    for(int i = 0; i < resultFeed.getEntries().size(); i++) {
	    	events.add(resultFeed.getEntries().get(i));
	    }
	    return events;
	}

	public void deleteEvents(CalendarService service, List<CalendarEventEntry> eventsToDelete) throws ServiceException, IOException {

		// Add each item in eventsToDelete to the batch request.
		CalendarEventFeed batchRequest = new CalendarEventFeed();
		for (int i = 0; i < eventsToDelete.size(); i++) {
			CalendarEventEntry toDelete = eventsToDelete.get(i);
			// Modify the entry toDelete with batch ID and operation type.
			BatchUtils.setBatchId(toDelete, String.valueOf(i));
			BatchUtils.setBatchOperationType(toDelete, BatchOperationType.DELETE);
			batchRequest.getEntries().add(toDelete);
		}

		// Get the URL to make batch requests to
		CalendarEventFeed feed = service.getFeed(eventFeedUrl, CalendarEventFeed.class);
		Link batchLink = feed.getLink(Link.Rel.FEED_BATCH, Link.Type.ATOM);
		URL batchUrl = new URL(batchLink.getHref());

		// Submit the batch request
		CalendarEventFeed batchResponse = service.batch(batchUrl, batchRequest);

		// Ensure that all the operations were successful.
		boolean isSuccess = true;
		for (CalendarEventEntry entry : batchResponse.getEntries()) {
			String batchId = BatchUtils.getBatchId(entry);
			if (!BatchUtils.isSuccess(entry)) {
				isSuccess = false;
				BatchStatus status = BatchUtils.getBatchStatus(entry);
				System.out.println("\n" + batchId + " failed (" + status.getReason() + ") " + status.getContent());
			}
		}
		if (isSuccess) {
			System.out.println("Successfully deleted all events via batch request.");
		}
	}

	public CalendarEventEntry createEvent(CalendarService service, String eventTitle, String eventContent, String recurData, boolean isQuickAdd, WebContent wc)
			throws ServiceException, IOException {
		CalendarEventEntry myEntry = new CalendarEventEntry();

		myEntry.setTitle(new PlainTextConstruct(eventTitle));
		myEntry.setContent(new PlainTextConstruct(eventContent));
		myEntry.setQuickAdd(isQuickAdd);
		myEntry.setWebContent(wc);

		// If a recurrence was requested, add it. Otherwise, set the
		// time (the current date and time) and duration (30 minutes)
		// of the event.
		if (recurData == null) {
			Calendar calendar = new GregorianCalendar();
			DateTime startTime = new DateTime(calendar.getTime(), TimeZone.getDefault());

			calendar.add(Calendar.MINUTE, 30);
			DateTime endTime = new DateTime(calendar.getTime(), TimeZone.getDefault());

			When eventTimes = new When();
			eventTimes.setStartTime(startTime);
			eventTimes.setEndTime(endTime);
			myEntry.addTime(eventTimes);
		} else {
			Recurrence recur = new Recurrence();
			recur.setValue(recurData);
			myEntry.setRecurrence(recur);
		}

		// Send the request and receive the response:
		return service.insert(eventFeedUrl, myEntry);
	}

	public CalendarService createConnect() {
		String userName = "fitnessfitty@gmail.com";
		String userPassword = "FitnessFitt";

		try {
			metafeedUrl = new URL(METAFEED_URL_BASE + userName);
			allcalendarsFeedUrl = new URL(METAFEED_URL_BASE + userName + ALLCALENDARS_FEED_URL_SUFFIX);
			owncalendarsFeedUrl = new URL(METAFEED_URL_BASE + userName + OWNCALENDARS_FEED_URL_SUFFIX);
			eventFeedUrl = new URL(METAFEED_URL_BASE + userName + EVENT_FEED_URL_SUFFIX);
		} catch (MalformedURLException e) {
			// Bad URL
			System.err.println("Uh oh - you've got an invalid URL.");
			e.printStackTrace();
		}

		CalendarService service = new CalendarService("demo-CalendarFeedDemo-1");

		try {
			service.setUserCredentials(userName, userPassword);
		} catch (AuthenticationException e) {
			// Invalid credentials
			e.printStackTrace();
		}
		return service;
	}
}
