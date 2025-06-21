package com.club4b.deportivogrupo4b

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import java.text.SimpleDateFormat
import java.util.Locale


class RegistroClienteActivity: AppCompatActivity() {

    private lateinit var dbHelper:UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)

        dbHelper = UserDBHelper(this)

        //Retornar con la imagen de flecha
        val back1 = findViewById<ImageView>(R.id.back1)
        back1.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent) }

        mostrarClientes()

        val etDocumento = findViewById<EditText>(R.id.etDocumento)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val lvClientes = findViewById<ListView>(R.id.lvClientes)

        val grupoDatosCliente = findViewById<LinearLayout>(R.id.grupoDatosCliente)
        grupoDatosCliente.visibility = View.GONE

        btnAgregar.setOnClickListener {
            val docIngresado = etDocumento.text.toString().trim()

            if (docIngresado.isEmpty()) {
                Toast.makeText(this, "Por favor, completar el Número de documento",
                                Toast.LENGTH_SHORT).show()

            } else {

                if (dbHelper.validarClienteExistePorDocumento(docIngresado)) {
                    Toast.makeText(this, "Cliente ya registrado", Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish() // volver al menú
                    }, 1500)
                } else {
                    grupoDatosCliente.visibility = View.VISIBLE
                    btnAgregar.visibility = View.GONE // Oculta el botón Buscar
                    lvClientes.visibility = View.GONE // Oculta lista de clientes

                    etDocumento.isEnabled = false
                    etDocumento.backgroundTintList = ContextCompat.getColorStateList(
                        this,
                        android.R.color.holo_green_dark
                    ) // o null

                }
            }
        }

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val cbAptoFisico = findViewById<CheckBox>(R.id.cbAptoFisico)
        val valorAptoFisico = if (cbAptoFisico.isChecked) 1 else 0
        val cbEmitirCarnet = findViewById<CheckBox>(R.id.cbEmitirCarnet)
        val valorEmitirCarnet = if (cbEmitirCarnet.isChecked) 1 else 0

        // Forma de pago
        val formaPagoSpinner = findViewById<Spinner>(R.id.spFormaPago)
        val formaPago = arrayOf("Efectivo", "3 Cuotas", "6 Cuotas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, formaPago)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        formaPagoSpinner.adapter = adapter

        // Fecha de nacimiento
        val etFecha = findViewById<EditText>(R.id.etFechaNacimiento)
        etFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val anio = 2000 //calendario.get(Calendar.YEAR)
            val mes = 0 //calendario.get(Calendar.MONTH)
            val dia = 1 //calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker =
                DatePickerDialog(this,
                    { _, añoSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaFormateada = String.format(
                        "%02d/%02d/%04d", diaSeleccionado, mesSeleccionado + 1, añoSeleccionado
                    )
                    etFecha.setText(fechaFormateada)
                }, anio, mes, dia)

            datePicker.show()
        }

        // Socio - No socio - Act
        val tipoCliente = arrayOf("Socio", "No Socio")
        val spTipoCliente = findViewById<Spinner>(R.id.spTipoCliente)
        val adapterCli = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoCliente)

        adapterCli.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipoCliente.adapter = adapterCli
        spTipoCliente.setSelection(1) //  "No Socio" está en la posición 1

        val etActividad = findViewById<EditText>(R.id.etActividad)
        val txtActividad = findViewById<TextView>(R.id.txtActividad)
        val txtCuota = findViewById<TextView>(R.id.txtCuota)
        txtCuota.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.darker_gray) // o null
        val lvActividades = findViewById<ListView>(R.id.lvActividades)

        // Mostrar lista al hacer foco
        etActividad.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val tipoSeleccionado = spTipoCliente.selectedItem.toString().trim()
                val actividades = dbHelper.obtenerActividades()
                mostrarActividades() // carga en lvActividades

                when (tipoSeleccionado) {
                    "Socio" -> {
                        //Socio: no mostrar lista, cargar la primera
                        val partes = actividades[0].split(" - ")
                        etActividad.setText(partes[0])
                        txtActividad.setText(partes[1])
                        txtCuota.setText(partes.getOrNull(3) ?: "")
                        lvActividades.visibility = View.GONE
                    }

                    "No Socio" -> {
                        // No socio: mostrar la lista, sin permitir seleccionar la primera
                        lvActividades.visibility = View.VISIBLE

                        lvActividades.setOnItemClickListener { _, _, position, _ ->
                            if (position == 0) {
                                Toast.makeText(
                                    this,
                                    "No se puede seleccionar esta actividad",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val partes = actividades[position].split(" - ")
                                etActividad.setText(partes[0])
                                txtActividad.setText(partes[1])
                                txtCuota.setText(partes.getOrNull(3) ?: "")
                                lvActividades.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)



        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val documento = etDocumento.text.toString().trim()
            val fechaNacimiento = etFecha.text.toString().trim()
            val fechaInscripcion =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(java.util.Date())
            val valorTipoCliente = spTipoCliente.selectedItem.toString()
            val actividadId = etActividad.text.toString().toInt()
            val cuota = txtCuota.text.toString().toDouble()
            val esMoroso = 0       // No es moroso
            val formaPago = formaPagoSpinner.selectedItemPosition + 1

            if (nombre.isEmpty() || apellido.isEmpty()) {
                Toast.makeText(this, "Por favor, completar todos los campos obligatorios",
                    Toast.LENGTH_SHORT).show()

            } else {

                val clienteId = (dbHelper.insertarCliente(
                    nombre, apellido, documento, fechaNacimiento, fechaInscripcion,
                    valorAptoFisico, valorTipoCliente, valorEmitirCarnet, esMoroso, actividadId))

                if (clienteId != -1L) {
                   Toast.makeText(this, "Cliente registrado correctamente", Toast.LENGTH_SHORT)
                        .show()
                   val clienteIdInt = clienteId.toInt()
                   registrarPrimerCobro(clienteIdInt, cuota, formaPago)
                } else {
                    Toast.makeText(this, "Error al agregar cliente", Toast.LENGTH_SHORT).show()
                }
                etDocumento.text.clear()
                etNombre.text.clear()
                etApellido.text.clear()
                etFecha.text.clear()
                etActividad.text.clear()

                Handler(Looper.getMainLooper()).postDelayed({
                    finish() // volver al menú
                }, 1500)
            }
        }
    }

    private fun mostrarClientes(){
        val lvClientes = findViewById<ListView>(R.id.lvClientes)
        val lista = dbHelper.obtenerClientes()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
        lvClientes.adapter = adapter
    }

    private fun mostrarActividades(){
        val lvActividades = findViewById<ListView>(R.id.lvActividades)
        val lista = dbHelper.obtenerActividades()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)
        lvActividades.adapter = adapter
    }

    private fun registrarPrimerCobro(clienteId: Int, cuota: Double, formaPago: Int){
        val calendario = Calendar.getInstance()
        val anioCobro = calendario.get(Calendar.YEAR)
        val mesCobro = calendario.get(Calendar.MONTH) + 1  // enero = 0, por eso se suma 1

        // calcular vencimiento
        calendario.add(Calendar.MONTH, 1)
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaVencimiento = formato.format(calendario.time)

        val pagado = 1

        val dbHelper = UserDBHelper(this)

        //usa la funcion declarada en la DB Helper para escribir la BD
        val exito = dbHelper.insertarCobro(
            anioCobro,
            mesCobro,
            clienteId,
            cuota,
            fechaVencimiento,
            formaPago,
            pagado
        )

        if (exito) {
            Toast.makeText(this, "Cobro registrado correctamente", Toast.LENGTH_SHORT)
                .show()
            Handler(Looper.getMainLooper()).postDelayed({
                findViewById<LinearLayout>(R.id.grupoDatosClienteCobro).visibility =
                    View.GONE
                findViewById<Button>(R.id.btnBuscarCobro).visibility = View.VISIBLE
            }, 1500)

        } else {
            Toast.makeText(this, "Error al registrar el cobro", Toast.LENGTH_SHORT).show()
        }
    }


}
