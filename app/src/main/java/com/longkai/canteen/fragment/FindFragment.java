package com.longkai.canteen.fragment;

import java.io.IOException;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.longkai.canteen.LoginActivity;
import com.longkai.canteen.R;
import com.longkai.canteen.adapter.AdminAdapter;
import com.longkai.canteen.adapter.DishAdapter;
import com.longkai.canteen.db.AdminInfo;
import com.longkai.canteen.db.UserInfo;
import com.longkai.canteen.util.HttpUtil;
import com.longkai.canteen.util.Utility;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindFragment extends BaseFragment {
  private static final String TAG = "FindFragment";
  private List<AdminInfo>mAdminInfoList;
  private ListView mListView;

  public static BaseFragment newInstance(String title) {
    Bundle args = new Bundle();
    args.putString(ARGS_TITLE, title);
    BaseFragment fragment = new FindFragment();
    fragment.setArguments(args);
    return fragment;
  }
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.find_layout,container,false);
    mListView=view.findViewById(R.id.admin_list);
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
    readAdminInfoFromLocal();
    Log.d(TAG, "onActivityCreated: "+mAdminInfoList);
    AdminAdapter adminAdapter = new AdminAdapter(getActivity(),R.layout.admin_item_layout,mAdminInfoList);
    mListView.setAdapter(adminAdapter);
  }
  private void queryAdminInfo(String url) {
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
        Log.d(TAG, "onFailure: ");
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String responseText = response.body().string();
        boolean result = false;
        result = Utility.handleAdminInfo(responseText);
        if (result) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              readAdminInfoFromLocal();
            }
          }).start();

        }
        Log.d(TAG, "onResponse: " + responseText);
      }
    });
  }

  private void readAdminInfoFromLocal() {
    mAdminInfoList=LitePal.findAll(AdminInfo.class);
    Log.d(TAG, "readAdminFromLocal: "+mAdminInfoList);
    if(mAdminInfoList!=null&&mAdminInfoList.size()>0){
      getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {

        }
      });
    } else {

      String url =
          "http://172.26.61.134:8081/canteen-ordering-system/admininfo/admininfoList";
      queryAdminInfo(url);
    }
  }
}
