package projet.projet_android_movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchMovie extends AppCompatActivity implements RecyclerViewlnterface {
    private static String JSON_URL;
    List<MovieModelClass> movieList;
    RecyclerView recyclerView;
    int i = 0;
    ImageButton bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#f0c528\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_play_arrow_24);
        BottomNavigationView bNV = findViewById(R.id.bottom_navMenu);


        /**
         * declaration de la varialble bNV de type {@link BottomNavigationView}
         * cette methode permet de deplacer l utilisateur vers les diffentes page
         * comme page home et page recherche film
         *
         */
        bNV.setSelectedItemId(R.id.searchMovie);
        bNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mainActivity:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.searchMovie:
                        startActivity(new Intent(getApplicationContext(), SearchMovie.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
    }

    // le bouton de recherche
    public void btn(View v) {
        if (i == 0) {
            EditText editTextTitle = findViewById(R.id.editTextTitle);
            String titleText = editTextTitle.getText().toString();
            JSON_URL = "https://imdb-api.com/en/API/SearchMovie/k_dl23ichl/" + titleText;
            GetData getData = new GetData();
            getData.execute();
            i++;
        } else if (i == 1) {
            recreate();
            i = 0;
        }
    }

    /**
     * GetData est la class dans la quelle est définie
     * la tache asynchrone qui nous permet de récupérer via url
     * les films
     */
    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        /**
         * dans la fonction  onPostExecute on passe en parametre une variable name de type string
         * ensuite dans l'url on le concatene pour trouver les films
         * on recupére les données sous forme de json
         */

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    // instanciation d'un MovieModelClass et récupération des données
                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("id"));
                    model.setTitle(jsonObject1.getString("title"));
                    model.setRating(jsonObject1.getString("description"));
                    model.setImg(jsonObject1.getString("image"));
                    movieList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(movieList);
        }
    }

    //Affichage des films dans un recyclerview
    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList) {
        Adaptery2 adaptery = new Adaptery2(this, movieList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptery);
    }
    // en cliquant sur un film l'Intent i nous renvoie dans la page  MoviePage ainsi que l'id, le title,et l'image du film
    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(SearchMovie.this, MoviePage.class);
        i.putExtra("Id", movieList.get(position).getId());
        i.putExtra("Title", movieList.get(position).getTitle());
        i.putExtra("Img", movieList.get(position).getImg());
        startActivity(i);
    }
}