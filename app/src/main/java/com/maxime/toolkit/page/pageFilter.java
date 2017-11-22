package com.maxime.toolkit.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxime.toolkit.R;
import com.maxime.toolkit.objects.Category;
import com.maxime.toolkit.objects.User;

public class pageFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.pageFilter_CategoryList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        pageFilter.CategoryListAdapter adapter = new pageFilter.CategoryListAdapter(this, User.getInstance().getCategoryList());
        recyclerView.setAdapter(adapter);
    }

    private class CategoryListAdapter extends RecyclerView.Adapter<pageFilter.CategoryListAdapter.MyViewHolder>  {

        private Category[] mListCategory;

        private int selectedPos = 0;

        public CategoryListAdapter(Context context, Category[] _listCategory) {

            mListCategory = _listCategory;
        }

        @Override
        public pageFilter.CategoryListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View categoryCell = inflater.inflate(R.layout.cell_filter, parent, false);

            pageFilter.CategoryListAdapter.MyViewHolder viewHolder = new pageFilter.CategoryListAdapter.MyViewHolder(categoryCell);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(pageFilter.CategoryListAdapter.MyViewHolder holder, int position)
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
