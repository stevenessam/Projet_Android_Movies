package projet.projet_android_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MoviePage extends AppCompatActivity {
        Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;
        String title = getIntent().getStringExtra("Title");
        String image = getIntent().getStringExtra("Img");
        TextView textV=findViewById(R.id.textViewName);
        ImageView imgV = findViewById(R.id.imageView);
        textV.setText(title);
        Glide.with(context)
                .load(image)
                .into(imgV);

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
}