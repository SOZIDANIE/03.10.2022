package com.bignerdranch.android.pract11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bignerdranch.android.pract11.data.DATABASE_NAME
import com.bignerdranch.android.pract11.data.KnigaDB
import com.bignerdranch.android.pract11.data.models.KnigaTypes
import com.bignerdranch.android.pract11.data.models.KnigaZhanr
import java.util.*
import java.util.concurrent.Executors

class razbor : AppCompatActivity() {
    private val Books: MutableList<KnigaTypes> = mutableListOf()
    private val Book: MutableList<KnigaZhanr> = mutableListOf()
    private lateinit var db: KnigaDB
    private lateinit var aV: KnigaRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_razbor)
        val b2 = findViewById<ImageButton>(R.id.i)
        //11 13 15 17
        var tvNAME = findViewById<TextView>(R.id.textView11)
        var tvAUTHOR = findViewById<TextView>(R.id.textView13)
        var tvPAGES = findViewById<TextView>(R.id.textView15)
        var tvZHANR = findViewById<TextView>(R.id.textView17)
        val buttonDelte = findViewById<Button>(R.id.button)
        val buttonChange = findViewById<Button>(R.id.button2)
        db = Room.databaseBuilder(this, KnigaDB::class.java, DATABASE_NAME).build()
        val knigaDAO = db.knigaDAO()
        val executor = Executors.newSingleThreadExecutor()
        val executor2 = Executors.newSingleThreadExecutor()
        val index = intent.getIntExtra("number", -1)
        val intent = Intent(this, zapomnit::class.java)
        val imenenie = intent.getIntExtra("izmenilos", -1)

        Log.d("otladka", imenenie.toString())

        if(imenenie > -1)
        {
            upInfo()
        }




        db.knigaDAO().getAllBooks().observe(this, androidx.lifecycle.Observer {
            Books.addAll(it)
            aV = KnigaRVAdapter(this, Books)
            tvNAME.text = Books[index].name
            tvAUTHOR.text = Books[index].author
            tvPAGES.text = Books[index].numPages
        })



        db.knigaDAO().getAllZhanrs().observe(this) {
            Book.addAll(it)
            tvZHANR.text = Book[index].zhanr
        }

        buttonChange.setOnClickListener{
            intent.putExtra("number", index)
            startActivity(intent)

        }


        b2.setOnClickListener{
            super.onBackPressed()
        }
    }

    private fun upInfo(){
        Books.clear()
        db.knigaDAO().getAllBooks().observe(this, androidx.lifecycle.Observer {
            Books.addAll(it)
        })
        db.knigaDAO().getAllZhanrs().observe(this, {
            Book.addAll(it)
        })
        Log.d("as2d", "qweqweqwe")
    }
    override fun onResume() {
        super.onResume()
        upInfo()
        Log.d("asd", "qwe")
    }

}

