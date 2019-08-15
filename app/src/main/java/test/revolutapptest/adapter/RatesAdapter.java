package test.revolutapptest.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import test.revolutapptest.R;
import test.revolutapptest.holder.RatesAdapterHolder;
import test.revolutapptest.server_data.Rates;

/**
 * Rate adapter
 *
 * @author Aleksandar Milojevic
 */
public class RatesAdapter extends RecyclerView.Adapter<RatesAdapterHolder> {

    /**
     * On item click interface
     */
    private OnItemClickInterface onItemClickInterface;

    /**
     * Server rates list
     */
    private List<Rates> serverRatesList = new ArrayList<>();

    /**
     * Updated rates list
     */
    private List<Rates> updatedRatesList = new ArrayList<>();

    /**
     * Context
     */
    private Context context;

    /**
     * Current rate
     */
    private String currentRate;

    /**
     * Entered text
     */
    private String enteredText;

    /**
     * Dividing results
     */
    private float dividingResult;

    /**
     * Constructor
     *
     * @param context context
     */
    public RatesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RatesAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rates_single_item_view, null);
        return new RatesAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RatesAdapterHolder ratesAdapterHolder, final int position) {

        //If updated rates list is not 0, show data from that list
        //Used after users activity
        //Initially, this list is empty, so it will be represent data from server without any changes
        if (updatedRatesList.size() != 0) {
            ratesAdapterHolder.getTitle().setText(updatedRatesList.get(position).getKey());
            ratesAdapterHolder.getRateView().setText(String.valueOf(updatedRatesList.get(position).getValue()));
            ratesAdapterHolder.getSubtitle().setText(updatedRatesList.get(position).getFullCurrencyName());
            try {
                ratesAdapterHolder.getRateCountryImage().setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open(updatedRatesList.get(position).getFlag())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ratesAdapterHolder.getTitle().setText(serverRatesList.get(position).getKey());
            ratesAdapterHolder.getRateView().setText(String.valueOf(serverRatesList.get(position).getValue()));
            ratesAdapterHolder.getSubtitle().setText(serverRatesList.get(position).getFullCurrencyName());

            try {
                ratesAdapterHolder.getRateCountryImage().setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open(serverRatesList.get(position).getFlag())));
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                // get input stream
//                InputStream ims = context.getAssets().open(serverRatesList.get(position).getFullCurrencyName());
//                // load image as Drawable
//                Drawable d = Drawable.createFromStream(ims, null);
//                // set image to ImageView
//                ratesAdapterHolder.getRateCountryImage().setImageDrawable(d);
//            }
//            catch(IOException ex) {
//                return;
//            }
        }

        //Set click listener on text view holder
        ratesAdapterHolder.getTextHolder().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //On text holder click
                onItemClickInterface.onTextHolderClicked();

                //Update server rates list
                Rates rates = serverRatesList.get(position);
                serverRatesList.remove(position);
                serverRatesList.add(0, rates);

                //Notify adapter item moved
                notifyItemMoved(position, 0);

                //Get current rate from the first position on server rates list
                //It is important to have updated rate every time on first position
                currentRate = String.valueOf(serverRatesList.get(0).getValue());
            }
        });

        //Set action listener on rate view
        ratesAdapterHolder.getRateView().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //Clear updated rates list every time on new click
                updatedRatesList.clear();
                enteredText = "";

                //Get entered text from holder
                if (!ratesAdapterHolder.getRateView().getText().toString().equals("")) {
                    enteredText = String.valueOf(ratesAdapterHolder.getRateView().getText());
                }

                //Get simple divide dividingResult to get
                if (!currentRate.equals("") && !enteredText.equals("")) {

                    //Parse rate and entered text to float and do simple math
                    float floatCurrentRate = Float.parseFloat(currentRate);
                    float floatEnteredText = Float.parseFloat(enteredText);
                    dividingResult = floatEnteredText / floatCurrentRate;
                }

                //Click on done after entering amount for change
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    actionDonePressed();
                    onItemClickInterface.onEnterKeyboardClick();
                }
                return false;
            }
        });
    }

    /**
     * On action done pressed
     */
    private void actionDonePressed() {

        //Go through server rates values and add new item to updated list with the keys, multiplication of dividingResult value and current rates value, full name of currency and flag
        //After press Done/Ok/Enter on soft keyboard, server list will be replaced with updated list
        for (int i = 0; i < serverRatesList.size(); i++) {
            float valueToFloat = Float.parseFloat(serverRatesList.get(i).getValue().toString());
            updatedRatesList.add(new Rates(serverRatesList.get(i).getKey(), valueToFloat * dividingResult, serverRatesList.get(i).getFullCurrencyName(), serverRatesList.get(i).getFlag()));

            //Notify from 1st position, because we do not want to change values on the first position in list
            notifyItemRangeChanged(1, updatedRatesList.size());
        }
    }

    @Override
    public int getItemCount() {
        return serverRatesList.size();
    }

    /**
     * Refresh method
     * <p>
     * Adapter gets data from this method
     *
     * @param rates rates list
     */
    public void refresh(List<Rates> rates) {

        //Check if local rates list is empty, if ok, put everything from server response
        //This is only for initial state, only first time server rates list is empty
        if (serverRatesList.isEmpty()) {
            serverRatesList.addAll(rates);
        } else {

            //Check if dividing result is not null, it means that user already entered some value to check other rates and results
            //If not 0, dividing results will multiply with fresh data from server and update rates list
            //If is 0, on screen will be visible current rate freshly from server
            if (dividingResult != 0) {
                for (int i = 0; i < rates.size(); i++) {
                    for (int j = 1; j < updatedRatesList.size(); j++) {
                        if (rates.get(i).getKey().equals(updatedRatesList.get(j).getKey())) {
                            float currentValue = Float.parseFloat(rates.get(i).getValue().toString());
                            updatedRatesList.get(j).setValue(currentValue * dividingResult);
                        }
                    }
                }
            } else {
                for (int i = 0; i < rates.size(); i++) {
                    for (int j = 0; j < serverRatesList.size(); j++) {
                        if (rates.get(i).getKey().equals(serverRatesList.get(j).getKey())) {
                            serverRatesList.get(j).setValue(rates.get(i).getValue());
                            break;
                        }
                    }
                }
            }

            //Notify to change elements in UI
            notifyItemRangeChanged(0, rates.size());
        }
    }

    /**
     * On item click listener
     */
    public interface OnItemClickInterface {

        /**
         * On current rate clicked
         */
        void onTextHolderClicked();

        /**
         * On enter keyboard click
         */
        void onEnterKeyboardClick();
    }

    /**
     * On item click interface
     *
     * @param onItemClickInterface on item click interface
     */
    public void setOnItemClickInterface(OnItemClickInterface onItemClickInterface) {
        this.onItemClickInterface = onItemClickInterface;
    }
}