package com.club4b.deportivogrupo4b

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class ListadoDeVencimientosActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_de_vencimientos)


        val editFecha = findViewById<EditText>(R.id.editFecha)

        editFecha.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                editFecha.setText(String.format("%02d/%02d/%04d", d, m + 1, y))
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }


        //Retornar con la imagen de flecha
        val back2 = findViewById<ImageView>(R.id.back2)
        back2.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)
        }


        //devuelve valores en la tabla de vencimientos- habra que modificarla cuando se conecte con BD.

        val recycler = findViewById<RecyclerView>(R.id.recyclerVencimientos)
        val btnBuscarFecha = findViewById<Button>(R.id.btnBuscarFecha)

        // Configura el RecyclerView una sola vez
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        btnBuscarFecha.setOnClickListener {
            // Lista de prueba (demo)
            val listaDemo = listOf(
                Vencimiento("35703124", "Gilda", "Morgante"),
                Vencimiento("29554441", "Carlos", "López"),
                Vencimiento("31122333", "Lucía", "Pérez")
            )

            // Asigna el adapter al RecyclerView con los datos demo
            recycler.adapter = VencimientoAdapter(listaDemo)

            // Muestra el RecyclerView (por si estaba oculto)
            recycler.visibility = View.VISIBLE
        }
    }
}
