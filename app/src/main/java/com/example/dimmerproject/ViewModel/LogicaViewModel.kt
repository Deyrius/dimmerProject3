package com.example.dimmerproject.ViewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dimmerproject.R


    class LogicaViewModel(application: Application) : AndroidViewModel(application) {

        @Suppress("UNCHECKED_CAST")
        class LogicaViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LogicaViewModel::class.java)) {
                    return LogicaViewModel(application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        fun colorearFuente (boton: Button,switch:String,  encendido: Boolean, contexto: Context ) : Pair<Boolean,String>{
            return if (encendido){
                boton.setTextColor(ContextCompat.getColor(contexto, R.color.white))

                Pair(false,switch[0]+"0".trim())
            }else{
                boton.setTextColor(ContextCompat.getColor(contexto, R.color.orange))
                Pair(true,switch.trim())
            }

        }

        fun dimmerValor (modulo: List<String>, dimmer:String, switchs: String,valor: Int,contexto: Context): String {
            val mensajeModuloFinal = modulo.find { it.isNotEmpty() }

            return if (mensajeModuloFinal.isNullOrEmpty() || switchs == "" + "," + "" + "," + "" + "," + "") {
                Toast.makeText(
                    contexto,
                    "DEBE DE SELECCIONAR UN MODULO Y UN SWITCH",
                    Toast.LENGTH_SHORT
                ).show()
                ""
            } else {
                "$mensajeModuloFinal,$dimmer,$switchs,$valor"
            }
        }

        fun guardarCfg (boton: String,dimmer1: String,dimmer2: String,dimmer3: String,contexto: Context){

               val sharedPreferences = contexto.applicationContext.getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE)
               val edit = sharedPreferences.edit()

               if (dimmer1.isEmpty() && dimmer2.isEmpty() && dimmer3.isEmpty()){
                   Toast.makeText(
                       contexto,
                       "DEBE DE SETEAR ALGUN VALOR",
                       Toast.LENGTH_SHORT
                   ).show()
               }
               else{
                   edit.putString("valorDimmer1$boton", "G$boton,$dimmer1")
                   edit.putString("valorDimmer2$boton", "G$boton,$dimmer2")
                   edit.putString("valorDimmer3$boton", "G$boton,$dimmer3")
                   Log.d("DEBUG","CLAVE: valorDimmer1$boton,VALOR  G$boton,$dimmer1")
                   Log.d("DEBUG","CLAVE: valorDimmer2$boton,VALOR  G$boton,$dimmer2")
                   Log.d("DEBUG","CLAVE: valorDimmer3$boton,VALOR  G$boton,$dimmer3")

                   edit.apply()



                   Log.d("DEBUG", "Contexto utilizado: ${contexto.javaClass.simpleName}")


                   Toast.makeText(
                       contexto,
                       "Se guardó la configuracion en el boton $boton",
                       Toast.LENGTH_SHORT
                   ).show()
               }
        }

        fun cargarCfg(boton: String,texto1: TextView,texto2: TextView,texto3: TextView,texto4: TextView,texto5: TextView,texto6: TextView,texto7: TextView,texto8: TextView,texto9: TextView,
                      texto10: TextView,texto11: TextView,texto12: TextView,contexto: Context ): List<String>{

            val sharedPreferences = contexto.applicationContext.getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE)

            val dimmer1 = sharedPreferences.getString("valorDimmer1$boton","").toString()
            val dimmer2 = sharedPreferences.getString("valorDimmer2$boton","").toString()
            val dimmer3 = sharedPreferences.getString("valorDimmer3$boton","").toString()

            val dimmers = listOf(dimmer1,dimmer2,dimmer3)
            val dimmerSplit1: List<String>
            val dimmerSplit2: List<String>
            val dimmerSplit3: List<String>
            val modulo1: String
            val modulo2: String
            val modulo3: String
            val valorDimmer1: String
            val valorDimmer2: String
            val valorDimmer3: String

            Log.d("CARGADO1",dimmer1)
            Log.d("CARGADO2",dimmer2)
            Log.d("CARGADO3",dimmer3)

            if (dimmer1.length > 3){
                dimmerSplit1 = dimmer1.split(",").toList()
                modulo1 = dimmerSplit1[1].substring(1)

                valorDimmer1 = if (dimmerSplit1[7].isNotEmpty()) {
                    (dimmerSplit1[7].toFloat() / 255 * 100).toInt().toString()
                } else {
                    "0"
                }
            }else{modulo1 = ""
            valorDimmer1 = "0"}
            if (dimmer2.length > 3){
                dimmerSplit2 = dimmer2.split(",").toList()
                modulo2 = dimmerSplit2[1].substring(1)

                valorDimmer2 = if (dimmerSplit2[7].isNotEmpty()) {
                    (dimmerSplit2[7].toFloat() / 255 * 100).toInt().toString()
                } else {
                    "0"
                }
            }else{modulo2 = ""
            valorDimmer2 = "0"}
            if (dimmer3.length > 3){
                dimmerSplit3 = dimmer3.split(",").toList()
                modulo3 = dimmerSplit3[1].substring(1)

                valorDimmer3 = if (dimmerSplit3[7].isNotEmpty()) {
                    (dimmerSplit3[7].toFloat() / 255 * 100).toInt().toString()
                } else {
                    "0"
                }

            }else{modulo3 = ""
            valorDimmer3 = "0"}



                if (modulo1.isNotEmpty() || modulo2.isNotEmpty() || modulo3.isNotEmpty()){
                    if (modulo1 == "1" || modulo2 == "1" || modulo3 == "1" ){
                        texto1.setTextColor(ContextCompat.getColor(contexto,R.color.red_900))
                        texto1.visibility = View.VISIBLE
                        texto1.text = contexto.getString(R.string.dimmer_text,"Dimmer 1",valorDimmer1)
                        texto2.setTextColor(ContextCompat.getColor(contexto,R.color.red_900))
                        texto2.visibility = View.VISIBLE
                        texto2.text = contexto.getString(R.string.dimmer_text,"Dimmer 2",valorDimmer2)
                        texto3.setTextColor(ContextCompat.getColor(contexto,R.color.red_900))
                        texto3.visibility = View.VISIBLE
                        texto3.text = contexto.getString(R.string.dimmer_text,"Dimmer 3",valorDimmer3)
                    }

                if (modulo1 == "2" || modulo2 == "2" || modulo3 == "2"){
                    texto4.setTextColor(ContextCompat.getColor(contexto,R.color.yellow_900))
                    texto4.visibility = View.VISIBLE
                    texto4.text = contexto.getString(R.string.dimmer_text,"Dimmer 1",valorDimmer1)
                    texto5.setTextColor(ContextCompat.getColor(contexto,R.color.yellow_900))
                    texto5.visibility = View.VISIBLE
                    texto5.text = contexto.getString(R.string.dimmer_text,"Dimmer 2",valorDimmer2)
                    texto6.setTextColor(ContextCompat.getColor(contexto,R.color.yellow_900))
                    texto6.visibility = View.VISIBLE
                    texto6.text = contexto.getString(R.string.dimmer_text,"Dimmer 3",valorDimmer3)
                }
                if (modulo1 == "3" || modulo2 == "3" || modulo3 == "3"){
                    texto7.setTextColor(ContextCompat.getColor(contexto,R.color.green_900))
                    texto7.visibility = View.VISIBLE
                    texto7.text = contexto.getString(R.string.dimmer_text,"Dimmer 1",valorDimmer1)
                    texto8.setTextColor(ContextCompat.getColor(contexto,R.color.green_900))
                    texto8.visibility = View.VISIBLE
                    texto8.text = contexto.getString(R.string.dimmer_text,"Dimmer 2",valorDimmer2)
                    texto9.setTextColor(ContextCompat.getColor(contexto,R.color.green_900))
                    texto9.visibility = View.VISIBLE
                    texto9.text = contexto.getString(R.string.dimmer_text,"Dimmer 3",valorDimmer3)
                }
                if (modulo1 == "4" || modulo2 == "4" || modulo3 == "4"){
                    texto10.setTextColor(ContextCompat.getColor(contexto,R.color.bluelight_900))
                    texto10.visibility = View.VISIBLE
                    texto10.text = contexto.getString(R.string.dimmer_text,"Dimmer 1",valorDimmer1)
                    texto11.setTextColor(ContextCompat.getColor(contexto,R.color.bluelight_900))
                    texto11.visibility = View.VISIBLE
                    texto11.text = contexto.getString(R.string.dimmer_text,"Dimmer 1",valorDimmer2)
                    texto12.setTextColor(ContextCompat.getColor(contexto,R.color.bluelight_900))
                    texto12.visibility = View.VISIBLE
                    texto12.text = contexto.getString(R.string.dimmer_text,"Dimmer 1",valorDimmer3)
                }
            }
            return  dimmers
        }

    }




