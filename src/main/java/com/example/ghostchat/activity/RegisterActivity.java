package com.example.ghostchat.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghostchat.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by Administrator on 2016/10/8.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{

    EditText et_userName;
    EditText et_userPwd;
    EditText et_surePwd;
    Button btn_OK;
    TextView title_text;
    TextView title_back;
    TextView title_exit;

    String userName;
    String userPwd;
    String surePwd;
    private String TAG = "RegisterActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setView();
        initView();
        setListener();
    }

    private void init() {
        userName = getIntent().getStringExtra("userName");
        userPwd = getIntent().getStringExtra("password");
    }

    private void setListener() {
        title_exit.setOnClickListener(this);
        title_back.setOnClickListener(this);
        btn_OK.setOnClickListener(this);
    }

    private void initView() {
        et_surePwd = (EditText) findViewById(R.id.et_surePwd);
        et_userName = (EditText) findViewById(R.id.et_newName);
        et_userPwd = (EditText) findViewById(R.id.et_newPwd);
        btn_OK = (Button) findViewById(R.id.btn_OK);
        title_text = (TextView) findViewById(R.id.title_text);
        title_back = (TextView) findViewById(R.id.title_back);
        title_exit = (TextView) findViewById(R.id.title_edit);
        title_text.setText("注册");
        et_userName.setText(userName);
        et_userPwd.setText(userPwd);
        title_exit.setVisibility(View.INVISIBLE);
        title_back.setVisibility(View.INVISIBLE);
    }

    private void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_OK:
                success();
                break;
            default:
                break;
        }
    }

    private void success() {
        userName = et_userName.getText().toString().trim();
        userPwd = et_userPwd.getText().toString().trim();
        surePwd = et_surePwd.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPwd)) {
            Toast.makeText(this,"密码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(surePwd)){
            Toast.makeText(RegisterActivity.this,"第二次密码为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!userPwd.equals(surePwd)){
            Toast.makeText(RegisterActivity.this,"两次密码不相同",Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("注册");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                //注册失败会抛出HyphenateException
                try {
                    EMClient.getInstance().createAccount(userName, userPwd);//同步方法
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (!RegisterActivity.this.isFinishing())
                                pd.dismiss();
                            // 保存用户名
                            Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    Log.i(TAG,"chengg");
                } catch (HyphenateException e) {
                    Log.i(TAG,"shibai");
                    pd.dismiss();
//                    e.printStackTrace();
//                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        }.start();

       /* EMClient.getInstance().login(userName,userPwd,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.i(TAG, "登录聊天服务器成功！");
                //--跳转到MainActivity
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                RegisterActivity.this.finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.i(TAG, "登录聊天服务器失败！");
            }
        });*/
    }

}
