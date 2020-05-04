package backend.entity;

import java.sql.Date;

public class Ticket {

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public Date getTime_created() {
        return time_created;
    }

    public void setTime_created(Date time_created) {
        this.time_created = time_created;
    }

    public Date getTime_accepted() {
        return time_accepted;
    }

    public void setTime_accepted(Date time_accepted) {
        this.time_accepted = time_accepted;
    }

    public Date getTime_assigned() {
        return time_assigned;
    }

    public void setTime_assigned(Date time_assigned) {
        this.time_assigned = time_assigned;
    }

    public Date getTime_returned() {
        return time_returned;
    }

    public void setTime_returned(Date time_returned) {
        this.time_returned = time_returned;
    }

    public int getUser_info() {
        return user_info;
    }

    public void setUser_info(int user_info) {
        this.user_info = user_info;
    }

    public int getAsset_info() {
        return asset_info;
    }

    public void setAsset_info(int asset_info) {
        this.asset_info = asset_info;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    int invoice_id;
    Date time_created;
    Date time_accepted;
    Date time_assigned;
    Date time_returned;
    int user_info;
    int asset_info;
    User user;
    Asset asset;
    String comment;

    public Ticket() {}

    public Ticket(User currentUser, Asset asset) {
        this.user = currentUser;
        this.user_info = currentUser.getUser_id();
        this.asset = asset;
        this.asset_info = asset.getAsset_id();
        this.time_created = new Date(System.currentTimeMillis());
    }
}
