package com.akarsh.dao;

import com.akarsh.entity.Users;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

/**
 * Dao class for Users table DB related queries
 */
@Transactional
@Repository
public class UsersDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Check if the user already exist
     *
     * @param user the user
     * @return the user if exist
     */
    public Users getUser(Users user) {

        Optional<Users> newUser = checkUserExist(user);
        if (!newUser.isPresent()) {
            user.setId(UUID.randomUUID().toString());
            entityManager.persist(user);
            return user;
        }
        return newUser.get();
    }

    private Optional<Users> checkUserExist(Users user) {
        String hql = "SELECT * FROM Users WHERE mobile=" + user.getMobileNo().toString();
        return (Optional<Users>) entityManager.createNativeQuery(hql, Users.class).getResultList().stream().findFirst();
    }

    /**
     * To get the name of the user by its Id.
     *
     * @param id the user Id
     * @return the name of the user.
     */
    public String getUserNameById(String id) {
        return entityManager.find(Users.class, id).getName();
    }
}
