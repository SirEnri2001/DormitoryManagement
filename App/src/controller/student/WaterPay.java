package src.controller.student;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import src.Main;

public class WaterPay {

    @FXML
    private TextField money;

    @FXML
    private AnchorPane root;

    @FXML
    private Text waterBal,waterUse;

    @FXML
    private Button refresh;

    @FXML
    private Button pay;


    //窗口初始化自动加载
    public void initialize() {
        waterRefresh();
    }

    @FXML
    public void waterRefresh() {
        waterUse.setText(Main.studentUser.QueryRoomInfo().waterUsage+"立方米");
        waterBal.setText(Main.studentUser.QueryRoomInfo().waterBalance+"元");
    }

    @FXML
    void WaterP() {
        boolean payOK = Main.studentUser.PayWater(Float.parseFloat(money.getText()));

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
