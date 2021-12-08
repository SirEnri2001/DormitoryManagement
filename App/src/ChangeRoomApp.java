package src;

import java.util.Date;

public class ChangeRoomApp extends InfoClass {
    public String StudentID;
    public String ApplicationID;
    public String Building;
    public String Room;
    public String BuildingTar;
    public String RoomTar;
    public Date appDate;
    public String Reason;
    public String ManagerID;
    public Boolean isPass;
    public Date passDate;
    public Date confirmDate;

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getApplicationID() {
        return ApplicationID;
    }

    public void setApplicationID(String applicationID) {
        ApplicationID = applicationID;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public String getBuildingTar() {
        return BuildingTar;
    }

    public void setBuildingTar(String buildingTar) {
        BuildingTar = buildingTar;
    }

    public String getRoomTar() {
        return RoomTar;
    }

    public void setRoomTar(String roomTar) {
        RoomTar = roomTar;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getManagerID() {
        return ManagerID;
    }

    public void setManagerID(String managerID) {
        ManagerID = managerID;
    }

    public Boolean getPass() {
        return isPass;
    }

    public void setPass(Boolean pass) {
        isPass = pass;
    }

    public Date getPassDate() {
        return passDate;
    }

    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }
}
