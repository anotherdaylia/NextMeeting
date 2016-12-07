<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<link rel="shortcut icon" href="/images/favicon.ico" />
    <title>NextMeeting | Rules</title>

    <!-- Kendo UI Web Style -->
    <link rel="stylesheet" href="/styles/kendo.common.min.css" />
    <link rel="stylesheet" href="/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="/styles/foundation.min.css">


    <!-- Kendo UI Web Scripts -->
    <script type="text/javascript" src="/js/jquery.min.js"> </script>
    <script type="text/javascript" src="/js/kendo.web.min.js"></script>
    <script type="text/javascript" src="/js/pop.js"></script>
</head>

<body>
  <!-- Header and Nav -->
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
  <!-- End Header and Nav -->    

    <!-- Nav Sidebar -->
    <div class="two columns">
      <img src="/images/calendar.png">
    </div>
    
    <!-- Main Content Section -->
    <!-- This has been source ordered to come first in the markup (and on small devices) but to be to the right of the nav on larger screens -->
    <div class="nine columns">
      
      <div class="nine row columns"><h5>Rules</h5></div>    
      <hr>

      <div class="content">
		<ol>
        <li>Rooms can only be used with advance reservations.</li>
        <li>Reservations can only be made for team meetings on behalf of a team.</li>
        <li>Rooms can be booked for no more than ${MAXHOUR} hours/day in order to be fair to all students.</li>
        <li>Each team can only book rooms ${MAXAFTERNOON} times per week in time period 12:00pm - 6:00pm.</li>
        <li>Reservation check-in starts at ${CHECKIN} minutes before the reservation start time, and ends at ${CHECKIN} minutes after the reservation start time. Room bookings not checked in or used within ${CHECKIN} minutes of the start time are forfeited and removed from the reservation.</li>
        <li>Please delete unneeded room bookings as a courtesy to others.</li>
        <li>Please clean up the room, turn lights off, close windows and shut door when you are done using the meeting room.</li>
        <li>Reservations can be deleted for the purpose of administration with prior notice.</li>
        <li>Acknowledge that NextMeeting is designed and developed by Kewei Wang, Jason Liang, and Lia Qu from MSIT eBusiness Program Class of 2013.</li>
      	</ol>
      </div>

    </div>
    
  <!-- Footer -->
  
  <footer>
    <div>
      <hr />
        <div class="six columns">
          <p>Copyright&nbsp;&copy 2013 MSIT eBusiness Technology</p>
        </div>
    </div> 
  </footer>

</body>
</html>