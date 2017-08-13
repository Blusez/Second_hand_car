package keller.com.second_hand_car.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import keller.com.second_hand_car.R;
import keller.com.second_hand_car.fragment.BuyFragment;
import keller.com.second_hand_car.fragment.HomeFragment;
import keller.com.second_hand_car.fragment.MyFragment;
import keller.com.second_hand_car.fragment.SaleFragment;
import keller.com.second_hand_car.utils.ToolBarUtil;

public class MainActivity extends AppCompatActivity {


    //	@InjectView(R.id.main_tv_title)
//    TextView mMainTvTitle;
    LinearLayout myt;
    //	@InjectView(R.id.main_viewpager)
    ViewPager mMainViewpager;

    //	@InjectView(R.id.main_bottom)
    LinearLayout mMainBottom;

    // xutils viewutils 注解方式去找控件
    // viewutils httpUitls dbutils bitmaputils
    //

    private List<Fragment> mFragments	= new ArrayList<Fragment>();
    private ToolBarUtil		mToolBarUtil;
    private String[]		mToolBarTitleArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//		ButterKnife.inject(this);
        initData();
        initListener();
    }

    private void init() {
        // TODO Auto-generated method stub
//        mMainTvTitle =(TextView) this.findViewById(R.id.main_tv_title);
        myt =(LinearLayout) this.findViewById(R.id.myt);
        mMainViewpager = (ViewPager) this.findViewById(R.id.main_viewpager);
        mMainBottom = (LinearLayout) this.findViewById(R.id.main_bottom);
    }

    private void initListener() {
        mMainViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 修改颜色
                mToolBarUtil.changeColor(position);
                // 修改title
                if (position==1){
                    myt.setVisibility(View.VISIBLE);
//                    mMainTvTitle.setText("搜索");
                }else{
                    myt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mToolBarUtil.setOnToolBarClickListener(new ToolBarUtil.OnToolBarClickListener() {
            @Override
            public void onToolBarClick(int position) {
                mMainViewpager.setCurrentItem(position);
            }
        });
    }

    private void initData() {
        // viewPager-->view-->pagerAdapter
        // viewPager-->fragment-->fragmentPagerAdapter-->fragment数量比较少
        // viewPager-->fragment-->fragmentStatePagerAdapter

        // 添加fragment到集合中
        mFragments.add(new HomeFragment());
        mFragments.add(new BuyFragment());
        mFragments.add(new SaleFragment());
        mFragments.add(new MyFragment());

        mMainViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        //Viewpager加Fragment做界面切换时数据消失的解决方式
        mMainViewpager.setOffscreenPageLimit(4);

        // 底部按钮
        mToolBarUtil = new ToolBarUtil();
        // 文字内容
        mToolBarTitleArr = new String[] { "首页", "买车","卖车","我的" };
        // 图标内容
        int[] iconArr = { R.drawable.selector_shou,R.drawable.selector_mybuy, R.drawable.selector_mai, R.drawable.selector_my };

        mToolBarUtil.createToolBar(mMainBottom, mToolBarTitleArr, iconArr);

        // 设置默认选中会话
        mToolBarUtil.changeColor(0);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
