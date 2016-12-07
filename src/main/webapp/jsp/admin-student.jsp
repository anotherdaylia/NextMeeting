<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<link rel="shortcut icon" href="/images/favicon.ico" />
    <title>NextMeeting | Manage Student</title>

    <!-- Kendo UI Web Style -->
    <link rel="stylesheet" href="/styles/kendo.common.min.css" />
    <link rel="stylesheet" href="/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="/styles/foundation.min.css">


    <!-- Kendo UI Web Scripts -->
    <script type="text/javascript" src="/js/jquery.min.js"> </script>
    <script type="text/javascript" src="/js/kendo.web.min.js"></script>
    <script type="text/javascript" src="/js/pop2.js"></script>
</head>
<body>
  
  <!-- Header and Nav -->
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
  
  <!-- End Header and Nav -->
  

  <div class="content">    
  <hr/>

    <!-- Nav Sidebar -->
    <!-- This is source ordered to be pulled to the left on larger screens -->
    <div class="two columns">
        
      <ul class="side-nav">
        <li class="text-center"><h5>Menu</h5></li>
        <li class="divider"></li>
        <li><a href="/admin/welcome" class="menu-button size">Manage Task</a></li>
        <li><a href="/admin/groups" class="menu-button size">Manage Team</a></li>
        <li><a href="/admin/users" class="menu-button size selected">Manage Student</a></li>
        <li><a href="/admin/admins" class="menu-button size">Manage Admin</a></li>
        <li><a href="/admin/rooms" class="menu-button size">Manage Room</a></li>
        <li><a href="/admin/rules" class="menu-button size">Manage Rules</a></li>
        <li class="divider"></li>
        <li><a href="/admin/reservation" class="menu-button size">Student Reservation</a></li>
        <li class="divider"></li>
      </ul>
        
    </div>
    <!-- Main Content Section -->
    <!-- This has been source ordered to come first in the markup (and on small devices) but to be to the right of the nav on larger screens -->
    <form id="form" method="post">
    
    <div class="nine columns">
      
      <div class="seven columns"><h5>Student List</h5></div>
      <div class="four columns"><button class="k-button margin" id="add-to-cart" type="button" style="margin-left:50px;">Proved Students</button>
                                <button class="k-button margin removeBtn right" type="button">Remove Students</button></div>       
      <hr>
      
      <!-- Success Message and Error Message -->
      <c:if test="${!empty successMessage}"><div class="success_area">${successMessage}</div></c:if>
      <c:if test="${!empty errorMessage}"><div class="warning">${errorMessage}</div></c:if>
      <!-- Success Message and Error Message -->
      
      <div class="mail_table content">
        <table class="list_table list_table_choose">
         <thead>
          <tr>
            <th class="row_1">Name</th>
            <th class="row_1">Andrew ID</th>
            <th class="row_1">Team Name</th>
            <th class="row_1">Action</th>
          </tr>
         </thead>
         
         <c:if  test="${!empty studentList}">
         
         <tbody>
          <c:forEach items="${studentList}" var="student">
           <tr>
              <td>${student.firstname},${student.lastname}</td>
              <td>${student.andrewId}</td>
              <td>${student.group.name}</td>
              <td><button class="k-button resetBtn" data-andrewId="${student.andrewId}"  type="button">Reset Password</button></td>
           </tr>
           </c:forEach>
         </tbody>
         </c:if>
        </table>
      </div>
    </div>
      
	  <div id="cart1" class="cart">
        <div class="header"> 
          <span class="title"><strong>&nbsp;Authorized Student List</strong></span>          
        </div><br>
        <div>
          <div class="k-edit-label"><label>Student List:</label></div>
          <div class="k-edit-field"><textarea name="andrewList">${andrewList}</textarea></div>
        </div>
        <div class="right">
          <button class="k-button" type="button" id="update">Update</button>
          <button class="k-button margin-right" onclick="closePop()" type="button">Cancel</button>
        </div>
      </div>

      <div id="cart2" class="cart">
        <div class="header"> 
          <span class="title"><strong>&nbsp;Reset password</strong></span>          
        </div><br>
        <div>&nbsp;&nbsp;&nbsp;Are you sure you want to reset <b id="studentAndewId"></b>'s password?
        </div>
        <div class="right">
          <button class="k-button" type="button" id="resetPW">Yes</button>
          <button class="k-button margin-right" onclick="closePop()" type="button">No</button>
        </div>
      </div>
      
      <div id="cart3" class="cart">
        <div class="header"> 
          <span class="title"><strong>&nbsp;Remove Student</strong></span>          
        </div><br>
        <div>&nbsp;&nbsp;Are you sure you want to delete all students?
        </div>
        <div class="right">
          <button class="k-button" id="deleteStudent" type="button">Yes</button>
          <button class="k-button margin-right" onclick="closePop()" type="button">No</button>
        </div>
      </div> 
      
      <div id="pop-back"></div> 
      </form>
	
      <script type="text/javascript">
      $(document).ready(
      function() {
    	  $("#update").click(
    	  function() {
    	  $("#form").attr("action","/admin/users/update");
    	  $("#form").submit();
     	 });
    	  
       	$("#deleteStudent").click(
        function() {
       	$("#form").attr("action","/admin/users/deleteAll");
       	$("#form").submit();
        });
       	
       	$("#resetPW").click(
       	function() {
       	var studentAndrewId = document.getElementById("studentAndewId").innerHTML;
       	$("#form").attr("action","/admin/users/resetPW/"+studentAndrewId);
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
