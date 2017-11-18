package com.maxime.toolkit.page;

import android.content.Context;
import android.graphics.Color;
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
import com.maxime.toolkit.DataManager;
import com.maxime.toolkit.R;
import com.maxime.toolkit.objects.Category;
import com.maxime.toolkit.objects.Panier;
import com.maxime.toolkit.objects.Product;

public class FilterPage extends AppCompatActivity implements View.OnClickListener {

    private DataManager _dataManager;

    private TextView selectedCategory;
    private TextView btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        bindUI();
        setupUI();

        _dataManager = new DataManager();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pageFilter_CategoryList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        FilterPage.CategoryListAdapter adapter = new FilterPage.CategoryListAdapter(this, _dataManager.getAllCategories());
        recyclerView.setAdapter(adapter);
    }

    private  void bindUI () {

        selectedCategory = (TextView) findViewById(R.id.filterPage_SelectedCategory);
        btnApply = (TextView) findViewById(R.id.filterPage_btnApply);

        btnApply.setOnClickListener(this);
    }

    private  void setupUI () {

        selectedCategory.setText("LALALALALA");

    }

    @Override
    public void onClick(View v) {

    }

    private class CategoryListAdapter extends RecyclerView.Adapter<FilterPage.CategoryListAdapter.MyViewHolder>  {

        private Category[] mListCategory;
        private Context mContext;
        private int selectedPos = 0;

        public CategoryListAdapter(Context context, Category[] _listCategory) {
            mContext = context;
            mListCategory = _listCategory;
        }

        @Override
        public FilterPage.CategoryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View categoryCell = inflater.inflate(R.layout.category_cell, parent, false);

            FilterPage.CategoryListAdapter.MyViewHolder viewHolder = new FilterPage.CategoryListAdapter.MyViewHolder(categoryCell);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(FilterPage.CategoryListAdapter.MyViewHolder holder, int position)
        {
            Category currentCategory = mListCategory[position];


            holder.bindName(currentCategory.getName());

            holder.itemView.setBackground(selectedPos == position ? getDrawable(R.drawable.border_selected) : getDrawable(R.drawable.border));



        }

        @Override
        public int getItemCount()
        {
            return (mListCategory.length);
        }


        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView mNameTextView;

            public MyViewHolder(View itemView) {

                super(itemView);
                mNameTextView = (TextView) itemView.findViewById(R.id.cellCategory_name);
                itemView.setOnClickListener(this);
            }

            public void bindName(String text){ mNameTextView.setText(text); }


            @Override
            public void onClick(View view) {

                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                notifyItemChanged(selectedPos);
                selectedPos = getAdapterPosition();
                notifyItemChanged(selectedPos);



            }
        }
    }
}
