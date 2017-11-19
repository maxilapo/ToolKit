package com.maxime.toolkit.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.maxime.toolkit.objects.User;

public class FilterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pageFilter_CategoryList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        FilterPage.CategoryListAdapter adapter = new FilterPage.CategoryListAdapter(this, User.getInstance().getCategoryList());
        recyclerView.setAdapter(adapter);
    }

    private class CategoryListAdapter extends RecyclerView.Adapter<FilterPage.CategoryListAdapter.MyViewHolder>  {

        private Category[] mListCategory;

        private int selectedPos = 0;

        public CategoryListAdapter(Context context, Category[] _listCategory) {

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
            holder.bindTag(currentCategory.getID());
            holder.itemView.setBackground(currentCategory.isSelected() ? getDrawable(R.drawable.border_selected) : getDrawable(R.drawable.border));
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

            public void bindTag(int categoryID){ itemView.setTag(categoryID); }

            @Override
            public void onClick(View view) {

                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                if (view.getTag() == null)
                    return;

                int categoryID = (int)view.getTag();

                User.getInstance().selectCategory(categoryID);

                //Updating the old cell and the new one.
                notifyItemChanged(selectedPos);
                selectedPos = getAdapterPosition();
                notifyItemChanged(selectedPos);

                Intent output = new Intent();
                setResult(Activity.RESULT_OK, output);
                finish();
            }
        }
    }
}
