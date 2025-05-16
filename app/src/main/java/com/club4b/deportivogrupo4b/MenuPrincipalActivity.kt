package com.club4b.deportivogrupo4b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuPrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        val btnRegistrarCliente = findViewById<Button>(R.id.btnRegistrarCliente)

        btnRegistrarCliente.setOnClickListener {
            val intent = Intent(this, RegistroClienteActivity::class.java)
            startActivity(intent)
        }

        val btnCobrarCuota= findViewById<Button>(R.id.btnCobrarCuota)

        btnCobrarCuota.setOnClickListener {
            val intent = Intent(this, GestionDeCobroActivity::class.java)
            startActivity(intent)

        }

        val btnListarVencimientos= findViewById<Button>(R.id.btnListarVencimientos)

        btnListarVencimientos.setOnClickListener {
            val intent = Intent(this,ListadoDeVencimientosActivity::class.java)
            startActivity(intent)
        }
    }
}