package Servlets;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.ingineriesoftwareparkinglot.common.CarDto;
import org.example.ingineriesoftwareparkinglot.common.UserDto;
import org.example.ingineriesoftwareparkinglot.ejb.CarsBean;
import org.example.ingineriesoftwareparkinglot.ejb.UserBean;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Users", value = "/Users")
public class Users extends HttpServlet {

    @Inject
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        List<UserDto> user = userBean.findAllUsers();

        request.getRequestDispatcher("/WEB-INF/pages/users.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
    }
}