package backend.entity;
import javax.persistence.*;
//@PersistenceContext
@Entity
@Table(name = "location")
public class Location {

    @Id @GeneratedValue
    @Column(name = "location_id")
    int location_id;

    @Column(name = "state")
    String state;

    @Column(name = "address")
    String address;

    @Column(name = "postcode")
    int postcode;


    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public Location() { }

    public Location(String state, String address, int postcode) {
        this.state = state;
        this.address = address;
        this.postcode = postcode;
    }


}
