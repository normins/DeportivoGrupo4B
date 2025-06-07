package com.club4b.deportivogrupo4b

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDBHelper(context: Context) : SQLiteOpenHelper(context, "UsuariosDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT UNIQUE,
                contrasena TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE clientes (
                id_cliente INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT ,
                apellido TEXT,
                tipo_documento TEXT,
                nro_documento  TEXT,
                fecha_nacimiento TEXT,
                fecha_inscripcion TEXT,
                apto_fisico INTEGER,
                tipo_cliente TEXT,
                estado_carnet INTEGER,
                esMoroso INTEGER
            )
        """.trimIndent())


        db.execSQL("INSERT INTO usuarios (nombre, contrasena) VALUES ('admin', '1234')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO(   )
    }

    fun login (nombre: String, contrasena: String): Boolean {
        val db= readableDatabase
        val cursor= db.rawQuery(
            "SELECT * FROM usuarios WHERE nombre=? and contrasena=?",
            arrayOf(nombre, contrasena)
        )

        val existe= cursor.count>0
        return existe
    }
}