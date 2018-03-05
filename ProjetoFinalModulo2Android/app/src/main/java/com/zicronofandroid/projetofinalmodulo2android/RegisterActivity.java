package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button register;
    private TextView Login;
    private EditText insertName, insertLogin, insertPassword;
    public DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        db = new DataBaseHelper(this);
        register = (Button)findViewById(R.id.btnRegistar);
        Login = (TextView)findViewById(R.id.goLogin);
        insertLogin = (EditText)findViewById(R.id.insertLogin);
        insertPassword = (EditText)findViewById(R.id.insertPassword);
        insertName = (EditText)findViewById(R.id.insertName);
        register.setOnClickListener(this);
        Login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnRegistar:
                register();
                break;
            case R.id.goLogin:
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private void register(){
        String nome = insertName.getText().toString();
        String login = insertLogin.getText().toString();
        String pass = insertPassword.getText().toString();
        if(login.isEmpty() || pass.isEmpty() || nome.isEmpty()){
            displayToast("Por favor, preencha todos os dados.");
        }else{
            if(db.verifyUserByRegiste(login) == false){
                Toast.makeText(getApplicationContext(), "Esta conta não existe!", Toast.LENGTH_SHORT).show();
                db.addUser(login,pass,nome,"2");
                displayToast("Este utilizador foi registado!");
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Esta conta já existe!", Toast.LENGTH_SHORT).show();
            }
            /*db.addUser(login,pass,nome,3);
            displayToast("Este utilizador foi registado!");
            finish();*/
        }
    }

    private void displayToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
