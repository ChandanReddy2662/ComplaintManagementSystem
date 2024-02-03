package com.servlet.myservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String uname = req.getParameter("uname");
        String pass = req.getParameter("pass");
        PrintWriter op = res.getWriter();

        try {
            Users users = new Users();
            if(!users.isUserExists(uname)){
                users.insert(name, Integer.parseInt(age), uname, pass);
                users.close();
                res.sendRedirect("index.html");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("warning", "User Already Exists");
            res.sendRedirect("register.html");
            op.println("User Already Exists");
        }
    }
}
