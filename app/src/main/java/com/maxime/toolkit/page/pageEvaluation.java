package com.maxime.toolkit.page;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.RatingBar;
import android.widget.TextView;

import com.maxime.toolkit.DataManager;
import com.maxime.toolkit.R;

public class pageEvaluation extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_PRODUCTID = "pageProductDetail.ProductID";

    private TextView mName;
    private RatingBar mScore;
    private EditText mComment;
    private TextView mbtnSubmit;

    private int productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        productID = getIntent().getIntExtra("productID", 0);

        bindUI();
    }

    private void bindUI(){
        mName = (TextView) findViewById(R.id.pageRating_name);
        mComment = (EditText) findViewById(R.id.pageRating_Comment);
        mScore = (RatingBar) findViewById(R.id.pageRating_star);
        mbtnSubmit = (TextView) findViewById(R.id.pageRating_btnSubmit);

        mbtnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.pageRating_btnSubmit)
        {
            DataManager dataManager = new DataManager();
            if (dataManager.addEvaluation(productID, mName.getText().toString(), (int)mScore.getRating(), mComment.getText().toString()))
            {
                setResult(Activity.RESULT_OK);
                finish();
            }
        }
    }
}
