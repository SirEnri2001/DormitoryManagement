package src.controller.student;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import src.Main;

public class ElecPay {

    @FXML
    private Text remainFee,monthFee;

    @FXML
    private Button elecRefresh;

    @FXML
    private TextField money;

    @FXML
    private AnchorPane root;

    //窗口初始化自动加载
    public void initialize() {
        elecRefresh();
    }
    @FXML
    void elecRefresh() {
        monthFee.setText(Main.studentUser.QueryRoomInfo().elecUsage+"度");
        remainFee.setText(Main.studentUser.QueryRoomInfo().elecBalance+"元");
    }

    @FXML
    void elecP() {
        boolean payOK = Main.studentUser.PayElec(Float.parseFloat(money.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        if(payOK){
            alert.headerTextProperty().set("缴费成功！");
        }
        else{
            alert.headerTextProperty().set("缴费失败！");
        }
        alert.showAndWait();
    }
}
