package com.fromgeoto.linxiangpeng.andstepcounter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fromgeoto.linxiangpeng.stepcounter.ObtainStepListener;
import com.fromgeoto.linxiangpeng.stepcounter.StepCounter;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mTextView;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        registerListener();
    }

    private void init (){
        mActivity = MainActivity.this;
        StepCounter.install(mActivity);

        mTextView = (TextView)findViewById(R.id.content);
        mButton = (Button)findViewById(R.id.button);
    }

    private void registerListener(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepCounter.fetchStep(mActivity, new ObtainStepListener() {
                    @Override
                    public void obtain(long number, long count, long lastNumber, long time) {
                        String result = "今日步数 : "+count+"\n"
                                +"统计起点 : " + number + "\n"
                                +"上次计数 : " + lastNumber + "\n"
                                +"更新时间 : " + time;
                        mTextView.setText(result);
                    }
                });
            }
        });
    }

}
