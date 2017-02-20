package pom.pom.prom.prompompom.productsscreen.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import pom.pom.prom.prompompom.R;

/**
 * Created by vararg on 19.02.2017.
 */

public class SortTypesAdapter extends BaseAdapter {

    private ArrayList<String> sortTypes;

    private LayoutInflater layoutInflater;

    public SortTypesAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public SortTypesAdapter(Context context, Collection<String> sortTypes) {
        super();

        layoutInflater = LayoutInflater.from(context);
        this.sortTypes = new ArrayList<>(sortTypes);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, R.layout.item_spinner_drop_down_sort_type);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, R.layout.item_spinner_list_sort_type);
    }

    @Override
    public int getCount() {
        return sortTypes.size();
    }

    @Override
    public String getItem(int position) {
        return sortTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void mergeAll(Collection<String> newSorts) {
        for (String sortType : newSorts){
            if (!sortTypes.contains(sortType)) sortTypes.add(sortType);
        }
        notifyDataSetChanged();
    }

    public int getPosition(String sort) {

        for (int i = 0; i < sortTypes.size(); i++) {
            if (sortTypes.get(i).equals(sort)) return i;
        }

        return 0;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent,
                              @LayoutRes int layoutId) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutId, parent, false);
        }
        TextView label = (TextView) convertView.findViewById(R.id.sort_type_name);

        label.setText(sortTypes.get(position));

        return convertView;
    }

}
