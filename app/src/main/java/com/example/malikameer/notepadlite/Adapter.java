package com.example.malikameer.notepadlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    OnRecyclerViewItemClick itemClick;
    List<Model> mList;
    Context context;
    Adapter(List<Model> dataSet, Context context) {
        this.mList = dataSet;
        this.context = context;
    }
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View v=layoutInflater.inflate(R.layout.recyclerview_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model data=mList.get(position);
        holder.txtTitle.setText(data.getTitle());
        holder.txtDescription.setText(data.getDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView txtTitle,txtDescription,txtTime,txtReminder;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtDescription=itemView.findViewById(R.id.txtDescription);
            txtTime=itemView.findViewById(R.id.txtTimeandDate);
            txtReminder=itemView.findViewById(R.id.txtReminder);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =getAdapterPosition();
                    itemClick.OnClick(position,txtTitle,txtDescription);
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, 1234, 0, "Delete");
            menu.getItem(0).setOnMenuItemClickListener(onMenuItemClickListener);
        }
        MenuItem.OnMenuItemClickListener onMenuItemClickListener=new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case 1234:
                        SQLDatabase sqlDatabase=new SQLDatabase(context);
                        sqlDatabase.deleteRecord(getAdapterPosition());
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(context, MainActivity.class);
                        context.startActivity(myIntent);       }
                return true;
            }
        };
    }
    public interface OnRecyclerViewItemClick{
        void OnClick(int position,TextView txtTitle,TextView txtDescription);
    }
    public void setOnItemClickListner(OnRecyclerViewItemClick itemClick){
        this.itemClick=itemClick;
    }
}
