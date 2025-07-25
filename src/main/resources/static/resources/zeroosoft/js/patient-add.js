/*function calculateAge(birthday) { // birthday is a date
    var ageDifMs = Date.now() - birthday.getTime();
    var ageDate = new Date(ageDifMs); // miliseconds from epoch
    return Math.abs(ageDate.getUTCFullYear() - 1970);
}*/


$(".message").fadeOut(10000, "linear");

//Initialize Select2 Elements
$('.select2').select2();

//Date picker
$('#dob, #firstDoseDate, #secondDoseDate, #thirdDoseDate').datetimepicker({
    format: 'DD/MM/YYYY'
});
$('#regDate').datetimepicker({
    format: 'DD/MM/YYYY',
    defaultDate: new Date()
});
    
//Timepicker
$('#reportDeliveryTimeFrom').datetimepicker({
  //format: 'LT',
  format: 'HH:mm A',
  defaultDate: moment(new Date()).hours(8).minutes(0).seconds(0).milliseconds(0)
});

//Timepicker
$('#reportDeliveryTimeTo').datetimepicker({
  //format: 'LT',
  format: 'HH:mm A',
  defaultDate: moment(new Date()).hours(20).minutes(30).seconds(0).milliseconds(0)
});

//calculate age from DOBStr 
$('input[name=patient\\.patientDOBStr]').focusout(function() {// get the dob after enter is completed
	var dobStr = $("#patient\\.patientDOBStr").val();
	var dateMomentObject = moment(dobStr, "DD/MM/YYYY"); // 1st argument - string, 2nd argument - format
	//var dateObject = dateMomentObject.toDate(); // convert moment.js object to Date object
	
	var now = moment(new Date());
 	var years = moment().diff(dateMomentObject, 'years');
 	var months = moment.duration(now.diff(dateMomentObject)).months();
 	var days = moment.duration(now.diff(dateMomentObject)).days();
	
	var fullAge = years + "y " + months + "m "+ days +"d";
	console.log("Full Age " + fullAge);
 	/*$("#ageYear").val(years);
 	$("#ageMonth").val(months);
 	$("#ageDay").val(days);*/
 	
 	
 	$("#patient\\.patientAge").val(years);
 	
 	
	
	/*var ageDifMs = Date.now() - dateObject.getTime();
	console.log("Age diff " + ageDifMs);
    var ageDate = new Date(ageDifMs); // miliseconds from epoch
    var age = Math.abs(ageDate.getUTCFullYear() - 1970);
    
	console.log("Now" + Date.now());*/
    
 	//alert("dobstr "+ dobStr + "and date " + dateObject + " Age " + age);
 	//console.log("dobStr " + dobStr + "\tdob " + dateObject + " Age " + age);
 	//console.log("Full Age " + age + "yr " + ageDate.getUTCMonth() + "m "+ ageDate.getUTCDay() +"d");
 	
 	
 	
 	/*var now =Date.now();
	console.log("Now" + now);
	console.log("Now from moment " + moment().format('L'));
	
	console.log("full age from now " + moment().subtract(29, 'years').subtract(9, 'months').subtract(4, 'days').format('L'));*/
	
 	
 	/*var yr = moment().diff(dateMomentObject, 'years');
 	console.log(" Age yr " + yr);
 	console.log(" Age months " + moment().diff(dateMomentObject, 'months'));
 	console.log(" Age days " + moment().diff(dateMomentObject, 'days'));
 	
 	
 	
 	
 	
 	console.log(" D " + days);
 	console.log(" M " + months);*/
 	//console.log(" test " + moment(dateMomentObject).toNow());
 	
	
});


/* calculate DOB from age in year, month, days */
/*$('input[name=ageDay]').focusout(function() {// get the dob after enter is completed
	var dobStr = $("#patient\\.patientDOBStr").val();
	if(dobStr)
		alert("Clear date of birth field");
	else{
		
	var y = $("#ageYear").val();
	var m = $("#ageMonth").val();
	var d = $("#ageDay").val();
	
		//do a ajax call to get dob
	    $.ajax({
		  type : 'POST',
		  url : 'getDateofbirthFromYMD', // this could be found in PatientRegistrationController
		  data : {y, m, d},
		  success : function(result) {
			console.log(result);
			$("#patient\\.patientDOBStr").val(result);
		  }
	    });
	}
});*/


