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
import src.StudentInfo;

import java.util.List;

//SelectUpdateStu.fxml的配置文件
public class SelectUpdateStuC {
    @FXML
    private TextField sbyBuilding,sbyRoom,sbyClass;
    @FXML
    private TableView<StudentInfo> sTable;
    @FXML
    private TableColumn<StudentInfo,String> id,name,sBuilding,sRoom;
    @FXML
    private TableColumn<StudentInfo,Integer>sex,sClass;
    private ObservableList<StudentInfo> students = FXCollections.observableArrayList();
    //根据房间号查询
    public void selectByRoom(){
        sTable.getItems().clear();//清空单元格内容
        List<StudentInfo> lists = Main.managerUser.SelectByRoomID(sbyRoom.getText());
        if(lists.isEmpty()||lists==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("未查询到结果！");
            alert.showAndWait();
        }
        else{
            for(StudentInfo s:lists){
                System.out.println(s);
                students.add(s);
            }
            showTable(students);
        }
    }
    //根据班级查询
    public void selectByClass(){
        sTable.getItems().clear();
        List<StudentInfo> lists=Main.managerUser.SelectByClass(sbyClass.getText());
        if(lists.isEmpty()||lists==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("未查询到结果！");
            alert.showAndWait();
        }
        else {
            for(StudentInfo s:lists){
                System.out.println(s);
                students.add(s);
            }
            showTable(students);
        }
    }
    //根据楼号查询
    public void selectByBuilding(){
        sTable.getItems().clear();
        List<StudentInfo> lists=Main.managerUser.SelectByBuildingID(sbyBuilding.getText());
        if(lists.isEmpty()||lists==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.titleProperty().set("信息");
            alert.headerTextProperty().set("未查询到结果！");
            alert.showAndWait();
        }
        else {
            for(StudentInfo s:lists){
                System.out.println(s);
                students.add(s);
            }
            showTable(students);
        }
    }
    //将数据与视图进行绑定
    public void showTable(ObservableList<StudentInfo> students){
        id.setCellValueFactory(new PropertyValueFactory<>("StudentID"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sex.setCellValueFactory(new PropertyValueFactory<>("SexStr"));
        sClass.setCellValueFactory(new PropertyValueFactory<>("ClassID"));
        sBuilding.setCellValueFactory(new PropertyValueFactory<>("BuildingID"));
        sRoom.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
        //添加按钮进列表
        /*Update.setCellFactory((col)->{
                    TableCell<StudentInfo, String> cell = new TableCell<StudentInfo, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("编辑");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseClicked((col) -> {
                                //获取list列表中的位置，进而获取列表对应的信息数据
                                StudentInfo s1 = students.get(getIndex());
                                //按钮事件自己添加
                                Stage updateWindow = new Stage();//创建新窗口
                                VBox box = new VBox(20);
                                TextArea update_1 = new TextArea();
                                TextArea update_2 =new TextArea();
                                Label updatable_1 = new Label("楼号：" );
                                Label updatable_2 = new Label("房间号：" );
                                Button add = new Button("确认");
                                add.setPrefWidth(70);
                                add.setPrefHeight(50);
                                add.setStyle(" -fx-background-color: #63bbd0;");
                                box.getChildren().addAll(updatable_1,update_1,updatable_2,update_2,add);
                                updateWindow.setScene(new Scene(box,350,250));
                                updateWindow.show();
                                add.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        System.out.println("Button点击一下 打印一下");
                                    }
                                });
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
        );*/
        sTable.setItems(students);
    }
}