package org.jboss.as.quickstarts.greeter.service;

import org.jboss.as.quickstarts.greeter.domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Stateless
public class UserService {

    @Inject
    private EntityManager entityManager;

    public User getForUsername(String username) {
        try {
            System.out.println("Stateless!!!");
            Query query = entityManager.createQuery("select u from User u where u.username = :username");
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void createUser(User user) {
        entityManager.persist(user);
    }

}
