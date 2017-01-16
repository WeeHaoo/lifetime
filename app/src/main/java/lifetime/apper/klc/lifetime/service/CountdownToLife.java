package lifetime.apper.klc.lifetime.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

import lifetime.apper.klc.lifetime.R;
import lifetime.apper.klc.lifetime.lifeCard;

/**
 * Created by 151305 on 2017/1/12.
 */

public class CountdownToLife extends Service {
    private static final int ALARM_DURATION  = 5 * 60 * 1000; // service 自启间隔
    private static final int UPDATE_DURATION = 1 * 1000;     // Widget 更新间隔
    private static final int UPDATE_MESSAGE  = 1000;
    private Context context;
    private UpdateHandler updateHandler; // 更新 Widget 的 Handler

    private int ProgressBarValue=0;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 每个 ALARM_DURATION 自启一次
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getBaseContext(), lifeCard.class);
        PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 0,
                alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + ALARM_DURATION, pendingIntent);
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Message message = updateHandler.obtainMessage();
        message.what = UPDATE_MESSAGE;
        updateHandler = new UpdateHandler();
        updateHandler.sendMessageDelayed(message, UPDATE_DURATION);
    }

    private void updateWidget() {
        // 更新 Widget
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.life_card);
        context=getApplicationContext();
        //String.format(String.valueOf(getString(R.string.AFB_Read_Total1_Text)),Integer.toString(l),ALL_Money)
//        views.setTextViewText(R.id.UserName_textView, widgetText);
        ProgressBarValue++;
        if(ProgressBarValue>100)
            ProgressBarValue=0;
        remoteViews.setProgressBar(R.id.progressBar,100,ProgressBarValue,false);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(new ComponentName(context, lifeCard.class), remoteViews);

        // 发送下次更新的消息
        Message message = updateHandler.obtainMessage();
        message.what = UPDATE_MESSAGE;
        updateHandler.sendMessageDelayed(message, UPDATE_DURATION);
    }

    protected final class UpdateHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MESSAGE:
                    updateWidget();
                    break;
                default:
                    break;
            }
        }
    }
}