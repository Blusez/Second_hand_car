package keller.com.second_hand_car.Adapter.ImageAdapter;

/**
 * Created by keller on 2017/4/23.
 * 加载网络图片
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import keller.com.second_hand_car.R;
import keller.com.second_hand_car.activity.CarinforActivity;
import keller.com.second_hand_car.model.CarInfor;
import keller.com.second_hand_car.utils.HttpUtil;
import keller.com.second_hand_car.utils.ReadProperties;

/**
 *
 * @author LeoLeoHan
 *
 */
public class ImageAdapter extends BaseAdapter {
    // 要显示的数据的集合
    private List<Map<String, Object>> data;
    // 接受上下文
    private Context context;
    // 声明内部类对象
    private ViewHolder viewHolder;
    private SharedPreferences sp;
    private String uid;
    /**
     * 构造函数
     *
     * @param context
     * @param data
     */
    public ImageAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
    }

    // 返回的总个数
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    // 返回每个条目对应的数据
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    // 返回的id
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    // 返回这个条目对应的控件对象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 判断当前条目是否为null
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.list_item, null);
            viewHolder.iv_image = (ImageView) convertView
                    .findViewById(R.id.img);
            viewHolder.tv_title = (TextView) convertView
                    .findViewById(R.id.title);
            viewHolder.tv_place = (TextView) convertView
                    .findViewById(R.id.place);
            viewHolder.tv_info = (TextView) convertView
                    .findViewById(R.id.info);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 获取List集合中的map对象
        Map<String, Object> map = data.get(position);
        final CarInfor car_infor = (CarInfor) map.get("carin");
        // 获取图片的url路径
        String title = car_infor.getcName().split(" ")[0];
        String place = map.get("place").toString();
        String info = map.get("info").toString();
        String img = car_infor.getpUrl().split(",")[0].trim();
        Log.i("img",img);
        Log.i("place",place);
        Log.i("info",info);

        // 这里调用了图片加载工具类的setImage方法将图片直接显示到控件上


//        GetImageByUrl getImageByUrl = new GetImageByUrl();
//        getImageByUrl.setImage(viewHolder.iv_image, img);

        //使用Glide加载图片
        Glide
                .with(context)
                .load(img)
                .into(viewHolder.iv_image);
        sp=context.getSharedPreferences("userinfo", 0);
        uid = sp.getString("uid","");

        viewHolder.tv_title.setText(title);
        viewHolder.tv_info.setText(info);
        viewHolder.tv_place.setText(place);
        final int arg = position;


        //点击事件
        convertView.setOnClickListener(new ImageView.OnClickListener(){
            public void onClick(View v) {
                //历史记录
                new InsertTask(context,car_infor).execute();
                Intent intent = new Intent(context, CarinforActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("car_infor", car_infor);
                intent.putExtras(bundle);
                context.startActivity(intent);

//                Toast.makeText(context,"点击了第"+car_infor.getcName()+"个",Toast.LENGTH_SHORT).show();
//
            }
        });

        return convertView;
    }

    /**
     * 内部类 记录单个条目中所有属性
     *
     * @author LeoLeoHan
     *
     */
    class ViewHolder {
        public ImageView iv_image;
        public TextView tv_title;
        public TextView tv_place;
        public TextView tv_info;
    }


    class InsertTask extends AsyncTask<String, Integer, String> {
        private boolean flag = false;
        private Context context;
        private CarInfor carInfor;
        public InsertTask(Context context,CarInfor carInfor){
            this.context= context;
            this.carInfor =carInfor;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if (HttpUtil.isNetConn(context)) {
                String url = "CarSanHisController/addhis.do?cid=" + carInfor.getcId() + "&status=1&uid="+uid;
                try {
                    URL url1=new ReadProperties().getUrl(context,url);
                    HttpURLConnection connection=(HttpURLConnection) url1.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("charset", "utf-8");
                    OutputStream out=connection.getOutputStream();
                    out.close();
                    InputStream inputStream=connection.getInputStream();

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

        }
    }

}