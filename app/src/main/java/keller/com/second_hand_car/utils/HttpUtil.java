package keller.com.second_hand_car.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by keller on 2017/4/24.
 */

public class HttpUtil {

    /**
     * 获取网络状态
     *
     * @param context 上下文
     * @return 联通状态
     */
    public static boolean isNetConn(Context context) {
        //获取网络连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        //获取活跃状态的网络信息对象
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            return info.isConnected();  //返回是否链接
        } else {
            return false;
        }
    }


    /**
     * 获取网络上下载下来的数据的byte数组
     *
     * @param urlPath 网络URL路径
     * @return 网络上获取的json字符串的byte数组形式
     */
    public static byte[] downloadFromNet(Context con,String urlPath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        URL url = null;
        byte b[] = new byte[1024];
        try {
            url = new ReadProperties().getUrl(con,urlPath);
            Log.i("url",url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
//            conn.setRequestProperty("charset", "utf-8");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                int len;

                //注意这里：is.read(b) 中的b数组一定要写，不然读取的数据不对
                while ((len = is.read(b)) != -1) {
                    baos.write(b, 0, len);
                    baos.flush();
                }
                b=baos.toByteArray();
                is.close();
                baos.close();
                return b;
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        return b;
    }
}
