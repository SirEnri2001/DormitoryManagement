package src.controller.student;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import src.Main;

import java.util.HashMap;
import java.util.Map;

public class MoveOutApply {

    @FXML
    private TextArea moveOUTreason;

    @FXML
    private AnchorPane root;

    @FXML
    private Button moveINresultButton;

    @FXML
    private Text oldBID,oldRID;

    @FXML
    private Button moveINapplyButton;

    //窗口初始化自动加载
    public void initialize() {
        show();
    }
    //点击按钮
    public void show(){
        Map<String,String> studentQueryResult = new HashMap<>(Main.studentUser.QueryStudentInfo());
        oldBID.setText(studentQueryResult.get("BuildingID"));
        oldRID.setText(studentQueryResult.get("RoomID"));
    }

    public void apply() {
        Boolean applyOK = Main.studentUser.MoveOutApp(moveOUTreason.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        if(applyOK){
            alert.headerTextProperty().set("申请成功！");
        }
        else {
            alert.headerTextProperty().set("申请失败！");
        }
        alert.showAndWait();
    }
}
