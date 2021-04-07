package com.longkai.canteen.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.longkai.canteen.LoginActivity;
import com.longkai.canteen.R;
import com.longkai.canteen.db.Canteen;
import com.longkai.canteen.db.UserInfo;
import com.longkai.canteen.util.HttpUtil;
import com.longkai.canteen.util.Utility;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProfileFragment extends BaseFragment {
  private static final String TAG = "ProfileFragment";
  private TextView mUserName;
  private UserInfo mUserInfo;
  private TextView school;
  private TextView sno;
  private TextView name;
  private TextView regDate;
  private TextView sex;
  private TextView email;
  private TextView address;
  private TextView balance;

  public static BaseFragment newInstance(String title) {
    Bundle args = new Bundle();
    args.putString(ARGS_TITLE, title);
    BaseFragment fragment = new ProfileFragment();
    fragment.setArguments(args);
    return fragment;
  }
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.profile_layout, container, false);
    school=view.findViewById(R.id.nav_school);
    sno=view.findViewById(R.id.nav_sno);
    name=view.findViewById(R.id.nav_name);
    regDate=view.findViewById(R.id.nav_date);
    sex=view.findViewById(R.id.nav_sex);
    email=view.findViewById(R.id.nav_mail);
    address=view.findViewById(R.id.nav_address);
    balance=view.findViewById(R.id.nav_balance);
    mUserName=view.findViewById(R.id.userName);
    return view;
  }
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    readUserInfoFromLocal();

  }

  private void queryUserInfo(String url) {
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
        Log.d(TAG, "onFailure: ");
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String responseText = response.body().string();
        System.out.println(responseText+"userinfo");
        boolean result = false;
        result = Utility.handleUserInfoResponse(responseText);
        if (result) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              readUserInfoFromLocal();
            }
          }).start();

        }
        Log.d(TAG, "onResponse: " + responseText);
      }
    });
  }

  private void readUserInfoFromLocal() {
    mUserInfo = LitePal.findLast(UserInfo.class);
    Log.d(TAG, "readUserInfoFromLocal: "+mUserInfo);
    if(mUserInfo!=null){
      getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          school.setText(mUserInfo.getSchool());
          sno.setText(mUserInfo.getSno());
          sex.setText(mUserInfo.getSex());
          name.setText(mUserInfo.getRealName());
          balance.setText(mUserInfo.getBalance());
          address.setText(mUserInfo.getAddress());
          email.setText(mUserInfo.getEmail());
          mUserName.setText(mUserInfo.getUserName());
          regDate.setText(mUserInfo.getRegDate());
          LoginActivity.id=mUserInfo.getId();
        }
      });
    } else {
      String name = LoginActivity.account;
      String password = LoginActivity.password;
      String url =
          "http://172.26.10.104:8081/canteen-ordering-system/userinfo/getUserInfo?name=" + name +
              "&password=" + password;
      queryUserInfo(url);
    }
  }
}
