package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Users;

public class EditUser extends AppCompatActivity implements View.OnClickListener {

    Button voltar, cofirmEdit;
    EditText nome, login, password, level;
    DataBaseHelper db;
    String oldLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user);

        db = new DataBaseHelper(getApplicationContext());
        nome = (EditText) findViewById(R.id.name);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        level = (EditText) findViewById(R.id.level);

        voltar = (Button) findViewById(R.id.voltar);
        cofirmEdit = (Button) findViewById(R.id.cofirmEdit);
        voltar.setOnClickListener(this);
        cofirmEdit.setOnClickListener(this);

        Intent intent = getIntent();
        oldLogin = intent.getStringExtra("login");
        Toast.makeText(getBaseContext(), oldLogin, Toast.LENGTH_SHORT).show();
        loadUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cofirmEdit:
                confirmEdit();
                break;
            case R.id.voltar:
                voltar();
                break;
        }
    }

    private void loadUser() {
        Users user = db.getUserToEdit(oldLogin);
        nome.setText(user.getNome());
        login.setText(user.getLogin());
        password.setText(user.getPassword());
        level.setText(user.getLevel());
    }

    private void confirmEdit() {
        if ((nome.length() != 0) && (login.length() != 0) && (password.length() != 0) && (level.length() != 0)) {
            db.updateUser(oldLogin, login.getText().toString(), password.getText().toString(), nome.getText().toString(), level.getText().toString());
            Toast.makeText(getBaseContext(), "Valores modificados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    private void voltar() {
        finish();
        startActivity(new Intent(EditUser.this, ShowUsers.class));
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(EditUser.this, ShowUsers.class));
    }
}
