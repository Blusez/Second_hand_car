package keller.com.second_hand_car.http;

import android.os.AsyncTask;

/**
 * Created by keller on 2017/4/27.
 */

public class AsyncTaskUtil {

    public static void doAsync(final AsyncCallBack callBack) {
        if (callBack == null) {
            return;
        }
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                callBack.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                callBack.doInBackground();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                callBack.onPostExecute();
            }
        }.execute();
    }

}