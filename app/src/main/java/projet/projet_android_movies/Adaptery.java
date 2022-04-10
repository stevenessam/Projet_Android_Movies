package projet.projet_android_movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {


    private final RecyclerViewlnterface recyclerViewlnterface;
    private Context mContext;
    private List<MovieModelClass> mData;


    public Adaptery(Context mContext, List<MovieModelClass> mData, RecyclerViewlnterface recyclerViewlnterface) {
        this.mContext = mContext;
        this.mData = mData;
        this.recyclerViewlnterface = recyclerViewlnterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(v, recyclerViewlnterface);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rate.setText(mData.get(position).getRating());
        holder.title.setText(mData.get(position).getTitle());
        Glide.with(mContext)
                .load(mData.get(position).getImg())
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView rate;
        ImageView img;

        public MyViewHolder(@NonNull View itemView, RecyclerViewlnterface recyclerViewlnterface) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            rate = itemView.findViewById(R.id.rateingTextView);
            img = itemView.findViewById(R.id.imageImageView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (recyclerViewlnterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewlnterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
