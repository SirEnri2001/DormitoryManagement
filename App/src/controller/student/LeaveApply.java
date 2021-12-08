package src.controller.student;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import src.Main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaveApply {

    @FXML
    private DatePicker date1,date2;

    @FXML
    private TextArea leaveReason;

    @FXML
    private Button leaveApplyButton;



    @FXML
    private AnchorPane root;
    public List<Integer> getDate() {
        List<Integer> list = new ArrayList<Integer>();
        date1.setEditable(false);
        date2.setEditable(false);
        LocalDate d1=date1.getValue();
        LocalDate d2=date2.getValue();
        list.add(d1.getYear());
        list.add(d1.getMonth().getValue());
        list.add(d1.getDayOfMonth());
        list.add(d2.getYear());
        list.add(d2.getMonth().getValue());
        list.add(d2.getDayOfMonth());
        return list;
    }

    @FXML
    void apply() {
        List<Integer> lists = getDate();
        boolean applyOK=false;
        try {
        applyOK = Main.studentUser.LeaveApp(lists.get(0), lists.get(1), lists.get(2), lists.get(3), lists.get(4), lists.get(5), leaveReason.getText());}
        catch(NumberFormatException e){

        }
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
