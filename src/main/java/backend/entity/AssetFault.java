package backend.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "asset_fault")
public class AssetFault {

    @Id
    @GeneratedValue
    @Column(name = "asset_failt_id")
    int asset_failt_id;

    public void setFault(Fault fault) {
        this.fault = fault;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    @ManyToOne
    @JoinColumn(name = "fault_id", insertable=false, updatable = false)
    Fault fault;

    @ManyToOne
    @JoinColumn(name = "asset_id", insertable=false, updatable = false)
    Asset asset;

    @Transient
    int fault_id;

    @Transient
    int asset_id;

    @Column(name = "time_of_failure")
    Date time_of_failure;

    @Column(name = "fix_time")
    Date fix_time;

    @Column(name = "fixable")
    Boolean fixable;

    public AssetFault(Fault fault, Asset asset, int fault_id, int asset_id, Date time_of_failure, Boolean fixable) {
        this.fault = fault;
        this.asset = asset;
        this.fault_id = fault_id;
        this.asset_id = asset_id;
        this.time_of_failure = time_of_failure;
        this.fixable = fixable;
    }

    public AssetFault(int asset_failt_id, Fault fault, Asset asset, Date time_of_failure, Date fix_time, Boolean fixable) {
        this.asset_failt_id = asset_failt_id;
        this.fault_id = fault.getFault_id();
        this.asset_id = asset.getAsset_id();
        this.time_of_failure = time_of_failure;
        this.fix_time = fix_time;
        this.fixable = fixable;
    }
//    public AssetFault(int asset_failt_id, Fault fault, Asset asset, Date time_of_failure, Date fix_time, Boolean fixable) {
//        this.asset_failt_id = asset_failt_id;
//        this.fault = fault;
//        this.asset = asset;
//        this.time_of_failure = time_of_failure;
//        this.fix_time = fix_time;
//        this.fixable = fixable;
//    }

    public AssetFault() {
    }

    public int getAsset_failt_id() {
        return asset_failt_id;
    }

    public void setAsset_failt_id(int asset_failt_id) {
        this.asset_failt_id = asset_failt_id;
    }

    public int getFault_id() {
        return fault_id;
    }

    public void setFault_id(int fault_id) {
        this.fault_id = fault_id;
    }

    public int getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(int asset_id) {
        this.asset_id = asset_id;
    }

    public Date getTime_of_failure() {
        return time_of_failure;
    }

    public void setTime_of_failure(Date time_of_failure) {
        this.time_of_failure = time_of_failure;
    }

    public Date getFix_time() {
        return fix_time;
    }

    public void setFix_time(Date fix_time) {
        this.fix_time = fix_time;
    }

    public Boolean getFixable() {
        return fixable;
    }

    public void setFixable(Boolean fixable) {
        this.fixable = fixable;
    }

    public Asset getAsset() {
        return asset;
    }

    public Fault getFault(){
        return fault;
    }


}
