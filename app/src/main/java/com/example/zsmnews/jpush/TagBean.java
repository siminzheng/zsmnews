package com.example.zsmnews.jpush;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Copyright(c) 2020 极光
 * Description
 */
public class TagBean implements Serializable , MultiItemEntity {

    public static final int TEXT = 1;
    public static final int ADD = 2;

    private String id;
    private String text;
    private boolean isEdit = false;

    private int itemType = TEXT;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    @Override
    public String toString() {
        return "TagBean{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", isEdit=" + isEdit +
                '}';
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
