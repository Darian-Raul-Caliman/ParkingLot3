package org.example.ingineriesoftwareparkinglot.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.ingineriesoftwareparkinglot.common.CarDto;
import org.example.ingineriesoftwareparkinglot.common.UserDto;
import org.example.ingineriesoftwareparkinglot.entities.Car;
import org.example.ingineriesoftwareparkinglot.entities.User;
import org.example.ingineriesoftwareparkinglot.entities.UserGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Stateless
public class UserBean {
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;
    @Inject
    PasswordBean passwordBean;



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

    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        assignGroupsToUser(username, groups);
    }
    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }


    }

    public Collection<String> findUsernameByUserIds(Collection<Long> userIds){
        List<String> usernames =
                entityManager.createQuery("SELECT u.username FROM User u WHERE u.id IN :userIds", String.class)
                .setParameter("userIds", userIds)
                        .getResultList();
        return usernames;
    }


    public void updateUser(Long userId, String username, String email, String password) {
        LOG.info("updateUser");
        User user = entityManager.find(User.class, userId);
        user.setUsername(username);
        user.setEmail(email);

        // FIX: Hash the password before saving!
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(passwordBean.convertToSha256(password));
        }
    }

    public UserDto findById(Long id) {
        User user = entityManager.find(User.class, id);
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}


