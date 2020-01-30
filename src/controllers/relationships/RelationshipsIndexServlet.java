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


        em.close();

    request.setAttribute("reports", reportAll);
    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
    rd.forward(request, response);
    }

}
