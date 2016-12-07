function pop(){
	var $popBack = $("#pop-back");
	var $cart = $("#cart");
	$popBack.hide();
	$cart.hide();
	$(".add-to-cart").click(function(){
		var ruleid = $(this).attr("data-ruleId");
		var value = $(this).attr("data-ruleValue");
		var ruleName = $(this).attr("data-ruleName");
		document.getElementById("ruleid").innerHTML=ruleid;
		document.getElementById("ruleName").innerHTML=ruleName;
		document.getElementById("ruleValue").value=value;

		$cart.show();
		$popBack.fadeIn("1000");
	});
	$popBack.click(function(){
		closePop();
	});
	$("#cart .x").click(function(){
		closePop();
	});
}

function closePop(){
	var $popBack = $("#pop-back");
	var $cart = $("#cart");
	$cart.fadeOut("1000");
	$popBack.hide();
}