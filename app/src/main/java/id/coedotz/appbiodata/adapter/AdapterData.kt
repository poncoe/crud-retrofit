package id.coedotz.appbiodata.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.coedotz.appbiodata.MainActivity
import id.coedotz.appbiodata.R
import id.coedotz.appbiodata.model.DataModel

class AdapterData(private val ctx: Context, private val mList: List<DataModel>) : RecyclerView.Adapter<AdapterData.HolderData>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderData {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.layoutlist, parent, false)
        return HolderData(layout)
    }

    override fun onBindViewHolder(holder: HolderData, position: Int) {
        val dm = mList[position]
        holder.nama.text = dm.nama
        holder.usia.text = dm.usia
        holder.domisili.text = dm.domisili
        holder.dm = dm
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class HolderData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nama: TextView = itemView.findViewById(R.id.tvNama)
        var usia: TextView = itemView.findViewById(R.id.tvUsia)
        var domisili: TextView = itemView.findViewById(R.id.tvDomisili)
        var dm: DataModel? = null

        init {
            itemView.setOnClickListener {
                val goInput = Intent(ctx, MainActivity::class.java)
                goInput.putExtra("id", dm?.id)
                goInput.putExtra("nama", dm?.nama)
                goInput.putExtra("usia", dm?.usia)
                goInput.putExtra("domisili", dm?.domisili)
                ctx.startActivity(goInput)
            }
        }
    }
}