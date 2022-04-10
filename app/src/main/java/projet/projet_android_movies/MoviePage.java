package projet.projet_android_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MoviePage extends AppCompatActivity {
    Context context;
    String link;
    //déclaration des textview
    TextView textViewName2;
    TextView textVRate;
    TextView textVHours;
    TextView textVYear;
    TextView textVDirector;
    TextView textVageRat;
    TextView textVgenre;

    ArrayList<MovieModelClass> movieList = new ArrayList<MovieModelClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        // récupération des données envoyés des d'autres pages
        String id = getIntent().getStringExtra("Id");
        String title = getIntent().getStringExtra("Title");
        String image = getIntent().getStringExtra("Img");
        String rating = getIntent().getStringExtra("Rate");
        // dans le textview qui a pour id:textViewName on change le texte par le contenu de title
        TextView textV = findViewById(R.id.textViewName);
        //on fait le lien de l'imageView imgV  avec l'id imageView de layout
        ImageView imgV = findViewById(R.id.imageView);
        textV.setText(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_play_arrow_24);
        // le logo de l'application
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#f0c528\">" + getString(R.string.app_name) + "</font>"));
        // téléchargement de l'image dans l'imageView
        Glide.with(context)
                .load(image)
                .into(imgV);
        String data = id;
        // on instancie la class Requestask
        RequestTask requestTask = new RequestTask();
        // exécute la fonction execute
        requestTask.execute(data);
    }

    /**
     * la créaation du menu
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * RequestTask est la class dans la quelle est définie
     * la tache asynchrone qui nous permet de récupérer via url
     * les films
     */
    private class RequestTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... name) {
            String response = requete(name[0]);
            return response;
        }

        /**
         * dans la fonction  requete on passe en parametre une variable name de type string
         * ensuite dans l'url on le concatene pour trouver les films
         * on recupére les données sous forme de json
         */


        private String requete(String name) {
            String response = "";
            try {
                HttpURLConnection connection = null;
                //name est la variable qui va prendre le titre du film
                URL url = new URL("https://imdb-api.com/en/API/Title/k_k6eancl2/" + name + "/Trailer,");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine();
                while (ligne != null) {
                    response += ligne;
                    ligne = bufferedReader.readLine();
                }
                JSONObject toDecode = new JSONObject(response);
                response = decodeJSON(toDecode);
            } catch (UnsupportedEncodingException e) {
                response = "problème d'encodage";
            } catch (MalformedURLException e) {
                response = "problème d'URL ";
            } catch (IOException e) {
                response = "problème de connexion ";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        /**
         * Dans la fonction decodeJSON on récupere le json
         * et on extrait les données
         *
         * @param jso
         * @return
         * @throws Exception
         */
        private String decodeJSON(JSONObject jso) throws Exception {
            String response = "";
            //ici on extrait dans une chaine  de caractere jsoid la description du film
            String jsoid = jso.getString("plot");
            //String jsoid = jso.getString("plotLocal");
            // on concatene le tout dans reponse
            response += jsoid;
            //ici on extrait dans une chaine  de caractere jsLink le lien du film
            JSONObject js = jso.getJSONObject("trailer");
            String jsoLink = js.getString("link");
            // on concatene le tout dans reponse
            response += "---" + jsoLink;
            //ici on extrait dans une chaine  de caractere jsRate le Rate du film
            String jsoRate = jso.getString("imDbRating");
            // on concatene le tout dans reponse
            response += "---" + jsoRate;
            //ici on extrait dans une chaine  de caractere jsoHours la durée du film
            String jsoHours = jso.getString("runtimeStr");
            // on concatene le tout dans reponse
            response += "---" + jsoHours;
            //ici on extrait dans une chaine  de caractere jsoYear l'année du film
            String jsoYear = jso.getString("year");
            // on concatene le tout dans reponse
            response += "---" + jsoYear;
            //ici on extrait dans une chaine  de caractere jsoDirectors les Directeurs  du film
            String jsoDirectors = jso.getString("directors");
            // on concatene le tout dans reponse
            response += "---" + jsoDirectors;

            String jsocontentRating = jso.getString("contentRating");
            // on concatene le tout dans reponse
            response += "---" + jsocontentRating;
            //ici on extrait dans une chaine  de caractere jsDgenres le gere du film
            String jsoDgenres = jso.getString("genres");
            // on concatene le tout dans reponse
            response += "---" + jsoDgenres;
            // les "---" nous permettent de séparer les donnée dans la fonction suivante
            return response;
        }

        /**
         * onPostExecute est la fonction dans laquelle on sépare les données
         * et les mettre dans les textview
         *
         * @param result est la reponse qui a été retourner par la fonction requete
         */

        protected void onPostExecute(String result) {
            // on appelle split(---) pour la séparation des chaines des caracteres
            String[] parts = result.split("---");
            // on recupere la description
            String descPlot = parts[0];
            // on recupere le lien etc.
            link = parts[1];
            String rateM = parts[2];
            String movieH = parts[3];
            String movieY = parts[4];
            String movieD = parts[5];
            String movieAR = parts[6];
            String movieG = parts[7];
            // dans le textview qui a pour id:textViewName2 on change le texte par le contenu de desPlot
            textViewName2 = findViewById(R.id.textViewName2);
            textViewName2.setText(descPlot);
            // dans le textview qui a pour id:rating on change le texte par le contenu de rateM
            textVRate = findViewById(R.id.rating);
            textVRate.setText(rateM);
            // dans le textview qui a pour id:hoursM on change le texte par le contenu de movieH
            textVHours = findViewById(R.id.hoursM);
            textVHours.setText(movieH);
            // dans le textview qui a pour id:yearM on change le texte par le contenu de movieY
            textVYear = findViewById(R.id.yearM);
            textVYear.setText(movieY);
            // dans le textview qui a pour id:director on change le texte par le contenu de director
            textVDirector = findViewById(R.id.director);
            textVDirector.setText(movieD);
            // dans le textview qui a pour id:ageRat on change le texte par le contenu de movieAR
            textVageRat = findViewById(R.id.ageRat);
            textVageRat.setText(movieAR);
            // dans le textview qui a pour id:genre on change le texte par le contenu de movieG
            textVgenre = findViewById(R.id.genre);
            textVgenre.setText(movieG);
        }
    }

    public void btnVideo(View v) {

        // le bouton par lequel on clique pour visionner le film
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browse);
    }
}