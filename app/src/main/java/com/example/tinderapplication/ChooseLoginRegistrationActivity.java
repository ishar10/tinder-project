package com.example.tinderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseLoginRegistrationActivity extends AppCompatActivity {
private Button mlogin,mregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_registration);
        mlogin=(Button)findViewById(R.id.login);
        mregister=(Button)findViewById(R.id.register);

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseLoginRegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        mregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseLoginRegistrationActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }
}
