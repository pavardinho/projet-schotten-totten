package com.schottenTotten.model;

public class CarteTactique extends Carte{
    
    private String nom;
    private String description;

    public String getNom() { return this.nom; }
    public String getDescription() { return this.description; }

    public CarteTactique(String nom, String description){
        this.nom = nom;
        this.description = description;
    }
}
