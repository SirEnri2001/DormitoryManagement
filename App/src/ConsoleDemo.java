package src;

import java.io.Console;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleDemo {
    private static void StudentConsole(StudentUser user){
        System.out.println("---------Dormitory Management System---------");
        StudentInfo info = user.getStudentInfo();
        System.out.println("Welcome "+info.Name);
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("1. Show Student info\n " +
                    "2. Show Building info\n" +
                    "3. Show Billing info\n" +
                    "4. Move in Application\n" +
                    "5. Move out Application\n" +
                    "6. Change room Application\n" +
                    "7. Leave Application");
            int select = scanner.nextInt();
            scanner.nextLine();
            switch (select){
                case 1:
                    System.out.println(user.getStudentInfo().toString());
                    break;
                case 2:
                    System.out.println(user.getBuildingInfo().toString());
                    break;
                case 3:
                    break;
                case 4:
                case 6:
                    System.out.println("Current Available room:");
                    List<RoomInfo> roomInfos = user.AvailableRoomQuery();
                    for(RoomInfo roomInfo:roomInfos){
                        System.out.println(roomInfo.toString());
                    }
                    System.out.println("Select building");
                    String building = scanner.nextLine();
                    System.out.println("Select room");
                    String room = scanner.nextLine();
                    System.out.println("Reason?");
                    String reason = scanner.nextLine();
                    if(select==4){
                        if(user.MoveInApp(building,room,reason)){
                            System.out.println("Send Application Success");
                        }else{
                            System.out.println("Send Application Failed");
                        }
                    }else {
                        if (user.ChangeRoomApp(building, room, reason)) {
                            System.out.println("Send Application Success");
                        } else {
                            System.out.println("Send Application Failed");
                        }
                    }
                    break;
                case 5:
                    System.out.println("Reason?");
                    if(user.MoveOutApp(scanner.nextLine())){
                        System.out.println("Send Application Success");
                    }else{
                        System.out.println("Send Application Failed");
                    }
                    break;
                default:
                    break;

            }
        }

    }

    private static void ManagerConsole(ManagerUser user){
        System.out.println("---------Dormitory Management System---------");
        ManagerInfo info = user.getManagerInfo();
        System.out.println("Welcome "+info.Name);
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("1. Show Student info\n " +
                    "2. Show Room info\n" +
                    "3. Show Building info\n" +
                    "4. Show Billing info\n" +
                    "5. Move out Application\n" +
                    "6. Move in Application\n" +
                    "6. Change room Application\n" +
                    "7. Leave Application");
            int select = scanner.nextInt();
            int secondeSelect;
            scanner.nextLine();
            switch (select){
                case 1:
                    System.out.println("---Select by---\n" +
                            "1. Building\n" +
                            "2. Room\n" +
                            "3. Class");
                    secondeSelect = scanner.nextInt();
                    scanner.nextLine();
                    List<StudentInfo> studentInfos;
                    switch (secondeSelect){
                        case 1:
                            System.out.println("Input BuildingID:");
                            studentInfos = user.SelectByBuildingID(scanner.nextLine());
                            for(StudentInfo studentInfo:studentInfos){
                                System.out.println(studentInfo.toString());
                            }
                            break;
                        case 2:
                            System.out.println("Input RoomID:");
                            studentInfos = user.SelectByRoomID(scanner.nextLine());
                            for(StudentInfo studentInfo:studentInfos){
                                System.out.println(studentInfo.toString());
                            }
                            break;
                        case 3:
                            System.out.println("Input Class:");
                            studentInfos = user.SelectByClass(scanner.nextLine());
                            for(StudentInfo studentInfo:studentInfos){
                                System.out.println(studentInfo.toString());
                            }
                            break;
                        default:
                            System.out.println("Invalid select");
                    }
                    break;
                case 2:
                    List<RoomInfo> roomInfos = user.QueryRoomInfo();
                    for(RoomInfo roomInfo:roomInfos){
                        System.out.println(roomInfo.toString());
                    }
                    break;
                case 3:
                    List<BuildingInfo> buildingInfos = user.QueryBuildingInfo();
                    for(BuildingInfo buildingInfo:buildingInfos){
                        System.out.println(buildingInfo.toString());
                    }
                    break;
                default:
                    break;

            }
        }
    }

    public static void main(String[] args) {
        StudentUser studentUser;
        ManagerUser managerUser;
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------Dormitory Management System---------");
        System.out.println("Input username(ID/Name/Mobile):");
        String username = scanner.nextLine();
        System.out.println("Input password:");
        String password = scanner.nextLine();
        System.out.println("Select login role: 1.(default)Student 2.Manager");
        String role = scanner.nextLine();
        if(role.contains("2")){
            managerUser = new ManagerUser();

            if(managerUser.AdminLoginAuth(username,password)){
                System.out.println("Login success, role: manager");
                ManagerConsole(managerUser);
            }else{
                System.out.println("Authentication refused");
            }
        }else{
            studentUser = new StudentUser();
            if(studentUser.StuLoginAuth(username,password)){
                System.out.println("Login success, role: student");
                StudentConsole(studentUser);
            }
            else{
                System.out.println("Authentication refused");
            }
        }
    }
}
