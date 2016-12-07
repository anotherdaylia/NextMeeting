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
  <script type="text/javascript" src="/js/adminTask.js"></script>

  <!-- dhtmlxscheduler -->
  <script src="/codebase/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
  <script src="/codebase/ext/dhtmlxscheduler_limit.js" type="text/javascript" charset="utf-8"></script>
  <script src="/codebase/ext/dhtmlxscheduler_dhx_terrace.js" type="text/javascript" charset="utf-8"></script>
  <!--block-->
  <script src="/codebase/ext/dhtmlxscheduler_collision.js"></script>
  <script src="/codebase/ext/dhtmlxscheduler_readonly.js" type="text/javascript" charset="utf-8"></script>  
  <link rel="stylesheet" href="/codebase/dhtmlxscheduler_dhx_terrace.css" type="text/css" media="screen"
      title="no title" charset="utf-8">
</head>

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

<body >
    <div class="three columns">
      <h1><a href="/admin/welcome"><img src="/images/logo.png" /></a></h1>
    </div>
    <div class="right">
      <ul class="link-list right">
        <li><a href="/admin/account">Account</a></li>
        <li><a href="/logout">Logout</a></li>
        <li></li>
      </ul>
    </div>
    <hr/>
    <div class="two columns">
        
      <ul class="side-nav">
        <li class="text-center"><h5>Menu</h5></li>
        <li class="divider"></li>
        <li><a href="/admin/welcome" class="menu-button size selected">Manage Task</a></li>
        <li><a href="/admin/groups" class="menu-button size">Manage Team</a></li>
        <li><a href="/admin/users" class="menu-button size">Manage Student</a></li>
        <li><a href="/admin/admins" class="menu-button size">Manage Admin</a></li>
        <li><a href="/admin/rooms" class="menu-button size">Manage Room</a></li>
        <li><a href="/admin/rules" class="menu-button size">Manage Rules</a></li>
        <li class="divider"></li>
        <li><a href="/admin/reservation" class="menu-button size">Student Reservation</a></li>
        <li class="divider"></li>
      </ul>
        
    </div>

    <div class="nine columns">
      <!-- Success Message and Error Message -->
      <div class="success_area" id="successMessage" style="display:none;">Success!</div>
      <div class="warning" id="errorMessage" style="display:none;">Error!</div>
      <!-- Success Message and Error Message -->
    </div>
    
    <div id="scheduler_here" class="dhx_cal_container right" style='width:78%; height:83%;'>
        <div class="dhx_cal_navline">
          <div class="dhx_cal_prev_button">&nbsp;</div>
          <div class="dhx_cal_next_button">&nbsp;</div>
          <div class="dhx_cal_today_button"></div>
          <div class="dhx_cal_date"></div>
        </div>
        <div class="dhx_cal_header">
        </div>
        <div class="dhx_cal_data">
        </div>
    </div>

    
</body>
