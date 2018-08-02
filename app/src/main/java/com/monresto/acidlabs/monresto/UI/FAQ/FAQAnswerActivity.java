package com.monresto.acidlabs.monresto.UI.FAQ;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class FAQAnswerActivity extends AppCompatActivity {
    @BindView(R.id.textFaqAnswer)
    TextView textFaqAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_faq_answer);
        ButterKnife.bind(this);
        getWindow().setLayout(width, height);


        Intent intent = getIntent();
        String answer = intent.getStringExtra("answer");
        textFaqAnswer.setText(answer);

    }
}
