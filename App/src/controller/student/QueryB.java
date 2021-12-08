package src.controller.student;

import javafx.scene.text.Text;
import src.Main;
import java.util.Map;
import java.util.HashMap;
import src.StudentUser;

public class QueryB {
    public Text building,all,free;

    //窗口初始化自动加载
    public void initialize() {
        QueryB();
    }
    public void QueryB(){
        Map<String,String> buildingQueryResult = new HashMap<>(Main.studentUser.QueryBuildingInfo());
        building.setText(buildingQueryResult.get("BuildingID"));
        all.setText(buildingQueryResult.get("Capacity"));
        free.setText(buildingQueryResult.get("Available"));
    }
}
