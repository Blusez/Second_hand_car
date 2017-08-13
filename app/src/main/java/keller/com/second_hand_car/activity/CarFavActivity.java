package keller.com.second_hand_car.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import keller.com.second_hand_car.Adapter.ImageAdapter.ImageAdapter;
import keller.com.second_hand_car.R;
import keller.com.second_hand_car.model.CarInfor;
import keller.com.second_hand_car.utils.HttpUtil;
import keller.com.second_hand_car.view.XListView;

public class CarFavActivity extends AppCompatActivity implements XListView.IXListViewListener{

    private XListView mListView;
    private ImageAdapter mAdapter;
    private ArrayList<Map<String, Object>> items = new ArrayList<>();
    private Handler mHandler;
    private int start = 0;
    private int limit = 6;
    private static int refreshCnt = 0;
    private ImageView loadimg;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private List<CarInfor> carInfors = new ArrayList<>();
    private ImageAdapter imageAdapter;

    private String uid;
    private SharedPreferences sp;

    public CarFavActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_fav);
        initView();
    }


    private void init() {
        //没有影响
        geneItems();
//        getData();
    }

    private void initView() {
        sp=this.getSharedPreferences("userinfo", 0);
        uid = sp.getString("uid","");
        mListView = (XListView) findViewById(R.id.xListView5);
        mListView.setPullLoadEnable(true);

        if (HttpUtil.isNetConn(this)){
            new CarFavActivity.LoadCarAsyncTask(this, mListView).execute();
        }else {
            //必须直接添加数据的函数
            mAdapter = new ImageAdapter(this, getData());
            mListView.setAdapter(mAdapter);
//		mListView.setPullLoadEnable(false);
//		mListView.setPullRefreshEnable(false);

        }
        mListView.setXListViewListener(this);
        mHandler = new Handler();


    }



    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> retlist = new ArrayList<Map<String, Object>>();
        String url1 = "http://imgstore04.cdn.sogou.com/app/a/100520024/877e990117d6a7ebc68f46c5e76fc47a";
        String url2 = "https://image1.guazistatic.com/qn1701260942205f87f5a896374385552f2107b2038d69.jpg?imageView2/1/w/600/h/400/q/88";
        Map<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("title", "G2");
        map.put("info", "google 2");
        map.put("place", "google 1");
        map.put("img", url1);
        retlist.add(map);

        return retlist;
    }

    private void geneItems() {
        for (int i = 0; i != 5; ++i) {
//            items.add("refresh cnt " + (++start));
        }
    }


    public void mback(View view){
        finish();

    }
    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = 0;
                refreshCnt=0;
                list.clear();
//                getData();
                // mAdapter.notifyDataSetChanged();
//                mAdapter = new ImageAdapter(this, getData());
//                mListView.setAdapter(mAdapter);
//                new LoadCarAsyncTask(this, mListView).execute();
                if (HttpUtil.isNetConn(CarFavActivity.this)){
                    new CarFavActivity.LoadCarAsyncTask(CarFavActivity.this, mListView).execute();
                }else {
                    //必须直接添加数据的函数
                    mAdapter = new ImageAdapter(CarFavActivity.this, getData());
                    mListView.setAdapter(mAdapter);
                }
                onLoad();
            }
        }, 1000);
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = limit * (++refreshCnt);
                new CarFavActivity.LoadCarAsyncTask(CarFavActivity.this, mListView).execute();
//                getData();
//                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 1000);
    }


    private void initListener() {

    }

    class LoadCarAsyncTask extends AsyncTask<String, Integer, String> {

        private Context mContext;
        private XListView lv;

        public LoadCarAsyncTask(Context mContext, XListView lv) {
            this.mContext = mContext;
            this.lv = lv;
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (HttpUtil.isNetConn(mContext)) {
                String url = "CarFarController/selectFav.do?start=" + start + "&limit=" + limit+"&uid="+uid;
                byte[] b = HttpUtil.downloadFromNet(CarFavActivity.this, url);  //可变参数params当成一个数组使用，其中的params[0]就是我们传递过来的参数
                String jsonString = new String(b).trim();
                Log.d("Tag", jsonString);

                try {
                    carInfors.clear();
                    if (jsonString != null && !jsonString.equals("no")&&!jsonString.equals("")) {
                        if (!jsonString.equals("no")){
                            Log.d("jsonString",jsonString);
                            JSONArray array = new JSONArray(jsonString);
                            for (int i = 0; i < array.length(); i++) {
                                /**
                                 *
                                 */
                                CarInfor carInfor = new CarInfor();
                                JSONObject jsonObject = array.getJSONObject(i);
                                carInfor.setpUrl(jsonObject.getString("pUrl"));
                                carInfor.setcName(jsonObject.getString("cName"));
                                carInfor.setcId(jsonObject.getInt("cId"));
                                carInfor.setuId(jsonObject.getString("uId"));
                                carInfor.setbId(jsonObject.getString("bId"));
                                carInfor.setmId(jsonObject.getString("mId"));
                                carInfor.setcCity(jsonObject.getString("cCity"));
                                carInfor.setcPrice(jsonObject.getString("cPrice"));
                                carInfor.setcLicensrtime(jsonObject.getString("cLicensrtime"));
                                carInfor.setcDistance(jsonObject.getString("cDistance"));
                                carInfor.setcPraise(jsonObject.getInt("cPraise"));
                                carInfor.setcBrowse(jsonObject.getInt("cBrowse"));
                                carInfor.setcStatus(jsonObject.getInt("cStatus"));
                                carInfor.setcType(jsonObject.getInt("cType"));
                                carInfor.setLimit(jsonObject.getInt("limit"));
                                carInfor.setStart(jsonObject.getInt("start"));
                                Log.i("car dec;", carInfor.getpUrl());
                                carInfors.add(carInfor);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            if (carInfors.size() != 0) {
                //处理上拉刷新不重新载入
                if (imageAdapter == null) {
                    list = getData1();
                    imageAdapter = new ImageAdapter(CarFavActivity.this, list);
                    lv.setAdapter(imageAdapter);
                } else {
                    list.addAll(getData1());
                    imageAdapter.notifyDataSetChanged();
                }
//                lv.setAdapter(new ImageAdapter(this, getData1()));
            } else {
                if (imageAdapter == null) {
                    list = getData1();
                    imageAdapter = new ImageAdapter(CarFavActivity.this, list);
                    lv.setAdapter(imageAdapter);
                } else {
                    list.addAll(getData1());
                    imageAdapter.notifyDataSetChanged();
                }
//                list.addAll(getData());
//                imageAdapter = new ImageAdapter(this, list);
//                lv.setAdapter(imageAdapter);
//                imageAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT).show();
            }
        }

        private List<Map<String, Object>> getData1() {
            List<Map<String, Object>> retlist = new ArrayList<Map<String, Object>>();
            String url1 = "http://imgstore04.cdn.sogou.com/app/a/100520024/877e990117d6a7ebc68f46c5e76fc47a";
            String url2 = "https://image1.guazistatic.com/qn1701260942205f87f5a896374385552f2107b2038d69.jpg?imageView2/1/w/600/h/400/q/88";
            Map<String, Object> map;
            for (CarInfor carin : carInfors) {
                map = new HashMap<String, Object>();
                map.put("title", carin.getcName());
                map.put("info", carin.getcPrice());
                map.put("place", carin.getcCity()+"/"+carin.getcLicensrtime()+"/"+carin.getcDistance());
                map.put("img", carin.getpUrl());
                map.put("carin",carin);
                retlist.add(map);
            }
            return retlist;
        }
    }
}
