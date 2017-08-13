package keller.com.second_hand_car.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import keller.com.second_hand_car.R;
import keller.com.second_hand_car.activity.CarFavActivity;
import keller.com.second_hand_car.activity.CarRecordActivity;
import keller.com.second_hand_car.activity.CarScanHisActivity;
import keller.com.second_hand_car.activity.GuessPriceActivity;
import keller.com.second_hand_car.activity.LoginActivity;
import keller.com.second_hand_car.activity.WhatYourLIkeActivity;
import keller.com.second_hand_car.ui.UIHelper;
import keller.com.second_hand_car.ui.pulltozoomview.PullToZoomScrollViewEx;
import keller.com.second_hand_car.utils.ToastUtils;

public class MyFragment extends Fragment {

    private Activity context;
    private View root;
    private PullToZoomScrollViewEx scrollView;
    private SharedPreferences sp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return root = inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initData();
        initView();
    }

    void initView() {

        scrollView = (PullToZoomScrollViewEx) root.findViewById(R.id.scrollView);
        View headView = LayoutInflater.from(context).inflate(R.layout.member_head_view, null, false);
        View zoomView = LayoutInflater.from(context).inflate(R.layout.member_zoom_view, null, false);
        View contentView = LayoutInflater.from(context).inflate(R.layout.member_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        sp=getActivity().getSharedPreferences("userinfo", 0);
        if(!sp.getString("uid","default").equals("default")){
            headView.findViewById(R.id.tv_register).setVisibility(View.GONE);
            headView.findViewById(R.id.tv_login).setVisibility(View.GONE);
            ((TextView)headView.findViewById(R.id.tv_user_name)).setText(sp.getString("username","default"));
        }else{
            headView.findViewById(R.id.tv_register).setVisibility(View.VISIBLE);
            headView.findViewById(R.id.tv_login).setVisibility(View.VISIBLE);
            scrollView.getPullRootView().findViewById(R.id.textSetting).setVisibility(View.GONE);
        }


        headView.findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showRegister(getActivity());
            }
        });

        headView.findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showLogin(getActivity());
            }
        });

        //收藏
        scrollView.getPullRootView().findViewById(R.id.textBalance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //检查是否登陆
                String username = sp.getString("username","");
                if (!username.equals("")){
                    Intent intent = new Intent(getActivity(), CarFavActivity.class);
                    startActivityForResult(intent,0);
                }else {
                    ToastUtils.showToastSafe(getActivity(),"请先登录");
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }


            }
        });
        //历史记录
        scrollView.getPullRootView().findViewById(R.id.textRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //检查是否登陆
                String username = sp.getString("username","");
                if (!username.equals("")){
                    Intent intent = new Intent(getActivity(), CarScanHisActivity.class);
                    startActivityForResult(intent,0);
                }else {
                    ToastUtils.showToastSafe(getActivity(),"请先登录");
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        //猜你喜欢
        scrollView.getPullRootView().findViewById(R.id.textAttention).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查是否登陆
                String username = sp.getString("username","");
                if (!username.equals("")){
                    Intent intent = new Intent(getActivity(), WhatYourLIkeActivity.class);
                    startActivityForResult(intent,0);
                }else {
                    ToastUtils.showToastSafe(getActivity(),"请先登录");
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        //估计车价
        scrollView.getPullRootView().findViewById(R.id.textHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查是否登陆
                String username = sp.getString("username","");
                if (!username.equals("")){
                    Intent intent = new Intent(getActivity(), GuessPriceActivity.class);
                    startActivityForResult(intent,0);
                }else {
                    ToastUtils.showToastSafe(getActivity(),"请先登录");
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        //购车记录
        scrollView.getPullRootView().findViewById(R.id.textCalculator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查是否登陆
                String username = sp.getString("username","");
                if (!username.equals("")){
                    Intent intent = new Intent(getActivity(), CarRecordActivity.class);
                    startActivityForResult(intent,0);
                }else {
                    ToastUtils.showToastSafe(getActivity(),"请先登录");
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        //退出
        scrollView.getPullRootView().findViewById(R.id.textSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sp.edit();
                editor.remove("username");
                editor.remove("userpwd");
                editor.remove("uid");
                editor.remove("userlogin");
                editor.commit();
                initView();

            }
        });

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenHeight = localDisplayMetrics.heightPixels;
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 16.0F)));
        scrollView.setHeaderLayoutParams(localObject);
    }

    private void initData() {

    }
	

	
}
