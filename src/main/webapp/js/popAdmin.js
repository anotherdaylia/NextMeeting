$(document).ready(function() {
  $("#pop-back").hide();
	$(".cart").hide();
	$("#add-to-cart").click(function(){
		$("#cart1").show();
		$("#pop-back").fadeIn("1000");
	});
	$("#pop-back").click(function(){
		closePop();
	});
	$("#cart1 .x").click(function(){
		closePop();
	});

	
	$(".removeBtn").click(function(){
		var andrewId = $(this).attr("data-andrewId");
		document.getElementById("andrewId").innerHTML=andrewId;
		$("#cart3").show();
		$("#pop-back").fadeIn("1000");
	});
	$("#cart3 .x").click(function(){
		closePop();
	});

});

function closePop(){
	$(".cart").hide();
	$("#pop-back").hide();
}
