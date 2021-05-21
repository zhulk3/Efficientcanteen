package com.longkai.canteen.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.longkai.canteen.R;
import com.longkai.canteen.db.OrderDetail;
import com.longkai.canteen.db.OrderInfo;

public class OrderListAdapter extends BaseExpandableListAdapter {
  private static final String TAG = "OrderListAdapter";
  List<OrderInfo> mGroupList;//一级List
  List<List<OrderDetail>> mChildList;//二级List 注意!这里是List里面套了一个List<String>,实际项目你可以写一个pojo类来管理2层数据


  public OrderListAdapter(List<OrderInfo> groupList,
      List<List<OrderDetail>> childList) {
    mGroupList = groupList;
    mChildList = childList;
  }

  @Override
  public int getGroupCount() {//返回第一级List长度
    return mGroupList.size();
  }

  @Override
  public int getChildrenCount(int groupPosition) {//返回指定groupPosition的第二级List长度
    return mChildList.size();
  }

  @Override
  public Object getGroup(int groupPosition) {//返回一级List里的内容
    return mGroupList.get(groupPosition);
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {//返回二级List的内容
    return mChildList.get(childPosition);
  }

  @Override
  public long getGroupId(int groupPosition) {//返回一级View的id 保证id唯一
    return groupPosition;
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {//返回二级View的id 保证id唯一
    return groupPosition + childPosition;
  }

  /**
   * 指示在对基础数据进行更改时子ID和组ID是否稳定
   *
   * @return
   */
  @Override
  public boolean hasStableIds() {
    return true;
  }

  /**
   * 返回一级父View
   */
  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
      ViewGroup parent) {
    OrderInfo orderInfo = (OrderInfo) getGroup(groupPosition);

    View view;
    ViewHolder viewHolder;
    if (convertView == null) {
      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_info_item_layout,
          parent, false);
      viewHolder = new ViewHolder();
      viewHolder.id = view.findViewById(R.id.id);
      viewHolder.date = view.findViewById(R.id.order_date);
      viewHolder.extra = view.findViewById(R.id.extra);
      viewHolder.state = view.findViewById(R.id.state);
      view.setTag(viewHolder);
    } else {
      view = convertView;
      viewHolder = (OrderListAdapter.ViewHolder) view.getTag();
    }
    viewHolder.id.setText(orderInfo.getId() + "");
    Double price = orderInfo.getOrderprice();
    viewHolder.extra.setText(price.toString());
    viewHolder.state.setText(orderInfo.getStatus());
    viewHolder.date.setText(orderInfo.getOrdertime());

    return view;
  }

  /**
   * 返回二级子View
   */
  @Override
  public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {
    List<OrderDetail> orderDetailList = (List<OrderDetail>)getChild(groupPosition, childPosition);
    OrderDetail orderDetail = orderDetailList.get(0);
    Log.d(TAG, "getChildView: "+orderDetail);

    View view;
    ChildViewHolder childViewHolder;
    if (convertView == null) {
      view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item_layout,
          parent, false);
      childViewHolder = new ChildViewHolder();
      childViewHolder.dishName = view.findViewById(R.id.dish_name);
      childViewHolder.dishType = view.findViewById(R.id.dish_type);
      childViewHolder.canteen = view.findViewById(R.id.canteen);
      childViewHolder.num = view.findViewById(R.id.num);
      childViewHolder.price = view.findViewById(R.id.dish_price);
      childViewHolder.totalPrice = view.findViewById(R.id.total_price);
      view.setTag(childViewHolder);
    } else {
      view = convertView;
      childViewHolder = (OrderListAdapter.ChildViewHolder) view.getTag();
    }
    childViewHolder.dishName.setText(orderDetail.getDishName());
    childViewHolder.dishType.setText(orderDetail.getDishType());
    childViewHolder.canteen.setText(orderDetail.getWhichCanteen());
    Double price = orderDetail.getPrice();
    childViewHolder.price.setText(price.toString());
    childViewHolder.num.setText(orderDetail.getNum()+"");
    Double totalPrice = orderDetail.getTotalprice();
    childViewHolder.totalPrice.setText(totalPrice.toString());
    return view;
  }

  /**
   * 指定位置的子项是否可选
   */
  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

  class ViewHolder {
    TextView id;
    TextView date;
    TextView extra;
    TextView state;
  }

  class ChildViewHolder {
    TextView dishName;
    TextView dishType;
    TextView canteen;
    TextView price;
    TextView num;
    TextView totalPrice;
  }
}
