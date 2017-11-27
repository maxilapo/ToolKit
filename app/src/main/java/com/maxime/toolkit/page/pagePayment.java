package com.maxime.toolkit.page;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.maxime.toolkit.DataManager;
import com.maxime.toolkit.R;
import com.maxime.toolkit.objects.Client;
import com.maxime.toolkit.objects.Panier;

import org.json.JSONException;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class pagePayment extends AppCompatActivity implements View.OnClickListener {

    private final String className = "pagePayment";

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

        if (id != R.id.pagePayment_btnNext)
            return;

        String cardHolder = txtCardHolder.getText().toString();
        String cardType = cbCardType.getSelectedItem().toString();

        int month = cbMonth.getSelectedItemPosition() + 1;
        int CVV = Integer.parseInt(txtCVV.getText().toString());
        int year = Integer.parseInt(cbYear.getSelectedItem().toString());

        BigInteger cardNumber = new BigInteger(txtCardNumber.getText().toString());

        DataManager _dataManager = new DataManager();

        Log.d("max_PAYMENT", "Payment in progress");

        try {
            boolean creditCardOK = _dataManager.addCreditCard(clientID, cardType, cardNumber, month, year, cardHolder, CVV);
            Log.d(className, "Credit card added");

            boolean saleOrderCreated = false;
             if (creditCardOK)
                saleOrderCreated = _dataManager.addSaleOrders(clientID, Panier.getInstance().getTotal(), Panier.getInstance().getCartProduct());
            //else
                //UI about failure

            Log.d(className, "SaleOrder added");
             //SUCCESS
             if (saleOrderCreated)
             {
                 Intent intent = new Intent(getApplicationContext(), pageConfirmation.class);
                 startActivity(intent);
             }
             else{
                 //UI about FAILURE
             }
        }
        catch (JSONException e) {e.printStackTrace();}
        catch (ExecutionException e) {e.printStackTrace();}
        catch (InterruptedException e) {e.printStackTrace();}
    }
}
