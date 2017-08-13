package keller.com.second_hand_car.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import keller.com.second_hand_car.R;
import keller.com.second_hand_car.utils.ReadProperties;

public class RegisterActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    private EditText e_name;
    private EditText e_pwd;
    private EditText e_pwd1;
    private SharedPreferences sp;
    private String name ;
    private String pwd ;
    private String pwd1 ;
    private String rtnresult;
    private String rtnpwd;
    private String rtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init() {
        e_name = (EditText)findViewById(R.id.E_name);
        e_pwd = (EditText) findViewById(R.id.E_pwd);
        e_pwd1 = (EditText) findViewById(R.id.E_pwd1);
    }
    public void login(View view) {
        name = e_name.getText().toString().trim();
        pwd = e_pwd.getText().toString().trim();
        pwd1 = e_pwd1.getText().toString().trim();
        if (name == null || "".equals(name)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd == null || "".equals(pwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(pwd1)) {
            Toast.makeText(this, "两次输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        new RegisterAsyncTask().execute();
    }
    /**
     * 记住用户名和密码
     */
    private void remeberUser() {
        // TODO Auto-generated method stub
        sp=getSharedPreferences("userinfo", 0);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("username", name);
        editor.putString("userpwd", pwd);
        editor.commit();
    }

    class RegisterAsyncTask extends AsyncTask<String, Integer,String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialog= new ProgressDialog(RegisterActivity.this);
            dialog.setMessage("正在注册中");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url=new ReadProperties().getUrl(RegisterActivity.this,"UserController/register.do");
//                URL url=new URL("http://10.0.2.2:8080/Second_hand_car/UserController/register.do");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("charset", "utf-8");
                OutputStream out=connection.getOutputStream();
                out.write(("name="+name+"&pwd="+pwd).getBytes());
                out.close();
                InputStream inputStream=connection.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer sbBuffer = new StringBuffer();
                //rtn=reader.readLine();
                String temp = "";
                while (null != (temp = reader.readLine())) {
                    sbBuffer.append(temp);
                }
                reader.close();
                try {
                    JSONObject jsonObject = new JSONObject(sbBuffer.toString());
                    rtnresult = jsonObject.getString("result");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return rtn;
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            dialog.dismiss();
            if(rtnresult.equals("success")){
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra("username", name);
                startActivity(intent);
                finish();
            }else if(rtnresult.equals("fail")) {
                Toast.makeText(RegisterActivity.this, "注册失败，请重新注册", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(RegisterActivity.this, "该用户已存在请重新注册", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
