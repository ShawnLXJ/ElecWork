package cn.lxj.swork.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringCoverDate {

    public static Date stringConvertDate(String edate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
             date = format.parse(edate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
}
