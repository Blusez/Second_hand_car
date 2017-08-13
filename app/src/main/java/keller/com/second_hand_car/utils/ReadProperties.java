package keller.com.second_hand_car.utils;


import android.content.Context;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


/**
 * Created by keller on 2017/4/23.
 */

public class ReadProperties {


    public URL getUrl(Context c,String path) throws MalformedURLException {
        Properties pro = new Properties();
        try {
            pro.load(c.getAssets().open("connection.properties"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String con = pro.getProperty("url");
        URL url=new URL(con+path);
        return url;
    }


}
