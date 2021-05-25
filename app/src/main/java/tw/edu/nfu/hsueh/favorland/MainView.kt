package tw.edu.nfu.hsueh.favorland

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_main_view.*

class MainView : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)

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