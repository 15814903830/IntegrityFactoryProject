package com.xsylsb.integrity.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jph.takephoto.app.TakePhotoActivity;

import java.lang.reflect.ParameterizedType;



public abstract class MVPBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends TakePhotoActivity implements BaseView {
    public T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
        mPresenter.setContext(this);//判断当前网络类型所需的Context
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
