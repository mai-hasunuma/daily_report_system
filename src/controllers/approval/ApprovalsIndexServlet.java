package controllers.approval;

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
 * Servlet implementation class ApprovalsIndexServlet
 */
@WebServlet("/approvals/index")
public class ApprovalsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {
        }

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        List<Report> non_approvaal_reports = new ArrayList<Report>();
        long non_approval_reports_count = 0;

        if(login_employee.getAdmin_flag() == 2) {
            non_approvaal_reports = em.createNamedQuery("getNonApprovalReports", Report.class)
                    .setParameter("admin_flag", 0)
                    .setFirstResult(15 * (page - 1))
                    .setMaxResults(15)
                    .getResultList();

            non_approval_reports_count = (long)em.createNamedQuery("getNonApprovalReportsCount", Long.class)
                    .setParameter("admin_flag", 0)
                    .getSingleResult();
        }

        if(login_employee.getAdmin_flag() == 3) {
            non_approvaal_reports = em.createNamedQuery("getNonApprovalReports", Report.class)
                    .setParameter("admin_flag", 2)
                    .setFirstResult(15 * (page - 1))
                    .setMaxResults(15)
                    .getResultList();

            non_approval_reports_count = (long)em.createNamedQuery("getNonApprovalReportsCount", Long.class)
                    .setParameter("admin_flag", 2)
                    .getSingleResult();
        }

        em.close();

        request.setAttribute("reports", non_approvaal_reports);
        request.setAttribute("reports_count", non_approval_reports_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approvals/index.jsp");
        rd.forward(request, response);
    }

}
