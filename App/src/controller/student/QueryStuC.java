package src.controller.student;

import javafx.scene.text.Text;
import src.Main;
import java.util.Map;
import java.util.HashMap;
import src.StudentUser;

public class QueryStuC {
    public Text name,id,classid,bid,rid;

    //窗口初始化自动加载
    public void initialize() {
        QueryStuC();
    }

    public void QueryStuC(){
        Map<String,String> studentQueryResult = new HashMap<>(Main.studentUser.QueryStudentInfo());
        name.setText(studentQueryResult.get("Name"));
        id.setText(studentQueryResult.get("StudentID"));
        classid.setText(studentQueryResult.get("Class"));
        bid.setText(studentQueryResult.get("BuildingID"));
        rid.setText(studentQueryResult.get("RoomID"));
    }
}
