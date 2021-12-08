package src.controller.student;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import src.Main;

public class RoomPay {

    @FXML
    private Text amount;

    @FXML
    private AnchorPane root;

    @FXML
    private Button refresh;

    @FXML
    private Button pay;


    //窗口初始化自动加载
    public void initialize() {
        roomRefresh();
    }

    @FXML
    void roomRefresh() {
        amount.setText(String.valueOf(Main.studentUser.QueryRoomPaymentInfo().getAmount())+"元");
    }

    @FXML
    void RoomP() {
        boolean payOK = Main.studentUser.PayRoom();
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
