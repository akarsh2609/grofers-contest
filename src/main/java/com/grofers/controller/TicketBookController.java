package com.grofers.controller;


import com.grofers.entity.Users;
import com.grofers.entity.Tickets;
import com.grofers.services.TicketBookService;
import com.grofers.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.grofers.util.ResponseHelper.getErrorResponse;
import static com.grofers.util.ResponseHelper.getSuccessResponse;

/**
 * Resource class for all the REST endpoint
 */
@Controller
@RequestMapping("gamingservice")
public class TicketBookController {

    @Autowired
    private TicketBookService service;

    /**
     * to create/login a user
     *
     * @param users the data of a user
     * @return the User created with their Id
     */
    @PostMapping("loginSignup")
    public ResponseEntity createUser(@RequestBody Users users) {

        if(Objects.isNull(users.getMobileNo()) || Objects.isNull(users.getName())) {
            return getErrorResponse("Invalid params", HttpStatus.BAD_REQUEST);
        }
        Users user = service.createUser(users);
        return new ResponseEntity<Users>(user, HttpStatus.CREATED);
    }

    /**
     * To create a ticket for a user
     *
     * @param users the user object containing userId
     * @return the success/failure response along with ticketId
     */
    @PostMapping("createTicket")
    public ResponseEntity<ResponseHelper> createTicket(@RequestBody Users users) {

        if (Objects.isNull(users.getId())) {
            return getErrorResponse("Enter a valid UserId", HttpStatus.BAD_REQUEST);
        }
        Tickets ticket = service.createTicket(users);
        return Objects.isNull(ticket) ? getErrorResponse("User Does not Exist", HttpStatus.BAD_REQUEST) : ResponseEntity
                .status(HttpStatus.CREATED).body(getSuccessResponse("Ticket has been successfully created with Id = " + ticket.getTicketId(), false));
    }

    /**
     * to register for a contest
     *
     * @param tickets the ticket containing ticketId and userId
     * @return success/failure response
     */
    @PostMapping("takePart")
    public ResponseEntity<ResponseHelper> takePartInContest(@RequestBody Tickets tickets) {

        if (Objects.isNull(tickets.getTicketId()) || Objects.isNull(tickets.getId()) || Objects.isNull(tickets.getContestName())) {
            return getErrorResponse("Params missing", HttpStatus.BAD_REQUEST);
        }
        return service.takePartInContest(tickets);
    }

    /**
     * to find the upcoming contests in a week
     *
     * @return the list of contests
     */
    @GetMapping("upcomingContest")
    public ResponseEntity<Map<Date, String>> getUpComingContest() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUpComingContest());
    }

    /**
     * to find the winners of the last week contests
     *
     * @return the list of winners along with contest name
     */
    @GetMapping("lastWeekWinners")
    public ResponseEntity<Map<String, String>> getLastWeekWinners() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getLastWeekWinners());
    }

    /**
     * To find the winner of a contest
     *
     * @param contest the name of the contest
     * @return the winner
     */
    @GetMapping("findWinner")
    public ResponseEntity<ResponseHelper> findWinner(@RequestParam("contest") String contest) {
        return service.findWinnerOfContest(contest);
    }

}
