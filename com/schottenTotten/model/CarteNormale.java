package com.schottenTotten.model;

public class CarteNormale extends Carte {

    public enum Valeur {
        UN(1), DEUX(2), TROIS(3), QUATRE(4), CINQ(5), SIX(6), SEPT(7), HUIT(8), NEUF(9);

        private int numero;
        private Valeur(int n) { this.numero = n; }
        public int getNumero() { return this.numero; }
    }
    public enum Couleur {VERT, BLEU, ROUGE, JAUNE, VIOLET, MARRON}

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
