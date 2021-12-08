package src;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class Query<T>{
    protected Class<T> clazz;
    protected Field[] fields;
    protected List<String> fieldStrs;
    protected Map<String,Object> filter;
    public Query(Class<T> clazz){
        this.clazz = clazz;
        fields = clazz.getFields();
        fieldStrs = new ArrayList<String>();

        filter = new JSONObject();
        for(Field field:fields){
            fieldStrs.add(field.getName());
        }
    }

    public void setEqualValue(String fieldName,Object value){
        if(fieldName.toUpperCase().contains("DATE")){
            filter.put(fieldName,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((long)value)));
        }
        if(fieldStrs.contains(fieldName)){
            filter.put(fieldName,value);
        }
    }
    public void setEqualValue(Field field,Object value){
        setEqualValue(field.getName(),value);
    }

    public void setIsNull(String fieldName){
        if(fieldStrs.contains(fieldName)){
            if(filter.containsKey(fieldName+"_is not null")){
                filter.remove(fieldName+"_is not null");
            }
            filter.put(fieldName+"_is null",new Object());
        }
    }
    public void setIsNull(Field field){
        setIsNull(field.getName());
    }

    public void setNotNull(String fieldName){
        if(fieldStrs.contains(fieldName)){
            if(filter.containsKey(fieldName+"_is null")){
                filter.remove(fieldName+"_is null");
            }
            filter.put(fieldName+"_is not null",new Object());
        }
    }
    public void setNotNull(Field field){
        setNotNull(field.getName());
    }


    public void setMinValue(String fieldName,Object value){
        if(fieldName.toUpperCase().contains("DATE")){
            filter.put(fieldName+"_>=",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((long)value)));
        }
        if(fieldStrs.contains(fieldName)){
            filter.put(fieldName+"_>=",value);
        }
    }

    public void setMinValue(String fieldName,int value){
        if(fieldStrs.contains(fieldName)){
            filter.put(fieldName+"_>=",value);
        }
    }

    public void setMinValue(Field field,Object value){
        setMinValue(field.getName(),value);
    }
    public void setMaxValue(String fieldName,Object value){
        if(fieldName.toUpperCase().contains("DATE")){
            filter.put(fieldName+"_<=",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((long)value)));
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

    public List<T> ExecuteQuery(){
        IServerIO serverIO = new ServerIO();
        serverIO.EstablishConnection();
        serverIO.SendQueryRequest(clazz.getSimpleName(),filter);
        List<T> results = serverIO.GetQueryInfo(clazz);
        serverIO.ConnectionClosed();
        return results;
    }

}
