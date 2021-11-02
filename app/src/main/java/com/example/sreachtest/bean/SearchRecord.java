package com.example.sreachtest.bean;

import org.litepal.crud.LitePalSupport;

/*数据库实体类
搜索记录实体类（继承LitePalSupport，才能对数据库进行增删改查）
 */
public class SearchRecord extends LitePalSupport {

   private Integer type;//设置一个中间值，type并都设置为0，方便在删除按钮时全删除

    private String content;//名字

    public Integer getType() {
       return type;
  }

  public void setType(Integer type) {
        this.type = type;
   }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
