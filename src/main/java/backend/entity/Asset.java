package backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "asset")
public class Asset {
//    @Id
    public int getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(int asset_id) {
        this.asset_id = asset_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getAsset_category() {
        return asset_category;
    }

    public void setAsset_category(String asset_category) {
        this.asset_category = asset_category;
    }

    public int getAsset_department() {
        return asset_department;
    }

    public void setAsset_department(int asset_department) {
        this.asset_department = asset_department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Department getDepartment() {
        return department;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Id
    @GeneratedValue
    @Column(name = "asset_id")
    private int asset_id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "qr_code")
    private String qr_code;

    @Column(name = "asset_category")
    private String asset_category;

    private int asset_department;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "asset_department", insertable=false, updatable = false)
    private Department department;

    @Transient
    private int count;

    public Asset() {}

    public Asset(String name, String type, String qr_code, String asset_category, String status, Department department) {
        this.name = name;
        this.type = type;
        this.qr_code = qr_code;
        this.asset_category = asset_category;
        this.status = status;
        this.department = department;
    }

}
