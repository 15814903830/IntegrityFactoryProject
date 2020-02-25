package com.xsylsb.integrity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.xsylsb.integrity.util.MyURL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControlActivity extends AppCompatActivity {
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv_kaiqi)
    ImageView ivKaiqi;
    @BindView(R.id.iv_shengchang)
    ImageView ivShengchang;
    @BindView(R.id.iv_test)
    ImageView ivTest;
    @BindView(R.id.et_api_test)
    EditText etApiTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        setapiimg();

    }
    public void setapiimg() {
        if (MyURL.URLL.equals("http://liugangapitest.gx11.cn/")) {
            ivShengchang.setSelected(true);
            ivTest.setSelected(false);
            ivKaiqi.setSelected(false);
        } else if (MyURL.URLL.equals("http://testapi.liugang.gx11.cn/")) {
            ivShengchang.setSelected(false);
            ivTest.setSelected(true);
            ivKaiqi.setSelected(false);
        } else {
            ivShengchang.setSelected(false);
            ivTest.setSelected(false);
            ivKaiqi.setSelected(true);
        }
        etApiTest.setText(MyURL.URLL);
    }

    @OnClick({R.id.iv_kaiqi, R.id.iv_shengchang, R.id.iv_test, R.id.btn_preserve, R.id.iv_return})
    public void MyOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shengchang:
                //生产
                etApiTest.setText("http://liugangapitest.gx11.cn/");
                ivShengchang.setSelected(true);
                ivTest.setSelected(false);
                ivKaiqi.setSelected(false);
                break;
            case R.id.iv_test:
                //测试
                etApiTest.setText("http://testapi.liugang.gx11.cn/");
                ivShengchang.setSelected(false);
                ivTest.setSelected(true);
                ivKaiqi.setSelected(false);

                break;
            case R.id.iv_kaiqi:
                //开发
                ivShengchang.setSelected(false);
                ivTest.setSelected(false);
                ivKaiqi.setSelected(true);
                etApiTest.setText("http://192.168.0.24/factory.api/");
                break;
            case R.id.btn_preserve:
                //保存
                MyURL.URL = etApiTest.getText().toString() + "Api/Account/";
                MyURL.URLL = etApiTest.getText().toString();
                finish();
                break;
            case R.id.iv_return:
                //返回
                finish();
                break;


        }
    }

}
