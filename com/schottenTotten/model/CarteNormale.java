package com.schottenTotten.model;


public class CarteNormale extends Carte {

    public enum Valeur {UN, DEUX, TROIS, QUATRE, CINQ, SIX, SEPT, HUIT, NEUF}
    public enum Couleur {VERT, BLEU, ROUGE, JAUNE, VIOLET, MARRON}

    private Valeur valeur;
    private Couleur couleur;

    public Valeur getValeur() { return this.valeur; }
    public Couleur getCouleur() { return this.couleur; }

    public CarteNormale(Valeur v, Couleur c){
        super(v.name() + "_" + c.name().charAt(0));
        this.valeur = v;
        this.couleur = c;
    }
}
