package com.example.sreachtest;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import org.litepal.LitePal;

import com.example.sreachtest.adapter.SRAdapterForRV;
import com.example.sreachtest.bean.SearchRecord;
import com.example.sreachtest.bean.SearchRecordLab;
import com.example.sreachtest.bean.TicketLab;
/*
搜索活动
 */
public class SearchActivity extends AppCompatActivity {
    private static final String SEARCH_CONTENT = "SEARCH_CONTENT";
    private ImageView mEmptyIV;
    private AutoCompleteTextView mAutoCompleteTextView;//自动补全类
    private RecyclerView mHistoryRV;
    private SRAdapterForRV mAdapter;//搜索记录适配器
    private ArrayAdapter mArrayAdapter;//自动补全适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
     //LitePal.initialize(this);//初始化数据库，也可以在项目清单中写*/
        //初始化控件
        initElement();
        init();
        //当点击删除按钮时
        mEmptyIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(SearchActivity.this);
                //通过AlertDialog.Builder创建一个AlertDialog的实例
                dialog.setTitle(getResources().getString(R.string.alert_dialog_title));
                dialog.setMessage(getResources().getString(R.string.alert_dialog_msg_for_search_activity));
                dialog.setCancelable(true);
                //为这个对话框设置标题，内容，可否取消属性
                dialog.setPositiveButton(getResources().getString(R.string.alert_dialog_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //清空历史记录也就是RecyclerView+FlexBoxlayout类型的
                        SearchRecordLab.get(SearchActivity.this).clearSearchRecords();
                        //历史记录recyclerView，刷新数据集合
                        mAdapter.notifyDataSetChanged();
                        //清理所有历史记录（也就是自动补全时的历史记录）
                        mArrayAdapter.clear();
                        //但我们也还要还原自动补全数据集合中的所有东西，以遍下次进行自动补全
                        mArrayAdapter.addAll(TicketLab.getInstance(SearchActivity.this).getTicketsInfo());
                        Toast.makeText(SearchActivity.this,getResources().getString(R.string.alert_dialog_ok_toast),Toast.LENGTH_LONG).show();
                    }
                });
                //调用setPositiveButton()方法为对话框设置确定按钮的点击事件
                dialog.setNegativeButton(getResources().getString(R.string.alert_dialog_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SearchActivity.this,getResources().getString(R.string.alert_dialog_cancel_toast),Toast.LENGTH_SHORT).show();
                    }
                });
                //调用setNegativeButton()方法为对话框设置取消按钮的点击事件
                dialog.show();//将对话框显示出来
            }
        });

        //点击搜索记录时,也就是搜索的历史记录(采用接口回调)
        mAdapter.setOnItemClickListener(new SRAdapterForRV.OnItemClickListener() {
            @Override
            public void onClick(SearchRecord searchRecord) {
                //跳转到搜索结果界面，并将搜索内容传递过去
                String content = searchRecord.getContent();
                Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                intent.putExtra(SEARCH_CONTENT,content);//存起来
                startActivity(intent);
            }
        });

        //当点击输入键盘的搜索时
        mAutoCompleteTextView.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //若为搜索活动，则开始执行相关逻辑
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = v.getText().toString();
                    //若搜索内容为空，则返回false,不执行
                    if ("".equals(content)){
                        return false;
                    }
                    //将搜索记录添加到搜索记录的单例中（通过get方法获取对象后在获取对象方法）
                    SearchRecordLab.get(SearchActivity.this).addSearchRecord(content);
                    //历史记录recyclerView，刷新数据集合（recyclerView在原有的基础上增加数据记得刷新一下）
                    mAdapter.notifyDataSetChanged();
                    //清空文本框
                    v.setText("");
                    //判断是否是存储的内容，若为新的内容则添加到补全适配器中的数据集合
                    if (!TicketLab.getInstance(SearchActivity.this).isTicketsInfo(content)){
                        mArrayAdapter.add(content);
                    }
                    //跳转到搜索结果页面，并将搜索内容传递过去，然后再跳到TicketDetail类将结果实现出来
                    Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
                    intent.putExtra(SEARCH_CONTENT,content);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    private void initElement() {
        mEmptyIV = (ImageView) findViewById(R.id.widget_search_empty_iv);
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.widget_search_ac_tv);
        mHistoryRV = (RecyclerView) findViewById(R.id.widget_search_history_rv);
    }

    private void init()
    {
        //新建数组适配器用于 AutoCompleteTextView 控件 参数一：上下文 参数二：布局样式（此处采用android自带的简易布局） 参数三：数据集合，此处为历史搜索记录的字符串集合
        mArrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, SearchRecordLab.get(this).getHistoryToStringList());
        //添加姓名和图片相关信息的字符串集合到数组适配器中用于自动补全
        mArrayAdapter.addAll(TicketLab.getInstance(this).getTicketsInfo());
        //给自动补全文本框设置适配器
        mAutoCompleteTextView.setAdapter(mArrayAdapter);
        //设置搜索记录适配器
        mAdapter=new SRAdapterForRV(SearchRecordLab.get(this).getSearchRecords());
        FlexboxLayoutManager layoutManager=new FlexboxLayoutManager(this);
        //设置是否换行
        layoutManager.setFlexWrap(FlexWrap.WRAP);//wrap: 按正常方向换行
        layoutManager.setAlignItems(AlignItems.STRETCH);//stretch (default) ：默认值，如果item没有设置高度，则充满容器高度
        mHistoryRV.setLayoutManager(layoutManager);
        mHistoryRV.setAdapter(mAdapter);
    }
}