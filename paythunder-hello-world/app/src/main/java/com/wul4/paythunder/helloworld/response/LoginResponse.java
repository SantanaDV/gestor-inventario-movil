package com.wul4.paythunder.helloworld.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la respuesta de inicio de sesión.
 * Esta clase se utiliza para recibir los datos de inicio de sesión del servidor.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    //Mensaje de error o de otro tipo de respuesta
    private String message;

}
