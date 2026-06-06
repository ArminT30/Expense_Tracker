package tracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddExpenseServlet01 extends HttpServlet {

	private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    HttpSession session = req.getSession(false);

    if(session == null){
        res.sendRedirect("login.html");
        return;
    }

    String user = (String) session.getAttribute("user");

    try {
        Connection con = DBConnection.getConnection();

        if(con == null){
            res.getWriter().println("DB NOT CONNECTED");
            return;
        }

        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO expenses(title,amount,category,date,username) VALUES(?,?,?,?,?)"
        );

        ps.setString(1, req.getParameter("title"));
        ps.setDouble(2, Double.parseDouble(req.getParameter("amount")));
        ps.setString(3, req.getParameter("category"));
        ps.setDate(4, java.sql.Date.valueOf(req.getParameter("date")));
        ps.setString(5, user);

        ps.executeUpdate();

        res.sendRedirect("dashboard");

    } catch(Exception e){
        e.printStackTrace();
    }
}
}