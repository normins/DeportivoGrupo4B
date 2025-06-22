package com.club4b.deportivogrupo4b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class UserDBHelper(context: Context) : SQLiteOpenHelper(context, "4BClubDeportivoDB", null, 27) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT UNIQUE,
                contrasena TEXT
            )
        """.trimIndent()
        )

        db.execSQL(
            """
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
                esMoroso INTEGER,
                actividad INTEGER
            )
        """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE actividades (
                id INTEGER PRIMARY KEY,
                nombre TEXT ,
                horario TEXT,
                cupoTotal INTEGER,
                precio FLOAT
            )
        """.trimIndent()
        )

        db.execSQL(
            """
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
        """.trimIndent()
        )

        db.execSQL("INSERT INTO usuarios (nombre, contrasena) VALUES ('admin', '1234')")

        db.execSQL(
            "INSERT INTO actividades(id,nombre, horario, cupoTotal, precio)" +
                    "VALUES (1000,'Cuota social','Cuota social',0,150000)"
        )
        db.execSQL(
            "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                    "VALUES (2059,'Musculación','Todos los días',20,10000)"
        )
        db.execSQL(
            "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                    "VALUES (2060,'Spinning','Ma-Ju-Sa 18-19',10,7000)"
        )
        db.execSQL(
            "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                    "VALUES (2063,'Boxeo','Lu-Mi-Vi 20-22',10,5000)"
        )
        db.execSQL(
            "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                    "VALUES (2034,'Natación','Ma-Ju-Sa 7-10',20,10000)"
        )
        db.execSQL(
            "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                    "VALUES (2035,'Tenis','Ma-Ju-Sa 15-16',10,5000)"
        )
        db.execSQL(
            "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                    "VALUES (2036,'Pilates','Lu-Mi-Vi 10-11',11,7000)"
        )
        db.execSQL(
            "INSERT INTO actividades(id, nombre, horario, cupoTotal, precio)" +
                    "VALUES (2037,'Yoga','Lu-Mi-Vi 8-9',11,8000)"
        )

        //para simular busqueda en activity gestion de cobro
        db.execSQL(
            "INSERT INTO clientes( id_cliente, nro_documento, fecha_nacimiento," +
                    " nombre, apellido,fecha_inscripcion, apto_fisico, tipo_cliente," +
                    " estado_carnet, esMoroso, actividad)"+
                    "VALUES (1, '35703124', '01/01/1990', 'Gilda', 'Morgante', " +
                    "'01/01/2023', 1, 'Socio', 1, 0, 1000)")

        db.execSQL(
            "INSERT INTO clientes( id_cliente, nro_documento, fecha_nacimiento," +
                    " nombre, apellido,fecha_inscripcion, apto_fisico, tipo_cliente," +
                    " estado_carnet, esMoroso, actividad)"+
                    "VALUES (2, '25252525', '12/06/1994', 'Harry', 'Potter', " +
                    "'01/01/2024', 1, 'Socio', 1, 0, 1000)")

        //para simular la busqueda en activity gestion de cobro y ver fecha de vto

        db.execSQL(
            "INSERT INTO cobros(anio_id, mes_id, cliente_id,importe, fechaVencimiento, formapago, pagado)"
                    + "VALUES (2025,06, 1,2000, '12/06/2025',0,2000)")
    }

    fun insertarCliente (nombre: String, apellido:String, nroDocumento:String,
                         fechaNacimiento:String, fechaInscripcion:String,
                         aptoFisico:Int, tipoCliente:String,
                         estadoCarnet:Int, esMoroso:Int, actividad:Int): Long {


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
            put("actividad", actividad)
        }

        Log.d("DB", "Insertando cliente: $nombre $apellido $nroDocumento")
        val resultado = db.insert("clientes", null, valores)
        Log.d("DB", "Resultado inserción: $resultado")

        return resultado
    }


    fun obtenerClientes(): List<String> {
        val clientes = mutableListOf<String>()
        val db = readableDatabase
        val cursor =db.rawQuery("SELECT nombre, apellido, nro_documento " +
                "FROM clientes order by apellido, nombre",null)
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
        val cursor = db.rawQuery("SELECT id, nombre, horario, precio FROM actividades", null)
        if (cursor.moveToFirst()){
            do {
                val id = cursor.getString(0)
                val nombre = cursor.getString(1)
                val horario = cursor.getString(2)
                val precio = cursor.getString(3)
                actividades.add("$id - $nombre - $horario - $precio")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return actividades
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

            db.execSQL("DROP TABLE IF EXISTS usuarios")
            db.execSQL("DROP TABLE IF EXISTS clientes")
            db.execSQL("DROP TABLE IF EXISTS actividades")
            db.execSQL("DROP TABLE IF EXISTS cobros")
            onCreate(db)
        }

    fun login(nombre: String, contrasena: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM usuarios WHERE nombre=? and contrasena=?",
            arrayOf(nombre, contrasena)
        )

        val existe = cursor.count > 0
        cursor.close()
        db.close()
        return existe
    }

    fun validarClienteExistePorDocumento(documento: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT 1 FROM clientes WHERE nro_documento = ?", arrayOf(documento))
        val existe = cursor.moveToFirst()
        cursor.close()
        db.close()
        return existe
    }

      fun insertarCobro(
            anio: Int,
            mes: Int,
            cliente: Int,
            importe: Double,
            fechaVencimiento: String,
            formaPago: Int,
            pagado: Int
        ): Boolean {

            val db = this.writableDatabase


            val valores = ContentValues().apply {
                put("anio_id", anio)
                put("mes_id", mes)
                put("cliente_id", cliente)
                put("importe", importe)
                put("fechaVencimiento", fechaVencimiento)
                put("formaPago", formaPago)
                put("pagado", pagado)
            }

            val resultado = db.insert("cobros", null, valores)
            db.close()

            return resultado != -1L

        }

    // obtiene el vencimiento de cuotas diarias

    fun listadoVencimientos (buscarFecha:String): List<Vencimiento> {

        val lista = mutableListOf<Vencimiento>()
        val db = this.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT c.nro_documento, c.nombre, c.apellido
        FROM cobros co
        JOIN clientes c ON co.cliente_id = c.id_cliente
        WHERE co.fechaVencimiento = ?
        """.trimIndent(),
            arrayOf(buscarFecha)
        )

        if (cursor.moveToFirst()) {
            do {
                val nroDoc = cursor.getString(0)
                val nombre = cursor.getString(1)
                val apellido = cursor.getString(2)
                lista.add(Vencimiento(nroDoc, nombre, apellido))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return lista
    }


    }

