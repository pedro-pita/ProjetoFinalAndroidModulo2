package com.zicronofandroid.projetofinalmodulo2android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.zicronofandroid.projetofinalmodulo2android.helper.DataBaseHelper;
import com.zicronofandroid.projetofinalmodulo2android.model.Imoveis;
import java.util.List;

public class ImoveisAdapter extends RecyclerView.Adapter<ImoveisAdapter.ImoveisViewHolder> {
    List<Imoveis> imoveis;
    Context ctx;
    DataBaseHelper db;
    Imoveis imovel;
    int id;

    public ImoveisAdapter(List<Imoveis> imoveis,Context ctx) {
        this.imoveis = imoveis;
        this.ctx=ctx;
    }

    @Override
    public ImoveisAdapter.ImoveisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_imoveis,parent,false);
        ImoveisViewHolder ivh = new ImoveisViewHolder(v, ctx);
        return ivh;
    }

    @Override
    public void onBindViewHolder(ImoveisAdapter.ImoveisViewHolder viewHolder, int i) {
        viewHolder.localizacao.setText(imoveis.get(i).getLocalizacao());
        viewHolder.tipologia.setText(imoveis.get(i).getTipologia());
        viewHolder.descricao.setText(imoveis.get(i).getDescricao());
        viewHolder.img.setImageUrl(imoveis.get(i).getUrl_foto(), VolleySingleton.getmInstance(ctx).getImageLoader());
    }

    public class ImoveisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView localizacao, tipologia, descricao;
        Context ctx;
        NetworkImageView img;
        ImageButton remove, edit;

        ImoveisViewHolder(View itemView, Context ctx) {
            super(itemView);
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            localizacao = (TextView) itemView.findViewById(R.id.localizacaoView);
            tipologia = (TextView) itemView.findViewById(R.id.tipologiaView);
            img = (NetworkImageView) itemView.findViewById(R.id.imageView);
            descricao = (TextView) itemView.findViewById(R.id.descricaoView);
            remove = (ImageButton) itemView.findViewById(R.id.remove);
            edit = (ImageButton) itemView.findViewById(R.id.edit);
            remove.setOnClickListener(this);
            edit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            db = new DataBaseHelper(ctx.getApplicationContext());

            switch(view.getId()){
                case R.id.remove:
                    imoveis.remove(getPosition());
                    notifyItemRemoved(getPosition());
                    db.removeImovelByDesc(descricao.getText().toString());
                    break;
                case R.id.edit:
                    Intent intent = new Intent(ctx, EditImovel.class);
                    intent.putExtra("descricao", descricao.getText());
                    ctx.startActivity(intent);
                    break;
                default:
                    Intent intentDetails = new Intent(ctx, DetailsImoveis.class);
                    intentDetails.putExtra("descricao", imovel.getDescricao().toString());
                    ctx.startActivity(intentDetails);
            }
        }
    }

    @Override
    public int getItemCount() {
        return imoveis.size();
    }
}
