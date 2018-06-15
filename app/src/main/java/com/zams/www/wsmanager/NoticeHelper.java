package com.zams.www.wsmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;


import com.zams.www.MainFragment;
import com.zams.www.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Administrator on 2018/6/14.
 */

public class NoticeHelper {

    public static void senNotice(int noticId, Context ctx, String title, String content) {

        /**
         *  创建通知栏管理工具
         */

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);

        /**
         *  实例化通知栏构造器
         */

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);

        /**
         *  设置Builder
         */
        //设置标题
        mBuilder.setContentTitle(title)
                //设置内容
                .setContentText(content)
                //设置小图标
                .setSmallIcon(R.drawable.zams)
                //设置通知时间
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                //首次进入时显示效果
                .setTicker("收到中安民的一条消息！")
                //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
                .setDefaults(Notification.DEFAULT_SOUND);
        //发送通知请求
        Intent intent = new Intent(ctx, MainFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        Notification build = mBuilder.build();
        notificationManager.notify(noticId, build);
    }

}
