package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.NetworkImageView;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Clientes;
import java.util.List;

public class ClientesAdapter extends RecyclerView.Adapter<ClientesAdapter.ClientesViewHolder> {
    List<Clientes> clientes;
    Context ctx;

    public ClientesAdapter(List<Clientes> clientes, Context ctx) {
        this.clientes = clientes;
        this.ctx=ctx;
    }
    @Override
    public ClientesAdapter.ClientesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_clientes,parent,false);
        ClientesViewHolder viewHolder = new ClientesViewHolder(view,ctx);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClientesAdapter.ClientesViewHolder viewHolder, int i) {
        viewHolder.nome.setText(clientes.get(i).getNome());
        viewHolder.idade.setText(clientes.get(i).getIdade());
        viewHolder.img.setImageUrl(clientes.get(i).getUrl_foto(), VolleySingleton.getmInstance(ctx).getImageLoader());
    }

    public class ClientesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nome, idade, id;
        NetworkImageView img;
        Context ctx;
        ImageButton remove, edit;

        ClientesViewHolder(View itemView, Context ctx) {
            super(itemView);
            this.ctx=ctx;
            itemView.setOnClickListener(this);
            nome = (TextView) itemView.findViewById(R.id.nome);
            idade = (TextView) itemView.findViewById(R.id.idade);
            img = (NetworkImageView) itemView.findViewById(R.id.imageView);
            remove = (ImageButton) itemView.findViewById(R.id.remove);
            edit = (ImageButton) itemView.findViewById(R.id.edit);
            remove.setOnClickListener(this);
            edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(ctx, "Este user já não tem permissões para estar aqui!!", Toast.LENGTH_SHORT).show();
            DataBaseHelper db = new DataBaseHelper(ctx.getApplicationContext());

            switch(view.getId()){
                case R.id.remove:
                    clientes.remove(getPosition());
                    notifyItemRemoved(getPosition());
                    db.removeClienteByName(nome.getText().toString());
                    break;

                case R.id.edit:
                    Intent intentEdit = new Intent(ctx, EditCliente.class);
                    intentEdit.putExtra("nome", nome.getText().toString());
                    ctx.startActivity(intentEdit);
                    break;

                default:
                    Intent intentDetails = new Intent(ctx, DetailsClientes.class);
                    intentDetails.putExtra("nome", nome.getText().toString());
                    ctx.startActivity(intentDetails);
            }
        }
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }
}
