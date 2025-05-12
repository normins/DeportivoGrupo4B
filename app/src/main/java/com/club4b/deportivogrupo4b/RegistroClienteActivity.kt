package com.club4b.deportivogrupo4b

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.util.Log.*
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.club4b.deportivogrupo4b.R.id.spFormaPago
import com.club4b.deportivogrupo4b.R.id.spTipoCliente


class RegistroClienteActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)




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
                        "%02d/%02d/%04d", diaSeleccionado, mesSeleccionado + 1, añoSeleccionado )
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



    }
}
