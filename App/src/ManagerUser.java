package src;
import java.util.Date;
import java.util.List;

public class ManagerUser implements IManagerUser{
    private ManagerInfo managerInfo;

    public ManagerInfo getManagerInfo(){
        return managerInfo;
    }

// Account
    public Boolean AdminLoginAuth(String Username, String Password) {
    // Null input check
    if(Username.isEmpty() || Password.isEmpty())
        return false;

    // Log in Authentication
    LoginAuthentication loginAuthentication = new LoginAuthentication();
    loginAuthentication.StartManagerAuthentication(Username,Password);
    String result = loginAuthentication.GetStatus();
    System.out.println(result);
    int times = 5;
    while(!result.equals("manager")&&times-->0){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = loginAuthentication.GetStatus();
    }
    //Initialise m_studentID
    if(!result.equals("manager")) {
        try {
            ThreadTool.login_mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;

    }

    // Initialise managerUser
    Query<ManagerInfo> query = new Query<>(ManagerInfo.class);
    query.setEqualValue("ManagerID", Username);
    List<ManagerInfo> managerInfoList = query.ExecuteQuery();
    if(managerInfoList.isEmpty())
    {
        query = new Query<>(ManagerInfo.class);
        query.setEqualValue("Name", Username);
        managerInfoList = query.ExecuteQuery();
    }
    if(managerInfoList.isEmpty())
    {
        query = new Query<>(ManagerInfo.class);
        query.setEqualValue("Phone", Username);
        managerInfoList = query.ExecuteQuery();
    }
    managerInfo = managerInfoList.get(0);
    return true;
}

    @Override
    public Boolean AdminLoginAuth(String Username, String Password,ISetProgress controller) {
        // Null input check
        if(Username.isEmpty() || Password.isEmpty())
            return false;

        // Log in Authentication
        LoginAuthentication loginAuthentication = new LoginAuthentication();
        loginAuthentication.StartManagerAuthentication(Username,Password);
        String result = loginAuthentication.GetStatus();
        System.out.println(result);
        int times = 5;
        while(!result.equals("manager")&&times-->0){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = loginAuthentication.GetStatus();
        }
        //Initialise m_studentID
        if(!result.equals("manager"))
            return false;


        // Initialise managerUser
        Query<ManagerInfo> query = new Query<>(ManagerInfo.class);
        query.setEqualValue("ManagerID", Username);
        List<ManagerInfo> managerInfoList = query.ExecuteQuery();
        if(managerInfoList.isEmpty())
        {
            query = new Query<>(ManagerInfo.class);
            query.setEqualValue("Name", Username);
            managerInfoList = query.ExecuteQuery();
        }
        if(managerInfoList.isEmpty())
        {
            query = new Query<>(ManagerInfo.class);
            query.setEqualValue("Phone", Username);
           managerInfoList = query.ExecuteQuery();
        }
        managerInfo = managerInfoList.get(0);
        return true;
    }

    @Override
    public Boolean UpdatePassword(String pre, String Password) {
        // Null input check
        if(pre.isEmpty() || Password.isEmpty())
            return false;

        // Old password check
        if(!managerInfo.password.equals(pre))
            return false;

        // Update database
        Modify<ManagerInfo> managerInfoModify = new Modify<>(ManagerInfo.class);
        managerInfoModify.setEqualValue("ManagerID",managerInfo.ManagerID);
        managerInfoModify.setUpdateValue("password",Password);
        managerInfoModify.ExecuteUpdate();
        Query<ManagerInfo> managerInfoQuery = new Query<>(ManagerInfo.class);
        managerInfoQuery.setEqualValue("ManagerID",managerInfo.ManagerID);
        if(managerInfoQuery.ExecuteQuery().get(0).password.equals(Password))
        {//Check if password has been updated correctly
            managerInfo.password = Password;
            return true;
        }
        else return false;
    }

// Query
    @Override
    public List<StudentInfo> SelectByBuildingID(String ID) {
        if(ID.isEmpty())
            return null;

        Query<StudentInfo> studentInfoQuery = new Query<>(StudentInfo.class);
        studentInfoQuery.setEqualValue("BuildingID", ID);
        return studentInfoQuery.ExecuteQuery();
    }

    @Override
    public List<StudentInfo> SelectByRoomID(String ID) {
        if(ID.isEmpty())
            return null;

        Query<StudentInfo> studentInfoQuery = new Query<>(StudentInfo.class);
        studentInfoQuery.setEqualValue("RoomID", ID);
        return studentInfoQuery.ExecuteQuery();
    }

    @Override
    public List<StudentInfo> SelectByClass(String ID) {
        if(ID.isEmpty())
            return null;

        Query<StudentInfo> studentInfoQuery = new Query<>(StudentInfo.class);
        studentInfoQuery.setEqualValue("classID", ID);
        return studentInfoQuery.ExecuteQuery();
    }

    @Override
    public List<BuildingInfo> QueryBuildingInfo() {
        return (new Query<>(BuildingInfo.class)).ExecuteQuery();
    }

    @Override
    public List<RoomInfo> QueryRoomInfo() {
        return (new Query<>(RoomInfo.class)).ExecuteQuery();
    }
    @Override
    public List<RoomInfo> QueryFeeInfo(String ID) {
        // Null input check
        if(ID.isEmpty())
            return null;

        // Check if this student exists
        Query<StudentInfo> studentInfoQuery = new Query<>(StudentInfo.class);
        studentInfoQuery.setEqualValue("StudentID", ID);
        List<StudentInfo> studentInfoList = studentInfoQuery.ExecuteQuery();
        if(studentInfoList.isEmpty())
            return null;
        StudentInfo studentInfo = studentInfoList.get(0);

        // Check if he/she has a room
        if(studentInfo.RoomID == null)
            return null;

        Query<RoomInfo> roomInfoQuery = new Query<>(RoomInfo.class);
        roomInfoQuery.setEqualValue("BuildingID",studentInfo.BuildingID);
        roomInfoQuery.setEqualValue("RoomID",studentInfo.RoomID);
        return  roomInfoQuery.ExecuteQuery();
    }

    @Override
    public List<ChangeRoomApp> QueryChangeApp() {
        Query<ChangeRoomApp> ChangeRoomAppQuery = new Query<>(src.ChangeRoomApp.class);
        ChangeRoomAppQuery.setIsNull("isPass");
        return  ChangeRoomAppQuery.ExecuteQuery();
    }

    @Override
    public List<LeaveApp> QueryLeaveApp() {
       Query<LeaveApp> LeaveAppQuery = new Query<>(LeaveApp.class);
       LeaveAppQuery.setIsNull("isPass");
       return LeaveAppQuery.ExecuteQuery();
    }

    @Override
    public List<MoveInApp> QueryMoveInApp() {
       Query<MoveInApp> MoveInAppQuery = new Query<>(MoveInApp.class);
       MoveInAppQuery.setIsNull("isPass");
       return  MoveInAppQuery.ExecuteQuery();
    }

    @Override
    public List<MoveOutApp> QueryMoveOutApp() {
        Query<MoveOutApp> MoveOutQuery = new Query<>(MoveOutApp.class);
        MoveOutQuery.setIsNull("isPass");
        return  MoveOutQuery.ExecuteQuery();
    }

    // Update
    @Override
    public boolean LeaveAppProcess(LeaveApp leaveApp) {
        Modify<LeaveApp> leaveAppModify = new Modify<>(LeaveApp.class);
        leaveAppModify.setEqualValue("ApplicationID", leaveApp.ApplicationID);
        leaveAppModify.setUpdateValue("isPass",leaveApp.isPass);
        leaveAppModify.setUpdateValue("passDate",new Date());
        leaveAppModify.setUpdateValue("managerID",managerInfo.ManagerID);
        return (leaveAppModify.ExecuteUpdate() > 0);
    }

    @Override
    public boolean MoveInAppProcess(MoveInApp moveInApp) {
        // Check if this app is passed
        // if so, check target available space
        // and update available for target room
        if(moveInApp.isPass) {
            // Get target room available space
            Query<RoomInfo> tarRoomInfoQuery = new Query<>(RoomInfo.class);
            tarRoomInfoQuery.setEqualValue("RoomID",moveInApp.Room);
            tarRoomInfoQuery.setEqualValue("BuildingID",moveInApp.Building);
            int tarAvailable = tarRoomInfoQuery.ExecuteQuery().get(0).Available;

            // check if target room has enough space
            // if not, return false
            if(tarAvailable==0)
                return false;
            else {
                // else, update available for target room
                // Target room available --
                Modify<RoomInfo> tarRoomInfoModify = new Modify<>(RoomInfo.class);
                tarRoomInfoModify.setEqualValue("RoomID", moveInApp.Room);
                tarRoomInfoModify.setEqualValue("BuildingID", moveInApp.Building);
                tarRoomInfoModify.setUpdateValue("Available", tarAvailable - 1);

                // Check if available space of target room has been updated correctly
                // if not, return false
                if (tarRoomInfoModify.ExecuteUpdate() == 0) {
                    return false;
                }
            }
        }
        // No matter what the judgement is, update it to the app table
        Modify<MoveInApp> moveInAppModify = new Modify<>(MoveInApp.class);
        moveInAppModify.setEqualValue("ApplicationID", moveInApp.ApplicationID);
        moveInAppModify.setUpdateValue("isPass", moveInApp.isPass);
        moveInAppModify.setUpdateValue("passDate", new Date());
        moveInAppModify.setUpdateValue("managerID", managerInfo.ManagerID);

        // Check if moveInApp table is updated correctly
        // if not, return false and print message to console
        if(moveInAppModify.ExecuteUpdate() > 0)
            return true;
        else{
            if(moveInApp.isPass)
                System.out.println("Target room available and Target room available have all been correctly updated but changeRoomApp table has not, thus they are inconsistent now. Please check them");
            return false;
        }
    }

    @Override
    public boolean MoveOutAppProcess(MoveOutApp moveOutApp) {
        // Check if this app is passed
        // if so, update available for his original room
        if(moveOutApp.isPass) {
                // Update available for original room
                // get the available space of his original room
                Query<RoomInfo> oriRoomInfoQuery = new Query<>(RoomInfo.class);
                oriRoomInfoQuery.setEqualValue("BuildingID",moveOutApp.Building);
                oriRoomInfoQuery.setEqualValue("RoomID",moveOutApp.Room);
                int oriAvailable = oriRoomInfoQuery.ExecuteQuery().get(0).Available;

                // Original room available ++
                Modify<RoomInfo> oriRoomInfoModify = new Modify<>(RoomInfo.class);
                oriRoomInfoModify.setEqualValue("BuildingID", moveOutApp.Building);
                oriRoomInfoModify.setEqualValue("RoomID", moveOutApp.Room);
                oriRoomInfoModify.setUpdateValue("Available", oriAvailable + 1);

                // Check if available space of original room has been updated correctly
                // if not, return false
                if (oriRoomInfoModify.ExecuteUpdate() == 0)
                    return false;
        }

        // No matter what the judgement is, update it to the app table
        Modify<MoveOutApp> moveOutAppModify = new Modify<>(MoveOutApp.class);
        moveOutAppModify.setEqualValue("ApplicationID", moveOutApp.ApplicationID);
        moveOutAppModify.setUpdateValue("isPass", moveOutApp.isPass);
        moveOutAppModify.setUpdateValue("passDate", new Date());
        moveOutAppModify.setUpdateValue("managerID", managerInfo.ManagerID);

        // Check if moveOut table is updated correctly
        // if not, return false and print message to console if needed
        if(moveOutAppModify.ExecuteUpdate() > 0)
            return true;
        else{
            if(moveOutApp.isPass)
                System.out.println("Original room available has been correctly updated but moveOutApp table has not, thus they are inconsistent now. Please check them");
            return false;
        }
    }

    @Override
    public boolean ChangeRoomApProcess(ChangeRoomApp changeRoomApp) {
        // Check if this app is passed
        // if so, check target available space
        // and update available for both rooms if needed
        if(changeRoomApp.isPass) {
            // Get target room available space
            Query<RoomInfo> tarRoomInfoQuery = new Query<>(RoomInfo.class);
            tarRoomInfoQuery.setEqualValue("RoomID",changeRoomApp.RoomTar);
            tarRoomInfoQuery.setEqualValue("BuildingID",changeRoomApp.BuildingTar);
            int tarAvailable = tarRoomInfoQuery.ExecuteQuery().get(0).Available;

            // check if target room has enough space
            // if not, return false
            if(tarAvailable==0)
                return false;
            else {
                // else, update available for both rooms
                //get the available space of his original room
                Query<RoomInfo> oriRoomInfoQuery = new Query<>(RoomInfo.class);
                oriRoomInfoQuery.setEqualValue("BuildingID",changeRoomApp.Building);
                oriRoomInfoQuery.setEqualValue("RoomID",changeRoomApp.Room);
                int oriAvailable = oriRoomInfoQuery.ExecuteQuery().get(0).Available;

                // Original room available ++
                Modify<RoomInfo> oriRoomInfoModify = new Modify<>(RoomInfo.class);
                oriRoomInfoModify.setEqualValue("BuildingID", changeRoomApp.Building);
                oriRoomInfoModify.setEqualValue("RoomID", changeRoomApp.Room);
                oriRoomInfoModify.setUpdateValue("Available", oriAvailable + 1);

                // Check if available space of original room has been updated correctly
                // if not, return false
                if (oriRoomInfoModify.ExecuteUpdate() == 0)
                    return false;

                // Target room available --
                Modify<RoomInfo> tarRoomInfoModify = new Modify<>(RoomInfo.class);
                tarRoomInfoModify.setEqualValue("RoomID", changeRoomApp.RoomTar);
                tarRoomInfoModify.setEqualValue("BuildingID", changeRoomApp.BuildingTar);
                tarRoomInfoModify.setUpdateValue("Available", tarAvailable - 1);

                // Check if available space of target room has been updated correctly
                // if not, return false & print message to console
                if (tarRoomInfoModify.ExecuteUpdate() == 0) {
                    System.out.println("Original room available has been updated correctly but target room available has not, thus they are inconsistent now. Please check them");
                    return false;
                }
            }
        }
        // No matter what the judgement is, update it to the app table
        Modify<ChangeRoomApp> changeRoomAppModify = new Modify<>(ChangeRoomApp.class);
        changeRoomAppModify.setEqualValue("ApplicationID", changeRoomApp.ApplicationID);
        changeRoomAppModify.setUpdateValue("isPass", changeRoomApp.isPass);
        changeRoomAppModify.setUpdateValue("passDate", new Date());
        changeRoomAppModify.setUpdateValue("managerID", managerInfo.ManagerID);

        // Check if changeRoomApp table is updated correctly
        // if not, return false and print message to console if needed
        if(changeRoomAppModify.ExecuteUpdate() > 0)
            return true;
        else{
            if(changeRoomApp.isPass)
                System.out.println("Original room available and Target room available have all been correctly updated but changeRoomApp table has not, thus they are inconsistent now. Please check them");
            return false;
        }
    }

    @Override
    public boolean PayWater(String ID, float payment) {
        // null input check
        if(ID.isEmpty())
            return false;

        // illegal input check
        if(payment < 0)
            return false;

        // Check if this student exists
        // if so, get his information
        Query<StudentInfo> studentInfoQuery = new Query<>(StudentInfo.class);
        studentInfoQuery.setEqualValue("StudentID", ID);
        List<StudentInfo> studentInfoList = studentInfoQuery.ExecuteQuery();
        if(studentInfoList.isEmpty())
            return false;
        StudentInfo studentInfo = studentInfoList.get(0);

        // Check if he/she has a room
        // if so, get his roomInfo
        if(studentInfo.RoomID == null)
            return false;
        Query<RoomInfo> roomInfoQuery = new Query<>(RoomInfo.class);
        roomInfoQuery.setEqualValue("RoomID",studentInfo.RoomID);
        roomInfoQuery.setEqualValue("BuildingID",studentInfo.BuildingID);
        List<RoomInfo> roomInfoList = roomInfoQuery.ExecuteQuery();
        if(roomInfoList.isEmpty())
            return false;
        RoomInfo roomInfo = roomInfoList.get(0);

        float restBalance = roomInfo.waterBalance + payment;

        // Check if the elecFee is already due
        // if so, then update elecPayment
        if(roomInfo.waterBalance < 0){
            // Check if the payment is enough
            // if not, return false
            if(restBalance < 0)
                return false;

            // Update the elecPayment table
            Query<WaterPaymentInfo> waterPaymentInfoQuery = new Query<>(WaterPaymentInfo.class);
            waterPaymentInfoQuery.setEqualValue("RoomID",roomInfo.RoomID);
            waterPaymentInfoQuery.setEqualValue("BuildingID",roomInfo.BuildingID);
            waterPaymentInfoQuery.setIsNull("StudentID");
            waterPaymentInfoQuery.setIsNull("PayDate");
            Modify<WaterPaymentInfo> waterPaymentInfoModify = new Modify<>(WaterPaymentInfo.class);
            waterPaymentInfoModify.setEqualValue("PaymentID", waterPaymentInfoQuery.ExecuteQuery().get(0).PaymentID);
            waterPaymentInfoModify.setUpdateValue("PayDate",new Date());
            waterPaymentInfoModify.setUpdateValue("StudentID", studentInfo.StudentID);
            waterPaymentInfoModify.setUpdateValue("Payment", payment);

            // Check if the elecPayment table is correctly updated
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
        else return true;
    }

    @Override
    public boolean PayElec(String ID, float payment) {
        // null input check
        if(ID.isEmpty())
            return false;

        // illegal input check
        if(payment < 0)
            return false;

        // Check if this student exists
        // if so, get his information
        Query<StudentInfo> studentInfoQuery = new Query<>(StudentInfo.class);
        studentInfoQuery.setEqualValue("StudentID", ID);
        List<StudentInfo> studentInfoList = studentInfoQuery.ExecuteQuery();
        if(studentInfoList.isEmpty())
            return false;
        StudentInfo studentInfo = studentInfoList.get(0);

        // Check if he/she has a room
        // if so, get his roomInfo
        if(studentInfo.RoomID == null)
            return false;
        Query<RoomInfo> roomInfoQuery = new Query<>(RoomInfo.class);
        roomInfoQuery.setEqualValue("RoomID",studentInfo.RoomID);
        roomInfoQuery.setEqualValue("BuildingID",studentInfo.BuildingID);
        List<RoomInfo> roomInfoList = roomInfoQuery.ExecuteQuery();
        if(roomInfoList.isEmpty())
            return false;
        RoomInfo roomInfo = roomInfoList.get(0);

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
            elecPaymentInfoModify.setUpdateValue("StudentID", studentInfo.StudentID);
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
        else return true;
        }
}
