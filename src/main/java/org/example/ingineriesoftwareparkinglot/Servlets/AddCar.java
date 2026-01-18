package org.example.ingineriesoftwareparkinglot.Servlets;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.ingineriesoftwareparkinglot.common.UserDto;
import org.example.ingineriesoftwareparkinglot.ejb.CarsBean;
import org.example.ingineriesoftwareparkinglot.ejb.UserBean;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddCar", value = "/AddCar")
public class AddCar extends HttpServlet {

    @Inject
    UserBean usersBean;

    @Inject
    CarsBean carsBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        List<UserDto> user = usersBean.findAllUsers();
        request.setAttribute("users", user);
        request.getRequestDispatcher("/WEB-INF/pages/addCar.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String licensePlate = request.getParameter("license_plate");
        // FIX: Change "parkingSpot" to "parking_spot" to match the JSP
        String parkingSpot = request.getParameter("parking_spot");
        // This one was already correct (it matches name="owner_id")
        Long userId = Long.parseLong(request.getParameter("owner_id"));
        carsBean.createCar(licensePlate, parkingSpot, userId);
        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}