(function($) {
	
	"use strict";
 
	$.catalog = function(selector, target) {
		
		$(target + " :header").each(function(i,item){
	        var tag = $(item).get(0).localName;
	        $(item).attr("id","wow"+i);
	        $(selector).append('<a class="new'+tag+'" href="#wow'+i+'">'+$(this).text()+'</a></br>');
	        $(".newh1").css("margin-left",0);
	        $(".newh2").css("margin-left",5);
	        $(".newh3").css("margin-left",10);
	        $(".newh4").css("margin-left",15);
	        $(".newh5").css("margin-left",20);
	        $(".newh6").css("margin-left",25);
	      });
	};

})(jQuery);