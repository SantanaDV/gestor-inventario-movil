package com.wul4.paythunder.gestorInventario.utils;


import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Clase de utilidades con distitns funciones utiles a lo largo del programa
 */
public class Funciones {

    /**
     * Comprueba que el string solo contiene numeros y letras
     *
     * @param contrasena
     * @return boolean
     */
    public static boolean stringNumerosYLetras(String contrasena) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]+$";;
        return contrasena.matches(regex);
    }

    /**
     * Comprueba que el string contiene mas de 6 caracteres
     *
     * @param contrasena
     * @return boolean
     */
    public static boolean stringMayorASeisCaracteres(String contrasena) {
        return contrasena.length() >= 6;
    }

    /**
     * Comprueba que el string contiene un numero mediante el metodo equals
     * @param cadena1
     * @param cadena2
     * @return boolean
     */
    public static boolean comprobarCadenasIguales(String cadena1, String cadena2) {
        return cadena1.equals(cadena2);
    }

    /**
     * Comprueba que el email tiene el formato correcto
     * @param email
     * @return boolean
     */
    public static boolean comprobarFormatoCorreo(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }

    /**
     * Comprueba que el editText esta vacio
     * @param editText
     * @return boolean
     */
    public static boolean editTextEmpty(EditText editText){
        return  editText.getText().toString().isEmpty();
    }

}
