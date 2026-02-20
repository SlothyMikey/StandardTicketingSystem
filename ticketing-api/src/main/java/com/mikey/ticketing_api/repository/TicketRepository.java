package com.mikey.ticketing_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mikey.ticketing_api.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
