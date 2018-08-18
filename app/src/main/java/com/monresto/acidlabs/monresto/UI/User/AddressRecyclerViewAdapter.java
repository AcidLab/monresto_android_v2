package com.monresto.acidlabs.monresto.UI.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monresto.acidlabs.monresto.Model.Address;
import com.monresto.acidlabs.monresto.Model.Monresto;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.UI.Profile.Address.EditAddressActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressRecyclerViewAdapter extends RecyclerView.Adapter<AddressRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Address> addresses;

    public AddressRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.address_title_item)
        TextView address_title_item;
        @BindView(R.id.layoutAddressItem)
        ConstraintLayout layoutAddressItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.item_address, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Address address;
        address = addresses.get(i);
        viewHolder.address_title_item.setText(address.getAdresse());
        viewHolder.layoutAddressItem.setOnClickListener(e -> {
            Monresto.getInstance().setAddress(address);
            Intent intent = new Intent();
            intent.putExtra("address", address);
            Monresto.setLat(address.getLat());
            Monresto.setLon(address.getLon());
            ((SelectAddressActivity)context).setResult(Activity.RESULT_OK, intent);
            ((SelectAddressActivity)context).finish();
        });
    }

    @Override
    public int getItemCount() {
        if (addresses == null)
            return 0;
        return addresses.size();
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }
}
