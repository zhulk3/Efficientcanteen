package com.longkai.canteen.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.longkai.canteen.R;
import com.longkai.canteen.adapter.OrderListAdapter;
import com.longkai.canteen.db.OrderInfo;


public class TreatingFragment extends TabFragment {
  private ExpandableListView mExpandableListView;
  List<OrderInfo> mOrderInfoList = new ArrayList<>();

  //这个构造方法是便于各导航同时调用一个fragment
  public TreatingFragment(String title) {
    super(title);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_tab, container, false);
    mExpandableListView = view.findViewById(R.id.expandablelistview);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mOrderInfoList.clear();
    for (OrderInfo orderInfo : OrderFragment.getOrderInfoList()) {
      if (orderInfo.getStatus().equals(super.getTitle())) {
        mOrderInfoList.add(orderInfo);
      }
    }
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    OrderListAdapter orderListAdapter =
        new OrderListAdapter(mOrderInfoList, OrderFragment.getOrderDetailList());
    mExpandableListView.setAdapter(orderListAdapter);

    mExpandableListView
        .setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {//一级点击监听
          @Override
          public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
              long id) {

            //如果你处理了并且消费了点击返回true,这是一个基本的防止onTouch事件向下或者向上传递的返回机制
            return false;
          }
        });

    mExpandableListView
        .setOnChildClickListener(new ExpandableListView.OnChildClickListener() {//二级点击监听
          @Override
          public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
              int childPosition, long id) {

            //如果你处理了并且消费了点击返回true
            return false;
          }
        });
  }
}