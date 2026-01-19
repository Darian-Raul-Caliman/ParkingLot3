<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">
    <h1>Users</h1>

    <form method="POST" action="${pageContext.request.contextPath}/Users">

        <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
            <a href="${pageContext.request.contextPath}/AddUser" class="btn btn-primary btn-lg">Add User</a>
        </c:if>

        <c:if test="${pageContext.request.isUserInRole('INVOICING')}">
            <button class="btn btn-secondary btn-lg" type="submit">Invoice</button>
        </c:if>

        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Select</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>
                                <input type="checkbox" name="user_ids" value="${user.id}" />
                            </td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                            <td>
                                <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
                                    <a class="btn btn-secondary"
                                       href="${pageContext.request.contextPath}/EditUser?id=${user.id}">
                                        Edit User
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </form>

    <c:if test="${pageContext.request.isUserInRole('INVOICING') and not empty invoices}">
        <h2>Invoices</h2>
        <c:forEach var="username" items="${invoices}" varStatus="status">
            ${status.index + 1}. ${username}
            <br/>
        </c:forEach>
    </c:if>

</t:pageTemplate>