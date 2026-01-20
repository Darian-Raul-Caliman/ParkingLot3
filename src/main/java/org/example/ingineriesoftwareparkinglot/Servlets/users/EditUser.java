package org.example.ingineriesoftwareparkinglot.Servlets.users;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.ingineriesoftwareparkinglot.common.UserDto;
import org.example.ingineriesoftwareparkinglot.ejb.UserBean;

import java.io.IOException;

@WebServlet(name = "EditUser", value = "/EditUser")
public class EditUser extends HttpServlet {

@Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idAsString = request.getParameter("id");

        if (idAsString != null) {
            Long id = Long.parseLong(idAsString);
            UserDto user = userBean.findById(id);
            request.setAttribute("user", user);
        }

        request.getRequestDispatcher("/WEB-INF/pages/users/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idAsString = request.getParameter("user_id");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Long id = Long.parseLong(idAsString);

        userBean.updateUser(id, username, email, password);

        response.sendRedirect(request.getContextPath() + "/Users");
    }
}