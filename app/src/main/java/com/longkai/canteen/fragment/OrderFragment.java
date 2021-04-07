package com.longkai.canteen.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.longkai.canteen.LoginActivity;
import com.longkai.canteen.R;
import com.longkai.canteen.adapter.OrderAdapter;
import com.longkai.canteen.db.OrderDetail;
import com.longkai.canteen.db.OrderInfo;
import com.longkai.canteen.db.UserInfo;
import com.longkai.canteen.util.HttpUtil;
import com.longkai.canteen.util.Utility;
import com.longkai.canteen.view.TabView;

import butterknife.BindArray;
import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class OrderFragment extends BaseFragment {
  private static final String TAG = "OrderFragment";
  private static List<OrderInfo>orderInfoList;
  private static List<List<OrderDetail>> orderDetailList=new ArrayList<>();
  private List<OrderDetail>tempOrderDetailList;


  public static BaseFragment newInstance(String title) {
    Bundle args = new Bundle();
    args.putString(ARGS_TITLE, title);
    BaseFragment fragment = new OrderFragment();
    fragment.setArguments(args);
    return fragment;
  }

  private ViewPager pager;
  private OrderAdapter fragmentAdapter;
  private List<TabFragment> fragmentList;
  private TabLayout tabLayout;
  private List<String> mTitles;
  private String[] title = {"待处理", "处理中", "待取餐", "已完成"};

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.order_layout, container, false);
    pager = view.findViewById(R.id.page);
    tabLayout = view.findViewById(R.id.tab_layout);
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
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    fragmentList = new ArrayList<>();
    mTitles = new ArrayList<>();
    for (int i = 0; i < title.length; i++) {
      mTitles.add(title[i]);
    }
    fragmentList.add(new UntreatedFragment(title[0]));
    fragmentList.add(new TreatingFragment(title[1]));
    fragmentList.add(new ToBeTakenFragment(title[2]));
    fragmentList.add(new FinishedFragment(title[3]));


    fragmentAdapter =
        new OrderAdapter(getActivity().getSupportFragmentManager(), fragmentList, mTitles);
    pager.setAdapter(fragmentAdapter);
    tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
    readOrderInfoFromLocal();
  }

  private void queryOrderInfo(String url) {
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
        Log.d(TAG, "onFailure: ");
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String responseText = response.body().string();
        Log.d(TAG, "onResponse: " + responseText);
        boolean result = false;
        result = Utility.handleOrderInfo(responseText);
        if (result) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              readOrderInfoFromLocal();
            }
          }).start();

        }
        Log.d(TAG, "onResponse: " + responseText);
      }
    });
  }


  private void readOrderInfoFromLocal() {
   orderInfoList = LitePal.findAll(OrderInfo.class);
    Log.d(TAG, "readOrderInfoFromLocal: " +orderInfoList);
    if (orderInfoList != null &&orderInfoList.size() > 0) {
      readOrderDetailFromLocal();
    } else {
      String url =
          "http://172.26.10.104:8081/canteen-ordering-system/orderinfo/findOrderInfo?uid=" +
              LoginActivity.id;
      queryOrderInfo(url);
    }
  }

  private void queryOrderDetail(String url) {
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
        Log.d(TAG, "onFailure: ");
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String responseText = response.body().string();
        Log.d(TAG, "onResponse: " + responseText);
        boolean result = false;
        result = Utility.handleOrderDetailResponse(responseText);
        if (result) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              readOrderDetailFromLocal();
            }
          }).start();

        }
        Log.d(TAG, "onResponse: " + responseText);
      }
    });
  }

  private void readOrderDetailFromLocal() {
    List<OrderDetail>tempOrderDetailList = LitePal.findAll(OrderDetail.class);
    if (tempOrderDetailList != null && tempOrderDetailList.size() > 0) {
      Log.d(TAG, "readOrderDetailFromLocal: " + tempOrderDetailList);
      for (OrderDetail orderDetail:tempOrderDetailList){
        List<OrderDetail>detailList = new ArrayList<>(1);
        detailList.add(orderDetail);
        orderDetailList.add(detailList);
      }

    } else {
      List<Integer> oids = new ArrayList<>();
      for (OrderInfo orderInfo :orderInfoList) {
        oids.add(orderInfo.getId());
      }
      if (oids.size() > 0) {
        for (int oid : oids) {
          String url =
              "http://172.26.10.104:8081/canteen-ordering-system/orderinfo/getOrderDetails?oid=" +
                  oid;
          queryOrderDetail(url);
        }
      }


    }
  }

  public static List<OrderInfo> getOrderInfoList() {
    return orderInfoList;
  }

  public static List<List<OrderDetail>> getOrderDetailList() {
    return orderDetailList;
  }
}
