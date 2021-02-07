package com.aponte.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class Utileria {

    public static String guardarArchivo(MultipartFile multiPart, String ruta) {
        //obtenemos el nombre original del archivo
        String nombreOriginal = multiPart.getOriginalFilename();
        nombreOriginal=nombreOriginal.replace(" ", "-");
        nombreOriginal=randomAlphaNumeric(8)+nombreOriginal;
        try {
            //Formamos el nombre del archivo para guardarlo en el disco duro.
            File imageFile = new File(ruta + nombreOriginal);
            System.out.println("Archivo: "+imageFile.getAbsolutePath());
            //Guardamos fisicamente el archivo en HD
            multiPart.transferTo(imageFile);
            return nombreOriginal;
        } catch (IOException e) {
            System.err.println("Error "+e.getMessage());
            return null;
        }
    }

    //Generar cadena aleatoria
    public static String randomAlphaNumeric(int count){
        String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while(count-- !=0){
            int character = (int) (Math.random()*CARACTERES.length());
            builder.append(CARACTERES.charAt(character));
        }
        return builder.toString();
    }

}
