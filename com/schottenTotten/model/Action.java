package com.schottenTotten.model;

public class Action {
    
    // Permet de faire le lien entre model et ai

    private Carte carte;
    private Borne borne;

    public Action(Carte carte, Borne borne) {
        this.carte = carte;
        this.borne = borne;
    }

    public Carte getCarte() {
        return carte;
    }

    public Borne getBorne() {
        return borne;
    }

}
