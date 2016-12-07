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
      <h1><a href="/"><img src="/images/logo.png" /></a></h1>
    </div>
    <div class="right" style='height:100px'>
      <ul class="link-list right">
        <li><a href="/logout">Logout</a></li>
        <li></li>
      </ul>
    </div>
    <hr/>
  <!-- End Header and Nav -->    

    <!-- Nav Sidebar -->
    <!-- This is source ordered to be pulled to the left on larger screens -->
    <div class="two columns">
      <img src="/images/key.png">
    </div>
    
    <!-- Main Content Section -->
    <!-- This has been source ordered to come first in the markup (and on small devices) but to be to the right of the nav on larger screens -->
    <div class="nine columns">
      
      <div class="nine row columns"></div>    
      <div class="content">
        <h4>Sorry! You are not authorized to enter this page.</h4>
        <div class="rule_content">
          Please <a href="${redirectUrl}">Go Back</a> to main calendar page.
        </div>
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