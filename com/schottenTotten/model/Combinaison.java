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
        
        if (isSuite(lc) && isMemeCouleur(lc)) {
            this.type = TypeCombinaison.SUITE_COULEUR;
        } 
        else if (isMemeCouleur(lc)) {
            this.type = TypeCombinaison.COULEUR;
        }
        else if (isMemeValeur(lc)) {
            this.type = TypeCombinaison.BRELAN;
        }
        else if (isSuite(lc)) {
            this.type = TypeCombinaison.SUITE;
        }
        else {
            this.type = TypeCombinaison.SOMME;
        }
    }
    
    private boolean isSuite(List<Carte> lc) {
        for (int i = 0; i <= lc.size() - 3; i++) {
            boolean estSuite = true;
            for (int j = i + 1; j < i + 3; j++) {
                int valActuelle = lc.get(j).getValeur().getNumero();
                int valPrecedente = lc.get(j - 1).getValeur().getNumero();
                if (valActuelle != valPrecedente + 1) {
                    estSuite = false;
                    break;
                }
            }
            if (estSuite) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isMemeCouleur(List<Carte> lc) {
        // Compter les occurrences de chaque couleur
        java.util.Map<CarteNormale.Couleur, Integer> compteurs = new java.util.HashMap<>();
        for (Carte c : lc) {
            compteurs.put(c.getCouleur(), compteurs.getOrDefault(c.getCouleur(), 0) + 1);
        }
        for (int count : compteurs.values()) {
            if (count >= 3) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isMemeValeur(List<Carte> lc) {
        // Compter les occurrences de chaque valeur
        java.util.Map<CarteNormale.Valeur, Integer> compteurs = new java.util.HashMap<>();
        for (Carte c : lc) {
            compteurs.put(c.getValeur(), compteurs.getOrDefault(c.getValeur(), 0) + 1);
        }
        for (int count : compteurs.values()) {
            if (count >= 3) {
                return true;
            }
        }
        return false;
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
