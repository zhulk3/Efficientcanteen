package com.longkai.canteen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.longkai.canteen.R;

public class TabFragment extends Fragment {

  private String mTitle;

  public String getTitle() {
    return mTitle;
  }

  //这个构造方法是便于各导航同时调用一个fragment
  public TabFragment(String title){
    mTitle=title;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState){
    return null;
  }
}
