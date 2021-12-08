package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static StudentUser studentUser=new StudentUser();
    public static ManagerUser managerUser=new ManagerUser();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/scenes/login.fxml"));
        primaryStage.setTitle("学生管理系统");
        primaryStage.setScene(new Scene(root));//添加场景
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResource("images/start.png").toString()));//设置图标
        primaryStage.setResizable(false);//窗口不可改变高度、宽度,这样就不用调节自适应了
        primaryStage.show();//显示窗口
    }


    public static void main(String[] args) {
        launch(args);
    }
}