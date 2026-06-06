package tracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DeleteExpenseServlet01 extends HttpServlet {


	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    HttpSession session = req.getSession(false);

    if(session == null || session.getAttribute("user") == null){
        res.sendRedirect("login.html");
        return;
    }

    try {
        Connection con = DBConnection.getConnection();

        if(con == null){
            res.getWriter().println("Database not connected");
            return;
        }

        String idParam = req.getParameter("id");

        if(idParam == null){
            res.getWriter().println("Invalid request");
            return;
        }

        PreparedStatement ps = con.prepareStatement(
            "DELETE FROM expenses WHERE id=?"
        );

        ps.setInt(1, Integer.parseInt(idParam));

        ps.executeUpdate();

        res.sendRedirect("view");

    } catch(Exception e){
        e.printStackTrace();
        res.getWriter().println("Error deleting expense");
    }
}
}