package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Relationship;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        Employee e = r.getEmployee();

        // リンク先のユーザーをログインユーザーがフォローしているかチェックして、結果を文字列に変換する
        List<Relationship> relationship = em.createNamedQuery("followingJudgement", Relationship.class)
                .setParameter("following", request.getSession().getAttribute("login_employee"))
                .setParameter("followed", e)
                .getResultList();
        em.close();

/*        String relationship_judgement = "フォロー解除ボタン設置";
        if (relationship == null  && relationship.size() == 0) {
            relationship_judgement ="フォローボタン設置";
        }
        if (request.getSession().getAttribute("login_employee") == r.getEmployee()) {
            relationship_judgement = "フォロー関連ボタン設置しない";
        }*/

        request.setAttribute("relationship", relationship);
/*        request.setAttribute("relationship_judgement", relationship_judgement);*/
        request.setAttribute("report", r);
        request.getSession().setAttribute("employee", e);
        request.setAttribute("_token", request.getSession().getId());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);

    }

}
