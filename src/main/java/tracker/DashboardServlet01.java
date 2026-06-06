package tracker;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DashboardServlet01 extends HttpServlet {


	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    HttpSession session = req.getSession(false);

    if(session == null){
        res.sendRedirect("login.html");
        return;
    }

    String user = (String) session.getAttribute("user");

    double total = 0;
    String labels = "";
    String data = "";

    try {
        Connection con = DBConnection.getConnection();

        if(con != null){

            PreparedStatement ps = con.prepareStatement(
                "SELECT MONTH(date), SUM(amount) FROM expenses WHERE username=? GROUP BY MONTH(date)"
            );

            ps.setString(1, user);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                labels += rs.getInt(1) + ",";
                data += rs.getDouble(2) + ",";
            }

            PreparedStatement ps2 = con.prepareStatement(
                "SELECT SUM(amount) FROM expenses WHERE username=?"
            );

            ps2.setString(1, user);
            ResultSet rs2 = ps2.executeQuery();

            if(rs2.next()){
                total = rs2.getDouble(1);
            }
        }

    } catch(Exception e){
        e.printStackTrace();
    }

    req.setAttribute("total", total);
    req.setAttribute("labels", labels);
    req.setAttribute("data", data);

    RequestDispatcher rd = req.getRequestDispatcher("dashboard.jsp");
    rd.forward(req, res);
}
}