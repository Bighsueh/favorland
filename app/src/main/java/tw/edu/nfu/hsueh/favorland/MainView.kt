package tw.edu.nfu.hsueh.favorland

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main_view.*
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.nav_header.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainView : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var menuListAdapter:MenuListAdapter
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var recordAdapter: RecordAdapter
    private val itemContacts = ArrayList<ItemModel>()
    private val menuList = ArrayList<MenuList>()
    private val order = ArrayList<OrderModel>()
    private val record = ArrayList<RecordModel>()

    private lateinit var db: SQLiteDatabase
    private lateinit var userName:String
    private var total:Int = 0
    private var discount:Float = 1.0F
    private var userId:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        //database 能讀寫


        intent?.extras?.let {
            userName = it.getString("UserName").toString()
            userId = it.getInt("UserId")
//            Toast.makeText(applicationContext,userName,Toast.LENGTH_SHORT).show()
            val nav = findViewById<NavigationView>(R.id.nav_view)
           val headerView = nav.getHeaderView(0)
            val navUserName = headerView.findViewById<TextView>(R.id.user_name)
            navUserName.setText(userName)
        }


        db = MySQLiteHelper(this).readableDatabase
        //select
        val query = db.rawQuery("SELECT * FROM items",null)
        // 宣告recyclerView
        val recycler = findViewById<RecyclerView>(R.id.rcv_item)
        // 透過grid的方式排版 當超過6欄會換行
        val gridLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
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
        val recyclerMenu = findViewById<RecyclerView>(R.id.rcv_dishes)
        val menuGridLayoutManager = GridLayoutManager(this,4)
        menuListAdapter = MenuListAdapter(menuList)
        recyclerMenu.layoutManager = menuGridLayoutManager
        recyclerMenu.adapter = menuListAdapter
        // recylerMenu點擊
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
        val recyclerOrder = findViewById<RecyclerView>(R.id.rcv_order)
        orderAdapter = OrderAdapter(order)
        val orderLinearLayout = LinearLayoutManager(this)
        recyclerOrder.adapter = orderAdapter
        recyclerOrder.layoutManager = orderLinearLayout

        orderAdapter?.setOnClickItem {
            calculationTotal()
        }



        btn_insert.setOnClickListener {
            calculationTotal()
            val sdf = SimpleDateFormat("yyyy/dd/M hh:mm")
            val currentDate = sdf.format(Date())
            var quantity:Int = 0
            for(i in 0 until order.count()){
                quantity+=order[i].count
            }

            val recordInsertsql = "INSERT INTO records(date, orderquantity, total, userid) VALUES(?,?,?,?)"
            db.execSQL(recordInsertsql, arrayOf(currentDate,quantity,total,userId))
            clearView()



        }

        ed_discount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                discount = ed_discount.text.toString().toFloat()
                if (discount>1){
                    ed_discount.setText("1")
                }
            }

        })




        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_view.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_order -> {
                    Toast.makeText(applicationContext,"Clicked order",Toast.LENGTH_SHORT).show()
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.main_fragment,OrderFragment()).commit()
                }
                R.id.nav_record -> {
                    Toast.makeText(applicationContext,"Clicked record",Toast.LENGTH_SHORT).show()
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.main_fragment,RecordFragment()).commit()

                    val query = db.rawQuery("SELECT * FROM records",null)

                    val recyclerrecord = findViewById<RecyclerView>(R.id.rcv_record)

                    val recordGLM = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

                    recordAdapter = RecordAdapter(record)

                    recyclerrecord.layoutManager = recordGLM

                    recyclerrecord.adapter = orderAdapter

                    query.moveToFirst()
                    record.clear()
                    for(i in 0 until query.count){
                        record.add(RecordModel(query.getInt(0),query.getString(1),query.getInt(2),query.getInt(3),query.getInt(4)))
                        query.moveToNext()
                        recordAdapter.notifyDataSetChanged()
                    }

                }
                R.id.nav_logout -> {
                    Toast.makeText(applicationContext,"Clicked logout",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            true
        }
    }
    fun calculationTotal() {
        total = 0
        discount = ed_discount.text.toString().toFloat()
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
