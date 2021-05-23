package com.ssr_projects.lexicalanalyzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText codeTextBox;
    FloatingActionButton submitButton;
    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        codeTextBox = findViewById(R.id.code_text_box);
        submitButton = findViewById(R.id.submit_button);
        submitButton.setEnabled(false);

    }

    @Override
    protected void onStart() {
        super.onStart();

        codeTextBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0 ){
                    submitButton.setEnabled(false);
                }
                else{
                    submitButton.setEnabled(true);
                }
                Log.e(TAG, "onTextChanged: " + charSequence + " " + i + " " + i1 + " " + i2 );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "Generating Report", Toast.LENGTH_SHORT);

                View toastView = toast.getView();
                toastView.getBackground().setColorFilter(getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

                TextView text = toastView.findViewById(android.R.id.message);
                text.setTextColor(getColor(R.color.colorWhite));

                toast.show();

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("CODE", codeTextBox.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        submitButton = null;
        codeTextBox = null;
    }
}