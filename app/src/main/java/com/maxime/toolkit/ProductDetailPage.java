package com.maxime.toolkit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maxime.toolkit.objects.Panier;
import com.maxime.toolkit.objects.Product;

public class ProductDetailPage extends AppCompatActivity  implements View.OnClickListener{

    public static final String EXTRA_PRODUCT = "ProductDetailPage.Product";

    private DataManager _dataManager;

    private Product currentProduct;

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private TextView mPriceTextView;
    private TextView mRatingTextView;
    private ImageView mProductImage;

    private ImageView btnAddComment;
    private TextView btnAddCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        _dataManager = new DataManager();


        Product produit = getIntent().getParcelableExtra(EXTRA_PRODUCT);

        //To get more information
        currentProduct = _dataManager.getProductDetails(produit.getID());

        setupUI();
        initListeners();

        Glide.with(this)
                .load(currentProduct.getImageUrl())
                .asBitmap()
                .error(R.mipmap.productplaceholder400)
                .listener(new RequestListener<String, Bitmap>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache,
                                                   boolean isFirstResource) {

                        //onPalette(Palette.from(resource).generate());
                        mProductImage.setImageBitmap(resource);

                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mProductImage);
    }


    private  void setupUI ()
    {
        //Binding
        mProductImage = (ImageView) findViewById(R.id.productDetail_Image);
        mTitleTextView = (TextView) findViewById(R.id.productDetail_Title);
        mDescriptionTextView = (TextView) findViewById(R.id.productDetail_Description);
        mPriceTextView = (TextView) findViewById(R.id.productDetail_Price);
        mRatingTextView = (TextView) findViewById(R.id.productDetail_Rating);

        //Buttons
        btnAddComment = (ImageView)  findViewById(R.id.productDetail_btnAddComment);
        btnAddCart = (TextView)  findViewById(R.id.productDetail_btnAddCart);

        //Setter
        mTitleTextView.setText(currentProduct.getTitle());
        mDescriptionTextView.setText(currentProduct.getDescription());
        mPriceTextView.setText(String.format("%.2f$", currentProduct.getPrice()));
        mRatingTextView.setText(currentProduct.getRatingStar());
    }

    private void initListeners() {

        btnAddComment.setOnClickListener(this);
        btnAddCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.productDetail_btnAddComment) {
            //https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
            Intent intent = new Intent(this, RatingPage.class);
            startActivity(intent);
        }
        else if (id == R.id.productDetail_btnAddCart){
            Context context = getApplicationContext();
            CharSequence text = "Item added to the Cart ";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Panier.getInstance().addToCart(currentProduct);


        }
    }
}
