package com.xsylsb.integrity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TranscriptFailActivity extends AppCompatActivity {

    @BindView(R.id.iv_return_examination)
    ImageView ivReturnExamination;
    @BindView(R.id.btn_tran_ok)
    Button btnTranOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transcript_fail);
        ButterKnife.bind(this);
        ivReturnExamination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnTranOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
