package backend.entity;

public class Window {
    String department_name;
    String state;
    int asset_count;
    int rank;

    public Window(String department_name, String state, int asset_count, int rank) {
        this.department_name = department_name;
        this.state = state;
        this.asset_count = asset_count;
        this.rank = rank;
    }

    public Window() {
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAsset_count() {
        return asset_count;
    }

    public void setAsset_count(int asset_count) {
        this.asset_count = asset_count;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
