package src.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.Main;
import src.MoveInApp;

import java.util.List;

public class MMoveInApply {
    @FXML
    private TableView<MoveInApp> sTable;
    @FXML
    private TableColumn<MoveInApp, String> id,Appid,ProBid,ProRid,Reason,Yes,No;
    private ObservableList<MoveInApp> MI = FXCollections.observableArrayList();
    //窗口初始化自动加载
    public void initialize() {
        sTable.getItems().clear();
        selectMoveInApply();
    }
    //点击按钮查询函数
    public void selectMoveInApply(){
        sTable.getItems().clear();
        showTable(getData());
    }
    //获取要加载的数据
    public ObservableList<MoveInApp> getData(){
        List<MoveInApp> lists = Main.managerUser.QueryMoveInApp();
        //进行遍历 并加载到Leave中
        for (MoveInApp l : lists) {
            System.out.println(l);
            MI.add(l);
        }
        return MI;
    }
    public void showTable(ObservableList<MoveInApp> MI) {
        id.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
        Appid.setCellValueFactory(new PropertyValueFactory<>("ApplicationID"));
        ProBid.setCellValueFactory(new PropertyValueFactory<>("Building"));
        ProRid.setCellValueFactory(new PropertyValueFactory<>("Room"));
        Reason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
        //添加按钮进列表
        Yes.setCellFactory((col)->{
                    TableCell<MoveInApp, String> cell = new TableCell<MoveInApp, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("同意");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseClicked((col) -> {
                                //获取list列表中的位置，进而获取列表对应的信息数据
                                MoveInApp c1 = MI.get(getIndex());
                                c1.isPass=true;
                                //按钮事件自己添加
                                boolean changeOK=Main.managerUser.MoveInAppProcess(c1);
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
                    TableCell<MoveInApp, String> cell = new TableCell<MoveInApp, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("拒绝");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseClicked((col) -> {
                                //获取list列表中的位置，进而获取列表对应的信息数据
                                MoveInApp c1 = MI.get(getIndex());
                                c1.isPass = false;
                                //按钮事件自己添加
                                boolean changeOK=Main.managerUser.MoveInAppProcess(c1);
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
        sTable.setItems(MI);
    }
}

