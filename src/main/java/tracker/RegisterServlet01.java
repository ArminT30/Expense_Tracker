package tracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterServlet01 extends HttpServlet {


	private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    String user = req.getParameter("user").trim();
    String pass = req.getParameter("pass").trim();

    if(pass.length() < 6){
        res.sendRedirect("register.html?error=weak");
        return;
    }

    try {
        Connection con = DBConnection.getConnection();

        if(con == null){
            res.getWriter().println("DB NOT CONNECTED");
            return;
        }

        // CHECK IF USER EXISTS
        PreparedStatement check = con.prepareStatement(
            "SELECT id FROM users WHERE username=?"
        );

        check.setString(1, user);
        ResultSet rs = check.executeQuery();

        if(rs.next()){
            res.sendRedirect("register.html?error=exists");
            return;
        }

        // INSERT NEW USER (ANYONE CAN REGISTER)
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO users(username,password) VALUES(?,?)"
        );

        ps.setString(1, user);
        ps.setString(2, pass);

        ps.executeUpdate();

        // AUTO LOGIN
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        res.sendRedirect("dashboard");

    } catch(Exception e){
        e.printStackTrace();
    }
}
}