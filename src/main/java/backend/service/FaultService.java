package backend.service;

import backend.entity.Fault;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

public class FaultService {
    public List<Fault> getAll() {
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
            e.printStackTrace();
        }
        return faults;
    }
}
