package com.example.wzes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by WZES on 2016/12/18.
 */

public class calculateTime {
    long Time;
    String year ;
    String month ;
    String day ;
    String hour ;
    String minute ;
    public calculateTime(String data){
        year = data.substring(0, 4);
        month = data.substring(5, 7);
        day = data.substring(8, 10);
        hour = data.substring(11, 13);
        minute = data.substring(14, 16);

    }
    public long time(){
        Calendar localCalendar = Calendar.getInstance();
        String i = String.valueOf(localCalendar.get(1));
        //String y = String.valueOf(localCalendar.get(1));
        String j = String.valueOf(1 + localCalendar.get(2));
        String k = String.valueOf(localCalendar.get(5));
        String l = String.valueOf(localCalendar.get(11));
        String i1 = String.valueOf(localCalendar.get(12));
        String i2 = String.valueOf(localCalendar.get(10));
        if (j.length()<2){
            j = "0"+j;
        }
        if (k.length()<2){
            k = "0"+k;
        }
        if (l.length()<2){
            l = "0"+l;
        }
        if (i1.length()<2){
            i1 = "0"+i1;
        }
        if (i2.length()<2){
            i2 = "0"+i2;
        }
        //开始时间;
        String str1 = i+j+k
                +" "+l+":"+i1+":"+i2; //"yyyyMMdd"格式 如 20131022
        //System.out.println("\n结束时间:");
        String str2 = year+month+day+" "+hour+":"+minute+":"+"00"; //"yyyyMMdd"格式 如 20131022
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");//输入日期的格式
        java.util.Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(str1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(date1);
        cal2.setTime(date2);
        double dayCount = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*60);//从间隔毫秒变成间隔小时数
        //System.out.println("\n相差"+dayCount+"天");

        return (long) dayCount;
    }

}
