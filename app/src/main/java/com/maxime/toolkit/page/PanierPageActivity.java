package com.maxime.toolkit.page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.maxime.toolkit.ProductDetailPage;
import com.maxime.toolkit.ProductGalleryActivity;
import com.maxime.toolkit.R;
import com.maxime.toolkit.RatingPage;
import com.maxime.toolkit.objects.Panier;
import com.maxime.toolkit.objects.Product;

import org.w3c.dom.Text;

public class PanierPageActivity extends AppCompatActivity {

    private TextView subTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier_page);

        bindUI();
        setupUI();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.panier_ProductList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        PanierPageActivity.ImageGalleryAdapter adapter = new PanierPageActivity.ImageGalleryAdapter(this, Panier.getInstance().getCartProduct());
        recyclerView.setAdapter(adapter);
    }

    private  void bindUI () {
        subTotal = (TextView)  findViewById(R.id.panierPage_Subtotal);
    }

    private  void setupUI () {
        subTotal.setText(Panier.getInstance().getSubtotalFormatted());
    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<PanierPageActivity.ImageGalleryAdapter.MyViewHolder>  {

        private Product[] mListProduct;
        private Context mContext;

        public ImageGalleryAdapter(Context context, Product[] _listProduct) {
            mContext = context;
            mListProduct = _listProduct;
        }

        @Override
        public PanierPageActivity.ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View photoView = inflater.inflate(R.layout.panier_product, parent, false);

            PanierPageActivity.ImageGalleryAdapter.MyViewHolder viewHolder = new PanierPageActivity.ImageGalleryAdapter.MyViewHolder(photoView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PanierPageActivity.ImageGalleryAdapter.MyViewHolder holder, int position)
        {
            Product produit = mListProduct[position];
            ImageView imageView = holder.mPhotoImageView;

            holder.bindTitle(produit.getTitle());

            String formatedPrice = String.format("%.2f$", produit.getPrice());
            holder.bindPrice(formatedPrice);
            holder.bindQuantity(String.valueOf(produit.getQuantity()));

            holder.bindButtonAdd(produit.getID());
            holder.bindButtonRemove(produit.getID());

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
            public TextView mTitleTextView;
            public TextView mPriceTextView;
            public TextView mQuantityTextView;

            public ImageView mBtnAddItem;
            public ImageView mBtnRemoveItem;


            public void bindTitle(String text){ mTitleTextView.setText(text); }
            public void bindPrice(String text){mPriceTextView.setText(text);}
            public void bindQuantity(String text){mQuantityTextView.setText(text);}

            public void bindButtonAdd(int productID){ mBtnAddItem.setTag(productID); }
            public void bindButtonRemove(int productID){ mBtnRemoveItem.setTag(productID); }


            public MyViewHolder(View itemView) {

                super(itemView);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.panierProduct_Image);
                mTitleTextView = (TextView) itemView.findViewById(R.id.panierProduct_Title);
                mPriceTextView = (TextView) itemView.findViewById(R.id.panierProduct_Price);
                mQuantityTextView = (TextView) itemView.findViewById(R.id.panierProduct_Quantity);

                mBtnAddItem = (ImageView) itemView.findViewById(R.id.panierProduct_btnAddItem);
                mBtnRemoveItem = (ImageView) itemView.findViewById(R.id.panierProduct_btnRemoveItem);


                mBtnAddItem.setOnClickListener(this);
                mBtnRemoveItem.setOnClickListener(this);

                itemView.setOnClickListener(this);
            }


            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                int id = view.getId();

                if (view.getTag() == null)
                    return;

                int productID = (int)view.getTag();

                if (id == R.id.panierProduct_btnAddItem) //ADD ITEM
                {
                    Panier.getInstance().incrementProduit(productID);
                    ImageGalleryAdapter.this.notifyItemChanged(position, "payload " + position);
                    setupUI();
                }
                else if (id == R.id.panierProduct_btnRemoveItem) //REMOVE ITEM
                {
                    Panier.getInstance().decrementProduit(productID);

                    //TODO : Manage the quantity 0 here
                    mListProduct = Panier.getInstance().getCartProduct();

                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mListProduct.length);

                    setupUI();
                }
            }
        }
    }
}
