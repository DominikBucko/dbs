package backend.mapping;

public class Asset {
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

    private int asset_id;
    private String name;
    private String type;
    private String qr_code;
    private String asset_category;
    private int asset_department;
    private String status;
    private Department department;

    public Asset(int asset_id, String name, String type, String qr_code, String asset_category, int asset_department, String status, Department department) {
        this.asset_id = asset_id;
        this.name = name;
        this.type = type;
        this.qr_code = qr_code;
        this.asset_category = asset_category;
        this.asset_department = asset_department;
        this.status = status;
        this.department = department;
    }

}
