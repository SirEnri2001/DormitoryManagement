package src;

import java.util.List;

public interface IManagerUser{
// Account
    Boolean AdminLoginAuth(String Username,String Password,ISetProgress controller);//判断管理员用户名密码是否储存在数据库中
    Boolean UpdatePassword(String pre,String Password);//修改密码，传入之前的密码，验证密码正确则修改

// Query
    List<StudentInfo> SelectByBuildingID(String ID);//通过楼号查询学生信息
    List<StudentInfo> SelectByRoomID(String ID);//通过宿舍号查询学生信息
    List<StudentInfo> SelectByClass(String ID);//通过班级查询学生信息
    List<BuildingInfo> QueryBuildingInfo();//楼宇信息查询
    List<RoomInfo> QueryRoomInfo();//房间信息查询
    List<RoomInfo> QueryFeeInfo(String ID);//通过学号查询电费水费
    List<ChangeRoomApp> QueryChangeApp();//查询换房申请
    List<LeaveApp> QueryLeaveApp();//查询请假申请
    List<MoveInApp> QueryMoveInApp();//查询搬入申请
    List<MoveOutApp> QueryMoveOutApp();//查询搬出申请

// Update
    boolean LeaveAppProcess(LeaveApp leaveApp);  //请假申请处理
    boolean MoveInAppProcess(MoveInApp moveInApp);  //搬入申请处理
    boolean MoveOutAppProcess(MoveOutApp moveOutApp);  //搬出申请处理
    boolean ChangeRoomApProcess(ChangeRoomApp changeRoomApp);  //换房申请处理
    boolean PayWater(String ID, float payment);//根据学号缴纳水费
    boolean PayElec(String ID, float payment);//根据学号缴纳电费
    //boolean PayRoom(String ID);//根据学号缴纳房费
}
