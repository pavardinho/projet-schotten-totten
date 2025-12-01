package com.schottenTotten.model;

public abstract class Carte {

    private String nom;

    public String getNom() { return this.nom; }

    public Carte(String nom){
        this.nom = nom;
    }    
}
