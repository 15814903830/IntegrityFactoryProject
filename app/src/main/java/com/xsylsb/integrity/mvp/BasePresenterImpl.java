package com.xsylsb.integrity.mvp;

import android.content.Context;
import android.util.Log;


public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    protected V mView;
    protected Context  mContext;


    @Override
    public void attachView(V view) {
        mView=view;
    }

    @Override
    public void detachView() {
        mView=null;
    }

    public void setContext(Context context) {
        Log.e("111", "BasePresenterImpl-context: " + context);
        mContext = context;
    }


}
