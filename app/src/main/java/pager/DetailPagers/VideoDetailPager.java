package pager.DetailPagers;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.zsmnews.R;
import com.flyco.tablayout.SlidingTabLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import pager.MenuDetailBasePager;
import pager.TabDetailPager.NewTabDetailPager;
import pager.TabDetailPager.VideoTabDetailPager;

public class VideoDetailPager extends MenuDetailBasePager {
    //private final List<String> pindaolist;
    @ViewInject(R.id.video_slidingtablayout)
    SlidingTabLayout video_slidingtablayout;
    @ViewInject(R.id.videodetail_vp)
    ViewPager videodetail_vp;

    //网易视频类型
    String Wangyivideo_title[]={"搞笑","新闻","萌物","黑科技","二次元","涨知识"};
    String Wangyivideo_typetitle[]={"VAP4BFE3U","VAV3H6JSN","VAP4BFR16","VBF8F2PKF","VBF8F1PSA","VBF8F3SGL"};
    private ArrayList<VideoTabDetailPager> videoTabDetailPagers;


    public VideoDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view=View.inflate(context,R.layout.videodetailpager,null);
        x.view().inject(this,view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        videoTabDetailPagers=new ArrayList<>();
        for (int i=0;i<Wangyivideo_title.length;i++){
            videoTabDetailPagers.add(new VideoTabDetailPager(context,Wangyivideo_typetitle[i]));
        }

        //设置适配器
        videodetail_vp.setAdapter(new MyvideoDetailPagerAdapter());
        //viewpager与SlidingTabLayout相关联
        video_slidingtablayout.setViewPager(videodetail_vp,Wangyivideo_title);
    }

    class MyvideoDetailPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return videoTabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            VideoTabDetailPager videoTabDetailPager = videoTabDetailPagers.get(position);
            View view=videoTabDetailPager.rootview;
            videoTabDetailPager.initData();
            container.addView(view);
            return view;
        }
    }

}
