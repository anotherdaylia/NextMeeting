<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	 <link rel="shortcut icon" href="/images/favicon.ico" />
     <title>Next Meeting - eBusiness Room Reservation Official Site</title>
     
      
    <link rel="stylesheet" href="/styles/login.css" />
    <link rel="stylesheet" href="/styles/kendo.common.min.css" />
    <link rel="stylesheet" href="/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="/styles/foundation.min.css">

    <script type="text/javascript" src="/js/jquery.min.js"> </script>
    <script type="text/javascript" src="/js/kendo.web.min.js"></script>

</head>
<body>
<!-- Header and Nav -->

<div class="container">
    <div class="login_area">
    <div class="login_logo">MSIT eBusiness Technology Room Reservation Application</div>
        <div class="login_area_content">
        
        <div class="login_notice">
                <font size="4">${errorMessage}</font>
        </div>
        
        <form action="/login" namespace="/" method="post" id="form">
        <table cellspacing="0" cellpadding="0" class="login_table">
            <tr>
                <td class="text_status">Andrew ID:</td>
                <td><input type="text" name="andrewID" value="${andrewID}"/></td>
            </tr>
            <tr>
                <td class="text_status">Password:</td>
                <td><input type="password" name="password"/></td>
                <td><a id="logbtn" class="btn_login" >
                    <button class="k-button margin">Log In</button></a>
                </td>
            </tr>
            <tr>
                <td class="text_status"></td>
                <td class="text-right"> Not a member yet? </td>
                <td><a href="/student/register"> Register Now!</a></td>
            </tr>
           
        </table>
        </form>           
        </div>
        <div class="login_area_bottom"><div class="login_area_bottom_left"></div>Copyright © 2013 MSIT eBusiness Technology<div class="login_area_bottom_right"></div></div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(document).ready(function(){   
    $(document).keydown(function(event){
    event||(event=window.event);
    if(event.keyCode==13){
         $("#form").submit();
      }})
    }); 
       
   $("#logbtn").click(function(){        
      $("#form").submit();        
   }); 
</script>