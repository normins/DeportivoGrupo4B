package com.club4b.deportivogrupo4b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast


class UserDBHelper(context: Context) : SQLiteOpenHelper(context, "4BClubDeportivoDB", null, 8) {

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
                nro_documento  TEXT UNIQUE,
                fecha_nacimiento TEXT,
                fecha_inscripcion TEXT,
                apto_fisico INTEGER,
                tipo_cliente TEXT,
                estado_carnet INTEGER,
                esMoroso INTEGER
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE actividades (
                id INTEGER PRIMARY KEY,
                nombre TEXT ,
                horario TEXT,
                cupoTotal INTEGER,
                precio FLOAT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE cobros (
                anio_id  INTEGER,
                mes_id INTEGER,
                cliente_id INTEGER,
                importe FLOAT,
                fechaVencimiento TEXT,
                formaPago INTEGER,
                pagado INTEGER,
                PRIMARY KEY (anio_id, mes_id, cliente_id),
                FOREIGN KEY (cliente_id) REFERENCES clientes(id_cliente)
                )
        """.trimIndent())

        db.execSQL("INSERT INTO usuarios (nombre, contrasena) VALUES ('admin', '1234')")

        db.execSQL( "INSERT INTO actividades(id,nombre, horario, cupoTotal, precio)" +
                "VALUES (1000,'Cuota social','Cuota social',0,150000)" )
        db.execSQL( "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                "VALUES (2059,'Musculación','Todos los días',20,10000)" )
        db.execSQL( "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                "VALUES (2060,'Spinning','Ma-Ju-Sa 18-19',10,7000)" )
        db.execSQL( "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                "VALUES (2063,'Boxeo','Lu-Mi-Vi 20-22',10,5000)" )
        db.execSQL( "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                "VALUES (2034,'Natación','Ma-Ju-Sa 7-10',20,10000)" )
        db.execSQL( "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                "VALUES (2035,'Tenis','Ma-Ju-Sa 15-16',10,5000)" )
        db.execSQL( "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                "VALUES (2036,'Pilates','Lu-Mi-Vi 10-11',11,7000)" )
        db.execSQL( "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                "VALUES (2037,'Yoga','Lu-Mi-Vi 8-9',11,8000)" )
    }

    override fun onUpgrade(db:SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS clientes")
        db.execSQL("DROP TABLE IF EXISTS actividades")
        db.execSQL("DROP TABLE IF EXISTS cobros")
        onCreate(db)
    }

    fun login (nombre: String, contrasena: String): Boolean {
        val db= readableDatabase
        val cursor= db.rawQuery(
            "SELECT * FROM usuarios WHERE nombre=? and contrasena=?",
            arrayOf(nombre, contrasena)
        )
        val existe= cursor.count>0
        cursor.close()
        return existe
    }

    fun insertarCliente (nombre: String, apellido:String, nroDocumento:String,
                         fechaNacimiento:String, fechaInscripcion:String,
                         aptoFisico:Int, tipoCliente:String,
                         estadoCarnet:Int, esMoroso:Int): Boolean{

        val db = writableDatabase
        val valores = ContentValues().apply{
            put("nombre", nombre)
            put("apellido", apellido)
            put("nro_documento", nroDocumento)
            put("fecha_nacimiento", fechaNacimiento)
            put("fecha_inscripcion", fechaInscripcion)
            put("apto_fisico", aptoFisico)
            put("tipo_cliente", tipoCliente)
            put("estado_carnet", estadoCarnet)
            put("esMoroso", esMoroso)
        }

        Log.d("DB", "Insertando cliente: $nombre $apellido $nroDocumento")
        val resultado = db.insert("clientes", null, valores)
        Log.d("DB", "Resultado inserción: $resultado")

        return resultado != -1L
    }


    fun obtenerClientes(): List<String> {
        val clientes = mutableListOf<String>()
        val db = readableDatabase
        val cursor =db.rawQuery("SELECT nombre, apellido, nro_documento FROM clientes",null)
        if (cursor.moveToFirst())
            do {
                val nombre = cursor.getString(0)
                val apellido = cursor.getString(1)
                val nroDocumento = cursor.getString(2)
                clientes.add("$nroDocumento - $nombre $apellido")
            } while (cursor.moveToNext())

        cursor.close()
        return clientes
    }


    fun obtenerActividades(): List<String> {
        val actividades = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre, horario, precio FROM actividades", null)
        if (cursor.moveToFirst()){
            do {
                val nombre = cursor.getString(0)
                val horario = cursor.getString(1)
                val precio = cursor.getString(2)
                actividades.add("$nombre - $horario -  $precio")
            } while (cursor.moveToNext())
        // Usar actividades para llenar el ListView
        }
        cursor.close()
        return actividades
    }
}