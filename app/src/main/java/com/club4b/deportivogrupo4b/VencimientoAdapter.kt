package com.club4b.deportivogrupo4b

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

class VencimientoAdapter(private val lista: List<Vencimiento>) :
    RecyclerView.Adapter<VencimientoAdapter.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNroDoc: TextView = itemView.findViewById(R.id.txtNroDoc)
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val txtApellido: TextView = itemView.findViewById(R.id.txtApellido)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutId = R.layout.item_vencimiento
        val vista = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            // Fila de cabecera
            holder.txtNroDoc.text = "Nro Doc"
            holder.txtNombre.text = "Nombre"
            holder.txtApellido.text = "Apellido"
            holder.txtNombre.setTypeface(null, android.graphics.Typeface.BOLD)
            holder.txtApellido.setTypeface(null, android.graphics.Typeface.BOLD)
            holder.txtNroDoc.setTypeface(null, android.graphics.Typeface.BOLD)
        } else {
            val venc = lista[position - 1] // -1 porque la cabecera ocupa la posición 0
            holder.txtNroDoc.text = venc.nroDoc
            holder.txtNombre.text = venc.nombre
            holder.txtApellido.text = venc.apellido
        }
    }

    override fun getItemCount(): Int = lista.size + 1 // +1 por la cabecera
}