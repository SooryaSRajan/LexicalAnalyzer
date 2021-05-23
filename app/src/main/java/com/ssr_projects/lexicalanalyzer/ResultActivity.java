package com.ssr_projects.lexicalanalyzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);


        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(getIntent().getStringExtra("CODE"), this);
        lexicalAnalyzer.AnalyzeCode();
        lexicalAnalyzer.checkBrackets();
        lexicalAnalyzer.setData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}