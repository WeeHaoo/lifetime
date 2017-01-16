package lifetime.apper.klc.lifetime;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by klc on 2017/1/7.
 */

public class aboveRun extends Activity {
    private TextView mbornDate;
    private EditText muserName;
    private EditText mwishAge;
    bornDay mbornDay;
    private SharedPreferences SP;
    private SharedPreferences life;
    int sporttime=0;
    int smokenum=0;
    int betelnum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        mbornDay = new bornDay();
        SP = getSharedPreferences(mbornDay.data,0);
        life = getSharedPreferences(mbornDay.name+"_"+mbornDay.lifekey,0);
        mbornDate = (TextView)findViewById(R.id.bornDate);
        muserName = (EditText)findViewById(R.id.userName);
        mwishAge = (EditText)findViewById(R.id.wishlife);
    }
    //Button function
    public void chooseDate(View view){
        pickerdialogs mpickdialogs = new pickerdialogs();
        mpickdialogs.show(getFragmentManager(),"date_picker");
    }

    public void btn1(View view){
        saveData();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public  void saveData(){
        mbornDay.username = muserName.getText().toString();
        mbornDay.age = Integer.parseInt(mwishAge.getText().toString());
        SP.edit().putString(mbornDay.name,mbornDay.username)
                .putInt(mbornDay.wishage,mbornDay.age)
                .putInt("YEAR",mbornDay.year)
                .putInt("MONTH",mbornDay.month)
                .putInt("DAY",mbornDay.day)
                .putInt(mbornDay.smoke,smokenum)
                .putInt(mbornDay.betel,betelnum)
                .putInt(mbornDay.sport,sporttime)
                .commit();
        Log.d("MYLOG",mbornDay.username+" : "+mbornDay.year+" / "+mbornDay.month+" / "+mbornDay.day);
        mbornDay.getLifeSec();
        life.edit().putLong(mbornDay.lifesecMAXstr,mbornDay.lifesecMAX)
                    .putLong("curage",mbornDay.lifecurage)
                    .putLong("longAge",mbornDay.longage)
                    .putLong(mbornDay.lifesecNOWstr,mbornDay.lifesecNOW).commit();
        setResult(RESULT_OK);
        Intent backgroundservice = new Intent(aboveRun.this, backgroundService.class);
        startService(backgroundservice);
        finish();
    }
}
