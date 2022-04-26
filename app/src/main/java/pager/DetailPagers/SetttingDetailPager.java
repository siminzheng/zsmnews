package pager.DetailPagers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zsmnews.R;
import com.example.zsmnews.login;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import pager.MenuDetailBasePager;
import pager.realpager.lishitoday;
import pager.realpager.wodexinxi;
import pager.realpager.xinwenshoucangjiemian;

public class SetttingDetailPager extends MenuDetailBasePager {

    @ViewInject(R.id.touxiang)
    ImageView touxiang;

    @ViewInject(R.id.yonghu)
    TextView yonghu;

    @ViewInject(R.id.jiqiren)
    Button jiqiren;

    @ViewInject(R.id.xinxi)
    Button xinxi;

    @ViewInject(R.id.xinwenshoucang)
    Button xinwenshoucang;

    @ViewInject(R.id.denglujiemian)
    Button denglujiemian;




    public SetttingDetailPager(Context context) {
        super(context);

    }

    @Override
    public View initView() {

        View view=View.inflate(context, R.layout.settingdetailpager,null);
        x.view().inject(this,view);

        xinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, wodexinxi.class);
                context.startActivity(intent);
            }
        });

        jiqiren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, lishitoday.class);
                context.startActivity(intent);
            }
        });

        xinwenshoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jinrushoucangjiemian();
            }
        });

        denglujiemian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIfTuiChuDengLu();
            }
        });


        return view;
    }

    private void jinrushoucangjiemian() {
        Intent intent=new Intent(context, xinwenshoucangjiemian.class);
        context.startActivity(intent);
    }

    private void showIfTuiChuDengLu() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setIcon(R.mipmap.gantanhao1)
                .setTitle("退出确认")
                .setMessage("是否要退出登录?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context,"正在退出登录...",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context,login.class);
                        context.startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(context,"",Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
        builder.create().show();

    }

    @Override
    public void initData() {



        super.initData();
    }
}
