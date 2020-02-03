package controllers.divisions;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Division;

/**
 * Servlet implementation class DivisionsNewServlet
 */
@WebServlet("/divisions/new")
public class DivisionsNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId());

        Division d = new Division();
        d.setCreated_at(new Timestamp(System.currentTimeMillis()));
        request.setAttribute("division", d);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/divisions/new.jsp");
        rd.forward(request, response);
    }

}
