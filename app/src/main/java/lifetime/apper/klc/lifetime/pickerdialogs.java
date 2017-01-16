package lifetime.apper.klc.lifetime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

/**
 * Created by klc on 2017/1/7.
 */

public class pickerdialogs extends DialogFragment{
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateSetting dateSetting = new DateSetting(getActivity());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(getActivity(), dateSetting, year, month, day);


        return dialog;
    }
}
