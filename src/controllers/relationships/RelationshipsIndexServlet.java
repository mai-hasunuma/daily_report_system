package controllers.relationships;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class RelationshipsIndexServlet
 */
@WebServlet("/relationships/index")
public class RelationshipsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        List<Employee> followed_employees = em.createNamedQuery("getFolloewdByMe", Employee.class)
                .setParameter("following", login_employee)
                .getResultList();

        List<Report> reportAll = new ArrayList<Report>();

        for (int i = 0; i < followed_employees.size(); i++) {
            Employee followed_employee = followed_employees.get(i);
            int followed_employee_id = followed_employee.getId();

            List<Report> following_reports = em.createNamedQuery("getMyFollowingAllReports", Report.class)
                    .setParameter("followed_id", followed_employee_id)
                    .getResultList();
            // .addAllメソッドを使うことによってリスト丸ごと追加できるようになる
            reportAll.addAll(following_reports);

        }

        int toIndex = 0;
        if(reportAll.size() / 15 == page - 1) {
            // 最終ページ
            // インデックスは最後の値は含まないので-1
            toIndex = reportAll.size();
        }else {
            toIndex = page * 15;
        }

        List<Report>reports = new ArrayList<Report>();
        if(toIndex >= 0) {
            reports = reportAll.subList(15 * (page -1), toIndex);
        } else {
            reports = null;
        }

        em.close();
        int reports_count = reportAll.size();


    request.setAttribute("reports", reports);
    request.setAttribute("reports_count", reports_count);
    request.setAttribute("page", page);
    request.setAttribute("servletPath", request.getServletPath());
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/relationships/index.jsp");
    rd.forward(request, response);
    }

}
