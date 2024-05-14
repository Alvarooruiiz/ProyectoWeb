/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tabla;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.inerttia.ittwsEntidades.wsAlmacen.PaletsRespuesta;
import java.net.MalformedURLException;
import java.net.URL;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PeticionJSON {
    
    private PaletsRespuesta paletsRespuesta;
    

    
    public void hacerLlamada(String ssccIntroducido) throws MalformedURLException{
        OkHttpClient client = new OkHttpClient();
        URL url = new URL("http://172.26.100.114:8080/ittws3/webresources/post");
        
        String jsonData = "{" +
                    "\"almacen\": null," +
                    "\"centro\": null," +
                    "\"empresa\": null," +
                    "\"listaparametros\": []," +
                    "\"metodo\": \"getPalet\"," +
                    "\"parametro1\": \"" +  ssccIntroducido + "\"," +
                    "\"parametro2\": \"1\"," +
                    "\"parametro3\": \"1\"," +
                    "\"parametro4\": \"\"," +
                    "\"parametro5\": \"\"," +
                    "\"parametro6\": \"\"," +
                    "\"parametro7\": \"\"," +
                    "\"parametros\": \"\"," +
                    "\"password\": \"admin\"," +
                    "\"tracking\": \"\"," +
                    "\"usuario\": \"admin\"," +
                    "\"version\": \"1.1.2.37\"" +
                "}";

        RequestBody body = RequestBody.create(
                jsonData,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                paletsRespuesta = mapper.readValue(responseBody, PaletsRespuesta.class);
                System.out.println("Palets: " + paletsRespuesta.getPalets());
                System.out.println("Respuesta: " + paletsRespuesta.getRespuesta());
            } else {
                System.out.println("Request failed with error code: " + response.code());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void setPaletsRespuesta(PaletsRespuesta paletsRespuesta) {
        this.paletsRespuesta = paletsRespuesta;
    }

    public PaletsRespuesta getPaletsRespuesta() {
        return paletsRespuesta;
    }
    
    
}