package com.schottenTotten.model;

import java.util.List;

public class Plateau {
    private List<Borne> bornes;
    private List<Carte> pioche;

    public List<Borne> getBornes() { return this.bornes; }
    public List<Carte> getPioche() { return this.pioche; }

    Plateau(List<Borne> bornes, List<Carte> pioche){
        
    }
}
