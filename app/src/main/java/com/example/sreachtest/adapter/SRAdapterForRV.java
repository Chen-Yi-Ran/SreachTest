package com.example.sreachtest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sreachtest.R;

import java.util.List;

import com.example.sreachtest.bean.SearchRecord;

/*
搜索记录适配器
 */
public class SRAdapterForRV extends RecyclerView.Adapter<SRAdapterForRV.ViewHolder> {

    private List<SearchRecord> mSearchRecords;
    private OnItemClickListener listener;
    //静态内部类用于缓存子项控件实例
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView srContent;
        //静态内部类的构造函数，参数view通常就是RecyclerView子项的最外层布局
        public ViewHolder (View view){
            super(view);
            srContent = (TextView)view.findViewById(R.id.search_record_item_tv);
        }
    }

    //构造函数
    public SRAdapterForRV(List<SearchRecord> searchRecords){
        mSearchRecords = searchRecords;
    }

    //用于告诉RecyclerView一共有多少子项，直接返回数据源的长度即可
    @Override
    public int getItemCount() {
        return mSearchRecords.size();
    }

    //用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行。
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SearchRecord searchRecord = mSearchRecords.get(position);
        holder.srContent.setText(searchRecord.getContent());//给历史记录的视图赋为我们搜索的
        if (listener != null) {
            holder.srContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(searchRecord);//当点击历史记录时跳转到对应的那个部分的图
                }
            });
        }
    }

    //用于创建ViewHolder实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_record_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //点击事件
    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(SearchRecord searchRecord);
    }

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
