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
      <img src="/images/calendar.png">
    </div>

    <div class="nine columns">
      
      <div class="nine row columns"><h5>Team List</h5></div>
      <div class="two columns"></div>       
      <hr>
      <form id ="form">
      <!-- Success Message and Error Message -->
      <div class="success_area" id="successMessage" style="display:none;">Success!</div>
      <div class="warning" id="errorMessage" style="display:none;">Error!</div>
      <!-- Success Message and Error Message -->
      
      <c:if test="${!empty groupList}">
      <div class="mail_table content">
         <table class="list_table list_table_choose">
         <thead>
          <tr>
            <th class="row_1">Team Name</th>
            <th class="row_4">Team member</th>
            <th class="row_1">Action</th>
          </tr>
         </thead>
         <tbody>
         <c:forEach items="${groupList}" var="group">
         <tr>
         	<td>${group.name}</td>
         	<td><c:forEach items="${group.users}" var="user">
         	${user.firstname},${user.lastname}    
         	</c:forEach>
         	</td>
         	<td>
         	<c:if test="${currentGroup == -1 }"><button class="k-button join" data-groupId="${group.id}"> Join </button></c:if>
            <c:if test="${currentGroup == -1 }"><button class="k-button k-state-disabled"> Quit </button></c:if>
            <c:if test="${currentGroup!=-1}"><button class="k-button k-state-disabled" > Join </button></c:if>
            <c:if test="${currentGroup==group.id}"><button class="k-button quit"> Quit </button></c:if>
            <c:if test="${currentGroup!=group.id && currentGroup != -1}"><button class="k-button k-state-disabled"> Quit </button></c:if>
              </td>
         </tr>
         </c:forEach>
         </tbody>
        </table>
      </div>
      </c:if>
      </form>
    </div>
    
     <script type="text/javascript">
      $(document).ready(
      function() {
    	 
    	  
    	  $(".quit").click(
    		  function() {
        	  $("#form").attr("action","/student/teams/quit");
        	  $("#form").submit();
          });
    	  
    	  $(".join").click(
        		  function() {
            	  var groupId = $(this).attr("data-groupId");
            	  $("#form").attr("action","/student/teams/join/"+groupId);
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
