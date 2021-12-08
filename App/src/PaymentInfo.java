package src;

import java.util.Date;

public class PaymentInfo extends InfoClass {
    public Date PayDate;        //交付日期
    public Date SetDate;        //欠费记录的日期设置
    public String RoomID;       //宿舍房间号
    public String BuildingID;   //宿舍所在楼号
    public String StudentID;    //学生ID
    // String PaymentType;  //费用类型，包含水费，住宿费，电费
    public String PaymentID;
}