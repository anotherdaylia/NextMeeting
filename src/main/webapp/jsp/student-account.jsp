<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<link rel="shortcut icon" href="/images/favicon.ico" />
    <title>NextMeeting</title>

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
      
      <div class="nine row columns"><h5>Change Password</h5></div>
      <div class="three columns"></div>       
      <hr>
      
      <!-- Success Message and Error Message -->
      <c:if test="${!empty successMessage}"><div class="success_area">${successMessage}</div></c:if>
      <c:if test="${!empty errorMessage}"><div class="warning">${errorMessage}</div></c:if>
      <!-- Success Message and Error Message -->
      
      <div class="mail_table content">
         <form namespace="/student/profile/change" method="post" id="form">
          <table cellspacing="0" cellpadding="0" class="login_table">
            <tr>
                <td class="text_status">Current Password:</td>
                <td><input type="password" name="currentPW"/></td>
            </tr>
            <tr>
                <td class="text_status">New Password:</td>
                <td><input type="password" name="newPW"/></td>
            </tr>
            <tr>
                <td class="text_status">Confirm Password:</td>
                <td><input type="password" name="confirmNewPW"/></td>
                <td><button class="k-button margin2" id="update">Change</button>
                </td>
            </tr>
        </table>
        </form>
      </div>
    </div>
    
    <script type="text/javascript">
      $(document).ready(
      function() {
    	  $("#update").click(
    	  	function() {
    	  	$("#form").attr("action","/student/profile/change/PW");
    	  	$("#form").submit();
     	 });
      });
      </script>
    
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
