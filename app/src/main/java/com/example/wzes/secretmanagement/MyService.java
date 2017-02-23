package com.example.wzes.secretmanagement;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.wzes.util.calculateTime;
import com.example.wzes.util.memoData;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MyService extends Service {
    private String lastUsername;
    private boolean first;
    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!first){
                    //获取上次登录的用户名
                    lastUsername = intent.getStringExtra("lastUsername");
                    first = true;
                }
                //获取未完成的待办
                List<memoData> memoDatas = DataSupport.where("user = ? and finish = ?", lastUsername, "false").find(memoData.class);
                for(memoData memo: memoDatas){
                    calculateTime time = new calculateTime(memo.getDate());
                    long Time = time.time();
                    Log.i("GGGG", "run: "+ Time);
                    if(Time <= 20 && Time >= 0){
                        myNotification(memo.getContext(), Time);
                    }
                }
            }
            void myNotification(String content,long date) {
                //获取NotificationManager实例
                NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                Notification notification = new NotificationCompat.Builder(MyService.this)
                        //设置小图标
                        .setSmallIcon(R.drawable.people)
                        //设置通知标题
                        .setContentTitle("时间还剩"+date+"分钟")
                        .setVibrate(new long[]{0,2000,1000,2000,1000,2000})
                        //设置通知内容
                        .setContentText(content)
                        .setWhen(System.currentTimeMillis())
                        .setPriority(Notification.PRIORITY_MAX)
                        .build();
                notification.flags = Notification.FLAG_AUTO_CANCEL ;
                notifyManager.notify(1, notification);
            }
        }).start();
        Log.i("my", "onStartCommand: ");
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 1000*60;
        long now = SystemClock.elapsedRealtime()+ time;
        Intent i = new Intent(this, MyService.class);
        PendingIntent pi = PendingIntent.getService(this, 0 , i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, now, pi);

        return super.onStartCommand(intent, flags, startId);
    }
}
