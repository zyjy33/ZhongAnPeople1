package com.zams.www;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.Constant;
import com.android.hengyu.web.Location;
import com.android.hengyu.web.RealmName;
import com.example.downloadandnotificationbar.UpdateApkThread;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.DBManager;
import com.hengyushop.demo.activity.ZhongAnMSActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.MyShopPingCarActivity;
import com.hengyushop.demo.service.YunshangServiceActivity;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zams.www.weiget.PermissionSetting;
import com.zams.www.wsmanager.WsManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


@SuppressWarnings("ResourceType")
public class MainFragment extends BaseActivity {
    private RadioButton main_bottom_rtn0, main_bottom_rtn1, main_bottom_rtn2,
            main_bottom_rtn3, main_bottom_rtn4;
    private IWXAPI api;
    private WareDao wareDao;
    private RadioGroup main_bottom_rtns;
    private List<UserRegisterData> list_isLogin;
    private String yth, key;
    private String strUrl;
    private String strUrl2 = RealmName.REALM_NAME + "/apkdown/ysj_apk/version.xml";
    private String strUr2 = RealmName.REALM_NAME_LL + "/get_apk_version?browser=android";
    private String url, version, updatainfo, c_version;
    private SharedPreferences spPreferences;
    private String URL;
    public static String user_name, user_id, nickname;
    public static boolean zhuangtai = false;
    public static boolean panduan = false;

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public static Handler handlerll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_activity);

        SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
        String user_name = spPreferences.getString("user", "");
        System.out.println("user_name-----用户信息---------" + user_name);
        if (!user_name.equals("")) {
            getUserxinxi(user_name);
        }
        handlerll = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        main_bottom_rtns.check(0);
                        break;
                }
            }
        };
        AndPermission.with(this)
                .permission(Permission.READ_PHONE_STATE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        WsManager.getInstance().init();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        new PermissionSetting(MainFragment.this).showSetting(permissions);
                    }
                }).start();


        popupWindowMenu = new MyPopupWindowMenu(MainFragment.this);
        manager = getFragmentManager();
        ((Location) getApplicationContext()).reloadClient();
        ((Location) getApplicationContext()).startLocation();
        main_bottom_rtns = (RadioGroup) findViewById(R.id.main_bottom_rtns);
        main_bottom_rtn0 = (RadioButton) findViewById(R.id.main_bottom_rtn0);
        main_bottom_rtn1 = (RadioButton) findViewById(R.id.main_bottom_rtn1);
        main_bottom_rtn2 = (RadioButton) findViewById(R.id.main_bottom_rtn2);
        main_bottom_rtn3 = (RadioButton) findViewById(R.id.main_bottom_rtn3);
        main_bottom_rtn4 = (RadioButton) findViewById(R.id.main_bottom_rtn4);

        main_bottom_rtns.setOnCheckedChangeListener(listener);
        main_bottom_rtns.check(0);
        new Thread() {
            public void run() {
                api = WXAPIFactory.createWXAPI(MainFragment.this,
                        Constant.APP_ID);
                wareDao = new WareDao(getApplicationContext());
                list_isLogin = wareDao.findisLogin();
                reView();

                final String dbFile1 = "/data/data/" + DBManager.PACKAGE_NAME
                        + "/databases/work.db";
                final String dbFile2 = "/data/data/" + DBManager.PACKAGE_NAME
                        + "/databases/hcz.db";
                final String dbFile3 = "/data/data/" + DBManager.PACKAGE_NAME
                        + "/databases/tuangou.db";
                final String dbFile4 = "/data/data/" + DBManager.PACKAGE_NAME
                        + "/databases/jdpiao.db";
                final String dbFile0 = "/data/data/" + DBManager.PACKAGE_NAME
                        + "/databases/city.db";
                copyCityDB(dbFile0);
                copyWorkDB(dbFile1);
                copyHczDB(dbFile2);
                copyTuanDB(dbFile3);
                copyJDB(dbFile4);

            }

            ;
        }.start();


    }

    UserRegisterllData data;
    Editor editor;

    private void getUserxinxi(String user_name) {

        try {

            String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username=" + user_name + "";
            System.out.println("======11=============" + strUrlone);
            AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
                public void onSuccess(int arg0, String arg1) {
                    try {
                        System.out.println("======66输出用户资料=============" + arg1);
                        JSONObject object = new JSONObject(arg1);
                        String status = object.getString("status");
                        String datetime = object.getString("datetime");
                        JSONObject obj = object.getJSONObject("data");
                        if (status.equals("y")) {
                            data = new UserRegisterllData();
                            data.user_name = obj.getString("user_name");
                            data.user_code = obj.getString("user_code");
                            data.agency_id = obj.getInt("agency_id");
                            data.amount = obj.getString("amount");
                            data.pension = obj.getString("pension");
                            data.packet = obj.getString("packet");
                            data.point = obj.getString("point");
                            data.mobile = obj.getString("mobile");
                            data.group_id = obj.getString("group_id");
                            data.login_sign = obj.getString("login_sign");
                            data.avatar = obj.getString("avatar");
                            data.real_name = obj.getString("real_name");
                            data.company_id = obj.getString("company_id");
                            data.birthday = obj.getString("birthday");
                            data.group_name = obj.getString("group_name");
                            data.sex = obj.getString("sex");

                            System.out.println("login_sign=====================" + data.login_sign);
                            spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
                            editor = spPreferences.edit();
                            editor.putString("login_sign", data.login_sign);
                            editor.putString("user_code", data.user_code);
                            editor.putString("avatar", data.avatar);
                            editor.putString("mobile", data.mobile);
                            editor.putString("group_id", data.group_id);
                            editor.putString("point", data.point);
                            editor.putString("company_id", data.company_id);
                            editor.putString("real_name", data.real_name);
                            editor.putString("birthday", data.birthday);
                            editor.putString("sex", data.sex);
                            editor.putString("datetime", datetime);
                            editor.putString("group_name", data.group_name);
                            editor.commit();
                        } else {
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

                ;

                @Override
                public void onFailure(Throwable arg0, String arg1) {

                    super.onFailure(arg0, arg1);
                    NewDataToast.makeText(MainFragment.this, "连接超时", false, 0).show();
                }
            }, MainFragment.this);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // 获取当前程序的版本信息
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {

            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    private int ischecked = 1;

    private void parseShopCart(String st) {
        try {
            JSONObject jsonObject = new JSONObject(st);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                ShopCartData data = new ShopCartData();
                data.orderid = object.getInt("ProductOrderItemId");
                data.wareid = object.getInt("productItemId");
                data.warename = object.getString("proName");
                data.number = object.getInt("productCount");
                data.retailprice = object.getString("oneProductPrice");
                data.marketprice = object.getString("marketPrice");
                data.stylenameone = object.getString("sellPropertyName1");
                data.stylenatureone = object.getString("sellPropertyValue1");
                data.stylenametwo = object.getString("sellPropertyName2");
                data.stylenaturetwo = object.getString("sellPropertyValue2");
                String abe = object.getString("AvailableIntegral");
                if (abe.length() == 0) {
                    data.jf = 0;
                } else {
                    data.jf = (int) Double.parseDouble(object.getString("AvailableIntegral"));
                }
                // data.jf = 2;
                data.imgurl = object.getString("proFaceImg");

                if (!"".equals(data.stylenameone) && !"".equals(data.stylenametwo)) { // 当商品有两种属性时
                    data.setWareid(data.wareid);
                    data.setImgurl(data.imgurl);
                    data.setWarename(data.warename);
                    data.setRetailprice(data.retailprice);
                    data.setMarketprice(data.marketprice);
                    data.setStylenameone(data.stylenameone);
                    data.setStylenatureone(data.stylenatureone);
                    data.setStylenametwo(data.stylenametwo);
                    data.setStylenaturetwo(data.stylenaturetwo);
                    data.setNumber(data.number);
                    data.setIschecked(ischecked);
                    data.setOrderid(data.orderid);
                    data.setJf(data.jf);
                    wareDao = new WareDao(getApplicationContext());
                    wareDao.insertShopCartTwoStyle(data);
                } else if (!"".equals(data.stylenameone)
                        && "".equals(data.stylenametwo)) { // 当时商品只有一种属性时
                    data.setWareid(data.wareid);
                    data.setImgurl(data.imgurl);
                    data.setWarename(data.warename);
                    data.setRetailprice(data.retailprice);
                    data.setMarketprice(data.marketprice);
                    data.setStylenameone(data.stylenameone);
                    data.setStylenatureone(data.stylenatureone);
                    data.setNumber(data.number);
                    data.setIschecked(ischecked);
                    data.setOrderid(data.orderid);
                    data.setJf(data.jf);
                    wareDao.insertShopCartTwoStyle(data);
                } else { // 当商品没有属性时
                    data.setWareid(data.wareid);
                    data.setImgurl(data.imgurl);
                    data.setWarename(data.warename);
                    data.setRetailprice(data.retailprice);
                    data.setMarketprice(data.marketprice);
                    data.setNumber(data.number);
                    data.setIschecked(ischecked);
                    data.setOrderid(data.orderid);
                    data.setJf(data.jf);
                    wareDao = new WareDao(getApplicationContext());
                    wareDao.insertShopCartNoStyle(data);
                }
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    // 解析服务器端的版本信息
    public void xmlparse(String st) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(st.getBytes()));
            NodeList nodeList1 = document.getElementsByTagName("version");
            NodeList nodeList2 = document.getElementsByTagName("url");
            NodeList nodeList3 = document.getElementsByTagName("updateInfo");
            //			System.out.println("nodeList1=================="+nodeList1);
            //			System.out.println("nodeList2=================="+nodeList2);
            //			System.out.println("nodeList3=================="+nodeList3);
            version = nodeList1.item(0).getTextContent();
            url = nodeList2.item(0).getTextContent();
            updatainfo = nodeList3.item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reView() {
        try {

            spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
            user_name = spPreferences.getString("user", "");
            user_id = spPreferences.getString("user_id", "");
            nickname = spPreferences.getString("nickname", "");
            //			String pwdString = spPreferences.getString("pwd", "");

            System.out.println("==================" + user_name);
            System.out.println("===========nickname========" + nickname);
            //			Toast.makeText(MainFragment.this, nickname, 200).show();
            if (!nickname.equals("")) {
                try {
                    //				Toast.makeText(MainFragment.this, "不需要登录", 200).show();
                    //				wareDao = new WareDao(getApplicationContext());
                    //				UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
                    //				yth = registerData.getHengyuCode();
                    //				key = registerData.getUserkey();
                    //				wareDao = new WareDao(getApplicationContext());
                    //				wareDao.deleteAllShopCart(); // 清空购物车里面的信息，以便重新插入
                    c_version = getAppVersionName(this).trim();

                    if (zhuangtai == false) {
                        handler.sendEmptyMessage(-2);//版本更新
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            } else {
                try {
                    try {
                        c_version = getAppVersionName(this).trim();
                        if (zhuangtai == false) {
                            handler.sendEmptyMessage(-2);//版本更新
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static File getFileFromServer(String path, ProgressDialog pd)
            throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(),
                    "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    /*
     * 从服务器中下载APK
     */


    // 程序版本更新
    private void dialog() {
        System.out.println("首页版本==============");
        AlertDialog.Builder builder = new Builder(this);
        //		builder.setMessage(updatainfo);
        builder.setMessage("检查到最新版本，是否要更新！");
        builder.setTitle("提示:新版本");
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                AndPermission.with(MainFragment.this)
                        .permission(Permission.Group.STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                final String filePath = Environment.getExternalStorageDirectory() + "/ss";
                                new UpdateApkThread(URL, filePath, "zams.apk", MainFragment.this).start();
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                new PermissionSetting(MainFragment.this).showSettingStorage(permissions);
                            }
                        }).start();
            }
        });

        builder.setNegativeButton("以后再说",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    Handler handler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case -3:
                    int id = msg.arg1;
                    Intent intent = new Intent(MainFragment.this,
                            WareInformationActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    break;
                case -2:
            /*	Map<String, String> params = new HashMap<String, String>();
                params.put("act", "UserCartInfo");
				params.put("yth", yth);
				params.put("key", key);
				strUrl = RealmName.REALM_NAME + "/mi/getdata.ashx";
				AsyncHttp.post_1(strUrl, params,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						parseShopCart(arg1);
					}
				});*/
                    try {

                        //				AsyncHttp.get(strUrl2, new AsyncHttpResponseHandler() {
                        //					@Override
                        //					public void onSuccess(int arg0, String arg1) {
                        //
                        //						super.onSuccess(arg0, arg1);
                        //						System.out.println("首页版本=============="+arg1);
                        //						xmlparse(arg1);
                        //
                        //						String c_version = getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
                        //						float server_version = Float.parseFloat(version.replaceAll("\\.", ""));
                        //						float client_version = Float.parseFloat(c_version);
                        //						System.out.println("当前:" + client_version + "服务器:"+ server_version);
                        //						if (server_version > client_version) {
                        ////						if (client_version > server_version) {
                        //							Message message = new Message();
                        //							message.what = 0;
                        //							handler.sendMessage(message);
                        //						}
                        //					}
                        //				}, getApplicationContext());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        /**
                         * 版本2
                         */
                        AsyncHttp.get(strUr2, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String arg1) {

                                super.onSuccess(arg0, arg1);
                                System.out.println("首页版本==============" + arg1);
                                try {
                                    JSONObject jsonObject = new JSONObject(arg1);
                                    JSONObject jsob = jsonObject.getJSONObject("data");
                                    String file_version = jsob.getString("file_version");
                                    String link_url = jsob.getString("link_url");
                                    String file_path = jsob.getString("file_path");
                                    String id = jsob.getString("id");
                                    URL = RealmName.REALM_NAME_HTTP + file_path;
                                    System.out.println("首页版本URL==============" + URL);
                                    String c_version = getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
                                    System.out.println("当前--------------" + c_version);
                                    System.out.println("服务器--------------" + file_version);
                                    float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//服务器
                                    float client_version = Float.parseFloat(c_version);//当前
                                    System.out.println("服务器:" + server_version + "/当前:" + client_version);
                                    if (server_version > client_version) {
                                        Message message = new Message();
                                        message.what = 0;
                                        handler.sendMessage(message);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, getApplicationContext());


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                //			case -1:
                //				SoftWarePopuWindow();
                //				break;
                case 0:
                    dialog();
                    break;
                case 2:

                    break;
                case 3:
                    main_bottom_rtns.check(3);
                    break;
                //			case 5:
                //				String text1 = (String) msg.obj;
                //				softshareWxChat(text1);
                //				break;
                //			case 6:
                //				String text2 = (String) msg.obj;
                //				softshareWxFriend(text2);
                //				break;
                case 7:
                    String text = (String) msg.obj;
                    Uri uri = Uri.parse("smsto:");
                    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                    it.putExtra("sms_body", text);
                    startActivity(it);
                    break;
                default:
                    break;
            }
        }

        ;
    };
    private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup arg0, int arg1) {

            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = getFragment(arg1);
            transaction.replace(R.id.main_content, fragment);
            transaction.commit();

        }
    };

    private boolean isEdit = true;

    public Fragment getFragment(int what) {
        Fragment fragment = null;
        switch (what) {
            case R.id.main_bottom_rtn0:
            case 0:
                isEdit = true;
                //			fragment = new HomeActivity(imageLoader,handler, MainFragment.this,key,yth);
                fragment = new HomeActivity();
                main_bottom_rtns.setVisibility(View.VISIBLE);
                break;
            case R.id.main_bottom_rtn1:
            case 1:
                isEdit = true;
                main_bottom_rtns.setVisibility(View.VISIBLE);
                //			fragment = new YiHuaActivity(imageLoader,MainFragment.this);YunshangServiceActivity
                fragment = new YunshangServiceActivity();
                break;
            case R.id.main_bottom_rtn2:
            case 3:
                //Cocos2d
                isEdit = true;
                //			fragment = new MenuActivity();
                fragment = new ZhongAnMSActivity();
                main_bottom_rtns.setVisibility(View.VISIBLE);
                break;
            case R.id.main_bottom_rtn3:
            case 4://购物车
                isEdit = true;
                main_bottom_rtns.setVisibility(View.VISIBLE);
                fragment = new MyShopPingCarActivity();
                break;
            case R.id.main_bottom_rtn4:
            case 5:
                isEdit = true;
                main_bottom_rtns.setVisibility(View.VISIBLE);
                fragment = new IndividualCenterActivity(imageLoader, handler, getApplicationContext());
                //			fragment = new IndividualCenterActivity();
                break;

            default:
                break;
        }
        return fragment;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("返回值:" + requestCode);
        switch (requestCode) {
            case 0:
                main_bottom_rtns.check(0);
                break;
            case 3:
                main_bottom_rtns.check(3);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isEdit) {
                AlertDialog isExit = new AlertDialog.Builder(this).create();
                isExit.setTitle("系统提示");
                isExit.setMessage("确定退出中安民生？");
                isExit.setButton("确定", listener2);
                isExit.setButton2("取消", listener2);
                isExit.show();
            } else {
                main_bottom_rtns.check(0);
            }
        }
        return true;
    }

    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    wareDao.deleteAllShopCart();
                    //				AppManager.getAppManager().finishAllActivity();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
            popupWindowMenu.dismiss(); // 对话框消失
            popupWindowMenu.currentState = 1; // 标记状态，已消失
        } else {
            popupWindowMenu.showAtLocation(findViewById(R.id.layout),
                    Gravity.BOTTOM, 0, 0);
            popupWindowMenu.currentState = 0; // 标记状态，显示中
        }
        return false; // true--显示系统自带菜单；false--不显示。
    }

    private FragmentManager manager;
    private MyPopupWindowMenu popupWindowMenu;


    /**
     * 复制数据库文件
     *
     * @param dbFile
     */
    public void copyHczDB(String dbFile) {
        // new File(dbFile).delete();
        System.out.println(dbFile);
        if (!new File(dbFile).exists()) {// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            InputStream is = getApplicationContext().getResources()
                    .openRawResource(R.raw.hcz); // 欲导入的数据库
            try {
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[40000];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void copyCityDB(String dbFile) {
        // new File(dbFile).delete();
        System.out.println(dbFile);
        if (!new File(dbFile).exists()) {// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            InputStream is = getApplicationContext().getResources()
                    .openRawResource(R.raw.city); // 欲导入的数据库
            try {
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[40000];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }

    /**
     * 复制数据库文件
     *
     * @param dbFile
     */
    public void copyTuanDB(String dbFile) {
        // new File(dbFile).delete();
        System.out.println(dbFile);
        if (!new File(dbFile).exists()) {// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            InputStream is = getApplicationContext().getResources()
                    .openRawResource(R.raw.tuangou); // 欲导入的数据库
            try {
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[40000];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }

    /**
     * 复制数据库文件
     *
     * @param dbFile
     */
    public void copyJDB(String dbFile) {
        // new File(dbFile).delete();
        System.out.println(dbFile);
        if (!new File(dbFile).exists()) {// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            InputStream is = getApplicationContext().getResources()
                    .openRawResource(R.raw.jdpiao); // 欲导入的数据库
            try {
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[40000];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }

    public void copyWorkDB(String dbFile) {
        // new File(dbFile).delete();
        if (!new File(dbFile).exists()) {// 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
            InputStream is = getApplicationContext().getResources()
                    .openRawResource(R.raw.work); // 欲导入的数据库
            try {
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[40000];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private ImageButton btn_wechat;
    private ImageButton btn_wx_friend;
    private ImageButton btn_sms;
    private LayoutInflater inflater;
    private View view;
    private PopupWindow pop;

    private void con(final int index, int type) {
        WareDao wareDao = new WareDao(getApplicationContext());
        UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
        String yth = registerData.getHengyuCode();
        if (yth.length() == 0) {
            NewDataToast.makeText(getApplicationContext(), "请先登录", false, 0).show();
        } else {
            Map<String, String> param0 = new HashMap<String, String>();
            param0.put("act", "GetDownloadAPK_URL");
            param0.put("yth", "");
            param0.put("reqType", "" + type);
            AsyncHttp.post_1(
                    RealmName.REALM_NAME + "/mi/getdata.ashx"
                    , param0, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int arg0, String arg1) {
                            super.onSuccess(arg0, arg1);
                            try {
                                JSONObject jsonObject = new JSONObject(arg1);
                                Message msg = new Message();
                                msg.what = index;
                                msg.obj = jsonObject.getString("msg");
                                handler.sendMessage(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WsManager.getInstance().disconnect();
    }
}
