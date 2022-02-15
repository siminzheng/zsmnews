package com.example.zsmnews;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

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
    }

    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Intent intent=new Intent(MainActivity.this,zhu.class);
            startActivity(intent);
            finish();

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}