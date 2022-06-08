package uk.ac.abertay.cmp309.morse2flash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class info extends AppCompatActivity { //class to launch info xml layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info); //set layout to info xml

        Button back = findViewById(R.id.back); //button to return to main page

        back.setOnClickListener(new View.OnClickListener() { //listener for main page button
            @Override
            public void onClick(View v) {
                startActivity(new Intent(info.this, MainActivity.class)); //start main page activity if user clicks
            }
        });
    }
}
