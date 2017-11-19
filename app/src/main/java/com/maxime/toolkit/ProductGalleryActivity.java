package com.maxime.toolkit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maxime.toolkit.objects.Product;
import com.maxime.toolkit.objects.User;
import com.maxime.toolkit.page.FilterPage;
import com.maxime.toolkit.page.PanierPageActivity;

/**
 * Created by Maxime
 */

public class ProductGalleryActivity extends AppCompatActivity   implements View.OnClickListener{

    private DataManager _dataManager;

    private RecyclerView recyclerView;
    private ImageView btnPanier;
    private ImageView btnFilter;
    private TextView txtCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_gallery);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView = (RecyclerView) findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        _dataManager = new DataManager();

        ProductGalleryActivity.ImageGalleryAdapter adapter = new ProductGalleryActivity.ImageGalleryAdapter(this, _dataManager.getProducts());
        recyclerView.setAdapter(adapter);

        bindUI();
        refreshUI();
        initListeners();
    }

    private  void bindUI () {
        btnPanier = (ImageView)  findViewById(R.id.btn_Panier);
        btnFilter = (ImageView)  findViewById(R.id.pageGallery_btnFilter);
        txtCategory = (TextView) findViewById(R.id.pageGallery_txtSelectedCategory);
    }

    private  void refreshUI () {
        txtCategory.setText(User.getInstance().getSelectedCategoryName());
    }

    private void initListeners() {
        btnPanier.setOnClickListener(this);
        btnFilter.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("max_GALLERY", "ON ACTIVITY RESULT");
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            //Update the data list.
            ProductGalleryActivity.ImageGalleryAdapter adapter = new ProductGalleryActivity.ImageGalleryAdapter(this, _dataManager.getProducts());
            recyclerView.setAdapter(adapter);
            recyclerView.getAdapter().notifyDataSetChanged();

            refreshUI();

            Log.d("max_GALLERY", "ON ACTIVITY RESULT OK");
        }

        //}
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_Panier) {
            Intent intent = new Intent(this, PanierPageActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.pageGallery_btnFilter)
        {
            Intent intent = new Intent(this, FilterPage.class);
            startActivityForResult(intent, 1);
        }
    }



    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder> {

        private Product[] mListProduct;

        private Context mContext;

        public ImageGalleryAdapter(Context context, Product[] _listProduct) {
            mContext = context;
            mListProduct = _listProduct;
        }



        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View photoView = inflater.inflate(R.layout.cell_produit, parent, false);

            ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);

            return viewHolder;
        }


        @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position)
        {
            Product produit = mListProduct[position];
            ImageView imageView = holder.mPhotoImageView;

            String formatedPrice = String.format("%.2f$", produit.getPrice());
            holder.bindPrice(formatedPrice);

            holder.bindTitle(produit.getTitle());
            holder.bindDescription(produit.getDescription());
            holder.bindRating(produit.getRatingStar());

            Glide.with(mContext)
                    .load(produit.getImageUrl())
                    .placeholder(R.mipmap.productplaceholder400)
                    .into(imageView);
        }

        @Override
        public int getItemCount()
        {
            return (mListProduct.length);
        }




        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mPhotoImageView;
            public TextView mPriceTextView;
            public TextView mTitleTextView;
            public TextView mDescriptionTextView;
            public TextView mRatingTextView;



            public MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);
                mPriceTextView = (TextView) itemView.findViewById(R.id.item_Price);
                mTitleTextView = (TextView) itemView.findViewById(R.id.item_Title);
                mDescriptionTextView = (TextView) itemView.findViewById(R.id.item_Description);
                mRatingTextView = (TextView) itemView.findViewById(R.id.item_Stars);

                itemView.setOnClickListener(this);
            }

            public void bindPrice(String text){
                mPriceTextView.setText(text);
            }
            public void bindTitle(String text){ mTitleTextView.setText(text); }
            public void bindDescription(String text){ mDescriptionTextView.setText(text); }
            public void bindRating(String text){ mRatingTextView.setText(text); }




            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {

                    Product produit = mListProduct[position];

                    Intent intent = new Intent(mContext, ProductDetailPage.class);
                    Log.d("max_clickOnItem", "ID = " + produit.getID());
                    intent.putExtra(ProductDetailPage.EXTRA_PRODUCT, produit);
                    startActivity(intent);
                }
            }
        }


    }
}
