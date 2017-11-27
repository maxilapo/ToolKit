package com.maxime.toolkit.page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.maxime.toolkit.DataManager;
import com.maxime.toolkit.R;
import com.maxime.toolkit.objects.Panier;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class pagePayment extends AppCompatActivity implements View.OnClickListener {

    private int clientID;

    private TextView btnNext;
    private TextView txtCardNumber;
    private TextView txtCardHolder;
    private TextView txtCVV;
    private Spinner cbCardType;
    private Spinner cbMonth;
    private Spinner cbYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_payment);

        clientID = getIntent().getIntExtra("clientID", 7);

        bindUI();
    }

    private  void bindUI () {
        btnNext = (TextView) findViewById(R.id.pagePayment_btnNext);
        txtCardNumber = (TextView) findViewById(R.id.pagePayment_txtCardNumber);
        txtCardHolder = (TextView) findViewById(R.id.pagePayment_txtCardHolder);
        txtCVV = (TextView) findViewById(R.id.pagePayment_txtCVV);

        cbCardType = (Spinner) findViewById(R.id.pagePayment_cbCardType);
        cbMonth = (Spinner) findViewById(R.id.pagePayment_cbMonth);
        cbYear  = (Spinner) findViewById(R.id.pagePayment_cbYear);

        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.pagePayment_btnNext) {


            DataManager _dataManager = new DataManager();
            try {
                _dataManager.addSaleOrders(clientID, 37.43, Panier.getInstance().getCartProduct());
            }
            catch (JSONException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
            catch (InterruptedException e) {e.printStackTrace();}

            Intent intent = new Intent(getApplicationContext(), pageProductGallery.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    }
}
