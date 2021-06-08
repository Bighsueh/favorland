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

class ItemAdapter(private val data:ArrayList<ItemModel>):RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private var onClickItem : ((ItemModel) -> Unit)? = null
    class ViewHolder(v:View) : RecyclerView.ViewHolder(v) {
        val btn_item = v.findViewById<Button>(R.id.btn_item)

    }
    fun setOnClickItem(callback:(ItemModel)->Unit){
        this.onClickItem = callback
    }
    override fun onCreateViewHolder(ViewGroup: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(ViewGroup.context).inflate(R.layout.list_item,ViewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btn_item.setText(data[position].name)
        holder.btn_item.setOnClickListener {
            onClickItem?.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }




}
