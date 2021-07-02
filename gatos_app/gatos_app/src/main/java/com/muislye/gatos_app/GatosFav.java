/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muislye.gatos_app;

/**
 *
 * @author fco_g
 */
public class GatosFav {
    String id;
    String image_id;
    String apikey="da52a4ac-0a37-4527-acbe-4bf2a5e71679";
    Imagex image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public Imagex getImage() {
        return image;
    }

    public void setImage(Imagex image) {
        this.image = image;
    }
}
