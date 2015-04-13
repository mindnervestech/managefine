(function($) {
	$.fn.extend({
		
		weekSchedule: function(opts){
		
			var defaults = {
				startDay: "mon",
				day:["mon","tue","wed","ths","fri","sat","sun"],
				
			};
			var self = this;
			var width = opts.width;
			var opts = $.extend(defaults, opts);
			var main = $(this);
			var day = opts.day;
			var map = opts.data;
			function getData(k){
				if(map[k] == undefined ) {
					return "[]";
				}
				if(typeof map[k] =='string') 
					return map[k];
				else 
					return JSON.stringify(map[k]);
			}
			
			setSchdule();
			
			
			
			
			
			
			function setSchdule(){
				/*var names = $("<div class='name' style='float:left;padding:2px;'></div>");
				names.text(opts.truckName);*/
				if($("#doctor-week-schedule").length>0) {
					$("#doctor-week-schedule").remove();
				}
				var week = $("<div class='week'></div>");
				var truckSchedule = $("<div id='doctor-week-schedule' style='width:100%'></div>");
				var dayView;
				for(var i = 0; i <day.length; i++){
					showRullerFlag = true;
					showCurrentTimeFlag = false;
					newTickLabel = $("<div style'border:  1px solid black;' class='leftSch' id="+i+"></div>");
					week.append($(newTickLabel));
					if(i==6) {
						opts.week = opts.week+1;
					}
					if(i >= 1){ showRullerFlag = false;}
					if(moment(moment(moment({year:opts.year}).day(day[i]).week(opts.week))).format('DD/MM/YYYY') == moment(new Date()).format('DD/MM/YYYY')){showCurrentTimeFlag = true;}
					var date = new Date(moment(moment({year:opts.year}).day(day[i]).week(opts.week)).format('MM/DD/YYYY'));
					var unitValueInMin = 60;
					var gradationBetweenPerUnit = 15;
					var gradationBetweenPerUnitpx = 20;
					var str = 0;
					var end = 24;
					height = (end-str)*((60/gradationBetweenPerUnit)*gradationBetweenPerUnitpx);
					if(i==0) {
						dayView = newTickLabel.borderify({
							unitValueInMin:  unitValueInMin,
							gradationBetweenPerUnit: gradationBetweenPerUnit,
							gradationBetweenPerUnitpx:gradationBetweenPerUnitpx,
							width:(1.2*(100/(day.length+0.4)))+"%",
							data: getData(moment(moment({year:opts.year}).day(day[i]).week(opts.week)).format('DD/MM/YYYY')),
							startTime:str,
							endTime:end,
							showCurrentTime:showCurrentTimeFlag,
							showRuller:showRullerFlag,
							date:date,
							rullerHeight:height+"px",
							weekly:false,
							onceRuller:true,
							vertically:true,
							addFunction:opts.addFunction,
							editFunction:opts.editFunction
						});
					} else {
						dayView = newTickLabel.borderify({
							unitValueInMin:  unitValueInMin,
							gradationBetweenPerUnit: gradationBetweenPerUnit,
							gradationBetweenPerUnitpx:gradationBetweenPerUnitpx,
							width:(100/(day.length+0.4))+"%",
							data: getData(moment(moment({year:opts.year}).day(day[i]).week(opts.week)).format('DD/MM/YYYY')),
							startTime:str,
							endTime:end,
							showCurrentTime:showCurrentTimeFlag,
							showRuller:showRullerFlag,
							date:date,
							rullerHeight:height+"px",
							weekly:true,
							onceRuller:true,
							vertically:true,
							addFunction:opts.addFunction,
							editFunction:opts.editFunction
						});
					}
				}
				//names.css("height",dayView.height);
				//truckSchedule.append(names,week);
				truckSchedule.append(week);
				main.append(truckSchedule);
				main.css({
					"border": '1px solid black ',
					"border-radius": '3px',
				});
			}
			
			
			
		},
		
		
		
		
		
	});
}(jQuery));