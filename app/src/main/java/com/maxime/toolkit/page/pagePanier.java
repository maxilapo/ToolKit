package com.maxime.toolkit.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maxime.toolkit.R;
import com.maxime.toolkit.objects.Panier;
import com.maxime.toolkit.objects.Product;

public class pagePanier extends AppCompatActivity implements View.OnClickListener {

    private TextView subTotal;
    private TextView taxes;
    private TextView total;
    private TextView btnCheckout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_panier);

        bindUI();
        setupUI();
        initListeners();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.panier_ProductList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        pagePanier.ImageGalleryAdapter adapter = new pagePanier.ImageGalleryAdapter(this, Panier.getInstance().getCartProduct());
        recyclerView.setAdapter(adapter);
    }

    private  void bindUI () {
        subTotal    = (TextView)  findViewById(R.id.pagePanier_txtSubtotal);
        taxes       = (TextView)  findViewById(R.id.pagePanier_txtTaxes);
        total       = (TextView)  findViewById(R.id.pagePanier_txtTotal);
        btnCheckout = (TextView) findViewById(R.id.pagePanier_btnCheckout);
    }

    private  void setupUI () {
        subTotal.setText("Subtotal : " + Panier.getInstance().getSubtotalFormatted());
        taxes.setText("Taxes : " + Panier.getInstance().getTaxesFormatted());
        total.setText("Total : " + Panier.getInstance().getTotalFormatted());
    }

    private void initListeners() {btnCheckout.setOnClickListener(this); }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (Panier.getInstance().getCartCount() <= 0)
            return;

        if (id == R.id.pagePanier_btnCheckout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to purchases these items ?")
                    .setTitle("Confirm");

            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    Intent intent = new Intent(getApplicationContext(), pageCheckout.class);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("Recheck", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<pagePanier.ImageGalleryAdapter.MyViewHolder>  {

        private Product[] mListProduct;
        private Context mContext;

        public ImageGalleryAdapter(Context context, Product[] _listProduct) {
            mContext = context;
            mListProduct = _listProduct;
        }

        @Override
        public pagePanier.ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View photoView = inflater.inflate(R.layout.cell_panier, parent, false);
            pagePanier.ImageGalleryAdapter.MyViewHolder viewHolder = new pagePanier.ImageGalleryAdapter.MyViewHolder(photoView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(pagePanier.ImageGalleryAdapter.MyViewHolder holder, int position)
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
                    int newQty = Panier.getInstance().decrementProduit(productID);

                    if (newQty == 0){
                        Log.d("max_decrement", "panier, qty a 0");
                        notifyItemRemoved(position);
                        //notifyItemRangeChanged(position, mListProduct.length);

                        pagePanier.ImageGalleryAdapter adapter = new pagePanier.ImageGalleryAdapter(getApplicationContext(), Panier.getInstance().getCartProduct());
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        Log.d("max_decrement", "panier, qty pas 0");



                        ImageGalleryAdapter.this.notifyItemChanged(position, "payload " + position);
                    }


                    //TODO : Manage the quantity 0 here
                    //mListProduct = Panier.getInstance().getCartProduct();

                    //

                    setupUI();
                }
            }
        }
    }
}
