package org.example.ingineriesoftwareparkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceProperty;
import jakarta.persistence.TypedQuery;
import org.example.ingineriesoftwareparkinglot.common.CarDto;
import org.example.ingineriesoftwareparkinglot.entities.Car;
import org.example.ingineriesoftwareparkinglot.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    public void createCar(String licensePlate, String parkingSpot, long userId){
        LOG.info("createCar");
        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);
        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);
        entityManager.persist(car);
    }


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

    public CarDto findById(Long carId) {
        LOG.info("findById");
        try {
            Car car = entityManager.find(Car.class, carId);
            if (car == null) {
                return null;
            }


            String ownerName = (car.getOwner() != null) ? car.getOwner().getUsername() : null;

            return new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), ownerName);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, long userId){
        LOG.info("updateCar");
        Car car = entityManager.find(Car.class, carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User oldUser = car.getOwner();
        oldUser.getCars().remove(car);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);
    }

    public void deleteCarsByIds(Collection<Long> carIds){
        LOG.info("deleteCarsByIds");
        for(Long carId : carIds){
            Car car = entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }

}


