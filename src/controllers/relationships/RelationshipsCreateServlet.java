package controllers.relationships;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Relationship;
import utils.DBUtil;

/**
 * Servlet implementation class RelationshipsCreateServlet
 */
@WebServlet("/relationships/create")
public class RelationshipsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Relationship r = new Relationship();

            r.setFollowing((Employee)request.getSession().getAttribute("login_employee"));
            r.setFollowed((Employee)request.getSession().getAttribute("employee"));
                System.out.println(request.getSession().getAttribute("employee"));


            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "フォロー完了しました");
            request.getSession().removeAttribute("employee");
            response.sendRedirect(request.getContextPath() + "/reports/index");


        }
    }

}
