package com.longkai.canteen;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.longkai.canteen.db.LoginStatus;
import com.longkai.canteen.util.HttpUtil;
import com.longkai.canteen.util.Utility;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {


  private EditText mAccount;
  private EditText mPassword;
  private Button mButton;
  private SharedPreferences.Editor mEditor;
  private SharedPreferences mSharedPreferences;
  private Boolean isRememberPassword;
  private CheckBox checkBox;
  public static String account;
  public static String password;
  public static int id;
  private static final String TAG = "LoginActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mAccount = (EditText) findViewById(R.id.account);
    mPassword = (EditText) findViewById(R.id.password);
    mButton = (Button) findViewById(R.id.login);
    checkBox = (CheckBox) findViewById(R.id.remember_password);
    mEditor = getSharedPreferences("AccountAndPassword", MODE_PRIVATE).edit();
    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        account = mAccount.getText().toString();
        password = mPassword.getText().toString();
        String url =
            "http://172.26.10.104:8081/canteen-ordering-system/userinfo/login?name=" + account +
                "&password=" + password;
        verify(url);

      }
    });

    mSharedPreferences = getSharedPreferences("AccountAndPassword", MODE_PRIVATE);
    isRememberPassword = mSharedPreferences.getBoolean("isRememberPassword", false);
    if (isRememberPassword) {
      String account = mSharedPreferences.getString("account", "");
      String password = mSharedPreferences.getString("password", "");
      mAccount.setText(account);
      mPassword.setText(password);
      checkBox.setChecked(true);
    }
  }

  private void verify(String url) {
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
        Log.d(TAG, "onFailure: ");
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String responseText = response.body().string();
        LoginStatus loginStatus = Utility.handleLoginResponse(responseText);
        if (loginStatus != null && "true".equals(loginStatus.getSuccess()) &&
            "登录成功".equals(loginStatus.getMessage())) {
          if (checkBox.isChecked()) {
            mEditor.putString("account", account);
            mEditor.putString("password", password);
            mEditor.putBoolean("isRememberPassword", true);
          } else {
            mEditor.clear();
          }
          mEditor.apply();
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}