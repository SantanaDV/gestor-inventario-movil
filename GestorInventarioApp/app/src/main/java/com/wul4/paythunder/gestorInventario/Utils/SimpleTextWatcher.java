package com.wul4.paythunder.gestorInventario.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Clase abstracta que implementa TextWatcher para simplificar el código.
 *
 */
public abstract class SimpleTextWatcher implements TextWatcher {
    /**
     * Es utilizadoEste método se llama justo antes de que el usuario realice un cambio en el texto
     * @param s La secuencia de caracteres actual (texto) que se mostraba antes de la modificación.
     * @param start La posición inicial donde se efectuará el cambio.
     * @param count La cantidad de caracteres que serán sustituidos/quitados.
     * @param after La cantidad de caracteres que se insertarán.
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    /**
     * Este método se llama justo después de que el usuario realice un cambio en el texto.
     * @param s  Un objeto Editable que contiene el texto modificado.
     */
    @Override
    public void afterTextChanged(Editable s) {}
}
