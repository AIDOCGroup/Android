package me.aidoc.client.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GetApiDataReslutActivity extends AppCompatActivity implements View.OnClickListener{

    protected TextView tvLog;
    private ImageView back;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_get_api_datas_result);
        initView();
    }

    private void initView() {
        tvLog = (TextView) findViewById(R.id.tv_log);
        String result = getIntent().getStringExtra("result");
        String titleStr = getIntent().getStringExtra("title");
        if(!TextUtils.isEmpty(result) && result.contains("SocketTimeoutException")){
            result ="连接超时，请稍后重试";
        }
        tvLog.setText(result+"");

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        titleStr = titleStr.contains("数据")?titleStr:titleStr+"数据";
        title.setText(titleStr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
