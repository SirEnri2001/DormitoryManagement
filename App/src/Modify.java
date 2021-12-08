package src;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Modify<T> {
    protected Class<T> clazz;
    protected Field[] fields;
    protected List<String> fieldStrs;
    protected Map<String,Object> filter;
    protected Map<String,Object> column;
    public Modify(Class<T> clazz){
        this.clazz = clazz;
        fields = clazz.getFields();
        fieldStrs = new ArrayList<String>();

        filter = new JSONObject();
        column = new JSONObject();
        for(Field field:fields){
            fieldStrs.add(field.getName());
        }
    }

    public void setUpdateValue(String fieldName,Object value){
        if(fieldName.toUpperCase().contains("DATE")){
            column.put(fieldName,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
            return;
        }
        if(fieldStrs.contains(fieldName)){
            column.put(fieldName,value);
        }
    }
    public void setUpdateValue(Field field,Object value){
        setUpdateValue(field.getName(),value);
    }

    public void setUpdateNull(String fieldName){
        if(fieldStrs.contains(fieldName)){
            column.put(fieldName,"__null__");
        }
    }
    public void setUpdateNull(Field field){
        setUpdateNull(field.getName());
    }

    public void setIsNull(String fieldName){
        if(fieldStrs.contains(fieldName)){
            filter.put(fieldName+"_is null",new Object());
        }
    }
    public void setIsNull(Field field){
        setIsNull(field.getName());
    }

    public void setNotNull(String fieldName){
        if(fieldStrs.contains(fieldName)){
            filter.put(fieldName+"_is not null",new Object());
        }
    }
    public void setNotNull(Field field){
        setNotNull(field.getName());
    }

    public void setEqualValue(String fieldName,Object value){
        if(fieldName.toUpperCase().contains("DATE")){
            column.put(fieldName,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((long)value)));
        }
        if(fieldStrs.contains(fieldName)){
            filter.put(fieldName,value);
        }
    }
    public void setEqualValue(Field field,Object value){
        setEqualValue(field.getName(),value);
    }

    public void setMinValue(String fieldName,Object value){
        if(fieldName.toUpperCase().contains("DATE")){
            column.put(fieldName+"_>=",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((long)value)));
        }
        if(fieldStrs.contains(fieldName)){
            filter.put(fieldName+"_>=",value);
        }
    }
    public void setMinValue(Field field,Object value){
        setMinValue(field.getName(),value);
    }
    public void setMaxValue(String fieldName,Object value){
        if(fieldName.toUpperCase().contains("DATE")){
            column.put(fieldName+"_<=",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((long)value)));
        }
        if(fieldStrs.contains(fieldName)){
            filter.put(fieldName+"_<=",value);
        }
    }
    public void setMaxValue(Field field,Object value){
        setMaxValue(field.getName(),value);
    }

    public void setLikeValue(String fieldName,String value){
        if(fieldStrs.contains(fieldName)){
            filter.put(fieldName+"_like",value);
        }
    }
    public void setLikeValue(Field field,String value){
        setLikeValue(field.getName(),value);
    }
    public int ExecuteInsert(){
        IServerIO serverIO = new ServerIO();
        serverIO.EstablishConnection();
        int rows = serverIO.SendInsertRequest(clazz.getSimpleName(),column);
        serverIO.ConnectionClosed();
        return rows;
    }

    public int ExecuteUpdate(){
        IServerIO serverIO = new ServerIO();
        serverIO.EstablishConnection();
        int row = serverIO.SendUpdateRequest(clazz.getSimpleName(),column,filter);
        return row;
    }

    public int ExecuteDelete(){
        IServerIO serverIO = new ServerIO();
        serverIO.EstablishConnection();
        int rows = serverIO.SendDeleteRequest(clazz.getSimpleName(),filter);
        serverIO.ConnectionClosed();
        return rows;
    }
}
