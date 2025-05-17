package com.club4b.deportivogrupo4b

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Log.*
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.club4b.deportivogrupo4b.R.id.spFormaPago
import com.club4b.deportivogrupo4b.R.id.spTipoCliente


class RegistroClienteActivity: AppCompatActivity() {

    // Lista de documentos ya registrados (simulado)
    private val clientesRegistrados = listOf("12345678", "87654321")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)

        val etDocumento = findViewById<EditText>(R.id.etDocumento)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val grupoDatosCliente = findViewById<LinearLayout>(R.id.grupoDatosCliente)

        btnBuscar.setOnClickListener {
            val docIngresado = etDocumento.text.toString()

            if (clientesRegistrados.contains(docIngresado)) {
                Toast.makeText(this, "Cliente ya registrado", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    finish() // volver al menú
                }, 1500)
            } else {
                grupoDatosCliente.visibility = View.VISIBLE
                btnBuscar.visibility = View.GONE // Oculta el botón Buscar
                etDocumento.isEnabled = false
            }
        }


        // Forma de pago
        val formaPagoSpinner = findViewById<Spinner>(spFormaPago)
        val formaPago = arrayOf("Efectivo", "3 Cuotas", "6 Cuotas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, formaPago)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        formaPagoSpinner.adapter = adapter

        // Fecha de nacimiento
        val etFecha = findViewById<EditText>(R.id.etFechaNacimiento)
        etFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val anio = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker =
                DatePickerDialog(this, { _, añoSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaFormateada = String.format(
                        "%02d/%02d/%04d", diaSeleccionado, mesSeleccionado + 1, añoSeleccionado
                    )
                    etFecha.setText(fechaFormateada)
                }, anio, mes, dia)

            datePicker.show()
        }

        // Tipo de cliente
        val tipoCliente = arrayOf("Socio", "No Socio")
        val tipoClienteSpinner = findViewById<Spinner>(spTipoCliente)
        val adapterCli = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoCliente)
        adapterCli.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipoClienteSpinner.adapter = adapterCli

        //
        val boton = findViewById<Button>(R.id.btnRegistrar)

        boton.setOnClickListener {
            Toast.makeText(this, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 1500) // Espera 1.5 segundos


        }
    }
}
