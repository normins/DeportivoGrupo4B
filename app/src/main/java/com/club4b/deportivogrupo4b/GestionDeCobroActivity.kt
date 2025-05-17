package com.club4b.deportivogrupo4b

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
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
        val back2 = findViewById<ImageView>(R.id.back2)
        back2.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)

        }

        //Abrir gestion de cobro con el evento click en boton buscar.

        val btnBuscarCobro = findViewById<Button>(R.id.btnBuscarCobro)
        val grupoDatosClienteCobro = findViewById<LinearLayout>(R.id.grupoDatosClienteCobro)

            btnBuscarCobro.setOnClickListener {
                // Mostrar el grupo de datos sin validar el documento
                grupoDatosClienteCobro.visibility = View.VISIBLE
                btnBuscarCobro.visibility = View.GONE // Oculta el botón Buscar
            }

        val btnConfirmarCobro= findViewById<Button>(R.id.btnConfirmarCobro)

        btnConfirmarCobro.setOnClickListener {
            Toast.makeText(this, "Pago registrado correctamente", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                // Restaurar el estado inicial sin cerrar el Activity
                grupoDatosClienteCobro.visibility = View.GONE
                btnBuscarCobro.visibility = View.VISIBLE
            }, 1500)
        }


        val btnCancelarCobro= findViewById<Button>(R.id.btnCancelarCobro)

        btnCancelarCobro.setOnClickListener {
             Toast.makeText(this, "El pago no ha sido registrado", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
            // Restaurar el estado inicial sin cerrar el Activity
                grupoDatosClienteCobro.visibility = View.GONE
                btnBuscarCobro.visibility = View.VISIBLE
                }, 1500)

            }
    }
}



