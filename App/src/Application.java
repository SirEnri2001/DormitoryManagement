package src;

import java.util.Date;


public class Application extends InfoClass{
    String ApplicationType;
    String StudentID;
    String ApplicationID;
    Date AppDate;//在DatabaseAccess里面的返回数据表属性中未返回
    Date leaDate;//在DatabaseAccess里面的返回数据表属性中未返回
    Date ExpRetDate;//在DatabaseAccess里面的返回数据表属性中未返回
    String Reason;
    String ManagerID;
    Boolean isPass;//在DatabaseAccess里面的返回数据表属性中未返回
    Date PassDate;//在DatabaseAccess里面的返回数据表属性中未返回
    Date ActRetDate;//在DatabaseAccess里面的返回数据表属性中未返回
    String Building;
    String BuildingOut;
    String Room;
    String RoomOut;
    public static String toJSON(Application application){
        return "";
    }
}