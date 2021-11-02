package com.example.sreachtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.sreachtest.adapter.TicketAdapterRV;
import com.example.sreachtest.bean.Ticket;
import com.example.sreachtest.bean.TicketLab;

public class SearchResultActivity extends AppCompatActivity {

    private static final String SEARCH_CONTENT = "SEARCH_CONTENT";
    private RecyclerView mRecyclerView;
    private TicketAdapterRV mAdapter;
    private List<Ticket> mTickets;
    private String searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initData();
    }

    private void initData()
    {
        mTickets=new ArrayList<>();
        //获取搜索内容
        searchContent=getIntent().getStringExtra(SEARCH_CONTENT);
        //addAll方法表示将Collection中所有的元素添加到mTickets（也就是将另一个集合添加到另一个集合）
        //mTickets为搜索结果集合，通过传到适配器，再放到recyclerview
        mTickets.addAll(TicketLab.getInstance(this).searchResult(searchContent,0));
        if (mTickets.size()>0){//说明搜索的内容是我们的范围里面的
            initElement();
            init();
        }else {
            Toast.makeText(this, "搜索内容不在范围内！", Toast.LENGTH_SHORT).show();
        }
    }
    private void initElement()
    {
        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
    }
    private void init()
    {
        //将搜索结果的数据集合添加进适配器，并将适配器设置给相应的RecyclerView
        LinearLayoutManager layoutManagerMR=new LinearLayoutManager(this);
        layoutManagerMR.setOrientation(LinearLayoutManager.VERTICAL);//设置布局排列方向
        mRecyclerView.setLayoutManager(layoutManagerMR);
        //将搜索结果的数据集合添加进适配器，此时的mTickets已经在上面获取到了搜索结果，图片+文字
        mAdapter=new TicketAdapterRV(mTickets,this);
        mRecyclerView.setAdapter(mAdapter);//适配器设置给相应的RecyclerView
        //添加分割线（让显示更明显）
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mAdapter.setOnItemClickListener(new TicketAdapterRV.OnItemClickListener() {
            //重写点击方法（跳转到TicketDetailActivity中把搜索结果的图片显示出来）
            @Override
            public void onClick(Ticket ticket) {
                Intent intent = new Intent(SearchResultActivity.this,TicketDetailActivity.class);

                intent.putExtra("TICKET", ticket);
                startActivity(intent);
            }
        });
    }
}