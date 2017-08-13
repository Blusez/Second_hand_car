package keller.com.second_hand_car;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import keller.com.second_hand_car.activity.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private TextView versionNumber;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //整个layout的ID
        mLinearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout01);
        //版本号的id
        versionNumber = (TextView) this.findViewById(R.id.versionNumber);
        //这里要通过方法得到配置文件的版本号添加到splash街面上去
        versionNumber.setText(getVersion());
        // 判断当前网络状态是否可用
        if (isNetWorkConnection()) {
            // splash 做一个动画,进入主界面
            AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
            aa.setDuration(2000);
            mLinearLayout.setAnimation(aa);
            mLinearLayout.startAnimation(aa);
            // 通过handler 延时2秒 执行r任务
            new Handler().postDelayed(new LoadMainTabTask(), 2000);
        } else {
            showSetNetworkDialog();
        }

    }
    private class LoadMainTabTask implements Runnable{

        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void showSetNetworkDialog() {

        AlertDialog.Builder builder = new Builder(this);
        builder.setTitle("设置网络");
        builder.setMessage("网络错误请检查网络状态");
        builder.setPositiveButton("设置网络", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                // 类名一定要包含名
                if(android.os.Build.VERSION.SDK_INT > 10 ){
                    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                }else {
                    startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                }
                finish();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();
    }

    private boolean isNetWorkConnection() {
        ConnectivityManager manager=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        return (info!=null&&info.isConnected());
    }

    private String getVersion() {
        try {
            //通过包管理者拿到配置文件中的版本号
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
            return "Version" + info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "Version";
        }
    }

}
