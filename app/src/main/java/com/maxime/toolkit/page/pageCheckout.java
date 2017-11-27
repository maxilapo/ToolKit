package com.maxime.toolkit.page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.TextView;

import com.maxime.toolkit.DataManager;
import com.maxime.toolkit.R;
import com.maxime.toolkit.objects.Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

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
            String firstname = txtFirstname.getText().toString();
            String lastname = txtLastname.getText().toString();
            String phone = txtPhone.getText().toString();
            String adress = txtAddress.getText().toString();
            String adress2 = txtAddress2.getText().toString();
            String city = txtCity.getText().toString();
            String zip = txtZIP.getText().toString();
            String province = cbProvince.getSelectedItem().toString();

            if (email.length() == 0 || firstname.length() == 0 || lastname.length() == 0 || phone.length() == 0 || adress.length() == 0 ||
                    adress2.length() == 0 || city.length() == 0 || zip.length() == 0 || province.length() == 0) {
                //Show UI about missing information
                return;
            }

            Intent intent = new Intent(this, pagePayment.class);
            intent.putExtra("clientID", 7);
            startActivity(intent);

            Log.d("max_CHECKOUT", "onClick, addClient");
            /*DataManager _dataManager = new DataManager();
            try {
                Client newClient = _dataManager.addClient(email, firstname, lastname, phone, adress, adress2, city, zip, province);

                if (newClient != null) {
                    Intent intent = new Intent(this, pagePayment.class);
                    intent.putExtra("clientID", newClient.getId());
                    startActivity(intent);
                }
            }
            catch (JSONException e) { e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
            catch (InterruptedException e) {e.printStackTrace();}*/

        }
    }
}
