package vnzit.com.sampleretrofit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ReposAdapter.Callback {

    private RecyclerView rvContainer;
    private Subscription loadReposSubscription;

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

    private void loadReposByUsername(@NonNull final String username) {
        Rest.INSTANCE.api().getReposByUsername(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<Repo>>() {
                            @Override
                            public void call(List<Repo> repos) {
                                final ReposAdapter adapter = new ReposAdapter(repos);
                                adapter.setCallback(MainActivity.this);
                                rvContainer.setAdapter(adapter);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(MainActivity.this, "Have an error when load repo info: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (loadReposSubscription.isUnsubscribed()) {
            loadReposSubscription.unsubscribe();
        }
    }

    @Override
    public void onClickItem(int position, Repo repo) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getHtmlUrl()));
        startActivity(intent);
    }
}
