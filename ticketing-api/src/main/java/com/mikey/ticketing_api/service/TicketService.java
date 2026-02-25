package com.mikey.ticketing_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mikey.ticketing_api.dto.TicketRequest;
import com.mikey.ticketing_api.exception.ResourceNotFoundException;
import com.mikey.ticketing_api.model.Ticket;
import com.mikey.ticketing_api.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(TicketRequest request) {
        Ticket ticket = new Ticket();
        applyRequest(ticket, request);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", ticketId));
    }

    public Ticket updateTicket(Long ticketId, TicketRequest request) {
        Ticket existingTicket = getTicketById(ticketId);
        applyRequest(existingTicket, request);
        return ticketRepository.save(existingTicket);
    }

    public void deleteTicket(Long ticketId) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new ResourceNotFoundException("Ticket", ticketId);
        }
        ticketRepository.deleteById(ticketId);
    }

    private static void applyRequest(Ticket ticket, TicketRequest request) {
        ticket.setSubject(request.subject());
        ticket.setDescription(request.description());
        ticket.setRequesterId(request.requesterId());
        ticket.setAssigneeId(request.assigneeId());
        ticket.setCategoryId(request.categoryId());
        ticket.setPriorityId(request.priorityId());
        ticket.setStatusId(request.statusId());
        ticket.setResolvedAt(request.resolvedAt());
    }
}
