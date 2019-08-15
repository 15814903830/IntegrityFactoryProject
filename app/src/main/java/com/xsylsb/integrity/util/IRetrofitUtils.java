package com.xsylsb.integrity.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZXY on 2019/3/30 13:50.
 * Class functions
 * *********************************************************
 * *
 * *********************************************************
 */

public class IRetrofitUtils {
    private int CODE_SUCC = 200;
    private static IRetrofitUtils iRetrofitUtils;
    private Retrofit retrofit;

    public interface IHttpResponceLinener<C> {
        void onResponse(C object);
    }

    public interface IHttpDefeatedLinener<C> {
        void onDefeated(C object);
    }

    private IRetrofitUtils() {
        String url = MyURL.URL;
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.newBuilder().readTimeout(20, TimeUnit.SECONDS);
        okHttpClient.newBuilder().writeTimeout(20, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())//GSON解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//Rxjava
                .client(okHttpClient)
                .build();
    }

    public static IRetrofitUtils getInstances() {
        if (iRetrofitUtils == null) {
            synchronized (IRetrofitUtils.class) {
                if (iRetrofitUtils == null) {
                    iRetrofitUtils = new IRetrofitUtils();
                }
            }
        }
        return iRetrofitUtils;
    }

    /**
     * 创建API
     */
    public <T> T createAPI(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public int getNetworkType(Context mContext) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = 3;
                } else {
                    netType = 2;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
        return netType;
    }

    public <C> C enqueue(Call<C> call,Context mContext, final IHttpResponceLinener<C> iHttpResponceLinener,final IHttpDefeatedLinener<C> iHttpDefeatedLinener) {
        if (mContext!=null && getNetworkType(mContext) == 0) {
            Toast.makeText(mContext, "当前无网路", Toast.LENGTH_SHORT).show();
        } else {
            call.enqueue(new Callback<C>() {
                @Override
                public void onResponse(Call<C> call, Response<C> response) {
                    Log.e("111", "onResponse: " + response.toString());
                    C c = response.body();
                    Class<?> zClass = c.getClass();
                    try {

                        Method getCode = zClass.getMethod("getCode");
                        int code = (int) getCode.invoke(c);
                        Method getMsg = zClass.getMethod("getMsg");
                        String msg = (String)getMsg.invoke(c);
                        Log.e("111", "onResponseMSG: " + msg);
                        Log.e("111", "onResponseCode: " + code);
                        if (code == CODE_SUCC) {
                            iHttpResponceLinener.onResponse(c);
                        }else {
                            iHttpDefeatedLinener.onDefeated(c);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<C> call, Throwable t) {
                    Log.e("111", "onFailure: " + t.getMessage());

                }
            });
        }
        return null;
    }
}
