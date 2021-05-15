package com.grofers.controller;


import com.grofers.entity.Users;
import com.grofers.entity.Tickets;
import com.grofers.services.TicketBookService;
import com.grofers.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
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

        Tickets ticket = service.createTicket(users);
        return Objects.isNull(ticket) ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseHelper.getInstance()
                .getResponse("User Does not Exist", true)) : ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseHelper.getInstance()
                        .getResponse("Ticket has been successfully created with Id = " + ticket.getTicketId(), false));

    }

    /**
     * to register for a contest
     *
     * @param tickets the ticket containing ticketId and userId
     * @return success/failure response
     */
    @PostMapping("takePart")
    public ResponseEntity<ResponseHelper> takePartInContest(@RequestBody Tickets tickets) {
        return service.takePartInContest(tickets);
    }

    /**
     * to find the upcoming contests in a week
     *
     * @return the list of contests
     */
    @GetMapping("upcomingContest")
    public ResponseEntity<Map<String, String>> getUpComingContest() {
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

}
