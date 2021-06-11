package tw.edu.nfu.hsueh.favorland

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.record_view.*


class RecordFragment : Fragment() {

    private lateinit var recordAdapter: RecordAdapter
    private val record = ArrayList<RecordModel>()
    private lateinit var db: SQLiteDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        db = MySQLiteHelper(context!!).readableDatabase
        val view = inflater.inflate(R.layout.fragment_record, container, false)

        val queryy = db.rawQuery("SELECT * FROM records",null)

        val recyclerrecord = view.findViewById<RecyclerView>(R.id.rcv_record)

        val recordGLM = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        recordAdapter = RecordAdapter(record)

        recyclerrecord.layoutManager = recordGLM

        recyclerrecord.adapter = recordAdapter

        queryy.moveToFirst()
        record.clear()
        for(i in 0 until queryy.count){
            record.add(RecordModel(queryy.getInt(0),queryy.getString(1),queryy.getInt(2),queryy.getInt(3),queryy.getInt(4)))
            queryy.moveToNext()
            recordAdapter.notifyDataSetChanged()
        }


        return view



    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }




    }