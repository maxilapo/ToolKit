package com.maxime.toolkit.page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;

import com.maxime.toolkit.R;

import org.json.JSONException;
import org.json.JSONObject;

public class pageCheckout extends AppCompatActivity implements View.OnClickListener {

    private TextView btnNext;
    private TextView txtEmail;
    private TextView txtFirstname;
    private TextView txtLastname;
    private TextView txtPhone;
    private TextView txtAddress;
    private TextView txtAddress2;
    private TextView txtCity;
    private TextView txtZIP;
    private Spinner cbProvince;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_checkout);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        bindUI();
    }

    private  void bindUI () {
        btnNext = (TextView) findViewById(R.id.pageCheckout_btnNext);

        txtEmail = (TextView) findViewById(R.id.pageCheckout_txtEmail);
        txtFirstname = (TextView) findViewById(R.id.pageCheckout_txtFirstName);
        txtLastname = (TextView) findViewById(R.id.pageCheckout_txtLastName);
        txtPhone = (TextView) findViewById(R.id.pageCheckout_txtPhone);
        txtAddress = (TextView) findViewById(R.id.pageCheckout_txtAddress);
        txtAddress2 = (TextView) findViewById(R.id.pageCheckout_txtAddress2);
        txtCity = (TextView) findViewById(R.id.pageCheckout_txtCity);
        txtZIP = (TextView) findViewById(R.id.pageCheckout_txtZIP);

        cbProvince = (Spinner) findViewById(R.id.pageCheckout_cbProvince);

        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.pageCheckout_btnNext) {

            String email = txtEmail.getText().toString();
            String firstname = txtEmail.getText().toString();
            String lastname = txtEmail.getText().toString();
            String phone = txtEmail.getText().toString();
            String adress = txtEmail.getText().toString();
            String adress2 = txtEmail.getText().toString();
            String city = txtEmail.getText().toString();
            String zip = txtEmail.getText().toString();
            String province = cbProvince.getSelectedItem().toString();

            JSONObject jsonCheckout = new JSONObject();

            if (email != null) {


                try {
                    jsonCheckout.put("email", email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            Log.d("max_CHECKOUT", "JSon checkout = " + jsonCheckout.toString());




        //Intent intent = new Intent(this, pagePayment.class);
        //intent.putExtra("productID", currentProduct.getID());
        //startActivity(intent);
        //startActivityForResult(intent, 1);

        }
    }








}
