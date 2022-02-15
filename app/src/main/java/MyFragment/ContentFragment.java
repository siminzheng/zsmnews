package MyFragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.zsmnews.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import pager.BasePager;
import pager.NewsPager;
import pager.PhotoPager;
import pager.SettingPager;
import pager.VideoPager;

public class ContentFragment extends BaseFragment{

    @ViewInject(R.id.contant_viewpg)
    ViewPager contant_viewpg;

    @ViewInject(R.id.rbgroup)
    RadioGroup rbgroup;

    ArrayList<BasePager> basePagers;
    int ifnewsinitdata=0;
    int ifphotoinitdata=0;
    int ifvideosinitdata=0;
    int ifsettingsinitdata=0;

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.contentfragment_layout,null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initDate() {
        super.initDate();
        basePagers=new ArrayList<>();

        basePagers.add(new NewsPager(context));
        basePagers.add(new PhotoPager(context));
        basePagers.add(new VideoPager(context));
        basePagers.add(new SettingPager(context));


        //默认选择“新闻”
        rbgroup.check(R.id.rb_news);
        basePagers.get(0).initData();

        //设置适配器
        contant_viewpg.setAdapter(new MyContentFragmentPagerAdapter());
        //设置radiobutton的点击事件
        rbgroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置当转到“新闻”，“图片”，“视频”，“设置”的页面时,加载那个页面的数据
        contant_viewpg.addOnPageChangeListener(new MyOnPageChangeListener());

    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (ifnewsinitdata==0&&position==0){
                basePagers.get(position).initData();
                ifnewsinitdata++;
            }else if (ifphotoinitdata==0&&position==1){
                basePagers.get(position).initData();
                ifphotoinitdata++;
            }else if (ifvideosinitdata==0&&position==2){
                basePagers.get(position).initData();
                ifvideosinitdata++;
            }else if (ifsettingsinitdata==0&&position==3){
                basePagers.get(position).initData();
                ifsettingsinitdata++;
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
            switch (checkid){
                case R.id.rb_news:
                    contant_viewpg.setCurrentItem(0);
                    break;
                case R.id.rb_photo:
                    contant_viewpg.setCurrentItem(1);
                    break;
                case R.id.rb_video:
                    contant_viewpg.setCurrentItem(2);
                    break;
                case R.id.rb_setting:
                    contant_viewpg.setCurrentItem(3);
                    break;
            }

        }
    }

    class MyContentFragmentPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return basePagers.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager basePager=basePagers.get(position);
            View root_view=basePager.root_view;
            container.addView(root_view);
            return root_view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
    }
}
