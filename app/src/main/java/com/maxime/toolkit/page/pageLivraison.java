package com.maxime.toolkit.page;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxime.toolkit.DataManager;
import com.maxime.toolkit.R;
import com.maxime.toolkit.objects.Delivery;

import org.json.JSONException;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class pageLivraison extends AppCompatActivity  implements View.OnClickListener{

    private DataManager _dataManager;
    private Delivery[] deliveryArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_delivery);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pageLivraison_rcvLivraison);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        _dataManager = new DataManager();

        try {
            deliveryArray = _dataManager.getAllDelivery();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        pageLivraison.LivraisonListAdapter adapter = new pageLivraison.LivraisonListAdapter(this, deliveryArray);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.pagePanier_btnCheckout) {
            Intent intent = new Intent(this, pageCheckout.class);
            //intent.putExtra("productID", currentProduct.getID());
            startActivity(intent);
            //startActivityForResult(intent, 1);
        }

    }

    private class LivraisonListAdapter extends RecyclerView.Adapter<pageLivraison.LivraisonListAdapter.MyViewHolder>  {

        private Delivery[] mListDelivery;
        private Context mContext;

        public LivraisonListAdapter(Context context, Delivery[] _listDelivery) {
            mContext = context;
            mListDelivery = _listDelivery;
        }

        @Override
        public pageLivraison.LivraisonListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View photoView = inflater.inflate(R.layout.cell_delivery, parent, false);
            pageLivraison.LivraisonListAdapter.MyViewHolder viewHolder = new pageLivraison.LivraisonListAdapter.MyViewHolder(photoView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(pageLivraison.LivraisonListAdapter.MyViewHolder holder, int position)
        {
            Delivery delivery = mListDelivery[position];

            holder.bindName("client");
            holder.bindName(delivery.getClient().getName());
            holder.bindEmail(delivery.getClient().getEmail());
            holder.bindPhone(delivery.getClient().getPhone());
            holder.bindProduits(delivery.formattedProductList());

            holder.bindAddress(delivery.getClient().getStreet());
            holder.bindCityZIP(delivery.getClient().getCity() + ", " + delivery.getClient().getZip());
            holder.bindCountryProvince(delivery.getClient().getProvince() + ", " + delivery.getClient().getCountry());
        }

        @Override
        public int getItemCount()
        {
            return (mListDelivery.length);
        }


        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView btnMaps;
            public TextView mNameTextView;
            public TextView mEmailTextView;
            public TextView mPhoneTextView;
            public TextView mProduitsTextView;

            public TextView mAddressTextView;
            public TextView mCityTextView;
            public TextView mCountryTextView;

            public MyViewHolder(View itemView) {

                super(itemView);

                btnMaps = (ImageView) itemView.findViewById(R.id.cellDelivery_btnMaps);
                mNameTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtName);
                mEmailTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtEmail);
                mPhoneTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtPhone);
                mProduitsTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtProduits);

                mAddressTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtAddress);
                mCityTextView    = (TextView) itemView.findViewById(R.id.cellDelivery_txtCity);
                mCountryTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtCountry);

                btnMaps.setOnClickListener(this);
            }

            public void bindName(String text){ mNameTextView.setText(text); }
            public void bindEmail(String text){ mEmailTextView.setText(text); }
            public void bindPhone(String text){mPhoneTextView.setText(text);}
            public void bindProduits(String text){mProduitsTextView.setText(text);}
            public void bindAddress(String text){mAddressTextView.setText(text);}
            public void bindCityZIP(String text){mCityTextView.setText(text);}
            public void bindCountryProvince(String text){mCountryTextView.setText(text);}

            @Override
            public void onClick(View view) {

                //int position = getAdapterPosition();
                int id = view.getId();

               // if (view.getTag() == null)
               //     return;

                //int productID = (int)view.getTag();

                if (id == R.id.cellDelivery_btnMaps)
                {

                    String uri = String.format(Locale.ENGLISH, "geo:0,0?q=" + "3119 place alton gobloom");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);

                }
            }
        }
    }
}
