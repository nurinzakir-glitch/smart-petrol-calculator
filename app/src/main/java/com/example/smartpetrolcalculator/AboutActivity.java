package com.example.smartpetrolcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    TextView txtGithub;
    Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        Toolbar toolbarAbout = findViewById(R.id.toolbarAbout);
        setSupportActionBar(toolbarAbout);

        txtGithub = findViewById(R.id.txtGithub);
        btnBackHome = findViewById(R.id.btnBackHome);

        txtGithub.setMovementMethod(LinkMovementMethod.getInstance());

        btnBackHome.setOnClickListener(v -> {
            Toast.makeText(AboutActivity.this, "Back clicked!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AboutActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_home) {
            Toast.makeText(this, "Home clicked!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AboutActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.menu_calculate) {
            Toast.makeText(this, "Calculate clicked!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AboutActivity.this, CalculateActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.menu_about) {
            Toast.makeText(this, "You are already on About page", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_share) {
            Toast.makeText(this, "Share clicked!", Toast.LENGTH_SHORT).show();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Smart Petrol Calculator");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Try this Smart Petrol Cost Calculator with BUDI MADANI Rebate.");
            startActivity(Intent.createChooser(shareIntent, "Share using"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}