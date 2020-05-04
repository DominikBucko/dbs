package backend.service;

import backend.entity.Ticket;
import backend.entity.User;

import java.util.ArrayList;
import java.util.List;

public class TicketService {
    public List<Ticket> getUserTickets(User current_user) {
        return new ArrayList<>();
    }

    public boolean saveTicket(Ticket newTicket) {
        return false;
    }
}
