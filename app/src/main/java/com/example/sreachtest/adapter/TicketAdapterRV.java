package com.example.sreachtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sreachtest.R;

import java.util.ArrayList;
import java.util.List;

import com.example.sreachtest.bean.Ticket;

/*
姓名和图片列表适配器
 */
public class TicketAdapterRV extends RecyclerView.Adapter<TicketAdapterRV.ViewHolder> {

    private List<Ticket> mTickets;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{

        private Ticket mTicket;
        private ImageView imageView;
        private TextView textView;
        public ViewHolder (View view){
            super(view);
            imageView=(ImageView) view.findViewById(R.id.item_image);
            textView=(TextView) view.findViewById(R.id.item_name);
        }

        public void bindTicket(Ticket ticket){
            mTicket = ticket;
            int img = ticket.getImg();
            String name = ticket.getName();
            textView.setText(name);
            imageView.setImageResource(img);
           /* destinationStationTV.setText(ticket.getDestinationStation());
            busTypeTV.setText(ticket.getBusType());
            ticketPriceTV.setText(ticketPrice);
            ticketNumTV.setText(ticketNum);*/
        }
    }

    //构造函数
    public TicketAdapterRV(List<Ticket> tickets,Context context){
        mTickets = new ArrayList<>();
        mTickets.addAll(tickets);
        mContext = context;
    }

    //用于告诉RecyclerView一共有多少子项，直接返回数据源的长度即可
    @Override
    public int getItemCount() {
        return mTickets.size();
    }

    //用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行。
    @Override
    public void onBindViewHolder(TicketAdapterRV.ViewHolder holder, int position) {
        final Ticket ticket = mTickets.get(position);
        holder.bindTicket(ticket);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClick(ticket);
                }
            }
        });
    }

    //用于创建ViewHolder实例
    @Override
    public TicketAdapterRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item,parent,false);
        TicketAdapterRV.ViewHolder holder = new TicketAdapterRV.ViewHolder(view);
        return holder;
    }

    //点击事件
    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(Ticket ticket);
    }

    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /*public void refreshData(List<Ticket> tickets){
        mTickets.clear();
        if (tickets != null){
            mTickets.addAll(tickets);
        }
        notifyDataSetChanged();
    }*/

}
