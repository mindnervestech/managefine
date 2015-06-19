<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="menuContext.jsp" />
<div ng-app="ClientInfoApp" ng-controller="ClientInfoController">

	<h4>
		<b style="margin-left: 20px;"><i>Customer Info</i></b>
	</h4>
	
	<select
		class="col-md-3 ng-pristine ng-invalid ng-invalid-required ng-touched"
		data-ng-model="item.id" ng-change="viewdata(item.id)"
		 style="margin-left: 15px;"
		required="">
		<option value="">Select Customer</option>
		<option value="{{item.id}}" ng-repeat="item in clientlist">{{item.clientname}}</option>
		
		
		

	</select> <label style="margin-left: 2%; margin-bottom: -1px;"
		class="control-label" for="selectbasic" >From Date</label>
	<div data-provide="datepicker" class="input-append date datepicker"
		data-date="" data-date-format="dd-mm-yyyy"
		style="margin-top: -25px; margin-left: 72px;">
		<input style="width: 80%; height: 20px;" id="LeaveaddstartDateWindow"
			size="16" type="text" value="{{clientlist[0].fromdate}}" readonly="" name="startDateWindow"><span
			class="add-on"><i class="icon-calendar"></i></span>
	</div>

	<label style="margin-left: 52%; margin-top: -39px; margin-left: 545px;"
		class="control-label" for="selectbasic" >To Date</label>
	<div data-provide="datepicker" class="input-append date datepicker"
		data-date="" data-date-format="dd-mm-yyyy"
		style="margin-top: -34px; margin-left: 17px;">
		<input style="width: 29%; height: 20px; margin-left: 285px;"
			id="LeaveaddstartDateWindow" size="16" type="text" value="{{clientlist[0].todate}}"
			readonly="" name="startDateWindow"><span class="add-on"><i
			class="icon-calendar"></i></span>
	</div>


	<!-- <div class="tabs">
				<ul class="tab-links">
					<li class="active"><a href="#tab1" data-toggle="tab">Project</a></li>
					
					<li style="margin-left: 323px;font-size: 19px;color: #0044cc;margin-top: 6px;">sss</li>
				</ul>
				<div id="tab1" class="tab active">
				
					<li><a href="#tab2"  data-toggle="tab">Define Parts</a></li> 
					<li><a href="#tab3"  data-toggle="tab">History</a></li>
					<li style="margin-left: 323px;font-size: 19px;color: #0044cc;margin-top: 6px;">ss</li>
				</div>
				
			
	<div class="tab-content" style="max-height: 437px;">
					<div id="tab1" class="tab active">
					<label>hhhhhhh</label>
					</div></div>
					
<div class="tab-content" style="max-height: 437px;">
					<div id="tab2" class="tab">
					<label>h111hhhhhh</label>
					</div></div>
