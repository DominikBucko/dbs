package backend.service;

import backend.entity.Ticket;
import backend.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketService {
    public List<Ticket> getUserTickets(User current_user) {
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx;
        List returnedTickets;
        List<Ticket> ticketsDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        try {
            tx = session.beginTransaction();
            returnedTickets = session.createQuery("FROM backend.entity.Ticket ticket where ticket.user.login = :username ")
                    .setFirstResult(0)
                    .setMaxResults(1000)
                    .setParameter("username", current_user.getLogin())
                    .list();
            tx.commit();

            for (int i = 0; i < returnedTickets.size(); i++) {
                ticketsDTO.add(mapper.map(returnedTickets.get(i), Ticket.class));
            }
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
        return ticketsDTO;
    }

    public List<Ticket> getAllHib(){
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx = null;
        List <Ticket> tickets = null;
        List <Ticket> mappedTickets = null;
        ModelMapper modelMapper = new ModelMapper();

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM backend.entity.Ticket");
            query.setFirstResult(0);
            query.setMaxResults(1000);
            tickets = query.list();
            tx.commit();

            for (int i = 0; i < tickets.size(); i++) {
                mappedTickets.add(modelMapper.map(tickets.get(i), Ticket.class));
            }
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        return mappedTickets;
    }
    public boolean saveTicket(Ticket newTicket) {
//        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
//        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
//        parameterSource.addValue("asset_id", newTicket.getAsset().getAsset_id());
//        parameterSource.addValue("user_id", newTicket.getUser().getUser_id());
//        parameterSource.addValue("time_created", newTicket.getTime_created());
//        parameterSource.addValue("time_accepted", newTicket.getTime_accepted());
//        parameterSource.addValue("time_assigned", newTicket.getTime_assigned());
//        parameterSource.addValue("time_returned", newTicket.getTime_returned());
//        parameterSource.addValue("comment", newTicket.getComment());
//        try {
//            jdbcTemplate.update("INSERT INTO asset_manager.public.ticket (asset_info, user_info, time_created, time_accepted, time_assigned, time_returned, comment)" +
//                    " VALUES (:asset_id, :user_id, :time_created, :time_accepted, :time_assigned, :time_returned, :comment)", parameterSource);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
        return true;
//       Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().getCurrentSession();
//        try {
//            Transaction tx = session.beginTransaction();
//            session.persist(newTicket);
//            tx.commit();
//        }
//        catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;

    }
}
