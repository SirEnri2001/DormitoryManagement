package src.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.Main;
import src.MoveOutApp;

import java.util.List;

public class MMoveOutApply {
    @FXML
    private TableView<MoveOutApp> sTable;
    @FXML
    private TableColumn<MoveOutApp, String> id,Appid,PreBid,PreRid,Reason,Yes,No;
    private ObservableList<MoveOutApp> MO = FXCollections.observableArrayList();
    //窗口初始化自动加载
    public void initialize() {
        sTable.getItems().clear();
        selectMoveOutApply();
    }
    //点击按钮查询函数
    public void selectMoveOutApply(){
        sTable.getItems().clear();
        showTable(getData());
    }
    //获取要加载的数据
    public ObservableList<MoveOutApp> getData(){
        List<MoveOutApp> lists = Main.managerUser.QueryMoveOutApp();
        //进行遍历 并加载到MO中
        for (MoveOutApp l : lists) {
            System.out.println(l);
            MO.add(l);
        }
        return MO;
    }
    public void showTable(ObservableList<MoveOutApp> Change) {
        id.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
        Appid.setCellValueFactory(new PropertyValueFactory<>("ApplicationID"));
        PreBid.setCellValueFactory(new PropertyValueFactory<>("Building"));
        PreRid.setCellValueFactory(new PropertyValueFactory<>("Room"));
        Reason.setCellValueFactory(new PropertyValueFactory<>("Reason"));
        //添加按钮进列表
        Yes.setCellFactory((col)->{
                    TableCell<MoveOutApp, String> cell = new TableCell<MoveOutApp, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("同意");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseClicked((col) -> {
                                //获取list列表中的位置，进而获取列表对应的信息数据
                                MoveOutApp c1 = MO.get(getIndex());
                                c1.isPass=true;
                                //按钮事件自己添加
                                boolean changeOK=Main.managerUser.MoveOutAppProcess(c1);
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
                    TableCell<MoveOutApp, String> cell = new TableCell<MoveOutApp, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("拒绝");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseClicked((col) -> {
                                //获取list列表中的位置，进而获取列表对应的信息数据
                                MoveOutApp c1 = MO.get(getIndex());
                                c1.isPass = false;
                                //按钮事件自己添加
                                boolean changeOK=Main.managerUser.MoveOutAppProcess(c1);
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
        sTable.setItems(MO);
    }
}

