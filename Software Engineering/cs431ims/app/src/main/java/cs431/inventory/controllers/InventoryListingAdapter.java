package cs431.inventory.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cs431.inventory.R;
import cs431.inventory.activities.ItemActivity;
import cs431.inventory.objects.Item;

public class InventoryListingAdapter extends RecyclerView.Adapter<InventoryListingAdapter.InventoryListingViewHolder> {
    private ArrayList<Item> items;
    private Activity activity;
    int userType;
    public static class InventoryListingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView qrCode;
        public TextView itemName;
        public TextView itemQuantity;
        ArrayList<Item> items;
        Activity activity;
        int userType;
        public InventoryListingViewHolder(View itemView, ArrayList<Item> items, Activity activity, int userType) {
            super(itemView);

            qrCode = (ImageView) itemView.findViewById(R.id.item_qrcode);
            itemName = (TextView) itemView.findViewById(R.id.item_name_textview);
            itemQuantity = (TextView) itemView.findViewById(R.id.item_quantity);
            this.activity = activity;
            this.userType = userType;

            this.items = items;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Item selectedItem = items.get(position);
                Intent intent = new Intent(activity.getApplicationContext(), ItemActivity.class);
                intent.putExtra("USER_TYPE", userType);
                intent.putExtra("ITEM", selectedItem);
                activity.startActivity(intent);
            }
        }
    }
    public InventoryListingAdapter(ArrayList<Item> items, Activity activity, int userType) {
        this.items = items;
        this.activity = activity;
        this.userType = userType;
    }

    @NonNull
    @Override
    public InventoryListingAdapter.InventoryListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_listing_item, parent, false);
        InventoryListingViewHolder viewHolder = new InventoryListingViewHolder(itemView, items, activity, userType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InventoryListingViewHolder holder, int position) {
        // TODO: After generating QR Code as Bitmap, delete this line and uncomment other -----
        holder.qrCode.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.qr_code));
        // holder.qrCode.setImageBitmap(items.get(position).getQrCode());
        // --------------------
        holder.itemName.setText(items.get(position).getName());
        holder.itemQuantity.setText("Quantity: " + items.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
