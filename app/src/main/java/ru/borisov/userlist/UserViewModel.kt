package ru.borisov.userlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val userList: MutableLiveData<List<User>> by lazy { MutableLiveData<List<User>>() }
}