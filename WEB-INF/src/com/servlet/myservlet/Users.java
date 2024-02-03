package com.servlet.myservlet;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {
    private final static String DATABASE = "jdbc:mysql://localhost:3306/servlets";
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String USERNAME = "acr123";
    private final static String PASSWORD = "acr123";
    private Connection conn;
    private PreparedStatement prep;
    private Statement stat;

    public Users(){
        try{
            Class.forName(DRIVER);
            this.conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            System.out.println("Connected");
        }
        catch(Exception e){
            System.out.println("Not Connected");
        }
    }

    public int insert(String name, int age, String uname, String pass) throws SQLException{
        int res = -1;
        int ALREADY_EXISTS = -2;
        try {
            if(!this.isUserExists(uname)){
                String insert = "INSERT INTO users VALUES(?, ?, ?, ?)";
                this.prep = this.conn.prepareStatement(insert);
                this.prep.setString(1, name);
                this.prep.setInt(2, age);
                this.prep.setString(3, uname);
                this.prep.setString(4, pass);
                res = this.prep.executeUpdate();
                this.prep.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid Credentials Or Already An User Exists");
            return ALREADY_EXISTS;
        }
        return res;
    }

    public int update(String name, int age, String uname, String pass) throws SQLException{
        int res = -1;
        try {
            if (this.isUserExists(uname)) {
                String update = "UPDATE users SET name=?, age=?, uname=?, pass=? where uname=?";
                this.prep = this.conn.prepareStatement(update);
                this.prep.setString(1, name);
                this.prep.setInt(2, age);
                this.prep.setString(3, uname);
                this.prep.setString(4, pass);
                this.prep.setString(5, uname);
                res = this.prep.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("Unable To Update");
            return -2;
        }
        return res;
    }

    public int delete(String uname) throws SQLException{
        int res = -1;
        try {
            String delete = "DELETE FROM users WHERE uname='" + uname + "'";
            this.stat = this.conn.createStatement();
            res = this.stat.executeUpdate(delete);
            this.stat.close();
        } catch (Exception e) {
            // return e;
            System.out.println("Unable To Delete");
            res = -2;
        }
        return res;
    }

    public boolean isUserExists(String uname) throws SQLException{
        boolean res = true;
        try {
            this.stat = this.conn.createStatement();
            String select = "SELECT * FROM users WHERE uname='" + uname + "'";
            ResultSet resultset = this.stat.executeQuery(select);
            res = resultset.next();
            resultset.close();
            this.stat.close();
        } catch (Exception e) {
            System.out.println("Error in checking user existence.");
        }
        return res;
    }

    public boolean authenicate(String uname, String pass) throws SQLException{
        boolean auth = false;
        try {
            this.stat = this.conn.createStatement();
            String select = "SELECT * FROM users WHERE uname='" + uname + "'";
            ResultSet resultset = this.stat.executeQuery(select);
            resultset.next();
            auth = resultset.getString(3).equals(uname) && resultset.getString(4).equals(pass);
            resultset.close();
            this.stat.close();
        } catch (Exception e) {
            System.out.println("Unable To Authenicate");
        }
        return auth;
    }

    public void close() throws SQLException{
        this.conn.close();
    }
}
