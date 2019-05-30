package academy.learnprogramming.pooling.ApiUrl;

import java.util.List;

import academy.learnprogramming.pooling.response.PeopleResponse;
import academy.learnprogramming.pooling.response.SalaryResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("home.php")
    Call<List<PeopleResponse>> people();

    @FormUrlEncoded
    @POST("ret.php")
    Call<SalaryResponse> salary(
            @Field("name") String name
    );

}
