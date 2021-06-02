package tw.edu.nfu.hsueh.favorland

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val data:ArrayList<ItemModel>):RecyclerView.Adapter<ItemAdapter.ViewHolder>() {



    class ViewHolder(v:View) : RecyclerView.ViewHolder(v) {
        val btn_item = v.findViewById<Button>(R.id.btn_item)

    }
    override fun onCreateViewHolder(ViewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(ViewGroup.context).inflate(R.layout.list_item,ViewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btn_item.setText(data[position].name)
    }

    override fun getItemCount(): Int {
        return data.size
    }




}
