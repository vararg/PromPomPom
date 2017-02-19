package pom.pom.prom.prompompom.widgets;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pom.pom.prom.prompompom.R;

/**
 * Created by vararg on 18.02.2017.
 */

public final class BindingAdapters {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.color.image_loading_placeholder)
                .error(R.color.image_loading_error)
                .into(view);
    }

    @BindingAdapter({"itemSpaceDecoration"})
    public static void addSpacesItemDecoration(RecyclerView view, float offset) {
        view.addItemDecoration(new SpacesItemDecoration((int) offset));
    }

    @BindingAdapter({"textStrikethrough"})
    public static void makeTextStrikethrough(TextView view, boolean isNeeded) {
        if (isNeeded) view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
