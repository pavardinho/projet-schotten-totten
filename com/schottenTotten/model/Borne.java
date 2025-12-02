package com.schottenTotten.model;

import java.util.ArrayList;
import java.util.List;
/// import com.schottenTotten.model.Joueur;


public class Borne {
    public enum Statut { NON_RECLAMEE, RECLAMEE_J1, RECLAMEE_J2 };

    private int numero;
    private List<Carte> cartesJoueur1;
    private List<Carte> cartesJoueur2;
    private Statut statut;

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

    public boolean estPleine(Joueur j){
        if ( this.cartesJoueur1.size() == 3 || this.cartesJoueur2.size() == 3 ){ /// ATTENTION CETTE CONDITION EST A MODIFIER UNE FOIS QUE J'AURAI CRÉÉ JOUEUR
            return true;                                                            /// ELLE DEVRA IDENTIFIER LE JOUEUR QUI JOUE
        }
        return false;
    }

    public boolean estControlee(){
        if (this.statut != Statut.NON_RECLAMEE){
            return true;
        }
        return false;
    }
}
