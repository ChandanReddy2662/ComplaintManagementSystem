package com.servlet.myservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/Login")
public class Login extends HttpServlet{

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        String uname = req.getParameter("uname");
        String pass = req.getParameter("pass");
        PrintWriter op = res.getWriter();
        res.setContentType("text/html");
        try {
            Users users = new Users();
            if(users.authenicate(uname, pass)){
                users.close();
                HttpSession session = req.getSession();
                session.setAttribute("uname", uname);
                if(uname.equals("admin"))
                {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("admin.jsp");
                    requestDispatcher.forward(req, res);
                }
                else{
                    res.sendRedirect("home.jsp");
                }
                
            }
            else
                op.println("<html>"+
                "<head>" +
                "<style> *{font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;}" +
                "body{display: flex; align-items:center; justify-content: center; height: 100vh; flex-direction: column;}" + 
                "h1, h2{text-align:center;} button{display: inline;} button:hover{background: green;}" +
                "a{text-decoration: none;}</style>" +
                "</head>" +
                "<h1>Wrong Username or Password</h1>" + 
                "<h1>Select</h1>" + 
                "<p> If Already Have An Account</p>" +
                "<a href=\"index.html\"><button>Login</button></a>" +
                "<p> Don't Have An ACcount</p>" +
                "<a href=\"register.html\"><button>Register</button></a>" + 
                "</html>");
        } catch (Exception e) {
            op.println(e);
        }
        op.close();
    }
}
