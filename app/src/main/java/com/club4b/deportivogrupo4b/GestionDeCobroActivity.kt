package com.club4b.deportivogrupo4b

import android.content.Intent
import android.icu.util.Calendar
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
import java.text.SimpleDateFormat
import java.util.Locale

class GestionDeCobroActivity : AppCompatActivity() {

    private var clienteId: Int = -1
    private var anioCliente: Int = 0
    private var mesCliente: Int = 0
    private var importePendiente: Double = 0.0
    private var fechaUltimaVto: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_decobro)

        //Spinner forma de pago
        val spFormaPago = findViewById<Spinner>(R.id.spFormaPago)

        // Lista de opciones

        val opcionesPago = listOf("Efectivo", "3 cuotas", "6 cuotas")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesPago)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spFormaPago.adapter = adapter

        //Retornar con la imagen de flecha
        val back2 = findViewById<ImageView>(R.id.back2)
        back2.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)
        }

        //Abrir gestion de cobro con el evento click en boton buscar.

        val btnBuscarCobro = findViewById<Button>(R.id.btnBuscarCobro)
        val grupoDatosClienteCobro = findViewById<LinearLayout>(R.id.grupoDatosClienteCobro)
        val etDocumento = findViewById<EditText>(R.id.etDocumento)
        val etnombreApellido = findViewById<EditText>(R.id.etnombreApellido)
        val etipoCliente = findViewById<EditText>(R.id.etipodeCliente)
        val etfechaVencimiento = findViewById<EditText>(R.id.etfechaVencimiento)


        btnBuscarCobro.setOnClickListener {
            // Mostrar el grupo de datos validando el doc
            val dniBuscado = etDocumento.text.toString()

            //valida si el campo etDocumento esta en blanco

            if (dniBuscado.isBlank()) {
                Toast.makeText(this, "Ingrese un Dni para buscar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = UserDBHelper(this)
            val db = dbHelper.readableDatabase

            //busca los valores en las tablas existentes para devolverlos en la activity.
            val cursor = db.rawQuery(
                """
                    SELECT c.id_cliente, c.nro_documento, c.nombre, c.apellido, c.tipo_cliente,
                    co.fechaVencimiento, co.anio_id, co.mes_id, co.importe
                    FROM clientes c 
                    JOIN cobros co ON co.cliente_id = c.id_cliente
                    WHERE c.nro_documento = ?
                    ORDER BY co.anio_id DESC, co.mes_id DESC
                    LIMIT 1
                    """.trimIndent(),
                arrayOf(dniBuscado)
            )

            if (cursor.moveToFirst()) {
                // Si encontró el cliente
                clienteId = cursor.getInt(0)
                val nro_documento = cursor.getString(1)
                val nombre = cursor.getString(2)
                val apellido = cursor.getString(3)
                val tipo_cliente = cursor.getString(4)
                fechaUltimaVto = cursor.getString(5)
                anioCliente = cursor.getInt(6)
                mesCliente = cursor.getInt(7)
                importePendiente = cursor.getDouble(8)


                //se muestran los datos
                etDocumento.setText(nro_documento)
                etnombreApellido.setText("$nombre $apellido") // Une nombre y apellido
                etipoCliente.setText(tipo_cliente)
                etfechaVencimiento.setText(fechaUltimaVto)

                grupoDatosClienteCobro.visibility = View.VISIBLE
                btnBuscarCobro.visibility = View.GONE

            } else {
                Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
            }

            cursor.close()
            db.close()

        }


        val btnConfirmarCobro = findViewById<Button>(R.id.btnConfirmarCobro)

        btnConfirmarCobro.setOnClickListener {
            val spFormaPago = findViewById<Spinner>(R.id.spFormaPagoCobro)

            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fecha = formato.parse(fechaUltimaVto)
            val calendar = Calendar.getInstance()
            calendar.time = fecha!!
            calendar.add(Calendar.DAY_OF_YEAR, 31)

            val nuevaFechaVencimiento = formato.format(calendar.time)

            val formaPago = spFormaPago.selectedItemPosition + 1
            val pagado = 1

            val dbHelper = UserDBHelper(this)

            //usa la funcion declarada en la DB Helper para escribir la BD
            val exito = dbHelper.insertarCobro(
                anioCliente,
                mesCliente,
                clienteId,
                importePendiente,
                nuevaFechaVencimiento,
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


        val btnCancelarCobro = findViewById<Button>(R.id.btnCancelarCobro)

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

