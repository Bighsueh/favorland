package tw.edu.nfu.hsueh.favorland

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class MySQLiteHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "sqlite.db"

    }
    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE member(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,acc TEXT,psd TEXT)")
        val memberInsertsql = "INSERT into member(name, acc, psd) VALUES(?,?,?)"
        db?.execSQL(memberInsertsql, arrayOf("林政佑","123","456"))
        db?.execSQL(memberInsertsql, arrayOf("辛昌紘","123","123"))

        db?.execSQL("CREATE TABLE items(id INTEGER PRIMARY KEY AUTOINCREMENT,item TEXT)")
        val  itemInsersql = "INSERT into items(item) VALUES(?)"
        db?.execSQL(itemInsersql, arrayOf("沙拉"))
        db?.execSQL(itemInsersql, arrayOf("主食"))
        db?.execSQL(itemInsersql, arrayOf("配菜"))
        db?.execSQL(itemInsersql, arrayOf("湯品"))
        db?.execSQL(itemInsersql, arrayOf("甜點"))
        db?.execSQL(itemInsersql, arrayOf("飲品"))

        db?.execSQL("CREATE TABLE dishes(id INTEGER PRIMARY KEY AUTOINCREMENT,dish TEXT,price INTEGER,itemid INTEGER)")
        val dishesInsertsql = "INSERT into dishes(dish, price, itemid) VALUES(?,?,?)"
        db?.execSQL(dishesInsertsql, arrayOf("塔塔醬",30,1))
        db?.execSQL(dishesInsertsql, arrayOf("沙拉醬",30,1))
        db?.execSQL(dishesInsertsql, arrayOf("貢丸米粉",40,2))
        db?.execSQL(dishesInsertsql, arrayOf("紅薑黃飯",40,2))
        db?.execSQL(dishesInsertsql, arrayOf("三杯雞",50,3))
        db?.execSQL(dishesInsertsql, arrayOf("清蒸魚",50,3))
        db?.execSQL(dishesInsertsql, arrayOf("清蒸蛤蜊",50,3))
        db?.execSQL(dishesInsertsql, arrayOf("酒粕貢丸湯",100,4))
        db?.execSQL(dishesInsertsql, arrayOf("蒜頭醋燉雞湯",100,4))
        db?.execSQL(dishesInsertsql, arrayOf("麻油雞湯",100,4))
        db?.execSQL(dishesInsertsql, arrayOf("酒粕貢丸湯",100,4))
        db?.execSQL(dishesInsertsql, arrayOf("冰棒",20,5))
        db?.execSQL(dishesInsertsql, arrayOf("冰淇淋",20,5))
        db?.execSQL(dishesInsertsql, arrayOf("醋果凍",20,5))
        db?.execSQL(dishesInsertsql, arrayOf("水果酪",20,5))
        db?.execSQL(dishesInsertsql, arrayOf("紅茶",30,6))
        db?.execSQL(dishesInsertsql, arrayOf("奶茶",30,6))
        db?.execSQL(dishesInsertsql, arrayOf("果醋氣泡飲",30,6))

//       task 桌號 total 小記
        db?.execSQL("CREATE TABLE records(id INTEGER PRIMARY KEY AUTOINCREMENT,date TEXT,orderquantity INTEGER,total INTEGER,userid INTEGER)")
        val recordInsertsql = "INSERT into records(date, orderquantity, total,userid) VALUES(?,?,?,?)"
        db?.execSQL(recordInsertsql, arrayOf("2021/6/12 03:44",5,500,1))

//      record 再加入時抓records生成的id 也就是這筆結帳records的id對多樣菜品到這裡 ；item 蔡名(看你要用TEXT還是INTEGER) ；quantity 數量
        db?.execSQL("CREATE TABLE recorddetails(id INTEGER PRIMARY KEY AUTOINCREMENT,record INTEGER,item TEXT,quantity INTEGER)")



    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
        db!!.execSQL("DROP TABLE IF EXISTS member")
        onCreate(db)
    }

}