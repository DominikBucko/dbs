package backend.entity;

public class Location {
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

    int location_id;
    String state;
    String address;
    int postcode;

}
