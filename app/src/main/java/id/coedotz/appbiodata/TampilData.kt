package id.coedotz.appbiodata

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.coedotz.appbiodata.adapter.AdapterData
import id.coedotz.appbiodata.api.ApiRequestBiodata
import id.coedotz.appbiodata.api.Retroserver
import id.coedotz.appbiodata.model.DataModel
import id.coedotz.appbiodata.model.ResponsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TampilData : AppCompatActivity() {
    private lateinit var mRecycler: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    private lateinit var mManager: RecyclerView.LayoutManager
    private var mItems: MutableList<DataModel> = ArrayList()
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampil_data)

        pd = ProgressDialog(this)
        mRecycler = findViewById(R.id.recyclerTemp)
        mManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecycler.layoutManager = mManager

        pd.setMessage("Loading ...")
        pd.setCancelable(false)
        pd.show()

        val api = Retroserver.getClient().create(ApiRequestBiodata::class.java)
        val getdata: Call<ResponsModel> = api.getBiodata()
        getdata.enqueue(object : Callback<ResponsModel> {
            override fun onResponse(call: Call<ResponsModel>, response: Response<ResponsModel>) {
                pd.hide()
                Log.d("RETRO", "RESPONSE : " + response.body()?.kode)
                mItems = (response.body()?.result ?: ArrayList()) as MutableList<DataModel>

                mAdapter = AdapterData(this@TampilData, mItems)
                mRecycler.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ResponsModel>, t: Throwable) {
                pd.hide()
                Log.d("RETRO", "FAILED : respon gagal")
            }
        })
    }
}