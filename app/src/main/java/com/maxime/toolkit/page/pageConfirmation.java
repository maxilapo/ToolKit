package com.maxime.toolkit.page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.maxime.toolkit.R;
import com.maxime.toolkit.objects.Panier;

public class pageConfirmation extends AppCompatActivity implements View.OnClickListener {

    private TextView btnHome;
    private TextView mProductlist;
    private TextView mSubtotal;
    private TextView mTaxes;
    private TextView mTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_confirmation);

        bindUI();
        setupUI();

    }

    private  void bindUI () {
        mProductlist = (TextView) findViewById(R.id.pageConfirmation_txtProductList);
        mSubtotal = (TextView) findViewById(R.id.pageConfirmation_txtSubtotal);
        mTaxes = (TextView) findViewById(R.id.pageConfirmation_txtTaxes);
        mTotal = (TextView) findViewById(R.id.pageConfirmation_txtTotal);
        btnHome = (TextView) findViewById(R.id.pageConfirmation_btnGallery);
        btnHome.setOnClickListener(this);
    }

    private  void setupUI () {
        mProductlist.setText(Panier.getInstance().formattedProductList());
        mSubtotal.setText(Panier.getInstance().getSubtotalFormatted());
        mTaxes.setText(Panier.getInstance().getTaxesFormatted());
        mTotal.setText(Panier.getInstance().getTotalFormatted());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id != R.id.pageConfirmation_btnGallery)
            return;

        Panier.getInstance().viderPanier();

        Intent intent = new Intent(getApplicationContext(), pageProductGallery.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
