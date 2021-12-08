package src.controller.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import src.Main;
import src.RoomInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeApply {
    @FXML
    private TableView<RoomInfo> sTable;
    @FXML
    private TableColumn<RoomInfo, String> buildingColumn, roomColumn;
    @FXML
    private TableColumn<RoomInfo, Integer> availableColumn;
    @FXML
    private Text oldBID,oldRID;

    @FXML
    private TextField reason;

    @FXML
    private Button applyButton;

    @FXML
    private TextField newBID;

    @FXML
    private TextField newRID;

    @FXML
    private AnchorPane root;

    @FXML
    private Button resultButton;

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

        Map<String,String> studentQueryResult = new HashMap<>(Main.studentUser.QueryStudentInfo());
        oldBID.setText(studentQueryResult.get("BuildingID"));
        oldRID.setText(studentQueryResult.get("RoomID"));
    }
    public void apply(){
        boolean applyOK = Main.studentUser.ChangeRoomApp(newBID.getText(),newRID.getText(),reason.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        if(applyOK){
            alert.headerTextProperty().set("申请成功！");
        }
        else{
            alert.headerTextProperty().set("申请失败！");
        }
        alert.showAndWait();
    }
}
