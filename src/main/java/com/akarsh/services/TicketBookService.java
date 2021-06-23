package com.akarsh.services;

import com.akarsh.dao.ContestDao;
import com.akarsh.dao.UsersDao;
import com.akarsh.dao.TicketsDao;
import com.akarsh.entity.Contest;
import com.akarsh.entity.Users;
import com.akarsh.entity.Tickets;
import com.akarsh.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.akarsh.util.ResponseHelper.getErrorResponse;
import static com.akarsh.util.ResponseHelper.getSuccessResponse;

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
     * @param newUser the Ticket object containing userId
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
     * @param ticket the ticket
     * @return Success response if all the details are valid
     */
    public ResponseEntity<ResponseHelper> takePartInContest(Tickets ticket) {
        Tickets verifyTicket = ticketsDao.takePart(ticket);
        if (ticketsDao.checkUserIsAlreadyPartOfContest(ticket)) {
            return getErrorResponse("Can not take part in the same contest again", HttpStatus.OK);
        }
        if (Objects.isNull(verifyTicket)) {
            return getErrorResponse("the ticket does not belong to the user", HttpStatus.OK);
        }
        if (!Objects.isNull(verifyTicket.getContestName())) {
            return getErrorResponse("the ticket has already been used", HttpStatus.OK);
        }
        Contest contest = contestDao.findContest(ticket.getContestName());
        if (Objects.isNull(contest)) {
            return getErrorResponse("Contest Not Found", HttpStatus.OK);
        }
        if (contest.getEndTime().before(Timestamp.from(Instant.now()))) {
            return getErrorResponse("Sorry! the Contest has already Ended", HttpStatus.OK);
        }
        ticketsDao.updateTicket(verifyTicket, ticket.getContestName());
        return ResponseEntity.status(HttpStatus.CREATED).
                body(getSuccessResponse(String.format("Successfully taken part in the %s",
                        ticket.getContestName()), false));
    }

    /**
     * to find the Upcoming Contests in a week
     *
     * @return the Contest EndTime along with their prize.
     */
    public Map<Date, String> getUpComingContest() {
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

    /**
     * This method finds the winer of the contest
     *
     * @param contest the contest name
     * @return the response
     */
    public ResponseEntity<ResponseHelper> findWinnerOfContest(String contest) {

        Contest contestObj = contestDao.findContest(contest);
        if (Objects.isNull(contestObj)) {
            return getErrorResponse("Contest not found", HttpStatus.OK);
        }
        if (contestObj.getEndTime().after(Date.from(Instant.now()))) {
            return getErrorResponse("Contest has not ended yet", HttpStatus.OK);
        }
        String winner = contestObj.getWinner();
        winner = winner.isEmpty() ? (Objects.isNull(ticketsDao.findWinner(contest)) ? "" :
                userDao.getUserNameById(ticketsDao.findWinner(contest))) : contestObj.getWinner();
        return ResponseEntity.status(HttpStatus.OK).body(getSuccessResponse(winner, false));
    }
}
