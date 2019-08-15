package test.revolutapptest.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import test.revolutapptest.R;

/**
 * Class rates adapter holder
 *
 * @author Aleksandar Milojevic
 */
public class RatesAdapterHolder extends RecyclerView.ViewHolder {

    /**
     * Text holder
     */
    private RelativeLayout textHolder;

    /**
     * Rate country image
     */
    private ImageView rateCountryImage;

    /**
     * Title
     */
    private TextView title;

    /**
     * Subtitle
     */
    private TextView subtitle;

    /**
     * Rate view
     */
    private EditText rateView;

    /**
     * Constructor
     *
     * @param itemView item view
     */
    public RatesAdapterHolder(@NonNull View itemView) {
        super(itemView);

        //Set references
        textHolder = itemView.findViewById(R.id.rl_rates_single_item_text_holder);
        rateCountryImage = itemView.findViewById(R.id.iv_rates_single_item_country);
        title = itemView.findViewById(R.id.tv_rates_single_item_title);
        subtitle = itemView.findViewById(R.id.tv_rates_single_item_subtitle);
        rateView = itemView.findViewById(R.id.et_ates_single_item_rate);
    }

    public RelativeLayout getTextHolder() {
        return textHolder;
    }

    /**
     * Get rate country image
     *
     * @return rate country image
     */
    public ImageView getRateCountryImage() {
        return rateCountryImage;
    }

    /**
     * Get title
     *
     * @return title
     */
    public TextView getTitle() {
        return title;
    }

    /**
     * Get subtitle
     *
     * @return subtitle
     */
    public TextView getSubtitle() {
        return subtitle;
    }

    /**
     * Get rate view
     *
     * @return date view
     */
    public EditText getRateView() {
        return rateView;
    }
}