package com.xsylsb.integrity.mianfragment.homepage.scan;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xsylsb.integrity.R;
import com.xsylsb.integrity.mianfragment.homepage.notice.NoticeFragment;
import com.xsylsb.integrity.mvp.MVPBaseFragment;

/**
 *  扫一扫
 */

public class ScanFragment extends MVPBaseFragment<ScanContract.View, ScanPresenter> implements ScanContract.View {
    private View mView;

    /**
     * Fragment 的构造函数。
     */
    public ScanFragment() {
    }

    public static ScanFragment newInstance() {
        return new ScanFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.scanfragment, container, false);
        return mView;
    }
}
