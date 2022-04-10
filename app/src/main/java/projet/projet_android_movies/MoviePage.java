package projet.projet_android_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
    TextView textViewName2;
    String link;
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
        context=this;
        String id = getIntent().getStringExtra("Id");
        String title = getIntent().getStringExtra("Title");
        String image = getIntent().getStringExtra("Img");
        String rating = getIntent().getStringExtra("Rate");
        TextView textV=findViewById(R.id.textViewName);

        ImageView imgV = findViewById(R.id.imageView);
        textV.setText(title);


        Glide.with(context)
                .load(image)
                .into(imgV);
        String data = id;
        RequestTask requestTask = new RequestTask();
        requestTask.execute(data);



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



//-------------------Changement des couleur-------------------------------------------------------




















//---------------End----Changement des couleur-------------------------------------------------------




    private class RequestTask extends AsyncTask<String, String, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
//  lance la requète
        protected String doInBackground(String... name) {
            String response = requete(name[0]);
            return response;
        }
        private String requete(String name) {
            String response = "";
            try {
                HttpURLConnection connection = null;
                URL url = new URL("https://imdb-api.com/en/API/Title/k_xhs6fv51/" + name+"/Trailer,");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine() ;
                while (ligne!= null){
                    response+=ligne;
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

        private String decodeJSON(JSONObject jso) throws Exception {
            String response = "";

            String jsoid = jso.getString("plot");
            //String jsoid = jso.getString("plotLocal");
            response += jsoid;

            JSONObject js = jso.getJSONObject("trailer");
            String jsoLink = js.getString("link");
            response += "---" + jsoLink;

            String jsoRate = jso.getString("imDbRating");
            response += "---" + jsoRate;

            String jsoHours = jso.getString("runtimeStr");
            response += "---" + jsoHours;

            String jsoYear = jso.getString("year");
            response += "---" + jsoYear;

            String jsoDirectors = jso.getString("directors");
            response += "---" + jsoDirectors;

            String jsocontentRating = jso.getString("contentRating");
            response += "---" + jsocontentRating;

            String jsoDgenres = jso.getString("genres");
            response += "---" + jsoDgenres;

            return response;
        }
        // Méthode appelée lorsque la tâche de fond sera terminée
//  Affiche le résultat
        protected void onPostExecute(String result) {

            String[] parts = result.split("---");
            String descPlot = parts[0];
             link = parts[1];
            String rateM = parts[2];
            String movieH = parts[3];
            String movieY = parts[4];
            String movieD = parts[5];
            String movieAR = parts[6];
            String movieG = parts[7];

            textViewName2 =findViewById(R.id.textViewName2);
            textViewName2.setText(descPlot);

            textVRate=findViewById(R.id.rating);
            textVRate.setText(rateM);

            textVHours=findViewById(R.id.hoursM);
            textVHours.setText(movieH);

            textVYear=findViewById(R.id.yearM);
            textVYear.setText(movieY);

            textVDirector=findViewById(R.id.director);
            textVDirector.setText(movieD);

            textVageRat=findViewById(R.id.ageRat);
            textVageRat.setText(movieAR);

            textVgenre=findViewById(R.id.genre);
            textVgenre.setText(movieG);


        }

    }



    public void btnVideo (View v){

        Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( link) );

        startActivity( browse );
    }




}