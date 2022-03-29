package projet.projet_android_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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



    Button bt1;
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        movieList= new ArrayList<>();
        recyclerView= findViewById(R.id.recyclerView);



    }
    public void btn (View v){

        EditText editTextTitle = findViewById(R.id.editTextTitle);
        String titleText= editTextTitle.getText().toString();
        JSON_URL="https://imdb-api.com/en/API/SearchMovie/k_dgd1pq04/"+titleText;
        GetData getData = new GetData();
        getData.execute() ;

        bt1=(Button)findViewById(R.id.buttonSearch);
        bt2=(Button)findViewById(R.id.buttonSearchAgain);
        bt1.setVisibility(View.INVISIBLE);
        bt2.setVisibility(View.VISIBLE);


    }
    public void btn2 (View v){


        bt1.setVisibility(View.VISIBLE);
        bt2.setVisibility(View.INVISIBLE);
        recreate();


    }

    public  class GetData extends AsyncTask<String,String,String> {

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
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("id"));
                    model.setTitle(jsonObject1.getString("title"));
                    model.setRating(jsonObject1.getString("description"));
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


        Adaptery2 adaptery = new Adaptery2(this,movieList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adaptery);

    }


    @Override
    public void onItemClick(int position) {

//        System.out.println(movieList.get(position).getId());

        Intent i = new Intent(SearchMovie.this,MoviePage.class);

        i.putExtra("Title",movieList.get(position).getTitle());
        i.putExtra("Img",movieList.get(position).getImg());
        startActivity(i);


    }

}