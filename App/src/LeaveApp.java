package src;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeaveApp extends InfoClass{
    public String StudentID;
    public String ApplicationID;
    public Date appDate;
    public Date leaDate;
    public Date expRetDate;
    public Date ActRetDate;
    public String Reason;
    public String ManagerID;
    public Boolean isPass;
    public Date passDate;

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

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Date getLeaDate() {
        return leaDate;
    }

    public String getLeaDateStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(leaDate);
    }

    public void setLeaDate(Date leaDate) {
        this.leaDate = leaDate;
    }

    public Date getExpRetDate() {
        return expRetDate;
    }

    public String getExpDateStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(expRetDate);
    }

    public void setExpRetDate(Date expRetDate) {
        this.expRetDate = expRetDate;
    }

    public Date getActRetDate() {
        return ActRetDate;
    }

    public void setActRetDate(Date actRetDate) {
        ActRetDate = actRetDate;
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
