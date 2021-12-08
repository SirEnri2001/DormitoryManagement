package src;

import java.util.*;

public class StudentUser implements IStudentUser{
    private StudentInfo studentInfo;
    private BuildingInfo buildingInfo;
    private RoomInfo roomInfo;
    private ClassInfo classInfo;
    private ManagerInfo managerInfo;
    private final LoginAuthentication loginAuthentication = new LoginAuthentication();
    private final byte[] lockStudent = new byte[0];
    private final byte[] lockBuilding = new byte[0];
    private final byte[] lockRoom = new byte[0];
    private final byte[] lockClass = new byte[0];
    private final byte[] lockManager = new byte[0];

// Account
    @Override
    public Boolean StuLoginAuth(String Username, String Password, ISetProgress controller) {
        double progress = 0.1;
        controller.setProgress(progress);
        // Null input check
        if(Username.isEmpty() || Password.isEmpty())
            return false;

        // Log in Authentication
        loginAuthentication.StartStudentAuthentication(Username,Password);
        progress+=0.2;
        controller.setProgress(progress);
        String result = loginAuthentication.GetStatus();
        progress+=0.2;
        controller.setProgress(progress);
        System.out.println(result);
        int times = 5;
        while(!result.equals("student")&&times-->0){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = loginAuthentication.GetStatus();
            progress+=0.1;
            controller.setProgress(progress);
        }
        //Initialise m_studentID
        if(!result.equals("student")) {
            try {
                ThreadTool.login_mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }


        // Initialise studentUser
        Query<StudentInfo> query = new Query<>(StudentInfo.class);
        query.setEqualValue("StudentID", Username);
        List<StudentInfo> studentInfoList = query.ExecuteQuery();
        if(studentInfoList.isEmpty())
        {
            query = new Query<>(StudentInfo.class);
            query.setEqualValue("Name", Username);
            studentInfoList = query.ExecuteQuery();
        }
        if(studentInfoList.isEmpty())
        {
            query = new Query<>(StudentInfo.class);
            query.setEqualValue("Phone", Username);
            studentInfoList = query.ExecuteQuery();
        }
        studentInfo = studentInfoList.get(0);
        Thread t = new Thread(this::Preload);
        t.start();
        return true;
    }

    public Boolean StuLoginAuth(String Username, String Password) {
        // Null input check
        if(Username.isEmpty() || Password.isEmpty())
            return false;

        // Log in Authentication
        loginAuthentication.StartStudentAuthentication(Username,Password);
        String result = loginAuthentication.GetStatus();
        System.out.println(result);
        int times = 5;
        while(!result.equals("student")&&times-->0){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = loginAuthentication.GetStatus();
        }
        //Initialise m_studentID
        if(!result.equals("student")) {
            try {
                ThreadTool.login_mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }


        // Initialise studentUser
        Query<StudentInfo> query = new Query<>(StudentInfo.class);
        query.setEqualValue("StudentID", Username);
        List<StudentInfo> studentInfoList = query.ExecuteQuery();
        if(studentInfoList.isEmpty())
        {
            query = new Query<>(StudentInfo.class);
            query.setEqualValue("Name", Username);
            studentInfoList = query.ExecuteQuery();
        }
        if(studentInfoList.isEmpty())
        {
            query = new Query<>(StudentInfo.class);
            query.setEqualValue("Phone", Username);
            studentInfoList = query.ExecuteQuery();
        }
        studentInfo = studentInfoList.get(0);
        Thread t = new Thread(this::Preload);
        t.start();
        return true;
    }

    private void Preload(){
        try{
            // preload roomInfo
            if(studentInfo.RoomID!=null){
                Query<RoomInfo> roomInfoQuery = new Query<>(RoomInfo.class);
                roomInfoQuery.setEqualValue("RoomID",studentInfo.RoomID);
                roomInfoQuery.setEqualValue("BuildingID",studentInfo.BuildingID);
                synchronized (lockRoom){
                    roomInfo = roomInfoQuery.ExecuteQuery().get(0);
                }
            }

            // preload buildingInfo
            if(studentInfo.BuildingID!=null){
                Query<BuildingInfo> buildingInfoQuery = new Query<>(BuildingInfo.class);
                buildingInfoQuery.setEqualValue("BuildingID",studentInfo.BuildingID);
                synchronized (lockBuilding){
                    buildingInfo = buildingInfoQuery.ExecuteQuery().get(0);
                }
            }

            // preload classInfo
            if(studentInfo.classID != 0 ){
                Query<ClassInfo> classInfoQuery = new Query<>(ClassInfo.class);
                classInfoQuery.setEqualValue("Degree",studentInfo.Degree);
                classInfoQuery.setEqualValue("Grade", studentInfo.Grade);
                classInfoQuery.setEqualValue("classID",studentInfo.classID);
                classInfoQuery.setEqualValue("Major",studentInfo.Major);
                synchronized (lockClass){
                    classInfo = classInfoQuery.ExecuteQuery().get(0);
                }
            }

            // preload managerInfo
            if(classInfo != null){
                Query<ManagerInfo> managerInfoQuery = new Query<>(ManagerInfo.class);
                managerInfoQuery.setEqualValue("BuildingID",studentInfo.BuildingID);
                synchronized (lockManager){
                    managerInfo = managerInfoQuery.ExecuteQuery().get(0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Boolean UpdatePassword(String pre, String Password) {
        // Null input check
        if(pre.isEmpty() || Password.isEmpty())
            return false;

        // Old password check
        if(!studentInfo.password.equals(pre)&&!studentInfo.password.equals(SHAUtil.SHAMD5(pre)))
            return false;

        // Update database
        Modify<StudentInfo> studentInfoModify = new Modify<>(StudentInfo.class);
        studentInfoModify.setEqualValue("StudentID",studentInfo.StudentID);
        studentInfoModify.setUpdateValue("password",Password);
        studentInfoModify.ExecuteUpdate();
        Query<StudentInfo> studentInfoQuery = new Query<>(StudentInfo.class);
        studentInfoQuery.setEqualValue("StudentID",studentInfo.StudentID);
        if(studentInfoQuery.ExecuteQuery().get(0).password.equals(Password))
        {//Check if password has been updated correctly
            studentInfo.password = Password;
            return true;
        }
        else return false;
    }

    @Override
    public void LogOut(){
        synchronized (lockStudent){studentInfo = null;}
        synchronized (lockBuilding){buildingInfo = null;}
        synchronized (lockRoom){roomInfo = null;}
        synchronized (lockManager){managerInfo = null;}
        synchronized (lockClass){classInfo = null;}
        loginAuthentication.LogOut();
    }

    public StudentInfo getStudentInfo(){
        return studentInfo;
    }

    public BuildingInfo getBuildingInfo(){
        return buildingInfo;
    }

// Query
    @Override
    public Map<String, String> QueryStudentInfo() {

        // Set result
        String studentClass = studentInfo.classID == 0? "未分班":String.valueOf(studentInfo.classID);
        String buildingID;
        String roomID;
        if(studentInfo.BuildingID == null)
            buildingID = roomID = "未入住";
        else{
            buildingID = studentInfo.BuildingID;
            roomID = studentInfo.RoomID;

        }
        Map<String,String> result = new HashMap<>();
        result.put("Name", studentInfo.Name);
        result.put("StudentID", studentInfo.StudentID);
        result.put("Class", studentClass);
        result.put("BuildingID", buildingID);
        result.put("RoomID", roomID);
        return result;
    }

    @Override
    public Map<String, String> QueryBuildingInfo() {
        String buildingID;
        String capacity;
        String available;
        buildingID = studentInfo.BuildingID;
        if(buildingID == null){
            buildingID = "未入住";
            capacity = available = "0";
        }
        else{
                synchronized (lockBuilding){
                    buildingID = String.valueOf(buildingInfo.BuildingID);
                    capacity = String.valueOf(buildingInfo.Capacity);
                    available = String.valueOf(buildingInfo.Available);
            }
        }

        Map<String,String> result = new HashMap<>();
        result.put("BuildingID", buildingID);
        result.put("Capacity", capacity);
        result.put("Available", available);

        return result;
    }

    @Override
    public RoomInfo QueryRoomInfo() {
        synchronized (lockRoom) {
            return roomInfo;
        }
    }

    @Override
    public RoomPaymentInfo QueryRoomPaymentInfo() {
        synchronized (lockRoom){
        if(roomInfo == null)
            return null;
        }

        Query<RoomPaymentInfo> roomPaymentInfoQuery = new Query<>(RoomPaymentInfo.class);
        roomPaymentInfoQuery.setEqualValue("RoomID", roomInfo.RoomID);
        roomPaymentInfoQuery.setEqualValue("BuildingID",roomInfo.BuildingID);
        roomPaymentInfoQuery.setEqualValue("StudentID",studentInfo.StudentID);
        roomPaymentInfoQuery.setIsNull("PayDate");
        List<RoomPaymentInfo> roomPaymentInfoList = roomPaymentInfoQuery.ExecuteQuery();
        if(roomPaymentInfoList.isEmpty()) {
            RoomPaymentInfo result = new RoomPaymentInfo();
            result.Amount = 0;
            result.BuildingID = roomInfo.BuildingID;
            result.RoomID = roomInfo.RoomID;
            result.RoomCapacity = roomInfo.Capacity;
            return result;
        }
        else return roomPaymentInfoList.get(0);
    }

    @Override
    public boolean HasMoveInApp() {
       Query<MoveInApp> moveInAppQuery = new Query<>(MoveInApp.class);
       synchronized (lockStudent) {
           moveInAppQuery.setEqualValue("StudentID",studentInfo.StudentID);
       }
       moveInAppQuery.setIsNull("confirmDate");
       return !moveInAppQuery.ExecuteQuery().isEmpty();
    }

    @Override
    public int MoveInAppStatus() {
        Query<MoveInApp> moveInAppQuery = new Query<>(MoveInApp.class);
        synchronized (lockStudent) {moveInAppQuery.setEqualValue("StudentID",studentInfo.StudentID);}
        moveInAppQuery.setIsNull("confirmDate");
        moveInAppQuery.setIsNull("isPass");
        if(!moveInAppQuery.ExecuteQuery().isEmpty())
            return 0;
        else{
            moveInAppQuery.setNotNull("isPass");
            return moveInAppQuery.ExecuteQuery().get(0).isPass ? 2 : 1;
        }
    }

    @Override
    public boolean HasMoveOutApp() {
        Query<MoveOutApp> moveOutAppQuery = new Query<>(MoveOutApp.class);
        synchronized (lockStudent) {moveOutAppQuery.setEqualValue("StudentID",studentInfo.StudentID);}
        moveOutAppQuery.setIsNull("confirmDate");
        return !moveOutAppQuery.ExecuteQuery().isEmpty();
    }

    @Override
    public int MoveOutAppStatus() {
        Query<MoveOutApp> moveOutAppQuery = new Query<>(MoveOutApp.class);
        synchronized (lockStudent) {moveOutAppQuery.setEqualValue("StudentID",studentInfo.StudentID);}
        moveOutAppQuery.setIsNull("confirmDate");
        moveOutAppQuery.setIsNull("isPass");
        List<MoveOutApp> moveOutAppList = moveOutAppQuery.ExecuteQuery();
        if(!moveOutAppList.isEmpty())
            return 0;
        else{
            moveOutAppQuery.setNotNull("isPass");
            moveOutAppList = moveOutAppQuery.ExecuteQuery();
            if(moveOutAppList.isEmpty()){
                return 0;
            }
            return moveOutAppList.get(0).isPass? 2 : 1;

        }
    }

    @Override
    public boolean HasLeaveApp() {
        Query<LeaveApp> leaveAppQuery = new Query<>(LeaveApp.class);
        synchronized (lockStudent) {leaveAppQuery.setEqualValue("StudentID", studentInfo.StudentID);}
        leaveAppQuery.setIsNull("ActRetDate");
        return !leaveAppQuery.ExecuteQuery().isEmpty();
    }

    @Override
    public int LeaveAppStatus() {
        Query<LeaveApp> leaveAppQuery = new Query<>(LeaveApp.class);
        synchronized (lockStudent) {leaveAppQuery.setEqualValue("StudentID",studentInfo.StudentID);}
        leaveAppQuery.setIsNull("ActRetDate");
        leaveAppQuery.setIsNull("isPass");
        if(!leaveAppQuery.ExecuteQuery().isEmpty())
            return 0;
        else{
            leaveAppQuery.setNotNull("isPass");
            return leaveAppQuery.ExecuteQuery().get(0).isPass? 2 : 1;
        }
    }

    @Override
    public boolean HasChangeRoomApp() {
        Query<ChangeRoomApp> changeRoomAppQuery = new Query<>(ChangeRoomApp.class);
        synchronized (lockStudent) {
            changeRoomAppQuery.setEqualValue("StudentID",studentInfo.StudentID);
            changeRoomAppQuery.setNotNull("confirmDate");
        }
        changeRoomAppQuery.setIsNull("confirmDate");
        System.out.println(!changeRoomAppQuery.ExecuteQuery().isEmpty());
        return !changeRoomAppQuery.ExecuteQuery().isEmpty();
    }

    @Override
    public int ChangRoomAppStatus() {
        Query<ChangeRoomApp> changeRoomAppQuery = new Query<>(ChangeRoomApp.class);
        synchronized (lockStudent) {changeRoomAppQuery.setEqualValue("StudentID",studentInfo.StudentID);}
        changeRoomAppQuery.setIsNull("confirmDate");
        changeRoomAppQuery.setIsNull("isPass");
        if(!changeRoomAppQuery.ExecuteQuery().isEmpty())
            return 0;
        else{
            changeRoomAppQuery.setNotNull("isPass");
            return changeRoomAppQuery.ExecuteQuery().get(0).isPass? 2: 1;
        }
    }

    @Override
    public List<RoomInfo> AvailableRoomQuery() {
        Query<RoomInfo> roomInfoQuery = new Query<>(RoomInfo.class);
        roomInfoQuery.setMinValue("Available", 1);
        return roomInfoQuery.ExecuteQuery();
    }

    @Override
    public LeaveApp QueryLeaApp() {
        Query<LeaveApp> leaveAppQuery = new Query<>(LeaveApp.class);
        synchronized (lockStudent){leaveAppQuery.setEqualValue("StudentID",studentInfo.StudentID);}
        leaveAppQuery.setIsNull("ActRetDate");
        return leaveAppQuery.ExecuteQuery().get(0);
    }

// Application
    private boolean availableRoomCheck(String building, String room){
        // Null input check
        if(building.isEmpty() || room.isEmpty())
            return false;

        // LegalCheck
        Query<BuildingInfo> buildingInfoQuery = new Query<>(BuildingInfo.class);
        buildingInfoQuery.setEqualValue("BuildingID",building);
        if(buildingInfoQuery.ExecuteQuery().isEmpty())
            return false;

        Query<RoomInfo> roomInfoQuery = new Query<>(RoomInfo.class);
        roomInfoQuery.setEqualValue("BuildingID", building);
        roomInfoQuery.setEqualValue("RoomID",room);
        if(roomInfoQuery.ExecuteQuery().isEmpty())
            return false;
        return roomInfoQuery.ExecuteQuery().get(0).Available > 0;
    }

    @Override
    public boolean MoveInApp(String b_id,String r_id,String Reason) {
        b_id = b_id.toUpperCase();
        r_id = r_id.toUpperCase();
        if(!availableRoomCheck(b_id,r_id))
            return false;

        // Prepare required values
        // Prepare applicationID
        String appID = UUID.randomUUID().toString().replaceAll("-","").substring(0,16).toUpperCase();
        Query<MoveInApp> moveInAppQuery = new Query<>(MoveInApp.class);
        moveInAppQuery.setEqualValue("ApplicationID", appID);
        while(!moveInAppQuery.ExecuteQuery().isEmpty())
            appID = UUID.randomUUID().toString().replaceAll("-","").substring(0,16).toUpperCase();

        // Do the Insert
        Modify<MoveInApp> moveInAppModify = new Modify<>(MoveInApp.class);
        synchronized (lockStudent) {moveInAppModify.setUpdateValue("StudentID", studentInfo.StudentID);}
        moveInAppModify.setUpdateValue("ApplicationID", appID);
        moveInAppModify.setUpdateValue("Building", b_id);
        moveInAppModify.setUpdateValue("Room", r_id);
        moveInAppModify.setUpdateValue("Reason", Reason);
        moveInAppModify.setUpdateValue("appDate",new Date());
        moveInAppModify.setUpdateNull("isPass");
        moveInAppModify.setUpdateNull("passDate");
        moveInAppModify.setUpdateNull("confirmDate");
        moveInAppModify.setUpdateNull("ManagerID");

        return moveInAppModify.ExecuteInsert() > 0;
    }

    @Override
    public boolean MoveOutApp(String Reason){
        // Check if he/she has a room
        // if not, return false
        synchronized (lockStudent) {
            if (studentInfo.RoomID == null)
                return false;
        }
        // Prepare required values
        // Prepare applicationID
        String appID = UUID.randomUUID().toString().replaceAll("-","").substring(0,16).toUpperCase();
        Query<MoveOutApp> moveOutAppQuery = new Query<>(MoveOutApp.class);
        moveOutAppQuery.setEqualValue("ApplicationID", appID);
        while(!moveOutAppQuery.ExecuteQuery().isEmpty())
            appID = UUID.randomUUID().toString().replaceAll("-","").substring(0,16).toUpperCase();

        System.out.println(appID);

        // Do the Insert
        Modify<MoveOutApp> moveOutAppModify = new Modify<>(MoveOutApp.class);
        moveOutAppModify.setUpdateValue("StudentID", studentInfo.StudentID);
        moveOutAppModify.setUpdateValue("ApplicationID", appID);
        moveOutAppModify.setUpdateValue("Building", studentInfo.BuildingID);
        moveOutAppModify.setUpdateValue("Room", studentInfo.RoomID);
        moveOutAppModify.setUpdateValue("Reason", Reason);
        moveOutAppModify.setUpdateValue("appDate",new Date());
        moveOutAppModify.setUpdateNull("isPass");
        moveOutAppModify.setUpdateNull("passDate");
        moveOutAppModify.setUpdateNull("confirmDate");
        moveOutAppModify.setUpdateNull("ManagerID");

        return moveOutAppModify.ExecuteInsert() > 0;
    }

    @Override
    public boolean LeaveApp(int lea_year, int lea_month, int lea_day, int back_year, int back_month, int back_day, String reason) {
        // Pre process input
        lea_year -= 1900;
        lea_month--;
        back_year -= 1900;
        back_month--;

        // Date check
        if(!(isDateValid(lea_year, lea_month, lea_day) && isDateValid(back_year, back_month, back_day)))
            return false;
        Date leaDate = new Date(lea_year, lea_month, lea_day);
        Date expRetDate = new Date(back_year, back_month, back_day);
        if(leaDate.after(expRetDate))
            return false;

        // Prepare required values
        // Prepare applicationID
        String appID = UUID.randomUUID().toString().replaceAll("-","").substring(0,16).toUpperCase();
        Query<LeaveApp> leaveAppQuery = new Query<>(LeaveApp.class);
        leaveAppQuery.setEqualValue("ApplicationID", appID);
        while(!leaveAppQuery.ExecuteQuery().isEmpty())
            appID = UUID.randomUUID().toString().replaceAll("-","").substring(0,16).toUpperCase();

        // Do the Insert
        Modify<LeaveApp> leaveAppModify = new Modify<>(LeaveApp.class);
        synchronized (lockStudent) {leaveAppModify.setUpdateValue("StudentID", studentInfo.StudentID);}
        leaveAppModify.setUpdateValue("ApplicationID", appID);
        leaveAppModify.setUpdateValue("Reason", reason);
        leaveAppModify.setUpdateValue("appDate",new Date());
        leaveAppModify.setUpdateValue("leaDate",leaDate);
        System.out.println(new Date(lea_year, lea_month, lea_day));
        leaveAppModify.setUpdateValue("expRetDate",expRetDate);
        System.out.println(new Date(back_year, back_month, back_day));
        leaveAppModify.setUpdateNull("ActRetDate");
        leaveAppModify.setUpdateNull("isPass");
        leaveAppModify.setUpdateNull("passDate");

        return leaveAppModify.ExecuteInsert() > 0;
    }
    private boolean isDateValid(int year, int month, int day){
        if(year<0 || month<0 || month > 11 || day<0 || day > 31)
            return false;
        int[] dayList = new int[]{31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        boolean isLeapYear;
        if((year + 1900) % 100 == 0)
            isLeapYear = (year + 1900) % 400 == 0;
        else
            isLeapYear = (year + 1900) % 4 == 0;
        dayList[1] = isLeapYear?29:28;
        if(day > dayList[month - 1])
            return false;
        Date today = new Date();
        Date input = new Date(year, month, day);
        return (input.getTime() > today.getTime());
    }

    @Override
    public boolean ChangeRoomApp(String b_id, String r_id, String reason) {
        b_id = b_id.toUpperCase();
        r_id = r_id.toUpperCase();
        if(!availableRoomCheck(b_id,r_id))
            return false;

        if(b_id.equals(studentInfo.BuildingID) && r_id.equals(studentInfo.RoomID))
            return false;

        // Prepare required values
        // Prepare applicationID
        String appID = UUID.randomUUID().toString().replaceAll("-","").substring(0,16).toUpperCase();
        Query<ChangeRoomApp> changeRoomAppQuery = new Query<>(ChangeRoomApp.class);
        changeRoomAppQuery.setEqualValue("ApplicationID", appID);
        while(!changeRoomAppQuery.ExecuteQuery().isEmpty())
            appID = UUID.randomUUID().toString().replaceAll("-","").substring(0,16).toUpperCase();

        // Do the insert
        Modify<ChangeRoomApp> changeRoomAppModify = new Modify<>(ChangeRoomApp.class);
        synchronized (lockStudent) {changeRoomAppModify.setUpdateValue("StudentID",studentInfo.StudentID);}
        changeRoomAppModify.setUpdateValue("ApplicationID",appID);
        changeRoomAppModify.setUpdateValue("Building",studentInfo.BuildingID);
        changeRoomAppModify.setUpdateValue("Room",studentInfo.RoomID);
        changeRoomAppModify.setUpdateValue("BuildingTar",b_id);
        changeRoomAppModify.setUpdateValue("RoomTar",r_id);
        changeRoomAppModify.setUpdateValue("appDate",new Date());
        changeRoomAppModify.setUpdateValue("Reason", reason);
        changeRoomAppModify.setUpdateNull("isPass");
        changeRoomAppModify.setUpdateNull("passDate");
        changeRoomAppModify.setUpdateNull("confirmDate");
        changeRoomAppModify.setUpdateNull("ManagerID");
        return changeRoomAppModify.ExecuteInsert() > 0;
    }

// Update
    @Override
    public boolean LeaveCancel() {
        Modify<LeaveApp> leaveAppModify = new Modify<>(LeaveApp.class);
        synchronized (lockStudent) {leaveAppModify.setEqualValue("StudentID",studentInfo.StudentID);}
        leaveAppModify.setIsNull("ActRetDate");
        leaveAppModify.setUpdateValue("ActRetDate", new Date());
        return (leaveAppModify.ExecuteUpdate() > 0);
    }

    @Override
    public boolean MoveInAppConfirm() {
        // Get App record
        Query<MoveInApp> moveInAppQuery = new Query<>(MoveInApp.class);
        synchronized (lockStudent) {moveInAppQuery.setEqualValue("StudentID", studentInfo.getStudentID());}
        moveInAppQuery.setIsNull("confirmDate");
        MoveInApp moveInApp = moveInAppQuery.ExecuteQuery().get(0);

        // Update moveInApp table
        Modify<MoveInApp> moveInAppModify = new Modify<>(MoveInApp.class);
        moveInAppModify.setEqualValue("ApplicationID", moveInApp.ApplicationID);
        moveInAppModify.setUpdateValue("confirmDate", new Date());

        // Check if moveInApp table is correctly updated. If not, return false
        if(moveInAppModify.ExecuteUpdate() == 0)
            return false;

        // Else, update student table
        Modify<StudentInfo> studentInfoModify = new Modify<>(StudentInfo.class);
        studentInfoModify.setEqualValue("StudentID",studentInfo.StudentID);
        studentInfoModify.setUpdateValue("BuildingID", moveInApp.Building);
        studentInfoModify.setUpdateValue("RoomID", moveInApp.Room);

        // Check if the student table is correctly updated.
        // If not, return false & print inconsistency warning to the console
        if(studentInfoModify.ExecuteUpdate() == 0) {
            System.out.println("moveInApp table has been correctly updated but student table has not, thus they're inconsistent now. Please check them.");
            return false;
        } else{// Else, update preloaded studentInfo & roomInfo & building Info, return true
            synchronized (lockStudent){
                studentInfo.BuildingID = moveInApp.Building;
                studentInfo.RoomID = moveInApp.Room;
            }
            synchronized (lockBuilding){
                Query<BuildingInfo> buildingInfoQuery = new Query<>(BuildingInfo.class);
                buildingInfoQuery.setEqualValue("BuildingID", moveInApp.Building);
                buildingInfo = buildingInfoQuery.ExecuteQuery().get(0);
            }
            synchronized (lockRoom){
                Query<RoomInfo> roomInfoQuery = new Query<>(RoomInfo.class);
                roomInfoQuery.setEqualValue("BuildingID", moveInApp.Building);
                roomInfoQuery.setEqualValue("RoomID", moveInApp.Room);
                roomInfo = roomInfoQuery.ExecuteQuery().get(0);
            }
            return true;
        }
    }

    @Override
    public boolean MoveOutAppConfirm() {
        System.out.println("Function Called");
        // Get App record
        Query<MoveOutApp> moveOutAppQuery = new Query<>(MoveOutApp.class);
        synchronized (lockStudent) {moveOutAppQuery.setEqualValue("StudentID", studentInfo.getStudentID());}
        moveOutAppQuery.setIsNull("confirmDate");
        MoveOutApp moveInApp = moveOutAppQuery.ExecuteQuery().get(0);

        // Update moveOutApp table
        Modify<MoveOutApp> moveOutAppModify = new Modify<>(MoveOutApp.class);
        moveOutAppModify.setEqualValue("ApplicationID", moveInApp.ApplicationID);
        moveOutAppModify.setUpdateValue("confirmDate", new Date());

        // Check if moveOutApp table is correctly updated. If not, return false
        if(moveOutAppModify.ExecuteUpdate() == 0)
            return false;

        // Else, update student table
        Modify<StudentInfo> studentInfoModify = new Modify<>(StudentInfo.class);
        studentInfoModify.setEqualValue("StudentID",studentInfo.StudentID);
        studentInfoModify.setUpdateNull("BuildingID");
        studentInfoModify.setUpdateNull("RoomID");

        // Check if the student table is correctly updated.
        // If not, return false & print inconsistency warning to the console
        if(studentInfoModify.ExecuteUpdate() == 0) {
            System.out.println("moveOutApp table has been correctly updated but student table has not, thus they're inconsistent now. Please check them.");
            return false;
        }else{// Else, update preloaded studentInfo & roomInfo & building Info, return true
            synchronized (lockStudent){
                studentInfo.BuildingID = null;
                studentInfo.RoomID = null;
            }
            synchronized (lockBuilding){ buildingInfo = null; }
            synchronized (lockRoom){ roomInfo = null; }
            return true;
        }
    }

    @Override
    public boolean ChangeRoomAppConfirm() {
        // Get App record
        Query<ChangeRoomApp> changeRoomAppQuery = new Query<>(ChangeRoomApp.class);
        synchronized (lockStudent) {changeRoomAppQuery.setEqualValue("StudentID", studentInfo.getStudentID());}
        changeRoomAppQuery.setIsNull("confirmDate");
        ChangeRoomApp changeRoomApp = changeRoomAppQuery.ExecuteQuery().get(0);

        // Update changRoomApp table
        Modify<ChangeRoomApp> changeRoomAppModify = new Modify<>(ChangeRoomApp.class);
        changeRoomAppModify.setEqualValue("ApplicationID", changeRoomApp.ApplicationID);
        changeRoomAppModify.setUpdateValue("confirmDate", new Date());

        // Check if changRoomApp table is correctly updated. If not, return false
        if(changeRoomAppModify.ExecuteUpdate() == 0)
            return false;

        // Else, update student table
        Modify<StudentInfo> studentInfoModify = new Modify<>(StudentInfo.class);
        studentInfoModify.setEqualValue("StudentID",studentInfo.StudentID);
        studentInfoModify.setUpdateValue("BuildingID", changeRoomApp.BuildingTar);
        studentInfoModify.setUpdateValue("RoomID", changeRoomApp.RoomTar);


        // Check if the student table is correctly updated.
        // If not, return false & print inconsistency warning to the console
        if(studentInfoModify.ExecuteUpdate() == 0) {
            System.out.println("moveInApp table has been correctly updated but student table has not, thus they're inconsistent now. Please check them.");
            return false;
        }else{// Else, update preloaded studentInfo & roomInfo & building Info, return true
            synchronized (lockStudent){
                studentInfo.BuildingID = changeRoomApp.BuildingTar;
                studentInfo.RoomID = changeRoomApp.RoomTar;
            }
            synchronized (lockBuilding){
                Query<BuildingInfo> buildingInfoQuery = new Query<>(BuildingInfo.class);
                buildingInfoQuery.setEqualValue("BuildingID", changeRoomApp.BuildingTar);
                buildingInfo = buildingInfoQuery.ExecuteQuery().get(0);
            }
            synchronized (lockRoom){
                Query<RoomInfo> roomInfoQuery = new Query<>(RoomInfo.class);
                roomInfoQuery.setEqualValue("BuildingID", changeRoomApp.BuildingTar);
                roomInfoQuery.setEqualValue("RoomID", changeRoomApp.RoomTar);
                roomInfo = roomInfoQuery.ExecuteQuery().get(0);
            }
            return true;
        }
    }

    @Override
    public boolean PayElec(float payment) {
        // illegal input check
        if(payment < 0)
            return false;

        // Check if he/she has a room
        // if not, return false
        synchronized (lockRoom){
            if(roomInfo == null)
                return false;
        }

        float restBalance = roomInfo.elecBalance + payment;

        // Check if the elecFee is already due
        // if so, then update elecPayment
        if(roomInfo.elecBalance < 0){
            // Check if the payment is enough
            // if not, return false
            if(restBalance < 0)
                return false;

            // Update the elecPayment table
            Query<ElecPaymentInfo> elecPaymentInfoQuery = new Query<>(ElecPaymentInfo.class);
            elecPaymentInfoQuery.setEqualValue("RoomID",roomInfo.RoomID);
            elecPaymentInfoQuery.setEqualValue("BuildingID",roomInfo.BuildingID);
            elecPaymentInfoQuery.setIsNull("StudentID");
            elecPaymentInfoQuery.setIsNull("PayDate");
            Modify<ElecPaymentInfo> elecPaymentInfoModify = new Modify<>(ElecPaymentInfo.class);
            elecPaymentInfoModify.setEqualValue("PaymentID", elecPaymentInfoQuery.ExecuteQuery().get(0).PaymentID);
            elecPaymentInfoModify.setUpdateValue("PayDate",new Date());
            synchronized (lockStudent) {elecPaymentInfoModify.setUpdateValue("StudentID", studentInfo.StudentID);}
            elecPaymentInfoModify.setUpdateValue("Payment", payment);

            // Check if the elecPayment table is correctly updated
            // if not, return false;
            if(elecPaymentInfoModify.ExecuteUpdate() == 0)
                return false;
        }


        // Update the room table
        Modify<RoomInfo> roomInfoModify = new Modify<>(RoomInfo.class);
        roomInfoModify.setEqualValue("BuildingID",studentInfo.BuildingID);
        roomInfoModify.setEqualValue("RoomID",studentInfo.RoomID);
        roomInfoModify.setUpdateValue("elecBalance", restBalance);
        roomInfoModify.setUpdateValue("elecAvailable", true);

        // Check if the room table is correctly updated
        // if not, return false
        if (roomInfoModify.ExecuteUpdate() == 0)
            return false;
        else{// Else, update the preload roomInfo and return true;
            synchronized (lockRoom) {
                roomInfo.elecAvailable = true;
                roomInfo.elecBalance = restBalance;
                return true;
            }
        }
    }

    @Override
    public boolean PayWater(float payment) {
        // illegal input check
        if(payment < 0)
            return false;

        // Check if he/she has a room
        // if not, return false
        synchronized (lockRoom){
            if(roomInfo == null)
                return false;
        }

        float restBalance = roomInfo.waterBalance + payment;

        // Check if the waterFee is already due
        // if so, then update waterPayment
        if(roomInfo.waterBalance < 0){
            // Check if the payment is enough
            // if not, return false
            if(restBalance < 0)
                return false;

            // Update the waterPayment table
            Query<WaterPaymentInfo> waterPaymentInfoQuery = new Query<>(WaterPaymentInfo.class);
            waterPaymentInfoQuery.setEqualValue("RoomID",roomInfo.RoomID);
            waterPaymentInfoQuery.setEqualValue("BuildingID",roomInfo.BuildingID);
            waterPaymentInfoQuery.setIsNull("StudentID");
            waterPaymentInfoQuery.setIsNull("PayDate");
            Modify<WaterPaymentInfo> waterPaymentInfoModify = new Modify<>(WaterPaymentInfo.class);
            waterPaymentInfoModify.setEqualValue("PaymentID", waterPaymentInfoQuery.ExecuteQuery().get(0).PaymentID);
            waterPaymentInfoModify.setUpdateValue("PayDate",new Date());
            synchronized (lockStudent) {waterPaymentInfoModify.setUpdateValue("StudentID", studentInfo.StudentID);}
            waterPaymentInfoModify.setUpdateValue("Payment", payment);

            // Check if the waterPayment table is correctly updated
            // if not, return false;
            if(waterPaymentInfoModify.ExecuteUpdate() == 0)
                return false;
        }


        // Update the room table
        Modify<RoomInfo> roomInfoModify = new Modify<>(RoomInfo.class);
        roomInfoModify.setEqualValue("BuildingID",studentInfo.BuildingID);
        roomInfoModify.setEqualValue("RoomID",studentInfo.RoomID);
        roomInfoModify.setUpdateValue("waterBalance", restBalance);
        roomInfoModify.setUpdateValue("waterAvailable", true);

        // Check if the room table is correctly updated
        // if not, return false
        if (roomInfoModify.ExecuteUpdate() == 0)
            return false;
        else{// Else, update the preload roomInfo and return true;
            synchronized (lockRoom) {
                roomInfo.waterAvailable = true;
                roomInfo.waterBalance = restBalance;
                return true;
            }
        }
    }

    @Override
    public boolean PayRoom() {
        // Check if he/she has a room
        // if not, return false
        synchronized (lockRoom){
            if(roomInfo == null)
                return false;
        }

        Modify<RoomPaymentInfo> roomPaymentInfoModify = new Modify<>(RoomPaymentInfo.class);
        roomPaymentInfoModify.setEqualValue("RoomID",roomInfo.RoomID);
        roomPaymentInfoModify.setEqualValue("BuildingID",roomInfo.BuildingID);
        synchronized (lockStudent) {roomPaymentInfoModify.setEqualValue("StudentID",studentInfo.StudentID);}
        roomPaymentInfoModify.setIsNull("PayDate");
        roomPaymentInfoModify.setUpdateValue("PayDate", new Date());
        return (roomPaymentInfoModify.ExecuteUpdate() > 0);
    }
}