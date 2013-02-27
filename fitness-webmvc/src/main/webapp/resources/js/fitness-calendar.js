var FitnessCalendar = function(){
	var forTrainer=false;
	var oneDay = 1000*60*60*24;
	var today = new Date();
	var actualPageMonday = getActualPageMonday();
	var actualPageSunday = new Date();
	var weekday=["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	var monthNames = [ "Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December" ];
	var defaultUrl=$('#defaultUrl').val();
	var trainingDate = new Date();
	var username="";
	
	function init(calendarDivClassName){
		generateFitnessCalendarTable(calendarDivClassName);
		setDates();
		bindingClickEventToCalendarButtons();
	}
	
	function setTrainersNameAndCalendarEntries(userName, isForTrainer){
		username=userName;
		forTrainer=isForTrainer;
		setTrainersTrainingsOnCalendar();
	}

	function setTrainersTrainingsOnCalendar(){
		$.ajax({
			url: defaultUrl+'edzesek/edzo/naptar',
			type: 'POST',
			data: ({
				username: username,
				actualPageMonday: actualPageMonday
			}),
			success: function(data) {
					clearCalendar();
					markPastCalendarEntries();
					bindingClickToCalendarEntriesAfterToday();
					renderTrainingsOnCalendar(data, username);
			}
		});
	}
	
	function renderTrainingsOnCalendar(data, username){
		if(data.orderedTrainings.length>0){
			for(var i=0;i<data.orderedTrainings.length;++i){
				var timeDetails= getTrainingTimeDetails(data.orderedTrainings[i].date);
//				console.log(timeDetails.trainingDayName+' '+timeDetails.trainingStartHour);
				var calendarEntry=$('.hours-'+timeDetails.trainingStartHour+' .'+timeDetails.trainingDayName);
				modifyReservedCalendarEntry(calendarEntry, data.orderedTrainings[i].client);
			}
		}
		
		if(data.trainingsInBasket.length>0){
			for(var i=0;i<data.trainingsInBasket.length;++i){
				var timeDetails= getTrainingTimeDetails(data.trainingsInBasket[i].date);
				if(isDateOnActualWeek(timeDetails.trainingDate) && data.trainingsInBasket[i].trainer.username==username){
//					console.log(data.trainingsInBasket[i].trainer.username+' '+username);
					$('.hours-'+timeDetails.trainingStartHour+' .'+timeDetails.trainingDayName).removeClass('free-entry today-entry')
						.addClass('entry-inbasket').off('click').html('<i class="icon-white icon-shopping-cart"></i>');
				}
			}
		}
	}
	
	function bindingClickToCalendarEntriesAfterToday(){
		//entries on the future's weeks
		if(actualPageMonday.getTime()>today.getTime()){
			bindingClickToCalendarEntries();
		}
		//actual week
		else if(isDateOnActualWeek(today)){
			if(today.getDay()>0){
				for(var i=(today.getDay()+1);i<weekday.length;i++){
					$('#fitness-calendar-table > tbody > tr > td.'+weekday[i]).on('click', function(){
						calendarEntryClick($(this));
					});
				}
				$('#fitness-calendar-table > tbody > tr > td.'+weekday[0]).on('click', function(){
					calendarEntryClick($(this));
				});
			}
		}
	}
	
	function markPastCalendarEntries(){
		var actualPageSundayLastSec=new Date(actualPageSunday.getTime()+oneDay-10);
		
		//entries on the past's weeks
		if(actualPageSundayLastSec.getTime()<today.getTime()){
			$('#fitness-calendar-table > tbody > tr > td').addClass('past-entry');
		}
		//actual week
		else if(isDateOnActualWeek(today)){
				var limit=today.getDay();
				if(today.getDay()==0){
					limit=weekday.length;
				}
				
				for(var i=1;i<limit;i++){
					entry=$('#fitness-calendar-table > tbody > tr > td.'+weekday[i]).addClass('past-entry');
				}
		}
	}
	
	function modifyReservedCalendarEntry(calendarEntry, client){
		calendarEntry.off('click');
		calendarEntry.removeClass('free-entry today-entry').addClass('reserved-entry').html('<i class="icon-white icon-lock"></i>');
		if(forTrainer && client.username!=username){
			calendarEntry.html('<b>'+client.fullName+'</b>');
		}
		else if(forTrainer && client.username==username){
			calendarEntry.html('Szabadság');
		}
	}
	
	function getTrainingTimeDetails(startDate){
		var trainingDate = new Date(startDate);
		var trainingDayName = weekday[trainingDate.getDay()];
		var trainingStartHour = trainingDate.getHours();
		
		return {
			trainingDate : trainingDate,
			trainingDayName : trainingDayName,
			trainingStartHour : trainingStartHour
		};
	}
	
	function isDateOnActualWeek(date){
		return date.getTime()>=actualPageMonday.getTime() && date.getTime()<actualPageSunday.getTime()+oneDay;
	}
	
	function clearCalendar(){
		$('#fitness-calendar-table > tbody > tr > td').html('').removeClass('reserved-entry entry-inbasket today-entry free-entry past-entry').addClass('free-entry');
		$('#fitness-calendar-table > tbody > tr > td').off('click');
		markActualDayInCalendar();
	}
	
	function newTraining(date, url, tableElement) {
		selectedLi=$('#trainers-selector .trainer-name-li.active');
		$('#newTrainingModalTrainer').html(selectedLi.children(":first").html());
		$('#newTrainingModalPrice').html($(selectedLi.children(":first")).closest('li').data('price'));
		$('#newTrainingModalDateTD').html(formatDate(date));
		$('#training-date').val(date);
		$('#trainer-username').val(username);
		$('#newTrainingModal').modal('show');
	}
	
	function formatDate(date){
		return date.getFullYear() +'.'+(date.getMonth()+1) +'.' +date.getDate()+'  '+date.getHours()+':00';
	}
	
	function setDates() {
		actualPageSunday = new Date(actualPageMonday.getTime() + oneDay*6);
		thisMonday = actualDatesMonday(today);
//		console.log(actualPageMonday +  '          ' + thisMonday);
		$('#this-week-monday').html(monthNames[actualPageMonday.getMonth()] + " " + actualPageMonday.getDate());
		$('#this-week-sunday').html(monthNames[actualPageSunday.getMonth()]+ " " + actualPageSunday.getDate());
		
	}
	
	function markActualDayInCalendar(){
		$('#fitness-calendar-table thead tr th').removeClass('today-entry');
		
		if(actualPageMonday.getMonth() == thisMonday.getMonth() && actualPageMonday.getDate() == thisMonday.getDate()) {
			$('.' + weekday[today.getDay()]).addClass('today-entry');
		}
	}
	
	function actualDatesMonday(date) {
		var actualDay=getModulo(date.getDay()-1, 7);
		var monday=new Date(date.getTime() - (actualDay)*oneDay);
		return new Date(monday.setHours(0, 0, 0, 0));
	}
	
	function getActualPageMonday(){
		var actualDay=getModulo(today.getDay()-1, 7);
		var monday=new Date(today.getTime()-(actualDay*oneDay));
		return new Date(monday.setHours(0, 0, 0, 0));
	}
	
	function getModulo(number, mod){
		return ((number%mod)+mod)%mod;
	}
	
	function calendarEntryClick(element){
		var dayClassName=element.attr('class').split(' ')[0];
		var indexOfDay=(weekday.indexOf(dayClassName) - 1);
		var indexOfDayWithModulo=getModulo(indexOfDay, 7);
		var hour = element.parent().attr('class').replace("hours-", "");
		trainingDate = new Date(new Date(actualPageMonday.getTime()+ oneDay*indexOfDayWithModulo).setHours(hour, 0, 0, 0));
		
		if(!forTrainer){
			newTraining(trainingDate, defaultUrl+'edzesek/ujedzes', $this);
		}
		else{
			manageCalendarEntry(trainingDate);
		}
	}
	
	function manageCalendarEntry(trainingDate){
		$('#trainerManageModalTimeSpan').html(formatDate(trainingDate));
		$('#trainerManageModal-hour').click();
		$('#trainerManageModal').modal('show');
	}
	
	function bindingClickToTrainerManageModalSubmit(){
		$('#trainerManageModalSubmit').on('click', function(){
			var vacationRadioButton=$('#trainerManageModal button.active').data('time');
			if(vacationRadioButton=="hour"){
				addHourToVacation();
				$('#trainerManageModal').modal('hide');
			}
			else if(vacationRadioButton=="all-day"){
				addAllDayToVacation();
			}
		});
	}
	
	function addAllDayToVacation(){
		var timeDetails=getTrainingTimeDetails(trainingDate);
		var numberOfReservedEntries=$('#fitness-calendar-table > tbody > tr > td.reserved-entry.'+timeDetails.trainingDayName).length;
		if(numberOfReservedEntries>0 && $('#trainerModalWarning').css('display')=='none'){
			$('#trainerModalWarning').fadeIn('fast');
		}
		else{
			goOnHolidayAllDay();
			$('#trainerManageModal').modal('hide');
		}
		
		$('#trainerManageModal').on('hide', function(){
			$('#trainerModalWarning').css('display','none');
		});
	}
	
	function addHourToVacation(){
		$.ajax({
			url: defaultUrl+'edzo/vakacio',
			type: 'POST',
			data: ({
				trainingDate: trainingDate.getTime()
			}),
			success: function(data) {
				setTrainersTrainingsOnCalendar();
			}
		});
	}
	
	function goOnHolidayAllDay(){
		$.ajax({
			url: defaultUrl+'edzo/vakacioNap',
			type: 'POST',
			data: ({
				trainingDate: trainingDate.getTime()
			}),
			success: function(data) {
				setTrainersTrainingsOnCalendar();
			}
		});
	}
	
	function bindingClickToCalendarEntries(){
		$('#fitness-calendar-table > tbody > tr > td').on('click', function(e){
			var element=$(this);
			calendarEntryClick(element);
		});
	}
	
	function bindingClickEventToCalendarButtons(){
		$('#prev-week').on('click', function() {
			actualPageMonday.setTime(actualPageMonday.getTime() - oneDay*7);
			setDates();
			setTrainersTrainingsOnCalendar();
		});
		
		$('#next-week').on('click', function() {
			actualPageMonday.setTime(actualPageMonday.getTime() + oneDay*7);
			setDates();
			setTrainersTrainingsOnCalendar();
		});
		
		$('#this-week').on('click', function() {
			actualPageMonday.setTime(actualDatesMonday(today).getTime());
			setDates();
			setTrainersTrainingsOnCalendar();
		});
		
		bindingClickToCalendarEntries();
		bindingClickToTrainerManageModalSubmit();
	}
	
	
	function generateFitnessCalendarTable(calendarDivClassName) {
		
		var tableWithChanger = '<div id="week-changer" style="text-align:center; color:#0088cc"> <div class="btn-group weekchanger-btn-group">'
					+ '<button id="prev-week" class="btn"><i class="icon-arrow-left"></i><span id="this-week-monday"></span></button>' 
					+ '<button id="this-week" class="btn"><i class="icon-refresh"></i></button>'
					+ '<button id="next-week" class="btn"><span id="this-week-sunday"></span><i class="icon-arrow-right"></i></button>'
					+'</div></div>';
		
		datesInJSON = $.parseJSON('{"header" : ["", "Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"]}');
		datesInEnglishJSON = $.parseJSON('{"body" : ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]}');
		
		var table = '<table id="fitness-calendar-table" class="table table-bordered" style="table-layout: fixed">';
		var thead = '<thead><tr>';
		for(var i = 0; i < datesInJSON.header.length; i++) {
			span = i == 0 ? "span1" : "span2";
			
			if(i>0){
				var dayname=weekday[getModulo(i, 7)];
				thead += '<th class="'+dayname+' '+span+'">' + datesInJSON.header[i] + '</th>';
			}
			else{
				thead += '<th class='+span+'>' + datesInJSON.header[i] + '</th>';
			}
		}
		thead +='</th></thead>';
	
		var tbody = '<tbody>';
		for(var i = 8; i < 22; i++) {
			tbody += '<tr class="hours-'+i+'"><th>'+i+':00</th>';
			for(var j = 0; j < datesInEnglishJSON.body.length; j++) {
				tbody += '<td class="'+datesInEnglishJSON.body[j]+'"></td>';
			}
			tbody += '</tr>';
		}
		
		var notation='<tr><th id="notation-th" colspan="8">'
							+ '<div class="notation-div free-entry">&nbsp;</div><span>szabad időpont</span> <div class="notation-div today-entry">&nbsp;</div><span>mai nap</span>'
							+ '<div class="notation-div" style="background-color: rgb(223,223,223)">&nbsp;</div><span>múltbéli események</span>'
							+ '<div class="notation-div reserved-entry"><i class="icon-white icon-lock"></i></div><span>foglalt időpont</span>'
							+ '<div class="notation-div entry-inbasket"><i class="icon-white icon-shopping-cart"></i></i></div><span>kosárba helyezett időpont</span>'
					+'</th></tr>';
		
		tbody += notation + '</tbody>';
		
		table += thead + tbody + '</table>'; 
		table += generateNewTrainingModal(); 
		
		tableWithChanger += table;
		
		$('.'+calendarDivClassName).append(tableWithChanger);
	}
	
	function generateNewTrainingModal(){
		var newTrainingModal='<div id="newTrainingModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
					+ '<div class="modal-header">'
						+ '<h3 id="myModalLabel">Kosárba helyezi az edzést?</h3>'
					+ '</div>'
					+ '<form class="form-horizontal" action="'+defaultUrl+'edzesek/ujedzes" method="post">'
					+ '<input type="hidden" id="trainer-username" name="trainer-username" value="" />'
					+ '<input type="hidden" id="training-date" name="training-date" value="" />'
						+ '<div class="modal-body">'
							+ '<table class="table"><thead><th>Időpont</th><th>Ár</th><th>Edző</th></thead><tbody><tr>'
							+ '<td id="newTrainingModalDateTD">2012-12-11 12:12</td>'
							+ '<td id="newTrainingModalPrice"></td>'
							+ '<td id="newTrainingModalTrainer"></td>'
							+ '</tr></tbody></table>'
						+ '</div>'
						+ '<div class="modal-footer">'
							+ '<button class="btn btn-danger" type="submit">Felvesz</button>'
							+ '<button id="newTrainingModalHideButton" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Mégse</button>'
						+'</div>'
					+ '</form>'
				+'</div>';
		
		var trainerManageModal='<div id="trainerManageModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="trainerManageModalLabel" aria-hidden="true">'
			+ '<div class="modal-header">'
				+ '<h3 id="trainerManageModalLabel">Elfoglaltság felvétele</h3>'
			+ '</div>'
				+ '<div class="modal-body">'
					+ '<div style="padding:4px;"><b>Időpont: <span id="trainerManageModalTimeSpan"></span></b></div>'
					+ '<div class="btn-group" data-toggle="buttons-radio" style="margin-bottom:6px;">'
						+ '<button type="button" id="trainerManageModal-hour" class="btn btn-warning" data-time="hour">Adott órára</button>'
						+ '<button type="button" id="trainerManageModal-allday" class="btn btn-warning" data-time="all-day">Egész nap</button>'
					+ '</div>'
					+ '<div id="trainerModalWarning" class="alert alert-warning" style="display:none;"><strong>A kijelölt napon már van foglalt időpont!</strong><br/>'
							+'<span>Ha a felvesz gombra kattint, akkor a rendszer a kiválasztott nap szabad időpontjait foglalja le.</span></div>'
				+ '</div>'
				+ '<div class="modal-footer">'
					+ '<button id="trainerManageModalSubmit" class="btn btn-danger" type="button">Felvesz</button>'
					+ '<button id="trainerManageModalHideButton" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Mégse</button>'
				+'</div>'
			+ '</form>'
		+'</div>';

		return newTrainingModal + trainerManageModal;
	}
	
	return {
		init : init,
		setTrainersNameAndCalendarEntries : setTrainersNameAndCalendarEntries,
	};
}();