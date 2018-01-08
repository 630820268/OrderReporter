package com.example.administrator.orderreporter.bill;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.orderreporter.R;
import com.example.administrator.orderreporter.base.BaseActivity;
import com.example.administrator.orderreporter.base.view.RVScollListener;
import com.example.administrator.orderreporter.bill.bean.Bill;
import com.example.administrator.orderreporter.bill.bean.QueryBean;
import com.example.administrator.orderreporter.bill.presenter.IQueryContract;
import com.example.administrator.orderreporter.bill.presenter.QueryPresenter;
import com.example.administrator.orderreporter.utils.DateUtils;
import com.example.administrator.orderreporter.utils.LogUtils;
import com.example.administrator.orderreporter.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class QueryActivity extends BaseActivity implements View.OnClickListener,IQueryContract.QueryView{

    private RecyclerView query_rv;
    private LinearLayout query_start_linear,query_end_linear;
    private TextView query_start_tv,query_end_tv,query_total_tv;
    private BillAdapter queryAdapter;
    private ArrayList<Bill> queryList;

    private final int START = 1;
    private final int END = 2;
    private QueryPresenter queryPresenter;
    private SwipeRefreshLayout query_swip;
    private int pageNum = 1;
    private ArrayList<Bill> pageList;
    private TextView query_no_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        setTitle("查看流水", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        },null);
        setPresenter();
        initView();
        queryPresenter.get_search(String.valueOf(pageNum),query_start_tv.getText().toString(),query_end_tv.getText().toString());
    }

    private void initView(){
        queryList = new ArrayList<>();
        pageList = new ArrayList<>();
        query_rv = (RecyclerView) findViewById(R.id.query_rv);
        query_start_linear = (LinearLayout) findViewById(R.id.query_start_linear);
        query_end_linear = (LinearLayout) findViewById(R.id.query_end_linear);
        query_start_tv = (TextView) findViewById(R.id.query_start_tv);
        query_end_tv = (TextView) findViewById(R.id.query_end_tv);
        query_total_tv = (TextView) findViewById(R.id.query_total_tv);
        query_no_tv = (TextView) findViewById(R.id.query_no_tv);

        query_swip = (SwipeRefreshLayout) findViewById(R.id.query_swip);

        queryAdapter = new BillAdapter(queryList);
        LinearLayoutManager query_manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        query_rv.setLayoutManager(query_manager);
        query_rv.setAdapter(queryAdapter);
        query_rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        query_swip.setColorSchemeColors(Color.RED, Color.BLUE);
        query_swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                queryList.clear();
                pageNum = 1;
                queryPresenter.get_search(String.valueOf(pageNum),query_start_tv.getText().toString(),query_end_tv.getText().toString());

            }
        });



        query_rv.addOnScrollListener(new RVScollListener(10, 2) {
            @Override
            public void onBottom() {
                super.onBottom();
                LinearLayoutManager layoutManager = (LinearLayoutManager) query_rv.getLayoutManager();

                // int totalItemCount = layoutManager.getItemCount();
                LogUtils.Debug_E(BillActivity.class,layoutManager.getItemCount()+",");
                if (layoutManager.getItemCount() % 10 == 0) {
                    // rcv_foot_linear.setVisibility(View.VISIBLE);

                    pageNum++;
                    queryPresenter.get_search(String.valueOf(pageNum),query_start_tv.getText().toString(),query_end_tv.getText().toString());
                }else{
                    ToastUtil.showToast(QueryActivity.this,"没有数据了");
//                    Toast.makeText(QueryActivity.this,"没有数据了",Toast.LENGTH_SHORT).show();
                }
                if(pageList.size() == 0){
                    ToastUtil.showToast(QueryActivity.this,"没有数据了");
                }
            }
        });

        query_start_linear.setOnClickListener(this);
        query_end_linear.setOnClickListener(this);
        query_end_tv.setText(DateUtils.getDate());
        String begin = DateUtils.getMonth()+"-01";
        query_start_tv.setText(begin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.query_start_linear:
                showDatePickDlg(START);
                break;
            case R.id.query_end_linear:
                showDatePickDlg(END);
                break;
        }
    }

    protected void showDatePickDlg(final int time) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(QueryActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(time == START){
                    String start = year+"-"+monthOfYear+"-"+dayOfMonth;
                    int date_start = Integer.parseInt(start.replaceAll("-",""));
                    int date_end = Integer.parseInt(query_end_tv.getText().toString().replaceAll("-",""));
                    if(date_start<date_end){
                        query_start_tv.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }else{
                        Toast.makeText(QueryActivity.this,"初始日期错啦",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    String end = year+"-"+monthOfYear+"-"+dayOfMonth;
                    int date_end = Integer.parseInt(end.replaceAll("-",""));
                    int date_start = Integer.parseInt(query_start_tv.getText().toString().replaceAll("-",""));
                    if(date_end<date_start){
                        Toast.makeText(QueryActivity.this,"结束日期错啦",Toast.LENGTH_SHORT).show();
                    }else{
                        query_end_tv.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                }
                queryList.clear();
                queryPresenter.get_search(String.valueOf(pageNum),query_start_tv.getText().toString(),query_end_tv.getText().toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void setPresenter() {
        queryPresenter = new QueryPresenter(this);
    }

    @Override
    public void showSearch(QueryBean queryBean) {
        pageList.clear();
        if(queryBean.getList().size()==0){
            query_no_tv.setVisibility(View.VISIBLE);
            query_rv.setVisibility(View.GONE);
            query_total_tv.setText("¥"+queryBean.getTotalMoney());
            query_swip.setRefreshing(false);
        }else{
            query_no_tv.setVisibility(View.GONE);
            query_rv.setVisibility(View.VISIBLE);
            pageList.addAll(queryBean.getList());
            query_total_tv.setText("¥"+queryBean.getTotalMoney());
            queryList.addAll(queryBean.getList());
            queryAdapter.notifyDataSetChanged();
            query_swip.setRefreshing(false);
        }

        LogUtils.Debug_E(QueryActivity.class,queryBean.getList()+"");
    }
}
