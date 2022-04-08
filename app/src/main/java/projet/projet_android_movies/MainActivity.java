package projet.projet_android_movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity implements RecyclerViewlnterface{



    private static String JSON_URL="https://imdb-api.com/en/API/MostPopularMovies/k_h2ntsk74";

    List<MovieModelClass>   movieList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieList= new ArrayList<>();
        recyclerView= findViewById(R.id.recyclerView);

        GetData getData = new GetData();
        getData.execute() ;

    }


    public void btn (View v){

        Intent intent = new Intent (MainActivity.this, SearchMovie.class);
        startActivity(intent);
    }

    public  class GetData extends AsyncTask<String,String,String>{

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
            }catch (Exception e){
                e.printStackTrace();

            }
            return current;
        }


        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("id"));
                    model.setTitle(jsonObject1.getString("title"));
                    model.setRating(jsonObject1.getString("imDbRating"));
                    model.setImg(jsonObject1.getString("image"));

                    movieList.add(model);

                }

            }catch (JSONException e){
                e.printStackTrace();
            }

            PutDataIntoRecyclerView( movieList);

        }
    }


    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList){


        Adaptery adaptery = new Adaptery(this,movieList,this);
        GridLayoutManager gr = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gr);

        recyclerView.setAdapter(adaptery);

    }


    @Override
    public void onItemClick(int position) {

//        System.out.println(movieList.get(position).getId());

        Intent i = new Intent(MainActivity.this,MoviePage.class);
        i.putExtra("Id",movieList.get(position).getId());
        i.putExtra("Title",movieList.get(position).getTitle());
        i.putExtra("Img",movieList.get(position).getImg());

        startActivity(i);


    }




}