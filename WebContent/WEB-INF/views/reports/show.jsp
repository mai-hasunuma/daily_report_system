<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report != null}">
                <h2>日報 詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><a href="<c:url value="/employees/show?id=${report.employee.id}"/>"><c:out value="${report.employee.name}" /></a>&nbsp;
                                <!-- 最初sessionScope.login_employee != sessionScope.employee　と記載していたが、オブジェクト同士の比較になってしまい機能しなかった　.idとつけることで機能する -->
                                <c:if test="${relationship.size() == 0 and sessionScope.login_employee.id != sessionScope.employee.id }">
                                    <form method="POST" action="<c:url value='/relationships/create' />">
                                        <input type="hidden" name="_token" value="${_token}"/>
                                        <button type="submit">フォロー</button>
                                    </form>
                                </c:if>
                                <c:if test="${relationship.size() != 0 and sessionScope.login_employee.id != sessionScope.employee.id}">
                                    <form method="POST" action="<c:url value='/relationships/delete' />">
                                        <input type="hidden" name="_token" value="${_token}"/>
                                        <button type="submit">フォロー解除</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}"
                                    pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${report.content}" /></pre>
                            </td>
                        </tr>
                        <c:if test="${approval_authority == true and report.approval == 0}">
                            <tr>
                                <th>承認</th>
                                <td>
                                    <form method="POST" action="<c:url value='/approvals/update'/>">
                                        <input type="radio" name="report_approve" value="1">承認
                                        <input type="radio" name="report_approve" value="2">差戻
                                        <input type="hidden" name="_token" value="${_token}" />
                                        <button type="submit">投稿</button>
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${approval_authority == true and (report.approval == 1 or report.approval == 2)}">
                            <tr>
                                <th>承認</th>
                                <td>
                                    <c:if test="${report.approval == 1}">
                                        <c:out  value="承認済み"/>
                                    </c:if>
                                    <c:if test="${report.approval == 2}">
                                        <c:out  value="差し戻し中"/>
                                    </c:if>
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <th>登録日時</th>
                            <td><fmt:formatDate value="${report.created_at}"
                                    pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td><fmt:formatDate value="${report.updated_at}"
                                    pattern="yyyy-MM-dd HH:mm:ss" />
                        </tr>
                    </tbody>
                </table>
                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <p>
                        <a href="<c:url value="/reports/edit?id=${report.id}"/>">この日報を編集する</a>
                    </p>
                </c:if>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>
        <p>
            <a href="<c:url value="/reports/index"/>">一覧に戻る</a>
        </p>
    </c:param>
</c:import>