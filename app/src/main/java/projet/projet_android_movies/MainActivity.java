package projet.projet_android_movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity implements RecyclerViewlnterface {


    private static String JSON_URL = "https://imdb-api.com/en/API/MostPopularMovies/k_7pc3ijdc";

    List<MovieModelClass> movieList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#f0c528\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_play_arrow_24);

        /**
         * declaration de la varialble bNV de type {@link BottomNavigationView}
         * cette methode permet de deplacer l utilisateur vers les diffentes page
         * comme page home et page recherche film
         *
         */
        BottomNavigationView bNV = findViewById(R.id.bottom_navMenu);
        bNV.setSelectedItemId(R.id.mainActivity);
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

        GetData getData = new GetData();
        getData.execute();
    }

    /**
     * methode qui permet de cree le menu cde navigation
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    /**
     * permet de de retourner a l arrier quand on clique sur la flesh de la action bar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        /**
         * la redirection vers Settings si on clique sur l'item de menu qui a l'id :action_settings
         */
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * boutton pour la redirection vers SearchMovie
     * @param v
     */
    public void btn(View v) {
        Intent intent = new Intent(MainActivity.this, SearchMovie.class);
        startActivity(intent);
    }

    /**
     * GetData est la class dans la quelle est d??finie
     * la tache asynchrone qui nous permet de r??cup??rer via url
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
         * on recup??re les donn??es sous forme de json
         */

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    // instanciation d'un MovieModelClass
                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("id"));
                    model.setTitle(jsonObject1.getString("title"));
                    model.setRating(jsonObject1.getString("imDbRating"));
                    model.setImg(jsonObject1.getString("image"));
                    movieList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(movieList);
        }
    }

    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList) {

        Adaptery adaptery = new Adaptery(this, movieList, this);
        GridLayoutManager gr = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gr);
        recyclerView.setAdapter(adaptery);

    }



    /**
     *  En cliquant sur un film l'Intent i nous renvoie dans la page  MoviePage ainsi que l'id, le title,et l'image du film
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        // instanciation d'un Intent
        Intent i = new Intent(MainActivity.this, MoviePage.class);
        //transfert les donn??es vers MoviePage
        i.putExtra("Id", movieList.get(position).getId());
        i.putExtra("Title", movieList.get(position).getTitle());
        i.putExtra("Img", movieList.get(position).getImg());
        startActivity(i);
    }


}