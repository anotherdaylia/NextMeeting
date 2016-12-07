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
    scheduler.config.readonly = true;

    scheduler.config.xml_date="%Y-%m-%d %H:%i";
    scheduler.config.api_date="%Y-%m-%d %H:%i";
    scheduler.config.use_select_menu_space=false;

    scheduler.locale.labels.section_title = "Event & Location"
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
    var eventURL = "/student/event/program/" + 
			       "/" + currentDate.getFullYear() +
			       "/" + (currentDate.getMonth()+1) ;
    scheduler.load(eventURL,"json");
    
    scheduler.attachEvent("onEventAdded", function(event_id,event_object){
        scheduler.deleteEvent(event_id);
    });
    
    scheduler.attachEvent("onClick", function (event_id, native_event_object){
        scheduler.showLightbox(event_id);
   });
   
  }