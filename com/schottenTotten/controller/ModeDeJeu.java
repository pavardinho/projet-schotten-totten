package com.schottenTotten.controller;

import com.schottenTotten.model.*;

public interface ModeDeJeu {

    public int getNbBornes();
    public int getCartesMaxParBornes();
    public void setNbBornes(int nb);
    public void setCartesMaxParBornes(int nb);

    public void initialiserJeu(Jeu j);
    public boolean peutReclamerBorne(Joueur j, Borne b, Plateau p);
    public boolean peutJouerCarteTactique(CarteTactique c, Joueur j, Plateau p);

    public String getName();
}
