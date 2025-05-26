package com.club4b.deportivogrupo4b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al botón
        val loginButton = findViewById<Button>(R.id.btnLogin)

        loginButton.setOnClickListener {
            val intent = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intent)
            finish() // evita volver atrás, al login
        }

        val dbHelper = UserDBHelper (this)

        val user = findViewById<EditText>(R.id.etUserName)
        val pass = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val userString= user.text.toString().trim()
            val passString= pass.text.toString().trim()

            if (dbHelper.login(userString,passString)) {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                val intent= Intent (this, MenuPrincipalActivity :: class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }

        }


    }
}