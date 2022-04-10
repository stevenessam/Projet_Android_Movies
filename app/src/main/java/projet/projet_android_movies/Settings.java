package projet.projet_android_movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    /**
     * Declarataion des variables de type {@link Button},
     * {@link TextView} et {@link EditText}
     */
    Button wifiPanel;
    Button saveButton;
    TextView textView;
    EditText editText;

    /**
     * Declarataion des variables de type {@link String}
     */
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private String text;

    int NightMode;
    /**
     * Declarataion des variables de type {@link SharedPreferences} et {@link SharedPreferences.Editor}
     */
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        /**
         * getSupportActionBar affiche la flèche pour revenir en arrière
         */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * wifiPanel est le button qui est cree dans le layout activity_settings
         */
        wifiPanel = findViewById(R.id.wifiBtn);

        /**
         * On cree un action sur le button de type setOnClickListener pour permetre de ouvrire
         * ou fermer le wifi ou 3g
         */
        wifiPanel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                startActivity(new Intent(android.provider.Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
            }
        });


        /**
         * recuperation le MODE_PRIVATE par getSharedPreferences
         */
        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        NightMode = sharedPreferences.getInt("NightModeInt", 1);
        AppCompatDelegate.setDefaultNightMode(NightMode);

        /**
         * switch1 est le Switch qui est cree dans le layout activity_settings
         */
        Switch switch1 = findViewById(R.id.switch1);

        /**
         * On cree un action sur le switch de type setOnCheckedChangeListener pour permetre
         * de changer le theme de la application en nuit ou jour
         */
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//------------------------------------Save name-------------------------------------------------------
        /**
         *  les variables declare par leurs id a partir de la layout activity_settings
         */
        textView = (TextView) findViewById(R.id.nameTV);
        editText = (EditText) findViewById(R.id.edit1);
        saveButton = (Button) findViewById(R.id.save_button);

        /**
         * On cree un action sur le button de type setOnClickListener de definir un text au textview
         * et de sauvgarder le text dans l'application
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(editText.getText().toString());
                saveData();
            }
        });

        loadData();
        updateViews();

//------------------------------------Save name-------------------------------------------------------
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_play_arrow_24);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#f0c528\">" + getString(R.string.app_name) + "</font>"));
    }

    //------------------------------------Save name-------------------------------------------------------

    /**
     * Methode qui permet de sauvgarder le text entre par l'utilisateur dans l' application
     */
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, textView.getText().toString());

        editor.apply();

    }
    /**
     * Methode qui permet de afficher le text entre par l'utilisateur dans le textview
     */
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
    }
    /**
     * Methode qui permet de mettre à jour les vues
     */
    public void updateViews() {
        textView.setText(text);
    }


//------------------------------------Save name-------------------------------------------------------

    /**
     * Methode qui permet de sauvgarder le mode nuit ou jour qui est choisi par l utilisateur
     * quand l'application est fermer
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        NightMode = AppCompatDelegate.getDefaultNightMode();

        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putInt("NightModeInt", NightMode);
        editor.apply();
    }
    /**
     * Méthode qui permet à la flèche de l'action bar de revenir vers l'arrière
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
}