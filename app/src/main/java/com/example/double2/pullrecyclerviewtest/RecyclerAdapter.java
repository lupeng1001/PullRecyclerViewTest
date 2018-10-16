package com.example.double2.pullrecyclerviewtest;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：RecyclerViewTest
 * 创建人：Double2号
 * 创建时间：2016/4/18 8:12
 * 修改备注：
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private loadMoreListener load_listener;
    private Context mContext;
    private List<String> mData = new ArrayList<String>();//数据
    private Boolean isFootView = false;//是否添加了FootView
    private String footViewText = "";//FootView的内容

    //是否加载完毕
    private boolean is_load_finish;

    //两个final int类型表示ViewType的两种类型
    private final int NORMAL_TYPE = 0;
    private final int FOOT_TYPE = 1111;

    public boolean is_load_finish() {
        return is_load_finish;
    }

    public void setIs_load_finish(boolean is_load_finish) {
        this.is_load_finish = is_load_finish;
    }

    public loadMoreListener getLoad_listener() {
        return load_listener;
    }

    public void setLoad_listener(loadMoreListener load_listener) {
        this.load_listener = load_listener;
    }

    public List<String> getmData() {
        return mData;
    }

    public void setmData(List<String> mData) {
        this.mData = mData;
    }

    public void addDataList(List<String> data_list){
        if(null!=data_list
                &&data_list.size()>0){
            this.mData.addAll(data_list);
            notifyDataSetChanged();
        }
    }



    public RecyclerAdapter(Context context) {
        this.mContext = context;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvViewHolder;
        public LinearLayout llViewHolder;

        public TextView tvFootView;//footView的TextView属于独自的一个layout

        //初始化viewHolder，此处绑定后在onBindViewHolder中可以直接使用
        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == NORMAL_TYPE) {
                tvViewHolder = (TextView) itemView.findViewById(R.id.tv_view_holder);
                llViewHolder = (LinearLayout) itemView;
            } else if (viewType == FOOT_TYPE) {
                tvFootView = (TextView) itemView;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View normal_views = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.rc_item, parent, false);
        View foot_view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.foot_view, parent, false);
        if (viewType == FOOT_TYPE)
            return new ViewHolder(foot_view, FOOT_TYPE);
        return new ViewHolder(normal_views, NORMAL_TYPE);
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1 == getItemCount()) {
            return FOOT_TYPE;
        }
        return NORMAL_TYPE;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //建立起ViewHolder中试图与数据的关联
        Log.d("xjj", getItemViewType(position) + "");
        //如果footview存在，并且当前位置ViewType是FOOT_TYPE
        if (isFootView && (getItemViewType(position) == FOOT_TYPE)) {
            if(!is_load_finish()){
                holder.tvFootView.setText(footViewText);
            }else{
                holder.tvFootView.setText("-----我是有底线的-----");
            }

        } else {
            holder.tvViewHolder.setText(mData.get(position)+"");
        }
    }


    public interface loadMoreListener{
        public void onLoadMore();
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() + 1 : 0;
    }

    //创建一个方法来设置footView中的文字
    public void setFootViewText(String footViewText) {
        isFootView = true;
        this.footViewText = footViewText;
    }

}
