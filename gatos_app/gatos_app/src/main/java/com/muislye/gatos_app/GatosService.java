/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muislye.gatos_app;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author fco_g
 */
public class GatosService {

    public static void verGatos() throws IOException {
        //1. vamos a traer los datos de la API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").method("GET", null).build();
        Response response = client.newCall(request).execute();

        String json = response.body().string();
        json = json.substring(1, json.length() - 1);

        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(json, Gatos.class);

        Image imagen = null;
        try {
            URL url = new URL(gatos.getUrl());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.addRequestProperty("User-Agent", "");
            BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
            ImageIcon fondoGato = new ImageIcon(bufferedImage);

            if (fondoGato.getIconWidth() > 800 || fondoGato.getIconHeight() > 400) {

                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800, 400, java.awt.Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String menu = "Opciones: \n1.- Cambiar Gatitos \n2.-Favorito \n3.-Volver ";
            String botones[] = {"Ver Otra imagen", "Favoritos", "Volver"};
            String id_gato = gatos.getId();
            String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE,
                    fondoGato, botones, botones[0]);

            int seleccion = -1;

            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion = i;
                }
            }

            switch (seleccion) {
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatos);
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void favoritoGato(Gatos gato) {
        try {

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\":\"" + gato.getId() + "\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApikey())
                    .build();
            Response response = client.newCall(request).execute();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void verFavorito(String apikey) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.thecatapi.com/v1/favourites").method("GET", null).addHeader("x-api-key", apikey).build();
        Response response = client.newCall(request).execute();

        String elJson = response.body().string();
        Gson gson = new Gson();
        GatosFav[] gatosArray = gson.fromJson(elJson, GatosFav[].class);

        if (gatosArray.length > 0) {
            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() * (max - min) + 1) + min;
            int indice = aleatorio - 1;

            GatosFav gatofav = gatosArray[indice];
            Image imagen = null;
            try {
                URL url = new URL(gatofav.image.getUrl());
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.addRequestProperty("User-Agent", "");
                BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
                ImageIcon fondoGato = new ImageIcon(bufferedImage);

                if (fondoGato.getIconWidth() > 800 || fondoGato.getIconHeight() > 400) {

                    Image fondo = fondoGato.getImage();
                    Image modificada = fondo.getScaledInstance(800, 400, java.awt.Image.SCALE_SMOOTH);
                    fondoGato = new ImageIcon(modificada);
                }

                String menu = "Opciones: \n1.- Cambiar Gatitos \n2.-Eliminar Favorito \n3.-Volver ";
                String botones[] = {"Ver Otra imagen", "Eliminar Favoritos", "Volver"};
                String id_gato = gatofav.getId();
                String opcion = (String) JOptionPane.showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE,
                        fondoGato, botones, botones[0]);

                int seleccion = -1;

                for (int i = 0; i < botones.length; i++) {
                    if (opcion.equals(botones[i])) {
                        seleccion = i;
                    }
                }

                switch (seleccion) {
                    case 0:
                        verFavorito(apikey);
                        break;
                    case 1:
                        borrarFavorito(gatofav);
                        break;
                    default:
                        break;
                }

            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    public static void borrarFavorito(GatosFav gatofav) throws IOException {

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/"+gatofav.getId()+"")
                    .method("DELETE", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gatofav.getApikey())
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
