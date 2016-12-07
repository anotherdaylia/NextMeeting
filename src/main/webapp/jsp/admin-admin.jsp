<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<link rel="shortcut icon" href="/images/favicon.ico" />
    <title>NextMeeting | Manage Administration</title>

    <!-- Kendo UI Web Style -->
    <link rel="stylesheet" href="/styles/kendo.common.min.css" />
    <link rel="stylesheet" href="/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="/styles/foundation.min.css">


    <!-- Kendo UI Web Scripts -->
    <script type="text/javascript" src="/js/jquery.min.js"> </script>
    <script type="text/javascript" src="/js/kendo.web.min.js"></script>
    <script type="text/javascript" src="/js/popAdmin.js"></script>
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
        <li><a href="/admin/users" class="menu-button size">Manage Student</a></li>
        <li><a href="/admin/admins" class="menu-button size selected">Manage Admin</a></li>
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
      <div class="nine columns"><h5>Admin List</h5></div>
      <div class="two right"><button class="k-button margin" id="add-to-cart" type="button">+ New Admin</button></div>       
      <hr>
      
      <!-- Success Message and Error Message -->
      <c:if test="${!empty successMessage}"><div class="success_area">${successMessage}</div></c:if>
      <c:if test="${!empty errorMessage}"><div class="warning">${errorMessage}</div></c:if>
      <!-- Success Message and Error Message -->
      
      <c:if test="${!empty adminList}">
      <div class="mail_table content">
        <table class="list_table list_table_choose">
         <thead>
          <tr>
            <th class="row_1">Name</th>
            <th class="row_1">Username</th>
            <th class="row_1">Action</th>
          </tr>
         </thead>
         
         <tbody>
         <c:forEach items="${adminList}" var="admin">
           <tr>
              <td>${admin.firstname} , ${admin.lastname}</td>
              <td>${admin.andrewId }</td>
              <td><button class="k-button removeBtn" type="button" data-andrewId="${admin.andrewId }">Delete</button></td>
           </tr>
           </c:forEach>
         </tbody>
        </table>
      </div>
      </c:if>
    </div>
    
    <div id="pop-back"></div>
    
      <div id="cart1" class="cart">
        <div class="header"> 
          <span class="title"><strong>&nbsp;New Admin</strong></span>          
        </div><br>
        <div>
          <div class="k-edit-label"><label>User Name:</label></div>
          <div class="k-edit-field"><input type="text" name="userName"></div>
          <div class="k-edit-label"><label>First Name:</label></div>
          <div class="k-edit-field"><input type="text" name="firstName"></div>
          <div class="k-edit-label"><label>Last Name:</label></div>
          <div class="k-edit-field"><input type="text" name="lastName"></div>
        </div>
        <div class="right">
          <button class="k-button" type="submit" id="create">Create</button>
          <button class="k-button margin-right" onclick="closePop()" type="button">Cancel</button>
        </div>
      </div>
      
      <div id="cart3" class="cart">
        <div class="header"> 
          <span class="title"><strong>&nbsp;Delete admin</strong></span>          
        </div><br>
        <div>&nbsp;&nbsp;Are you sure you want to delete <b id="andrewId"></b>'s account?
        </div>
        <div class="right">
          <button class="k-button" id="delete" type="submit">Yes</button>
          <button class="k-button margin-right" onclick="closePop()" type="button">No</button>
        </div>
      </div>
      
      </form>
      
      <script type="text/javascript">
      $(document).ready(
      function() {
    	  $("#create").click(
    	  	function() {
    	  	$("#form").attr("action","/admin/admins/add");
    	  	$("#form").submit();
     	 });
    	  
    	  $("#delete").click(
        	  function() {
           	  var andrewId = document.getElementById("andrewId").innerHTML;
           	  $("#form").attr("action","/admin/admins/delete/"+andrewId);
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
