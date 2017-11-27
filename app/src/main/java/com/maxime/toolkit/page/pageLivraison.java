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
import com.maxime.toolkit.objects.Client;
import com.maxime.toolkit.objects.Product;
import com.maxime.toolkit.objects.SaleOrders;

import org.json.JSONException;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class pageLivraison extends AppCompatActivity {

    private DataManager _dataManager;
    private SaleOrders[] saleOrdersArray;

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
            saleOrdersArray = _dataManager.getAllDelivery();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        pageLivraison.LivraisonListAdapter adapter = new pageLivraison.LivraisonListAdapter(this, saleOrdersArray);
        recyclerView.setAdapter(adapter);
    }

    private class LivraisonListAdapter extends RecyclerView.Adapter<pageLivraison.LivraisonListAdapter.MyViewHolder>  {

        private SaleOrders[] mListSaleOrders;
        private Context mContext;

        public LivraisonListAdapter(Context context, SaleOrders[] _listSaleOrders) {
            mContext = context;
            mListSaleOrders = _listSaleOrders;
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
            SaleOrders saleOrders = mListSaleOrders[position];

            holder.bindName(saleOrders.getClient().getName());
            holder.bindEmail(saleOrders.getClient().getEmail());
            holder.bindPhone(saleOrders.getClient().getPhone());
            holder.bindProduits(saleOrders.formattedProductList());

            holder.bindAddress(saleOrders.getClient().getStreet());
            holder.bindCityZIP(saleOrders.getClient().getCity() + ", " + saleOrders.getClient().getZip());
            holder.bindCountryProvince(saleOrders.getClient().getProvince() + ", " + saleOrders.getClient().getCountry());
        }

        @Override
        public int getItemCount()
        {
            return (mListSaleOrders.length);
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
                int position = getAdapterPosition();
                int id = view.getId();

                if (id != R.id.cellDelivery_btnMaps)
                    return;

                if(position != RecyclerView.NO_POSITION) {
                    SaleOrders sale = mListSaleOrders[position];
                    Client client = sale.getClient();

                    String clientAddress = client.getStreet() + ", " + client.getCity();
                    String uri = String.format(Locale.ENGLISH, "geo:0,0?q=" + clientAddress);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
            }
        }
    }
}
