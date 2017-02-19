package pom.pom.prom.prompompom.network.retrofit;

import com.google.gson.Gson;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import pom.pom.prom.prompompom.network.responses.GetProductsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by vararg on 18.02.2017.
 */

public interface PromService {

    @POST("/graph/request")
    Flowable<GetProductsResponse> getProducts(@Query("limit") int limit, @Query("offset") int offset,
                                              @Query("category") long categoryId, @Query("sort") String sortType,
                                              @Body RequestBody body);

}
