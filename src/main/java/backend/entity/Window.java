package backend.entity;

public class Window {
    String departmentName;
    String state;
    int assetCount;
    int rank;

    public Window(String departmentName, String state, int asset_count, int rank) {
        this.departmentName = departmentName;
        this.state = state;
        this.assetCount = asset_count;
        this.rank = rank;
    }

    public Window() {
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAssetCount() {
        return assetCount;
    }

    public void setAssetCount(int assetCount) {
        this.assetCount = assetCount;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
