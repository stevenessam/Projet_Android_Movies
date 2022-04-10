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

public class Adaptery2 extends RecyclerView.Adapter<Adaptery2.MyViewHolder> {

    private final RecyclerViewlnterface recyclerViewlnterface;
    private Context mContext;
    private List<MovieModelClass> mData;

    public Adaptery2(Context mContext, List<MovieModelClass> mData, RecyclerViewlnterface recyclerViewlnterface) {
        this.mContext = mContext;
        this.mData = mData;
        this.recyclerViewlnterface = recyclerViewlnterface;
    }

    @NonNull
    @Override
    public Adaptery2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.moivie_item_search, parent, false);
        return new Adaptery2.MyViewHolder(v, recyclerViewlnterface);

    }

    @Override
    public void onBindViewHolder(@NonNull Adaptery2.MyViewHolder holder, int position) {

        holder.title.setText(mData.get(position).getTitle());
        holder.descrip.setText(mData.get(position).getRating());
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
        TextView descrip;
        ImageView img;

        public MyViewHolder(@NonNull View itemView, RecyclerViewlnterface recyclerViewlnterface) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTextView);
            descrip = itemView.findViewById(R.id.descriptionTextView);
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