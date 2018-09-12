package com.monresto.acidlabs.monresto.UI.Checkout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;

import com.monresto.acidlabs.monresto.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {
    @BindView(R.id.buttonClose)
    ImageView buttonClose;
    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        buttonClose.setOnClickListener(e -> finish());

        int orderID = getIntent().getIntExtra("orderID", 89551);
        String postData = "orderID=" + orderID;

        //webView.postUrl("https://www.monresto.net/processgpg.php", Base64.encode(postData.getBytes()).getBytes());

        String html = "<!DOCTYPE html>" +
                "<html>" +
                "<body onload='document.frm1.submit()'>" +
                "<form action='https://www.monresto.net/processgpg.php' method='post' name='frm1'>" +
                "  <input type='hidden' name='orderID' value='"+orderID+"'><br>" +
                "</form>" +
                "</body>" +
                "</html>";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(html, "text/html", "UTF-8");


    }


}
