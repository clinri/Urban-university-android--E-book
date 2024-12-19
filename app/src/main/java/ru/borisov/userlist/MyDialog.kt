package ru.borisov.userlist

import android.content.Context
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog

class MyDialog {
    companion object {
        fun createDialog(context: Context, adapter: ArrayAdapter<User>) =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Внимание")
                    .setMessage("Удалить пользователя?")
                    .setCancelable(true)
                    .setPositiveButton("Да") { _, _ ->
                        adapter.remove(adapter.getItem(position))
                    }
                    .setNegativeButton("Нет") { dialog, _ ->
                        dialog.cancel()
                    }.create()
                builder.show()
            }
    }
}