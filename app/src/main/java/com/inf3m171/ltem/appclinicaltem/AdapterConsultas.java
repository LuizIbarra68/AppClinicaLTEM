package com.inf3m171.ltem.appclinicaltem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inf3m171.ltem.appclinicaltem.model.Consulta;

import java.util.List;

/**
 * Created by tamir on 01/10/2018.
 */

public class AdapterConsultas extends BaseAdapter{
    private Context contexto;
    private List<Consulta> listaDeConsultas;
    private LayoutInflater inflater;
    public AdapterConsultas(Context context, List<Consulta> list){
        this.contexto = context;
        this.listaDeConsultas = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaDeConsultas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaDeConsultas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaDeConsultas.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Suporte item;

        if ( convertView == null){
            convertView = inflater.inflate(R.layout.layout_lista_consultas,null);
            item = new Suporte();
            item.fundo = (LinearLayout) convertView.findViewById(R.id.layout_lista_fundo);
            item.tvNome = (TextView) convertView.findViewById(R.id.layout_lista_nome_medico);
            item.tvData = (TextView) convertView.findViewById(R.id.layout_lista_data);
            item.tvHorario = (TextView) convertView.findViewById(R.id.layout_lista_horario);
            item.ivLogo = (ImageView) convertView.findViewById(R.id.layout_lista_foto);

            convertView.setTag(item);
        }else {
            item = (Suporte) convertView.getTag();


        }
        Consulta con = listaDeConsultas.get(position);
        item.tvNome.setText( con.getMedico());
        item.tvData.setText( String.valueOf(con.getData()));
        item.tvHorario.setText( String.valueOf(con.getHorario()));



        return convertView;
    }

    private class Suporte{
        LinearLayout fundo;
        TextView tvNome, tvData, tvHorario;
        ImageView ivLogo;
    }
}
