package backend.entity;

public class User {

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public int getUser_department() {
        return user_department;
    }

    public void setUser_department(int user_department) {
        this.user_department = user_department;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public User() {

    }

    public User(String first_name,
                String surname,
                String city,
                String address,
                int postcode,
                int user_department,
                String login,
                String password,
                Boolean is_admin) {
        this.first_name = first_name;
        this.surname = surname;
        this.city = city;
        this.address = address;
        this.postcode = postcode;
        this.user_department = user_department;
        this.login = login;
        this.password = password;
        this.is_admin = is_admin;
    }

    int user_id;
    String first_name;
    String surname;
    String city;
    String address;
    int postcode;
    int user_department;
    Department department;
    String login;
    String password;
    Boolean is_admin;
    int ticketCount;
}
