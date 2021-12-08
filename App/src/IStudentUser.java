package src;

import src.controller.Login;

import java.util.Map;
import java.util.List;
public interface IStudentUser{
//Account
    Boolean StuLoginAuth(String Username, String Password, ISetProgress controller);//判断学生用户名密码是否储存在数据库中，并初始化studentUser对象

    Boolean UpdatePassword(String pre, String Password);//修改密码，之前的密码，验证密码正确则修改

    void LogOut();

//Query
    Map<String, String> QueryStudentInfo();//学生信息查询

    Map<String, String> QueryBuildingInfo();//楼宇信息查询

    RoomInfo QueryRoomInfo();//房间信息查询, 若学生尚未入住则返回null

    RoomPaymentInfo QueryRoomPaymentInfo();//房费查询，若学生未入住则返回null，若未欠费则Amount为0

    boolean HasMoveInApp();//查询现在是否处于一个未结束的搬入申请流程

    int MoveInAppStatus();//搬入申请审批结果查询，0为未审批，1为审批不通过，2为审批通过

    boolean HasMoveOutApp();//查询当前是否处于一个未结束的搬出申请流程

    int MoveOutAppStatus();//搬出申请审批结果查询，0为未审批，1为审批不通过，2为审批通过

    boolean HasLeaveApp();//查询当前是否存在未销假的请假记录  surprise：请假被拒绝再次点击”请假申请“，只能弹出请假被拒绝

    int LeaveAppStatus();//请假申请审批结果查询，0为未审批，1为审批不通过，2为审批通过

    boolean HasChangeRoomApp();//查询现在是否处于一个未结束的换房申请流程

    int ChangRoomAppStatus();//换房申请审批结果查询，0为未审批，1为审批不通过，2为审批通过

    List<RoomInfo> AvailableRoomQuery();//空房查询

    LeaveApp QueryLeaApp();//查询请假信息

//Application
    boolean MoveInApp(String tar_b_id, String tar_r_id, String reason);//搬入申请

    boolean MoveOutApp(String reason);//搬出申请

    boolean LeaveApp(int lea_year, int lea_month, int lea_day, int back_year, int back_month, int back_day, String reason);//请假申请

    boolean ChangeRoomApp(String b_id, String r_id, String reason);//换房申请

//Update
    boolean LeaveCancel();//销假处理

    boolean MoveInAppConfirm();//搬入申请确认

    boolean MoveOutAppConfirm();//搬出申请确认，只负责修改App表和Student表记录，应在费用清算之后再调用

    boolean ChangeRoomAppConfirm();//换房申请确认，只负责修改App表和Student表记录，应在费用清算之后再调用

    boolean PayElec(float payment);//缴纳电费

    boolean PayWater(float payment);//缴纳水费

    boolean PayRoom();//缴纳房费
}
