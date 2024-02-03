@file:Suppress("DEPRECATION")

package com.example.dimmerproject

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.hardware.usb.UsbConstants
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbEndpoint
import android.hardware.usb.UsbInterface
import android.hardware.usb.UsbManager
import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.example.dimmerproject.ViewModel.LogicaViewModel


class MainActivity : AppCompatActivity() {


    lateinit var btn_mod_1: Button
    lateinit var btn_mod_2: Button
    lateinit var btn_mod_3: Button
    lateinit var btn_mod_4: Button
    lateinit var dimmer1: SeekBar
    lateinit var dimmer2: SeekBar
    lateinit var dimmer3: SeekBar

    //******* SWITCH 1 *******
    lateinit var btn_sw1_1: Button
    lateinit var btn_sw1_2: Button
    lateinit var btn_sw1_3: Button
    lateinit var btn_sw1_4: Button

    //******* SWITCH 2 *******
    lateinit var btn_sw2_1: Button
    lateinit var btn_sw2_2: Button
    lateinit var btn_sw2_3: Button
    lateinit var btn_sw2_4: Button

    //******* SWITCH 3 *******
    lateinit var btn_sw3_1: Button
    lateinit var btn_sw3_2: Button
    lateinit var btn_sw3_3: Button
    lateinit var btn_sw3_4: Button

    //******* BOTONERA ******
    lateinit var btn_lock: ImageButton
    lateinit var btn_g_1: Button
    lateinit var btn_g_2: Button
    lateinit var btn_g_3: Button
    lateinit var btn_g_4: Button
    lateinit var btn_g_5: Button
    lateinit var btn_g_6: Button
    lateinit var btn_g_7: Button
    lateinit var btn_g_8: Button
    lateinit var btn_g_9: Button
    lateinit var btn_g_10: Button
    lateinit var btn_g_11: Button
    lateinit var btn_g_12: Button
    lateinit var btn_g_13: Button
    lateinit var btn_g_14: Button
    lateinit var btn_g_15: Button
    lateinit var btn_g_16: Button
    lateinit var btn_g_17: Button
    lateinit var btn_g_18: Button

    //******* TextView´s *******
    lateinit var txt_m1_d1: TextView
    lateinit var txt_m1_d2: TextView
    lateinit var txt_m1_d3: TextView
    lateinit var txt_m2_d1: TextView
    lateinit var txt_m2_d2: TextView
    lateinit var txt_m2_d3: TextView
    lateinit var txt_m3_d1: TextView
    lateinit var txt_m3_d2: TextView
    lateinit var txt_m3_d3: TextView
    lateinit var txt_m4_d1: TextView
    lateinit var txt_m4_d2: TextView
    lateinit var txt_m4_d3: TextView
    lateinit var txt_dimmer1: TextView
    lateinit var txt_dimmer2: TextView
    lateinit var txt_dimmer3: TextView

    lateinit var viewModel: LogicaViewModel

    private var usbManager: UsbManager? = null
    private var usbDevice: UsbDevice? = null



    companion object {
        const val ACTION_USB_PERMISSION = "com.example.dimmerproject.USB_PERMISSION"
        private const val TIMEOUT = 1000 // Puedes ajustar este valor según tus necesidades

    }

    private val usbReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                when (it.action) {
                    UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                        // Manejar el evento de dispositivo USB conectado
                        val device: UsbDevice? = it.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                        // Aquí puedes realizar las acciones necesarias cuando un dispositivo USB se conecta
                        Log.d("Debug"," USB CONECTADO")

                    }

