package com.schottenTotten.model;

import java.util.List;
import com.schottenTotten.ai.*;

public class Joueur {

    private String nom;
    private List<Carte> main;
    private Strategie strategie;

    public String getNom () { return this.nom; }
    public List<Carte> getMain() { return this.main; }
    public Strategie getStrategie() { return this.strategie; }

    public boolean isAI() { return strategie != null; }

    public Joueur(String nom, List<Carte> main, Strategie strategie){
        this.nom = nom;
        this.main = main;
        this.strategie = strategie;
    }
}
