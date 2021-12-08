package src.controller.admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

//manager.fxml文件的控制器
public class ManagerUI {
    public AnchorPane root;
    //安全退出
    public void exist() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
        Parent root2 = fxmlLoader.load();//加载页面
        Stage stage =(Stage) root.getScene().getWindow();//获取当前窗口
        stage.setScene(new Scene(root2));///添加场景
    }
    //修改密码
    public void UpdatePassword() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/admin/UpdatePassword.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("修改密码");//设置标题
        stage.getIcons().add(new Image("images/Lock_Rotation.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //查找学生信息
    public void SelectUpdateStu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/admin/SelectUpdateStu.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("学生信息查改");//设置标题
        stage.getIcons().add(new Image("images/admin.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //查找楼宇信息
    public void QueryBuilding() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/admin/QueryBuilding.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("楼宇信息查询");//设置标题
        stage.getIcons().add(new Image("images/admin.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //查找房间信息
    public void QueryRoom() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/admin/QueryRoom.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("房间信息查询");//设置标题
        stage.getIcons().add(new Image("images/admin.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //账单信息查改
    public void SelectUpdateFee() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/admin/SelectUpdateFee.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("账单信息查改");//设置标题
        stage.getIcons().add(new Image("images/admin.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //更换房间
    public void MchangeApply() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/admin/MchangeApply.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("更换房间申请");//设置标题
        stage.getIcons().add(new Image("images/admin.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //请假
    public void MLeaveApply() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/admin/MLeaveApply.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("请假申请");//设置标题
        stage.getIcons().add(new Image("images/admin.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //搬入申请
    public void MMoveInApply() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/admin/MMoveInApply.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("搬入申请");//设置标题
        stage.getIcons().add(new Image("images/admin.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //搬出申请
    public void MMoveOutApply() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/admin/MMoveOutApply.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("搬出申请");//设置标题
        stage.getIcons().add(new Image("images/admin.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
}