                    UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                        // Manejar el evento de dispositivo USB desconectado
                        val device: UsbDevice? = it.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                        // Aquí puedes realizar las acciones necesarias cuando un dispositivo USB se desconecta
                        Log.d("Debug"," USB NO CONECTADO")
                    }

                    ACTION_USB_PERMISSION -> {
                        // Manejar la acción de permiso USB
                        val granted: Boolean =
                            it.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)
                        if (granted) {
                            // Permiso concedido, puedes realizar operaciones USB
                            mostrarToast("Conexion establecida.")
                        } else {
                            // Permiso denegado, manejar según sea necesario
                            mostrarToast("Conexion no establecida")
                            val device: UsbDevice? =
                                it.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                            Log.d(TAG, "Permiso USB denegado para el dispositivo $device")
                        }
                    }

                    else -> {
                        // Manejar cualquier otra acción aquí si es necesario
                    }
                }
            }
        }
    }







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_dimmer_2)
        inicializador()
        setDimmer()
        val contexto = this
        usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
        registerUSBReceiver()
        viewModel = ViewModelProvider(this, LogicaViewModel.LogicaViewModelFactory(application)).get(LogicaViewModel::class.java)


        //******* SWITCH 1 *******
        var btn_sw1_1_encendido = false
        var btn_sw1_2_encendido = false
        var btn_sw1_3_encendido = false
        var btn_sw1_4_encendido = false
        var sw1Message_1 = ""
        var sw1Message_2 = ""
        var sw1Message_3 = ""
        var sw1Message_4 = ""
        //******* SWITCH 2 *******
        var btn_sw2_1_encendido = false
        var btn_sw2_2_encendido = false
        var btn_sw2_3_encendido = false
        var btn_sw2_4_encendido = false
        var sw2Message_1 = ""
        var sw2Message_2 = ""
        var sw2Message_3 = ""
        var sw2Message_4 = ""
        //******* SWITCH 3 *******
        var btn_sw3_1_encendido = false
        var btn_sw3_2_encendido = false
        var btn_sw3_3_encendido = false
        var btn_sw3_4_encendido = false
        var sw3Message_1 = ""
        var sw3Message_2 = ""
        var sw3Message_3 = ""
        var sw3Message_4 = ""

        //******* DIMMERS *******
        var messageDimmer1 = ""
        var valorDimmer1Guardar = ""
        var messageDimmer2 = ""
        var valorDimmer2Guardar = ""
        var messageDimmer3 = ""
        var valorDimmer3Guardar = ""

        //******* Lock *******
        var btn_lock_encendido = false

        btn_mod_1.setOnClickListener {

            dimmer1.apply {
                tintDrawable(thumb, R.color.red_900)
                tintDrawable(progressDrawable, R.color.red_450)
                messageDimmer1 = "M1"
            }
            dimmer2.apply {
                tintDrawable(thumb, R.color.red_900)
                tintDrawable(progressDrawable, R.color.red_450)
                messageDimmer2 = "M1"
            }
            dimmer3.apply {
                tintDrawable(thumb, R.color.red_900)
                tintDrawable(progressDrawable, R.color.red_450)
                messageDimmer3 = "M1"
            }
        }
        btn_mod_2.setOnClickListener {

            dimmer1.apply {
                tintDrawable(thumb, R.color.yellow_900)
                tintDrawable(progressDrawable, R.color.yellow_450)
                messageDimmer1 = "M2"
            }
            dimmer2.apply {
                tintDrawable(thumb, R.color.yellow_900)
                tintDrawable(progressDrawable, R.color.yellow_450)
                messageDimmer2 = "M2"
            }
            dimmer3.apply {
                tintDrawable(thumb, R.color.yellow_900)
                tintDrawable(progressDrawable, R.color.yellow_450)
                messageDimmer3 = "M2"
            }
        }
        btn_mod_3.setOnClickListener {

            dimmer1.apply {
                tintDrawable(thumb, R.color.green_900)
                tintDrawable(progressDrawable, R.color.green_450)
                messageDimmer1 = "M3"
            }
            dimmer2.apply {
                tintDrawable(thumb, R.color.green_900)
                tintDrawable(progressDrawable, R.color.green_450)
                messageDimmer2 = "M3"
            }
            dimmer3.apply {
                tintDrawable(thumb, R.color.green_900)
                tintDrawable(progressDrawable, R.color.green_450)
                messageDimmer3 = "M3"
            }

        }
        btn_mod_4.setOnClickListener {

            dimmer1.apply {
                tintDrawable(thumb, R.color.bluelight_900)
                tintDrawable(progressDrawable, R.color.bluelight_450)
                messageDimmer1 = "M4"
            }
            dimmer2.apply {
                tintDrawable(thumb, R.color.bluelight_900)
                tintDrawable(progressDrawable, R.color.bluelight_450)
                messageDimmer2 = "M4"
            }
            dimmer3.apply {
                tintDrawable(thumb, R.color.bluelight_900)
                tintDrawable(progressDrawable, R.color.bluelight_450)
                messageDimmer3 = "M4"
            }
        }

        //******* SWITCH 1 *******
        btn_sw1_1.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw1_1,"11",btn_sw1_1_encendido,this)
            btn_sw1_1_encendido = valores.first
            sw1Message_1 = valores.second

            Log.d("DEBUG",valores.toString())
        }
        btn_sw1_2.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw1_2,"12",btn_sw1_2_encendido,this)
            btn_sw1_2_encendido = valores.first
            sw1Message_2 = valores.second
            Log.d("DEBUG",valores.toString())

        }
        btn_sw1_3.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw1_3,"13",btn_sw1_3_encendido,this)
            btn_sw1_3_encendido = valores.first
            sw1Message_3 = valores.second
            Log.d("DEBUG",valores.toString())
        }
        btn_sw1_4.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw1_4,"14",btn_sw1_4_encendido,this)
            btn_sw1_4_encendido = valores.first
            sw1Message_4 = valores.second
            Log.d("DEBUG",valores.toString())
        }

        //******* SWITCH 2 *******
        btn_sw2_1.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw2_1,"21",btn_sw2_1_encendido,this)
            btn_sw2_1_encendido = valores.first
            sw2Message_1 = valores.second.trim()
            Log.d("DEBUG",valores.toString())
        }
        btn_sw2_2.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw2_2,"22",btn_sw2_2_encendido,this)
            btn_sw2_2_encendido = valores.first
            sw2Message_2 = valores.second.trim()
            Log.d("DEBUG",valores.toString())
        }
        btn_sw2_3.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw2_3,"23",btn_sw2_3_encendido,this)
            btn_sw2_3_encendido = valores.first
            sw2Message_3 = valores.second.trim()
            Log.d("DEBUG",valores.toString())
        }
        btn_sw2_4.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw2_4,"24",btn_sw2_4_encendido,this)
            btn_sw2_4_encendido = valores.first
            sw2Message_4 = valores.second.trim()
            Log.d("DEBUG",valores.toString())
        }

        //******* SWITCH 3 *******
        btn_sw3_1.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw3_1,"31",btn_sw3_1_encendido,this)
            btn_sw3_1_encendido = valores.first
            sw3Message_1 = valores.second
        }
        btn_sw3_2.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw3_2,"32",btn_sw3_2_encendido,this)
            btn_sw3_2_encendido = valores.first
            sw3Message_2 = valores.second

        }
        btn_sw3_3.setOnClickListener {
            val valores = viewModel.colorearFuente(btn_sw3_3,"33",btn_sw3_3_encendido,this)
            btn_sw3_3_encendido = valores.first
            sw3Message_3 = valores.second
        }
        btn_sw3_4.setOnClickListener {

            val valores = viewModel.colorearFuente(btn_sw3_4,"34",btn_sw3_4_encendido,this)
            btn_sw3_4_encendido = valores.first
            sw3Message_4 = valores.second

        }

        //******* DIMMERS *******
        dimmer1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                /*
                var progress = seekBar?.progress ?: 0
                var mensajeModulo = listOf(messageDimmer1, messageDimmer2, messageDimmer3)
                var mensajeModuloFinal = mensajeModulo.find { it.isNotEmpty() }
                var mensajeSw = listOf(sw1Message_1, sw1Message_2, sw1Message_3, sw1Message_4)
                Log.d("Debugg", mensajeSw.toString())
                for ((index, mensaje) in mensajeSw.withIndex()) {
                    Log.d("Debugg", "Mensaje $index: $mensaje")
                }
                var mensajeSwFinal = mensajeSw.joinToString(",") { it.trim() }

                if (mensajeModuloFinal.isNullOrEmpty() || mensajeSw.all { it.isNullOrBlank() }) {
                    mostrarToast("DEBE DE SELECCIONAR UN MODULO Y UN SWITCH")
                } else {
                    if (btn_lock_encendido) {
                        mostrarToast("SE ENCUENTRA BLOQUEADA LA CONSOLA")
                    } else {
                        mostrarToast(mensajeModuloFinal.toString() + "," + mensajeSwFinal + "," + progress.toString())
                        Log.d(
                            "Debugg",
                            mensajeModuloFinal.toString() + "," + mensajeSwFinal + "," + progress.toString()
                        )
                        valorDimmer1Guardar =
                            mensajeModuloFinal.toString() + "," + mensajeSwFinal + "," + progress.toString()
                    }
                }

                */
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val mensajeModulo = listOf(messageDimmer1, messageDimmer2, messageDimmer3)

                val mensajeSwFinal = sw1Message_1.trim()+","+sw1Message_2.trim()+","+sw1Message_3.trim()+","+sw1Message_4.trim()

                if (viewModel.dimmerValor(mensajeModulo,"1",mensajeSwFinal,progress,contexto).isNotBlank()){

                    valorDimmer1Guardar = viewModel.dimmerValor(mensajeModulo,"1",mensajeSwFinal,progress,contexto).trim()
                    enviarDatosAlArduino(viewModel.dimmerValor(mensajeModulo,"1",mensajeSwFinal,progress,contexto))

                    //COMPROBACION
                    val byteArray = viewModel.dimmerValor(mensajeModulo,"1",mensajeSwFinal,progress,contexto).toByteArray(Charsets.UTF_8)
                    val hexString = byteArray.joinToString("") { "%02x".format(it) }
                    Log.d("Debugg BYTE", hexString)
                    Log.d("Debugg STRING", viewModel.dimmerValor(mensajeModulo,"1",mensajeSwFinal,progress,contexto))
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
        })

        dimmer2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onStopTrackingTouch(seekBar: SeekBar?) {/*
                var progress = seekBar?.progress ?: 0
                var mensajeModulo = listOf(messageDimmer1, messageDimmer2, messageDimmer3)
                var mensajeModuloFinal = mensajeModulo.find { it.isNotEmpty() }
                var mensajeSw = listOf(
                    sw2Message_1.trim(),
                    sw2Message_2.trim(),
                    sw2Message_3.trim(),
                    sw2Message_4.trim()
                )
                for ((index, mensaje) in mensajeSw.withIndex()) {
                    Log.d("Debugg", "Mensaje $index: $mensaje")
                }
                var mensajeSwFinal = mensajeSw.joinToString(",") { it.trim() }

                if (mensajeModuloFinal.isNullOrEmpty() || mensajeSw.all { it.isNullOrBlank() }) {
                    mostrarToast("DEBE DE SELECCIONAR UN MODULO, UN SWITCH Y ESTAR DESBLOQUEADO")
                } else {
                    if (btn_lock_encendido) {
                        mostrarToast("SE ENCUENTRA BLOQUEADA LA CONSOLA")
                    } else {
                        mostrarToast(mensajeModuloFinal.toString() + "," + mensajeSwFinal + "," + progress.toString())
                        Log.d(
                            "Debugg",
                            mensajeModuloFinal.toString() + "," + mensajeSwFinal + "," + progress.toString()
                        )
                        valorDimmer2Guardar =
                            mensajeModuloFinal.toString() + "," + mensajeSwFinal + "," + progress.toString()
                    }
                }*/
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val mensajeModulo = listOf(messageDimmer1, messageDimmer2, messageDimmer3)

                val mensajeSwFinal = sw2Message_1.trim()+","+sw2Message_2.trim()+","+sw2Message_3.trim()+","+sw2Message_4.trim()

                if (viewModel.dimmerValor(mensajeModulo,"2",mensajeSwFinal,progress,contexto).isNotBlank()){

                    valorDimmer2Guardar = viewModel.dimmerValor(mensajeModulo,"2",mensajeSwFinal,progress,contexto).trim()
                    enviarDatosAlArduino(viewModel.dimmerValor(mensajeModulo,"2",mensajeSwFinal,progress,contexto))

                    val byteArray = viewModel.dimmerValor(mensajeModulo,"2",mensajeSwFinal,progress,contexto).toByteArray(Charsets.UTF_8)
                    val hexString = byteArray.joinToString("") { "%02x".format(it) }
                    Log.d("Debugg BYTE", hexString)
                    Log.d("Debugg STRING", viewModel.dimmerValor(mensajeModulo,"2",mensajeSwFinal,progress,contexto))
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
        })

        dimmer3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onStopTrackingTouch(seekBar: SeekBar?) {/*
                var progress = seekBar?.progress ?: 0
                var mensajeModulo = listOf(messageDimmer1, messageDimmer2, messageDimmer3)
                var mensajeModuloFinal = mensajeModulo.find { it.isNotEmpty() }
                var mensajeSw = listOf(
                    sw3Message_1.trim(),
                    sw3Message_2.trim(),
                    sw3Message_3.trim(),
                    sw3Message_4.trim()
                )
                for ((index, mensaje) in mensajeSw.withIndex()) {
                    Log.d("Debugg", "Mensaje $index: $mensaje")
                }
                var mensajeSwFinal = mensajeSw.joinToString(",") { it.trim() }

                if (mensajeModuloFinal.isNullOrEmpty() || mensajeSw.all { it.isNullOrBlank() }) {
                    mostrarToast("DEBE DE SELECCIONAR UN MODULO Y UN SWITCH")
                } else {
                    if (btn_lock_encendido) {
                        mostrarToast("SE ENCUENTRA BLOQUEADA LA CONSOLA")
                    } else {
                        mostrarToast(mensajeModuloFinal.toString() + "," + mensajeSwFinal + "," + progress.toString())
                        Log.d(
                            "Debugg",
                            mensajeModuloFinal.toString() + "," + mensajeSwFinal + "," + progress.toString()
                        )
                        valorDimmer3Guardar =
                            mensajeModuloFinal.toString() + "," + mensajeSwFinal + "," + progress.toString()
                    }
                }*/
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val mensajeModulo = listOf(messageDimmer1, messageDimmer2, messageDimmer3)

                val mensajeSwFinal = sw3Message_1.trim()+","+sw3Message_2.trim()+","+sw3Message_3.trim()+","+sw3Message_4.trim()

                if (viewModel.dimmerValor(mensajeModulo,"3",mensajeSwFinal,progress,contexto).isNotBlank()){

                    valorDimmer3Guardar = viewModel.dimmerValor(mensajeModulo,"3",mensajeSwFinal,progress,contexto).trim()
                    enviarDatosAlArduino(viewModel.dimmerValor(mensajeModulo,"3",mensajeSwFinal,progress,contexto))

                    val byteArray = viewModel.dimmerValor(mensajeModulo,"3",mensajeSwFinal,progress,contexto).toByteArray(Charsets.UTF_8)
                    val hexString = byteArray.joinToString("") { "%02x".format(it) }
                    Log.d("Debugg BYTE", hexString)
                    Log.d("Debugg STRING", viewModel.dimmerValor(mensajeModulo,"3",mensajeSwFinal,progress,contexto))
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
        })

        //******* LOCKEO *******

        btn_lock.setOnClickListener {
            btn_lock_encendido = !btn_lock_encendido
            if (btn_lock_encendido) {
                btn_lock.setImageResource(R.drawable.ic_lock)
                Log.d("Debugg", "$btn_lock_encendido")
                dimmer1.isEnabled = false
                dimmer2.isEnabled = false
                dimmer3.isEnabled = false
                txt_dimmer1.setTextColor(ContextCompat.getColor(this, R.color.red_lock))
                txt_dimmer2.setTextColor(ContextCompat.getColor(this, R.color.red_lock))
                txt_dimmer3.setTextColor(ContextCompat.getColor(this, R.color.red_lock))
               /*
                dimmer1.apply {
                    progressDrawable.apply { tintDrawable(this, R.color.red_stop_solid) }
                }
               */

            } else {
                limpiador()
                btn_lock.setImageResource(R.drawable.ic_unlock)
                Log.d("Debugg", "$btn_lock_encendido ELSE")
                dimmer1.isEnabled = true
                dimmer2.isEnabled = true
                dimmer3.isEnabled = true
                txt_dimmer1.setTextColor(ContextCompat.getColor(this, R.color.orange))
                txt_dimmer2.setTextColor(ContextCompat.getColor(this, R.color.orange))
                txt_dimmer3.setTextColor(ContextCompat.getColor(this, R.color.orange))
            }


        }

        //******* BOTONERA *******

        btn_g_1.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("1" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("1",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 1")

                    //COMPROBADOR
                    val byteArray = dimmersCargados[0].toByteArray(Charsets.UTF_8)
                    val hexString = byteArray.joinToString("") { "%02x".format(it) }
                    Log.d("Debugg BYTE", hexString)
                    Log.d("Debugg STRING", dimmersCargados[0])

                    val byteArray1 = dimmersCargados[1].toByteArray(Charsets.UTF_8)
                    val hexString1 = byteArray1.joinToString("") { "%02x".format(it) }
                    Log.d("Debugg BYTE", hexString1)
                    Log.d("Debugg STRING", dimmersCargados[1])

                    val byteArray2 = dimmersCargados[2].toByteArray(Charsets.UTF_8)
                    val hexString2 = byteArray2.joinToString("") { "%02x".format(it) }
                    Log.d("Debugg BYTE", hexString2)
                     Log.d("Debugg STRING", dimmersCargados[2])

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")
                    Log.e("ERROR Catch","Error: ${e.message}")}
            }


        }
        btn_g_2.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("2" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("2",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 2")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }


        }
        btn_g_3.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("3" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("3",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 3")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_4.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("4" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("4",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 4")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }

        }
        btn_g_5.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("5" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("5",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 5")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_6.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("6" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("6",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 6")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_7.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("7" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("7",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 7")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_8.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("8" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("8",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 8")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_9.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("9" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("9",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 9")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_10.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("10" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("10",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 10")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_11.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("11" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("11",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 11")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_12.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("12" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("12",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 12")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_13.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("13" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("13",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 13")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_14.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("14" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("14",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 14")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_15.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("15" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("15",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 15")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_16.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("16" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("16",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 16")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_17.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("17" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("17",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 17")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }
        btn_g_18.setOnClickListener {
            limpiador()
            if (!btn_lock_encendido){
                viewModel.guardarCfg("18" ,valorDimmer1Guardar,valorDimmer2Guardar,valorDimmer3Guardar,applicationContext)
            }else{
                try {
                    val dimmersCargados = viewModel.cargarCfg("18",txt_m1_d1,txt_m1_d2,txt_m1_d3,txt_m2_d1,txt_m2_d2,txt_m2_d3,txt_m3_d1,txt_m3_d2,txt_m3_d3,
                        txt_m4_d1,txt_m4_d2,txt_m4_d3,applicationContext)
                    enviarDatosAlArduino(dimmersCargados[0])
                    enviarDatosAlArduino(dimmersCargados[1])
                    enviarDatosAlArduino(dimmersCargados[2])

                    mostrarToast("Se cargó la configuración del boton 18")

                }catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")}
            }
        }


    }

    override fun onResume() {
        super.onResume()
        registerUSBReceiver()
    }

    override fun onPause() {
        super.onPause()
        unregisterUSBReceiver()
    }


    fun inicializador() {
        btn_mod_1 = findViewById(R.id.btn_mod1)
        btn_mod_2 = findViewById(R.id.btn_mod2)
        btn_mod_3 = findViewById(R.id.btn_mod3)
        btn_mod_4 = findViewById(R.id.btn_mod4)

        //******* SWITCH 1 *******
        btn_sw1_1 = findViewById(R.id.btn_sw1_1)
        btn_sw1_2 = findViewById(R.id.btn_sw1_2)
        btn_sw1_3 = findViewById(R.id.btn_sw1_3)
        btn_sw1_4 = findViewById(R.id.btn_sw1_4)

        //******* SWITCH 2 *******
        btn_sw2_1 = findViewById(R.id.btn_sw2_1)
        btn_sw2_2 = findViewById(R.id.btn_sw2_2)
        btn_sw2_3 = findViewById(R.id.btn_sw2_3)
        btn_sw2_4 = findViewById(R.id.btn_sw2_4)

        //******* SWITCH 3 *******
        btn_sw3_1 = findViewById(R.id.btn_sw3_1)
        btn_sw3_2 = findViewById(R.id.btn_sw3_2)
        btn_sw3_3 = findViewById(R.id.btn_sw3_3)
        btn_sw3_4 = findViewById(R.id.btn_sw3_4)

        //******* BOTONERA *******
        btn_lock = findViewById(R.id.btn_lock)
        btn_g_1 = findViewById(R.id.btn_g_1)
        btn_g_2 = findViewById(R.id.btn_g_2)
        btn_g_3 = findViewById(R.id.btn_g_3)
        btn_g_4 = findViewById(R.id.btn_g_4)
        btn_g_5 = findViewById(R.id.btn_g_5)
        btn_g_6 = findViewById(R.id.btn_g_6)
        btn_g_7 = findViewById(R.id.btn_g_7)
        btn_g_8 = findViewById(R.id.btn_g_8)
        btn_g_9 = findViewById(R.id.btn_g_9)
        btn_g_10 = findViewById(R.id.btn_g_10)
        btn_g_11 = findViewById(R.id.btn_g_11)
        btn_g_12 = findViewById(R.id.btn_g_12)
        btn_g_13 = findViewById(R.id.btn_g_13)
        btn_g_14 = findViewById(R.id.btn_g_14)
        btn_g_15 = findViewById(R.id.btn_g_15)
        btn_g_16 = findViewById(R.id.btn_g_16)
        btn_g_17 = findViewById(R.id.btn_g_17)
        btn_g_18 = findViewById(R.id.btn_g_18)

        //******* TextView´s *******
        txt_m1_d1 = findViewById(R.id.txt_m1_d1)
        txt_m1_d2 = findViewById(R.id.txt_m1_d2)
        txt_m1_d3 = findViewById(R.id.txt_m1_d3)
        txt_m2_d1 = findViewById(R.id.txt_m2_d1)
        txt_m2_d2 = findViewById(R.id.txt_m2_d2)
        txt_m2_d3 = findViewById(R.id.txt_m2_d3)
        txt_m3_d1 = findViewById(R.id.txt_m3_d1)
        txt_m3_d2 = findViewById(R.id.txt_m3_d2)
        txt_m3_d3 = findViewById(R.id.txt_m3_d3)
        txt_m4_d1 = findViewById(R.id.txt_m4_d1)
        txt_m4_d2 = findViewById(R.id.txt_m4_d2)
        txt_m4_d3 = findViewById(R.id.txt_m4_d3)
        txt_dimmer1 = findViewById(R.id.txt_dimmer1)
        txt_dimmer2 = findViewById(R.id.txt_dimmer2)
        txt_dimmer3 = findViewById(R.id.txt_dimmer3)


    }



    fun limpiador() {
        txt_m1_d1.visibility = View.GONE
        txt_m1_d2.visibility = View.GONE
        txt_m1_d3.visibility = View.GONE
        txt_m2_d1.visibility = View.GONE
        txt_m2_d2.visibility = View.GONE
        txt_m2_d3.visibility = View.GONE
        txt_m3_d1.visibility = View.GONE
        txt_m3_d2.visibility = View.GONE
        txt_m3_d3.visibility = View.GONE
        txt_m4_d1.visibility = View.GONE
        txt_m4_d2.visibility = View.GONE
        txt_m4_d3.visibility = View.GONE
    }


    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }


    private fun setDimmer() {

        dimmer1 = findViewById(R.id.dimmer1)
        dimmer1.apply {
            tintDrawable(thumb, R.color.red_900)
            tintDrawable(progressDrawable, R.color.bluelight_stop)
        }
        dimmer2 = findViewById(R.id.dimmer2)
        dimmer2.apply {
            tintDrawable(thumb, R.color.red_900)
            tintDrawable(progressDrawable, R.color.bluelight_stop)
        }
        dimmer3 = findViewById(R.id.dimmer3)
        dimmer3.apply {
            tintDrawable(thumb, R.color.red_900)
            tintDrawable(progressDrawable, R.color.bluelight_stop)
        }
    }

    private fun tintDrawable(drawable: Drawable, @ColorRes color: Int) {
        DrawableCompat.setTint(
            drawable,
            ContextCompat.getColor(this, color)
        )
    }

    private fun registerUSBReceiver() {
        val filter = IntentFilter()
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
        filter.addAction(ACTION_USB_PERMISSION)
        registerReceiver(usbReceiver, filter)
    }

    // Función para desregistrar el receptor USB
    private fun unregisterUSBReceiver() {
        unregisterReceiver(usbReceiver)
    }

    private fun enviarDatosAlArduino(valor: String) {
        // Asegúrate de que usbManager y usbDevice no sean nulos
        if (usbManager != null && usbDevice != null) {
            // Abre el dispositivo USB
            val connection: UsbDeviceConnection? = usbManager?.openDevice(usbDevice)

            // Verifica si la conexión es exitosa
            if (connection != null) {
                try {
                    // Obtén el endpoint adecuado (debes configurar esto según tu dispositivo Arduino)
                    val endpointOut: UsbEndpoint? = findEndpoint(usbDevice!!, UsbConstants.USB_DIR_OUT)
                    if (endpointOut != null) {
                        // Convierte el mensaje a bytes
                        val data: ByteArray = valor.toByteArray(Charsets.UTF_8)
                        Log.d("Debugg",data.toString())

                        // Envía los datos al Arduino
                        val result = connection.bulkTransfer(endpointOut, data, data.size, TIMEOUT)

                        // Verifica el resultado de la transferencia
                        if (result < 0) {
                            mostrarToast("Error al enviar datos al Arduino")
                        } else {
                            mostrarToast("Datos enviados correctamente al Arduino")
                        }
                    } else {
                        mostrarToast("Endpoint de salida no encontrado")
                    }
                } catch (e: Exception) {
                    mostrarToast("Error: ${e.message}")
                } finally {
                    // Cierra la conexión cuando hayas terminado
                    connection.close()
                }
            } else {
                mostrarToast("No se pudo abrir la conexión con el dispositivo USB")
            }
        } else {
            mostrarToast("UsbManager o UsbDevice es nulo")
        }
    }

    // Función auxiliar para encontrar el endpoint adecuado
    private fun findEndpoint(device: UsbDevice, direction: Int): UsbEndpoint? {
        for (i in 0 until device.interfaceCount) {
            val usbInterface: UsbInterface = device.getInterface(i)
            for (j in 0 until usbInterface.endpointCount) {
                val endpoint: UsbEndpoint = usbInterface.getEndpoint(j)
                if (endpoint.direction == direction) {
                    return endpoint
                }
            }
        }
        return null
    }





}