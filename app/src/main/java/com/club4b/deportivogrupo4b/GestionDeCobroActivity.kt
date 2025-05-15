package com.club4b.deportivogrupo4b

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.club4b.deportivogrupo4b.R.id.spFormaPago

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




    }

}



