package com.example.projetows

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FilmeLista(private val context: Activity, internal var Filme: List<Filme>) :
    ArrayAdapter<Filme>(context, R.layout.filme_layout, Filme) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.filme_layout, null, true)

        val txtNome = listViewItem.findViewById(R.id.filmeNomeId) as TextView
        val txtGenero = listViewItem.findViewById(R.id.filmeGeneroId) as TextView

        val TMP = Filme[position]
        txtNome.text = TMP.nome
        txtGenero.text = TMP.genero

        return listViewItem
    }
}
