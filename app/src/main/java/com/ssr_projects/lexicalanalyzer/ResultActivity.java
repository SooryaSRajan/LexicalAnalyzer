package com.ssr_projects.lexicalanalyzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        ScrollView scrollLayout = findViewById(R.id.scroll_view);

        FloatingActionButton floatingActionButton = findViewById(R.id.scroll_down);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View lastChild = scrollLayout.getChildAt(scrollLayout.getChildCount() - 1);
                int bottom = lastChild.getBottom() + scrollLayout.getPaddingBottom();
                int sy = scrollLayout.getScrollY();
                int sh = scrollLayout.getHeight();
                int delta = bottom - (sy + sh);

                scrollLayout.smoothScrollBy(0, delta);
            }
        });


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