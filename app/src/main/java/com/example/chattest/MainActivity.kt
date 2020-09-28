package com.example.chattest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URISyntaxException

class MainActivity : AppCompatActivity() {

    private lateinit var socket: Socket
    private val adapter = ChatAdapter()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButtonEvent()
        initRecyclerView()
        initSocket()
        initSocketEvent()

    }

    override fun onDestroy() {
        super.onDestroy()

        socket.disconnect()
    }

    private fun initButtonEvent() {
        send.setOnClickListener { sendMessage() }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@MainActivity.adapter
        }
    }

    private fun initSocket() {
        try {
            socket = IO.socket(URL)
            socket.connect()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    private fun initSocketEvent() {
        socket.on("chat message") {
            val message = gson.fromJson(it[0].toString(), Message::class.java)
            runOnUiThread {
                adapter.addList(message)
            }
        }
    }

    private fun sendMessage() {
        val message = Message(chat.text.toString(), name.text.toString())
        socket.emit("chat message", gson.toJson(message))
        chat.setText("")
    }

    companion object {
        private const val URL = "http://10.0.2.2:8080"
    }
}