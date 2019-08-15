package test.revolutapptest.server_data;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Request data interface
 *
 * @author Aleksandar Milojevic
 */
public interface RequestDataInterface {

    /**
     * Base url
     */
    String BASE_URL = "https://revolut.duckdns.org/";

    @GET("latest?base=EUR")
    Call<Model> getModel();
}