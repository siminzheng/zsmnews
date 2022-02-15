package com.example.zsmnews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import MyFragment.ContentFragment;

public class zhu extends AppCompatActivity {


    public static final String CONTENT_TAG="content_tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu);
        initFragment();

    }

    private void initFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.content,new ContentFragment(),CONTENT_TAG);
        ft.commit();

    }


}