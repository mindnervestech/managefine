(function($) {
	$.fn.extend({
		weekHorizontal:function(opts) {
			var defaults = {
				
			};
			var self = this;
			var opts = $.extend(defaults, opts);
			var o = opts;
			unitValueInMin = parseInt(o.unitValueInMin, 10);
			startTime = parseInt(o.startTime, 10);
			endTime = parseInt(o.endTime, 10);
			unitPerGrid = parseInt(o.gradationBetweenPerUnit, 10);
			unitPerGridpx = parseInt(o.gradationBetweenPerUnitpx, 10); // 1 hour = 60 min ; 60/12 == 5 min
			var scaleNo = endTime - startTime;
			if($("#horizontal-scheduler-left").length>0) {
				$("#horizontal-scheduler-left").remove();
			}
			if($("#horizontal-scheduler-center").length>0) {
				$("#horizontal-scheduler-center").remove();
			}
			if($("#horizontal-scheduler-right").length>0) {
				$("#horizontal-scheduler-right").remove();
			}
			var left = $("<span id='horizontal-scheduler-left' style='float:left;width:15%;height:100%;overflow:hidden;border-right:2px solid blue;'></span>");
			var center = $("<span id='horizontal-scheduler-center' style='float:left;width:80%;height:100%;overflow:auto;border-right:2px solid blue;'></span>");
			var right = $("<span id='horizontal-scheduler-right' style='float:left;width:4%;height:50px;border-bottom: 1px solid;'><span class='fa fa-caret-square-o-right' style='font-size: 50px;color: #5686ba;margin-left: 8px;'></span></span>");
			$(self).append(left,center,right);
			//$(self).css("height","484px");
			var unitTicketlabel = 0;
			setHeaders();
			center.bind("scroll",function(e) {
				left.scrollTop(e.currentTarget.scrollTop);
			});
			left.bind("click",function(e) {
				var u =(unitValueInMin/unitPerGrid)*unitPerGridpx;
				center.scrollLeft(center.scrollLeft()-u);
			});
			right.bind("click",function(e) {
				var u =(unitValueInMin/unitPerGrid)*unitPerGridpx;
				center.scrollLeft(center.scrollLeft()+u);
			});
			function setHeaders() {
				var leftHead = $("<div style='width:100%;position:relative;min-height:50px;'></div>");
				var leftArrow = $("<span class='horizontal-week-left-arrow' style='width:30%;float:left;height:50px;border-bottom: 1px solid;'><span class='fa fa-caret-square-o-left' style='font-size: 50px;color: #5686ba;margin-left: 5px;'></span></span>");
				var doctorLabel = $("<span class='horizontal-resource-label' style='color:white;font-size:14px;width:70%;float:left;height:50px;'>Staff View</span>");
				var rightArrow = $("<span class='horizontal-week-right-arrow' style='position:relative;height:50px;'></span>");
				var scale = $("<div class='horizontal-week-scale' style='position:relative;height:50px;'></div>");
				this.config = o;
				this.editFunction = o.editFunction;
				this.addFunction = o.addFunction;
				leftHead.append(leftArrow,doctorLabel);
				left.append(leftHead);
				center.append(scale);
				var i = 0;
				var start = o.startTime * 60;
				var end = o.endTime * 60;
				newTickLabel = "";
				var unit = 0;
				while (start <  end) {
					if ((start % unitValueInMin ) == 0) {
						unit = 0;
						newTickLabel = "<div class='tickLabelH'><div></div><span style='position:absolute;font-size:12px;'>"+start/60+":00" + "</span></div>";
						$(newTickLabel).css("margin-left", unitTicketlabel+"px" ).appendTo(scale);
					} else if ((start % (unitPerGrid*2)) == 0) {
						unit = unit+unitPerGrid;
						newTickLabel = "<div class='tickMajorH'><span>"+unit+"</span></div>";
						$(newTickLabel).css( "margin-left", unitTicketlabel+"px" ).appendTo(scale);
					} else if ((start % unitPerGrid) == 0) {
						unit = unit+unitPerGrid;
						newTickLabel = "<div class='tickMinorH'><span>"+unit+"</span></div>";
						$(newTickLabel).css( "margin-left", unitTicketlabel+"px" ).appendTo(scale);
					} 
					//main.append(scale);
					scale.addClass("hRule");
					unitTicketlabel = unitTicketlabel + unitPerGridpx;
					start = ( start + unitPerGrid );
				}
				scale.css({"width":unitTicketlabel+"px"});
			}
			var data = JSON.parse(opts.data);
			var o1 = opts;
			var index = 0;
			for(d in data) {
				if(data[d].data == "") {
					data[d].data = "[]";
					o1.data = "[]";
				} else {
					o1.data = JSON.stringify(data[d].data);
				}
				var doctor = $("<div style='position:relative;padding:0% 0% 0% 30%;height:"+opts.height+";text-align:center;border: 1px solid;'></div>");
				//var img = $("<img src='get-doctor-profile/"+datad][.id+".jpg' style='height:80px;width:95%;'/>");
				var name = $("<span style='display:inline-block;position:relative;top:10%;font-size:14px;'>"+data[d].name+"</span>");
				var elem = $("<div style='height:"+opts.height+";width:100%;position:relative;'></div>");
				center.append(elem);
				o1.showRuller = false;
				o1.loopIndex = index;
				o1.rullerHeight = unitTicketlabel+"px";
				elem.borderify(o1);
				left.append(doctor);
				doctor.append(name);
				index++;
			}
			var hr = (new Date()).getHours();
			if(hr>1) {
				hr -=1;
			}
			$("#horizontal-scheduler-center").scrollLeft(hr*((unitValueInMin/unitPerGrid)*unitPerGridpx));
			
			div.bind("scroll",function(e) {
                $("#horizontal-scheduler-left").scrollTop(e.currentTarget.scrollTop);
			});
        
        div.slimScroll({
                height:"100%",
                 color: '#00f'
      });
			
		},
	});
})(jQuery);