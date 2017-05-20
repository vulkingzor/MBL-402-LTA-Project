package edu.phoenix.mbl402.LTA;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    Button globalMapBtn;
    Button trailMapBtn;
    Button userProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        globalMapBtn = (Button)findViewById(R.id.btnGlobalMap);
        trailMapBtn = (Button)findViewById(R.id.btnViewMap);
        userProfileBtn = (Button)findViewById(R.id.btnUser);

        globalMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Global Map", Toast.LENGTH_SHORT).show();
            }
        });

        trailMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Trail Map", Toast.LENGTH_SHORT).show();
            }
        });

        userProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "User Profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
