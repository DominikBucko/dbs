package backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "fault")
public class Fault {
    public void setFault_id(int fault_id) {
        this.fault_id = fault_id;
    }

    public void setType_of_fault(String type_of_fault) {
        this.type_of_fault = type_of_fault;
    }
    public int getFault_id() {
        return fault_id;
    }

    public String getType_of_fault() {
        return type_of_fault;
    }


    @Id
    @GeneratedValue
    @Column(name = "fault_id")
    private int fault_id;

    @Column(name = "type_of_fault")
    private String type_of_fault;

    public Fault(int fault_id, String type_of_fault) {
        this.fault_id = fault_id;
        this.type_of_fault = type_of_fault;
    }
    public Fault(String type_of_fault) {
        this.type_of_fault = type_of_fault;
    }
    public Fault() {
    }

}
