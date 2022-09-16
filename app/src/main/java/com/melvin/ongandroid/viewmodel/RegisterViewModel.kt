package com.melvin.ongandroid.viewmodel

import androidx.lifecycle.*
import com.melvin.ongandroid.application.Validator
import com.melvin.ongandroid.model.Register
import com.melvin.ongandroid.model.repository.Network.interfaces.*
import kotlinx.coroutines.launch


class RegisterViewModel(private val newRegisterStatus: NewRegisterStatus) : ViewModel() {

    private val _bottonEnable = MutableLiveData(false)
    val bottonEnable: LiveData<Boolean> = _bottonEnable

    private val _passwordAreDiferent: MutableLiveData<Boolean> = MutableLiveData(false)
    val passwordAreDiferent: LiveData<Boolean> = _passwordAreDiferent

    private val _statusNewRegister = MutableLiveData<Boolean>()
    val statusNewRegister : LiveData<Boolean> = _statusNewRegister

    fun validButtonRegister(name: String, email: String,
                            password1: String, password2: String){
        //veo si las dos contraseñas son iguales
        val areTheSame = arePasswordsTheSame(password1, password2)
        _passwordAreDiferent.value = areTheSame

        //si las contraseñas son iguales entonces verifico si todos los campos cumplen las condiciones
        if(areTheSame){
            //verifico si todos los campos cumplen
            val isVerify = areTheSame && isEmailAndNameValid(name, email)
            _bottonEnable.value = isVerify
        }

    }

    fun isEmailAndNameValid(name: String, email: String): Boolean{
        return name.isNotEmpty() &&
                email.isNotEmpty()&&
                Validator.isEmailValid(email)
    }

    //funcion que verifica que las dos contrasenias sean iguales y no vacias
    private fun arePasswordsTheSame(password1: String, password2: String): Boolean {
        //en caso de que sean distintas avisa al observable que no lo son
        return password1 == password2
    }

// Para registrar usuario pero la funcion no está completo todavia
    // la parte errores y

    fun saveNewRegister(name: String, email: String, password: String){

        viewModelScope.launch {
            val responseRegister = newRegisterStatus(
                Register(name, email, password)
            )
            if(responseRegister.success){
                _statusNewRegister.postValue(true)
            }
        }
    }

}