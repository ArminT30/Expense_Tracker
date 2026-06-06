package tracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ViewExpenseServlet01 extends HttpServlet {


	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    res.setContentType("text/html;charset=UTF-8");

    HttpSession session = req.getSession(false);

    if(session == null || session.getAttribute("user") == null){
        res.sendRedirect("login.html");
        return;
    }

    String user = (String) session.getAttribute("user");

    PrintWriter out = res.getWriter();

    out.println("<html><head>");
    out.println("<link rel='stylesheet' href='style.css'>");
    out.println("</head><body>");

    out.println("<div class='navbar'>Expenses</div>");

    try {
        Connection con = DBConnection.getConnection();

        if(con == null){
            out.println("DB NOT CONNECTED");
            return;
        }

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM expenses WHERE username=?"
        );

        ps.setString(1, user);

        ResultSet rs = ps.executeQuery();

        out.println("<table>");
        out.println("<tr><th>Title</th><th>Amount</th><th>Category</th><th>Date</th><th>Action</th></tr>");

        while(rs.next()){
            int id = rs.getInt("id");

            out.println("<tr>");
            out.println("<td>"+rs.getString("title")+"</td>");
            out.println("<td>₹"+rs.getDouble("amount")+"</td>");
            out.println("<td>"+rs.getString("category")+"</td>");
            out.println("<td>"+rs.getDate("date")+"</td>");

            out.println("<td>");
            out.println("<a href='edit?id="+id+"'>Edit</a> | ");
            out.println("<a href='delete?id="+id+"'>Delete</a>");
            out.println("</td>");

            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");

    } catch(Exception e){
        e.printStackTrace();
        out.println("Error loading data");
    }
}
}