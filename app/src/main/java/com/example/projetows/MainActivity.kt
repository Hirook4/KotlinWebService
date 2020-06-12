package com.example.projetows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var btnAdd: Button
    lateinit var btnAddFixo: Button
    lateinit var btnListar: Button
    lateinit var nomeFilme: EditText
    lateinit var generoFilme: EditText

    // Adiciona um filme ja determinado na URL
    val URL_ADD_FIXO = "http://192.168.0.100/projetos/webService/view/?op=insert&nome=Coringa&genero=Drama"

    // Adiciona um filme digitado pelo usuario
    val URL_ADD = "http://192.168.0.100/projetos/webService/view/?op=insert"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd = findViewById(R.id.btnAdd)
        btnAddFixo = findViewById(R.id.btnAddFixo)
        btnListar = findViewById(R.id.btnListar)
        nomeFilme = findViewById(R.id.nomeFilme)
        generoFilme = findViewById(R.id.generoFilme)

        btnAdd.setOnClickListener() {
            addFilme()
        }

        btnAddFixo.setOnClickListener() {
            addFilmeFixo()
        }

        btnListar.setOnClickListener() {
            listarFilmes()
        }
    }

    // Metodo usado para adicionar registro digitado pelo usuario
    // http://192.168.0.100/projetos/webService/view/?op=insert
    private fun addFilme() {
        val nome = nomeFilme.text.toString()
        val genero = generoFilme.text.toString()

        val stringRequest = object : StringRequest(Request.Method.GET, URL_ADD,
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

    // Metodo usado para adicionar um registro ja determinado na URL
    // http://192.168.0.100/projetos/webService/view/?op=insert&nome=Coringa&genero=Drama
    private fun addFilmeFixo() {
        val stringRequest = object : StringRequest(Request.Method.GET, URL_ADD_FIXO,
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
                return params
            }
        }

        VolleySingleton.instance?.addToRequestQueue(stringRequest)

    }

    private fun listarFilmes() {

    }
}

