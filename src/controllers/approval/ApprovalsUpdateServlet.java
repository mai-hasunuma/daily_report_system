package controllers.approval;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalsCreateServlet
 */
@WebServlet("/approvals/update")
public class ApprovalsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
             Integer report_id = ((Report)request.getSession().getAttribute("report")).getId();

            Report r = em.find(Report.class, report_id);

            // 承認機能の追加
            boolean approval_authority = false;
            if(r.getEmployee().getAdmin_flag() == 0 && login_employee.getAdmin_flag() == 2) {
                approval_authority = true;
            }
            if(r.getEmployee().getAdmin_flag() == 2 && login_employee.getAdmin_flag() == 3) {
                approval_authority = true;
            }

            if(approval_authority == true) {
                r.setApproval(Integer.parseInt(request.getParameter("report_approve")));
            }

            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "更新が完了しました");
            request.getSession().removeAttribute("employee");
            response.sendRedirect(request.getContextPath() + "/approvals/index");

        }
    }

}
