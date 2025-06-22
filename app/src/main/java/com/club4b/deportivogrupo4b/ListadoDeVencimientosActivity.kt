package com.club4b.deportivogrupo4b

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.print.PrintManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_de_vencimientos)

        data class Vencimiento(
            val nroDocumento: String,
            val nombre: String,
            val apellido: String
        )



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


        //Devuelve valores en la tabla de vencimientos-

        val recycler = findViewById<RecyclerView>(R.id.recyclerVencimientos)
        val btnBuscarFecha = findViewById<Button>(R.id.btnBuscarFecha)
        val btnImprimir=findViewById<Button>(R.id.btnDescargar)

        // Configura el RecyclerView una sola vez
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        btnBuscarFecha.setOnClickListener {

            val buscarFecha = editFecha.text.toString()

            if (buscarFecha.isBlank()) {
                Toast.makeText(this, "Ingresá una fecha para buscar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = UserDBHelper(this)
            val listaVencimientos = dbHelper.listadoVencimientos(buscarFecha)

            if (listaVencimientos.isEmpty()) {
                Toast.makeText(this, "No hay vencimientos para la fecha ingresada", Toast.LENGTH_SHORT).show()
                recycler.visibility = View.GONE
            } else {
                recycler.adapter = VencimientoAdapter(listaVencimientos)
                recycler.visibility = View.VISIBLE
                btnImprimir.visibility=View.VISIBLE

            }
        }

        btnImprimir.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Descarga")
                .setMessage("Se descargara el archivo")
                .setPositiveButton("OK", null)
                .show()
        }


        }
    }



