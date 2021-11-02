package com.example.sreachtest.bean;

import java.io.Serializable;

/*
车票实体类（我们的内容）
 */
//Serializable为了保存在内存中的各种对象的状态（也就是实例变量，不是方法），并且可以把保存的对象状态再读出来
public class Ticket implements Serializable {
    //属性
    private String name;
    private int img;


    public Ticket(String name, int img) {
        this.name = name;
        this.img = img;
    }

    public int getImg() {
        return img;
    }
    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
