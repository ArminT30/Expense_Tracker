package tracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UpdateExpenseServlet01 extends HttpServlet {


	private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    HttpSession session = req.getSession(false);

    if(session == null || session.getAttribute("user") == null){
        res.sendRedirect("login.html");
        return;
    }

    String user = (String) session.getAttribute("user");

    try {
        Connection con = DBConnection.getConnection();

        if(con == null){
            res.getWriter().println("DB not connected");
            return;
        }

        PreparedStatement ps = con.prepareStatement(
            "UPDATE expenses SET title=?, amount=?, category=?, date=? WHERE id=? AND username=?"
        );

        ps.setString(1, req.getParameter("title"));
        ps.setDouble(2, Double.parseDouble(req.getParameter("amount")));
        ps.setString(3, req.getParameter("category"));
        ps.setString(4, req.getParameter("date"));
        ps.setInt(5, Integer.parseInt(req.getParameter("id")));
        ps.setString(6, user);

        ps.executeUpdate();

        res.sendRedirect("view");

    } catch(Exception e){
        e.printStackTrace();
    }
}
}