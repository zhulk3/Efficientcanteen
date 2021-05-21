package com.longkai.canteen.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longkai.canteen.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.longkai.canteen.db.AdminInfo;
import com.longkai.canteen.db.Dish;

public class AdminAdapter extends ArrayAdapter<AdminInfo> {
  private int resourceId;

  public AdminAdapter(Context context, int textViewResourceId,
      List<AdminInfo> objects) {
    super(context, textViewResourceId, objects);
    resourceId = textViewResourceId;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    AdminInfo adminInfo = getItem(position);
    View view;
    ViewHolder viewHolder;
    if (convertView == null) {
      view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.adminName = view.findViewById(R.id.admin_name);
      viewHolder.office = view.findViewById(R.id.office_address);
      viewHolder.beginTime = view.findViewById(R.id.begin_time);
      viewHolder.endTime = view.findViewById(R.id.end_time);
      viewHolder.email = view.findViewById(R.id.email);
      viewHolder.telephone = view.findViewById(R.id.telephone);
      view.setTag(viewHolder);
    } else {
      view = convertView;
      viewHolder = (ViewHolder) view.getTag();
    }
    viewHolder.adminName.setText(adminInfo.getName());
    viewHolder.office.setText(adminInfo.getOffice());
    viewHolder.beginTime.setText(adminInfo.getBeginTime());
    viewHolder.endTime.setText(adminInfo.getEndTime());
    viewHolder.email.setText(adminInfo.getEmail());
    viewHolder.telephone.setText(adminInfo.getTelephone());
    return view;
  }

  class ViewHolder {
    TextView adminName;
    TextView office;
    TextView beginTime;
    TextView endTime;
    TextView telephone;
    TextView email;
  }
}
