package com.xsylsb.integrity.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.lang.reflect.ParameterizedType;


public abstract class MVPBaseFragment<V extends BaseView, T extends BasePresenterImpl<V>> extends Fragment implements BaseView {
    public T mPresenter;

    public static MVPBaseFragment getInstance(Class cls){
        MVPBaseFragment fragment = null;
        try {
            fragment = (MVPBaseFragment) cls.newInstance();
            if (fragment.mPresenter==null){
                fragment.mPresenter = (BasePresenterImpl) fragment.getInstance(fragment, 1);
                fragment.mPresenter.attachView(fragment);
                if (fragment.getContext() != null)
                    fragment.mPresenter.setContext(fragment.getContext());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter==null){
            mPresenter = getInstance(this, 1);
            mPresenter.attachView((V) this);
            if (getContext() != null)
                mPresenter.setContext(getContext());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return super.getContext();
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
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }



}
