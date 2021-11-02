package com.example.sreachtest.helper;
import android.database.sqlite.SQLiteDatabase;
import org.litepal.LitePal;
import java.util.List;
import com.example.sreachtest.bean.SearchRecord;
/*
数据库操作类
 */
public class DBHelper {
//static静态，表示程序启动前先执行，属性的话会先初始话
    //private static SQLiteDatabase db= LitePal.getDatabase();这条语句就是创建数据库，
    //LitePal.getDatabase(),表示已经建好数据库了，再传给db，db其实没什么用
    private static SQLiteDatabase db= LitePal.getDatabase();
    //获取所有历史记录
    public static List<SearchRecord> getHistoryRecords()
    {
        List<SearchRecord> records=LitePal.where("type==0").find(SearchRecord.class);
      return records;
    }
    //保存一条历史记录(增加一条历史记录)
    public static void insertHistoryRecord(String content)
    {
        SearchRecord sr=new SearchRecord();
        sr.setContent(content);
        sr.setType(0);
        sr.save();
    }
    //删除所有的历史记录（删除所有type==0的）
    public  static void deleteAllHistoryRecords()
    {
        LitePal.deleteAll(SearchRecord.class,"type=?","0");
    }
}
