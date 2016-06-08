package vnzit.com.sampleretrofit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sh on 6/8/16.
 */
public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {
    private final List<Repo> repos;
    private Callback callback;

    public ReposAdapter(List<Repo> repos) {
        this.repos = repos;
    }

    public void update(List<Repo> repos) {
        this.repos.clear();
        this.repos.addAll(repos);
        notifyDataSetChanged();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        return new ViewHolder(v, callback);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(repos.get(position));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public interface Callback {
        void onClickItem(int position, Repo repo);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvFullName;
        TextView tvDescription;
        TextView tvUrl;
        private Repo repo;

        public ViewHolder(View itemView, final Callback callback) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvFullName = (TextView) itemView.findViewById(R.id.tvFullName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvUrl = (TextView) itemView.findViewById(R.id.tvUrl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        if (repo != null) {
                            callback.onClickItem(getAdapterPosition(), repo);
                        }
                    }
                }
            });
        }

        public void bind(final Repo repo) {
            this.repo = repo;
            tvName.setText(repo.getName());
            tvFullName.setText(repo.getFullName());
            tvDescription.setText(repo.getDescription());
            tvUrl.setText(repo.getHtmlUrl());
        }
    }
}
