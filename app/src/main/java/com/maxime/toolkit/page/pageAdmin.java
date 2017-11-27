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
import com.maxime.toolkit.objects.SaleOrders;

import org.json.JSONException;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class pageAdmin extends AppCompatActivity {

    private DataManager _dataManager;
    private SaleOrders[] saleOrdersArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_admin);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pageAdmin_rcvSaleorders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        _dataManager = new DataManager();

        try {
            saleOrdersArray = _dataManager.getAllSaleorders();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        pageAdmin.SaleOrderListAdapter adapter = new pageAdmin.SaleOrderListAdapter(this, saleOrdersArray);
        recyclerView.setAdapter(adapter);
    }

    private class SaleOrderListAdapter extends RecyclerView.Adapter<pageAdmin.SaleOrderListAdapter.MyViewHolder>  {

        private SaleOrders[] mListSaleOrders;
        private Context mContext;

        public SaleOrderListAdapter(Context context, SaleOrders[] _listSaleOrders) {
            mContext = context;
            mListSaleOrders = _listSaleOrders;
        }

        @Override
        public pageAdmin.SaleOrderListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View photoView = inflater.inflate(R.layout.cell_delivery, parent, false);
            pageAdmin.SaleOrderListAdapter.MyViewHolder viewHolder = new pageAdmin.SaleOrderListAdapter.MyViewHolder(photoView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(pageAdmin.SaleOrderListAdapter.MyViewHolder holder, int position)
        {
            SaleOrders saleOrders = mListSaleOrders[position];

            holder.bindTotal(saleOrders.getTotalFormatted());
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
            public TextView mTotalTextView;
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
                mTotalTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtTotal);
                mNameTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtName);
                mEmailTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtEmail);
                mPhoneTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtPhone);
                mProduitsTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtProduits);

                mAddressTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtAddress);
                mCityTextView    = (TextView) itemView.findViewById(R.id.cellDelivery_txtCity);
                mCountryTextView = (TextView) itemView.findViewById(R.id.cellDelivery_txtCountry);

                btnMaps.setVisibility(View.INVISIBLE);
                mTotalTextView.setVisibility(View.VISIBLE);
            }

            public void bindTotal(String text){ mTotalTextView.setText(text); }
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
