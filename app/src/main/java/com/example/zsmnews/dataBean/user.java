package com.example.zsmnews.dataBean;

import org.litepal.crud.LitePalSupport;

public class user extends LitePalSupport {


    private String user;
    private String passwd;

    public void setUser(String user){
        this.user=user;
    }

    public String getUser(){
        return this.user;
    }

    public void setPasswd(String passwd){
        this.passwd=passwd;
    }


    public String getPasswd(){
        return this.passwd;
    }

}
