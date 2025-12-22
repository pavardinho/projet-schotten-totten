package com.schottenTotten.controller;

import java.util.List;
import java.util.Scanner;

import com.schottenTotten.model.*;

public interface ModeDeJeu {

    public int getNbBornes();
    public int getCartesMaxParBornes();
    public int getNbCarteMain();

    public void setNbBornes(int nb);
    public void setCartesMaxParBornes(int nb);
    public void setNbCarteMain(int nb);

    public void initialiserJeu(Jeu j);
    public boolean peutReclamerBorne(List<Carte> cj_reclame, List<Carte> cj_autre, Borne b);
    public boolean peutJouerCarteTactique(CarteTactique c, Joueur j, Joueur autreJoueur, Plateau p);
    public void jouerTour(Jeu jeu, Scanner sc, Joueur j);
    public void joueTourIA(Jeu jeu, Joueur j);

    public String getName();
}
