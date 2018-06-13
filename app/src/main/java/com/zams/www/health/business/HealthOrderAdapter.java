package com.zams.www.health.business;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.Constant;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.MyOrderActivity;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.demo.my.TishiCarArchivesActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zams.www.R;
import com.zams.www.health.NoEvaluatedActivity;
import com.zams.www.health.model.HealthOrder;
import com.zams.www.weiget.CommonAdaper;
import com.zams.www.weiget.ViewHolder;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/6/8.
 */

public class HealthOrderAdapter extends CommonAdaper<HealthOrder> {
    private Activity act;
    private Handler mHandler;

    public HealthOrderAdapter(Handler handler, Activity context, ArrayList<HealthOrder> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
        this.act = context;
        mHandler = handler;
    }

    @Override
    public void convert(ViewHolder holder, final HealthOrder item) {
        int paymentStatus = item.getPayment_status();
        int status = item.getStatusX();
        int expressStatus = item.getExpress_status();
        holder.setText(R.id.tv_company_name, item.getCompany_name());

        String expressFee = String.format("%1$.2f", item.getExpress_fee());
        holder.setText(R.id.tv_yunfei, "含运费（ ¥" + expressFee + "）");
        String payAmount = String.format("¥%1$.2f", item.getPayable_amount());
        holder.setText(R.id.tv_heji, payAmount);


        View ll_anliu = holder.getView(R.id.ll_anliu);
        TextView tv_kukuang = holder.getView(R.id.tv_kukuang);
        TextView tv_pingjia = holder.getView(R.id.tv_pingjia);
        TextView tv_queren_fukuan = holder.getView(R.id.tv_queren_fukuan);
        View tv_tuikuan = holder.getView(R.id.tv_tuikuan);
        TextView tv_zhuangtai = holder.getView(R.id.tv_zhuangtai);
        TextView tv_shanche = holder.getView(R.id.tv_shanche);
        if (paymentStatus == 1) {
            tv_zhuangtai.setText("等待付款");
            ll_anliu.setVisibility(View.VISIBLE);
            tv_kukuang.setVisibility(View.VISIBLE);
            tv_pingjia.setVisibility(View.GONE);
            tv_queren_fukuan.setVisibility(View.GONE);
            tv_tuikuan.setVisibility(View.GONE);
            tv_shanche.setVisibility(View.VISIBLE);
            tv_kukuang.setText("去付款");
        } else if (status == 4) {
            tv_zhuangtai.setText("已退款");
            ll_anliu.setVisibility(View.VISIBLE);
            tv_tuikuan.setVisibility(View.GONE);
            tv_kukuang.setVisibility(View.GONE);
            tv_pingjia.setVisibility(View.GONE);
            tv_shanche.setVisibility(View.VISIBLE);
        } else if (paymentStatus == 2 && expressStatus == 1 && status == 2) {
            tv_zhuangtai.setText("已付款");
            ll_anliu.setVisibility(View.VISIBLE);
            tv_kukuang.setVisibility(View.GONE);
            tv_pingjia.setVisibility(View.GONE);
            tv_tuikuan.setVisibility(View.VISIBLE);
            tv_shanche.setVisibility(View.GONE);
        } else if (paymentStatus == 2 && expressStatus == 2 && status == 2) {
            tv_zhuangtai.setText("已发货");
            ll_anliu.setVisibility(View.VISIBLE);
            tv_kukuang.setVisibility(View.GONE);
            tv_pingjia.setVisibility(View.GONE);
            tv_queren_fukuan.setVisibility(View.VISIBLE);
            tv_tuikuan.setVisibility(View.GONE);
            tv_shanche.setVisibility(View.GONE);
            tv_queren_fukuan.setText("确认收货");
        } else if (paymentStatus == 2 && expressStatus == 2 && status == 3) {
            tv_zhuangtai.setText("交易完成");
            ll_anliu.setVisibility(View.VISIBLE);
            tv_queren_fukuan.setVisibility(View.GONE);
            tv_kukuang.setVisibility(View.GONE);
            tv_pingjia.setVisibility(View.VISIBLE);
            tv_tuikuan.setVisibility(View.GONE);
            tv_shanche.setVisibility(View.GONE);
            tv_pingjia.setText("评价");
        }
        //删除
        tv_shanche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = Constant.DELETE;
                message.obj = item.getOrder_no();
                mHandler.sendMessage(message);
            }
        });
        tv_kukuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyOrderZFActivity.class);
                String orderNo = item.getOrder_no();
                intent.putExtra("order_no", orderNo);
                intent.putExtra("order_type", "1");
                intent.putExtra("total_c", "" + item.getPayable_amount());
                context.startActivity(intent);

            }
        });

        //完成订单
        tv_queren_fukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act, TishiCarArchivesActivity.class);
                intent.putExtra("order_no", item.getOrder_no());
                intent.putExtra("order_type", 2);
                intent.putExtra("title", "title");
                if (!act.isDestroyed()) {
                    act.startActivityForResult(intent, Constant.REQUEST_COMPLETE_ORDER);
                }
            }
        });
        /**
         * 评价
         */
        tv_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoEvaluatedActivity.class);
                intent.putExtra(Constant.ORDER_NO, item.getOrder_no());
                context.startActivity(intent);
            }
        });

        /**
         * 申请退款
         */
        tv_tuikuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String order_no = item.getOrder_no();
                    Message msg = new Message();
                    msg.what = Constant.REFUND;
                    msg.obj = order_no;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
