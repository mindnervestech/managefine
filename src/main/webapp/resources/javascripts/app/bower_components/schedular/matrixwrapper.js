(function($) {
	$.fn.extend({
		
		matrixWrapper: function(opts){
		
			var defaults = {
				
				
			};
			var self = this;
			
			var opts = $.extend(defaults, opts);
			var main = $(this);
			main.empty();
			var day = opts.day;
			var width = opts.widthofday;
			var date = opts.date;
			setHeaders();
			
			function setHeaders(){
				if($("#week-header").length>0) {
					$("#week-header").remove();
				}
				/*var names = $("<div class='name'></div>");*/
				var week = $("<div class='week' style='margin-bottom:-3px;'></div>");
				var header = $("<div id='week-header' style='height:20px;display: -webkit-inline-box;width:97%;'></div>");
				if(date != null){
					var weekDay = date.getDay();
					for(var i = 0; i <day.length; i++){
						if(weekDay == 0) {
							dateToBeShown = new Date(date.getTime() - (24 * 60 * 60 * 1000 * 6 ));
						} else {
							dateToBeShown = new Date(date.getTime() + (24 * 60 * 60 * 1000 * i+1 ));
						}
						
						var dateObj = new Date(dateToBeShown.getMonth()+1 + "/" + dateToBeShown.getDate() + "/" +dateToBeShown.getFullYear());
						
						newTickLabel = "<div style='position: relative;float:left;background:#0067b0;'><span>"+day[i] + " ("+moment(dateObj).format("DD MMM")+")"+"</span></div>";
						if(i==0) {
							$(newTickLabel).css( "width",(1.4*(100/(day.length+0.4)))+"%" ).appendTo(week);
						} else {
							$(newTickLabel).css( "width",(100/(day.length+0.4))+"%" ).appendTo(week);
						}
					}
				}
				//header.append(names,week);
				header.append(week);
				main.append(header);
			}
			
			var data = JSON.parse(opts.data);
			
			$.each( data, function( i, d ){
				main.weekSchedule({
					day:day,
					startDay:"mon",
					year:opts.year,
					width:width,
					week:opts.week,
					data:d.data,
					truckName: d.truckName,
					addFunction:opts.addFunction,
					editFunction:opts.editFunction
				});
			});
			
			
			
			
			
			
			
		
		
		
		
		},
	});
}(jQuery));