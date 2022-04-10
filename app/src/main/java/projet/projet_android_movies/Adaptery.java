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

/**
 * {@link Adaptery} est la class qui permet de controller la recycler view de la page
 * de la class {@link MainActivity}.Cet class va permtre de remplir le recyclerview avec les donnes recupere
 * par le decodejson dans l AsyncTask, pour ensuit les affichers dans des textview ou imageview
 * dans chaque dans chaque rangée de la recyclerview
 */
public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    /**
     * Declaration d'un variable de type {@link RecyclerViewlnterface}
     */
    private final RecyclerViewlnterface recyclerViewlnterface;
    /**
     * Declaration d'un variable de type {@link Context}
     */
    private Context mContext;
    /**
     * Declaration d'un list de type List<MovieModelClass>
     */
    private List<MovieModelClass> mData;

    /**
     * Constructeur de la class {@link Adaptery} qui initilaise les variable {@link Context}
     * ,List<MovieModelClass>,{@link RecyclerViewlnterface}
     * @param mContext
     * @param mData
     * @param recyclerViewlnterface
     */
    public Adaptery(Context mContext, List<MovieModelClass> mData, RecyclerViewlnterface recyclerViewlnterface) {
        this.mContext = mContext;
        this.mData = mData;
        this.recyclerViewlnterface = recyclerViewlnterface;
    }


    /**
     *La methode onCreateViewHolder est
     * une methode de la class RecyclerView.Adapter<Adaptery.MyViewHolder> et
     * elle est appelé pour cree une nouvelle vue
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.movie_item, parent, false);
        return new MyViewHolder(v, recyclerViewlnterface);

    }

    /**
     * La methode onBindViewHolder est une methode
     * de la class RecyclerView.Adapter<Adaptery.MyViewHolder>
     * permet de recupere les donnees et de définir le texte dans les textviews
     * ou les imageview
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rate.setText(mData.get(position).getRating());
        holder.title.setText(mData.get(position).getTitle());
        Glide.with(mContext)
                .load(mData.get(position).getImg())
                .into(holder.img);
    }

    /**
     * La methode getItemCount est une methode qui permet de voir
     * la taill des donnes dans la List<MovieModelClass>
     * @return
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * La class {@link MyViewHolder} contien un constructeur qui a comme parametre {@link View} ,
     * {@link RecyclerViewlnterface}cet constructeur contient tous les declarations les views
     * comme textview et image views.
     *  Cet constructeur permet de detecter la position quand on clique sur l ecran pour recupere les donnes
     *  comme les title ,rate et image
     *
     */
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
