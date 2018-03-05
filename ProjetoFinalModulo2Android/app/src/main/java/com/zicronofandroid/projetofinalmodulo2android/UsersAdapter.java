package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Users;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.AlbumViewHolder> {

    List<Users> users;
    Context ctx;

    @Override
    public UsersAdapter.AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        AlbumViewHolder avh = new AlbumViewHolder(v,ctx);
        return avh;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.AlbumViewHolder holder, int position) {
        holder.nome.setText(users.get(position).getNome());
        holder.login.setText(users.get(position).getLogin());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public UsersAdapter(List<Users> users, Context ctx) {
        this.users = users;
        this.ctx = ctx;
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context ctx;
        TextView id, login, nome;
        ImageButton remove, edit;

        AlbumViewHolder(View itemView, Context ctx) {
            super(itemView);
            this.ctx = ctx;
            login = (TextView) itemView.findViewById(R.id.loginView);
            nome = (TextView) itemView.findViewById(R.id.nomeView);
            remove = (ImageButton) itemView.findViewById(R.id.remove);
            edit = (ImageButton) itemView.findViewById(R.id.edit);
            itemView.setOnClickListener(this);
            remove.setOnClickListener(this);
            edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            DataBaseHelper db;
            switch(view.getId()){
                case R.id.remove:
                    db = new DataBaseHelper(ctx);
                    users.remove(getPosition());
                    notifyItemRemoved(getPosition());
                    db.removeUserByLogin(login.getText().toString());
                    break;
                case R.id.edit:
                    Intent intentEdit = new Intent(ctx, EditUser.class);
                    intentEdit.putExtra("login", login.getText().toString());
                    ctx.startActivity(intentEdit);
                    break;
                default:
                    Intent intentDetails = new Intent(ctx, DetailsUser.class);
                    intentDetails.putExtra("login", login.getText().toString());
                    ctx.startActivity(intentDetails);
            }
        }
    }
}
