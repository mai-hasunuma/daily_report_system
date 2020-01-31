package controllers.relationships;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Relationship;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class RelationshipDestroyServlet
 */
@WebServlet("/relationships/delete")
public class RelationshipsDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
     */
    protected void  doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
            Report r = (Report)request.getSession().getAttribute("report");

            Relationship relationship = em.createNamedQuery("followingJudgement", Relationship.class)
                    .setParameter("following", request.getSession().getAttribute("login_employee"))
                    .setParameter("followed", r.getEmployee())
                    .getSingleResult();

            Integer relationship_id = relationship.getId();

            em.getTransaction().begin();
            Relationship delete_relationship = em.find(Relationship.class, relationship_id);
            em.remove(delete_relationship);
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "削除が完了しました。");
            response.sendRedirect(request.getContextPath() + "/relationships/index");
        }
    }

}
