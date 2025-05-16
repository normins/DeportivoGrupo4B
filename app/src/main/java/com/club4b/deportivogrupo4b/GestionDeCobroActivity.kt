package com.club4b.deportivogrupo4b

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GestionDeCobroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_decobro)

        //Spinner forma de pago
        val spinner = findViewById<Spinner>(R.id.spFormaPagoCobro)

        // Lista de opciones

        val opcionesCobro = listOf("Efectivo", "3 cuotas", "6 cuotas")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesCobro)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //Retornar con la imagen de flecha
        val back1 = findViewById<ImageView>(R.id.back1)
        back1.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)

        }
    }
}



