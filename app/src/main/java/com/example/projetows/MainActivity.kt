package com.example.projetows

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var btnAdd: Button
    lateinit var btnListar: Button
    lateinit var nomeFilme: EditText
    lateinit var generoFilme: EditText

    val URL = "http://192.168.0.100/projetos/webService/view/?op="
    val URL_ADD = URL + "insert"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd = findViewById(R.id.btnAdd)
        btnListar = findViewById(R.id.btnListar)
        nomeFilme = findViewById(R.id.nomeFilme)
        generoFilme = findViewById(R.id.generoFilme)

        btnAdd.setOnClickListener() {
            addFilme()
            hideKeyboard()
            nomeFilme.setText("")
            generoFilme.setText("")
        }

        btnListar.setOnClickListener() {
            listarFilmes()
        }
    }

    // Metodo usado para adicionar registro digitado pelo usuario
    private fun addFilme() {
        val nome = nomeFilme?.text.toString()
        val genero = generoFilme?.text.toString()

        val stringRequest = object : StringRequest(Request.Method.POST, URL_ADD,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG)
                        .show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG)
                        .show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("nome", nome)
                params.put("genero", genero)
                return params
            }
        }

        VolleySingleton.instance?.addToRequestQueue(stringRequest)

    }

    private fun listarFilmes() {

    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive)
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}

