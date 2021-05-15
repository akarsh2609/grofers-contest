package com.grofers.entity;


import java.io.Serializable;
import java.util.Objects;

/**
 * Composite PK for Tickets
 */
public class TicketsPk implements Serializable {
    protected String id;
    protected String ticketId;

    public TicketsPk() {}

    public TicketsPk(String id, String ticketId) {
        this.id = id;
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketsPk ticketsPk = (TicketsPk) o;
        return Objects.equals(id, ticketsPk.id) && Objects.equals(ticketId, ticketsPk.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketId);
    }
}