package backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "department")

public class Department {
    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public int getDepartment_location() {
        return department_location;
    }

    public void setDepartment_location(int department_location) {
        this.department_location = department_location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Department() {}

    public Department(int department_id, String department_name, int department_location, Location location) {
        this.department_id = department_id;
        this.department_name = department_name;
        this.department_location = department_location;
        this.location = location;
    }

    @Id @GeneratedValue
    @Column(name = "department_id")
    private int department_id;

    @Column(name = "department_name")
    private String department_name;

    @Column(name = "department_location")
    private int department_location;

    @ManyToOne
    @JoinColumn(name = "department_location")
    private Location location;
}
