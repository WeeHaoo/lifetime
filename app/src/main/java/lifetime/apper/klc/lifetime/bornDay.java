package lifetime.apper.klc.lifetime;

import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.sql.Date;
import java.util.Locale;

/**
 * Created by klc on 2017/1/7.
 */

@TargetApi(Build.VERSION_CODES.N)
public class bornDay {
    public static long lifesecMAX;
    public static long lifesecNOW;
    public static long lifecurage;
    public static long longage;
    public static int age;
    public static Calendar calendarMAX;
    public static String username="";
    public static final String data="DATA";
    public static final String lifesecMAXstr="MAX";
    public static final String lifesecNOWstr="NOW";
    public  static final String lifekey="LIFE";
    public  static final String name="NAME";
    public  static final String born="BORNDAY";
    public  static final String wishage="WISHAGE";
    public  static final String smoke="SMOKE";
    public  static final String betel="BETEL";
    public  static final String sport="SPORT";
    public static int year,month,day;
    int curyear,curmonth,curdate;
    public bornDay(int year, int month, int day){

        this.year = year;
        this.month = month;
        this.day = day;
    }
    public bornDay(){
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getLifeSec(){
        calendarMAX = Calendar.getInstance(Locale.getDefault());
        Calendar calendarNOW;
        calendarNOW = Calendar.getInstance(Locale.getDefault());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(year,month,day);
        calendarMAX.set(year+age,month,day,12,0);
        lifecurage = (calendarNOW.getTimeInMillis()-calendar2.getTimeInMillis())/(1000);
        //remainder life
        lifesecNOW = (calendarMAX.getTimeInMillis()-calendarNOW.getTimeInMillis())/(1000);
        Log.d("MYLOG",lifesecNOW+"");
        lifesecMAX = (calendarMAX.getTimeInMillis()-calendar2.getTimeInMillis())/(1000);
        Log.d("MYLOG",lifesecMAX+"");
        longage = calendarMAX.getTimeInMillis();
    }

    public Calendar getLifeMax(){
        Calendar mcalendarMAX = Calendar.getInstance(Locale.getDefault());
        mcalendarMAX.set(year+age,month,day,12,0);
        return mcalendarMAX;
    }
}
