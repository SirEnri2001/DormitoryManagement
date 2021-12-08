package src.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import src.BuildingInfo;
import src.Main;

import java.util.List;

public class QueryBuilding {
    @FXML
    private TableView<BuildingInfo> sTable;

    @FXML
    private TableColumn<BuildingInfo, Integer> Capacity;

    @FXML
    private TableColumn<BuildingInfo, Integer> Available;

    @FXML
    private TableColumn<BuildingInfo, String> Bid;
    private ObservableList<BuildingInfo> B = FXCollections.observableArrayList();
    //窗口初始化自动加载
    public void initialize() {
        selectBuilding();
    }
    //点击按钮查询函数
    public void selectBuilding(){
        showTable(getData());
    }
    //获取要加载的数据
    public ObservableList<BuildingInfo> getData(){
        List<BuildingInfo> lists = Main.managerUser.QueryBuildingInfo();
        //进行遍历 并加载到B中
        for (BuildingInfo l : lists) {
            System.out.println(l);
            B.add(l);
        }
        return B;
    }
    public void showTable(ObservableList<BuildingInfo> rooms) {
        Bid.setCellValueFactory(new PropertyValueFactory<>("BuildingID"));
        Capacity.setCellValueFactory(new PropertyValueFactory<>("Capacity"));
        Available.setCellValueFactory(new PropertyValueFactory<>("Available"));
        sTable.setItems(B);
    }
}

