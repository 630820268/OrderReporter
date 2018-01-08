package com.example.administrator.orderreporter.bill;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.BaseActivity;
import com.example.administrator.orderreporter.base.view.RVScollListener;
import com.example.administrator.orderreporter.bill.bean.Bill;
import com.example.administrator.orderreporter.bill.bean.BillBean;
import com.example.administrator.orderreporter.bill.presenter.BillPresenter;
import com.example.administrator.orderreporter.bill.presenter.IBillContract;
import com.example.administrator.orderreporter.utils.LogUtils;
import com.example.administrator.orderreporter.utils.ToastUtil;

import java.util.ArrayList;

public class BillActivity extends BaseActivity implements View.OnClickListener,IBillContract.BillView{

    private RecyclerView bill_today_rv,bill_yesterday_rv;

    private BillAdapter billTodayAdapter,billYesterdayAdapter;
    private TextView bill_query_tv,bill_none_tv,bill_total_tv;
    private LinearLayout bill_today_linear,bill_yesterday_linear;
    private RelativeLayout bill_query_rela;
    private BillPresenter billPresenter;
    private ArrayList<Bill> billTodayList,billYesterdayList;
    private SwipeRefreshLayout bill_swip;
    private int todayPage = 1,yesterdayPage = 1;
    private int position = 0;
    private ArrayList<Bill> todayPageList,yesterdayPageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        setTitle("账单", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        },null);
        setPresenter();
        initView();
    }

    private void initView(){
        billTodayList = new ArrayList<>();
        billYesterdayList = new ArrayList<>();
        todayPageList = new ArrayList<>();
        yesterdayPageList = new ArrayList<>();

        bill_today_rv = (RecyclerView) findViewById(R.id.bill_today_rv);
        bill_yesterday_rv = (RecyclerView) findViewById(R.id.bill_yesterday_rv);
        bill_query_tv = (TextView) findViewById(R.id.bill_query_tv);
        bill_none_tv = (TextView) findViewById(R.id.bill_none_tv);
        bill_query_rela = (RelativeLayout) findViewById(R.id.bill_query_rela);
        bill_today_linear = (LinearLayout) findViewById(R.id.bill_today_linear);
        bill_yesterday_linear = (LinearLayout) findViewById(R.id.bill_yesterday_linear);
        bill_swip = (SwipeRefreshLayout) findViewById(R.id.bill_swip);
        bill_total_tv = (TextView) findViewById(R.id.bill_total_tv);

        bill_swip.setColorSchemeColors(Color.RED, Color.BLUE);
        bill_swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(position == 0){
                    billTodayList.clear();
                    billYesterdayList.clear();
                    todayPage = 1;
                    billPresenter.get_list(String.valueOf(todayPage));

                }else{
                    billTodayList.clear();
                    billYesterdayList.clear();
                    yesterdayPage = 1;
                    billPresenter.get_list(String.valueOf(yesterdayPage));
                }
            }
        });

        billTodayAdapter = new BillAdapter(billTodayList);
        LinearLayoutManager today_manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        bill_today_rv.setLayoutManager(today_manager);
        bill_today_rv.setAdapter(billTodayAdapter);
        bill_today_rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        billYesterdayAdapter = new BillAdapter(billYesterdayList);
        LinearLayoutManager yesterday_manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        bill_yesterday_rv.setLayoutManager(yesterday_manager);
        bill_yesterday_rv.setAdapter(billYesterdayAdapter);
        bill_yesterday_rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        bill_query_rela.setOnClickListener(this);
        billPresenter.get_list("1");

        bill_today_linear.setOnClickListener(this);
        bill_yesterday_linear.setOnClickListener(this);


        bill_today_rv.addOnScrollListener(new RVScollListener(10, 2) {
            @Override
            public void onBottom() {
                super.onBottom();
                LinearLayoutManager layoutManager = (LinearLayoutManager) bill_today_rv.getLayoutManager();

                // int totalItemCount = layoutManager.getItemCount();
                LogUtils.Debug_E(BillActivity.class,layoutManager.getItemCount()+",");
                if (layoutManager.getItemCount() % 10 == 0) {
                    // rcv_foot_linear.setVisibility(View.VISIBLE);
                    LogUtils.Debug_E(BillActivity.class,"zoule");
                    todayPage++;
                    billPresenter.get_list(String.valueOf(todayPage));
                }else{
                    ToastUtil.showToast(BillActivity.this,"没有数据了");
                    //Toast.makeText(BillActivity.this,"没有数据了",Toast.LENGTH_SHORT).show();
                }
                if(todayPageList.size() == 0){
                    ToastUtil.showToast(BillActivity.this,"没有数据了");
                }
            }
        });

        bill_yesterday_rv.addOnScrollListener(new RVScollListener(10, 2) {
            @Override
            public void onBottom() {
                super.onBottom();
                LinearLayoutManager layoutManager = (LinearLayoutManager) bill_yesterday_rv.getLayoutManager();

                // int totalItemCount = layoutManager.getItemCount();
                LogUtils.Debug_E(BillActivity.class,layoutManager.getItemCount()+",");
                if (layoutManager.getItemCount() % 10 == 0) {
                    // rcv_foot_linear.setVisibility(View.VISIBLE);
                    LogUtils.Debug_E(BillActivity.class,"zoule");
                    yesterdayPage++;
                    billPresenter.get_list(String.valueOf(yesterdayPage));
                }else{
                    ToastUtil.showToast(BillActivity.this,"没有数据了");
                   // Toast.makeText(BillActivity.this,"没有数据了",Toast.LENGTH_SHORT).show();
                }
                if(yesterdayPageList.size() == 0){
                    ToastUtil.showToast(BillActivity.this,"没有数据了");
                }
            }
        });

        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bill_query_rela:
                Intent intent_query = new Intent(BillActivity.this,QueryActivity.class);
                startActivity(intent_query);
                break;
            case R.id.bill_today_linear:
                position = 0;
                changeUI(position);
                bill_swip.setVisibility(View.VISIBLE);
                if(billTodayList.size()==0){
                    bill_today_rv.setVisibility(View.GONE);
                    bill_yesterday_rv.setVisibility(View.GONE);
                    bill_none_tv.setVisibility(View.VISIBLE);
                }else{
                    bill_today_rv.setVisibility(View.VISIBLE);
                    bill_yesterday_rv.setVisibility(View.GONE);
                    bill_none_tv.setVisibility(View.GONE);
                }
                break;
            case R.id.bill_yesterday_linear:
                position = 1;
                changeUI(position);
                LogUtils.Debug_E(BillActivity.class,billYesterdayList+"");
                bill_swip.setVisibility(View.VISIBLE);
                if(billYesterdayList.size()==0){
                    bill_today_rv.setVisibility(View.GONE);
                    bill_yesterday_rv.setVisibility(View.GONE);
                    bill_none_tv.setVisibility(View.VISIBLE);
                }else{
                    bill_today_rv.setVisibility(View.GONE);
                    bill_yesterday_rv.setVisibility(View.VISIBLE);
                    bill_none_tv.setVisibility(View.GONE);
                }
                break;
        }
    }


    public void changeUI(int position){
        if(position == 0){
            bill_today_linear.setBackgroundColor(getResources().getColor(R.color.pay_gather));
            bill_yesterday_linear.setBackgroundColor(getResources().getColor(R.color.bill_yesterday));
        }else{
            bill_today_linear.setBackgroundColor(getResources().getColor(R.color.bill_yesterday));
            bill_yesterday_linear.setBackgroundColor(getResources().getColor(R.color.pay_gather));
        }
    }

    @Override
    public void setPresenter() {
        billPresenter = new BillPresenter(this);
    }

    @Override
    public void show_list(BillBean billBean) {
        LogUtils.Debug_E(BillActivity.class,billBean.getTotalMoney()+","+billBean.getYesList());
        todayPageList.clear();
        yesterdayPageList.clear();
        todayPageList.addAll(billBean.getTodayList());
        yesterdayPageList.addAll(billBean.getYesList());

        bill_total_tv.setText("¥"+billBean.getTotalMoney().toString());
        billTodayList.addAll(billBean.getTodayList());
        billYesterdayList.addAll(billBean.getYesList());
        billTodayAdapter.notifyDataSetChanged();
        billYesterdayAdapter.notifyDataSetChanged();
        bill_swip.setRefreshing(false);
    }
}
