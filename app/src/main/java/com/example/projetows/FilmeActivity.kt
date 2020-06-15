package com.example.projetows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class FilmeActivity : AppCompatActivity() {

    val URL = "http://10.0.2.2/webService/view/?op="
    val URL_SELECT = URL + "select"

    lateinit var filmeListView: ListView
    lateinit var mfilmeList: MutableList<Filme>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filme)

        filmeListView = findViewById(R.id.lstFilme)
        mfilmeList = mutableListOf<Filme>()

        carregarFilmes()


    }

    private fun carregarFilmes() {

        val stringRequest = StringRequest(
            Request.Method.POST,
            URL_SELECT,
            Response.Listener<String> { s ->
                try {
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("filme")

                        for (i in 0..array.length()) {
                            val objectTmp = array.getJSONObject(i)
                            val tmp = Filme(
                                objectTmp.getString("nome"),
                                objectTmp.getString("genero")
                            )
                            mfilmeList.add(tmp)
                            val adapter = FilmeLista(this, mfilmeList)
                            filmeListView.adapter = adapter
                        }
                    } else {
                        Toast.makeText(
                            getApplicationContext(),
                            obj.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(
                    applicationContext,
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }
}