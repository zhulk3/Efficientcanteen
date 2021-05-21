package com.longkai.canteen.util;

import com.google.gson.Gson;
import com.longkai.canteen.json.Inserted;
import com.longkai.canteen.json.Orderinfo;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
  /**
   *
   * @param url 请求地址
   * @param callback 处理请求返回的回调
   */
  public static void sendHttpRequest(String url, okhttp3.Callback callback) {
    OkHttpClient okHttpClient = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    okHttpClient.newCall(request).enqueue(callback);
  }

  public static void sendHttpRequestWithPost(String url, Inserted inserted, Orderinfo orderinfo,
      okhttp3.Callback callback){
    Gson gson = new Gson();
    String i=gson.toJson(inserted);
    String o = gson.toJson(orderinfo);
    FormBody.Builder requestBuild=new FormBody.Builder();
    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody=requestBuild
        .add("inserted",i)
        .add("orderinfo",o)
        .build();
    Request request = new Request.Builder()
        .url(url)
        .post(requestBody)
        .build();
    client.newCall(request).enqueue(callback);
  }
}

