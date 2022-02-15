package pager.DetailPagers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.zsmnews.R;
import com.flyco.tablayout.SlidingTabLayout;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pager.MenuDetailBasePager;
import pager.TabDetailPager.NewTabDetailPager;


public class NewsDetailPager extends MenuDetailBasePager {

    //private final List<String> pindaolist;
    @ViewInject(R.id.slidingtablayout)
    SlidingTabLayout slidingtablayout;
    @ViewInject(R.id.newsdetail_vp)
    ViewPager newsdetail_vp;

    //极速新闻
    String title[]={"头条","新闻","国内","国际","政治","财经","体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿"};

    //聚合新闻类型
    String juhe_title[]={"头条","国内","国际","娱乐","体育","军事","科技","财经","游戏","汽车","健康"};
    String juhe_typetitle[]={"top","guonei","guoji","yule","tiyu","junshi","keji","caijing","youxi","qiche","jiankang"};

    //页签页面的集合
    private ArrayList<NewTabDetailPager> newTabDetailPagers;

    public NewsDetailPager(Context context) {
        super(context);
        //this.pindaolist=pindaolist;

    }

    @Override
    public View initView() {

        View view=View.inflate(context, R.layout.newsdetailpager,null);
        x.view().inject(this,view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻详情页面数据被初始化了"+juhe_title[0]);
        //Toast.makeText(context,"新闻详情页面数据被初始化了"+pindaolist.get(0),Toast.LENGTH_LONG).show();

        newTabDetailPagers=new ArrayList<>();
        for (int i=0;i<juhe_title.length;i++){
            newTabDetailPagers.add(new NewTabDetailPager(context,juhe_typetitle[i]));
        }

        //设置适配器
        newsdetail_vp.setAdapter(new MynewsDetailPagerApdapter());
        //viewpager与SlidingTabLayout相关联
        slidingtablayout.setViewPager(newsdetail_vp,juhe_title);

    }

    class MynewsDetailPagerApdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return newTabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            NewTabDetailPager newsTabDetailPager=newTabDetailPagers.get(position);
            View view=newsTabDetailPager.rootview;
            newsTabDetailPager.initData();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
