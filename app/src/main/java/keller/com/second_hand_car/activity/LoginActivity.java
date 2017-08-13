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


public class LoginActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    private EditText e_name;
    private EditText e_pwd;
    private SharedPreferences sp;
    private String name ;
    private String pwd ;
    private String rtnname;
    private String rtnpwd;
    private String rtn;
    private String rtuid;
    private String rtlogid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
    }
    public void login(View view) {
        name = e_name.getText().toString().trim();
        pwd = e_pwd.getText().toString().trim();
        if (name == null || "".equals(name)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd == null || "".equals(pwd)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        new LoginAsyncTask().execute();
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
        editor.putString("uid", rtuid);
        editor.putString("userlogin", rtlogid);
        editor.commit();
    }

    class LoginAsyncTask extends AsyncTask<String, Integer,String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            dialog= new ProgressDialog(LoginActivity.this);
            dialog.setMessage("正在登录中");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url=new ReadProperties().getUrl(LoginActivity.this,"UserController/login.do");
//                URL url=new URL("http://192.168.1.203:8080/Second_hand_car/UserController/login.do");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("charset", "utf-8");
                OutputStream out=connection.getOutputStream();
                out.write(("name="+name+"&pwd="+pwd+"&identype=1").getBytes());
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
                    rtuid= jsonObject.getString("uid");
                    rtnname= jsonObject.getString("name");
                    rtnpwd= jsonObject.getString("pwd");
                    rtlogid= jsonObject.getString("logid");

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
            if(rtnname.equals(name)&&rtnpwd.equals(pwd)){
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                remeberUser();
                intent.putExtra("uid", rtuid);
                intent.putExtra("username", rtnname);
                intent.putExtra("userpwd", rtnpwd);
                intent.putExtra("userlogin", rtlogid);
                startActivity(intent);
                finish();
            }else if(rtnname.equals(name)||!rtnpwd.equals(pwd)) {
                Toast.makeText(LoginActivity.this, "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this, "该用户不存在请先注册", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
