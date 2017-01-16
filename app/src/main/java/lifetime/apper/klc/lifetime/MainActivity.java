package lifetime.apper.klc.lifetime;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static lifetime.apper.klc.lifetime.bornDay.lifesecMAX;
import static lifetime.apper.klc.lifetime.bornDay.lifesecMAXstr;

public class MainActivity extends AppCompatActivity {

    public final static int REQUEST_LOGIN = 0;
    String Nname;
    int Nage;
    bornDay mbornDay;
    Intent backgroundservice;
    private long mlifesec;
    private SharedPreferences life;
    private SharedPreferences SP;
    ProgressBar progressBar;
    public long remainderlife;
    TextView remainsec,remainmin,remainhr,remainday,remainmon,str1;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        remainsec = (TextView)findViewById(R.id.remainderSec);
        remainmin = (TextView)findViewById(R.id.remainderMin);
        remainhr = (TextView)findViewById(R.id.remainderHr);
        remainday = (TextView)findViewById(R.id.remainderDay);
        remainmon = (TextView)findViewById(R.id.remainderMon);
        str1 = (TextView)findViewById(R.id.text1);
        mbornDay = new bornDay();
        life = getSharedPreferences(mbornDay.name+"_"+mbornDay.lifekey,0);
        SP = getSharedPreferences(mbornDay.data,0);
        remainderlife = life.getLong(mbornDay.lifesecNOWstr,0);
        if(remainderlife<1) {
            Intent callsetting = new Intent(MainActivity.this, aboveRun.class);
            startActivityForResult(callsetting, REQUEST_LOGIN);
        }else{
            mbornDay.lifesecMAX = life.getLong(mbornDay.lifesecMAXstr,0);
            mbornDay.lifesecNOW = life.getLong(mbornDay.lifesecNOWstr,0);
            mbornDay.lifecurage = life.getLong("curage",0);
            mbornDay.age = SP.getInt(mbornDay.wishage,mbornDay.age);
            mbornDay.year = SP.getInt("YEAR",0);
            mbornDay.month = SP.getInt("MONTH",0);
            mbornDay.day = SP.getInt("DAY",0);
            mbornDay.username = SP.getString(mbornDay.name,"");
            remainderlife = mbornDay.lifesecNOW;
            Log.d("MYLOG","YOUR WANT TO LIFE FOR "+mbornDay.getLifeMax().getTime()+" \n CURRENT AGE "+
                    mbornDay.lifesecNOW+" / "+mbornDay.lifesecMAX);
            backgroundservice = new Intent(MainActivity.this, backgroundService.class);
            startService(backgroundservice);
        }
        new whileisdone().execute();

    }

    protected  void onActivityResult(int requestCode,int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_LOGIN:
                if(resultCode != RESULT_OK)finish();
                break;
        }
    }

    @Override
    protected void onPause(){
        mbornDay.lifesecNOW = remainderlife;
        life.edit().putLong(mbornDay.lifesecMAXstr,mbornDay.lifesecMAX)
                .putLong(mbornDay.lifesecNOWstr,mbornDay.lifesecNOW).commit();
        Log.d("MYLOG", "Remainderlife: "+remainderlife);
        super.onPause();
    }

    @Override
    protected  void onResume(){
        mbornDay.lifesecMAX = life.getLong(mbornDay.lifesecMAXstr,0);
        mbornDay.lifesecNOW = life.getLong(mbornDay.lifesecNOWstr,0);
        mbornDay.age = SP.getInt(mbornDay.wishage,mbornDay.age);
        mbornDay.year = SP.getInt("YEAR",0);
        mbornDay.month = SP.getInt("MONTH",0);
        mbornDay.day = SP.getInt("DAY",0);
        mbornDay.username = SP.getString(mbornDay.name,"");
        remainderlife = mbornDay.lifesecNOW;
        Log.d("MYLOG", "Resume: "+mbornDay.lifesecNOW);
        super.onResume();
    }






    private class whileisdone extends AsyncTask<Long,Long,String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            int parseMAX = (int) (mbornDay.lifesecMAX/1000);
            Log.d("MYLOG", "MAX: "+parseMAX);
            progressBar.setMax(parseMAX);
            int parseNOW =(int)(mbornDay.lifesecNOW/1000);
            Log.d("MYLOG", "now: "+parseNOW);
            progressBar.setProgress(parseNOW);
        }




        protected void onProgressUpdate(Long... params) {
            super.onProgressUpdate(params);
            int setprogress = Integer.parseInt(params[1].toString());
            progressBar.setMax((int) (mbornDay.lifesecMAX/1000));
            progressBar.setProgress(setprogress);
            //Log.d("MYLOG", "NOW: "+params[0]);
            remainsec.setText(params[0]+" 秒");
            remainmin.setText(params[2]+" 分鐘");
            remainhr.setText(params[3]+" 小時");
            remainday.setText(params[4]+" 天");
            remainmon.setText(params[5]+" 月");
            str1.setText(mbornDay.username+" 您目前 "+(mbornDay.lifecurage/(60*60*24*30*12))+" 歲，你想活到 "+mbornDay.age+" 歲，只剩下 "+params[6]+" 年了");
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        protected String doInBackground(Long... params) {
            int s = 0;
            while (s==0){
                try {
                    remainderlife = mbornDay.lifesecNOW;
                    Thread.sleep(1000);
                    long prosbar = remainderlife;
                    Log.d("MYLOG","thread num: "+remainderlife);
                    long remSec = prosbar/1000;
                    //int maxHr = (int) (maxsec/(60*60));
                    long remMin = prosbar/60;
                    long remHr = remMin/60;
                    long remDay = remHr/24;
                    long remMon = remDay/30;
                    long remYear = remMon/12;

                    publishProgress(prosbar,remSec,remMin,remHr,remDay,remMon,remYear);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d("MYLOG", "is done");
            return null;
        }
        protected void onPostExecute(String result)
        {

        }
    }

    public void settingBtn(View view){
        Intent callsetting = new Intent(MainActivity.this, aboveRun.class);
        startActivity(callsetting);
    }

}
