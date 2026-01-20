<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Cars">
    <h1>Cars</h1>

    <form method="POST" action="${pageContext.request.contextPath}/Cars">

        <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
            <a href="${pageContext.request.contextPath}/AddCar" class="btn btn-primary" role="button">Add Car</a>
            <button class="btn btn-danger" type="submit">Delete Cars</button>
        </c:if>

        <p class="mt-3">Free parking spots: <b>${numberOfFreeParkingSpots}</b></p>

        <c:forEach var="car" items="${cars}">
            <div class="row mb-2 align-items-center">

                <div class="col">
                    <input type="checkbox" name="car_ids" value="${car.id}" />
                </div>

                <div class="col">
                        ${car.licensePlate}
                </div>
                <div class="col">
                        ${car.parkingSpot}
                </div>
                <div class="col">
                        ${car.ownerName}
                </div>

                <div class="col">
                    <img src="${pageContext.request.contextPath}/CarPhotos?id=${car.id}" width="48" />
                </div>

                <div class="col">
                    <c:if test="${pageContext.request.isUserInRole('WRITE_CARS')}">
                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/AddCarPhoto?id=${car.id}"
                           role="button">Add photo</a>

                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/EditCar?id=${car.id}">Edit Car</a>
                    </c:if>
                </div>
            </div>
        </c:forEach>

    </form>
</t:pageTemplate>