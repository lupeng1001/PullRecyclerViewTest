package com.example.double2.pullrecyclerviewtest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyRecycleView.loadMoreListener{
    private static final String TAG = "MainActivity";
    private MyRecycleView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;

    private List<String> mData = new ArrayList<String>();
    //设置单页数据量
    private static final int PAGESIZE = 10;
    private int datapage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //加载数据
        loadDataByPage(datapage);



    }

    /**
     * 根据页码来获取本地数据
     */
    public void loadDataByPage(int local_page){
        if(null!=mData
                &&mData.size()>0){
            mData.clear();
        }
        if(local_page<5){
            Log.e(TAG,"local_page<5 local_page="+local_page);
            for(int i=0;i<PAGESIZE;i++){
                mData.add("item_"+i);
            }
        }else{
            Log.e(TAG,"local_page>=5 local_page="+local_page);
            for(int i=0;i<5;i++){
                mData.add("item_"+i);
            }
        }
        if(mData.size()<PAGESIZE){
            mRecyclerAdapter.setIs_load_finish(true);
            mRecyclerAdapter.addDataList(mData);
        }else{
            mRecyclerAdapter.setIs_load_finish(false);
            mRecyclerAdapter.addDataList(mData);
            datapage++;
        }
    }

    /**
     * 判断recycleview是否滚动到了最底部
     * @param recyclerView
     * @return
     */
    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    private void initView() {
        mRecyclerView = (MyRecycleView) findViewById(R.id.rc_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setLoad_listener(this);
        mRecyclerAdapter = new RecyclerAdapter(MainActivity.this);
        mRecyclerAdapter.setFootViewText("加载中。。。");
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!mRecyclerAdapter.is_load_finish()){
                    loadDataByPage(datapage);
                }
            }
        },2000);
    }
}
