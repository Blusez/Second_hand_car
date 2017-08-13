package keller.com.second_hand_car.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.panxw.android.imageindicator.AutoPlayManager;
import com.panxw.android.imageindicator.ImageIndicatorView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import keller.com.second_hand_car.Adapter.ImageAdapter.NetworkImageIndicatorView;
import keller.com.second_hand_car.R;
import keller.com.second_hand_car.model.CarInfor;
import keller.com.second_hand_car.utils.HttpUtil;
import keller.com.second_hand_car.utils.ReadProperties;
import keller.com.second_hand_car.utils.ToastUtils;

public class CarinforActivity extends AppCompatActivity {
    //图片轮播
    private NetworkImageIndicatorView mAutoImageIndicatorView;
    private AutoPlayManager mAutoPlayManager;
    //不能设置static final,否则会重复加载
    private List<String> URL_LIST = new ArrayList<String>();
    private CarInfor car_infor;
  //  private ImageView imageView;
    private TextView info_title;
    private TextView infoTitle;
    private TextView inforPrice;
    private SharedPreferences sp;
    private ImageView collection;

    private String username;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carinfor);

        this.mAutoImageIndicatorView = (NetworkImageIndicatorView) findViewById(R.id.network_indicate_view11);
        this.mAutoImageIndicatorView.setOnItemChangeListener(new ImageIndicatorView.OnItemChangeListener() {
            @Override
            public void onPosition(int position, int totalCount) {

            }
        });
        this.mAutoImageIndicatorView.setOnItemChangeListener(new ImageIndicatorView.OnItemChangeListener() {
            @Override
            public void onPosition(int position, int totalCount) {

            }
        });
        init();
        initData();
        initView();
        new OrFavAsyncTask().execute();


    }


    private void initView() {
        this.mAutoImageIndicatorView.setupLayoutByImageUrl(URL_LIST);
        this.mAutoImageIndicatorView.show();
//        new OrFavAsyncTask().execute();
//        AutoPlayManager autoPlayManager = new AutoPlayManager(this.mAutoImageIndicatorView);
//        autoPlayManager.setBroadcastEnable(true);
//        autoPlayManager.setBroadCastTimes(5);//循环次数
//        autoPlayManager.setBroadcastTimeIntevel(3 * 1000, 3 * 1000);//首次启动时间及间隔
//        autoPlayManager.loop();

//        this.mAutoPlayManager = autoPlayManager;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mAutoPlayManager != null) {
            mAutoPlayManager.stop();
        }
    }

    private void init() {
//        imageView = (ImageView) findViewById(R.id.infoImg);
        infoTitle = (TextView) findViewById(R.id.infoTitle);
        info_title = (TextView) findViewById(R.id.info_title);
        inforPrice = (TextView) findViewById(R.id.inforPrice);
        collection = (ImageView) findViewById(R.id.collection);


    }

    private void initData() {

        sp=this.getSharedPreferences("userinfo", 0);
        username = sp.getString("username","");
        uid = sp.getString("uid","");
        Intent intent = this.getIntent();
        car_infor = (CarInfor) intent.getSerializableExtra("car_infor");
//        Toast.makeText(this,"点击了第"+car_infor.getcName()+"个",Toast.LENGTH_SHORT).show();
        info_title.setText(car_infor.getcName().split(" ")[0]);
        infoTitle.setText(car_infor.getcName());
        inforPrice.setText(car_infor.getcPrice());
//        String url = car_infor.getpUrl().split(",")[0].trim();
        for(String url:car_infor.getpUrl().split(",")){
            URL_LIST.add(url.trim());
        }
        //使用Glide加载图片,自适应大小
//        ImageUtil.loadIntoUseFitWidth(this,url,R.drawable.baoma,imageView);
//        Glide
//                .with(this)
//                .load(url)
//                .into(imageView);


    }


    public void my_ret(View view) {
//        Toast.makeText(this,"点击了第个",Toast.LENGTH_SHORT).show();
        finish();
    }
    public void collectionBtn(View view) {
//        Toast.makeText(this,"点击了第个",Toast.LENGTH_SHORT).show();
        new InsertAadDelTask().execute();

    }
    public void buyCar(View view) {
//        Toast.makeText(this,"点击了第个",Toast.LENGTH_SHORT).show();
        //检查是否登陆
        if (!username.equals("")){
            new LoadCarAsyncTask().execute();
        }else {
            ToastUtils.showToastSafe(this,"请先登录");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

//        finish();
    }


    class LoadCarAsyncTask extends AsyncTask<String, Integer, String> {
        private boolean flag = false;
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (HttpUtil.isNetConn(CarinforActivity.this)) {
                String url = "CarInforController/updateStatusByid.do?id=" + car_infor.getcId() + "&status=1&uid="+uid;
                byte[] b = HttpUtil.downloadFromNet(CarinforActivity.this, url);  //可变参数params当成一个数组使用，其中的params[0]就是我们传递过来的参数
                String jsonString = new String(b).trim();
                Log.d("Tag", jsonString);

                try {
                    if (jsonString != null && !jsonString.equals("no") && !jsonString.equals("")) {
                        if (!jsonString.equals("no")) {
                            //获取
                            flag = true;

                        }
                    } else {
                        flag = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            if (flag) {
//                Intent intent = new Intent(CarinforActivity.this, SuccessActivity.class);
//                startActivity(intent);
                // Toast.makeText(CarinfoActivity.this,"点击了第个",Toast.LENGTH_SHORT).show();
                ToastUtils.showDialog(CarinforActivity.this,"");
//                finish();
            } else {
                finish();
            }
        }
    }


    class OrFavAsyncTask extends AsyncTask<String, Integer, String> {
        private boolean flag = false;
        private Context context;
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (HttpUtil.isNetConn(CarinforActivity.this)) {
                String url = "CarFarController/orFav.do?cid=" + car_infor.getcId() + "&status=1&uid="+uid;
                try {
                    URL url1=new ReadProperties().getUrl(CarinforActivity.this,url);
                    HttpURLConnection connection=(HttpURLConnection) url1.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("charset", "utf-8");
                    OutputStream out=connection.getOutputStream();
                    out.close();
                    InputStream inputStream=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer sbBuffer = new StringBuffer();
                    //rtn=reader.readLine();
                    String temp = "";
                    while (null != (temp = reader.readLine())) {
                        sbBuffer.append(temp);
                    }
                    reader.close();

                    Log.d("Tag", sbBuffer.toString());
                    String jsonString=sbBuffer.toString();
                    if (jsonString != null && !jsonString.equals("no") && !jsonString.equals("")) {
                        if (!jsonString.equals("no")) {
                            //获取
                            flag = true;

                        }
                    } else {
                        flag = false;
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
        //这个可以更新UI
        @Override
        protected void onProgressUpdate(Integer... progress) {

        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            if (flag){
                Log.d("seccess","seccess");
                collection.setImageDrawable(getResources().getDrawable(R.drawable.myshoucang1));
//                collection.g
//                CarinforActivity.ImageLoader.destroy();
//                Glide
//                .with(CarinforActivity.this)
//                .load(R.drawable.myshoucang1)
//                .into(collection);
            }else{
                collection.setImageDrawable(getResources().getDrawable(R.drawable.myshoucang));
            }
        }
    }

    class InsertAadDelTask extends AsyncTask<String, Integer, String> {
        private boolean flag = false;
        private Context context;
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (HttpUtil.isNetConn(CarinforActivity.this)) {
                String url = "CarFarController/addAnddelfav.do?cid=" + car_infor.getcId() + "&status=1&uid="+uid;
                try {
                    URL url1=new ReadProperties().getUrl(CarinforActivity.this,url);
                    HttpURLConnection connection=(HttpURLConnection) url1.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("charset", "utf-8");
                    OutputStream out=connection.getOutputStream();
                    out.close();
                    InputStream inputStream=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer sbBuffer = new StringBuffer();
                    //rtn=reader.readLine();
                    String temp = "";
                    while (null != (temp = reader.readLine())) {
                        sbBuffer.append(temp);
                    }
                    reader.close();

                    Log.d("Tag", sbBuffer.toString());
                    String jsonString=sbBuffer.toString();
                    if (jsonString != null && !jsonString.equals("no") && !jsonString.equals("")) {
                        if (!jsonString.equals("no")) {
                            //获取
                            flag = true;

                        }
                    } else {
                        flag = false;
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
            return null;
        }
        //这个可以更新UI
        @Override
        protected void onProgressUpdate(Integer... progress) {
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            if (flag){
                Log.d("seccess","seccess");
                collection.setImageDrawable(getResources().getDrawable(R.drawable.myshoucang1));
//                collection.g
//                CarinforActivity.ImageLoader.destroy();
//                Glide
//                .with(CarinforActivity.this)
//                .load(R.drawable.myshoucang1)
//                .into(collection);
            }else{
                collection.setImageDrawable(getResources().getDrawable(R.drawable.myshoucang));
            }

        }
    }
}
