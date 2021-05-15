package com.grofers.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity for Tickets table
 */
@Entity
@Table(name = "tickets")
@IdClass(TicketsPk.class)
public class Tickets implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private String id;

    @Column(name = "CONTEST")
    private String contestName;

    @Id
    @Column(name = "TICKET_ID")
    private String ticketId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

}
