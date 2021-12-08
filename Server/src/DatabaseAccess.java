package src;
import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.javatuples.Pair;


public class DatabaseAccess {

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String JDBC_URL = "jdbc:mysql://101.34.57.237:3306/java?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    static final String USER = "sandian";
    static final String PASS = "dlut2021mysq!IloveDLUT@home";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASS);
    }

    public static void closeAll(Connection conn,Statement stmt,ResultSet rs) throws SQLException {
        if(rs!=null) {
            rs.close();
        }
        if(stmt!=null) {
            stmt.close();
        }
        if(conn!=null) {
            conn.close();
        }
    }


    public static <T> String getTableName(Class<T> type){
        return NameTransformer.ClassNameTransformer.get(type.getSimpleName());
    }


    public static <T> String getProperty(String field,Class<T> type){
        String className = type.getSimpleName();
        return NameTransformer.FieldNameTransformer.get(new Pair<>(className,field));
    }

    public static int getFieldIndex(int i){
        return i+1;
    }

    public static int getColumnIndex(int i){
        return i+1;
    }

    public static void init() {
        try {
            CNT_init();
            FNT_init();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void CNT_init() throws SQLException{
        Connection conn= DatabaseAccess.getConnection();
        System.out.println("CNT init");
        //SQL statement generate
        String sql="select * from ClassNameTransformer;";
        PreparedStatement stmt= conn.prepareStatement(sql);
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();

        NameTransformer.ClassNameTransformer = new HashMap<>();
        while(rs.next()){
            NameTransformer.ClassNameTransformer.put(rs.getString("ClassName"),rs.getString("TableName"));
        }
        DatabaseAccess.closeAll(conn, stmt, rs);
    }

    public static void FNT_init() throws SQLException{
        Connection conn= DatabaseAccess.getConnection();
        System.out.println("FNT init");
        //SQL statement generate
        String sql="select * from FieldNameTransformer;";
        PreparedStatement stmt= conn.prepareStatement(sql);
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();
        NameTransformer.FieldNameTransformer = new HashMap<>();
        while(rs.next()){
            NameTransformer.FieldNameTransformer.put(new Pair<>(
                    rs.getString("ClassName"),rs.getString("FieldName")
            ),rs.getString("ColumnName"));
        }
        DatabaseAccess.closeAll(conn, stmt, rs);
    }

}

class NameTransformer{


    public static Map<String,Class<?>> StringToClass;

    public static Map<String,String> ClassNameTransformer;

    public static Map<Pair<String,String>,String> FieldNameTransformer;

    static {
        StringToClass = new HashMap<>();
        List<Class<?>> ClassList = new ArrayList<>();

        //Update this whenever add new Info class
        ClassList.add(Application.class);
        ClassList.add(ChangeRoomApp.class);
        ClassList.add(LeaveApp.class);
        ClassList.add(MoveInApp.class);
        ClassList.add(MoveOutApp.class);
        ClassList.add(BuildingInfo.class);
        ClassList.add(ClassInfo.class);
        ClassList.add(ElecPaymentInfo.class);
        ClassList.add(ManagerInfo.class);
        ClassList.add(PaymentInfo.class);
        ClassList.add(RoomInfo.class);
        ClassList.add(RoomPaymentInfo.class);
        ClassList.add(StudentInfo.class);
        ClassList.add(WaterPaymentInfo.class);

        for(Class<?> clazz:ClassList){
            StringToClass.put(clazz.getSimpleName(),clazz);
        }

    }

}

class UserAuthDAO{
    public static boolean ManagerUserPswAuth(String[] authMode,String user,String psw) throws SQLException {
        return UserPswAuth("Manager",authMode,user,psw);
    }

    public static boolean UserPswAuth(String userType,String[] authMode,String user,String psw) throws SQLException {
        Connection conn= DatabaseAccess.getConnection();
        //SQL statement generate
        String sql="select ";
        for (int i = 0; i < authMode.length; i++) {
            sql+=authMode[i];
            if(i!=authMode.length-1){
                sql+=",";
            }
            sql+=" ";
        }

        sql+="from "+userType+" where ";
        sql+="password = ? and (";
        for (int i = 0; i < authMode.length; i++) {
            sql+=authMode[i] + " = ?";
            if(i!=authMode.length-1){
                sql+=" or ";
            }
        }
        sql+= ");";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,psw);
        for (int i = 0; i < authMode.length; i++) {
            stmt.setString(i+2,user);
        }
        System.out.println(stmt);
        ResultSet rs = stmt.executeQuery();
        int i = -1;
        rs.last();
        i = rs.getRow();
        if(i==0){
            if(userType.equals("Student")){
                stmt.setString(1,"123456");
            }else{
                stmt.setString(1,"666666");
            }
            System.out.println(stmt);
            rs = stmt.executeQuery();
            rs.last();
            i = rs.getRow();
        }

        DatabaseAccess.closeAll(conn, stmt, rs);
        if(i<=0){
            return false;
        }
        else{
            return true;
        }
    }


    public static boolean StudentUserPswAuth(String[] authMode,String user,String psw) throws SQLException {
        return UserPswAuth("Student",authMode,user,psw);
    }
}

