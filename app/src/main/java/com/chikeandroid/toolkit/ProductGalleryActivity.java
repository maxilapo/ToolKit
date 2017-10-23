package com.chikeandroid.toolkit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chikeandroid.toolkit.objects.Product;

/**
 * Created by Maxime
 */

public class ProductGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_gallery);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



        ProductGalleryActivity.ImageGalleryAdapter adapter = new ProductGalleryActivity.ImageGalleryAdapter(this, DataManager.getAllProducts());
        recyclerView.setAdapter(adapter);
    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the layout
            View photoView = inflater.inflate(R.layout.item_photo, parent, false);

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
                    .placeholder(R.drawable.productplaceholder)
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
                    //SpacePhoto spacePhoto = mSpacePhotos[position];
                    Product produit = mListProduct[position];

                    Intent intent = new Intent(mContext, ProductDetailPage.class);
                    intent.putExtra(ProductDetailPage.EXTRA_SPACE_PHOTO, produit);
                    startActivity(intent);
                }
            }
        }

        private Product[] mListProduct;

        //private SpacePhoto[] mSpacePhotos;
        private Context mContext;

        public ImageGalleryAdapter(Context context, Product[] _listProduct) {
            mContext = context;
            //mSpacePhotos = spacePhotos;
            mListProduct = _listProduct;
        }
    }
}
