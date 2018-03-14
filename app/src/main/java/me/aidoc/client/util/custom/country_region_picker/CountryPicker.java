package me.aidoc.client.util.custom.country_region_picker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.aidoc.client.R;


public class CountryPicker extends DialogFragment {

    private ArrayList<Country> allCountries = new ArrayList<>();
    private ArrayList<Country> selectedCountries = new ArrayList<>();
    private OnPick onPick;

    public CountryPicker() { }

    public static CountryPicker newInstance(Bundle args, OnPick onPick) {
        CountryPicker picker = new CountryPicker();
        picker.setArguments(args);
        picker.onPick = onPick;
        return picker;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_country_picker, container, false);
        EditText etSearch = (EditText) root.findViewById(R.id.et_search);
        final RecyclerView rvCountry = (RecyclerView) root.findViewById(R.id.rv_country);
        allCountries.clear();
        allCountries.addAll(Country.getAll(getContext(), null));
        selectedCountries.clear();
        selectedCountries.addAll(allCountries);
        final Adapter adapter = new Adapter();
        rvCountry.setAdapter(adapter);
        rvCountry.setLayoutManager(new LinearLayoutManager(getContext()));
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                selectedCountries.clear();
                for (Country country : allCountries) {
                    if(country.name.toUpperCase().contains(string.toUpperCase()))
                        selectedCountries.add(country);
                }
                adapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    private class Adapter extends RecyclerView.Adapter<VH> {
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(getContext()).inflate(R.layout.item_country, parent, false));
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            final Country country = selectedCountries.get(position);
            holder.ivFlag.setImageDrawable(country.flag ==0? null : getResources().getDrawable(country.flag));
            holder.tvName.setText(country.name);
            holder.tvCode.setText("+" + country.code);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if(onPick != null) onPick.onPick(country);
                }
            });
        }

        @Override
        public int getItemCount() {
            return selectedCountries.size();
        }
    }

    private class VH extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvCode;
        ImageView ivFlag;

        VH(View itemView) {
            super(itemView);
            ivFlag = (ImageView) itemView.findViewById(R.id.iv_flag);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvCode = (TextView) itemView.findViewById(R.id.tv_code);
        }
    }
}
