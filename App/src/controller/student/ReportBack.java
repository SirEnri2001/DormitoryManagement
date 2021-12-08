package src.controller.student;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import src.Main;

import java.text.SimpleDateFormat;

public class ReportBack {

    @FXML
    private Text date1,date2,reason;

    @FXML
    private Button BBB;

    @FXML
    private AnchorPane root;

    @FXML
    public void initialize(){
        //显示请假时间，返校时间，请假理由
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        date1.setText(sdf.format(Main.studentUser.QueryLeaApp().leaDate));
        date2.setText(sdf.format(Main.studentUser.QueryLeaApp().expRetDate));
        reason.setText(Main.studentUser.QueryLeaApp().Reason);
    }

    @FXML
    void apply() {
        boolean applyOK = Main.studentUser.LeaveCancel();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        if(applyOK){
            alert.headerTextProperty().set("销假成功！");
        }
        else{
            alert.headerTextProperty().set("销假失败！");
        }
        alert.showAndWait();
    }

}
