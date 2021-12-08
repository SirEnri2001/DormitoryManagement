package src;

public class StudentInfo extends InfoClass {
    public String StudentID;
    public String Name;
    public String Phone;
    public int Degree;
    public String Major;
    public int Grade;
    public int classID;
    public int Sex;
    public String password;
    public String RoomID;
    public String BuildingID;

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getDegree() {
        return Degree;
    }

    public void setDegree(int degree) {
        Degree = degree;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }



    public int getSex() {
        return Sex;
    }


    public void setSex(int sex) {
        Sex = sex;
    }


    public String getSexStr() {
        if(Sex==0){
            return "女";
        }else if(Sex==1){
            return "男";
        }else{
            return "其他";
        }
    }

    public void setSexStr(String sex){
        this.Sex = sex.equals("男")?1:0;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
