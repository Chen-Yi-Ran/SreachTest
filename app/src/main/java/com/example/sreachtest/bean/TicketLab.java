package com.example.sreachtest.bean;
import android.content.Context;

import com.example.sreachtest.R;
import com.example.sreachtest.bean.Ticket;
import java.util.ArrayList;
import java.util.List;
/*
车票 单例(对我们的图片和姓名进行操作的类)
 */
public class TicketLab {
    //车票单例
    private static TicketLab sTicketLab;
    //车票集合
    private List<Ticket> mTickets;
    //上下文
    private Context mContext;
    //提供一种访问其唯一的对象方式，可以直接访问
    public static TicketLab getInstance(Context context)
    {
        //该类负责创建自己的对象，同时确保只要单个对象被创建
        //如果不存在则new一个再返回，存在了直接返回
        if(sTicketLab==null)
        {
            sTicketLab=new TicketLab(context);
        }
        return sTicketLab;
    }
    //通过构造方法将原先的数据存进去
private TicketLab(Context context)
{
    mContext=context;
    initTickets();
}
private List<Ticket> getTickets()
{
    return mTickets;
}
private void initTickets()
{
    mTickets =new ArrayList<>();
    mTickets.add(new Ticket("Chen",R.mipmap.tx1));
    mTickets.add(new Ticket("Li",R.mipmap.tx2));
    mTickets.add(new Ticket("Wan",R.mipmap.tx3));
    mTickets.add(new Ticket("Zhao",R.mipmap.tx4));
    mTickets.add(new Ticket("zhang",R.mipmap.tx5));
    mTickets.add(new Ticket("Si",R.mipmap.tx6));
}
    /* *
     * @param content 搜索内容
     * @param type 搜索类型 0 按照出发地点搜索 1 按照目的地搜索 2 按照巴士类型搜索 3 0+1+2
     * @return List<Ticket>*/
public List<Ticket> searchResult(String content,int type){
    List<Ticket> tickets = new ArrayList<>();
    switch (type){
        case 0:
            for (Ticket ticket : mTickets) {
                //包含匹配，则添加到搜索结果列表
                if (ticket.getName().contains(content)){
                    tickets.add(ticket);
                }
            }
            break;
        /*case 1:
            for (Ticket ticket : mTickets) {
                if (ticket.getDestinationStation().contains(content)){
                    tickets.add(ticket);
                }
            }
            break;
        case 2:
            for (Ticket ticket : mTickets) {
                if (ticket.getBusType().contains(content)){
                    tickets.add(ticket);
                }
            }
            break;
        case 3:
            for (Ticket ticket : mTickets) {
                if (ticket.getOriginStation().contains(content) || ticket.getDestinationStation().contains(content) || ticket.getBusType().contains(content)){
                    tickets.add(ticket);
                }
            }
            break;*/
        default:
            break;
    }
    return tickets;//匹配得到的话就有数据加进去，没有的话就为null

//    if (tickets.size() > 0){
//        return tickets;
//    }else {
//        return mTickets;//如果搜索内容匹配不到，则返回所有
//    }
}

//获取所有车票的部分信息（姓名和图片）用于自动补全
    public List<Object> getTicketsInfo()
    {
        List<Object> data=new ArrayList<>();
        for (Ticket ticket:mTickets)
        {
            if(!data.contains(ticket.getName()))
            {
                data.add(ticket.getName());
            }
            if(!data.contains(ticket.getImg()))
            {
                data.add(ticket.getImg());
            }
        }
        return data;
    }

//是否为名字信息，是的话返回true，不是返回false
    public boolean isTicketsInfo(String content)
    {
        for (Ticket ticket:mTickets)
        {
            if(ticket.getName().equals(content))
            {
                return true;
            }
        }
        return false;
    }
}
