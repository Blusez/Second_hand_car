package keller.com.second_hand_car.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import keller.com.second_hand_car.R;
import keller.com.second_hand_car.view.XListView;

public class HomeActivity extends AppCompatActivity implements XListView.IXListViewListener{

    private XListView mListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        geneItems();
        mListView = (XListView) findViewById(R.id.xListView3);
        mListView.setPullLoadEnable(true);
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        mListView.setAdapter(mAdapter);
//		mListView.setPullLoadEnable(false);
//		mListView.setPullRefreshEnable(false);
        mListView.setXListViewListener(this);
        mHandler = new Handler();
    }

    private void geneItems() {
        for (int i = 0; i != 5; ++i) {
            items.add("refresh cnt " + (++start));
        }
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = ++refreshCnt;
                items.clear();
                geneItems();
                // mAdapter.notifyDataSetChanged();
                mAdapter = new ArrayAdapter<String>(HomeActivity.this, R.layout.list_item, items);
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }


    public void net(){

//        File file = new File(textFileName, ContentType.DEFAULT_BINARY);
//        HttpPost post = new HttpPost("http://echo.200please.com");
//        FileBody fileBody = new FileBody(file);
//        StringBody stringBody1 = new StringBody("Message 1", ContentType.MULTIPART_FORM_DATA);
//        StringBody stringBody2 = new StringBody("Message 2", ContentType.MULTIPART_FORM_DATA);
////
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        builder.addPart("upfile", fileBody);
//        builder.addPart("text1", stringBody1);
//        builder.addPart("text2", stringBody2);
//        HttpEntity entity = builder.build();
////
//        post.setEntity(entity);
//        HttpResponse response = client.execute(post);
    }
}
