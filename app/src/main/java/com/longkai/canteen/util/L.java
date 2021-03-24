package com.longkai.canteen.util;

import android.util.Log;

import com.longkai.canteen.BuildConfig;

public class L {
  private static final String TAG = "UMX";

  private static final boolean DEBUG = BuildConfig.DEBUG;

  public static void d(String msg) {
    if (DEBUG) {
      Log.d(TAG, msg);
    }
  }
}
