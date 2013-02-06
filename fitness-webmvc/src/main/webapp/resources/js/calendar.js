$(document).ready(function() {
	createFitnessCalendarTable();
	
	var oneDay = 1000*60*60*24;
	
	d=new Date(new Date().getTime() + oneDay*4);
	var actualPageMonday = new Date(d.getTime()-(d.getDay()-1)*oneDay);
	var today = new Date(new Date().getTime() + oneDay*4);
	
	var weekday=new Array(7);
	weekday[0]="Sunday";
	weekday[1]="Monday";
	weekday[2]="Tuesday";
	weekday[3]="Wednesday";
	weekday[4]="Thursday";
	weekday[5]="Friday";
	weekday[6]="Saturday";
	
	var monthNames = [ "Január", "Február", "Március", "Április", "Május", "Június",
	                   "Július", "Augusztus", "Szeptember", "Október", "November", "December" ];
	setDates();
	
	$('#prev-week').click(function() {
		actualPageMonday.setTime(actualPageMonday.getTime() - oneDay*7);
		setDates();
	});
	
	$('#next-week').click(function() {
		actualPageMonday.setTime(actualPageMonday.getTime() + oneDay*7);
		setDates();
	});
	
	$('#this-week').click(function() {
		actualPageMonday.setTime(actualDatesMonday(today).getTime());
		setDates();
	});
	
	function actualDatesMonday(date) {
		return new Date(date.getTime() - (date.getDay()-1)*oneDay);
	}
	
	function setDates() {
		setAllColumnToWhite();
		actualPageSunday = new Date(actualPageMonday.getTime() + oneDay*6);
		thisMonday = actualDatesMonday(today);
		$('#this-week-monday').html(monthNames[actualPageMonday.getMonth()] + " " + actualPageMonday.getDate());
		$('#this-week-sunday').html(monthNames[actualPageSunday.getMonth()]+ " " + actualPageSunday.getDate());
		if(actualPageMonday.getMonth() == thisMonday.getMonth() && actualPageMonday.getDate() == thisMonday.getDate()) {
			$('.' + weekday[today.getDay()]).css('background-color', "#fcf8e3");
		}
	}
	
	function setAllColumnToWhite() {
		for(var i = 0; i < 7; i++) {
			$('.' + weekday[i]).css('background-color', "white");
		}
	}
	
	$('#fitness-calendar-table > tbody > tr > td').on('click', function(e){
		indexOfDay=(weekday.indexOf($(this).attr('class')) - 1);
		indexOfDayWithModulo=((indexOfDay%7)+7)%7;
		hour = $(this).parent().attr('class');
		hour = hour.replace("hours-", "");
		trainingDate = new Date(new Date(actualPageMonday.getTime()+ oneDay*indexOfDayWithModulo).setHours(hour, 0, 0, 0));
		
		newTraining(trainingDate.getTime(), $('#defaultUrl').val()+'edzesek/ujedzes', this);
	});
	
	
	function newTraining(date, url, tableElement) {
		$.ajax({
			url: url,
			type: 'POST',
			data: ({
				date: date
			}),
			success: function(data) {
					tableElement.style.backgroundColor = 'red';
					tableElement.innerHTML = data;
			}
		});
	}

});


function createFitnessCalendarTable() {
	
	tableWithChanger = '<div id="week-changer" style="text-align:center; color:#0088cc">'
				+ '<i id="prev-week" class="icon-arrow-left"></i>  <span id="this-week-monday"></span>  ' 
				+ '<i id="this-week"class="icon-refresh"></i>  <span id="this-week-sunday"></span>  '
				+ '<i id="next-week" class="icon-arrow-right"></i></div>';
	
	datesInJSON = $.parseJSON('{"header" : ["Időpont", "Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"]}');
	datesInEnglishJSON = $.parseJSON('{"body" : ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]}');
	
	table = '<div><table id="fitness-calendar-table" class="table table-bordered">';
	thead = '<thead><tr>';
	for(var i = 0; i < datesInJSON.header.length; i++) {
		span = i == 0 ? "span1" : "span2";
		thead += '<th class='+span+'>' + datesInJSON.header[i] + '</th>';
	}
	thead +='</th></thead>';

	tbody = '<tbody>';
	for(var i = 8; i < 22; i++) {
		tbody += '<tr class="hours-'+i+'"><th>'+i+':00</th>';
		for(var j = 0; j < datesInEnglishJSON.body.length; j++) {
			tbody += '<td class="'+datesInEnglishJSON.body[j]+'"></td>';
		}
		tbody += '</tr>';
	}
	tbody += '</tbody>';
	
	table += thead + tbody + '</table></div>'; 
	
	tableWithChanger += table;
	
	$('.fitness-calendar').append(tableWithChanger);
	
}