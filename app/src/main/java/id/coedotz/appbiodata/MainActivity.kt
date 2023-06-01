package id.coedotz.appbiodata

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.coedotz.appbiodata.api.ApiRequestBiodata
import id.coedotz.appbiodata.api.Retroserver
import id.coedotz.appbiodata.model.ResponsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var nama: EditText
    private lateinit var usia: EditText
    private lateinit var domisili: EditText
    private lateinit var btnsave: Button
    private lateinit var btnTampildata: Button
    private lateinit var btnupdate: Button
    private lateinit var btndelete: Button
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nama = findViewById(R.id.edt_nama)
        usia = findViewById(R.id.edt_usia)
        domisili = findViewById(R.id.edtdomisili)
        btnTampildata = findViewById(R.id.btntampildata)
        btnupdate = findViewById(R.id.btnUpdate)
        btnsave = findViewById(R.id.btn_insertdata)
        btndelete = findViewById(R.id.btnhapus)

        val data = intent
        val iddata = data.getStringExtra("id")
        if (iddata != null) {
            btnsave.visibility = View.GONE
            btnTampildata.visibility = View.GONE
            btnupdate.visibility = View.VISIBLE
            btndelete.visibility = View.VISIBLE
            nama.setText(data.getStringExtra("nama"))
            usia.setText(data.getStringExtra("usia"))
            domisili.setText(data.getStringExtra("domisili"))
        }

        pd = ProgressDialog(this)

        btnTampildata.setOnClickListener {
            val godata = Intent(this@MainActivity, TampilData::class.java)
            startActivity(godata)
        }

        btndelete.setOnClickListener {
            pd.setMessage("Loading Hapus ...")
            pd.setCancelable(false)
            pd.show()

            val api = Retroserver.getClient().create(ApiRequestBiodata::class.java)
            val del: Call<ResponsModel> = api.deleteData(iddata)
            del.enqueue(object : Callback<ResponsModel> {
                override fun onResponse(call: Call<ResponsModel>, response: Response<ResponsModel>) {
                    Log.d("Retro", "onResponse")
                    Toast.makeText(this@MainActivity, response.body()?.pesan, Toast.LENGTH_SHORT).show()
                    val gotampil = Intent(this@MainActivity, TampilData::class.java)
                    startActivity(gotampil)
                }

                override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                    pd.hide()
                    Log.d("Retro", "onFailure")
                }
            })
        }

        btnupdate.setOnClickListener {
            pd.setMessage("update ....")
            pd.setCancelable(false)
            pd.show()

            val api = Retroserver.getClient().create(ApiRequestBiodata::class.java)
            val update: Call<ResponsModel> = api.updateData(
                iddata,
                nama.text.toString(),
                usia.text.toString(),
                domisili.text.toString()
            )
            update.enqueue(object : Callback<ResponsModel> {
                override fun onResponse(call: Call<ResponsModel>, response: Response<ResponsModel>) {
                    Log.d("Retro", "Response")
                    Toast.makeText(this@MainActivity, response.body()?.pesan, Toast.LENGTH_SHORT).show()
                    pd.hide()
                }

                override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                    pd.hide()
                    Log.d("Retro", "OnFailure")
                }
            })
        }

        btnsave.setOnClickListener {
            pd.setMessage("send data ... ")
            pd.setCancelable(false)
            pd.show()

            val snama = nama.text.toString()
            val susia = usia.text.toString()
            val sdomisili = domisili.text.toString()
            val api = Retroserver.getClient().create(ApiRequestBiodata::class.java)

            val sendbio: Call<ResponsModel> = api.sendBiodata(snama, susia, sdomisili)
            sendbio.enqueue(object : Callback<ResponsModel> {
                override fun onResponse(call: Call<ResponsModel>, response: Response<ResponsModel>) {
                    pd.hide()
                    Log.d("RETRO", "response : " + response.body().toString())
                    val kode = response.body()?.kode

                    if (kode == "1") {
                        Toast.makeText(this@MainActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Data Error tidak berhasil disimpan", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                    pd.hide()
                    Log.d("RETRO", "Falure : " + "Gagal Mengirim Request")
                }
            })
        }
    }
}
