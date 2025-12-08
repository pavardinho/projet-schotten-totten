package com.schottenTotten.model;

import java.util.Comparator;
import java.util.List;


public class Combinaison {
    public enum TypeCombinaison {
        SUITE_COULEUR(5),
        BRELAN(4),
        COULEUR(3),
        SUITE(2),
        SOMME(1);

        private int numero;
        private TypeCombinaison(int n) { this.numero = n; }
        public int getNumero() { return this.numero; }
    }
    private TypeCombinaison type;
    private int somme_valeur;

    public void setType(List<Carte> lc){
        lc.sort(Comparator.comparingInt(c -> c.getValeur().getNumero()));
        int c1_val = lc.get(0).getValeur().getNumero();
        int c2_val = lc.get(1).getValeur().getNumero();
        int c3_val = lc.get(2).getValeur().getNumero();
        CarteNormale.Couleur c1_coul = lc.get(0).getCouleur();
        CarteNormale.Couleur c2_coul = lc.get(1).getCouleur();
        CarteNormale.Couleur c3_coul = lc.get(2).getCouleur();

        if (c1_val + 1 == c2_val && c1_val + 2 == c3_val) {
            if (c1_coul == c2_coul && c2_coul == c3_coul) {
                this.type = TypeCombinaison.SUITE_COULEUR;
            }
            else {
                this.type = TypeCombinaison.SUITE;
            }
        } else if (c1_coul == c2_coul && c2_coul == c3_coul) {
            this.type = TypeCombinaison.COULEUR;
        } else if (c1_val == c2_val && c2_val == c3_val) {
            this.type = TypeCombinaison.BRELAN;
        } else {
            this.type = TypeCombinaison.SOMME;
        }
    }

    public void setSommeValeur(List<Carte> lc){
        this.somme_valeur = 0;
        for (Carte c : lc) {
            this.somme_valeur += c.getValeur().getNumero();
        }
    }

    public static int compareCombinaison(Combinaison c1, Combinaison c2){

        int n1 = c1.getTypeCombinaison().getNumero();
        int n2 = c2.getTypeCombinaison().getNumero();
        int sv1 = c1.getSommeValeur();
        int sv2 = c2.getSommeValeur(); 

        if (n1 > n2) {
            return 1;
        } else if (n1 < n2){
            return -1;
        } else if (sv1 > sv2){
            return 1;
        } else if (sv1 < sv2){
            return -1;
        } else {
            return 0;
        }
    }

    public TypeCombinaison getTypeCombinaison () { return this.type; }
    public int getSommeValeur () { return this.somme_valeur; }

    public Combinaison(List<Carte> lc){
        setType(lc);
        setSommeValeur(lc);
    }
}
