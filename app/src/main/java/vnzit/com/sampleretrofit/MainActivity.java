package vnzit.com.sampleretrofit;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ReposAdapter.Callback {

    private RecyclerView rvContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvContainer = (RecyclerView) findViewById(R.id.rvContainer);
        rvContainer.setLayoutManager(new LinearLayoutManager(this));
        final EditText etAccountName = (EditText) findViewById(R.id.etAccountName);
        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etAccountName.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(MainActivity.this, "Account name should not be empty", Toast.LENGTH_LONG).show();
                }
                loadReposByUsername(username);
            }
        });
    }

    private void loadReposByUsername(@NonNull  String username) {
        Rest.INSTANCE.api().getReposByUsername(username)
                .enqueue(new Callback<List<Repo>>() {
                    @Override
                    public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                        final ReposAdapter adapter = new ReposAdapter(response.body());
                        adapter.setCallback(MainActivity.this);
                        rvContainer.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Repo>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(MainActivity.this, "Have an error when load repo info: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClickItem(int position, Repo repo) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getHtmlUrl()));
        startActivity(intent);
    }
}
