package com.servlet.myservlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.Date;

public class Complaints{
    private final static String DATABASE = "jdbc:mysql://localhost:3306/servlets";
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String USERNAME = "acr123";
    private final static String PASSWORD = "acr123";

    private final static String COLUMN_COMPLAINT_ID = "complaint_id";
    private final static String COLUMN_DESCRIPTION = "description";
    private final static String COLUMN_WHEN_RAISED = "when_raised";
    private final static String COLUMN_UNAME = "uname";

    private Connection conn;
    private PreparedStatement prep;
    private Statement stat;

    public Complaints(){
        try {
            Class.forName(DRIVER);
            this.conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println("Unable to Connect");
        }
    }

    public int insert(String desc, LocalDateTime whenRaised, String uname){
        int res = -1;
        String insertComplaint = "INSERT INTO complaints(" +  COLUMN_COMPLAINT_ID + "," + 
        COLUMN_DESCRIPTION + ", " + COLUMN_WHEN_RAISED + ", " + COLUMN_UNAME +") values(?, ?, ?, ?)";
        String complaint_id = uname + whenRaised.getYear() + whenRaised.getMonthValue() + whenRaised.getDayOfMonth() + whenRaised.getHour() + whenRaised.getMinute() + whenRaised.getSecond() + whenRaised.getNano();

        try {
            Date date = Date.valueOf(whenRaised.getYear() + "-" + whenRaised.getMonthValue() + "-" + whenRaised.getDayOfMonth());
            this.prep = this.conn.prepareStatement(insertComplaint);
            this.prep.setString(2, desc);
            this.prep.setDate(3, date);
            this.prep.setString(4, uname);
            this.prep.setString(1, complaint_id);
            res = this.prep.executeUpdate();
            this.prep.close(); 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable To Insert");
            res = -2;
        }
        return res;
    }

    public int delete(String complaint_id){
        int res = -1;

        try {
            this.stat = this.conn.createStatement();
            String delete = "delete from complaints where " + COLUMN_COMPLAINT_ID + "='" + complaint_id + "'";
            res = this.stat.executeUpdate(delete);
            this.stat.close();
        } catch (Exception e) {
            System.out.println("Unable To Insert");
            res = -2;
        }
        return res;
    }

    public ResultSet complaintsOfUser(String uname){
        ResultSet resultSet = null;
        try {
            this.stat = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String complaints = "SELECT * FROM complaints where uname='" + uname + "'";
            resultSet = this.stat.executeQuery(complaints);
            System.out.println("in Complaints " + resultSet.isClosed());

        } catch (Exception e) {
            System.out.println("Cannot Retrive");
            resultSet = null;
        }
        return resultSet;
    }

    public ResultSet complaintsOfUsers(){
        ResultSet resultSet = null;
        try {
            this.stat = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String complaints = "SELECT * FROM complaints";
            resultSet = this.stat.executeQuery(complaints);
            System.out.println("in Complaints " + resultSet.isClosed());

        } catch (Exception e) {
            System.out.println("Cannot Retrive");
            resultSet = null;
        }
        return resultSet;
    }

    public int complete(String complaint_id){
        int res = -1;
        try {
            String complete = "UPDATE complaints SET status = 'completed'  where " + COLUMN_COMPLAINT_ID + "='" + complaint_id +"'";
            this.stat = this.conn.createStatement();
            res = this.stat.executeUpdate(complete);
        } catch (Exception e) {
            e.printStackTrace();
            res = -2;
        }
        return res;
    }
    public void close() throws SQLException{
        try {
            if(!this.conn.isClosed())
        this.conn.close();
        } catch (Exception e) {
            System.out.println("It isn't Opened");
        }
        try {
            if(!this.prep.isClosed())
                this.prep.close();
        } catch (Exception e) {
            System.out.println("It isn't Opened");
        }
        try {
            if(!this.stat.isClosed())
                this.stat.close();
        } catch (Exception e) {
            System.out.println("It isn't Opened");
        }
    }
}
