package backend.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ticket")
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

//    public String getComment() {
//        return comment;
//    }
//
//    public void setComment(String comment) {
//        this.comment = comment;
//    }
//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    int invoice_id;

    @Column(name = "time_created")
    Date time_created;

    @Column(name = "time_accepted")
    Date time_accepted;

    @Column(name = "time_assigned")
    Date time_assigned;

    @Column(name = "time_returned")
    Date time_returned;


    @Transient
    int user_info;

    @Transient
    int asset_info;

    @ManyToOne
    @JoinColumn(name = "user_info", insertable=false, updatable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "asset_info", insertable=false, updatable = false)
    Asset asset;


    @Column(name = "comment")
    String comment;

    public Ticket() {}

    public Ticket(User currentUser, Asset asset) {
        this.user = currentUser;
        this.user_info = currentUser.getUser_id();
        this.asset = asset;
        this.asset_info = asset.getAsset_id();
        this.time_created = new Date(System.currentTimeMillis());
    }

    public Ticket(User currentUser, Asset asset, Date time_created, Date time_accepted, Date time_assigned,
                  Date time_returned, String comment) {
        this.user = currentUser;
        this.user_info = currentUser.getUser_id();
        this.asset = asset;
        this.asset_info = asset.getAsset_id();
        this.time_created = time_created;
        this.time_accepted = time_accepted;
        this.time_assigned = time_assigned;
        this.time_returned = time_returned;
        this.comment = comment;

    }
}
