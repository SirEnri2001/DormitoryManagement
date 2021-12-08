package src;

import java.lang.reflect.Field;

public class InfoClass {
    @Override
    public String toString(){
        String res = getClass().getSimpleName()+": ";
        Field[] fields = getClass().getFields();
        for(Field field:fields){
            try {
                res+=field.getName()+": "+field.get(this)+" ";
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
