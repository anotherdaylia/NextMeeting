<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="shortcut icon" href="/images/favicon.ico" />
  <title>NextMeeting | Calendar</title>

  <!-- Kendo UI Web Style -->
    <link rel="stylesheet" href="/styles/kendo.common.min.css" />
    <link rel="stylesheet" href="/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="/styles/foundation.min.css">

    <!-- Kendo UI Web Scripts -->
    <script type="text/javascript" src="/js/jquery.min.js"> </script>
    <script type="text/javascript" src="/js/kendo.web.min.js"></script>
    <script type="text/javascript" src="/js/pop.js"></script>
    <script type="text/javascript" src="/js/calendar.js"></script>

    <!-- dhtmlxscheduler -->
    <script src="/codebase/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
    <script src="/codebase/ext/dhtmlxscheduler_limit.js" type="text/javascript" charset="utf-8"></script>
    <!--block-->
    <script src="/codebase/ext/dhtmlxscheduler_collision.js"></script>
    <script src="/codebase/ext/dhtmlxscheduler_readonly.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="./codebase/dhtmlxscheduler.css" type="text/css" media="screen" title="no title" charset="utf-8">

    <!-- Terrace skin -->
    <link rel="stylesheet" href="/codebase/dhtmlxscheduler_dhx_terrace.css" type="text/css" media="screen" title="no title" charset="utf-8">
    <script src="/codebase/ext/dhtmlxscheduler_dhx_terrace.js" type="text/javascript" charset="utf-8"></script>  

<style type="text/css" media="screen">
  html, body{
    margin:0px;
    padding:0px;
    height:100%;
    overflow:hidden;
  }
  
  .dhx_cal_event_line.custom, .dhx_cal_event.custom div{
    background-color:#fd7;
    border-color:#da6;
    color:#444;
  }
</style>
</head>

<body>
    <div class="three columns">
      <h1><a href="/student/"><img src="/images/logo.png" /></a></h1>
    </div>
    <div class="right" style='height:100px'>
      <ul class="link-list right">
       <li><a href="/student/task">Task Schedule</a></li>
        <li><a href="/student/">Make Reservation</a></li>
        <li><a href="/student/teams">Join Team</a></li>
        <li><a href="/student/rules">Rules</a></li>
        <li><a href="/student/profile">Account</a></li>
        <li><a href="/logout">Logout</a></li>
        <li></li>
      </ul>
    </div>
    <hr/>

    <div class="two columns">
      <ul class="side-nav">
        <li><div>Room:</div>
            <div class="k-edit-field"><select id="roomnumber">
                <c:if test="${!empty rooms}">
                <c:forEach var="room" items="${rooms}">
                <option value = "${room.id}" <c:if test="${room.id==roomId}"> selected = "selected" </c:if>>${room.name} </option>
                </c:forEach>
                </c:if>
                </select>
            </div>
        </li>
       </ul>
       <div class="scroll">
       	<ul>
        <li class="text-center"><h5>My Reservations</h5></li>
        <c:if test="${!empty meetings}">
        <c:forEach var="meeting" items="${meetings}">
        <li class="divider"></li>
	        <p>Title: ${meeting.text}<br/>
	        Room: ${meeting.roomName} <br>
	        Start: ${meeting.start_date}<br>
	        End: ${meeting.end_date} <br>
	        <c:choose>
	            <c:when test="${meeting.isCheckIn}"> Checked</c:when>
	        <c:otherwise>
	            <a href="/student/checkin/${roomId}/${meeting.id}">Check-in</a>
	        </c:otherwise>
	        </c:choose>
	        </c:forEach>
	        </c:if>
	        <li class="divider"></li>
	     </ul>
	    </div>
    </div>
    <script type="text/javascript">
        $("#roomnumber").kendoDropDownList();
        $("#roomnumber").change(function(){
            var roomID = $("#roomnumber").val();
            window.location = "/student/" + roomID ;
        });  
    </script>

    <div class="nine columns">
      <!-- Success Message and Error Message -->
      <div class="success_area" id="successMessage" style="display:none;">Success!</div>
      <c:choose>
      <c:when test="${errorMessage==''}">
            <div class="warning" id="errorMessage" style="display:none;">Error!</div>
      </c:when>
      <c:otherwise>
            <div class="warning" id="errorMessage">${errorMessage}</div>
        </c:otherwise>
       </c:choose>     
      <!-- Success Message and Error Message -->
    </div>
    
    <div id="scheduler_here" class="dhx_cal_container right" style='width:80%; height:83%;'>
        <div class="dhx_cal_navline">
          <div class="dhx_cal_prev_button">&nbsp;</div>
          <div class="dhx_cal_next_button">&nbsp;</div>
          <div class="dhx_cal_today_button"></div>
          <div class="dhx_cal_date"></div>
          <div class="dhx_cal_tab dhx_cal_tab_first" name="day_tab" style="right:204px;"></div>
          <div class="dhx_cal_tab dhx_cal_tab_last" name="week_tab" style="right:204px;"></div>
        </div>
        <div class="dhx_cal_header">
        </div>
        <div class="dhx_cal_data">
        </div>
    </div>
</body>
</html>
