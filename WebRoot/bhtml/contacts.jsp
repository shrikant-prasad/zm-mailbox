<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="zm" uri="com.zimbra.zm" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
 <head>
  <title>Zimbra: Contacts</title>
  <style type="text/css">
    @import url("style.css");
  </style>
 </head>

 <body>

  <! --------- TOP ------>
  <jsp:directive.include file="top.jsp" />

  <! --------- TABS ------>
  <table cellspacing=0 class=Tabs>
   <tr> 
    <td class='TabFiller'>
&nbsp;
    </td>
    <td class='Tab'>
<a href="clv.jsp">Mail</a>
    </td>

    <td class='TabSelected'>

<a href="contacts.jsp">Contacts</a>
    </td>
    <td class='Tab'>
<a href="">Calendar</a>
    </td>
    <td class='TabFiller'>
&nbsp;
    </td>
    <td class='Tab'>
<a href="">Options</a>
    </td>
   </tr>
  </table>

<zm:search var="searchResult" limit="25" query="in:contacts" offset="${param.offset}" types="contact" sort="nameAsc"/>
<c:set var="toolbar">
 <table cellspacing=0 class='Tb'>
   <tr>
<td align=left class=TbBt>
 <a href="">New</a>
 <a href="">Reply</a>
 <a href="">Reply All</a>
</td>
<td align=right>
 <c:if test="${param.offset > 0}">
   <a href="?offset=${searchResult.previousOffset}"><img src="images/arrows/LeftArrow.gif" border=0></a>
 </c:if>
 <span>${param.offset+1} - ${param.offset+searchResult.size}</span>
 <c:if test="${searchResult.hasMore}">
   <a href="?offset=${searchResult.nextOffset}"><img src="images/arrows/RightArrow.gif" border=0></a>
 </c:if>         	
</td>
</tr>
</table>
</c:set>     



  <! --------- LEFT/RIGHT ------>
  <table>
   <tr>
    <!------- LEFT -->
    <td valign=top style='width:150px'>
      <jsp:directive.include file="left.jsp" />
    </td>
    <!-- right -->
    <td valign='top'>
     <div class='RightTbTop'>${toolbar}</div>

<div class=List>
<table cellpadding=2 cellspacing=0 >

<tr>
<th width=1% nowrap>&nbsp;
<th width=1% nowrap>&nbsp;
<th>Name
<th>Email
</tr>

<c:forEach items="${searchResult.hits}" var="contact">
<tr>
<td width=1% nowrap><input type=checkbox name=t value="1"></td>
<td width=1% nowrap><img src="images/Contact.gif" alt=Contact></td>
<td width=40%><a href="contact.jsp?id=${contact.id}">${fn:escapeXml(contact.fileAsStr)}</a></td>
<td width=40%><c:if test="${empty contact.displayEmail}">&nbsp;</c:if>${fn:escapeXml(contact.displayEmail)}</td>

</tr>   
</c:forEach>

</table>
</div> <%-- list --%>
<div class='RightTbBottom'>${toolbar}</div>
<jsp:directive.include file="footer.jsp" />

    </td>
   </tr>
  </table>

 </body>

</html>
