package com.zams.www.health.business;

import java.util.List;

import com.android.hengyu.web.RealmName;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import com.zams.www.R;
import com.zams.www.health.DescriptionActivity;
import com.zams.www.health.model.AddHealthOrderBean;
import com.zams.www.health.request.HttpCallBack;
import com.zams.www.health.request.HttpProxy;
import com.zams.www.weiget.YSBaseAdapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;

public class HallListAdapter extends YSBaseAdapter<MedicalItems> {
    private static final String TAG = "HallListAdapter";
    private ImageView hallImg;
    private TextView hallTitleTv;
    private TextView hallMoneyTv;
    private ImageView shopIv;
    private String mUserId;
    private String mCompanyId;

    public HallListAdapter(Context context, String userId, String companyId, List<MedicalItems> list, int layout) {
        super(context, list, layout);
        this.mUserId = userId;
        this.mCompanyId = companyId;

    }

    @Override
    public void convert(YSBaseAdapter.ViewHolder holder, final MedicalItems data) {
        hallImg = holder.findViewById(R.id.hall_item_img);
        hallTitleTv = holder.findViewById(R.id.hall_title_tv);
        hallMoneyTv = holder.findViewById(R.id.hall_money_tv);
        shopIv = holder.findViewById(R.id.shop_iv);
        hallTitleTv.setText(data.getMedical_name());
        hallMoneyTv.setText(data.getMedical_price() + "元或" + data.getPoints()
                + "积分");
        Glide.with(mContext)
                .load(data.getImg_url())
                .placeholder(R.drawable.sj_fw)//图片加载出来前，显示的图片
                .error(R.drawable.sj_fw)//图片加载失败后，显示的图片
                .into(hallImg);
        shopIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar(data.getId());
            }
        });

    }


    public void upData(List<MedicalItems> datas) {
        mList.clear();
        if (datas != null) {
            mList.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    private void addCar(int id) {
        String url = RealmName.REALM_NAME + "/tools/mobile_ajax.asmx/submit_medical_orderdetails";
        RequestParams params = new RequestParams();
        params.put("tjitem", id + "_1");
        params.put("jine", "1");
        params.put("payment_id", "5");
        params.put("user_id", mUserId);
        params.put("company_id", "" + mCompanyId);
        HttpProxy.addCarRequest(mContext, url, params, new HttpCallBack<AddHealthOrderBean>() {
            @Override
            public void onSuccess(AddHealthOrderBean responseData) {
                Toast.makeText(mContext, "已成功添加到订单中", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Connection.Request request, String e) {
                Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
