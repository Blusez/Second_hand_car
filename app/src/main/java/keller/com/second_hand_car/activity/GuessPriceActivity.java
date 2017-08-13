package keller.com.second_hand_car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import keller.com.second_hand_car.R;

public class GuessPriceActivity extends AppCompatActivity {


    private EditText litime;
    private EditText mBrand;
    private EditText m_city;
    private EditText mdistance;
    private EditText mgrade;
    private String litime1;
    private String mBrand1;
    private String m_city1;
    private String mdistance1;
    private String mgrade1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_price);
        init();

    }

    private void init() {

        litime = (EditText) findViewById(R.id.litime);
        mBrand = (EditText) findViewById(R.id.mBrand);
        m_city = (EditText) findViewById(R.id.m_city);
        mdistance = (EditText) findViewById(R.id.mdistance);
        mgrade = (EditText) findViewById(R.id.mgrade);
    }

    private void initData() {
        litime1=litime.getText().toString().trim();
        mBrand1=mBrand.getText().toString().trim();
        m_city1=m_city.getText().toString().trim();
        mdistance1=mdistance.getText().toString().trim();
        mgrade1=mgrade.getText().toString().trim();
    }

    public void guessPrice(View view){
        initData();
        Intent intent = new Intent(this, ShowGuessPriceActivity.class);
        intent.putExtra("litime1",litime1);
        intent.putExtra("mBrand1",mBrand1);
        intent.putExtra("m_city1",m_city1);
        intent.putExtra("mdistance1",mdistance1);
        intent.putExtra("mgrade1",mgrade1);
        startActivityForResult(intent,1);
    }
    public void mback(View view){
        finish();
    }
}
