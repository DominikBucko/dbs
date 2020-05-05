package backend.service;

import backend.entity.Ticket;
import backend.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TicketService {
    public List<Ticket> getUserTickets(User current_user) {
        return new ArrayList<>();
    }
    public List<Ticket> getAllHib(){
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx = null;
        List <Ticket> tickets = null;

        try {
            tx = session.beginTransaction();
            tickets = session.createQuery("FROM backend.entity.Ticket").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        return tickets;
    }
    public boolean saveTicket(Ticket newTicket) {
        return false;
    }
}
