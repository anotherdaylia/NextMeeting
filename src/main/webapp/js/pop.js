function pop(){
	var $popBack = $("#pop-back");
	var $cart = $("#cart");
	$popBack.hide();
	$cart.hide();
	$("#add-to-cart").click(function(){
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