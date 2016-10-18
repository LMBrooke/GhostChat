package com.example.ghostchat.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ghostchat.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;


/**
 * Created by Administrator on 2016/10/8.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    EditText et_userName;
    EditText et_userPwd;
    Button btn_register;
    Button btn_login;

    String userName;
    String userPwd;

    private boolean progressShow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        initView();
        setListener();
    }

    private void setView() {
        setContentView(R.layout.activity_login);
    }

    private void initView() {
        et_userName = (EditText) findViewById(R.id.et_userNmae);
        et_userPwd = (EditText) findViewById(R.id.et_userPwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
    }

    private void setListener() {
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        userName = et_userName.getText().toString().trim();
        userPwd = et_userPwd.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_login:
                userLogin(userName,userPwd);
                break;
            case R.id.btn_register:
                userRegister(userName,userPwd);
                break;
            default:
                break;
        }
    }

    //账号123123  密码123
    private void userLogin(final String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "用户名为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"密码为空", Toast.LENGTH_SHORT).show();
            return;
        }
        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage("登录中");
        pd.show();
        EMClient.getInstance().login(userName,password,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.i(TAG, "登录聊天服务器成功！");
                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                //--跳转到MainActivity
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("username",userName);
                startActivity(intent);
                LoginActivity.this.finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.i(TAG, "登录聊天服务器失败！");
            }
        });
    }

    private void userRegister(final String username, final String pwd) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        intent.putExtra("userName",username);
        intent.putExtra("password",pwd);
        startActivity(intent);

        /*new Thread(){
            @Override
            public void run() {
                //注册失败会抛出HyphenateException
                try {
                    EMClient.getInstance().createAccount(username, pwd);//同步方法
                    Log.i(TAG,"chengg");
                } catch (HyphenateException e) {
                    Log.i(TAG,"shibai");
                    e.printStackTrace();
//                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        }.start();*/

    }

}
