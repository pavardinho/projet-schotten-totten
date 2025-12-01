package com.schottenTotten.model;

public class CarteTactique extends Carte{
    
    private String description;

    public String getNom() { return super.getNom(); }
    public String getDescription() { return this.description; }

    public CarteTactique(String nom, String description){
        super(nom);
        this.description = description;
    }
}
