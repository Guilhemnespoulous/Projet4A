package com.example.projet4a.presentation.main

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Projet4A.presentation.main.CreateAccount
import com.example.projet4a.domain.usecase.CreateUserUseCase
import com.example.projet4a.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel(){

    val loginLiveData: MutableLiveData<LoginStatus> = MutableLiveData()

    fun onClickedLogin(emailUser: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserUseCase.invoke(emailUser)
            val loginStatus = if(user != null){
                LoginSuccess(user.email, user.password, user)
            } else {
                LoginError
            }
            withContext(Dispatchers.Main){
                loginLiveData.value=loginStatus
            }
        }
    }

    fun onClickedCreateAccount(emailUser: String, password: String, fragmentManager: FragmentManager) {
        val createAccount = CreateAccount(emailUser, password)
        createAccount.show(fragmentManager, "activity_dialog")
    }
}