/*change reg fee according to collection type and add required fields*/
$(document).ready(
  function(){
	$('input[type=radio][name=sampleCollectionType]').change(function() {
		
		var testName = $("#testInfoEntity\\.testName").val();
		//var collectionType = this.value;
		
	    if (this.value == 'Home_Service') {
			
			$("#sampleCollectedByContainer").css("display", "block");
			$("#sampleCollectorId").focus();
			$("#sampleCollectorId").prop('required',true);
			
			$("#sampleCollectionFromContainer").css("display", "none");
			//$("#sampleCollectionFrom").prop('required',false);
					
			getAndsetRegFee(testName, this.value);
	    }
	    if (this.value == 'Hospital') {
			
			$("#sampleCollectionFromContainer").css("display", "block");
			$("#sampleCollectionFrom").focus();
			//$("#sampleCollectionFrom").prop('required',true);
			
			$("#sampleCollectedByContainer").css("display", "block");
			$("#sampleCollectorId").prop('required',true);
			
			getAndsetRegFee(testName, this.value);
	    }
	    if(this.value == 'General'){
			$("#sampleCollectedByContainer").css("display", "none");
			$("#sampleCollectorId").prop('required',false);
			
			$("#sampleCollectionFromContainer").css("display", "none");
			//$("#sampleCollectionFrom").prop('required',false);
			
			getAndsetRegFee(testName, this.value);
		}
	});
	
	function getAndsetRegFee(testName, collectionType){
		//do a ajax call to get reg fee 
		$.ajax({
			  type : 'POST',
			  url : 'getRegFeeByTestNameAndCollectionType', // this could be found in PatientRegistrationController
			  data : {testName, collectionType},
			  success : function(result) {
				console.log(result);
				
				$("#paymentEntity\\.regFee").val(result.regFee);
				$("#paymentEntity\\.netAmount").val(result.regFee);
				$("#paymentEntity\\.paidAmount").val(result.regFee);
				//$("#testInfoId").val(result.testInfoId);
				//$("#regFeeText").html(result.regFee);
				
				
			  }
		    });
	}

  }
  
 
  
);

/*function(){
	
  }*/
  
$('input[type=radio][name=identityType]').change(function() {
	console.log("Identity Type Changes");
	//var idType = $("#identityType").val();
	
    if (this.value == 'Others') {
		$("#otherIdTypeContainer").css("display", "block");
		$("#otherIdType").focus();
		$("#otherIdType").prop('required',true);
    }
    else{
    	$("#otherIdTypeContainer").css("display", "none");
		$("#otherIdType").prop('required',false);
	}
});


/*calculate total amount and default paid amount when discount is not null */
$('#paymentEntity\\.discount').bind('input', function() { 
    /*var discount = $(this).val() // get the current value of the input field.
    if(isNaN(discount))
    	discount = 0;
    // calculate total amount
    var netTotal =  $('#testInfoEntity\\.regFee').val() - discount;
    
    //set net amount to field
    $('#paymentEntity\\.netAmount').val(netTotal);
    //set paid amount to field
    $('#paymentEntity\\.paidAmount').val(netTotal);*/
    
    
	calculateTotalAmount();
});
	

/*calculate total amount and default paid amount when discount is not null */
$('#paymentEntity\\.regFee').bind('input', function() { 
	calculateTotalAmount();
});
  
function calculateTotalAmount() { 
	//console.log(" test " );
	var testName = $("#testInfoEntity\\.testName").val();
	console.log(" testName " + testName);
	
	if(testName != 'Dope'){
	    var discount = $('#paymentEntity\\.discount').val(); 
	    if(isNaN(discount)  || discount===''){
			discount = 0;
	    	console.log("discount " + discount);
		}
	    	
	    // calculate total amount
	    var regFee = $('#paymentEntity\\.regFee').val(); 
	    console.log("Fee " + regFee);
	    if(isNaN(regFee) || regFee===''){
	    	console.log("NAN or empty");
			alert("please enter reg. Fee to proceed");
		}
	    else{
	    	console.log("NAN false");
			var netTotal =  regFee - discount;
	    
	    	//set net amount to field
	    	$('#paymentEntity\\.netAmount').val(netTotal);
	    	//set paid amount to field
	    	$('#paymentEntity\\.paidAmount').val(netTotal);
		}
	}
    	
};
  
    
    
$('#district\\.districtId').change (function() {
	/*$('#saleQty').attr('readonly', true).css("background-color", "#eee").val(null);
	$('#addToSaleTable').attr('disabled', true);*/
	
    var districtId = this.value;
    
    $.ajax({
  	  type : 'POST',
  	  url : 'getSubdistirctsUnderDistrict', // this should be found in SubDistrictController
  	  data : {districtId},
  	  success : function(result) {
  		console.log("Result returned by ajax" , result);
  		
		$("#subDistrict\\.subDistrictId").empty();
		$("#subDistrict\\.subDistrictId").append('<option value="' + 0 +'"> Select One</option>');
		$.each(result, function (i) {
		    /*$.each(result[i], function (key, val) {
		        alert(key + val);
		    });*/
		     $("#subDistrict\\.subDistrictId").append(
				'<option value="' + result[i].subDistrictId +'">'+ result[i].subDistrictName + '</option>'
			 );
		});
		
		$("#subDistrict\\.subDistrictId").focus();
  		
  	  }
    });
});

