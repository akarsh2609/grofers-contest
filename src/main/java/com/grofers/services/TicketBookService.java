package com.grofers.services;

import com.grofers.dao.ContestDao;
import com.grofers.dao.UsersDao;
import com.grofers.dao.TicketsDao;
import com.grofers.entity.Contest;
import com.grofers.entity.Users;
import com.grofers.entity.Tickets;
import com.grofers.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service layer for all the API's
 */
@Service
public class TicketBookService {

    @Autowired
    private UsersDao userDao;

    @Autowired
    private TicketsDao ticketsDao;

    @Autowired
    private ContestDao contestDao;

    /**
     * Creates a user if not present
     *
     * @param user The details of a user
     * @return the user created
     */
    public Users createUser(Users user) {

        return userDao.getUser(user);
    }

    /**
     * To create a ticket corresponding to a contest
     *
     * @param ticket the Ticket object containing userId
     * @return the Ticket with a ticketId
     */
    public Tickets createTicket(Users newUser) {
        Users user = ticketsDao.getTicket(newUser);
        if (Objects.isNull(user)) {
            return null;
        }
        return ticketsDao.createTicket(user);
    }

    /**
     * To register in a contest against a corresponding ticket
     *
     * @param ticket
     * @return Success response if all the details are valid
     */
    public ResponseEntity<ResponseHelper> takePartInContest(Tickets ticket) {
        Tickets verifyTicket = ticketsDao.takePart(ticket);
        if (ticketsDao.checkUserIsAlreadyPartOfContest(ticket)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(ResponseHelper.getInstance().
                            getResponse("Can not take part in the same contest again", true));
        }
        if (Objects.isNull(verifyTicket)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(ResponseHelper.getInstance().
                            getResponse("the ticket does not belong to the user", true));
        }
        if (!Objects.isNull(verifyTicket.getContestName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(ResponseHelper.getInstance().
                            getResponse("the ticket has already been used", true));
        }
        Contest contest = contestDao.findContest(ticket.getContestName());
        if (Objects.isNull(contest)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(ResponseHelper.getInstance().getResponse("Contest Not Found", true));
        }
        if (contest.getEndTime().before(Timestamp.from(Instant.now()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body(ResponseHelper.getInstance().
                            getResponse("Sorry! the Contest has already Ended", true));
        }
        ticketsDao.updateTicket(verifyTicket, ticket.getContestName());
        return ResponseEntity.status(HttpStatus.CREATED).
                body(ResponseHelper.getInstance().getResponse(String.format("Successfully taken part in the %s",
                        ticket.getContestName()), false));
    }

    /**
     * to find the Upcoming Contests in a week
     *
     * @return the Contest EndTime along with their prize.
     */
    public Map<String, String> getUpComingContest() {
        return contestDao.upComingContests();
    }

    /**
     * To find out the winners of last week contests.
     *
     * @return the Contest name along with the Winner Name.
     */
    public Map<String, String> getLastWeekWinners() {
        return contestDao.lastWeekWinners();
    }
}
