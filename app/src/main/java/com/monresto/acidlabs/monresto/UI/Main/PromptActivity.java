package com.monresto.acidlabs.monresto.UI.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromptActivity extends AppCompatActivity {
    @BindView(R.id.buttonOk)
    Button buttonOk;
    @BindView(R.id.buttonBack)
    Button buttonBack;
    @BindView(R.id.promptText)
    TextView promptText;

    View.OnClickListener listener;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.40);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(width, height);

        setContentView(R.layout.activity_prompt);
        ButterKnife.bind(this);

        buttonBack.setOnClickListener(e -> finish());

        promptText.setText(text);
        buttonOk.setOnClickListener(listener);
    }

    public PromptActivity text(String text){
        this.text = text;
        return this;
    }

    public PromptActivity onValidate(View.OnClickListener listener){
        this.listener = listener;
        return this;
    }

    public void show(Context context){
        Intent intent = new Intent(context, this.getClass());
        context.startActivity(intent);
    }

}
