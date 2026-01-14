package org.example.ingineriesoftwareparkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceProperty;
import jakarta.persistence.TypedQuery;
import org.example.ingineriesoftwareparkinglot.common.CarDto;
import org.example.ingineriesoftwareparkinglot.entities.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;


    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> dtos = new ArrayList<>();
        for (Car car : cars) {
            String ownerName = (car.getOwner() != null) ? car.getOwner().getUsername() : null;
            dtos.add(new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), ownerName));
        }
        return dtos;
    }
}


