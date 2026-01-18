package org.example.ingineriesoftwareparkinglot.Servlets;

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

// Task: Only allow access if the user has the WRITE_CARS usergroup
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_CARS"}))
@WebServlet(name = "EditCar", value = "/EditCar")
public class EditCar extends HttpServlet {
    @Inject
    UserBean userBean;

    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        List<UserDto> users = userBean.findAllUsers();
        request.setAttribute("users", users);
        Long carId = Long.parseLong(request.getParameter("id"));
        CarDto car = carsBean.findById(carId);
        request.setAttribute("car", car);
        request.getRequestDispatcher("/WEB-INF/pages/cars/editCar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String licensePlate = request.getParameter("license_plate");
        String parkingSpot =  request.getParameter("parking_spot");
        Long userId = Long.parseLong(request.getParameter("owner_id"));
        Long carId = Long.parseLong(request.getParameter("car_id"));

        carsBean.updateCar(carId, licensePlate, parkingSpot, userId);

        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}