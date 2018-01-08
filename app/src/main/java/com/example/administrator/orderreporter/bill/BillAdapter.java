package com.example.administrator.orderreporter.bill;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.socket.BaseRecycleAdapter;
import com.example.administrator.orderreporter.bill.bean.Bill;
import com.example.administrator.orderreporter.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/8.
 */

public class BillAdapter extends BaseRecycleAdapter<Bill,BillAdapter.ViewHolder>{

    private ArrayList<Bill> bills;

    public BillAdapter(ArrayList<Bill> list) {
        super(list);
        this.bills = list;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_bill_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BillAdapter.ViewHolder viewHolder = new BillAdapter.ViewHolder(getView(parent, viewType));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final Bill bill = getItem(position);
        holder.item_bill_list_price_tv.setText("¥"+bill.getPayMoney());
        holder.item_bill_list_time_tv.setText(bill.getCreateTime());
        if(bill.getPlatform().equals("0")){
            holder.item_bill_list_type_tv.setText("微信支付");
        }else{
            holder.item_bill_list_type_tv.setText("支付宝支付");
        }

//        holder.goods_item_linear.setOnClickListener(new OnSingleClickListener() {
//            @Override
//            public void onSingleClick(View view) {
//                Intent intent_goods = new Intent(view.getContext(),GoodsActivity.class);
//                intent_goods.putExtra(IntentStates.CARINFO,carInfo);
//                LogUtils.Debug_E(MallAdapter.class,carInfo.getPuoductNo()+"");
//                view.getContext().startActivity(intent_goods);
//            }
//        });
//        ImgUtils.imageLoader(holder.goods_item_img, CarfriendNet.IMG_URL+carInfo.getImgUrl());
//        LogUtils.Debug_E(MallAdapter.class, CarfriendNet.IMG_URL+carInfo.getImgUrl());
//        holder.goods_item_img.setScaleType( ImageView.ScaleType.FIT_XY);
//        holder.goods_item_name.setText(carInfo.getProductName());
//        holder.goods_item_price.setText("指导价¥"+carInfo.getUnitPrice());
//        holder.goods_item_price_d.setText("首付价¥"+carInfo.getDownPayment());
    }


    class ViewHolder extends BaseRecycleAdapter.ViewHolder {

        TextView item_bill_list_type_tv;
        TextView item_bill_list_time_tv;
        TextView item_bill_list_price_tv;


        public ViewHolder(final View itemView) {
            super(itemView);
            item_bill_list_type_tv = (TextView) itemView.findViewById(R.id.item_bill_list_type_tv);
            item_bill_list_time_tv = (TextView) itemView.findViewById(R.id.item_bill_list_time_tv);
            item_bill_list_price_tv = (TextView) itemView.findViewById(R.id.item_bill_list_price_tv);
        }
    }
}
