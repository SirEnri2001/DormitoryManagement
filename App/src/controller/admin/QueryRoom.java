package src.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import src.Main;
import src.RoomInfo;

import java.util.List;

public class QueryRoom {
    @FXML
    private TableView<RoomInfo> sTable;
    @FXML
    private TableColumn<RoomInfo, Integer> Capacity,Available;
    @FXML
    private TableColumn<RoomInfo, Float> elecbalance,elecusage,waterbalance,waterusage;
    @FXML
    private TableColumn<RoomInfo, String> Bid,Rid;
    private ObservableList<RoomInfo> R = FXCollections.observableArrayList();
    //窗口初始化自动加载
    public void initialize() {
        selectRoom();
    }
    //点击按钮查询函数
    public void selectRoom(){
        showTable(getData());
    }
    //获取要加载的数据
    public ObservableList<RoomInfo> getData(){
        List<RoomInfo> lists = Main.managerUser.QueryRoomInfo();
        //进行遍历 并加载到B中
        for (RoomInfo l : lists) {
            System.out.println(l);
            R.add(l);
        }
        return R;
    }
    public void showTable(ObservableList<RoomInfo> rooms) {
        Bid.setCellValueFactory(new PropertyValueFactory<>("BuildingID"));
        Rid.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
        Capacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
        Available.setCellValueFactory(new PropertyValueFactory<>("Available"));
        elecusage.setCellValueFactory(new PropertyValueFactory<>("elecUsage"));
        elecbalance.setCellValueFactory(new PropertyValueFactory<>("elecBalance"));
        waterbalance.setCellValueFactory(new PropertyValueFactory<>("waterBalance"));
        waterusage.setCellValueFactory(new PropertyValueFactory<>("waterUsage"));
        sTable.setItems(R);
    }
}

