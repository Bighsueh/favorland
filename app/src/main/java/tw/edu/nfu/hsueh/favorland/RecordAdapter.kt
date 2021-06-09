package tw.edu.nfu.hsueh.favorland

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecordAdapter(private val data:ArrayList<RecordModel>):RecyclerView.Adapter<RecordAdapter.ViewHolder>() {



    class ViewHolder(v:View) : RecyclerView.ViewHolder(v) {
        val tv_id = v.findViewById<TextView>(R.id.tv_recordid)
        val tv_date = v.findViewById<TextView>(R.id.tv_recordtime)
        val tv_quantity = v.findViewById<TextView>(R.id.tv_recordquantity)
        val tv_total = v.findViewById<TextView>(R.id.tv_recordtotal)
        val tv_member = v.findViewById<TextView>(R.id.tv_recordmemberid)



    }
    override fun onCreateViewHolder(ViewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(ViewGroup.context).inflate(R.layout.record_view,ViewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_id.setText(data[position].id)
        holder.tv_date.setText(data[position].time)
        holder.tv_quantity.setText(data[position].quantity)
        holder.tv_total.setText(data[position].total)
        holder.tv_member.setText(data[position].memberid)
        }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return data.size
    }
}


