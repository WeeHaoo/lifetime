package lifetime.apper.klc.lifetime;


import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;
import java.util.Locale;

/**
 * Created by c1103304 on 2017/1/9.
 */

public class backgroundService extends Service{
    public long remainderlife;
    bornDay mbornDay;
    long maxsec;
    long lifeage;
    private SharedPreferences life;
    private SharedPreferences SP;
    private SharedPreferences SP2;
    String lifesecNOWstr;
    String remainderStr;
    private Handler handler = new Handler();
    Calendar col;
    @TargetApi(Build.VERSION_CODES.N)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mbornDay = new bornDay();
        life = getSharedPreferences(mbornDay.name+"_"+mbornDay.lifekey,0);
        SP2 = getSharedPreferences("DATA",0);
        SP2.edit().putString("1",mbornDay.data)
                .putString("2",mbornDay.name+"_"+mbornDay.lifekey)
                .putString("3",mbornDay.lifesecMAXstr)
                .putString("4",mbornDay.lifesecNOWstr)
                .commit();

    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MYLOG","onStart");
        mbornDay = new bornDay();
        SP2 = getSharedPreferences("DATA",0);
        String dataName = SP2.getString("1","");
        String lifeName = SP2.getString("2","");
        String lifesecMAXstr = SP2.getString("3","");
        lifesecNOWstr = SP2.getString("4","");
        life = getSharedPreferences(lifeName,0);

        lifeage = life.getLong("longAge",0);
        remainderStr = lifesecNOWstr;
        maxsec = life.getLong(lifesecMAXstr,0);
        remainderlife = life.getLong(lifesecNOWstr,0);





        Log.d("MYLOG","threads num: "+remainderlife);
        handler.postDelayed(showTime, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    private Runnable showTime = new Runnable() {
        @TargetApi(Build.VERSION_CODES.N)
        public void run() {
            //log目前時間
            col = Calendar.getInstance(Locale.getDefault());
            Log.d("MYLOG","maxsec: "+lifeage);
            if(remainderlife<0){
                String lifeName = SP2.getString("2","");
                String lifesecMAXstr = SP2.getString("3","");
                lifesecNOWstr = SP2.getString("4","");
                life = getSharedPreferences(lifeName,0);
                maxsec = life.getLong(lifesecMAXstr,0);
                remainderlife = life.getLong(lifesecNOWstr,0);
                handler.postDelayed(this, 1000);
            }else {
                remainderlife = ((lifeage - col.getTimeInMillis()) / 1000);
                Log.d("MYLOG","now time: "+col.getTimeInMillis() / 1000);
                Log.d("MYLOG", "threads num: " + remainderlife);
                life.edit().putLong(remainderStr, remainderlife).commit();
                mbornDay.lifesecNOW = remainderlife;
                handler.postDelayed(this, 1000);
            }
        }
    };
}