/*** field validation */
$('#patient\\.patientMajorContact').bind('input', function() { 
    if($(this).val().length > 11 ) // get the current value of the input field.
    	alert("Contact number exceeds 11 digits");
});



$('#patient\\.patientDOBStr').bind('input', function() { 
    if($(this).val() ) // get the current value of the input field.
		$("#patient\\.patientAge").prop('required',true);
	else
		$("#patient\\.patientAge").prop('required',false);
});


/*** prevent form submit on enter and move cursor to next input*/
// register jQuery extension
jQuery.extend(jQuery.expr[':'], {
    focusable: function (el, index, selector) {
        return $(el).is('a, button, :input, [tabindex]');
    }
});

$(document).on('keypress', 'input,select', function (e) {
    if (e.which == 13) {
        e.preventDefault();
        // Get all focusable elements on the page
        var $canfocus = $(':focusable');
        var index = $canfocus.index(this) + 1;
        if (index >= $canfocus.length) index = 0;
        $canfocus.eq(index).focus();
    }
});

/*** end prevent form submit on enter and move cursor to next input*/




	
  function printFrame() {
	  //window.frames["printf"].focus();
	  var newWin = window.frames['printf'];
	  newWin.document.write('<body onload=window.print()> ' 
			  + 'This is a new page I inserted' 
			  + '<div class="card-header">'
               + '<h3 class="card-title"> rRT-PCR Registration</h3>'
             + '</div>'
			 + ' <div class="row col-lg-12 text-center" id="printableArea" style="padding-top:10px;"> '
		       + '      <table class="table" style="table-layout: fixed; width: 100%;"> '
		        + '     	<tr> '
		          + '   		<th class="text-center" colspan="4" style="border-top: none;"> <h2> MONEY RECEIPT </h2>  '
		         + '    		<span th:text="${reg?.testInfoEntity?.testName=="RAT"} ? "(RAT)" : "(rRT-PCR)" "> </span></th> '
		          + '   	</tr> '
		          + '   	<tr> '
		          + '   		<td> '
	            	+ ' 			<img th:src="|data:image/png;base64,${barcodeString}|" style="width:100%; height: 100px;"/> '
	            	+ '			<p class="text-center" style="margin: 0;" th:text="${reg.regId}">  </p> '
	            	+ ' 		</td> '
	            	+ ' 		<td colspan="3"> '
	            	+ ' 			<img th:src="|data:image/png;base64,${logoAsString}|" style="width:90%; height: 100px;"> '
	            	+ ' 		</td> '
		            + ' 	</tr> '
		           + '  </table> '
		            + ' <table class="table " style="padding-top:20px;"> '
		            	+ ' <tr> '
		            		+ ' <td>Reg Id:</td> '
		            		+ ' <th th:text="${reg.regId}"></th> '
		            		+ ' <td>Passport:</td> '
		            		+ ' <td th:text="${reg?.patient?.patientPassport}"></td> '
		            		+ ' <td>Contact:</td> '
		            		+ ' <th th:text="${reg?.patient?.patientMajorContact}"></th> '
		            		+ ' <td>Reg. Date: </td> '
		            		+ ' <td th:text="${reg.updateOn == null} ? '
		            		 + '  ${#temporals.format(reg?.regDate, "dd/MM/yyyy")} '
		            		 + '  : ${#temporals.format(reg.updateOn, "dd/MM/yyyy")}"></td> '
		            	+ ' </tr> '
		            	+ ' <tr> '
		            		+ ' <td>Name:</td> '
		            		+ ' <th th:text="${reg?.patient?.patientName}"></th> '
		            		+ ' <td>Age:</td> '
		            		+ ' <td th:text="${reg?.patient?.patientAge}"></td> '
		            		+ ' <td>Sex:</td> '
		            		+ ' <td th:text="${reg.patient.patientGender}"></td> '
		            		+ ' <td>Address </td> '
		            		+ ' <td th:text="${reg?.address?.address}"></td> '
		            	+ ' </tr> '
		           + '  </table> '
		            + ' <hr/> '
		            + ' <table class="table box-table" style="table-layout: fixed; width: 100%;"> '
		            	+ ' <tr class="mt-2"> '
		            		+ ' <th> <p class="boxer"> Sr No:</p></th> '
		            		+ ' <th> <p class="boxer"> Dept</p></th> '
		            		+ ' <th> <p class="boxer"> Test Name</p></th> '
		            		+ ' <th> <p class="boxer"> Sample Coll. Date</p></th> '
		            		+ ' <th> <p class="boxer"> Rep dt</p></th> '
		            		+ ' <th> <p class="boxer"> Total</p> </th> '
		            	+ ' </tr> '
		            	+ ' <tr class="mt-2"> '
		            		+ ' <td> <p class="boxer2"> 1</p></td> '
		            		+ ' <td> <p class="boxer2"> COVID-19</p></td> '
		            		+ ' <td> <p class="boxer2"> COVID-19</p></td> '
		            		+ ' <td> <p class="boxer2"> </p> </td> '
		            		+ ' <td> <p class="boxer2"> </p> </td> '
		            		+ ' <td> <p class="boxer2" th:text="${reg?.paymentEntity?.netAmount}"> </p> </td> '
		            	+ ' </tr> '
		            + ' </table> '
		             + '  <table class="table grossTable" style="table-layout: fixed; width: 100%;"> '
		            	+ ' <tr> '
		            		+ ' <td colspan="10" ></td> '
		            		+ ' <td colspan="3" >Gross Bill(TK) </td> '
		            		+ ' <td colspan="2"  th:text="${#numbers.formatDecimal(reg?.testInfoEntity?.regFee, 0, 0)}" ></td> '
		            	+ ' </tr>  '
		            	+ ' <tr>	 '
		            		+ ' <td colspan="10" ></td> '
		            		+ ' <td colspan="3">Discount (TK)</td> '
		            		+ ' <td colspan="2" th:text="${#numbers.formatDecimal(reg?.paymentEntity?.discount, 0, 0)}"  ></td> '
		            	+ ' </tr> '
		            	+ ' <tr>	 '
		            	+ ' 	<td colspan="10" ></td> '
		            		+ ' <td colspan="3">Net Bill (TK)</td> '
		            	+ ' 	<td colspan="2" th:text="${#numbers.formatDecimal(reg?.paymentEntity?.netAmount, 0, 0)}"></td> '
		            + ' 	</tr> '
		            	+ ' <tr>	 '
		            	+ ' 	<td colspan="10" ></td> '
		            	+ ' 	<td colspan="3">Paid(TK) </td> '
		            	+ ' 	<th colspan="2" th:text="${#numbers.formatDecimal(reg?.paymentEntity?.paidAmount, 0, 0)}"></th> '
		            	+ ' </tr> '
		            	+ ' <tr> '
		            	+ ' 	<td colspan="10" ></td> '
		            	+ ' 	<td colspan="3">Due(TK)</td> '
		            	+ ' 	<th colspan="2" th:text="${#numbers.formatDecimal(reg?.paymentEntity?.dueAmount, 0, 0)}" ></th> '
		            	+ ' </tr> '
		              + ' </table> '
		             + '  <table class="table bottomTable" style="table-layout: fixed; width: 100%;"> '
		            + ' 	<tr> '
		            	+ ' 	<td colspan="2">Received (TK):</td> '
		            	+ ' 	<th colspan="6"th:text="${amountInWord}"></th> '
		            + ' 		<th colspan="2" style="border: solid thin black !important;">User: <p th:text="${reg.createdBy}"> </p></th> '
		            + ' 	</tr> '
		            + ' 	<tr> '
		            + ' 		<td colspan="8" class="text-center"> Report delivery time:- 8:30 am to 8:00 pm</td> '
		            + ' 	</tr> '
		            + ' 	<tr> '
		            + ' 		<td colspan="8" class="text-center"> Authority will not responsible if the report is not collected within one month.</td> '
		            + ' 	</tr> '
		            + ' 	<tr> '
		            + ' 		<td colspan="8" class="text-center"> This is a computer generated document, does not require any signature.</td> '
		            + ' 	</tr> '
		           + '  	<tr> '
		          + '   		<td colspan="8" class="text-center"> Contact/Whatsapp: +8801883077569 </td> '
		            + ' 	</tr> '
		           + '  	<tr> '
		           + '  		<td colspan="8" class="text-center" style="font-family: "Noto Sans Bengali", sans-serif;"> কাউন্টার ত্যাগ করার পূর্বে আপনার মোবাইল নাম্বার ও তথ্য মিলিয়ে নিন ।</td> '
		            + ' 	</tr> '
		            + ' 	<tr > '
		            + ' 		<td th:if="${reg?.testInfoEntity?.testName=="Immigrant"}" colspan="8" class="text-center" style="font-family: "Noto Sans Bengali", sans-serif;"> বিশেষ দ্রষ্টব্যঃ সেম্পল প্রদানের পর আপনার বিদেশ যাত্রার আগ পর্যন্ত আইসোলেশনে থাকুন।</td> '
		            + ' 	</tr> '
		            + '   </table> '
	           + '  </div>'
			 + '</body>'
			 );
	  newWin.document.close();
	  //window.frames["printf"].print();
  }

