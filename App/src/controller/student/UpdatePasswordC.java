package src.controller.student;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import src.Main;
import src.SHAUtil;

//UpdatePassword.fxml的控制器
public class UpdatePasswordC {
    public Button update;
    public PasswordField pre,pro;

    //点击提交按钮 事件
    public void cpw_edit(){
        //通过登录时保存的用户名 查询得到一个Student实例
        if(Main.studentUser.UpdatePassword(pre.getText(), SHAUtil.SHAMD5(pro.getText()))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("修改密码成功");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("修改密码失败");
            alert.showAndWait();
        }
    }
}