</div> -->

	<div class="container">

		<ul class="nav nav-tabs">
			<li><a data-toggle="tab" href="#home"
				style="background-color: lightgrey;">General</a></li>
			<li><a data-toggle="tab" href="#menu1"
				style="background-color: lightgrey;">Project</a></li>
			<li><a data-toggle="tab" href="#menu2"
				style="background-color: lightgrey;">Menu 2</a></li>
			<li><a data-toggle="tab" href="#menu3"
				style="background-color: lightgrey;">Menu 3</a></li>
		</ul>

		<div class="tab-content">
			<div id="home" class="tab-pane fade">
				<ul class="nav nav-tabs" style="background-color: lightgrey;">
					<li><a data-toggle="tab" href="#general">General
							Information</a></li>
					<li><a data-toggle="tab" href="#officeandfactory">Office
							and Factory </a></li>
					<li><a data-toggle="tab" href="#design">Design</a></li>

					<li><a data-toggle="tab" href="#account">Accounts</a></li>
					<li><a data-toggle="tab" href="#buyer">Buyer</a></li>
					<li><a data-toggle="tab" href="#owner">Owner,Directors</a></li>
					<li><a data-toggle="tab" href="#product">Product</a></li>

					<li><a data-toggle="tab" href="#turnoverandsale">Turnover
							and Sale</a></li>
					<li><a data-toggle="tab" href="#bankandsatutary">Banking
							and Satutary Information</a></li>
					<li><a data-toggle="tab" href="#others">Others</a></li>




				</ul>
			</div>


			<div id="menu1" class="tab-pane fade">
				<h6>shfkjsd</h6>
			</div>
			<div id="menu2" class="tab-pane fade"></div>
			<div id="menu3" class="tab-pane fade"></div>
		</div>

		<div class="tab-content">
			<div id="general" class="tab-pane fade">



				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					
					style="height: 400px; display: block;">
					<h3>Personal Info</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Customer Name


						</label>
						<div class="controls">


							<input id="CustomerclientName" name="clientName"
								class="input-large" type="text"  rel="popover" value="{{clientlist1.clientname}}"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Phone No </label>
						<div class="controls">



							<input id="CustomerphoneNo" name="phoneNo" class="input-large" value="{{clientlist1.phoneno}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Comman Email
							Id </label>
						<div class="controls">



							<input id="CustomerEmail" name="Email" class="input-large" value="{{clientlist1.email}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Street </label>
						<div class="controls">



							<input id="Customerstreet" name="street" class="input-large" value="{{clientlist1.street}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Country </label>
						<div class="controls">


							<input id="Customercountry" name="country" class="input-large" value="{{clientlist1.country}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">State </label>
						<div class="controls">



							<input id="Customerstate" name="state" class="input-large" value="{{clientlist1.state}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left:551px ; margin-top: -87px;">

						<label class="control-label" for="textinput">City </label>
						<div class="controls">



							<input id="Customercity" name="city" class="input-large" value="{{clientlist1.city}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left:816px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Sub location

						</label>
						<div class="controls">



							<input id="Customersublocation" name="sublocation" value="{{clientlist1.sublocation}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Pin </label>
						<div class="controls">


							<input id="Customerpin" name="pin" class="input-large" value="{{clientlist1.pin}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Region </label>
						<div class="controls">



							<input id="CustomerRegion" name="Region" class="input-large" value="{{clientlist1.region}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Website </label>
						<div class="controls">



							<input id="Customerwebsite" name="website" class="input-large" value="{{clientlist1.website}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">type </label>
						<div class="controls">



							<input id="Customertype" name="type" class="input-large" value="{{clientlist1.locality}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Customer Type


						</label>
						<div class="controls">


							<input id="Customercustomertype" name="customertype" value="{{clientlist1.customertype}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>

					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">segment </label>
						<div class="controls">



							<input id="Customersegment" name="segment" class="input-large" value="{{clientlist1.sagment}}"
								type="text" disabled="disabled">


						</div>
					</div>
					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Sub Segment </label>
						<div class="controls">



							<input id="Customersubsegment" name="subsegment" value="{{clientlist1.subsagment}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Number of
							Employees </label>
						<div class="controls">



							<input id="Customernoofemp" name="noofemp" class="input-large" value="{{clientlist1.noofemp}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Estabilshment
							year </label>
						<div class="controls">


							<input id="Customeryear" name="year" class="input-large" value="{{clientlist1.estabilshmentyear}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>

					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Company
							Registration Nos </label>
						<div class="controls">



							<input id="Customercompregno" name="compregno" value="{{clientlist1.companyregno}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Tier Large,
							Medium, Small </label>
						<div class="controls">



							<input id="Customerlms" name="lms" class="input-large" value="{{clientlist1.tier}}"
								type="text" disabled="disabled">


						</div>
					</div>

	<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Type of Company(Pvt Ltd,LLC,Public Ltd,Govt Enterprise, Semi Govt)  </label>
						<div class="controls">



							<input id="Customertypeofcompany" name="typeofcompany" class="input-large" value="{{clientlist1.typeofcompany}}"
								type="text" disabled="disabled">


						</div>
					</div>



				</div>




			</div>
			<div id="officeandfactory" class="tab-pane fade">
				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					style="height: 400px; display: block;">
					<h3>Office And Factory</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Office Address


						</label>
						<div class="controls">


							<input id="Customerofficeaddress" name="officeaddress" value="{{clientlist1.officeaddress}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Land line Nos of Office </label>
						<div class="controls">



							<input id="Customerlandlinenosofoffice" name="landlinenosofoffice" class="input-large" value="{{clientlist1.landlineno}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Factory Address
							</label>
						<div class="controls">



							<input id="Customerfactoryaddress" name="factoryaddress" class="input-large" value="{{clientlist1.factoryaddress}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Production Head Name</label>
						<div class="controls">



							<input id="Customerproductionheadname" name="productionheadname" class="input-large" value="{{clientlist1.productheadname}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Production Head Cell nos </label>
						<div class="controls">


							<input id="Customerproductionheadcellnos" name="productionheadcellnos" class="input-large" value="{{clientlist1.productheadcell}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Mail Id of Production  Head</label>
						<div class="controls">



							<input id="Customermailidofproductionhead" name="mailidofproductionhead" class="input-large" value="{{clientlist1.mailhead}}"
								type="text" disabled="disabled">


						</div>
					</div>