//GUARDADO
/*
 EJEMPLO DE COMO ESTABA ANTES

if (!btn_lock_encendido) {
    var valorDimmer1GuardarFinalG1 = "G1," + valorDimmer1Guardar
    var valorDimmer2GuardarFinalG1 = "G1," + valorDimmer2Guardar
    var valorDimmer3GuardarFinalG1 = "G1," + valorDimmer3Guardar
    val sharedPreferencesG1 =
        getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE)
    val edit = sharedPreferencesG1.edit()

    if (valorDimmer1Guardar.isNullOrBlank() && valorDimmer2Guardar.isNullOrBlank() && valorDimmer3Guardar.isNullOrBlank()) {
        mostrarToast("DEBE DE SETEAR ALGUN VALOR")
    } else {
        edit.putString("valorDimmer1G1", valorDimmer1GuardarFinalG1.trim())
        edit.putString("valorDimmer2G1", valorDimmer2GuardarFinalG1.trim())
        edit.putString("valorDimmer3G1", valorDimmer3GuardarFinalG1.trim())

        edit.apply()
        mostrarToast("Se guardó la configuracion en el Boton 1")
        Log.d("Debugging", valorDimmer1GuardarFinalG1)
        Log.d("Debugging", valorDimmer2GuardarFinalG1)
        Log.d("Debugging", valorDimmer3GuardarFinalG1)

    }



} else {
    //ACA DEBO DE ENVIAR LO GUARDADO
    val sharedPreferencesG1 =
        getSharedPreferences("miSharedPreferences", Context.MODE_PRIVATE)
    val valorDimmer1RecuperadoG1 = sharedPreferencesG1.getString("valorDimmer1G1", "")
    val valorDimmer2RecuperadoG1 = sharedPreferencesG1.getString("valorDimmer2G1", "")
    val valorDimmer3RecuperadoG1 = sharedPreferencesG1.getString("valorDimmer3G1", "")


    Log.d("Debugging Dimmer 1", valorDimmer1RecuperadoG1.toString())
    Log.d("Debugging Dimmer 2", valorDimmer2RecuperadoG1.toString())
    Log.d("Debugging Dimmer 3", valorDimmer3RecuperadoG1.toString())


    val numeroModulo = valorModulo(valorDimmer1RecuperadoG1.toString())
    Log.d("Debugging VALOR NUMERICO ", numeroModulo.toString())

    val valorDimmer1 = valorDimmer(valorDimmer1RecuperadoG1.toString())
    val valorDimmer2 = valorDimmer(valorDimmer2RecuperadoG1.toString())
    val valorDimmer3 = valorDimmer(valorDimmer3RecuperadoG1.toString())

    if (numeroModulo?.toInt() != 0) {
        if (numeroModulo?.toInt() == 1) {
            txt_m1_d1.setTextColor(ContextCompat.getColor(this, R.color.red_900))
            txt_m1_d1.visibility = View.VISIBLE
            txt_m1_d1.text = "Dimmer 1: " + porcentaje(valorDimmer1).toString() + "%"
            Log.d("Debugging VALOR DIMMER", porcentaje(valorDimmer1).toString() + "%")

            txt_m1_d2.setTextColor(ContextCompat.getColor(this, R.color.red_900))
            txt_m1_d2.visibility = View.VISIBLE
            txt_m1_d2.text = "Dimmer 2: " + porcentaje(valorDimmer2).toString() + "%"

            txt_m1_d3.setTextColor(ContextCompat.getColor(this, R.color.red_900))
            txt_m1_d3.visibility = View.VISIBLE
            txt_m1_d3.text = "Dimmer 3: " + porcentaje(valorDimmer3).toString() + "%"
        }
        if (numeroModulo?.toInt() == 2) {
            txt_m2_d1.setTextColor(ContextCompat.getColor(this, R.color.yellow_900))
            txt_m2_d1.visibility = View.VISIBLE
            txt_m2_d1.text = "Dimmer 1: " + porcentaje(valorDimmer1).toString() + "%"

            txt_m2_d2.setTextColor(ContextCompat.getColor(this, R.color.yellow_900))
            txt_m2_d2.visibility = View.VISIBLE
            txt_m2_d2.text = "Dimmer 2: " + porcentaje(valorDimmer2).toString() + "%"

            txt_m2_d3.setTextColor(ContextCompat.getColor(this, R.color.yellow_900))
            txt_m2_d3.visibility = View.VISIBLE
            txt_m2_d3.text = "Dimmer 3: " + porcentaje(valorDimmer3).toString() + "%"
        }
        if (numeroModulo?.toInt() == 3) {
            txt_m3_d1.setTextColor(ContextCompat.getColor(this, R.color.green_900))
            txt_m3_d1.visibility = View.VISIBLE
            txt_m3_d1.text = "Dimmer 1: " + porcentaje(valorDimmer1).toString() + "%"

            txt_m3_d2.setTextColor(ContextCompat.getColor(this, R.color.green_900))
            txt_m3_d2.visibility = View.VISIBLE
            txt_m3_d2.text = "Dimmer 2: " + porcentaje(valorDimmer2).toString() + "%"

            txt_m3_d3.setTextColor(ContextCompat.getColor(this, R.color.green_900))
            txt_m3_d3.visibility = View.VISIBLE
            txt_m3_d3.text = "Dimmer 3: " + porcentaje(valorDimmer3).toString() + "%"
        }
        if (numeroModulo?.toInt() == 4) {
            txt_m4_d1.setTextColor(ContextCompat.getColor(this, R.color.bluelight_900))
            txt_m4_d1.visibility = View.VISIBLE
            txt_m4_d1.text = "Dimmer 1: " + porcentaje(valorDimmer1).toString() + "%"

            txt_m4_d2.setTextColor(ContextCompat.getColor(this, R.color.bluelight_900))
            txt_m4_d2.visibility = View.VISIBLE
            txt_m4_d2.text = "Dimmer 2: " + porcentaje(valorDimmer2).toString() + "%"

            txt_m4_d3.setTextColor(ContextCompat.getColor(this, R.color.bluelight_900))
            txt_m4_d3.visibility = View.VISIBLE
            txt_m4_d3.text = "Dimmer 3: " + porcentaje(valorDimmer3).toString() + "%"
        }


    } else {
        mostrarToast("Hubo un error al leer el Modulo")
    }

    mostrarToast("Se cargo la configuracion del Boton 1")



}

 */















