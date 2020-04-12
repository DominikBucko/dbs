package backend.entity;

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

    private int department_id;
    private String department_name;
    private int department_location;
    private Location location;
}
