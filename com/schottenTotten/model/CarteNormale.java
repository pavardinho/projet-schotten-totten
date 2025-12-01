package com.schottenTotten.model;

enum Valeur {UN, DEUX, TROIS, QUATRE, CINQ, SIX, SEPT, HUIT, NEUF}
enum Couleur {VERT, BLEU, ROUGE, JAUNE, VIOLET, MARRON}

public class CarteNormale extends Carte {

    private Valeur valeur;
    private Couleur couleur;

    public Valeur getValeur() { return this.valeur; }
    public Couleur getCouleur() { return this.couleur; }

    public CarteNormale(Valeur v, Couleur c){
        super(v.name() + "_" + c.name());
        this.valeur = v;
        this.couleur = c;
    }
}
