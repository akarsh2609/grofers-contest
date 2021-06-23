package com.akarsh.dao;

import com.akarsh.entity.TicketsPk;
import com.akarsh.entity.Users;
import com.akarsh.entity.Tickets;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Dao class for Tickets table DB related queries
 */
@Transactional
@Repository
public class TicketsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Users getTicket(Users user) {
        return entityManager.find(Users.class, user.getId());
    }

    /**
     * to create the ticket.
     *
     * @param user the user
     * @return the ticket created
     */
    public Tickets createTicket(Users user) {
        Tickets tickets = new Tickets();
        tickets.setTicketId(UUID.randomUUID().toString());
        tickets.setId(user.getId());
        entityManager.persist(tickets);
        return tickets;
    }

    /**
     * Update the ticket in Db for setting the contest name
     *
     * @param ticket      the ticket
     * @param contestName the name of the contest
     */
    public void updateTicket(Tickets ticket, String contestName) {
        String hql = String.format("UPDATE Tickets SET CONTEST = '%s' WHERE USER_ID = '%s' AND TICKET_ID = '%s' ",
                contestName, ticket.getId(), ticket.getTicketId());
        entityManager.createNativeQuery(hql, Tickets.class).executeUpdate();
    }

    /**
     * whether ticket is valid or not
     *
     * @param tickets the ticket
     * @return the ticket from DB
     */
    public Tickets takePart(Tickets tickets) {
        return entityManager.
                find(Tickets.class, new TicketsPk(tickets.getId(), tickets.getTicketId()));
    }

    /**
     * this function checks whether the ticket is already a part of the contest.
     *
     * @param ticket the ticket
     * @return true/false according to whether ticket already exist or not
     */
    public boolean checkUserIsAlreadyPartOfContest(Tickets ticket) {
        String hql = String.format("Select * from Tickets where user_id = '%s' and contest = '%s'",
                ticket.getId(), ticket.getContestName());
        return entityManager.createNativeQuery(hql, Tickets.class).getResultList().size() > 0;
    }

    /**
     * To find the random winner of the contest
     *
     * @param contestName the name of the contest
     * @return the name of the winner
     */
    public String findWinner(String contestName) {
        String hql = String.format("Select * from Tickets where contest = '%s'", contestName);
        List<Tickets> ticketsList = (List<Tickets>) entityManager.createNativeQuery(hql, Tickets.class).getResultList();
        List<String> userList = ticketsList.stream().map(Tickets::getId).collect(Collectors.toList());
        Random r = new Random();
        try {
            return userList.get(r.nextInt(userList.size()));
        } catch (IllegalArgumentException e) {
            return null;
        }

    }
}
