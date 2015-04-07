(function($) {
	$.fn.extend({
		unitPerGridpx : 0,
		unitPerGrid : 0,
		unitValueInMin : 0,
		startTime : 0,
		editFunction:null,
		addFunction:null,
		config:null,
		borderify: function(opts){
			var defaults = {
				width: "100px",
				height: "100px",
				color: '#000'
			};
			var opts = $.extend(defaults, opts);
			var self = this;
			var o = opts;
			this.config = o;
			this.editFunction = o.editFunction;
			this.addFunction = o.addFunction;
			this.unitValueInMin = parseInt(o.unitValueInMin, 10);
			
			this.startTime = parseInt(o.startTime, 10);
			var endTime = parseInt(o.endTime, 10);
			
			this.unitPerGrid = parseInt(o.gradationBetweenPerUnit, 10);
			this.unitPerGridpx = parseInt(o.gradationBetweenPerUnitpx, 10); // 1 hour = 60 min ; 60/12 == 5 min
			var scaleNo = endTime - this.startTime;
			var data = JSON.parse(o.data);
			this.handleOverlap(data);
			var main = $(this);
			main.empty();
			var ruller = $("<div></div>");            
			var overlay;					
			var scheduler = $("<div></div>");
			var c = this.addFunction;
			scheduler.bind("click",function(e){
					var func = c.bind(null,e);
					func();
			});
								
			var i = 0;
			var start = this.startTime * 60;
			var end = o.endTime * 60;
			var unitTicketlabel = 0;
			var unit = 0;
			newTickLabel = "";
			while (start <  end) {
				if(o.showRuller) {
					if ((start % this.unitValueInMin ) == 0) {
						unit = 0;
						if(o.vertically) {
							newTickLabel = "<div class='tickLabel'><div></div><span style='position:absolute;font-size:12px;'>"+start/60+":00" + "</span></div>";
							$(newTickLabel).css( "top", unitTicketlabel+"px" ).appendTo(ruller);
						} else {
							newTickLabel = "<div class='tickLabelH'><div></div><span style='position:absolute;font-size:12px;'>"+start/60+":00" + "</span></div>";
							$(newTickLabel).css( "marginLeft", unitTicketlabel+"px" ).appendTo(ruller);
						}
						if(o.vertically) {
							darkLine = "<div class='WhiteLine'></div>";
							$(darkLine).css( "top", unitTicketlabel+"px" ).appendTo(scheduler);
						} else {
							darkLine = "<div class='WhiteLineH'></div>";
							$(darkLine).css( "marginLeft", unitTicketlabel+"px" ).appendTo(scheduler);
						}
					
					} else if ((start % (this.unitPerGrid*2)) == 0) {
						unit = unit + this.unitPerGrid;
						if(o.vertically) {
							newTickLabel = "<div class='tickMajor'><span>"+unit+"<span></div>";
							$(newTickLabel).css( "top", unitTicketlabel+"px" ).appendTo(ruller);
						} else {
							newTickLabel = "<div class='tickMajorH'><span>"+unit+"<span></div>";
							$(newTickLabel).css( "marginLeft", unitTicketlabel+"px" ).appendTo(ruller);
						}
					} else if ((start % this.unitPerGrid) == 0) {
						unit = unit + this.unitPerGrid;
						if(o.vertically) {
							newTickLabel = "<div class='tickMinor'><span>"+unit+"<span></div>";
							$(newTickLabel).css( "top", unitTicketlabel+"px" ).appendTo(ruller);
						} else {
							newTickLabel = "<div class='tickMinorH'><span>"+unit+"<span></div>";
							$(newTickLabel).css( "marginLeft", unitTicketlabel+"px" ).appendTo(ruller);
						}
					} 
					main.append(ruller);
					if(o.vertically) {
						ruller.addClass("vRule");
					} else {
						ruller.addClass("hRule");
					}
					if(o.onceRuller && o.vertically) {
						$(ruller).css({"width":"28.5%"});
						scheduler.css({"width":"70.9%"});
					} else if(o.onceRuller && !o.vertically){
						$(ruller).css({"height":"28.5%"});
						scheduler.css({"height":"71.5%"});
					}
						
				} else { //vert ticks
					if ((start % self.unitValueInMin ) == 0) {
						if(o.vertically) {
							darkLine = "<div class='WhiteLine'><span class='text-left'>"+start/60+":00" +"</span></div>";
							$(darkLine).css( "top", unitTicketlabel+"px" ).appendTo(scheduler);
						} else {
							darkLine = "<div class='WhiteLineH'><span class='text-left'>"+start/60+":00" +"</span></div>";
							$(darkLine).css( "marginLeft", unitTicketlabel+"px" ).appendTo(scheduler);
						}
						scheduler.addClass("completeScheduler");
						if(o.vertically) {
							scheduler.css({"width":"100%"});
						} else {
							scheduler.css({"height":o.height});
						}
					}
				}
				unitTicketlabel = unitTicketlabel + this.unitPerGridpx;
				start = ( start + this.unitPerGrid );	
			}
			if(o.vertically) {
				scheduler.addClass("scheduler");
			} else {
				scheduler.addClass("schedulerH");
			}
			main.append(scheduler);
			dateTemp = new Date();
			var dateObject = new Date(dateTemp.getFullYear(),dateTemp.getMonth(),dateTemp.getDate(),0,0,0,0);
			if(o.date.getDate() == dateObject.getDate() && o.date.getMonth() == dateObject.getMonth() && o.date.getFullYear() == dateObject.getFullYear()) {
				o.showCurrentTime = true;
			}
			if(dateObject>o.date) {
				o.showCurrentTime = false;
				var rullerHe = 0;
				if(o.rullerHeight) {
					rullerHe = o.rullerHeight;
				} else {
					rullerHe = $(ruller).css("height");
				}
				overlay;
				if(o.vertically) {
					overlay = $("<div></div>").css({"position":"absolute",
						"width":"100%",
						"zIndex":"0",
						"height":rullerHe,
						"opacity":"0.5",
						"background": "gainsboro"});
				} else {
					overlay = $("<div></div>").css({"position":"absolute",
						"width":rullerHe,
						"zIndex":"0",
						"height":o.height,
						"opacity":"0.5",
						"background": "gainsboro"});
				}
				$(scheduler).append(overlay);
				overlay.bind("click",function(e) {
					e.stopPropagation();
				});
			}
			//showRuller();
			if(o.date.getFullYear() >= dateObject.getFullYear() && o.date.getMonth() >= dateObject.getMonth() && o.date.getDate() > dateObject.getDate()) {
				o.showCurrentTime = false;
			}
			if(o.showCurrentTime){
				currentTime();
			}
			function currentTime() {
				var dt = new Date();
				if(dt.getHours() >= self.startTime && dt.getHours() < endTime){
					var top = (self.unitPerGridpx / self.unitPerGrid)*((dt.getHours() - self.startTime)*60  + dt.getMinutes());
				} else {
					return;
				}
				if (o.vertically){
					if($('.currentTime').length>0) {
						$('.currentTime').css( "top", top+"px" );
						$('.currentTime').children("span").text((dt.getHours()>=10?dt.getHours():"0"+dt.getHours())+":"+(dt.getMinutes()>10?dt.getMinutes():"0"+dt.getMinutes()));
						$(".overlay").css("height",top+"px");
						return;
					} 
				} else {
					if($('#overlay'+o.loopIndex).length>0) {
						$('.currentTimeV').children("span").text((dt.getHours()>=10?dt.getHours():"0"+dt.getHours())+":"+(dt.getMinutes()>10?dt.getMinutes():"0"+dt.getMinutes()));
						$('.currentTimeV').css( "marginLeft", top+"px" );
						$(".overlay").css("width",top+"px");
						return;
					}					
				}
				var width;
				var height;
				if(o.vertically) {
					width = '100%';
					height = top+"px";
					top = top+"px";
				} else {
					width = top+"px";
					height = o.height;
				}
				overlay = $("<div id='overlay"+o.loopIndex+"' class='overlay'></div>").css({"position":"absolute",
											"width":width,
											"zIndex":"0",
											"height":height,
											"opacity":"0.5",
											"background": "gainsboro"});
				$(scheduler).append(overlay);
				overlay.bind("click",function(e) {
					e.stopPropagation();
				});
				curTime = "<div class='currentTime'><span>"+dt.getHours()+":"+dt.getMinutes()+"</span></div>"; 
				if(!o.vertically) {
					if(o.loopIndex>0){
						curTime = $("<div class='currentTimeV' style='margin-left:"+top+"px;height:100%;top:0;'></div>");
					} else {
						curTime = $("<div class='currentTimeV' style='margin-left:"+top+"px;'><span>"+(dt.getHours()>=10 ? dt.getHours() : "0"+dt.getHours())+":"+(dt.getMinutes()>=10 ? dt.getMinutes() : "0"+dt.getMinutes())+"</span></div>");
					}
				} else {
					curTime = $("<div class='currentTime' style='top:"+top+";'><span>"+(dt.getHours()>=10 ? dt.getHours() : "0"+dt.getHours())+":"+(dt.getMinutes()>=10 ? dt.getMinutes() : "0"+dt.getMinutes())+"</span></div>");
				}
				$(curTime).appendTo(scheduler);
				setInterval ( function(){currentTime();}, 5*60*1000 );
			};
			function showRuller(){
				newTickLabel = "";
				while (start <  end) {
					if(!o.showRuller){
						if ((start % self.unitValueInMin ) == 0) {
							darkLine = "<div class='WhiteLine'><span>"+start/60+"00" +"</span></div>";
							$(darkLine).css( "top", unitTicketlabel+"px" ).appendTo(scheduler);
							scheduler.addClass("completeScheduler");
						} 
					}
					if(o.showRuller){
						if ((start % self.unitValueInMin ) == 0) {
							newTickLabel = "<div class='tickLabel'><div></div><span>"+start/60+"00" + "</span></div>";
							$(newTickLabel).css( "top", unitTicketlabel+"px" ).appendTo(ruller);
							darkLine = "<div class='WhiteLine'></div>";
							$(darkLine).css( "top", unitTicketlabel+"px" ).appendTo(scheduler);
							
						} else if ((start % (self.unitPerGrid*2)) == 0) {
							newTickLabel = "<div class='tickMajor'></div>";
							$(newTickLabel).css( "top", unitTicketlabel+"px" ).appendTo(ruller);
						} else if ((start % self.unitPerGrid) == 0) {
							newTickLabel = "<div class='tickMinor'></div>";
							$(newTickLabel).css( "top", unitTicketlabel+"px" ).appendTo(ruller);
						} 
						ruller.addClass("vRule");
						scheduler.addClass("scheduler");
					}
					unitTicketlabel = unitTicketlabel + self.unitPerGridpx;
					start = ( start + self.unitPerGrid );		
							
				}//vert ticks
				
				
			};
			for(var ind =0;ind<data.length;ind++) {
				scheduler.append(this.getScheduleDiv(data[ind]));
			}
			var height = ((60 / this.unitPerGrid)*this.unitPerGridpx)*scaleNo;
			if(o.vertically) {
				main.css({
					"width": o.width,
					"height": height,
					"border": '1px solid black',
					"border-radius": '3px'
				});
			} else {
				main.css({
					"width": height,
					"height": o.height,
					"border": '1px solid black',
					"border-radius": '3px'
				});
			}
			
			
		},
		
		getScheduleDiv : function (data) {
			var unitPerGridpx = this.unitPerGridpx;
			var unitValueInMin = this.unitValueInMin;
			var unitPerGrid = this.unitPerGrid;
			var value = this.unitPerGridpx*(this.unitValueInMin / this.unitPerGrid);
		    var pieces = data.endTime.split(':');
		    var toHr = parseInt(pieces[0], 10) - this.startTime;
		    var toMin = parseInt(pieces[1], 10);
		    var pieces = data.startTime.split(':');
		    var fromHr = parseInt(pieces[0], 10) -this.startTime;
		    var fromMin = parseInt(pieces[1], 10);
		    var toVal = (toHr*60 + toMin)/this.unitValueInMin;
		    var fromVal = (fromHr*60 + fromMin)/this.unitValueInMin;
			var top = this.unitPerGridpx*(this.unitValueInMin / this.unitPerGrid)*fromVal;
		    var height = this.unitPerGridpx*(this.unitValueInMin / this.unitPerGrid)*(toVal-fromVal);		
		    if((data.width+"").indexOf("px")==-1 && (data.width+"").indexOf("%")==-1) {
		    	data.width = data.width +"%";
		    }
		    if(data.left+"".indexOf("px")==-1 && data.left+"".indexOf("%")==-1) {
		    	data.left = data.left +"%";
		    }
		    height = height+"px";
		    if(!this.config.vertically) {
		    	var t1 = top;
		    	top = data.left;
		    	data.left = t1;
		    	t1 = data.width;
		    	data.width = height;
		    	height = t1;
		    } else {
		    	data.left = data.left+"%";
		    }
		    var line;
		    
		    if(this.config.vertically) {
		    	line = $("<div></div>").css({
							"marginTop":"2px",
							"top": top,
							"height": height,
							"width":data.width,
							"left":data.left,
							"background":data.color,
							"position":"absolute"
						});
		    } else {
			line = $("<div></div>").css({
						"marginTop":"2px",
						"top": top,
						"height": height,
						"width":data.width,
						"left":data.left,
						"background":data.color,
						"position":"absolute"
					});
		    }
		    if(data.type == "A") {
		    var c = this.editFunction;
		    	line.bind("click",function(e){
		    		if ($(this).hasClass('noclick')) {
		    	        data.eventType = "drag";
		    	        $(this).removeClass('noclick');
		    	    } else {
		    	    	data.eventType = "click";
		    	    }
		    		var temp = data;
		    		delete temp.dataIndex;
		    		delete temp.left;
		    		delete temp.width;
		    		var func = c.bind(null,e,temp);
		    		func();
		    	});
		    } else if(data.type == "Lu") {
		    	line.css({"width":data.width});
		    	line.bind("click",function(e){
		    		e.stopPropagation();
		    	});
		    } else {
		    	line.css({"width":"100%"});
		    	line.bind("click",function(e){
		    		e.stopPropagation();
		    	});
		    }
		    var s = data.startTime.split(":");
		    var tmpSHours = parseInt(s[0]);
		    var sAmPm = '';
		    if(tmpSHours<12){
		    	sAmPm = 'AM';
		    } else {
		    	sAmPm = 'PM';
		    }
		    var t1 = tmpSHours%12;
		    var sHours;
		    if(t1==0) {
		    	sHours = 12;
		    } else {
		    	sHours = t1;
		    }
		    if(sHours<10) {
		    	sHours = "0"+sHours;
		    }
		    var e = data.endTime.split(":");
		    var tmpEHours = parseInt(e[0]);
		    var eAmPm = '';
		    if(tmpEHours<12){
		    	eAmPm = 'AM';
		    } else {
		    	eAmPm = 'PM';
		    }
		    var t2 = tmpEHours%12;
		    var eHours;
		    if(t2==0) {
		    	eHours = 12;
		    } else {
		    	eHours = t2;
		    }
		    if(eHours<10) {
		    	eHours = "0"+eHours;
		    }
		    var smin = parseInt(s[1]);
		    if(smin<10) {
		    	smin = '0'+smin;
		    }
		    var emin = parseInt(e[1]);
		    if(emin<10) {
		    	emin = '0'+emin;
		    }
		    var toolTip = $("<div class='webui-popover' style='display: none;'></div>");
		    line.append();
		    line.addClass("shedule");
		    //line.draggable();
		    line.append(toolTip);
		    var arr = $("<div class='arrow'></div>");
		    var inner = $("<div class='webui-popover-inner'></div>");
		    toolTip.append(arr,inner);
		    var he = $("<h3 id='webui-popover-title'>"+data.visitType.split("-")[0]+" ("+sHours+":"+smin+sAmPm+" - "+eHours+":"+emin+eAmPm+")</h3>");
		    var cont = $("<div class='webui-popover-content'><div>");
		    inner.append(he,cont);
		    var table = $("<table></table>");
		    cont.append(table);
		    if(data.type == "A") {
		    	var t = $("<tr><td colspan='2' style='text-align:center;padding-bottom:12px;'>"+data.patientName+"</td></tr>");
		    	table.append(t);
		    } 
		    var t2 = $("<tr><td style='width:50%;'>Appointment Note</td><td style='width:50%;'>"+data.notes+"</td></tr>");
		    table.append(t2);
		    for(d in data.insurances) {
		    	var tr = $("<tr><td style='width:50%;'>Insurance :</td><td style='width:50%;'>"+d.planName+"</td></tr>");
		    	table.append(tr);
		    }
		    line.bind("mouseover",function(e) {
		    	toolTip.css({"display":"block","top":e.offsetY+12,"left":e.offsetX+12});
		    });
		    line.bind("mousemove",function(e) {
		    	toolTip.css({"top":e.offsetY+12,"left":e.offsetX+12});
		    });
		    line.bind("mouseout",function(e) {
		    	toolTip.css({"display":"none"});
		    });
		    //$(line).webuiPopover({title:'Notes',content:data.notes,trigger:'hover'});
		    /*line.draggable({ 
				containment: "parent",
				scroll: false ,
				grid: [ this.unitPerGridpx, this.unitPerGridpx ],
				
				stop: function(e){
					console.log(e);
					var offset = $(this).position();
					var yPos = offset.top;
					var hgt = parseInt(height);
					to = (yPos+hgt)/value;
					if(to<10) {
						to = "0"+parseInt(to);
					}
					var tomin = ((((yPos+hgt)%value)/unitPerGridpx)*unitPerGrid);
					if(tomin<10) {
						tomin = "0"+tomin;
					}
					to = to+":"+tomin;
					from = yPos/value;
					if(from<10) {
						from = "0"+parseInt(from);
					}
					var frommin = (((yPos%value)/unitPerGridpx)*unitPerGrid);
					if(frommin<10) {
						frommin = "0"+frommin;
					}
					from = from+":"+frommin;
					data.to = to;
					data.from = from;
					$(this).find("span").text(from+"to"+to);
					$(this).addClass('noclick');
				}
			});*/
		    return line;
		},
		handleOverlap : function(data) {
			for(var index=0;index<data.length;index++) {
				data[index].width = 95;
				data[index].left = 0.4;
			}
			for(var index=1;index<data.length;index++) {
				var currentStart = data[index].startTime.split(':');
				var currentStartTime = parseInt(currentStart[0], 10)*60+parseInt(currentStart[1], 10);
				var currentEnd = data[index].endTime.split(':');
				var currentEndTime = parseInt(currentEnd[0], 10)*60+parseInt(currentEnd[1], 10);
				var previousStart = data[index-1].startTime.split(':');
				var previousStartTime = parseInt(previousStart[0], 10)*60+parseInt(previousStart[1], 10);
				var previousEnd = data[index-1].endTime.split(':');
				var previousEndTime = parseInt(previousEnd[0], 10)*60+parseInt(previousEnd[1], 10);
				if(data[index].type!="Lu" && data[index].type!="L" && data[index].type!="O" 
					&& data[index-1].type!="Lu" && data[index-1].type!="L" && data[index-1].type!="O"
					&& previousEndTime >currentStartTime ||(currentStartTime == previousStartTime && (currentEndTime <= previousEndTime || currentEndTime > previousEndTime))) {
					var overlapCount = parseInt(95/Math.ceil(data[index-1].width));
					var counter = 0;
					for(var i=overlapCount;i>=0;i--) {
						data[index-i].width = (95/(overlapCount+1))-0.5;
						if(counter!=0) {
							data[index-i].left = (counter*(95/(overlapCount+1)))+0.5;
						} else {
							data[index-i].left = 0.4;
						}
						counter++;
					}
				}
			}
		}
	});
}(jQuery));