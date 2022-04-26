package com.example.zsmnews;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import com.example.zsmnews.dataBean.user;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout guide_rlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guide_rlayout = (RelativeLayout) findViewById(R.id.guide_rlayout);
        AlphaAnimation aa=new AlphaAnimation(0,1);
        aa.setFillAfter(true);
        AnimationSet ani=new AnimationSet(false);
        ani.addAnimation(aa);
        ani.setDuration(4000);
        ani.setAnimationListener(new MyAnimationListener());
        guide_rlayout.startAnimation(ani);

//        requestPermissions();
    }
//    private void requestPermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
//        }
//    }




    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            //LitePal.deleteAll(user.class);
            Intent intent=new Intent(MainActivity.this,login.class);
            startActivity(intent);
            finish();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}