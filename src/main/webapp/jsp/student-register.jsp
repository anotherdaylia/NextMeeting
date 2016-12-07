<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<link rel="shortcut icon" href="/images/favicon.ico" />
    <title>NextMeeting | Register</title>
    <link rel="stylesheet" href="/styles/login.css" />
    <link rel="stylesheet" href="/styles/kendo.common.min.css" />
    <link rel="stylesheet" href="/styles/kendo.default.min.css" />
    <link rel="stylesheet" href="/styles/foundation.min.css">

    <script type="text/javascript" src="/js/jquery.min.js"> </script>
    <script type="text/javascript" src="/js/kendo.web.min.js"></script>

</head>
<body>
<!-- Header and Nav -->

<div class="register-container">
    <div class="register_area">
    <div class="register_logo">NextMeeting - MSIT eBusiness Technology</div>
        <div class="register_area_content">
        <form action="/student/register/submit" method="post" id="form">
        <table cellspacing="0" cellpadding="0" class="login_table">
            <tr>
                <td class="text_status"><span class="star">*</span>Andrew ID:</td>
                <td><input type="text" name="andrewId" value="${user.andrewId}" /></td>
            </tr>
            <tr>
                <td class="text_status"><span class="star">*</span>First Name:</td>
                <td><input type="text" name="firstname" value="${user.firstname}" /></td>
            </tr>
            <tr>
                <td class="text_status"><span class="star">*</span>Last Name:</td>
                <td><input type="text" name="lastname" value="${user.lastname}"/></td>
            </tr>
            <tr>
                <td class="text_status"><span class="star">*</span>Password:</td>
                <td><input type="password" name="password" value=""/></td>
            </tr>
            <tr>
                <td class="text_status"><span class="star">*</span>Confirm Password:</td>
                <td><input type="password" name="confirmPassword" value=""/></td>
            </tr>
            <tr>
                <td colspan="2"><strong>Room Reservation Rules:</strong></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="2"><!--s:password name="password" /-->
                <textarea style="width: 470px; height:100px; resize: none;" readonly>
1.  Rooms can only be used with advance reservations.
2.  Reservations can only be made for team meetings on behalf of a team.
3.  Rooms can be booked for no more than 3 hours/day in order to be fair to all students.
4.  Each team can only book rooms twice a week in time period 12:00pm - 6:00pm during weekdays. 
5.  Reservation check-in starts at 15 minute before the reservation start time, and ends at 15 minutes after the reservation start time. Room bookings not checked in or used within 15 minutes of the start time are forfeited and removed from the reservation.
6.  Please delete unneeded room bookings as a courtesy to others.
7.  Please clean up the room, turn lights off, close windows and shut door when you are done using the meeting room.
8.  Reservations can be deleted for the purpose of administration with prior notice.
                </textarea></td>
            </tr>
            <tr>
                <td colspan="2"><span class="star">*</span><input type="checkbox" <c:if test="${obey == 'check'}"> checked="true" </c:if> name="obey" value="check" style="width:15px;" />I understand that I should obey the rules when booking rooms.</td> 
            </tr>
            <tr>
                <td></td>
                <td class="btn_login">
                    <div class="right">
                    <button type="button" class="k-button" id="logbtn" >Register</button>
                    <button type="button" class="k-button" id="cancel">Cancel</button>
                    </div>
                </td>
            </tr>
        </table>
        </form>           
        </div>
        <div class="register_notice">
                <font size="4">${errorMessage}</font>
        </div>
        <div class="login_area_bottom"><div class="login_area_bottom_left"></div>Copyright© 2013 MSIT eBusiness Technology<div class="login_area_bottom_right"></div></div>
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
   $("#cancel").click(function(){        
	   window.location.href="/";   
	   }); 
</script>