package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Users;

public class DetailsUser extends AppCompatActivity {

    TextView id, name, level, password, login;
    DataBaseHelper db;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        db = new DataBaseHelper(getApplicationContext());
        login = (TextView) findViewById(R.id.loginView);
        password = (TextView) findViewById(R.id.passwordView);
        name = (TextView) findViewById(R.id.nomeView);
        level = (TextView) findViewById(R.id.levelView);

        Intent intent = getIntent();
        String loginT = intent.getStringExtra("login");
        user = db.getUserToEdit(loginT);
        login.setText(user.getLogin());
        password.setText(user.getPassword());
        level.setText(user.getLevel());
        name.setText(user.getNome());
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}