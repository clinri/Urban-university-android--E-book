package ru.borisov.userlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val userList: MutableList<User> = mutableListOf()
    lateinit var nameET: EditText
    lateinit var ageET: EditText
    lateinit var saveBTN: Button
    lateinit var userListLV: ListView
    lateinit var toolbar: Toolbar
    lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        nameET = findViewById(R.id.nameET)
        ageET = findViewById(R.id.ageET)
        saveBTN = findViewById(R.id.saveBTN)
        userListLV = findViewById(R.id.userListLV)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val adapter = object : ArrayAdapter<User>(
            /* context = */ this,
            /* resource = */ android.R.layout.simple_list_item_2,
            /* textViewResourceId = */ android.R.id.text1,
            /* objects = */ userList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return super.getView(position, convertView, parent).apply {
                    findViewById<TextView>(android.R.id.text1).text = getItem(position)?.name
                    findViewById<TextView>(android.R.id.text2).text =
                        getItem(position)?.age.toString()
                }
            }
        }
        userListLV.adapter = adapter
        userViewModel.userList.observe(this) { list ->
            list?.let {
                userList.clear()
                userList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
        saveBTN.setOnClickListener {
            if (nameET.text.isNotEmpty() && ageET.text.isNotEmpty()) {
                val name = nameET.text.toString()
                val age = ageET.text.toString().toInt()
                val list = userViewModel.userList.value?.toMutableList() ?: mutableListOf()
                list.add(User(name, age))
                userViewModel.userList.value = list
                nameET.text.clear()
                ageET.text.clear()
            }
        }
        userListLV.onItemClickListener = MyDialog.createDialog(this, adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit) finish()
        return super.onOptionsItemSelected(item)
    }
}