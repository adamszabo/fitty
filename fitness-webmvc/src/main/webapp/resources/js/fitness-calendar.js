var FitnessCalendar = function(){
	
	var forTrainer=false;
	var oneDay = 1000*60*60*24;
	var today = new Date();
	var actualPageMonday = getActualPageMonday();
	var weekday=["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	var monthNames = [ "Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December" ];
	var defaultUrl=$('#defaultUrl').val();
	
	function init(calendarDivClassName, isTrainerCalendar){
		forTrainer=isTrainerCalendar;
		generateFitnessCalendarTable(calendarDivClassName);
		setDates();
		bindingClickEventToCalendarButtons();
	}

	
	function setTrainersTrainingsOnCalendar(username){
		$.ajax({
			url: defaultUrl+'edzesek/edzo/naptar',
			type: 'POST',
			data: ({
				username: username,
				actualPageMonday: actualPageMonday
			}),
			success: function(data) {
					clearCalendar();
					renderTrainingsOnCalendar(data, username);
			}
		});
	}
	
	function renderTrainingsOnCalendar(data, username){
		if(data.orderedTrainings.length>0){
			for(var i=0;i<data.orderedTrainings.length;++i){
				var timeDetails= getTrainingTimeDetails(data.orderedTrainings[i].trainingStartDate);
//				console.log(timeDetails.trainingDayName+' '+timeDetails.trainingStartHour);
				var calendarEntry=$('.hours-'+timeDetails.trainingStartHour+' .'+timeDetails.trainingDayName);
				modifyReservedCalendarEntry(calendarEntry, data.orderedTrainings[i].client);
			}
		}
		
		if(data.trainingsInBasket.length>0){
			for(var i=0;i<data.trainingsInBasket.length;++i){
				var timeDetails= getTrainingTimeDetails(data.trainingsInBasket[i].trainingStartDate);
				if(isDateOnActualWeek(timeDetails.trainingDate) && data.trainingsInBasket[i].trainer.username==username){
					$('.hours-'+timeDetails.trainingStartHour+' .'+timeDetails.trainingDayName).css('background-color','gray');
				}
			}
		}
	}
	
	function modifyReservedCalendarEntry(calendarEntry, client){
		calendarEntry.css('background-color','#620d0A');
		if(forTrainer){
			calendarEntry.html(client.fullName);
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
		return date.getTime()>=actualPageMonday && date.getTime()<=actualPageSunday.getTime();
	}
	
	function clearCalendar(){
		$('#fitness-calendar-table > tbody > tr > td').html('').css('background-color', 'white');
		markActualDayInCalendar();
	}
	
	function newTraining(date, url, tableElement) {
		selectedLi=$('#trainers-selector .trainer-name-li.active');
		$('#newTrainingModalTrainer').html(selectedLi.children(":first").html());
		$('#newTrainingModalDateTD').html(formatDate(date));
		$('#training-date').val(date);
		$('#trainer-username').val(selectedLi.data('username'));
		$('#newTrainingModal').modal('show');
	}
	
	function formatDate(date){
		return date.getFullYear() +'.'+(date.getMonth()+1) +'.' +date.getDate()+'  '+date.getHours()+':00';
	}
	
	function setDates() {
		for(var i = 0; i < 7; i++) {
			$('.' + weekday[i]).css('background-color', "white");
		}
		actualPageSunday = new Date(actualPageMonday.getTime() + oneDay*6);
		thisMonday = actualDatesMonday(today);
		console.log(actualPageMonday +  '          ' + thisMonday);
		$('#this-week-monday').html(monthNames[actualPageMonday.getMonth()] + " " + actualPageMonday.getDate());
		$('#this-week-sunday').html(monthNames[actualPageSunday.getMonth()]+ " " + actualPageSunday.getDate());
		
	}
	
	function markActualDayInCalendar(){
		if(actualPageMonday.getMonth() == thisMonday.getMonth() && actualPageMonday.getDate() == thisMonday.getDate()) {
			$('.' + weekday[today.getDay()]).css('background-color', "#fcf8e3");
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
	
	function setTrainersNameAndCalendarEntries(){
		username=$('#trainers-selector .active').data('username');
		setTrainersTrainingsOnCalendar(username, actualPageMonday);
	}
	
	function calendarEntryClick(element){
		indexOfDay=(weekday.indexOf(element.attr('class')) - 1);
		indexOfDayWithModulo=getModulo(indexOfDay, 7);
		hour = element.parent().attr('class').replace("hours-", "");
		trainingDate = new Date(new Date(actualPageMonday.getTime()+ oneDay*indexOfDayWithModulo).setHours(hour, 0, 0, 0));
		
		if(!forTrainer){
			newTraining(trainingDate, defaultUrl+'edzesek/ujedzes', $this);
		}
		else{
			$('#newTrainingModal .modal-body').html('<h4>Felveszi szabadnapként?</h4>');
			$('#newTrainingModal').modal('show');
		}
	}
	
	function bindingClickEventToCalendarButtons(){
		$('#prev-week').click(function() {
			actualPageMonday.setTime(actualPageMonday.getTime() - oneDay*7);
			setDates();
			setTrainersNameAndCalendarEntries();
		});
		
		$('#next-week').click(function() {
			actualPageMonday.setTime(actualPageMonday.getTime() + oneDay*7);
			setDates();
			setTrainersNameAndCalendarEntries();
		});
		
		$('#this-week').click(function() {
			actualPageMonday.setTime(actualDatesMonday(today).getTime());
			setDates();
			setTrainersNameAndCalendarEntries();
		});
		
		$('#fitness-calendar-table > tbody > tr > td').on('click', function(e){
			$this=$(this);
			calendarEntryClick($this);
		});
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
			thead += '<th class='+span+'>' + datesInJSON.header[i] + '</th>';
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
		tbody += '</tbody>';
		
		table += thead + tbody + '</table>'; 
		table += generateNewTrainingModal(); 
		
		tableWithChanger += table;
		
		$('.'+calendarDivClassName).append(tableWithChanger);
	}
	
	function generateNewTrainingModal(){
		var modal='<div id="newTrainingModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
					+ '<div class="modal-header">'
						+ '<h3 id="myModalLabel">Kosárba helyezi az edzést?</h3>'
					+ '</div>'
					+ '<form class="form-horizontal" action="'+defaultUrl+'edzesek/ujedzes" method="post">'
					+ '<input type="hidden" id="trainer-username" name="trainer-username" value="" />'
					+ '<input type="hidden" id="training-date" name="training-date" value="" />'
						+ '<div class="modal-body">'
							+ '<table class="table"><thead><th>Időpont</th><th>Edző</th></thead><tbody><tr>'
							+ '<td id="newTrainingModalDateTD">2012-12-11 12:12</td>'
							+ '<td id="newTrainingModalTrainer"></td>'
							+ '</tr></tbody></table>'
						+ '</div>'
						+ '<div class="modal-footer">'
							+ '<button class="btn btn-danger" type="submit">Felvesz</button>'
							+ '<button id="newTrainingModalHideButton" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">Mégse</button>'
						+'</div>'
					+ '</form>'
				+'</div>';
		
		return modal;
	}
	
	return {
		init : init,
		setTrainersTrainingsOnCalendar : setTrainersTrainingsOnCalendar
	};
}();