package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;


public class Borne {
    public enum Statut { NON_RECLAMEE, RECLAMEE_J1, RECLAMEE_J2 };

    private int numero;
    private List<Carte> cartesJoueur1;
    private List<Carte> cartesJoueur2;
    private Statut statut;

    private Joueur joueur1;
    private Joueur joueur2;

    public int getNumero() { return this.numero; }
    public List<Carte> getCarteJoueur1() { return this.cartesJoueur1; }
    public List<Carte> getCarteJoueur2() { return this.cartesJoueur2; }
    public Statut getStatut() { return this.statut; }


    public Borne(int numero){
        this.numero = numero;
        this.cartesJoueur1 = new ArrayList<Carte>();
        this.cartesJoueur2 = new ArrayList<Carte>();
        this.statut = Statut.NON_RECLAMEE;
    }

    public void setJoueurs(Joueur j1, Joueur j2){
        this.joueur1 = j1;
        this.joueur2 = j2;
    }

    public void addCarte(Joueur j, Carte c){
        if (j == joueur1) {
            cartesJoueur1.add(c);
        } else {
            cartesJoueur2.add(c);
        }
    }

    public boolean estPleine(Joueur j){
        if ( j == this.joueur1 ){
            if (this.cartesJoueur1.size() >= 3){
                return true;
            }
        }
        else if ( j == this.joueur2 ){
            if (this.cartesJoueur2.size() >= 3){
                return true;
            }
        }
        return false;
    }

    public boolean estControlee(){
        if (this.statut != Statut.NON_RECLAMEE){
            return true;
        }
        return false;
    }

    // Permet à l'ia de récupérer les cartes jqu'elle a jouées sur une borne
    public List<Carte> getCartesJoueur(Joueur j){
        if ( j == this.joueur1 ){
            return this.cartesJoueur1;
        }
        else if ( j == this.joueur2 ){
            return this.cartesJoueur2;
        }
        return new ArrayList<>();
    }

    public List<Carte> getCartesAutreJoueur(Joueur j){
        if ( j == this.joueur1 ){
            return this.cartesJoueur2;
        }
        else if ( j == this.joueur2 ){
            return this.cartesJoueur1;
        }
        return new ArrayList<>();
    }

    public void reclamerBorne(Joueur j){
        if ( j == this.joueur1 ){
            this.statut = Statut.RECLAMEE_J1;
        }
        else if ( j == this.joueur2 ){
            this.statut = Statut.RECLAMEE_J2;
        }
    }
}
