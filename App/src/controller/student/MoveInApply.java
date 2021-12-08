package src.controller.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.Main;
import src.RoomInfo;

import java.util.List;

public class MoveInApply {

    @FXML
    private TableView<RoomInfo> sTable;
    @FXML
    private TableColumn<RoomInfo, String> buildingColumn, roomColumn;
    @FXML
    private TableColumn<RoomInfo, Integer> availableColumn;

    @FXML
    private TextField newBID,newRID;
    @FXML
    private TextArea reason;

    private ObservableList<RoomInfo> rooms = FXCollections.observableArrayList();

    //窗口初始化自动加载
    public void initialize() {
        showTable(getData());
    }

    //点击按钮查询函数
    public void selectRoom(){
        showTable(getData());
    }
    //获取要加载的数据
    public ObservableList<RoomInfo> getData(){
        List<RoomInfo> lists = Main.studentUser.AvailableRoomQuery();
        //进行遍历 并加载到rooms中
        for (RoomInfo l : lists) {
            System.out.println(l);
            rooms.add(l);
        }
        return rooms;
    }
    public void showTable(ObservableList<RoomInfo> rooms) {
        buildingColumn.setCellValueFactory(new PropertyValueFactory<>("BuildingID"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("Available"));
        sTable.setItems(rooms);
    }

    //获取文本框中的寝室楼id与寝室号
    public void moveInApply(){
        System.out.println(newBID.getText() + newRID.getText() + reason.getText());
        System.out.println(newBID.getText() + newRID.getText() + reason.getText());
        System.out.println(newBID.getText() + newRID.getText() + reason.getText());
        System.out.println(newBID.getText() + newRID.getText() + reason.getText());
        System.out.println(newBID.getText() + newRID.getText() + reason.getText());

        boolean isOK = Main.studentUser.MoveInApp(newBID.getText(), newRID.getText(), reason.getText());
        System.out.println("执行请假申请函数！");//
        if(isOK==true){
            System.out.println("申请成功！");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("申请成功！");
            alert.showAndWait();
            newBID.setText("");
            newRID.setText("");
        }
        else{
            System.out.println("申请失败！");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("申请失败！");
            alert.showAndWait();
        }
    }
}