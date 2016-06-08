package vnzit.com.sampleretrofit;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sh on 6/8/16.
 */
public enum  Rest {
    INSTANCE;

    public static final String DOMAIN = "https://api.github.com/";
    private final Api api;
    Rest() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public @NonNull  Api api() {
        return api;
    }

}
