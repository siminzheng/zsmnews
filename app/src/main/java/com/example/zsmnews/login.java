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

public class login extends AppCompatActivity {

    EditText youxiang_login;
    EditText mima_login;
    Button bt_login;
    Button bt_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        youxiang_login = (EditText) findViewById(R.id.youxiang_login);
        mima_login = (EditText) findViewById(R.id.mima_login);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youxiang= String.valueOf(youxiang_login.getText());
                String mima= String.valueOf(mima_login.getText());
                List<user> userlist = LitePal.where("user=?",youxiang).find(user.class);
                if (youxiang !=null && mima !=null) {
                    if (userlist.size() == 0) {
                        Toast.makeText(login.this, "账号不存在或密码错误", Toast.LENGTH_LONG).show();
                    } else {
                        for (user item : userlist) {
                            if (youxiang == item.getUser() && mima == item.getPasswd()) {
                                Toast.makeText(login.this, "账号密码正确，正在登录...", Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                        Intent intent = new Intent(login.this, zhu.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

        });


        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,rigister.class);
                startActivity(intent);

            }
        });


    }
}