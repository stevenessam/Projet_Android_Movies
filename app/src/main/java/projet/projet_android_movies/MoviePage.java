package projet.projet_android_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MoviePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);

        String title = getIntent().getStringExtra("Title");
        TextView textV=findViewById(R.id.textViewName);
        textV.setText(title);


    }
}