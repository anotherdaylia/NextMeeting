<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<link rel="shortcut icon" href="/images/favicon.ico" />
    <title>NextMeeting | Manage Rules</title>

    <!-- Kendo UI Web Style -->
    <link rel="stylesheet" href="/styles/kendo.common.min.css" />
    <link rel="stylesheet" href="/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="/styles/foundation.min.css">


    <!-- Kendo UI Web Scripts -->
    <script type="text/javascript" src="/js/jquery.min.js"> </script>
    <script type="text/javascript" src="/js/kendo.web.min.js"></script>
    <script type="text/javascript" src="/js/poprules.js"></script>
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
        <li><a href="/admin/groups" class="menu-button size ">Manage Team</a></li>
        <li><a href="/admin/users" class="menu-button size">Manage Student</a></li>
        <li><a href="/admin/admins" class="menu-button size">Manage Admin</a></li>
        <li><a href="/admin/rooms" class="menu-button size">Manage Room</a></li>
        <li><a href="/admin/rules" class="menu-button size selected">Manage Rules</a></li>
        <li class="divider"></li>
        <li><a href="/admin/reservation" class="menu-button size">Student Reservation</a></li>
        <li class="divider"></li>
      </ul>
        
    </div>
    <!-- Main Content Section -->
    <!-- This has been source ordered to come first in the markup (and on small devices) but to be to the right of the nav on larger screens -->
    <div class="nine columns">
      
      <div class="nine columns"><h5>Rules List</h5></div>     
      <hr>

      <!-- Success Message and Error Message -->
      <c:if test="${!empty successMessage}"><div class="success_area">${successMessage}</div></c:if>
      <c:if test="${!empty errorMessage}"><div class="warning">${errorMessage}</div></c:if>
      <!-- Success Message and Error Message --> 
      
      <c:if test="${!empty ruleList}">
      <div class="mail_table content">
         <table class="list_table list_table_choose">
         <thead>
          <tr>
            <th class="row_1">Rule</th>
            <th class="row_4">Value</th>
            <th class="row_4">Description</th>
            <th class="row_4">Action</th>
          </tr>
         </thead>
         <tbody>
          <c:forEach items="${ruleList}" var="rule">
           <tr>
              <td>${rule.name}</td>
              <td>${rule.number}</td>
              <td>${rule.description }</td>
              <td><button class="k-button add-to-cart"  data-ruleId="${rule.id}" data-ruleValue="${rule.number}" data-ruleName="${rule.name}" type="button">Edit</button></td>
           </tr>
           </c:forEach>
         </tbody>
        </table>
      </div>
      </c:if>
    </div>

    <div id="pop-back"></div>
      <div id="cart">
        <div class="header">
          <span class="title"><strong>&nbsp;Edit Rules</strong></span>
        </div><br>
         <form id="form" method="post">
        <div>
          <div>
          <div class="k-edit-label"><label>Rule:</label></div>
          <div id="ruleid" class="k-edit-field"></div>
          <div class="k-edit-label"><label>Rule Name:</label></div>
          <div id="ruleName" class="k-edit-field"></div>
          <div class="k-edit-label"><label>Value:</label></div>
          <div class="k-edit-field"><input id="ruleValue" name ="ruleValue" type="text"></div>
        </div>
        </div>
        <hr/>
       
        <div id="popbtns" class="right">
          <button class="k-button" type="button" id="updateValue" >Update</button>
          <button class="k-button margin-right" onclick="closePop()" type="button">Cancel</button>
        </div>
        </form>
      </div>
     <script>pop();</script>
      <script type="text/javascript">
      $(document).ready(function() {
    	  $("#updateValue").click(function() {
    	  	var ruleId = document.getElementById("ruleid").innerHTML;
    	  	$("#form").attr("action","/admin/rules/modify/"+ruleId);
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
