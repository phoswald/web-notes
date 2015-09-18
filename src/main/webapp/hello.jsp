<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="phoswald.webnotes.GreetingService" %>
<%@ page import="phoswald.webnotes.entities.Greeting" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello, World!</title>
    <link type="text/css" rel="stylesheet" href="stylesheets/hello.css"/>
</head>
<body>
    <h1>Greeting</h1>
    <p>Hello, World!</p>
    <p>The current time is <%= new Date() %>.</p>
    <p>REST <a href="rest/service/greetings">greetings</a> or <a href="rest/service/greeting/123">greeting/id</a> or <a href="rest/service/add?name=REST">add</a>.</p>
    <table>
    <%
    GreetingService service = new GreetingService();
    for(Greeting greeting : service.list()) {
        %>
        <tr>
            <td><%= greeting.getId() %></td>
            <td><%= greeting.getName() %></td>
            <td><%= greeting.getText() %></td>
        </tr>
        <%
    }
    %>
    </table>
</body>
</html>