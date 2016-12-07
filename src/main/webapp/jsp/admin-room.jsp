<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<link rel="shortcut icon" href="/images/favicon.ico" />
    <title>NextMeeting | Manage Room</title>

    <!-- Kendo UI Web Style -->
    <link rel="stylesheet" href="/styles/kendo.common.min.css" />
    <link rel="stylesheet" href="/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="/styles/foundation.min.css">


    <!-- Kendo UI Web Scripts -->
    <script type="text/javascript" src="/js/jquery.min.js"> </script>
    <script type="text/javascript" src="/js/kendo.web.min.js"></script>
    <script type="text/javascript" src="/js/popRoom.js"></script>
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
        <li><a href="/admin/admins" class="menu-button size">Manage Admin</a></li>
        <li><a href="/admin/rooms" class="menu-button size selected">Manage Room</a></li>
        <li><a href="/admin/rules" class="menu-button size">Manage Rules</a></li>
        <li class="divider"></li>
        <li><a href="/admin/reservation" class="menu-button size">Student Reservation</a></li>
        <li class="divider"></li>
      </ul>
        
    </div>
    <!-- Main Content Section -->
    <!-- This has been source ordered to come first in the markup (and on small devices) but to be to the right of the nav on larger screens -->
    
    <form method="post" id="form">
    <div class="nine columns">
      <div class="nine columns"><h5>Room List</h5></div>
      <div class="two right"><button class="k-button margin" id="add-to-cart" type="button">+ New Room</button></div>       
      <hr>
     
      <!-- Success Message and Error Message -->
      <c:if test="${!empty successMessage}"><div class="success_area">Success!</div></c:if>
      <c:if test="${!empty errorMessage}"><div class="warning">${errorMessage}</div></c:if>
      <!-- Success Message and Error Message -->
      
      
      <c:if test="${!empty roomList}">
      <div class="mail_table content">
         <table class="list_table list_table_choose">
         <thead>
          <tr>
            <th class="row_1">Room#</th>
            <th class="row_4">Status</th>
            <th class="row_4">Action</th>
          </tr>
         </thead>
         <tbody>
         <c:forEach items="${roomList}" var="room">
         <tr>
         	<td>${room.name}</td>
              <td>${room.isActiveString}</td>
              <td>
              <c:choose>
              <c:when test="${room.isActive}">
              <button class="k-button onHold" data="${room.id}">On hold</button>
              <button class="k-button k-state-disabled">Release</button>
              </c:when>
              <c:otherwise>
              <button class="k-button k-state-disabled">On hold</button>
              <button class="k-button release" data="${room.id}">Release</button>
              </c:otherwise>
              </c:choose>
        	  <button class="k-button removeBtn" data-roomId="${room.id}" data-roomName="${room.name}" type="button">Delete</button>
              </td>
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
          <span class="title">&nbsp;New Room</span>
        </div><br>
        
        <div>
          <div class="k-edit-label"><label>Room #:</label></div>
          <div class="k-edit-field"><input type="text" name="roomName"></div>
          <div class="k-edit-label"><label>Status:</label></div>
          <div class="k-edit-field"><select id="status" name="statusOption">
                <option value="1">Available</option>
                <option value="2">On hold</option></select></div>
        </div>
        <hr/>
        <div id="popbtns" class="right">
          <button class="k-button" id="create">Create</button>
          <button class="k-button margin-right" onclick="closePop()" type="button">Cancel</button>
        </div>
      </div>
      
      <div id="cart3" class="cart">
        <div class="header"> 
          <span class="title"><strong>&nbsp;Delete room</strong></span>          
        </div><br>
        <div>&nbsp;&nbsp;Are you sure you want to delete room <b id="roomName"></b>?
        </div>
        <div id="roomId" style="display:none;"></div>
        <div class="right">
          <button class="k-button delete" onclick="closePop()" type="submit">Yes</button>
          <button class="k-button margin-right" onclick="closePop()" type="button">No</button>
        </div>
      </div>
      
    </form>
   </div> 
     
      <script type="text/javascript">
      $("#status").kendoDropDownList();
      </script>
      <script type="text/javascript">
      $(document).ready(
      function() {
    	  $("#create").click(
    	  function() {
    	  $("#form").attr("action","/admin/rooms/add");
    	  $("#form").submit();
     	 });
    	  
    	  $(".delete").click(
    		  function() {
    		  var roomId = document.getElementById("roomId").innerHTML;
        	  $("#form").attr("action","/admin/rooms/delete/"+roomId);
        	  $("#form").submit();
          });
    	  
    	  $(".release").click(
        		  function() {
            	  var roomId = $(this).attr("data");
            	  $("#form").attr("action","/admin/rooms/release/"+roomId);
            	  $("#form").submit();
              });
    	  
    	  $(".onHold").click(
        		  function() {
            	  var roomId = $(this).attr("data");
            	  $("#form").attr("action","/admin/rooms/onhold/"+roomId);
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
