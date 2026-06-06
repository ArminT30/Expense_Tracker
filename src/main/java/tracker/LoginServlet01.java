package tracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginServlet01 extends HttpServlet {


	private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    String user = req.getParameter("user").trim();
    String pass = req.getParameter("pass").trim();

    try {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM users WHERE username=? AND password=?"
        );

        ps.setString(1, user);
        ps.setString(2, pass);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            res.sendRedirect("dashboard");
        } else {
            res.sendRedirect("login.html?error=invalid");
        }

    } catch(Exception e){
        e.printStackTrace();
    }
}
}