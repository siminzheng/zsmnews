package com.example.zsmnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zsmnews.dataBean.user;

import org.litepal.LitePal;

import java.util.List;

import Utils.ifisEmail;

public class rigister extends AppCompatActivity {



    EditText youxiang_rigister;
    EditText mima_rigister;
    Button bt_zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigister);

        youxiang_rigister = (EditText) findViewById(R.id.youxiang_rigister);
        mima_rigister = (EditText) findViewById(R.id.mima_rigister);
        bt_zhuce = (Button) findViewById(R.id.bt_zhuce);

        bt_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youxiang= String.valueOf(youxiang_rigister.getText());
                String mima= String.valueOf(mima_rigister.getText());
                List<user> userlist = LitePal.where("user=?",youxiang).find(user.class);
                if (ifisEmail.isEmail(youxiang.trim()) && mima !=null) {
                    if (userlist.size() != 0) {
                        Toast.makeText(rigister.this, "这个邮箱已被注册", Toast.LENGTH_LONG).show();
                    } else {
                        user myuser = new user();
                        myuser.setUser(youxiang);
                        myuser.setPasswd(mima);
                        myuser.save();
                        Toast.makeText(rigister.this, "注册成功，正在返回登录界面...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(rigister.this, login.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Toast.makeText(rigister.this, "请输入正确的邮箱账号和密码", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}