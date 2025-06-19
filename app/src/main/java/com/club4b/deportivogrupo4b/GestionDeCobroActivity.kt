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
        val etDocumento= findViewById<EditText>(R.id.etDocumento)
        val etnombreApellido=findViewById<EditText>(R.id.etnombreApellido)
        val etipoCliente=findViewById<EditText>(R.id.etipodeCliente)
        val etfechaVencimiento=findViewById<EditText>(R.id.etfechaVencimiento)


            btnBuscarCobro.setOnClickListener {
                // Mostrar el grupo de datos validando el doc
                val dniBuscado= etDocumento.text.toString()

                //valida si el campo etDocumento esta en blanco

                if (dniBuscado.isBlank()) {
                    Toast.makeText(this,"Ingrese un Dni para buscar", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val dbHelper= UserDBHelper(this)
                val db= dbHelper.readableDatabase

                //busca los valores en las tablas existentes para devolverlos en la activity.
                val cursor = db.rawQuery(
                    """
                    SELECT c.nro_documento, c.nombre, c.apellido, c.tipo_cliente, co.fechaVencimiento
                    FROM clientes c 
                    JOIN cobros co ON co.cliente_id = c.id_cliente
                    WHERE c.nro_documento = ?
                     """.trimIndent(),
                    arrayOf(dniBuscado)
                )

                if (cursor.moveToFirst()) {
                    // Si encontró el cliente
                    val nro_documento = cursor.getString(0)
                    val nombre = cursor.getString(1)
                    val apellido = cursor.getString(2)
                    val tipo_cliente=cursor.getString(3)
                    val fechaVencimiento=cursor.getString(4)



                    etDocumento.setText(nro_documento)
                    etnombreApellido.setText("$nombre $apellido") // Une nombre y apellido
                    etipoCliente.setText(tipo_cliente)
                    etfechaVencimiento.setText(fechaVencimiento)

                    grupoDatosClienteCobro.visibility = View.VISIBLE
                    btnBuscarCobro.visibility = View.GONE
                } else {
                    Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
                }

                cursor.close()
                db.close()
            }
                //btnBuscarCobro.visibility = View.GONE // Oculta el botón Buscar


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



