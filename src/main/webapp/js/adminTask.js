$(document).ready(function() {
init();
});
	function init() {
   
    scheduler.config.multi_day = true;
    scheduler.config.drag_move = false;
    scheduler.config.icons_select=['icon_details', 'icon_delete'];
    scheduler.config.details_on_dblclick = true;
    
    scheduler.config.first_hour = 8;
    scheduler.config.last_hour = 22;
    scheduler.config.time_step = 30;
    scheduler.config.event_duration = 60;
    scheduler.config.start_on_monday = false;
    scheduler.config.limit_time_select = true;
    scheduler.config.collision_limit = 1;
    scheduler.config.mark_now = true;

    scheduler.config.xml_date="%Y-%m-%d %H:%i";
    scheduler.config.api_date="%Y-%m-%d %H:%i";
    scheduler.config.use_select_menu_space=false;

    scheduler.locale.labels.section_title = "Event& Location"
    scheduler.config.lightbox.sections=[
    {name:"title", height:50, map_to:"text", type:"textarea" , focus:true},
    {name:"time", height:72, type:"time", map_to:"auto"}]
    
    function block_readonly(id){
      if (!id) return true;
      return !this.getEvent(id).readonly;
    }
    scheduler.attachEvent("onBeforeDrag",block_readonly);
    scheduler.attachEvent("onClick",block_readonly);
    
    scheduler.templates.event_class=function(s,e,ev){ return ev.custom?"custom":""; };
    scheduler.init('scheduler_here', new Date(), "month");

    var currentDate = scheduler.getState().date;
    var eventURL = "/admin/event/program/" + 
			       "/" + currentDate.getFullYear() +
			       "/" + (currentDate.getMonth()+1) ;
    scheduler.load(eventURL,"json");
    
    scheduler.attachEvent("onEventAdded", function(event_id,event_object){
        
        scheduler.changeEventId(event_id, -1);
        var ev = scheduler.getEvent(-1);
        var start_date = ev.start_date;
        var end_date = ev.end_date;

        var title = ev.text;
        var year = start_date.getFullYear();
        var month = start_date.getMonth()+1;
        var day = start_date.getDate();
        var sh = start_date.getHours();
        var sm = start_date.getMinutes();
        var eh = end_date.getHours();
        var em = end_date.getMinutes(); 
        
        var isOccupied = scheduler.checkCollision(ev);
        if(!isOccupied){ 
        	document.getElementById('errorMessage').innerHTML = "The time slot is occupied!"; 
        }
            
        $.ajax({
          async: false,
          type: "POST",
          url: "/admin/event/program/add/" ,
          data: {
            year: year,
            month: month,
            day: day,
            sh: sh,
            sm: sm,
            eh: eh,
            em: em,
            title: title
          },
          success: function(result){
              if(result.id == 0 ){
                  scheduler.deleteEvent(-1);
                  $("#successMessage").css("display", "none");
                  $("#errorMessage").css("display", "block");
                  document.getElementById('errorMessage').innerHTML = result.text;
              }else{
                  scheduler.changeEventId(-1, result.id);
                  $("#errorMessage").css("display", "none");
                  $("#successMessage").css("display", "block");
                  document.getElementById('successMessage').innerHTML = "You have successfully created a Event!";
              }     
            },
	      error: function(result){
	    	  $("#successMessage").css("display", "none");
              $("#errorMessage").css("display", "block");
              document.getElementById('errorMessage').innerHTML = result.text;
	      },
          dataType:  "json"
        });

    });

    scheduler.attachEvent("onEventChanged", function(event_id,event_object){
      var ev = scheduler.getEvent(event_id);
      var start_date = ev.start_date;
      var end_date = ev.end_date;

      var title = ev.text;
      var year = start_date.getFullYear();
      var month = start_date.getMonth()+1;
      var day = start_date.getDate();
      var sh = start_date.getHours();
      var sm = start_date.getMinutes();
      var eh = end_date.getHours();
      var em = end_date.getMinutes(); 
      var isSuccess = false;
      $.ajax({
          async: false,
          type: "POST",
          url: "/admin/event/program/update/" + event_id ,
          data: {
        	  year: year,
              month: month,
              day: day,
              sh: sh,
              sm: sm,
              eh: eh,
              em: em,
              title: title
          },
          success: function(result){
        	  if(result.id == 0 ){
                  $("#successMessage").css("display", "none");
                  $("#errorMessage").css("display", "block");
                  document.getElementById('errorMessage').innerHTML = result.text;
      
              }else{
                  $("#errorMessage").css("display", "none");
                  $("#successMessage").css("display", "block");
                  document.getElementById('successMessage').innerHTML = "You have successfully changed a event!";
                  isSuccess = true;
              }        
          },
          dataType:  "json"
        });
        return isSuccess;
    });

    scheduler.attachEvent("onConfirmedBeforeEventDelete", function(event_id){
      var isSuccess = true;
      if(event_id > 0){
    	  $.ajax({
              async: false,
              type: "POST",
              url: "/admin/event/program/delete/" + event_id ,
              success: function(result){
            	  if(result.id == 0 ){ 
            		  $("#successMessage").css("display", "none");
                      $("#errorMessage").css("display", "block");
                      document.getElementById('errorMessage').innerHTML = result.text;
                      isSuccess = false;
            	  } else {
		        	  $("#successMessage").css("display", "block");
		        	  document.getElementById('successMessage').innerHTML = "You have successfully deleted a Event!";
		        	  isSuccess = true;
            	  }
              },
           });
      }
      return isSuccess;
    });

    scheduler.attachEvent("onViewChange", function (mode , date){
    	$("#successMessage").css("display", "none");
        $("#errorMessage").css("display", "none");
    	
        var currentDate = scheduler.getState().date;
        var eventURL = "/admin/event/program/" + 
    			       "/" + currentDate.getFullYear() +
    			       "/" + (currentDate.getMonth()+1) ;
        scheduler.load(eventURL,"json");
    });
    
    $("#errorMessage").ajaxError(function(){
    	$("#successMessage").css("display", "none");
        $("#errorMessage").css("display", "block");
        document.getElementById('errorMessage').innerHTML = "An error occurred!";
    });
    
  }