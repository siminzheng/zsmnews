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

import pager.BasePager;
import pager.MenuDetailBasePager;
import pager.TabDetailPager.PhotoTabDetailPager;

public class PhotoDetailPager extends MenuDetailBasePager {



    @ViewInject(R.id.photo_slidingtablayout)
    SlidingTabLayout photo_slidingtablayout;

    @ViewInject(R.id.photodetail_vp)
    ViewPager photodetail_vp;

    //网易图片类型
    String wangyipic_title[]={"军事","热点","明星","游戏"};
    String wangyipic_typetitle[]={"xinwen","redian","mingxing","youxi"};
    private ArrayList<Object> photoTabDetailpagers;


    public PhotoDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.photodetailpager,null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        photoTabDetailpagers=new ArrayList<>();

        for (int i=0;i<wangyipic_title.length;i++){
            photoTabDetailpagers.add(new PhotoTabDetailPager(context,wangyipic_typetitle[i]));
        }
        //设置适配器
        photodetail_vp.setAdapter(new MyPhotoDetailPagerAdapter());
        //viewpager与SlidingTabLayout相关联
        photo_slidingtablayout.setViewPager(photodetail_vp,wangyipic_title);

    }

    class MyPhotoDetailPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return photoTabDetailpagers.size();
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

            PhotoTabDetailPager photoTabDetailPager= (PhotoTabDetailPager) photoTabDetailpagers.get(position);
            View view=photoTabDetailPager.rootview;
            photoTabDetailPager.initData();
            container.addView(view);
            return view;
        }
    }
}
