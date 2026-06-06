package tracker;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LogoutServlet01 extends HttpServlet {


	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest req, HttpServletResponse res)
throws ServletException, IOException {

    HttpSession session = req.getSession(false);

    if(session != null){
        session.invalidate();
    }

    res.sendRedirect("login.html");
}
}