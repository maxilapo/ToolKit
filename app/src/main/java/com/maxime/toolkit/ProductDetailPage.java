package com.maxime.toolkit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.maxime.toolkit.objects.Category;
import com.maxime.toolkit.objects.Evaluation;
import com.maxime.toolkit.objects.Panier;
import com.maxime.toolkit.objects.Product;
import com.maxime.toolkit.objects.User;
import com.maxime.toolkit.page.FilterPage;

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
        currentProduct = _dataManager.getProductDetails(produit.getID());

        bindUI();
        setupUI();
        initListeners();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pageDetail_rcvEvaluation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ProductDetailPage.EvaluationListAdapter adapter = new ProductDetailPage.EvaluationListAdapter(this, _dataManager.getProductEvaluation(currentProduct.getID()));
        recyclerView.setAdapter(adapter);

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
                        mProductImage.setImageBitmap(resource);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mProductImage);
    }


    private  void bindUI (){
        btnAddComment = (ImageView)  findViewById(R.id.productDetail_btnAddComment);
        btnAddCart = (TextView)  findViewById(R.id.productDetail_btnAddCart);

        mProductImage = (ImageView) findViewById(R.id.productDetail_Image);
        mTitleTextView = (TextView) findViewById(R.id.productDetail_Title);
        mDescriptionTextView = (TextView) findViewById(R.id.productDetail_Description);
        mPriceTextView = (TextView) findViewById(R.id.productDetail_Price);
        mRatingTextView = (TextView) findViewById(R.id.productDetail_Rating);
    }

    private  void setupUI (){
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

    private class EvaluationListAdapter extends RecyclerView.Adapter<ProductDetailPage.EvaluationListAdapter.MyViewHolder>  {

        private Evaluation[] mListEvaluation;

        private int selectedPos = 0;

        public EvaluationListAdapter(Context context, Evaluation[] _listEval) {

            mListEvaluation = _listEval;
        }

        @Override
        public ProductDetailPage.EvaluationListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View evaluationCell = inflater.inflate(R.layout.cell_evaluation, parent, false);

            ProductDetailPage.EvaluationListAdapter.MyViewHolder viewHolder = new ProductDetailPage.EvaluationListAdapter.MyViewHolder(evaluationCell);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ProductDetailPage.EvaluationListAdapter.MyViewHolder holder, int position)
        {
            Evaluation currentEvaluation = mListEvaluation[position];

            holder.bindName(currentEvaluation.getName());
            holder.bindScore(currentEvaluation.getScoreStars());
            holder.bindComment(currentEvaluation.getComment());
        }

        @Override
        public int getItemCount()
        {
            return (mListEvaluation.length);
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView mNameTextView;
            public TextView mScoreTextView;
            public TextView mCommentTextView;

            public MyViewHolder(View itemView) {

                super(itemView);
                mNameTextView = (TextView) itemView.findViewById(R.id.cellEvaluation_txtName);
                mScoreTextView = (TextView) itemView.findViewById(R.id.cellEvaluation_txtNote);
                mCommentTextView = (TextView) itemView.findViewById(R.id.cellEvaluation_txtCommentaire);
            }

            public void bindName(String text){ mNameTextView.setText(text); }
            public void bindScore(String text){ mScoreTextView.setText(text); }
            public void bindComment(String text){ mCommentTextView.setText(text); }

        }
    }
}
