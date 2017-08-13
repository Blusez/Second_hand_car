package keller.com.second_hand_car.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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


/*
 * @创建者     Administrator
 * @创建时间   2015/8/7 11:08
 * @描述	      联系人的Fragment
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}$
 */
public class BuyFragment extends Fragment implements XListView.IXListViewListener {

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
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private EditText search_et_input;
    private Button search_btn_back;
    private  String  key = "dauft";


    //排序
    private String order_dauft = "dauft";
    private String order_brand = "dauft";
    private String order_price = "dauft";


    public BuyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
//        getData();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //  initView();
    }

    private void init() {
        //没有影响
        geneItems();
//        getData();
    }

    private void initView() {
        mListView = (XListView) getActivity().findViewById(R.id.xListView1);
        search_et_input = (EditText) getActivity().findViewById(R.id.search_et_input);
        search_btn_back = (Button) getActivity().findViewById(R.id.search_btn_back);
        spinner2 = (Spinner) getActivity().findViewById(R.id.spinner2);
        spinner3 = (Spinner) getActivity().findViewById(R.id.spinner3);
        spinner4 = (Spinner) getActivity().findViewById(R.id.spinner4);

        mListView.setPullLoadEnable(true);

        if (HttpUtil.isNetConn(getActivity())) {
            new BuyFragment.LoadCarAsyncTask(getActivity(), mListView).execute();
        } else {
            //必须直接添加数据的函数
            mAdapter = new ImageAdapter(getActivity(), getData());
            mListView.setAdapter(mAdapter);
//		mListView.setPullLoadEnable(false);
//		mListView.setPullRefreshEnable(false);

        }
        mListView.setXListViewListener(this);
        mHandler = new Handler();

        search_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = search_et_input.getText().toString().trim();
                onRefresh();
            }
        });

        spinner2.setOnItemSelectedListener(
                new Spinner.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        String data = (String) spinner2.getItemAtPosition(position);//从spinner中获取被选择的数据
                        order_dauft = data;
                        onRefresh();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        spinner3.setOnItemSelectedListener(
                new Spinner.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        String data = (String) spinner3.getItemAtPosition(position);//从spinner中获取被选择的数据
                        order_brand = data;
                        onRefresh();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        spinner4.setOnItemSelectedListener(
                new Spinner.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        String data = (String) spinner4.getItemAtPosition(position);//从spinner中获取被选择的数据
                        order_price = data;
                        onRefresh();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        initListener();
        super.onActivityCreated(savedInstanceState);
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
                refreshCnt = 0;
                if (HttpUtil.isNetConn(getActivity())) {
                    new BuyFragment.LoadCarAsyncTask(getActivity(), mListView).execute();
                } else {
                    //必须直接添加数据的函数
                    mAdapter = new ImageAdapter(getActivity(), getData());
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
                new BuyFragment.LoadCarAsyncTask(getActivity(), mListView).execute();
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
//            try {
//                order_dauft= URLEncoder.encode(order_dauft,"utf-8");
//                order_price=URLEncoder.encode(order_price,"utf-8");
//                order_brand=URLEncoder.encode(order_brand,"utf-8");
//                key=URLEncoder.encode(key,"utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            if (HttpUtil.isNetConn(mContext)) {
                String url = "CarInforController/selectByLimt.do?start=" + start + "&limit=" + limit + "&order_dauft=" + order_dauft+"&order_price="+order_price+"&order_brand="+order_brand+"&key="+key;
                Log.i("url:",url);
                byte[] b = HttpUtil.downloadFromNet(getActivity(), url);  //可变参数params当成一个数组使用，其中的params[0]就是我们传递过来的参数
                String jsonString = new String(b).trim();
                Log.d("Tag", jsonString);

                try {
                    carInfors.clear();
                    if (jsonString != null && !jsonString.equals("no") && !jsonString.equals("")) {
                        if (!jsonString.equals("no")) {
                            Log.d("jsonString", jsonString);
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
                    imageAdapter = new ImageAdapter(getActivity(), list);
                    lv.setAdapter(imageAdapter);
                } else {
                    list.addAll(getData1());
                    imageAdapter.notifyDataSetChanged();
                }
//                lv.setAdapter(new ImageAdapter(getActivity(), getData1()));
            } else {
                if (imageAdapter == null) {
                    list = getData1();
                    imageAdapter = new ImageAdapter(getActivity(), list);
                    lv.setAdapter(imageAdapter);
                } else {
                    list.addAll(getData1());
                    imageAdapter.notifyDataSetChanged();
                }
//                list.addAll(getData());
//                imageAdapter = new ImageAdapter(getActivity(), list);
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
                map.put("place", carin.getcCity() + "/" + carin.getcLicensrtime() + "/" + carin.getcDistance());
                map.put("img", carin.getpUrl());
                map.put("carin", carin);
                retlist.add(map);
            }
            return retlist;
        }
    }
}
