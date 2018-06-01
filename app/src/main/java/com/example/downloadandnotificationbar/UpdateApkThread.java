package com.example.downloadandnotificationbar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.zams.www.MainFragment;
import com.zams.www.PersonCenterActivity;
import com.zams.www.R;
import com.zams.www.UserLoginActivity;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateApkThread extends Thread {
    private String downloadUrl;
    private File saveFile;
    private String fileName;
    private Context context;
    private NotificationManager notificationManager;// 状态栏通知管理类
    private Notification notification;// 状态栏通知
    private RemoteViews notificationViews;// 状态栏通知显示的view
    private Timer timer;// 定时器，用于更新下载进度
    private TimerTask task;// 定时器执行的任务

    private final int notificationID = 1;// 通知的id
    private final int updateProgress = 1;// 更新状态栏的下载进度
    private final int downloadSuccess = 2;// 下载成功
    private final int downloadError = 3;// 下载失败
    private DownLoadUtil downLoadUtil;
    ProgressDialog downLoadDialog;

    public UpdateApkThread(String downloadUrl, String fileLocation, String fileName, Context context) {
        this.downloadUrl = downloadUrl;
        this.saveFile = new File(fileLocation);
        this.context = context;
        this.fileName = fileName;
        showDialog();
    }

    private void showDialog() {
        MainFragment.zhuangtai = true;
        // 进度条对话框
        downLoadDialog = new ProgressDialog(context);
        downLoadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downLoadDialog.setMessage("正在下载更新");
        downLoadDialog.setCanceledOnTouchOutside(true);
        downLoadDialog.setProgressNumberFormat(null);
        downLoadDialog.show();
    }

    @Override
    public void run() {
        super.run();
        try {
            initNofication();
            handlerTask();
            downLoadUtil = new DownLoadUtil();
            int downSize = downLoadUtil.downloadUpdateFile(downloadUrl, saveFile, fileName, callback);
            if (downSize == downLoadUtil.getRealSize() && downSize != 0) {
                downLoadDialog.setMax(downSize);
                handler.sendEmptyMessage(downloadSuccess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author wangqian@iliveasia.com
     * @Description: 初始化通知栏
     */
    private void initNofication() {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.drawable.app_zams;// 设置通知消息的图标
        notification.tickerText = "正在下载...";// 设置通知消息的标题
        notificationViews = new RemoteViews(context.getPackageName(), R.layout.down_notification);
        notificationViews.setImageViewResource(R.id.download_icon, R.drawable.app_zams);
    }

    /**
     * @author wangqian@iliveasia.com
     * @Description: 定时通知handler去显示下载百分比
     */
    private void handlerTask() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(updateProgress);
            }
        };
        timer.schedule(task, 500, 500);
    }

    private static final String TAG = "UpdateApkThread";
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == updateProgress) {// 更新下载进度
                int fileSize = downLoadUtil.getRealSize();
                int totalReadSize = downLoadUtil.getTotalSize();
                if (totalReadSize > 0) {
                    float size = (float) totalReadSize * 100 / (float) fileSize;
                    DecimalFormat format = new DecimalFormat("0.00");
                    String progress = format.format(size);
                    notificationViews.setTextViewText(R.id.progressTv, "已下载" + progress + "%");
                    notificationViews.setProgressBar(R.id.progressBar, 100, (int) size, false);
                    notification.contentView = notificationViews;
                    notificationManager.notify(notificationID, notification);
                    if (downLoadDialog != null && downLoadDialog.isShowing()) {
                        downLoadDialog.setProgress((int) size);
                    }
                }
            } else if (msg.what == downloadSuccess) {// 下载完成
                notificationViews.setTextViewText(R.id.progressTv, "下载完成");
                notificationViews.setProgressBar(R.id.progressBar, 100, 100, false);
                notification.contentView = notificationViews;
                notification.tickerText = "下载完成";
                notificationManager.notify(notificationID, notification);
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }
                File file = new File(saveFile + "/zams.apk");
                if (downLoadDialog != null && downLoadDialog.isShowing()) {
                    downLoadDialog.dismiss();
                    installApk(file);
                }
                Log.e(TAG, "handleMessage: 下载完成安装");
                MainFragment.zhuangtai = false;
                UserLoginActivity.zhuangtai = false;
                PersonCenterActivity.zhuangtai = false;
                // 安装apk
                Uri uri = Uri.fromFile(file);
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                // PendingIntent 通知栏跳转
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, installIntent, 0);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notification.contentIntent = pendingIntent;
                notification.contentView.setTextViewText(R.id.progressTv, "下载完成，点击安装");
                notificationManager.notify(notificationID, notification);

            } else if (msg.what == downloadError) {// 下载失败
                MainFragment.zhuangtai = false;
                UserLoginActivity.zhuangtai = false;
                PersonCenterActivity.zhuangtai = false;
                if (timer != null && task != null) {
                    timer.cancel();
                    task.cancel();
                    timer = null;
                    task = null;
                }
                Toast.makeText(context, "网络异常，下载失败", Toast.LENGTH_SHORT);
                if (downLoadDialog != null && downLoadDialog.isShowing()) {
                    downLoadDialog.dismiss();
                }
                notificationManager.cancel(notificationID);
            }
        }

    };

    // 安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        // 执行动作
        intent.setAction(Intent.ACTION_VIEW);
        // 执行的数据类型
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");// 此处Android应为android，否则造成安装不了
        context.startActivity(intent);
    }

    /**
     * 下载回调
     */
    DownloadFileCallback callback = new DownloadFileCallback() {

        @Override
        public void downloadError(String msg) {
            handler.sendEmptyMessage(downloadError);
        }
    };

}
