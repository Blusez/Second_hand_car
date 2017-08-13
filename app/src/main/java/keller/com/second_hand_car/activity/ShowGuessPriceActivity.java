package keller.com.second_hand_car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DecimalFormat;

import keller.com.second_hand_car.R;

public class ShowGuessPriceActivity extends AppCompatActivity {

    private TextView sg_title;
    private TextView sgp_licetine;
    private TextView shp_price1;
    private TextView sgp_price2;
    private TextView sgo_grade;
    private String sg_title1;
    private String sgp_licetine1;
    private String shp_price11;
    private String sgp_price21;
    private String sgo_grade1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_guess_price);
        init();
        initData();
    }

    private void init() {
        sg_title = (TextView) findViewById(R.id.sg_title);
        sgp_licetine = (TextView) findViewById(R.id.sgp_licetine);
        shp_price1 = (TextView) findViewById(R.id.shp_price1);
        sgp_price2 = (TextView) findViewById(R.id.sgp_price2);
        sgo_grade = (TextView) findViewById(R.id.sgo_grade);
    }

    private void initData() {
        Intent intent = getIntent();
//        sg_title1 = intent.getStringExtra("");
//        intent.putExtra("litime1",litime1);
//        intent.putExtra("mBrand1",mBrand1);
//        intent.putExtra("m_city1",m_city1);
//        intent.putExtra("mdistance1",mdistance1);
//        intent.putExtra("mgrade1",mgrade1);

        DecimalFormat dcmFmt = new DecimalFormat("0.00");
        float aa= (float) (20+Math.random()*49);
        String format = dcmFmt.format(aa);
        String format1 = dcmFmt.format(aa-1.56);
        sg_title.setText(intent.getStringExtra("mBrand1"));
        sgp_licetine.setText(intent.getStringExtra("litime1")+"/"+intent.getStringExtra("mdistance1"));
        shp_price1.setText(format);
        sgp_price2.setText(format1);
        sgo_grade.setText(intent.getStringExtra("mgrade1"));

    }
}
