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
import java.util.concurrent.ExecutionException;

public class pageCheckout extends AppCompatActivity implements View.OnClickListener {

    private final String className = "pageCheckout";

    private TextView btnNext;
    private TextView txtEmail;
    private TextView txtFirstname;
    private TextView txtLastname;
    private TextView txtPhone;
    private TextView txtAddress;
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
        txtCity = (TextView) findViewById(R.id.pageCheckout_txtCity);
        txtZIP = (TextView) findViewById(R.id.pageCheckout_txtZIP);

        cbProvince = (Spinner) findViewById(R.id.pageCheckout_cbProvince);

        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id != R.id.pageCheckout_btnNext)
            return;

        String email = txtEmail.getText().toString();
        String firstname = txtFirstname.getText().toString();
        String lastname = txtLastname.getText().toString();
        String phone = txtPhone.getText().toString();
        String adress = txtAddress.getText().toString();
        String city = txtCity.getText().toString();
        String zip = txtZIP.getText().toString();
        String province = cbProvince.getSelectedItem().toString();

        if (email.length() == 0 || firstname.length() == 0 || lastname.length() == 0 || phone.length() == 0 ||
                adress.length() == 0 || city.length() == 0 || zip.length() == 0 || province.length() == 0) {
            //Show UI about missing information
            return;
        }

        Log.d(className, "pass validation");

        DataManager _dataManager = new DataManager();
        try {
            Client newClient = _dataManager.addClient(email, firstname, lastname, phone, adress, city, zip, province);

            if (newClient != null) {
                Log.d(className, "Client ID : " + newClient.getId());
                Intent intent = new Intent(getApplicationContext(), pagePayment.class);
                intent.putExtra("clientID", newClient.getId());
                startActivity(intent);
            } else {
                //Show UI about error
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
