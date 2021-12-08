package src.controller.student;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QueryFee {
    public Text elec,water,accom;
    public void QueryFee(){
        elec.setText(String.valueOf(Main.studentUser.QueryRoomInfo().elecBalance));
        water.setText(String.valueOf(Main.studentUser.QueryRoomInfo().waterBalance));
        accom.setText(String.valueOf(1500));
    }
    //点击"水费缴费"按钮,加载新窗口（WaterPay.fxml）
    public void WaterPay() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/WaterPay.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("水费账单信息");//设置标题
        stage.getIcons().add(new Image("images/student_male.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //点击"电费缴费"按钮,加载新窗口（ElecPay.fxml）
    public void ElecPay() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/ElecPay.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("电费账单信息");//设置标题
        stage.getIcons().add(new Image("images/student_male.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //点击"住宿费缴费"按钮，跳出是否欠费提醒
    public void roomPayTip() throws IOException {
        if(Main.studentUser.QueryRoomPaymentInfo()==null){//无房间
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("您还没有房间，无需缴费！");
            alert.showAndWait();
        }
        else {//有房间
            if (Main.studentUser.QueryRoomPaymentInfo().getAmount() == 0) {//已付房费
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("信息");
                alert.headerTextProperty().set("您这一学年已缴房费！");
                alert.showAndWait();
            } else if (Main.studentUser.QueryRoomPaymentInfo().getAmount() > 0) {//房费欠费
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/RoomPay.fxml"));
                Parent root2 = fxmlLoader.load();
                Scene scene = new Scene(root2);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("房费账单信息");//设置标题
                stage.getIcons().add(new Image("images/student_male.png"));//设置图标
                stage.setScene(scene);//设置场景
                stage.showAndWait();
            }
        }
    }
}
