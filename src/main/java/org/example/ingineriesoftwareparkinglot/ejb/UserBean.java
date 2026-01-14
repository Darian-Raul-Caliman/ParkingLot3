package org.example.ingineriesoftwareparkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.ingineriesoftwareparkinglot.common.CarDto;
import org.example.ingineriesoftwareparkinglot.common.UserDto;
import org.example.ingineriesoftwareparkinglot.entities.Car;
import org.example.ingineriesoftwareparkinglot.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserBean {
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;


    public List<UserDto> findAllUsers(){
        LOG.info("findAllUsers");
        try{
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        } catch (Exception ex){
            throw new EJBException(ex);
        }
    }

    public List<UserDto> copyUsersToDto(List<User> users){
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users){

            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }
}


