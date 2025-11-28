package com.schottenTotten.model;

import java.util.List;

enum Statut { NON_RECLAMEE, RECLAMEE_J1, RECLAMEE_J2 }

public class Borne {
    private int numero;
    private List<Carte> cartesJoueur1;
    private List<Carte> cartesJoueur2;
    private Statut statut;

    public int getNumero() { return this.numero; }
    public List<Carte> getCarteJ1() { return this.cartesJoueur1; }
    public List<Carte> getCarteJ2() { return this.cartesJoueur2; }
    public Statut geStatut() { return this.statut; }

    public Borne(int numero, List<Carte> cartesJ1, List<Carte> cartesJ2, Statut statut){
        this.numero = numero;
        this.cartesJoueur1 = cartesJ1;
        this.cartesJoueur2 = cartesJ2;
        this.statut = statut;
    }
}
