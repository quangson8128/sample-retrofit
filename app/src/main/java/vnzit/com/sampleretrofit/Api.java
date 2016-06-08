package vnzit.com.sampleretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sh on 6/8/16.
 */
public interface Api {

    @GET("/users/{username}/repos")
    Call<List<Repo>> getReposByUsername(
            @Path("username") String username
    );

}
