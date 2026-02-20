package com.mikey.ticketing_api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mikey.ticketing_api.model.Ticket;
import com.mikey.ticketing_api.repository.TicketRepository;

@Component
public class TicketSeeder implements CommandLineRunner {

    private final TicketRepository ticketRepository;

    @Value("${app.seed.enabled:false}")
    private boolean seedEnabled;

    public TicketSeeder(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void run(String... args) {
        if (!seedEnabled || ticketRepository.count() > 0) {
            return;
        }

        Ticket ticket1 = new Ticket();
        ticket1.setSubject("Unable to login");
        ticket1.setDescription("Requester cannot log in using valid credentials.");
        ticket1.setRequesterId(101);
        ticket1.setAssigneeId(201);
        ticket1.setCategoryId(1);
        ticket1.setPriorityId(2);
        ticket1.setStatusId(1);

        Ticket ticket2 = new Ticket();
        ticket2.setSubject("Printer not working");
        ticket2.setDescription("Office printer shows paper jam even when empty.");
        ticket2.setRequesterId(102);
        ticket2.setAssigneeId(null);
        ticket2.setCategoryId(2);
        ticket2.setPriorityId(3);
        ticket2.setStatusId(1);

        Ticket ticket3 = new Ticket();
        ticket3.setSubject("VPN access request");
        ticket3.setDescription("Need VPN access for remote work.");
        ticket3.setRequesterId(103);
        ticket3.setAssigneeId(202);
        ticket3.setCategoryId(3);
        ticket3.setPriorityId(1);
        ticket3.setStatusId(2);

        ticketRepository.saveAll(List.of(ticket1, ticket2, ticket3));
    }
}
