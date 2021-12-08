package src.controller;

import com.mysql.cj.log.Log;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import javafx.concurrent.Task;


import src.*;

import java.io.IOException;

//login.fxml文件的控制器
public class Login implements ISetProgress{
    public RadioButton student;//单选按钮 学生
    public RadioButton admin;//单选按钮 管理员
    public Text tip;//提示信息
    public SplitPane root;
    public TextField username;//输入框 用户名
    public TextField password;//输入框 密码
    public ProgressBar progressBar;

    final String[] auth = {"non"};

    final Parent[] root2 = {null};

    public Stage stage = null;

    public void setProgress(double progress){
        progressBar.setProgress(progress);
    }

    //点击登录按钮的事件
    public void login() throws IOException {
        progressBar.setProgress(0.0);
        stage = (Stage) root.getScene().getWindow();//获取当前窗口
        stage.setResizable(false);//窗口不可改变高度、宽度,这样就不用调节自适应了
        LoginTask loginTask = new LoginTask();
        loginTask.controller = this;
        Thread loginThread = new Thread(loginTask);
        loginThread.start();
    }
}

class LoginTask extends Task<Void>{
    public Login controller;
    @Override
    protected void succeeded() {
        super.succeeded();
        controller.setProgress(0.0);
        if (controller.auth[0].equals("student")||controller.auth[0].equals("manager")) {
            controller.stage.setScene(new Scene(controller.root2[0]));//添加场景
            controller.stage.show();
        }else {
            controller.tip.setText("账号或密码错误");
        }

    }

    @Override
    protected void cancelled() {
        super.cancelled();
    }

    @Override
    protected void failed() {
        super.failed();
    }

    @Override
    protected Void call() throws Exception {
        try{
            controller.setProgress(0.1);
            controller.tip.setText("登陆中，请稍后...");
            if (controller.student.isSelected())//判断哪个单选按钮选中
            {
                if (Main.studentUser.StuLoginAuth(controller.username.getText(), controller.password.getText(),controller))//判断输入的密码和查询到的密码是否一致
                {
                    //获取Fxml加载器
                    controller.root2[0] = FXMLLoader.load(getClass().getResource("/scenes/student/student.fxml"));
                    controller.auth[0] = "student";

                }
            } else if(controller.admin.isSelected()) {
                if (Main.managerUser.AdminLoginAuth(controller.username.getText(), controller.password.getText(),controller))//判断输入的密码和查询到的密码是否一致
                {
                    //获取Fxml加载器
                    controller.root2[0] = FXMLLoader.load(getClass().getResource("/scenes/admin/manager.fxml"));
                    controller.auth[0] = "manager";
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
