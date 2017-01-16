package lifetime.apper.klc.lifetime;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by klc on 2017/1/7.
 */

public class DateSetting implements DatePickerDialog.OnDateSetListener{
    Context context;
    public DateSetting(Context context){
        this.context = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        bornDay mbornDay = new bornDay(year,month,dayOfMonth);
    }
}
