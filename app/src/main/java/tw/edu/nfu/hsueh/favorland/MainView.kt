package tw.edu.nfu.hsueh.favorland

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main_view.*
import kotlinx.android.synthetic.main.nav_header.*

class MainView : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var menuListAdapter:MenuListAdapter
    private lateinit var orderAdapter: OrderAdapter
    private val itemContacts = ArrayList<ItemModel>()
    private val menuList = ArrayList<MenuList>()
    private val order = ArrayList<OrderModel>()
    private lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        //database 能讀寫
        db = MySQLiteHelper(this).readableDatabase
        //select
        val query = db.rawQuery("SELECT * FROM items",null)
        // 宣告recyclerView
        val recycler = findViewById<RecyclerView>(R.id.rcv_item)
        // 透過grid的方式排版 當超過6欄會換行
        val gridLayoutManager = GridLayoutManager(this,6)
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
        val menuGridLayoutManager = GridLayoutManager(this,3)
        menuListAdapter = MenuListAdapter(menuList)
        recyclerMenu.layoutManager = menuGridLayoutManager
        recyclerMenu.adapter = menuListAdapter
        // recylerMenu點擊
        menuListAdapter?.setOnClickItem {
            order.add(OrderModel(it.dish,it.price,1))
            orderAdapter.notifyDataSetChanged()
        }
        val recyclerOrder = findViewById<RecyclerView>(R.id.rcv_order)
        orderAdapter = OrderAdapter(order)
        val orderLinearLayout = LinearLayoutManager(this)
        recyclerOrder.adapter = orderAdapter
        recyclerOrder.layoutManager = orderLinearLayout







        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        intent?.extras?.let {
            val data = it.getString("UserName")
            Toast.makeText(applicationContext,data,Toast.LENGTH_SHORT).show()
            val nav = findViewById<NavigationView>(R.id.nav_view)
            val headerView = nav.getHeaderView(0)
            val navUserName = headerView.findViewById<TextView>(R.id.user_name)
            navUserName.setText(data)
        }
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
                }
                R.id.nav_logout -> {
                    Toast.makeText(applicationContext,"Clicked logout",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
