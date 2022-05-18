<%--
  Created by IntelliJ IDEA.
  User: matia
  Date: 04.01.2022
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Homophonic Cipher</title>
  </head>
  <body>
  <form action="Homophonic Servlet" method="post">
    <p>Text: <input type="text" size="20" name="Text"> </p>
    <p>Key seed: <input type="number" size="20" name="KeySeed"> </p>
    <input type="radio" id="ENCRYPT" name="choose action" value="ENCRYPT" checked>
    <label for="ENCRYPT">ENCODE</label><br>
    <input type="radio" id="DECRYPT" name="choose action" value="DECRYPT">
    <label for="DECRYPT">DECODE</label><br>
    <input type="radio" id="both" name="choose action" value="Both">
    <label for="both">BOTH</label><br><br>
    <input type="radio" id="HISTORY" name="choose action" value="HISTORY">
    <label for="HISTORY">CHECK HISTORY</label><br><br>
    <input type="submit" value="Perform the operation" />
    <br><br>
  </form>
  <form action="CookieCheck" method="post">
    <input type="submit" value="Check the cookies" />
  </form>
  </body>
</html>
