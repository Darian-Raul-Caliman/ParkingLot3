<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Edit Car">
    <h1>Edit Car</h1>

    <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/EditCar">

        <input type="hidden" name="car_id" value="${car.id}" />

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="license_plate">License Plate</label>
                <input type="text" class="form-control" id="license_plate" name="license_plate" value="${car.licensePlate}" required>
                <div class="invalid-feedback">
                    License plate is required.
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="parking_spot">Parking Spot</label>
                <input type="text" class="form-control" id="parking_spot" name="parking_spot" value="${car.parkingSpot}" required>
                <div class="invalid-feedback">
                    Parking spot is required.
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="user_id">Owner</label>
                <select class="custom-select d-block w-100" id="user_id" name="user_id" required>
                    <option value="">Choose...</option>
                    <c:forEach var="user" items="${users}">
                        <option value="${user.id}" ${car.ownerName eq user.username ? 'selected' : ''}>${user.username}</option>
                    </c:forEach>
                </select>
                <div class="invalid-feedback">
                    Please select an owner.
                </div>
            </div>
        </div>

        <hr class="mb-4">
        <button class="btn btn-primary btn-lg btn-block" type="submit">Save Changes</button>
        <a href="${pageContext.request.contextPath}/Cars" class="btn btn-secondary btn-lg btn-block">Cancel</a>
    </form>

    <script>
        (function() {
            'use strict';
            window.addEventListener('load', function() {
                var forms = document.getElementsByClassName('needs-validation');
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
    </script>
</t:pageTemplate>