<!doctype html>
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
      <h1><a href="/admin/welcome"><img src="/images/logo.png" /></a></h1>
    </div>
    <div class="right" style='height:100px'>
      <ul class="link-list right">
        <li><a href="#">Logout</a></li>
        <li></li>
      </ul>
    </div>
    <hr/>
  <!-- End Header and Nav -->    

    <!-- Nav Sidebar -->
    <!-- This is source ordered to be pulled to the left on larger screens -->
    <div class="two columns">
      <img src="/images/error.png">
    </div>
    
    <!-- Main Content Section -->
    <!-- This has been source ordered to come first in the markup (and on small devices) but to be to the right of the nav on larger screens -->
    <div class="nine columns">
      
      <div class="nine row columns"></div>    
      <div class="content">
        <h4>Due to some connectivity issue, you may see an error message. When you get an error message, please try again!</h4>
        <br/>
        <button class="k-button" type="button" onclick="window.history.back();">Go Back</button>
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