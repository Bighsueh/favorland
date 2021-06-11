package tw.edu.nfu.hsueh.favorland

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_order.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.contracts.contract


class OrderFragment : Fragment() {

    private lateinit var db: SQLiteDatabase
    private lateinit var itemAdapter: ItemAdapter
    private val itemContacts = ArrayList<ItemModel>()

    private lateinit var menuListAdapter:MenuListAdapter
    private val menuList = ArrayList<MenuList>()

    private lateinit var orderAdapter:OrderAdapter
    private val order = ArrayList<OrderModel>()

    private var total:Int = 0

    private lateinit var userName:String
    private var discount:Float = 1.0F
    private var userId:Int? = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        userName = arguments?.getString("UserName").toString()
        userId = arguments?.getInt("UserId")


        val view = inflater.inflate(R.layout.fragment_order, container, false)
        // Inflate the layout for this fragment
        db = MySQLiteHelper(context!!).readableDatabase

        val recyclerMenu = view.findViewById<RecyclerView>(R.id.rcv_dishes)
        val menuGridLayoutManager = GridLayoutManager(context!!,4)
        menuListAdapter = MenuListAdapter(menuList)
        recyclerMenu.layoutManager = menuGridLayoutManager
        recyclerMenu.adapter = menuListAdapter

        itemView(view)

        val recyclerOrder = view.findViewById<RecyclerView>(R.id.rcv_order)
        orderAdapter = OrderAdapter(order)
        val orderLinearLayout = LinearLayoutManager(context!!)
        recyclerOrder.adapter = orderAdapter
        recyclerOrder.layoutManager = orderLinearLayout

        menuListAdapter?.setOnClickItem {
            var adddish:String = it.dish
            var index:Int = -1
            for (i in 0 until order.count()){
                if(adddish == order[i].dish){
                    index = i
                    order[i].count+=1
                }
            }
            if(index == -1){order.add(OrderModel(it.dish,it.price,1))}
            orderAdapter.notifyDataSetChanged()
            calculationTotal()
        }


        orderAdapter?.setOnClickItem {
            calculationTotal()
        }
        val btn_insert = view.findViewById<Button>(R.id.btn_insert)
        btn_insert.setOnClickListener {
            calculationTotal()
            val sdf = SimpleDateFormat("yyyy/M/dd hh:mm")
            val currentDate = sdf.format(Date())
            var quantity:Int = 0
            for(i in 0 until order.count()){
                quantity+=order[i].count
            }

            val recordInsertsql = "INSERT INTO records(date, orderquantity, total, userid) VALUES(?,?,?,?)"
            db.execSQL(recordInsertsql, arrayOf(currentDate,quantity,total,userId))
            clearView()
        }
        val ed_discount = view.findViewById<EditText>(R.id.ed_discount)
        ed_discount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }


            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculationTotal()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        return view

    }
    fun itemView(v:View){
        val query = db.rawQuery("SELECT * FROM items",null)
        // 宣告recyclerView
        val recycler = v.findViewById<RecyclerView>(R.id.rcv_item)
        // 透過grid的方式排版 當超過6欄會換行
        val gridLayoutManager = LinearLayoutManager(context!!,LinearLayoutManager.HORIZONTAL,false)
        //adapter綁定資料來源
        itemAdapter = ItemAdapter(itemContacts)
        // recycler 綁定 gridLayout
        recycler.layoutManager = gridLayoutManager
        // recycler的adapter 使用itemAdapter
        recycler.adapter = itemAdapter
        //添加資料到dataClass
        query.moveToFirst()
        itemContacts.clear()
        for(i in 0 until query.count){
            itemContacts.add(ItemModel(query.getInt(0),query.getString(1)))
            query.moveToNext()
            itemAdapter.notifyDataSetChanged()
        }
        // recycler點擊事件
        itemAdapter?.setOnClickItem {
            menuList.clear()
            val dish = db.rawQuery("SELECT dish,price,item FROM dishes JOIN items ON dishes.itemid = items.id WHERE item = '${it.name}'",null)
            dish.moveToFirst()
            for(i in 0 until dish.count){
                menuList.add(MenuList(dish.getString(0),dish.getInt(1),dish.getString(2)))
                dish.moveToNext()
                menuListAdapter.notifyDataSetChanged()
            }
        }
    }
    fun calculationTotal() {
        if(ed_discount.text.toString() !=""){
            discount = ed_discount.text.toString().toFloat()
        }
        if(discount>1.0F){
            discount = 1.0F
            ed_discount.setText("1")
        }
        total = 0
        for (i in 0 until order.count()){
            total += (order[i].price * order[i].count)
        }
        total = (discount * total).toInt()
        tv_total.setText(total.toString())

    }
    fun clearView(){
        ed_task.setText("")
        ed_people.setText("")
        order.clear()

        total = 0
        calculationTotal()
        orderAdapter.notifyDataSetChanged()
    }




}