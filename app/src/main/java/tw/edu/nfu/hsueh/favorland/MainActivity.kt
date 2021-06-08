package tw.edu.nfu.hsueh.favorland

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            db = MySQLiteHelper(this).writableDatabase
            val query = db.rawQuery("SELECT * FROM member WHERE acc = '${ed_acc.text}' AND psd = '${ed_psd.text}'",null)

            query.moveToFirst()

            if(query.count >0){
                val intent = Intent(this,MainView::class.java)
//                Toast.makeText(this, "使用者:${query.getString(1)}", Toast.LENGTH_SHORT).show()
                intent.putExtra("UserName","${query.getString(1)}")
                intent.putExtra("UserId",query.getInt(0))
                startActivity(intent)
                db.close()

            }
            else {
                db.close()
                Toast.makeText(this, "帳號或密碼錯誤", Toast.LENGTH_SHORT).show()

            }
        }
    }
}