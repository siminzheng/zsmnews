package pager.realpager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zsmnews.R;
import com.example.zsmnews.dataBean.newsshoucang;

import org.litepal.LitePal;
import org.xutils.x;

import java.util.List;

import pager.TabDetailPager.NewTabDetailPager;

public class xinwenshoucangjiemian extends AppCompatActivity {



    ListView lv_xinwenshoucang;
    private List<newsshoucang> newsshoucanglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinwenshoucangjiemian);
        lv_xinwenshoucang = (ListView) findViewById(R.id.lv_xinwenshoucang);
        newsshoucanglist= LitePal.findAll(newsshoucang.class);
        lv_xinwenshoucang.setAdapter(new MyxinwenshoucangAdapter());

        lv_xinwenshoucang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int poisition, long l) {
                newsshoucang shoucanglsitem2 = newsshoucanglist.get(poisition);
                Intent intent=new Intent(xinwenshoucangjiemian.this,realxinwenshoucang.class);
                Bundle bd=new Bundle();
                bd.putString("realnewsurl",shoucanglsitem2.getUrl());
                bd.putString("realnewstitle",shoucanglsitem2.getTitle());
                bd.putString("realnewsdate",shoucanglsitem2.getDate());
                bd.putString("realnewspic",shoucanglsitem2.getPic());
                intent.putExtras(bd);

                startActivity(intent);

            }
        });

    }


    private class MyxinwenshoucangAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return newsshoucanglist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int poisition, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder=new ViewHolder();
            if (view==null){
                view=View.inflate(xinwenshoucangjiemian.this,R.layout.xinwenshoucanglistitem,null);
                viewHolder.xwsc_iv_image=(ImageView)view.findViewById(R.id.xwsc_iv_image);
                viewHolder.xwsc_tv_newstitle=(TextView)view.findViewById(R.id.xwsc_tv_newstitle);
                viewHolder.xwsc_tv_date=(TextView) view.findViewById(R.id.xwsc_tv_date);

                view.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) view.getTag();
            }

            newsshoucang shoucanglsitem = newsshoucanglist.get(poisition);

            String imaurl=shoucanglsitem.getPic();
            x.image().bind(viewHolder.xwsc_iv_image,imaurl);
            viewHolder.xwsc_tv_newstitle.setText(shoucanglsitem.getTitle());
            viewHolder.xwsc_tv_date.setText(shoucanglsitem.getDate());

            return view;
        }
    }

    class ViewHolder{
        ImageView xwsc_iv_image;
        TextView xwsc_tv_newstitle;
        TextView xwsc_tv_date;
    }
}