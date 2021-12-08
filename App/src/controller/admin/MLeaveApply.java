package src.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.LeaveApp;
import src.Main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MLeaveApply {

    @FXML
    private TableView<LeaveApp> sTable;

    @FXML
    private TableColumn<LeaveApp, String > id,Appid,PreDate,ProDate,Reason,Yes,No;
    private ObservableList<LeaveApp> Leave = FXCollections.observableArrayList();
    //窗口初始化自动加载
    public void initialize() {
        sTable.getItems().clear();
        selectLeaveApply();
    }
    //点击按钮查询函数
    public void selectLeaveApply(){
        sTable.getItems().clear();
        showTable(getData());
    }
    //获取要加载的数据
    public ObservableList<LeaveApp> getData(){
        List<LeaveApp> lists = Main.managerUser.QueryLeaveApp();
        //进行遍历 并加载到Leave中
        for (LeaveApp l : lists) {
            System.out.println(l);
            Leave.add(l);
        }
        return Leave;
    }
    public void showTable(ObservableList<LeaveApp> Change) {
        id.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
        Appid.setCellValueFactory(new PropertyValueFactory<>("ApplicationID"));
        PreDate.setCellValueFactory(new PropertyValueFactory<>("leaDateStr"));
        ProDate.setCellValueFactory(new PropertyValueFactory<>("expDateStr"));
        Reason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
        //添加按钮进列表
        Yes.setCellFactory((col)->{
                    TableCell<LeaveApp, String> cell = new TableCell<LeaveApp, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("同意");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseClicked((col) -> {
                                //获取list列表中的位置，进而获取列表对应的信息数据
                                LeaveApp c1 = Leave.get(getIndex());
                                c1.isPass=true;
                                //按钮事件自己添加
                                boolean changeOK=Main.managerUser.LeaveAppProcess(c1);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.titleProperty().set("信息");
                                if(changeOK) {
                                    alert.headerTextProperty().set("已同意！");
                                }
                                else{
                                    alert.headerTextProperty().set("操作失败！");
                                }
                                alert.showAndWait();
                            });
                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button1);
                            }
                        }
                    };
                    return cell;
                }
        );
        No.setCellFactory((col)->{
                    TableCell<LeaveApp, String> cell = new TableCell<LeaveApp, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("拒绝");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseClicked((col) -> {
                                //获取list列表中的位置，进而获取列表对应的信息数据
                                LeaveApp c1 = Leave.get(getIndex());
                                c1.isPass = false;
                                //按钮事件自己添加
                                boolean changeOK=Main.managerUser.LeaveAppProcess(c1);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.titleProperty().set("信息");
                                if(changeOK) {
                                    alert.headerTextProperty().set("已拒绝！");
                                }
                                else{
                                    alert.headerTextProperty().set("操作失败！");
                                }
                                alert.showAndWait();
                            });
                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button1);
                            }
                        }
                    };
                    return cell;
                }
        );
        sTable.setItems(Leave);
    }
}

