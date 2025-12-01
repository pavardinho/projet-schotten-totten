package com.schottenTotten.ai;

import com.schottenTotten.model.Joueur;
import com.schottenTotten.model.Action;
import com.schottenTotten.controller.Jeu;

public interface Strategie {

    Action choisirAction(Jeu jeu, Joueur joueur);
    
} 