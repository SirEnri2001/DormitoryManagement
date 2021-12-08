package src.controller.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import src.Main;
import src.RoomInfo;

import java.util.List;

public class SelectUpdateFee {
    @FXML
    private TextField Water, Elec, ID;

    @FXML
    private TableView<RoomInfo> sTable;

    @FXML
    private TableColumn<RoomInfo, Float> WaterBalance, ElecBalance, ElecUsage, WaterUsage;
    public void selectFee() {
        sTable.getItems().clear();
        ObservableList<RoomInfo> room = FXCollections.observableArrayList();
        List<RoomInfo> lists = Main.managerUser.QueryFeeInfo(ID.getText());
        if(lists==null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("该学生未入住，没有查询到费用信息！");
            alert.showAndWait();
        }
        else{
            //进行遍历 并加载到room中
            for (RoomInfo l : lists) {
                System.out.println(l);
                room.add(l);
            }
            WaterBalance.setCellValueFactory(new PropertyValueFactory<>("waterBalance"));
            ElecBalance.setCellValueFactory(new PropertyValueFactory<>("elecBalance"));
            ElecUsage.setCellValueFactory(new PropertyValueFactory<>("elecUsage"));
            WaterUsage.setCellValueFactory(new PropertyValueFactory<>("waterUsage"));
            sTable.setItems(room);
        }
    }

    public void PayWater(){
        String SID=ID.getText();
        Float payWater=Float.parseFloat(Water.getText());
        boolean payOK=Main.managerUser.PayWater(SID,payWater);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        if(payOK){
            alert.headerTextProperty().set("缴费成功！");
        }
        else{
            alert.headerTextProperty().set("缴费失败！");
        }
        alert.showAndWait();
        selectFee();
    }
    public void PayElec(){
        String SID=ID.getText();
        Float payElec=Float.parseFloat(Elec.getText());
        boolean payOK=Main.managerUser.PayElec(SID,payElec);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        if(payOK){
            alert.headerTextProperty().set("缴费成功！");
        }
        else{
            alert.headerTextProperty().set("缴费失败！");
        }
        alert.showAndWait();
        selectFee();
    }
}

