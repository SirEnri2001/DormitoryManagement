package src;

import javafx.concurrent.Task;
import org.javatuples.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class AutomaticTask extends Task<Void> {

    public static void main(String[] args) {
        AutomaticTask at = new AutomaticTask();
        try {
            at.UpdateBalance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void succeeded(){

    }

    @Override
    protected Void call() throws Exception {
        UpdateBalance();
        //ClearUsage();
        return null;
    }


    @Override
    protected void cancelled() {
        super.cancelled();
    }

    @Override
    protected void failed() {
        super.failed();
    }
    void UpdateBalance() throws SQLException,InterruptedException {
        while (true){
            System.out.println("Auto task started.");
            Connection conn= DatabaseAccess.getConnection();
            //SQL statement generate
            String sql="update Room set waterBalance = waterBalance - waterUsage*(select price from WaterPrice where waterUsage>min and waterUsage<=max);";
            PreparedStatement stmt= conn.prepareStatement(sql);
            System.out.println(stmt);
            stmt.executeUpdate();
            sql="update Room set elecBalance = elecBalance - elecUsage*(select price from ElecPrice where elecUsage>min and elecUsage<=max);";
            stmt= conn.prepareStatement(sql);
            System.out.println(stmt);
            stmt.executeUpdate();
            stmt.close();
            ResultSet payment = null;

            sql = "select " +
                    "Room.bID bID, " +
                    "Room.rID rID, " +
                    "elecUsage, " +
                    "ElecPrice.grade grade, " +
                    "elecUsage*price cost, " +
                    "elecBalance balance," +
                    "elecBalance + elecUsage*ElecPrice.price lastBalance, " +
                    "elecAvailable eAvail " +
                    "from Room " +
                    "join ElecPrice on min<elecUsage and elecUsage<=max " +
                    "where elecBalance < 0;";
            stmt= conn.prepareStatement(sql);
            System.out.println(stmt);
            ResultSet rs = stmt.executeQuery();
            int lines = 0;
            int i;
            while(rs.next()){
                sql = "select * from ElecPayment where bID = ? and rID = ?;";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,rs.getString(1));
                stmt.setString(2,rs.getString(2));
                System.out.println(stmt);
                payment = stmt.executeQuery();
                payment.last();
                if(payment.getRow()!=0){
                    sql = "update ElecPayment set "+
                            "elecUsage = elecUsage + ?," +
                            "grade = ?," +
                            "cost = cost + ?," +
                            "balance = ?," +
                            "setDate = ?" +
                            "where bID = ? and rID = ?;";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1,rs.getString("elecUsage"));
                    stmt.setInt(2, Math.max(payment.getInt("grade"), rs.getInt("grade")));
                    stmt.setDouble(3,rs.getDouble("cost"));
                    stmt.setDouble(4,rs.getDouble("balance"));
                    stmt.setDate(5, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())));
                    stmt.setString(6,rs.getString(1));
                    stmt.setString(7,rs.getString(2));
                    System.out.println(stmt);
                    stmt.executeUpdate();
                    stmt.close();
                    continue;
                }
                stmt.close();

                sql = "insert into ElecPayment set " +
                        "epayID = ?, sID = null, " +
                        "bID = ?," +
                        "rID = ?," +
                        "elecUsage = ?," +
                        "grade = ?," +
                        "cost = ?," +
                        "balance = ?," +
                        "setDate = ?;";
                stmt= conn.prepareStatement(sql);
                stmt.setString(1, UUID.randomUUID().toString().replace("-","").substring(0,16));
                i = 2;
                while(i<8){
                    stmt.setObject(i,rs.getObject(i-1));
                    i++;
                }
                stmt.setDate(8, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())));
                System.out.println(stmt);

                lines+=stmt.executeUpdate();
                stmt.close();
            }
            stmt.close();

            System.out.println(lines+" updated in UpdateBalance for ElecPayment. ");

            sql = "select " +
                    "Room.bID bID, " +
                    "Room.rID rID," +
                    "waterUsage," +
                    "waterBalance balance ," +
                    "WaterPrice.grade grade," +
                    "waterUsage*price cost," +
                    "waterBalance + waterUsage* WaterPrice.price  lastBalance," +
                    "waterAvailable wAvail " +
                    "from Room " +
                    "join WaterPrice on min<waterUsage and waterUsage<=max " +
                    "where waterBalance < 0;";
            stmt= conn.prepareStatement(sql);
            System.out.println(stmt);
            rs = stmt.executeQuery();
            while (rs.next()){
                sql = "select * from WaterPayment where bID = ? and rID = ?;";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,rs.getString(1));
                stmt.setString(2,rs.getString(2));
                System.out.println(stmt);
                payment = stmt.executeQuery();
                payment.last();
                if(payment.getRow()!=0){
                    sql = "update WaterPayment set "+
                            "waterUsage = waterUsage + ?," +
                            "grade = ?," +
                            "cost = cost + ?," +
                            "balance = ?," +
                            "setDate = ?" +
                            "where bID = ? and rID = ?;";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1,rs.getString("waterUsage"));
                    stmt.setInt(2, Math.max(payment.getInt("grade"), rs.getInt("grade")));
                    stmt.setDouble(3,rs.getDouble("cost"));
                    stmt.setDouble(4,rs.getDouble("balance"));
                    stmt.setDate(5, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())));
                    stmt.setString(6,rs.getString(1));
                    stmt.setString(7,rs.getString(2));
                    System.out.println(stmt);
                    stmt.executeUpdate();
                    stmt.close();
                    continue;
                }
                stmt.close();


                sql = "insert into WaterPayment set sID = null,wpID = ?,bID = ?,rID = ?,waterUsage = ?,balance = ?,grade = ?,cost = ?,setDate = ?;";
                stmt= conn.prepareStatement(sql);
                stmt.setString(1, UUID.randomUUID().toString().replace("-","").substring(0,16));
                i = 2;
                while(i<8){
                    stmt.setObject(i,rs.getObject(i-1));
                    i++;
                }
                stmt.setDate(8, java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())));
                System.out.println(stmt);
                lines += stmt.executeUpdate();
                stmt.close();
            }

            System.out.println(lines+" updated in UpdateBalance for WaterPayment. ");

            DatabaseAccess.closeAll(null, stmt, payment);
            DatabaseAccess.closeAll(conn, null, rs);
            System.out.println("Autotask completed");
            Thread.sleep(1000*40);
        }
    }

    void ClearUsage() throws SQLException,InterruptedException {
        Connection conn= DatabaseAccess.getConnection();
        //SQL statement generate
        String sql="update Room set waterUsage = 0,elecUsage = 0;";
        PreparedStatement stmt= conn.prepareStatement(sql);
        System.out.println(stmt);
        int lines = stmt.executeUpdate();
        System.out.println(lines+" line(s) updated in UpdateBalance for WaterPayment. ");
        DatabaseAccess.closeAll(conn, stmt, null);
    }
}
