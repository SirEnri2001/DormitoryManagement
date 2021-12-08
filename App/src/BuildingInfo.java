package src;

import java.util.List;

public class BuildingInfo extends InfoClass{
    public String BuildingID;
    public int Capacity;
    public int Available;

    public String getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(String buildingID) {
        BuildingID = buildingID;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public int getAvailable() {
        return Available;
    }

    public void setAvailable(int available) {
        Available = available;
    }

    List<RoomInfo> RoomList;   //在DatabaseAccess里面的返回数据表属性中未返回
}
