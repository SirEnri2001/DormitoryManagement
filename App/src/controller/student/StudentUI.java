package src.controller.student;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.ChangeRoomApp;
import src.Main;
import src.UI_Task;
import sun.security.timestamp.TSRequest;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;
import netscape.javascript.JSObject;


import java.io.File;
import java.io.IOException;

//student.fxml文件的控制器

public class StudentUI {
    public AnchorPane root;
    public Text building,all,free;
    public Text elec,water,accom;
    public PasswordField pre,pro;
    public Button SIBtn,BuildingBtn,PaymentBtn,MIInfoBtn,MOBtn,CRBtn,LeaveBtn,ShowMapBtn;
    public Button[] buttons = new Button[8];

    public void InitController(){
        buttons[0] = SIBtn;
        buttons[1] = BuildingBtn;
        buttons[2] = PaymentBtn;
        buttons[3] = MIInfoBtn;
        buttons[4] = MOBtn;
        buttons[5] = CRBtn;
        buttons[6] = LeaveBtn;
        buttons[7] = ShowMapBtn;
    }

    public void SetBtnAvailable(boolean val){
        for(Button btn:buttons){
            btn.setDisable(!val);
        }
    }

    //安全退出
    public void exist() throws IOException {
        Main.studentUser.LogOut();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
        Parent root2 = fxmlLoader.load();//加载页面
        Stage stage =(Stage) root.getScene().getWindow();//获取当前窗口
        stage.setScene(new Scene(root2));///添加场景
    }
    //修改密码
    public void UpdatePassword() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/UpdatePassword.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("修改密码");//设置标题
        stage.getIcons().add(new Image("images/Lock_Rotation.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //查询学生信息
    public void QueryStu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/QueryStu.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("学生信息");//设置标题
        stage.getIcons().add(new Image("images/student_male.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //查询楼宇信息
    public void QueryB() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/QueryB.fxml"));
        Parent root2 = fxmlLoader.load();
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("楼宇信息");//设置标题
        stage.getIcons().add(new Image("images/student_male.png"));//设置图标
        stage.setScene(scene);//设置场景
        stage.showAndWait();
    }
    //查询账单信息
    public void QueryFee() throws IOException {
        if(Main.studentUser.QueryRoomInfo()!=null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/QueryFee.fxml"));
            Parent root2 = fxmlLoader.load();
            Scene scene = new Scene(root2);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("账单信息");//设置标题
            stage.getIcons().add(new Image("images/student_male.png"));//设置图标
            stage.setScene(scene);//设置场景
            stage.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("您没有寝室，不可缴费！\n若想入住寝室，请移步”入住申请“！");
            alert.showAndWait();
        }
    }

    public void ShowMapCtrl() throws IOException {

        final WebView browser = new WebView();
        WebEngine webEngine;
        webEngine = browser.getEngine();
        //String url = new File("scenes/student/BuildingMap.html").toURI().toURL().toString();
        webEngine.loadContent("<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no, width=device-width\">\n" +
                "    <title>标准图层</title>\n" +
                "    <style>\n" +
                "        html,\n" +
                "        body,\n" +
                "        #container {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"container\"></div>\n" +
                "<script src=\"https://webapi.amap.com/maps?v=1.4.15&key=33f9fc837551c67bc51643e2cae143b3\"></script>\n" +
                "<script>\n" +
                "    var map = new AMap.Map('container', {\n" +
                "        center: [121.821608, 39.086506],\n" +
                "        layers: [//只显示默认图层的时候，layers可以缺省\n" +
                "            new AMap.TileLayer()//高德默认标准图层\n" +
                "        ],\n" +
                "        zoom: 25\n" +
                "    });\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>");
        /*webEngine.getLoadWorker().stateProperty().addListener(
                (ObservableValue<? extends Worker.State> ov, Worker.State oldState,
                 Worker.State newState) -> {

                    if (newState == Worker.State.SUCCEEDED) {
                        JSObject win = (JSObject) webEngine.executeScript("window");
                        //win.setMember("apps",apps);//设置变量
                    }
                });

        Button button1 = new Button("java调JS方法");
        button1.setOnAction(event -> {
            try {

                JSObject win = (JSObject) webEngine.executeScript("window");
                //webEngine.executeScript("show()");//执行js函数
                //win.call("show","a","b");
                win.eval("show('a','b')");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
        VBox stackPane = new VBox();
        stackPane.setSpacing(20);
        stackPane.getChildren().addAll(browser);
        Scene scene = new Scene(stackPane,600,400);

        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/QueryB.fxml"));
        Stage stage = new Stage();


        scene.setRoot(stackPane);
        stage.setScene(scene);
        stage.show();

    }

    //入住申请
    public void MoveInApp() throws IOException {
        System.out.println("Button clicked");
        if(Main.studentUser.QueryRoomInfo()==null) {//没有房间
            MoveInIOTask task = new MoveInIOTask();
            task.uiContainer = this;
            Thread t = new Thread(task);
            t.start();
        }
        else{//已有房间
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("您已入住寝室，无需再次申请入住！\n若想更换寝室，请移步更换房间申请");
            alert.showAndWait();
        }
    }
    //退宿申请
    public void MoveOutApp() throws IOException{
        if(Main.studentUser.QueryRoomInfo()!=null) {//有房可退
            MoveOutIOTask task = new MoveOutIOTask();
            task.uiContainer = this;
            Thread t = new Thread(task);
            t.start();
        }
        else{//无房可退
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("您没有房间，无需退宿！");
            alert.showAndWait();
        }
    }
    //换寝申请
    public void ChangeApply() throws IOException{
        if(Main.studentUser.QueryRoomInfo()!=null) {//有房可换
            if(Main.studentUser.QueryRoomInfo().elecBalance<0 || Main.studentUser.QueryRoomInfo().waterBalance<0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("信息");
                alert.headerTextProperty().set("您寝室的水电费已欠费\n如需换寝请移步”账单信息“进行缴费");
                alert.showAndWait();
            }
            else{//水电房费不欠费
                ChangeRoomIOTask task = new ChangeRoomIOTask();
                task.uiContainer = this;
                Thread t = new Thread(task);
                t.start();
            }
        }
        else{//无房可换
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("您没有房间，不能换房操作！");
            alert.showAndWait();
        }
    }
//    请假申请
    public void leaveApply() throws IOException{
        if(Main.studentUser.QueryRoomInfo()!=null) {//有房才能请假
            if(Main.studentUser.HasLeaveApp()==false) {//没有未完成的申请（已经销假或之前没请过假）
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/LeaveApply.fxml"));
                Parent root2 = fxmlLoader.load();
                Scene scene = new Scene(root2);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("请假申请");//设置标题
                stage.getIcons().add(new Image("images/student_male.png"));//设置图标
                stage.setScene(scene);//设置场景
                stage.showAndWait();
            }
            else{//有未结束的申请，分为三种情况：0为未审批，1为审批不通过，2为审批通过
                int status = Main.studentUser.LeaveAppStatus();
                if(status==0){//没审批
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("信息");
                    alert.headerTextProperty().set("申请未审批，请耐心等待！");
                    alert.showAndWait();
                }
                else if(status==1){//审批拒绝
                    Main.studentUser.LeaveCancel();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("信息");
                    alert.headerTextProperty().set("申请被拒绝，请重新申请！");
                    alert.showAndWait();
                }
                else if(status==2){//审批通过
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.titleProperty().set("信息");
                    alert.headerTextProperty().set("请假申请通过！");
                    alert.showAndWait();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/ReportBack.fxml"));
                    Parent root2 = fxmlLoader.load();
                    Scene scene = new Scene(root2);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("销假申请");//设置标题
                    stage.getIcons().add(new Image("images/student_male.png"));//设置图标
                    stage.setScene(scene);//设置场景
                    stage.showAndWait();
                }
            }

        }
        else{//没有寝室不行请假
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("您没有房间，不能申请请假！");
            alert.showAndWait();
        }
    }

}



class MoveInIOTask extends UI_Task {
    String info = "";
    boolean hasApp;

    @Override
    protected void succeeded(){
        super.succeeded();
        System.out.println("succeed");
        if(!hasApp) {//没有未完成的申请（或初次申请）
            uiContainer.SetBtnAvailable(true);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/MoveInApp.fxml"));
            Parent root2 = null;
            try {
                root2 = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root2);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("入住申请");//设置标题
            stage.getIcons().add(new Image("images/student_male.png"));//设置图标
            stage.setScene(scene);//设置场景
            stage.showAndWait();
        }else{
            uiContainer.SetBtnAvailable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set(info);
            alert.showAndWait();
        }
    }

    @Override
    protected Void call(){
        try{
            super.call();
            System.out.println("Thread call");
            uiContainer.SetBtnAvailable(false);
            hasApp = Main.studentUser.HasMoveInApp();
            if(hasApp){
                int status = Main.studentUser.MoveInAppStatus();
                if(status==0){//没审批

                   info = "申请未审批，请耐心等待！";
                }
                else if(status==1){//审批拒绝
                    Main.studentUser.MoveInAppConfirm();
                    info = "申请被拒绝，请重新申请！";
                }
                else if(status==2){//审批通过
                    if(Main.studentUser.MoveInAppConfirm()) {//判断学生确认成功，此时已入住寝室
                        info = "申请通过，您已入住！";
                    }
                    else{//判断学生确认失败，此时没有入住寝室
                        info = "申请通过，但但您确认失败！";
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

class MoveOutIOTask extends UI_Task{
    boolean hasMoveOut;
    String info = "";
    @Override
    public void succeeded(){
        if(!hasMoveOut){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/MoveOutApp.fxml"));
            Parent root2 = null;
            try {
                root2 = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root2);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("退宿申请");//设置标题
            stage.getIcons().add(new Image("images/student_male.png"));//设置图标
            stage.setScene(scene);//设置场景
            uiContainer.SetBtnAvailable(true);
            stage.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set(info);
            uiContainer.SetBtnAvailable(true);
            alert.showAndWait();
        }
    }
    @Override
    public Void call(){
        try{
            uiContainer.InitController();
            uiContainer.SetBtnAvailable(false);
            hasMoveOut = Main.studentUser.HasMoveOutApp();
            if(Main.studentUser.QueryRoomInfo().elecBalance<0 || Main.studentUser.QueryRoomInfo().waterBalance<0 || Main.studentUser.QueryRoomPaymentInfo().Amount>0) {
                info = "您寝室的水电住宿费已欠费\n如需退宿请移步”账单信息“进行缴费";
            }
            else{
                //有未结束的申请，分为三种情况：0为未审批，1为审批不通过，2为审批通过
                int status = Main.studentUser.MoveOutAppStatus();
                if (status == 0) {//没审批
                    info = "申请未审批，请耐心等待！";
                } else if (status == 1) {//审批拒绝
                    info = "申请被拒绝，请重新申请！";
                } else if (status == 2) {//审批通过
                    boolean confirmOK = Main.studentUser.MoveOutAppConfirm();
                    if(confirmOK) {//判断学生确认成功，此时已退宿
                        info = "申请通过，您已退宿！";
                    }
                    else{//判断学生确认失败，但没有退宿
                        info = "申请通过，但您确认失败！";
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}

class ChangeRoomIOTask extends UI_Task{
    String info = "";
    boolean hasChangeRoomApp;
    @Override
    public void succeeded(){
        uiContainer.SetBtnAvailable(true);
        if (!hasChangeRoomApp) {//没有未完成的申请，进入换房申请界面
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scenes/student/ChangeApply.fxml"));
            Parent root2 = null;
            try {
                root2 = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root2);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("更换房间申请");//设置标题
            stage.getIcons().add(new Image("images/student_male.png"));//设置图标
            stage.setScene(scene);//设置场景
            stage.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set(info);
            alert.showAndWait();
        }
    }

    @Override
    public Void call(){
        uiContainer.InitController();
        uiContainer.SetBtnAvailable(false);
        hasChangeRoomApp = Main.studentUser.HasChangeRoomApp();
        if(hasChangeRoomApp) {//有未结束的申请，分为三种情况：0为未审批，1为审批不通过，2为审批通过
            int status = Main.studentUser.ChangRoomAppStatus();
            if (status == 0) {//没审批
                info = "申请未审批，请耐心等待！";
            } else if (status == 1) {//审批拒绝
                Main.studentUser.ChangeRoomAppConfirm();
                info ="申请被拒绝，请重新申请！";
            } else if (status == 2) {//审批通过
                if(Main.studentUser.ChangeRoomAppConfirm()) {//判断学生确认成功，此时寝室已更换
                    info ="申请通过，您已换寝室！";
                }
                else{//判断学生确认失败，寝室没有更换
                    info ="申请通过，但您确认失败！";
                }
            }
        }
        return null;
    }
}