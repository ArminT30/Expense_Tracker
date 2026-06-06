package tracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class EditExpenseServlet01 extends HttpServlet {


	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    HttpSession session = req.getSession(false);

    if(session == null || session.getAttribute("user") == null){
        res.sendRedirect("login.html");
        return;
    }

    String user = (String) session.getAttribute("user");

    PrintWriter out = res.getWriter();
    res.setContentType("text/html");

    String idParam = req.getParameter("id");

    if(idParam == null){
        out.println("Invalid ID");
        return;
    }

    try {
        Connection con = DBConnection.getConnection();

        if(con == null){
            out.println("Database not connected");
            return;
        }

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM expenses WHERE id=? AND username=?"
        );

        ps.setInt(1, Integer.parseInt(idParam));
        ps.setString(2, user);

        ResultSet rs = ps.executeQuery();

        out.println("<html><head><link rel='stylesheet' href='style.css'></head><body>");
        out.println("<div class='navbar'>Edit Expense</div>");

        if(rs.next()){
            out.println("<div class='container'>");
            out.println("<form action='update' method='post'>");

            out.println("<input type='hidden' name='id' value='"+idParam+"'>");
            out.println("<input name='title' value='"+rs.getString("title")+"'>");
            out.println("<input name='amount' value='"+rs.getDouble("amount")+"'>");
            out.println("<input name='category' value='"+rs.getString("category")+"'>");
            out.println("<input type='date' name='date' value='"+rs.getDate("date")+"'>");

            out.println("<button>Update</button>");
            out.println("</form>");
            out.println("</div>");
        } else {
            out.println("<p style='text-align:center;'>Expense not found</p>");
        }

        out.println("</body></html>");

    } catch(Exception e){
        e.printStackTrace();
        out.println("Error loading edit page");
    }
}
}