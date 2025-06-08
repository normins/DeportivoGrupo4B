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

    lateinit var dbHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = UserDBHelper (this)

        val user = findViewById<EditText>(R.id.etUserName)
        val pass = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val userString= user.text.toString().trim()
            val passString= pass.text.toString().trim()

            if (dbHelper.login(userString,passString)) {
                Toast.makeText(this, "Bienvenido $userString", Toast.LENGTH_SHORT).show()
                val intent= Intent (this, MenuPrincipalActivity :: class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show()
                user.text.clear()
                pass.text.clear()
                user.requestFocus()
            }

        }


    }
}