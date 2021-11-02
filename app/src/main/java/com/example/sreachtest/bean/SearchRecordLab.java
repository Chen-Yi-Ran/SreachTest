package com.example.sreachtest.bean;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import com.example.sreachtest.helper.DBHelper;
/*
搜索记录 单例（只使用一次，如果原来里面有东西，下次使用会在原来的基础上继续使用）
（通过对数据库的方法进行调用，从而对搜索记录进行修改的类）
*/
public class SearchRecordLab {
    private static SearchRecordLab sSearchRecordLab;
    private List<SearchRecord> mSearchRecords;
    public static SearchRecordLab get(Context context)
    {
        if(sSearchRecordLab==null)
        {
            sSearchRecordLab=new SearchRecordLab(context);//会调用构造方法
        }
        return sSearchRecordLab;
    }

    private SearchRecordLab(Context context)
    {
        mSearchRecords=new ArrayList<>();
        //将所有数据库获取到的的历史记录放进mSearchRecords
        //只要调过改方法就是将目前已有的历史记录都放到mSearchRecords中
        mSearchRecords.addAll(DBHelper.getHistoryRecords());
    }
    public List<SearchRecord> getSearchRecords()
    {
        return mSearchRecords;
    }

//添加历史记录
public void addSearchRecord(String content)
{
    //判断是否存在该搜索记录
    boolean isExist=false;
    for(SearchRecord searchRecord:mSearchRecords)
    {
        if(searchRecord.getContent().equals(content))
        {
            isExist=true;
        }
    }
    //如果已经存在历史记录，就不添加，否则添加
    //这个是看你的历史记录，已经有相同的历史记录在历史就不会继续添加到属于库了，而是放到mSearchRecords集合中
    if(!isExist)
    {
        //也就是把没有的历史记录数据保存到数据库
        DBHelper.insertHistoryRecord(content);
     SearchRecord sr=new SearchRecord();//再次new一个对象
    sr.setType(0);
      sr.setContent(content);
 //     sr.save();不能增加这条语句，不然数据库就增加一条数据了
      //content代表搜索的名字
        mSearchRecords.add(sr);//将对象放进集合
    }
}

//清空所有的搜索记录包括数据库
public void clearSearchRecords()
{
    DBHelper.deleteAllHistoryRecords();//删除数据库所有元素
    mSearchRecords.clear();//清除mSearchRecords集合中所有的元素
}

//获取历史记录的字符串集合
public List<String> getHistoryToStringList()
{
    List<String> strings=new ArrayList<>();
    for(SearchRecord searchRecord:mSearchRecords)
    {
        strings.add(searchRecord.getContent());
    }
    return strings;
}
}
