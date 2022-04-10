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
 * {@link Adaptery2} est la class qui permet de controller la recycler view de la page de la class
 * {@link SearchMovie}.Cet class va permtre de remplir le recyclerview avec les donnes recupere
 * par le decodejson dans l AsyncTask, pour ensuit les affichers dans des textview ou imageview
 * dans chaque dans chaque rangée de la recyclerview
 */
public class Adaptery2 extends RecyclerView.Adapter<Adaptery2.MyViewHolder> {
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
     * Constructeur de la class {@link Adaptery2} qui initilaise les variable {@link Context}
     * ,List<MovieModelClass>,{@link RecyclerViewlnterface}
     * @param mContext
     * @param mData
     * @param recyclerViewlnterface
     */
    public Adaptery2(Context mContext, List<MovieModelClass> mData, RecyclerViewlnterface recyclerViewlnterface) {
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
    public Adaptery2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.moivie_item_search, parent, false);
        return new Adaptery2.MyViewHolder(v, recyclerViewlnterface);

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
    public void onBindViewHolder(@NonNull Adaptery2.MyViewHolder holder, int position) {

        holder.title.setText(mData.get(position).getTitle());
        holder.descrip.setText(mData.get(position).getRating());
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
     * La class {@link Adaptery2.MyViewHolder} contien un constructeur qui a comme parametre {@link View} ,
     * {@link RecyclerViewlnterface}cet constructeur contient tous les declarations les views
     * comme textview et image views.
     *  Cet constructeur permet de detecter la position de cliqiue quand on clique sur l ecran
     *  pour recupere les donnes comme les title ,description et image
     *
     */
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