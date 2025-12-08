package com.schottenTotten.model;

public abstract class Carte {

    private String nom;
    private CarteNormale.Valeur valeur;
    private CarteNormale.Couleur couleur;

    public String getNom() { return this.nom; }
    public CarteNormale.Valeur getValeur() { return this.valeur; }
    public CarteNormale.Couleur getCouleur() { return this.couleur; }

    public Carte(String nom){
        this.nom = nom;
    }    
}
