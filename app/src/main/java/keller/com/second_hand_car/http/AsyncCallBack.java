package keller.com.second_hand_car.http;


public abstract class AsyncCallBack {

    public void onPreExecute() {
    }

    public abstract void doInBackground();

    public abstract void onPostExecute();
}