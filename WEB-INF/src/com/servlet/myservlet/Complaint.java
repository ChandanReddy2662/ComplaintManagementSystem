package com.servlet.myservlet;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Complaint")
public class Complaint extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String desc = req.getParameter("desc");
        LocalDateTime dateTime = LocalDateTime.now();
        HttpSession session = req.getSession();
        String uname = (String)session.getAttribute("uname");
        try {
            Complaints complaints = new Complaints();
            int raise = complaints.insert(desc, dateTime, uname);
            if(raise >= 0)
                res.sendRedirect("home.jsp");
            else
                res.getWriter().println("not raised");
        } catch (Exception e) {
            res.getWriter().println("unable to raise");
        }
    }
}
