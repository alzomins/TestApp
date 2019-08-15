package test.revolutapptest;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.revolutapptest.adapter.RatesAdapter;
import test.revolutapptest.data.NamesAndFlags;
import test.revolutapptest.server_data.Model;
import test.revolutapptest.server_data.Rates;
import test.revolutapptest.server_data.RequestDataInterface;

/**
 * Main activity
 *
 * @author Aleksandar Milojevic
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Call
     */
    private Call<Model> call;

    /**
     * Model callback
     */
    private Callback<Model> modelCallback;

    /**
     * Rate title
     */
    private TextView rateTitle;

    /**
     * Recycler view
     */
    private RecyclerView recyclerView;

    /**
     * Rates adapter
     */
    private RatesAdapter ratesAdapter;

    /**
     * Handler
     */
    private Handler handler = new Handler();

    /**
     * Helper flag
     */
    private boolean clicked = false;

    /**
     * Rates list
     */
    private List<Rates> rates;

    /**
     * Names and flags list
     */
    private List<NamesAndFlags> namesAndFlags = new ArrayList<>();

    /**
     * Repeat call value
     * `
     */
    private static final int DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set reference to main view, inflate custom layout to main view
        RelativeLayout mainView = findViewById(R.id.rl_main_view);
        View ratesLayout = LayoutInflater.from(this).inflate(R.layout.rates_layout, null);
        mainView.addView(ratesLayout);

        //Set references for custom title
        rateTitle = ratesLayout.findViewById(R.id.tv_rate_title);
        recyclerView = ratesLayout.findViewById(R.id.rv_recycler);
        rates = new ArrayList<>();

        //Hardcode title
        rateTitle.setText("Rates");

        //Call to get data
        retrofitCall();
    }

    /**
     * Retrofit call
     */
    private void retrofitCall() {

        //Create retrofit call, set needed data to get data from BE
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestDataInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Create data
        RequestDataInterface api = retrofit.create(RequestDataInterface.class);

        //Get model from url
        call = api.getModel();

        //Create and set adapter to recycler view and layout manager to set VERTICAL orientation
        ratesAdapter = new RatesAdapter(this);
        recyclerView.setAdapter(ratesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (recyclerView.getItemAnimator() != null) {
            recyclerView.getItemAnimator().setChangeDuration(0);
        }

        //Implement adapter methods
        ratesAdapter.setOnItemClickInterface(new RatesAdapter.OnItemClickInterface() {
            @Override
            public void onTextHolderClicked() {

                //Tap on item in recycler will scroll recycler to top position
                recyclerView.scrollToPosition(0);

                //Helper flag
                clicked = true;
            }

            @Override
            public void onEnterKeyboardClick() {

                //Helper flag
                clicked = false;
            }
        });

        //Create callback to get response from server
        modelCallback = new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, final Response<Model> response) {
                if (response.isSuccessful()) {

                    //If response is ok, set rate title to be visible
                    rateTitle.setVisibility(View.VISIBLE);
                    if (response.body() != null) {
                        if (!clicked) {

                            //Use response body elements
                            useResponseElements(response);
                        }

                        //Repeat call every second to get new rate list
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                repeatCall();
                            }
                        }, DELAY);
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

                //TODO need to improve handling errors
                if (t.getMessage().contains("No address associated with hostname")) {
                    Snackbar.make(findViewById(R.id.rl_main_view), "No internet connection", Snackbar.LENGTH_LONG).show();
                }
            }
        };

        //Send request asynchronously
        call.enqueue(modelCallback);
    }

    /**
     * Repeat call
     */
    private void repeatCall() {
        call.clone().enqueue(modelCallback);
    }

    /**
     * Conversion map to List of rates objects
     *
     * @param response response
     */
    private void useResponseElements(final Response<Model> response) {
        if (response.body() != null) {

            //GO through entire map and create list of Rates object with two parameters, key an value
            for (Map.Entry<String, Object> map : response.body().getRates().entrySet()) {
                rates.add(new Rates(map.getKey(), String.valueOf(map.getValue())));
            }

            for (int i = 0; i < rates.size(); i++) {
                rates.get(i).setFullCurrencyName(getNamesAndFlags().get(i).getFullName());
                rates.get(i).setFlag(getNamesAndFlags().get(i).getFlag());
            }

            //Refresh elements in adapter
            ratesAdapter.refresh(rates);
        }
    }

    /**
     * Get names and flags
     *
     * @return return names and flags
     */
    public List<NamesAndFlags> getNamesAndFlags() {
        namesAndFlags.add(new NamesAndFlags("Australian dollar", "AUD.png"));
        namesAndFlags.add(new NamesAndFlags("Bulgarian lev", "BGN.png"));
        namesAndFlags.add(new NamesAndFlags("Brazilian real", "BRL.png"));
        namesAndFlags.add(new NamesAndFlags("Canadian dollar", "CAD.png"));
        namesAndFlags.add(new NamesAndFlags("Swiss franc", "CHF.png"));
        namesAndFlags.add(new NamesAndFlags("Chinese yuan renminbi", "CNY.png"));
        namesAndFlags.add(new NamesAndFlags("Czech koruna", "CZK.png"));
        namesAndFlags.add(new NamesAndFlags("Danish krone", "DKK.png"));
        namesAndFlags.add(new NamesAndFlags("Pound sterling", "GBP.png"));
        namesAndFlags.add(new NamesAndFlags("Hong Kong dollar", "HKD.png"));
        namesAndFlags.add(new NamesAndFlags("Croatian kuna", "HRK.png"));
        namesAndFlags.add(new NamesAndFlags("Hungarian forint", "HUF.png"));
        namesAndFlags.add(new NamesAndFlags("Indonesian rupiah", "IDR.png"));
        namesAndFlags.add(new NamesAndFlags("Israeli shekel", "ILS.png"));
        namesAndFlags.add(new NamesAndFlags("Indian rupee", "INR.png"));
        namesAndFlags.add(new NamesAndFlags("Icelandic krona", "ISK.png"));
        namesAndFlags.add(new NamesAndFlags("Japanese yen", "JPY.png"));
        namesAndFlags.add(new NamesAndFlags("South Korean won", "KRW.png"));
        namesAndFlags.add(new NamesAndFlags("Mexican peso", "MXN.png"));
        namesAndFlags.add(new NamesAndFlags("Malaysian ringgit", "MYR.png"));
        namesAndFlags.add(new NamesAndFlags("Norwegian krone", "NOK.png"));
        namesAndFlags.add(new NamesAndFlags("New Zealand dollar", "NZD.png"));
        namesAndFlags.add(new NamesAndFlags("Philippine peso", "PHP.png"));
        namesAndFlags.add(new NamesAndFlags("Polish zloty", "PLN.png"));
        namesAndFlags.add(new NamesAndFlags("Romanian leu", "RON.png"));
        namesAndFlags.add(new NamesAndFlags("Russian rouble", "RUB.png"));
        namesAndFlags.add(new NamesAndFlags("Swedish krona", "SEK.png"));
        namesAndFlags.add(new NamesAndFlags("Singapore dollar", "SGD.png"));
        namesAndFlags.add(new NamesAndFlags("Thai baht", "THB.png"));
        namesAndFlags.add(new NamesAndFlags("Turkish lira", "TRY.png"));
        namesAndFlags.add(new NamesAndFlags("US dollar", "USD.png"));
        namesAndFlags.add(new NamesAndFlags("South African rand", "ZAR.png"));
        return namesAndFlags;
    }
}