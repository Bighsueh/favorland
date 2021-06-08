package tw.edu.nfu.hsueh.favorland

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MenuListAdapter(private val data:ArrayList<MenuList>): RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {

    class ViewHolder(v: View):RecyclerView.ViewHolder(v){
        val btn_menuItems = v.findViewById<Button>(R.id.btn_menuItems)
    }

    override fun onCreateViewHolder(ViewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(ViewGroup.context).inflate(R.layout.menu_list_view,ViewGroup,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btn_menuItems.text=data[position].dish
    }

    override fun getItemCount(): Int =data.size
}