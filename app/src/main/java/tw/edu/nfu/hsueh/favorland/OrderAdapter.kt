package tw.edu.nfu.hsueh.favorland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(private val data:ArrayList<OrderModel>):RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private var onClickItem : ((OrderModel) -> Unit)? = null
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val tv_orderName = itemView.findViewById<TextView>(R.id.tv_orderName)
        val imgView_minus = itemView.findViewById<ImageView>(R.id.imgView_minus)
        val imgView_add = itemView.findViewById<ImageView>(R.id.imgView_add)
        val tv_count = itemView.findViewById<TextView>(R.id.tv_count)
    }
    fun setOnClickItem(callback: (OrderModel)->Unit) {
        this.onClickItem = callback
    }
    override fun onCreateViewHolder(ViewGroup: ViewGroup, viewType: Int): OrderAdapter.ViewHolder {
        val v = LayoutInflater.from(ViewGroup.context).inflate(R.layout.order_view,ViewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: OrderAdapter.ViewHolder, position: Int) {
        holder.tv_orderName.text = data[position].dish
        holder.tv_count.text = data[position].count.toString()
        holder.imgView_minus.setOnClickListener {
            if (data[position].count > 1 ){
                data[position].count -= 1
                onClickItem?.invoke(data[position])
            }
           else{
               data[position].count -= 1
               onClickItem?.invoke(data[position])
               data.removeAt(position)
           }
            notifyDataSetChanged()


        }
        holder.imgView_add.setOnClickListener {
            data[position].count += 1
            notifyDataSetChanged()
            onClickItem?.invoke(data[position])
        }

    }

    override fun getItemCount(): Int = data.size
}