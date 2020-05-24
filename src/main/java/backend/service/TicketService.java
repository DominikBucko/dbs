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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class TicketService {

    private static final Logger LOGGER = Logger.getLogger(TicketService.class.getName());

    public List<Ticket> getUserTickets(User current_user) {
        LOGGER.info("GETTING USER TICKETS");
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx;
        List returnedTickets;
        List<Ticket> ticketsDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        try {
            tx = session.beginTransaction();
            returnedTickets = session.createQuery("FROM backend.entity.Ticket ticket where ticket.user.login = :username " +
                    "order by ticket.time_accepted desc")
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
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return ticketsDTO;
    }

    public String exportToCsv() {
        LOGGER.info("EXPORTING TICKETS TO CSV");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        String filename = "/tmp/" + new Date().getTime() + "_" + "tickets" + ".csv";
        String file = "'" + filename + "'";
        try {
            PreparedStatement sql = conn.prepareStatement("COPY ticket TO "+ file +" WITH (FORMAT CSV , HEADER )");
            sql.execute();
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return filename;
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
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return mappedTickets;
    }

        public boolean updateTicket(Ticket newTicket) {
            LOGGER.info("UPDATING TICKET");
            NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("invoice_id", newTicket.getInvoice_id());
            parameterSource.addValue("asset_id", newTicket.getAsset().getAsset_id());
            parameterSource.addValue("user_id", newTicket.getUser().getUser_id());
            parameterSource.addValue("time_created", newTicket.getTime_created());
            parameterSource.addValue("time_accepted", newTicket.getTime_accepted());
            parameterSource.addValue("time_assigned", newTicket.getTime_assigned());
            parameterSource.addValue("time_returned", newTicket.getTime_returned());
            try {
                jdbcTemplate.update("UPDATE asset_manager.public.ticket " +
                        "SET asset_info = :asset_id, user_info = :user_id, time_created = :time_created, time_accepted = :time_accepted, time_assigned = :time_assigned, time_returned = :time_returned" +
                        " WHERE invoice_id = :invoice_id", parameterSource);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.warning(e.getMessage());
                return false;
            }
            return true;
        }

    public boolean saveTicket(Ticket newTicket) {
        LOGGER.info("SAVING TICKET");
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("asset_id", newTicket.getAsset().getAsset_id());
        parameterSource.addValue("user_id", newTicket.getUser().getUser_id());
        parameterSource.addValue("time_created", newTicket.getTime_created());
        parameterSource.addValue("time_accepted", newTicket.getTime_accepted());
        parameterSource.addValue("time_assigned", newTicket.getTime_assigned());
        parameterSource.addValue("time_returned", newTicket.getTime_returned());
        try {
            jdbcTemplate.update("INSERT INTO asset_manager.public.ticket (asset_info, user_info, time_created, time_accepted, time_assigned, time_returned)" +
                    " VALUES (:asset_id, :user_id, :time_created, :time_accepted, :time_assigned, :time_returned)", parameterSource);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
            return false;
        }
        return true;

//       Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().getCurrentSession();
//        try {
//            Transaction tx = session.beginTransaction();
//            session.save(newTicket);
//            tx.commit();
//        }
//        catch (HibernateException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
    }

    public Collection<Ticket> getUnapprovedTickets(String type, String name, int limit, int offset) {

        LOGGER.info("GETTING UNAPPROVED TICKETS");

        String delims = "[ ]";

        List<String> names = new ArrayList<>(Arrays.asList(name.split(delims)));
        if (names.size() < 2) {
            names.add("");
            names.add("");
        }
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().getCurrentSession();
        List returned = null;
        List<Ticket> tickets;
        Query query;
        
        try {
            Transaction tx = session.beginTransaction();
            if (type.equals("approve")) {
                query = session.createQuery("FROM backend.entity.Ticket t where t.time_accepted = null and lower(t.user.first_name) like :fname and lower(t.user.surname) like :lname");
            } else {
                query = session.createQuery("FROM backend.entity.Ticket t where t.time_assigned != null and t.time_returned = null and lower(t.user.first_name) like :fname and lower(t.user.surname) like :lname");
            }
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            query.setString("fname", names.get(0) + "%");
            query.setString("lname", names.get(1)+ "%");
            returned = query.list();
            tx.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
        return mapList(returned);
    }

    public List<Ticket> mapList(List returned) {
        List<Ticket> mapped = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (int i = 0; i < returned.size(); i++) {
            mapped.add(mapper.map(returned.get(i), Ticket.class));
        }
        return mapped;
    }

    public void deleteTicket(Ticket currentTicket) {
        LOGGER.info("DELETING TICKETS");
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ConnectionService.getConnectionService().getCustomDataSource());
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("invoice_id", currentTicket.getInvoice_id());
        try {
            jdbcTemplate.update("DELETE from ticket where invoice_id = :invoice_id", parameterSource);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
    }

    public int countUnapprovedTickets(String type, String name) {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        ResultSet rs;

        String delims = "[ ]";

        List<String> names = new ArrayList<>(Arrays.asList(name.split(delims)));
        if (names.size() < 2) {
            names.add("");
            names.add("");
        }

        try {

            if (type.equals("approve")) {
                PreparedStatement sql = conn.prepareStatement(
                        "select count(invoice_id) as POCET\n" +
                                "from \"ticket\" " +
                                "JOIN \"user\" u on user_info = u.user_id "+
                                "where time_accepted is null " +
                                "and time_returned is null " +
                                "and lower(u.first_name) like ? and lower(u.surname) like ?;"
                );
                sql.setString(1, names.get(0) + "%");
                sql.setString(2, names.get(1) + "%");
                rs = sql.executeQuery();
                rs.next();
                return rs.getInt("POCET");

            } else {
                PreparedStatement sql = conn.prepareStatement(
                        "select count(invoice_id) as POCET\n" +
                                "from \"ticket\" " +
                                "JOIN \"user\" u on user_info = u.user_id "+
                                "where time_assigned is not null " +
                                "and time_returned is null " +
                                "and lower(u.first_name) like ? and lower(u.surname) like ?;"
                );
                sql.setString(1, names.get(0) + "%");
                sql.setString(2, names.get(1) + "%");
                rs = sql.executeQuery();
                rs.next();
                return rs.getInt("POCET");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
        return 0;
    }
}