<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Average stay with Company 
							 </label>
						<div class="controls">



							<input id="Customeraveragestaywithcompany" name="averagestaywithcompany" class="input-large" value="{{clientlist1.avgfactory}}"
								type="text" disabled="disabled">


						</div>
					</div>


				</div>

			</div>

			<div id="design" class="tab-pane fade">
				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					style="height: 400px; display: block;">
					<h3>Design</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">R&D Head Name


						</label>
						<div class="controls">


							<input id="Customernamedesign1" name="namedesign1" value="{{clientlist1.randdname}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">R&D Head Cell
							nos </label>
						<div class="controls">



							<input id="Customercelldesign1" name="celldesign1" value="{{clientlist1.randdcell}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Mail Id of
							R&D Head </label>
						<div class="controls">



							<input id="Customeremaildesign1" name="emaildesign1" value="{{clientlist1.mailrandd}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Average stay
							with Company </label>
						<div class="controls">



							<input id="Customeraveragestaydesign1" name="averagestaydesign1" value="{{clientlist1.avgranddfirst}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">R&D Second
							Name </label>
						<div class="controls">


							<input id="Customernamedesign2" name="namedesign2" value="{{clientlist1.randdsecondname}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">R&D Second
							Cell nos </label>
						<div class="controls">



							<input id="Customercelldesign2" name="celldesign2" value="{{clientlist1.randdsecondcell}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left:551px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Mail Id of
							R&D Second Head </label>
						<div class="controls">



							<input id="Customeremaildesign2" name="emaildesign2" value="{{clientlist1.mailranddsecond}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Average stay
							with Company </label>
						<div class="controls">



							<input id="Customeraveragestaydesign2" name="averagestaydesign2" value="{{clientlist1.avgranddsecond}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

				</div>
			</div>

			<div id="account" class="tab-pane fade">
				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					style="height: 400px; display: block;">
					<h3>Account</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Accounts Head
							Name </label>
						<div class="controls">


							<input id="Customernameaccount1" name="nameaccount1" value="{{clientlist1.accountname}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Accounts Head
							Cell nos </label>
						<div class="controls">



							<input id="Customercellaccount1" name="cellaccount1" value="{{clientlist1.accountcell}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Mail Id of
							Accounts Head </label>
						<div class="controls">



							<input id="Customeremailaccount1" name="emailaccount1" value="{{clientlist1.mailaccount}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Accounts
							Second Name </label>
						<div class="controls">



							<input id="Customernameaccount2" name="nameaccount2" value="{{clientlist1.accountsecondname}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Accounts
							Second Cell nos </label>
						<div class="controls">


							<input id="Customercellaccount2" name="cellaccount2" value="{{clientlist1.accountsecondcell}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Mail Id of
							Accounts Second Head </label>
						<div class="controls">



							<input id="Customeremailaccount2" name="emailaccount2" value="{{clientlist1.mailaccountsecond}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

				</div>
			</div>

			<div id="buyer" class="tab-pane fade">
				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					style="height: 400px; display: block;">
					<h3>Buyer</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Purchase Head
							Name </label>
						<div class="controls">


							<input id="Customernamebuyer1" name="namebuyer1" value="{{clientlist1.purchasename}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Purchase Head
							Cell nos </label>
						<div class="controls">



							<input id="Customercellbuyer1" name="cellbuyer1" value="{{clientlist1.purchasecell}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Mail Id of
							Purchase Head </label>
						<div class="controls">



							<input id="Customeremailbuyer1" name="emailbuyer1" value="{{clientlist1.mailpurchase}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Average stay
							with Company </label>
						<div class="controls">



							<input id="Customeraveragestaybuyer1" name="averagestaybuyer1" value="{{clientlist1.avgbuyer}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Purchase
							Second Head Name </label>
						<div class="controls">


							<input id="Customernamebuyer" name="namebuyer2" value="{{clientlist1.purchasesecondname}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Purchase
							Second Head Cell nos </label>
						<div class="controls">



							<input id="Customercellbuyer2" name="cellbuyer2" value="{{clientlist1.purchasesecondcell}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Mail Id of
							Second Purchase Head </label>
						<div class="controls">



							<input id="Customeremailbuyer2" name="emailbuyer2" value="{{clientlist1.mailpurchasesecond}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left:816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Average stay
							with Company </label>
						<div class="controls">



							<input id="Customeraveragestaybuyer" name="averagestaybuyer" value="{{clientlist1.avgpurchasesecond}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">overseas
							supplier 1 </label>
						<div class="controls">


							<input id="Customeroverseassupplier1" name="overseassupplier1" value="{{clientlist1.overseassupplier1}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">overseas
							supplier 2 </label>
						<div class="controls">



							<input id="Customeroverseassupplier2" name="overseassupplier2" value="{{clientlist1.overseassupplier2}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">overseas
							supplier 3 </label>
						<div class="controls">



							<input id="Customeroverseassupplier3" name="overseassupplier3" value="{{clientlist1.overseassupplier3}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">overseas
							supplier 4 </label>
						<div class="controls">



							<input id="Customeroverseassupplier4" name="overseassupplier4" value="{{clientlist1.overseassupplier4}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">overseas
							supplier 5 </label>
						<div class="controls">


							<input id="Customeroverseassupplier5" name="overseassupplier5" value="{{clientlist1.overseassupplier5}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>

					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput"> Local
							supplier 1 </label>
						<div class="controls">



							<input id="Customerlocalsupplier1" name="localsupplier1" value="{{clientlist1.localsupplier1}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>
					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput"> Local
							supplier 2 </label>
						<div class="controls">



							<input id="Customerlocalsupplier2" name="localsupplier2" value="{{clientlist1.localsupplier2}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput"> Local
							supplier 3 </label>
						<div class="controls">



							<input id="Customerlocalsupplier3" name="localsupplier3" value="{{clientlist1.localsupplier3}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput"> Local
							supplier 4 </label>
						<div class="controls">


							<input id="Customerlocalsupplier4" name="localsupplier4" value="{{clientlist1.localsupplier4}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>

					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput"> Local
							supplier 5 </label>
						<div class="controls">



							<input id="Customerlocalsupplier5" name="localsupplier5" value="{{clientlist1.localsupplier5}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


				</div>
			</div>

			<div id="owner" class="tab-pane fade">
				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					style="height: 400px; display: block;">
					<h3>Owner and Directors</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput"> Director
							1Name </label>
						<div class="controls">


							<input id="Customername1" name="name1" class="input-large" value="{{clientlist1.director1name}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Director 1
							Cell nos </label>
						<div class="controls">



							<input id="Customercellno1" name="cellno1" class="input-large" value="{{clientlist1.director1cell}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Mail Id of
							Director 1 Head </label>
						<div class="controls">



							<input id="Customeremail1" name="email1" class="input-large" value="{{clientlist1.maildirector1}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Expert 1 </label>
						<div class="controls">



							<input id="Customerexpert1" name="expert1" class="input-large" value="{{clientlist1.expert1}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Qualification


						</label>
						<div class="controls">


							<input id="Customerqualification1" name="qualification1" value="{{clientlist1.qualification1}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Age of
							Director 1 </label>
						<div class="controls">



							<input id="Customerageofdirector1" name="ageofdirector1" value="{{clientlist1.agedirector1}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Director work
							with before co. estabilshment </label>
						<div class="controls">



							<input id="Customerdirectorwork" name="directorwork " value="{{clientlist1.directorwork}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput"> Director 2
							Name </label>
						<div class="controls">



							<input id="Customername2" name="name2" class="input-large" value="{{clientlist1.director2name}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Director 2
							Cell nos </label>
						<div class="controls">


							<input id="Customercellno2" name="cellno2" class="input-large" value="{{clientlist1.director2cell}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Mail Id of
							Director 2 Head </label>
						<div class="controls">



							<input id="Customeremail2" name="email2" class="input-large" value="{{clientlist1.maildirector2}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Expert </label>
						<div class="controls">



							<input id="Customerexpert2" name="expert2" class="input-large" value="{{clientlist1.expert2}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Qualification

						</label>
						<div class="controls">



							<input id="Customerqualification2" name="qualification2" value="{{clientlist1.qualification2}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Age of
							Director 2 </label>
						<div class="controls">


							<input id="Customerageofdirector2" name="ageofdirector2" value="{{clientlist1.agedirector2}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>

					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Director
							3Name </label>
						<div class="controls">



							<input id="Customername3" name="name3" class="input-large" value="{{clientlist1.director3name}}"
								type="text" disabled="disabled">


						</div>
					</div>
					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Director 3
							Cell nos </label>
						<div class="controls">



							<input id="Customercellno3" name="cellno3" class="input-large" value="{{clientlist1.director3cell}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Mail Id of
							Director 3 Head </label>
						<div class="controls">



							<input id="Customeremail3" name="email3" class="input-large" value="{{clientlist1.maildirector3}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Expert </label>
						<div class="controls">


							<input id="Customerexpert3" name="expert3" class="input-large" value="{{clientlist1.expert3}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>

					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Qualification

						</label>
						<div class="controls">



							<input id="Customerqualification" name="qualification" value="{{clientlist1.qualification}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Age of
							Director 3 </label>
						<div class="controls">



							<input id="Customerageofdirector3" name="ageofdirector3" value="{{clientlist1.agedirector3}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>





				</div>
			</div>

			<div id="product" class="tab-pane fade">
				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					style="height: 400px; display: block;">
					<h3>Product</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Project 1 </label>
						<div class="controls">


							<input id="Customerp1" name="p1" class="input-large" type="text" value="{{clientlist1.product1}}"
								rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Project 2 </label>
						<div class="controls">



							<input id="Customerp2" name="p2" class="input-large" type="text" value="{{clientlist1.product2}}"
								disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Project 3 </label>
						<div class="controls">



							<input id="Customerp3" name="p3" class="input-large" type="text" value="{{clientlist1.product3}}"
								disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Project 4 </label>
						<div class="controls">



							<input id="Customerp4" name="p4" class="input-large" type="text" value="{{clientlist1.product4}}"
								disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Project 5 </label>
						<div class="controls">


							<input id="Customerp5" name="p5" class="input-large" type="text" value="{{clientlist1.product5}}"
								rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Project 6 </label>
						<div class="controls">



							<input id="Customerp6" name="p6" class="input-large" type="text" value="{{clientlist1.product6}}"
								disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Project 7 </label>
						<div class="controls">



							<input id="Customerp7" name="p7" class="input-large" type="text" value="{{clientlist1.product7}}"
								disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left:816px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Project 8 </label>
						<div class="controls">



							<input id="Customerp8" name="p8" class="input-large" type="text" value="{{clientlist1.product8}}"
								disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Project 9 </label>
						<div class="controls">


							<input id="Customerp9" name="p9" class="input-large" type="text" value="{{clientlist1.product9}}"
								rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Project 10 </label>
						<div class="controls">



							<input id="Customerp10" name="p10" class="input-large" value="{{clientlist1.product10}}"
								type="text" disabled="disabled">


						</div>
					</div>






				</div>
			</div>

			<div id="turnoverandsale" class="tab-pane fade">
				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					style="height: 400px; display: block;">
					<h3>Turnover And Sale</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Turnover FY
							2012 </label>
						<div class="controls">


							<input id="Customerproject2012" name="project2012" value="{{clientlist1.turnover2012}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Turnover FY
							2013 </label>
						<div class="controls">



							<input id="Customerproject2013" name="project2013" value="{{clientlist1.turnover2013}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Turnover FY
							2014 </label>
						<div class="controls">



							<input id="Customerproject2014" name="project2014" value="{{clientlist1.turnover2014}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Projected
							Turnover for FY 2015 </label>
						<div class="controls">



							<input id="Customerproject2015" name="project2015" value="{{clientlist1.turnover2015}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Projected
							Turnover for FY 2016 </label>
						<div class="controls">


							<input id="Customerproject2016" name="project2016" value="{{clientlist1.turnover2016}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Projected
							Turnover for FY 2017 </label>
						<div class="controls">



							<input id="Customerproject2017" name="project2017" value="{{clientlist1.turnover2017}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left:551px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Sales Head
							Name </label>
						<div class="controls">



							<input id="Customersalesheadname" name="salesheadname" value="{{clientlist1.salesname}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left:816px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Average price
							of Product </label>
						<div class="controls">



							<input id="Customeraveragepriceofproduct" value="{{clientlist1.avgproduct}}"
								name="averagepriceofproduct" class="input-large" type="text"
								disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Pursuing
							Approval </label>
						<div class="controls">


							<input id="Customerpursuingapproval" name="pursuingapproval" value="{{clientlist1.pursuingapprovals}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>







				</div>
			</div>



			<div id="bankandsatutary" class="tab-pane fade">
				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					style="height: 400px; display: block;">
					<h3>Bank And Satutary Information</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Bank 1 </label>
						<div class="controls">


							<input id="Customerbank1" name="bank1" class="input-large" value="{{clientlist1.bank1}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Bank 2 </label>
						<div class="controls">



							<input id="Customerbank2" name="bank2" class="input-large" value="{{clientlist1.bank2}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Bank 3 </label>
						<div class="controls">



							<input id="Customerbank3" name="bank3" class="input-large" value="{{clientlist1.bank3}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">limit </label>
						<div class="controls">



							<input id="Customerlimit" name="limit" class="input-large" value="{{clientlist1.limit}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">VAT No </label>
						<div class="controls">


							<input id="Customervatno" name="vatno" class="input-large" value="{{clientlist1.vatno}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">CST No </label>
						<div class="controls">



							<input id="Customercstno" name="cstno" class="input-large" value="{{clientlist1.cstno}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left:551px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Excsie No </label>
						<div class="controls">



							<input id="Customerexcsieno" name="excsieno" class="input-large" value="{{clientlist1.excsieno}}"
								type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left:816px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Excsie Range

						</label>
						<div class="controls">



							<input id="Customerexcsierange" name="excsierange " value="{{clientlist1.excsierange}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Excsie
							Division </label>
						<div class="controls">


							<input id="Customerexcsiedivision" name="excsiedivision" value="{{clientlist1.excsiediv}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Excsie
							Commisionrate </label>
						<div class="controls">



							<input id="Customerexcsiecommisionrate"
								name="excsiecommisionrate" class="input-large" type="text" value="{{clientlist1.excsiecomm}}"
								disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Pan No </label>
						<div class="controls">



							<input id="Customerpanno" name="panno" class="input-large" value="{{clientlist1.panno}}"
								type="text" disabled="disabled">


						</div>
					</div>






				</div>
			</div>

			<div id="others" class="tab-pane fade">
				<div class="wizard-card" data-cardname="CustomerPersonal Info"
					data-validate="form_Customer_add_wizard"
					style="height: 400px; display: block;">
					<h3>Others</h3>












					<input id="Customerid" name="id_hidden" class="wizardhidden"
						type="hidden" value="">


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Future
							Product </label>
						<div class="controls">


							<input id="Customerfutureproduct" name="futureproduct" value="{{clientlist1.futureproduct}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Benchmark </label>
						<div class="controls">



							<input id="Customerbenchmark" name="benchmark" value="{{clientlist1.benchmark}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">Competitor
							No1 </label>
						<div class="controls">



							<input id="Customercompetitorno1" name="competitorno1" value="{{clientlist1.competitorno1}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Competitor
							No2 </label>
						<div class="controls">



							<input id="Customercompetitorno2" name="competitorno2" value="{{clientlist1.competitorno2}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Competitor
							No3 </label>
						<div class="controls">


							<input id="Customercompetitorno3" name="competitorno3" value="{{clientlist1.competitorno3}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Organazation
							Chart </label>
						<div class="controls">



							<input id="Customerorganazationchart" name="organazationchart" value="{{clientlist1.organazationchat}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left:551px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Average age
							of Employee </label>
						<div class="controls">



							<input id="Customeraverageageemp" name="averageageemp" value="{{clientlist1.avgofemp}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>


					<div class="control-group"
						style="height: 75px; margin-left:816px ; margin-top: -87px;">

						<label class="control-label" for="textinput">Average stay
							with Company </label>
						<div class="controls">



							<input id="Customeraveragestayarc" name="averagestayarc" value="{{clientlist1.avgarchiechture}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Contact
							Person </label>
						<div class="controls">


							<input id="Customercontactperson" name="contactperson" value="{{clientlist1.cantactname}}"
								class="input-large" type="text" rel="popover"
								disabled="disabled">



						</div>
					</div>


					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Designation </label>
						<div class="controls">



							<input id="Customerdesignation" name="designation" value="{{clientlist1.designation}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 551px; margin-top: -87px;">

						<label class="control-label" for="textinput">E-mail </label>
						<div class="controls">



							<input id="Customeremailaddress" name="emailaddress" value="{{clientlist1.emailaddrerss}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="height: 75px; margin-left: 816px; margin-top: -87px;">

						<label class="control-label" for="textinput">Tel No. </label>
						<div class="controls">



							<input id="Customertelno" name="telno" class="input-large" value="{{clientlist1.telno}}"
								type="text" disabled="disabled">


						</div>
					</div>

					<div class="control-group"
						style="float: left; width: 26%; margin: 0px; height: 75px;">


						<label class="control-label" for="textinput">Hp No. </label>
						<div class="controls">


							<input id="Customerhpno" name="customerhpno" class="input-large" value="{{clientlist1.hpno}}"
								type="text" rel="popover" disabled="disabled">



						</div>
					</div>

					<div class="control-group" style="height: 75px;">

						<label class="control-label" for="textinput">Navision Id </label>
						<div class="controls">



							<input id="Customernavisionid" name="navisionid" value="{{clientlist1.navisionid}}"
								class="input-large" type="text" disabled="disabled">


						</div>
					</div>
				</div>
			</div>


		</div>
	</div>
</div>

<script type="text/javascript" src='<c:url value="/resources/customScripts/clientInfoController/app.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/customScripts/clientInfoController/controller.js"/>'></script>


<style>


.nav>li>a, .nav>li>p {
  color: #FCFBF8;
}
label,input,button,select,textarea {
	font-size: 12px;
	font-weight: normal;
	line-height: 21px;
}

.uneditable-input {
	display: inline-block;
	height: 17px;
	padding: 4px 6px;
	margin-bottom: 0px;
	font-size: 14px;
	line-height: 20px;
	color: #555555;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	vertical-align: middle;
}

.nav > li > a:hover, .nav > li > a:focus {
  text-decoration: none;
  background-color:blue;
}
.nav-tabs > li > a:hover, .nav-tabs > li > a:focus {
  border-color: #000000#000000#000000;
}
</style>



