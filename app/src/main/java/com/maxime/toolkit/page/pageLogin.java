package com.maxime.toolkit.page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.maxime.toolkit.DataManager;
import com.maxime.toolkit.R;
import com.maxime.toolkit.objects.User;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class pageLogin extends AppCompatActivity implements View.OnClickListener {

    private final String className = "pageLogin";

    private TextView btnLogin;
    private TextView btnLogout;
    private TextView txtEmail;
    private TextView txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_login);

        bindUI();
        setupUI();
    }

    public void bindUI(){
        btnLogin = (TextView) findViewById(R.id.pageLogin_btnLogin);
        btnLogout = (TextView) findViewById(R.id.pageLogin_btnLogout);
        txtEmail = (TextView) findViewById(R.id.pageLogin_txtEmail);
        txtPassword = (TextView) findViewById(R.id.pageLogin_txtPassword);

        btnLogin.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    public void setupUI(){
        if (User.getInstance().isConnected()){
            txtEmail.setVisibility(View.INVISIBLE);
            txtPassword.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.INVISIBLE);
            btnLogout.setVisibility(View.VISIBLE);
        }
        else{
            txtEmail.setVisibility(View.VISIBLE);
            txtPassword.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.pageLogin_btnLogin){
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();

            if (email == "" || password == "")
                return;

            DataManager _dataManager = new DataManager();
            try {
                Log.d(className, "user creds : " +  email + "  " + password);
                if (_dataManager.login(email, password)){
                    Intent intent = new Intent(getApplicationContext(), pageProductGallery.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    //TODO: show error UI
                }
            }
            catch (JSONException e) {e.printStackTrace();}
            catch (ExecutionException e) {e.printStackTrace();}
            catch (InterruptedException e) {e.printStackTrace();}
        }
        else if(id == R.id.pageLogin_btnLogout){
            User.getInstance().resetUser();
            setupUI();
        }
    }
}
