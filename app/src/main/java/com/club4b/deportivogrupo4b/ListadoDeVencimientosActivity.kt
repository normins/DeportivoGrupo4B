package com.club4b.deportivogrupo4b

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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

    }
}
