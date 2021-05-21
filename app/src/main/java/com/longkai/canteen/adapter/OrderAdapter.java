package com.longkai.canteen.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.longkai.canteen.fragment.TabFragment;

public class OrderAdapter extends FragmentPagerAdapter {
  private List<TabFragment> mFragmentList;//各导航的Fragment
  private List<String> mTitle; //导航的标题

  public  OrderAdapter(FragmentManager fragmentManager,List<TabFragment>fragments,List<String>title){
    super(fragmentManager);
    mFragmentList=fragments;
    mTitle=title;

  }
  @Override
  public Fragment getItem(int position) {
    return mFragmentList.get(position);
  }

  @Override
  public int getCount() {
    return mFragmentList.size();
  }
  @Override
  public CharSequence getPageTitle(int position) {
    return mTitle.get(position);
  }

}
