package com.melvin.ongandroid.view.contacto

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.melvin.ongandroid.application.Validator

import com.melvin.ongandroid.databinding.FragmentContactoBinding



class ContactoFragment : Fragment() {
    private var binding : FragmentContactoBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentContactoBinding.inflate(inflater, container, false)

        botonEnviarEstaActivado(false)

        validarCampos()

        return binding?.root
    }

    /**
     * Esta variable fue necesaria porque sino hubiese tenido que repetir el textWatcher en cada uno de los EditText
     */
    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        /**
         * Este metodo sobrescrito se encarga de guardar los cambios que haya en el EditText luego de escribir y despues activa el boton
         */
        override fun afterTextChanged(editable : Editable?) {
             var etNombreYApellido = ""
             var etEmail = ""
             var etMensaje = ""
            binding?.let { binding ->
                etNombreYApellido = binding.etNombreYApellido?.text.toString()
                etEmail = binding.etEmail.text.toString()
                etMensaje = binding.etMensaje.text.toString()
            }

            botonEnviarEstaActivado(etNombreYApellido.isNotEmpty() && etEmail.isNotEmpty()
                    && Validator.isEmailValid(etEmail) && etMensaje.isNotEmpty())

        }

    }

    /**
     * Valida que los campos no esten vacios y que el email tenga el formato correspondiente antes de activar el boton
     */
    private fun validarCampos() {
        binding?.let { binding ->
            binding.etNombreYApellido.addTextChangedListener(textWatcher)
            binding.etEmail.addTextChangedListener(textWatcher)
            binding.etMensaje.addTextChangedListener(textWatcher)
        }

    }

    /**
     * Activa o desactiva el boton de enviar
     */
    private fun botonEnviarEstaActivado(boolean : Boolean) {
        binding?.let { binding ->
            binding.btnSubmit.isEnabled = boolean
        }
    }


}