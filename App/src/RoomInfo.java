package src;

public class RoomInfo {
    public String RoomID;
    public String BuildingID;
    public int Capacity;
    public float elecUsage;
    public float elecBalance;
    public boolean elecAvailable;//在DatabaseAccess里面的返回数据表属性中未返回
    public float waterUsage;
    public float waterBalance;
    public boolean waterAvailable;//在DatabaseAccess里面的返回数据表属性中未返回
    public int Available;

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public float getElecUsage() {
        return elecUsage;
    }

    public void setElecUsage(float elecUsage) {
        this.elecUsage = elecUsage;
    }

    public float getElecBalance() {
        return elecBalance;
    }

    public void setElecBalance(float elecBalance) {
        this.elecBalance = elecBalance;
    }

    public float getWaterUsage() {
        return waterUsage;
    }

    public void setWaterUsage(float waterUsage) {
        this.waterUsage = waterUsage;
    }

    public float getWaterBalance() {
        return waterBalance;
    }

    public void setWaterBalance(float waterBalance) {
        this.waterBalance = waterBalance;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(String buildingID) {
        BuildingID = buildingID;
    }

    public int getAvailable() {
        return Available;
    }

    public void setAvailable(int available) {
        Available = available;
    }

    public String Description;
    public String toString(){
        return "RoomID: "+RoomID+" BuildingID: "+BuildingID+" Available: "+Available;
    }
}
