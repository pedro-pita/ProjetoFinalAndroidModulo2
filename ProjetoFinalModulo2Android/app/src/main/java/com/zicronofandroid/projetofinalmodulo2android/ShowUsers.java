package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Users;
import java.util.List;

public class ShowUsers extends AppCompatActivity{

    public static DataBaseHelper db;
    Session ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data);

        db = new DataBaseHelper(getApplicationContext());
        ss = new Session(this);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        List<Users> todosUsers = db.getAllUsers();

        UsersAdapter adapter = new UsersAdapter(todosUsers, this);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        irParaConfig();
        return false;
    }

    void irParaConfig() {
        finish();
        startActivity(new Intent(ShowUsers.this, ManageUsers.class));
    }
    private void logout(){
        ss.setLoggedin(false, null);
        finish();
        startActivity(new Intent(ShowUsers.this, MainActivity.class));
    }
}