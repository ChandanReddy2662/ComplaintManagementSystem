<!DOCTYPE html>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="com.servlet.myservlet.Complaints"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Site</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style/bootstrap.css">
    <style>
        header{
            height: 50px;
        }
        main{
            margin-top: 100px;
            height: 100%;
        }
        nav{
            height: 50px;
        }
        nav ul{
            height: 50px;
        }
    </style>
</head>
<!-- <body class="d-flex align-items-center justify-content-center vh-100 flex-column"> -->
<body>

    <header class="d-flex bg-gradient fixed-top bg-info-subtle w-100 align-items-center">
        <h1>Hello, <%= (String)session.getAttribute("uname")%>!</h1>
        <nav class="position-absolute end-0">
            <ul class="d-flex gap-3 list-unstyled justify-content-center align-items-center">
                <li><a href="" class="text-decoration-none text-black fs-4">Home</a></li>
                <li><a href="Logout" class="text-decoration-none text-black fs-4">Logout</a></li>
            </ul>
        </nav>
    </header>
    
    

    <main class="d-flex align-items-center justify-content-center flex-column w-100">
    <%
        Complaints complaints = new Complaints();

        String uname = (String)session.getAttribute("uname");
        ResultSet resultSet = complaints.complaintsOfUsers();
        
        try{
            if(resultSet.next()){
                resultSet.previous();
                out.println("<table  id=\"complaints\" class=\"table w-75 table-bordered table-striped table-hover table-responsive-md table-responsive-sm\">");
                out.println("<thead><tr><th>Complaint ID</th><th>Username</th><th>Date Of Complaint</th><th>Description</th><th>Status</th></tr></thead>");
                while(resultSet.next()){
                    out.println("<tr><td>" + resultSet.getString(1) + "</td>");
                    out.println("<td>" + resultSet.getString(5) + "</td>");
                    out.println("<td>" + resultSet.getString(3) + "</td>");
                    out.println("<td>" + resultSet.getString(2) + "</td>");
                    out.println("<td><select name=\"status\" id=\"status\"><option>" + resultSet.getString(4) + "</option><option>Completed</option>" + "</option></select></td></tr>");
                }
                out.println("</table>");
                resultSet.beforeFirst();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            out.println("in catch");
        }
        finally{
            complaints.close();
        }
    %>
</main>
    <script src="script/bootstrap.js"></script>
    <script>
    </script>
</body>
</html>