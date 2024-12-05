package com.servlet.myservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;

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
        res.getWriter().println("this is get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("In Update");
        System.out.println(req.getReader().toString());
        Complaints complaints = new Complaints();

        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String payload = sb.toString();
        HashMap<String, String> map = new HashMap<>();
        for(String s: payload.split("&")){// /Complaint?&status=completed&complaintid=aidsbfidsnf
            String[] s2 = s.split("=");
            map.put(s2[0], s2[1]);
        }
        String complaintId = map.get("complaintId");
        String status = map.get("status");

        try{
            int stat = complaints.complete(complaintId);
            System.out.println(stat);
            res.setStatus(200);
            System.out.println("Updated successfully");
            res.getWriter().println("Complaint updated successfully.");
        }
        catch(Exception e){
            res.setStatus(500);
            System.out.println(e.getMessage());
        }
    }
}
