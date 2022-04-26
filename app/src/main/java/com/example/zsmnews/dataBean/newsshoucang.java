package com.example.zsmnews.dataBean;

import org.litepal.crud.LitePalSupport;

public class newsshoucang extends LitePalSupport {


    private String url;
    private String title;
    private String date;
    private String pic;

    public void setUrl(String url){
        this.url=url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic() {
        return pic;
    }
}
