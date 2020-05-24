package backend.service;

import backend.entity.Fault;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FaultService {
    private static final Logger LOGGER = Logger.getLogger(FaultService.class.getName());

    public List<Fault> getAll() {
        LOGGER.info("GETTING ALL FAULTS");
        Session session = SessionFactoryProvider.getSessionFactoryProvider().getSessionFactory().openSession();
        Transaction tx;
        List returned;
        List<Fault> faults = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        try {
            tx = session.beginTransaction();
            returned = session.createQuery("FROM Fault ").list();
            for (int i = 0; i < returned.size(); i++) {
                faults.add(mapper.map(returned.get(i), Fault.class));
            }
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return faults;
    }

    public int countAll() {
        LOGGER.info("COUNTING ALL FAULTS");
        Connection conn = ConnectionService.getConnectionService().getConnection();
        ResultSet rs;

        try {
            PreparedStatement sql = conn.prepareStatement(
                    "select count(fault_id) as POCET\n" +
                            "from fault"
            );
            rs = sql.executeQuery();
            rs.next();
            return rs.getInt("POCET");
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