class QueryDAO{


    public static <T> String getSQLString(Map<String,Object> filters,Class<T> type){
        String sql = "select * from ";
        sql+=DatabaseAccess.getTableName(type);
        if(filters==null||filters.size()==0){
            sql+=" where 1=1;";
            return sql;
        }
        sql+=" where ";
        int i = 0;
        for (Map.Entry<String,Object> entry : filters.entrySet()) { //对where子句Operator进行修改
            String property = entry.getKey();
            String operator = "=";
            if(property.contains("_")){ //如果filter.key中包含以"_"分割的运算符
                String[] childStmt = property.split("_");
                property = childStmt[0];
                operator = childStmt[1];
            }
            sql+=DatabaseAccess.getProperty(property,type)+" ";
            sql+=operator;
            if(!operator.endsWith("null")){
                sql+=" ? ";
            }
            if(i!=filters.size()-1){
                sql+=" and ";
            }
            i++;
        }
        sql+=";";
        return sql;
    }

    //All List should be ArrayList
    public static <T> List<T> getList(Map<String,Object> filters,Class<T> type) throws SQLException {
        Connection conn= DatabaseAccess.getConnection(); //连接数据库
        PreparedStatement stmt= conn.prepareStatement(getSQLString(filters,type)); //获得SQL语句模板
        int i=0;
        for (Map.Entry<String,Object> entry : filters.entrySet()) { //对where子句进行修改
            if(entry.getKey().endsWith("null")){
                continue;
            }
            Object field = entry.getValue();
            stmt.setObject(DatabaseAccess.getFieldIndex(i),field);
            i++;
        }
        System.out.println(stmt); //Output SQL statement
        ResultSet rs = stmt.executeQuery();
        List<T> InfoList = new ArrayList<T>();
        Field[] fields = type.getFields();
        while(rs.next()){
            T info = null;
            try {
                info = type.newInstance();
                for (int j = 0; j < fields.length; j++) {
                    System.out.println(fields[j].getName()+":"+DatabaseAccess.getProperty(fields[j].getName(),type));
                    Object rsObject = rs.getObject(DatabaseAccess.getProperty(fields[j].getName(),type));
                    if(rsObject==null){
                        continue;
                    }
                    fields[j].set(info,rsObject);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            InfoList.add(info);
        }
        DatabaseAccess.closeAll(conn,stmt,rs);
        return InfoList;
    }
}

class ModifyDAO{
    public static int columnCount = 0;

    private static int getFilterIndex(int i){
        return i+1+columnCount;
    }

    public static <T> String getUpdateSQL(Map<String,Object> column,Map<String,Object> filters,Class<T> type){
        String sql = "update ";
        columnCount = column.size();
        sql+=DatabaseAccess.getTableName(type);
        sql+=" set ";
        int ind = 0;
        for (Map.Entry<String,Object> entry : column.entrySet()) {
            String property = entry.getKey();
            String operator = "=";
            sql+=DatabaseAccess.getProperty(property,type)+" ";
            sql+=operator;
            if(entry.getValue().equals("__null__")){
                sql+=" null ";
            }else{
                sql+=" ? ";
            }
            if(ind!=column.size()-1){
                sql+=", ";
            }
            ind++;
        }
        sql += " where ";
        if(filters==null||filters.size()==0){
            sql+=" 1=1;";
            return sql;
        }
        ind = 0;
        for (Map.Entry<String,Object> entry : filters.entrySet()) { //对where子句Operator进行修改
            String property = entry.getKey();
            String operator = "=";
            String propertyDB = DatabaseAccess.getProperty(property,type);
            /*if(propertyDB == null){
                column.remove(entry.getKey());
                columnCount--;
                continue;
            }*/
            if(property.contains("_")){ //如果filter.key中包含以"_"分割的运算符
                String[] childStmt = property.split("_");
                property = childStmt[0];
                operator = childStmt[1];
            }

            sql+=DatabaseAccess.getProperty(property,type)+" ";
            sql+=operator;
            if(!operator.endsWith("null")){
                sql+=" ? ";
            }
            if(ind!=filters.size()-1){
                sql+=" and ";
            }
            ind++;
        }
        sql+=";";
        return sql;
    }

    //All List should be ArrayList
    public static <T> int executeUpdate(Map<String,Object> column,Map<String,Object> filters,Class<T> type) throws SQLException {
        Connection conn= DatabaseAccess.getConnection(); //连接数据库
        PreparedStatement stmt= conn.prepareStatement(getUpdateSQL(column,filters,type)); //获得SQL语句模板
        int ind=0;
        for (Map.Entry<String,Object> entry : column.entrySet()) { //对where子句进行修改
            if(entry.getValue().equals("__null__")){
                columnCount--;
                continue;
            }
            Object field = entry.getValue();
            stmt.setObject(DatabaseAccess.getColumnIndex(ind),field);

            ind++;
        }
        ind = 0;
        for (Map.Entry<String,Object> entry : filters.entrySet()) { //对where子句进行修改
            if(entry.getKey().endsWith("null")){
                continue;
            }
            Object field = entry.getValue();
            stmt.setObject(getFilterIndex(ind),field);
            ind++;
        }
        System.out.println(stmt); //Output SQL statement
        int lines = stmt.executeUpdate();
        DatabaseAccess.closeAll(conn,stmt,null);
        return lines;
    }

    public static <T> String getInsertSQL(Map<String,Object> column,Class<T> type){
        String sql = "insert into ";
        columnCount = column.size();
        sql+=DatabaseAccess.getTableName(type);
        sql+=" ( ";
        int ind = 0;
        for (Map.Entry<String,Object> entry : column.entrySet()) {
            String property = entry.getKey();
            String propertyDB = DatabaseAccess.getProperty(property,type);
            /*if(propertyDB == null){
                column.remove(entry.getKey());
                columnCount--;
                continue;
            }*/
            sql+=DatabaseAccess.getProperty(property,type)+" ";
            if(ind!=column.size()-1){
                sql+=", ";
            }
            ind++;
        }
        sql += " ) values ( ";
        ind = 0;
        for (Map.Entry<String,Object> entry : column.entrySet()) { //对where子句Operator进行修改
            if(entry.getValue().equals("__null__")){
                sql+=" null ";
            }else{
                sql+=" ? ";
            }
            if(ind!=column.size()-1){
                sql+=", ";
            }
            ind++;
        }
        sql+=");";
        System.out.println(sql);
        return sql;
    }

    //All List should be ArrayList
    public static <T> int executeInsert(Map<String,Object> column,Class<T> type) throws SQLException {
        Connection conn= DatabaseAccess.getConnection(); //连接数据库
        PreparedStatement stmt= conn.prepareStatement(getInsertSQL(column,type)); //获得SQL语句模板
        int ind=0;
        for (Map.Entry<String,Object> entry : column.entrySet()) {
            if(entry.getValue().equals("__null__")){
                continue;
            }
            Object field = entry.getValue();
            stmt.setObject(DatabaseAccess.getColumnIndex(ind),field);
            ind++;
        }
        System.out.println(stmt); //Output SQL statement
        int lines = stmt.executeUpdate();
        DatabaseAccess.closeAll(conn,stmt,null);
        return lines;
    }

    public static <T> String getDeleteSQL(Map<String,Object> filters,Class<T> type){
        String sql = "delete from ";
        sql+=DatabaseAccess.getTableName(type);
        sql+=" where ";
        if(filters==null||filters.size()==0){
            sql+=" 1=1; ";
            return sql;
        }
        int i = 0;
        for (Map.Entry<String,Object> entry : filters.entrySet()) { //对where子句Operator进行修改
            String property = entry.getKey();
            String operator = "=";
            if(property.contains("_")){ //如果filter.key中包含以"_"分割的运算符
                String[] childStmt = property.split("_");
                property = childStmt[0];
                operator = childStmt[1];
            }
            sql+=DatabaseAccess.getProperty(property,type)+" ";
            sql+=operator;
            if(!operator.endsWith("null")){
                sql+=" ? ";
            }
            if(i!=filters.size()-1){
                sql+=" and ";
            }
            i++;
        }
        sql+=";";
        return sql;
    }

    //All List should be ArrayList
    public static <T> int executeDelete(Map<String,Object> filters,Class<T> type) throws SQLException {
        Connection conn= DatabaseAccess.getConnection(); //连接数据库
        PreparedStatement stmt= conn.prepareStatement(getDeleteSQL(filters,type)); //获得SQL语句模板
        int i=0;
        for (Map.Entry<String,Object> entry : filters.entrySet()) { //对where子句进行修改
            if(entry.getKey().endsWith("null")){
                continue;
            }
            Object field = entry.getValue();
            stmt.setObject(DatabaseAccess.getColumnIndex(i),field);
            i++;
        }
        System.out.println(stmt); //Output SQL statement
        int lines = stmt.executeUpdate();
        DatabaseAccess.closeAll(conn,stmt,null);
        return lines;
    }
}