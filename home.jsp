
<%@ page import="java.sql.ResultSet, com.servlet.myservlet.Complaints" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="style/bootstrap.css">
    <style>
        #raisecomplaint {
            display: none;
        }
        header{
            height: 50px;
        }
        main{
            margin-top: 80px;
            height: 100%;
        }
        thead{
            background-color: black;
            color: white;
        }
        nav ul{
            height: 50px;
        }
    </style>
</head>
<body>
    <header class="position-fixed top-0 w-100 d-flex bg-info-subtle">
        <h1>Hello, <%= (String)session.getAttribute("uname")%>!</h1>
        <nav class="position-absolute end-0">
            <ul class="d-flex gap-3 list-unstyled justify-content-center align-items-center">
                <li><a href="" class="text-decoration-none text-black fs-4">Home</a></li>
                <li><a href="Logout" class="text-decoration-none text-black fs-4">Logout</a></li>
            </ul>
        </nav>
    </header>
    
    <main class="d-flex align-items-center justify-content-center flex-column w-100 overflow-auto">
        <%
            String uname = (String)session.getAttribute("uname");
            Complaints complaints = new Complaints();
            ResultSet res = complaints.complaintsOfUser(uname);
            try{
                if(res.next()){
                    res.previous();
                    out.println("<table class=\"table table-bordered table-hover w-75 table-striped border border-3 shadow-lg\"><caption>Complaints</caption><thead class=\"thead-dark\"><tr><th>Complaint Id</th><th>Description</th><th>When raised</th><th>Status</th></tr></thead><tbody>");
                    while(res.next()) {
                        out.println("<tr><td>" + res.getString(1) + "</td>");
                        out.println("<td>" + res.getString(2) + "</td>");
                        out.println("<td>" + res.getString(3) + "</td>");
                        out.println("<td>" + res.getString(4) + "</td></tr>");   
                    }
                    out.println("</tbody></table>");
                }
                else{
                    out.println("<div><p>No Complaints Raised Yet</p></div>");
                }
                res.close();
            }
            catch(Exception e){
                e.printStackTrace();
                out.println("<br>Unable To Fetch Complaints");
            }
            finally{
                complaints.close();
            }
        %>

        <button id="raise" class="btn btn-dark">Raise Complaint</button>
        <div id="raisecomplaint">   
            <div id="raisecomplaintform" class="from-group border border-2 rounded p-3 m-3">
                <h1>Raise Complaint</h1>
                <form action="Complaint" method="get" id="comp" class="form" >
                    <label for="desc">Description: </label>
                    <input type="text" name="desc" id="desc" class="form-control" placeholder="Enter Description">
                    <small id="emailHelp" class="form-text text-muted">Describe your problem</small><br><br>
                    <input type="button" value="Raise" id="raiseBtn" onclick="submitDescription()" class="w-100 btn btn-primary">
                </form>
            </div>
        </div>
    </main>
    
    <script>

        function submitDescription(){
            var desc = document.getElementById('desc').value;
            if(desc == "")
             alert("Please enter a description!");
            else
                document.getElementById("comp").submit()
        }
        complaintToggle = false;
        document.getElementById('raise').addEventListener('click', () => {
            complaintToggle = !complaintToggle;
            if(complaintToggle)
                document.getElementById('raisecomplaint').style.display = 'block';
            else
                document.getElementById('raisecomplaint').style.display = 'none';
        });
    </script>

    <script src="script/bootstrap.js"></script>
</body>
</html>
