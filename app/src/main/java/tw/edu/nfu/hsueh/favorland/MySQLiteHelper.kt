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



    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
        db!!.execSQL("DROP TABLE IF EXISTS member")
        onCreate(db)
    }

}