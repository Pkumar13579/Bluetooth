package com.example.iteradmin.bluetooth

import android.app.Activity
import android.app.DownloadManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val ba:BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val Request:Int = 100
    var listView:ListView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val active = findViewById<Button>(R.id.active)
        val list = findViewById<ListView>(R.id.listView)



        active.setOnClickListener{
            if (ba == null)
            {
                Toast.makeText(this,"Bluetooth not supported",Toast.LENGTH_LONG).show()
            }
            else
            {
                if (ba.isEnabled == false)
                {
                    active.text = "turn OFF"
                    val i = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(i,Request)
                }
                else{
                    ba.disable()
                    active.text = " turn ON"
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Request && resultCode == Activity.RESULT_OK)
        {
            var data:Array<String> = arrayOf()
            val devices:Set<BluetoothDevice> = ba.bondedDevices
            for (device in devices) {
                val names:String = device.name
                val address:String = device.address

                data += names
            }
            val adp = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
            listView?.adapter = adp
        }
        else{
            Toast.makeText(this,"intent not working",Toast.LENGTH_LONG).show()
        }
    }
}
