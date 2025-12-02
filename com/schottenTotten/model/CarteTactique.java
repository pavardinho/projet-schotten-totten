package com.schottenTotten.model;

public class CarteTactique extends Carte{

    public enum TypeTactique{
        JOKER("Carte Normale dont vous choisissez la couleur et la valeur au moment de revendiquer la Borne où elle se trouve. Chaque joueur ne peut avoir qu’un seul Joker de son côté de la frontière. Si vous avez déjà posé un Joker et que vous piochez le second, vous devrez le garder en main jusqu’à la fin de la partie.(se joue comme une carte normale)"),
        ESPION("Carte Normale de valeur 7 dont vous choisissez la couleur au moment de revendiquer la Borne où elle se trouve.(se joue comme une carte normale)"),
        PORTEBOUCLIER("Carte Normale de valeur 1, 2 ou 3 dont vous choisissez la couleur et la valeur au moment de revendiquer la Borne où elle se trouve.(se joue comme une carte normale)"),
        COLINMAILLARD("Pour revendiquer la Borne où se situe Colin-Maillard, additionnez uniquement la valeur des cartes jouées sur celle-ci, sans tenir compte des éventuelles combinaisons.(se joue sur une borne)"),
        COMBATDEBOUE("Pour revendiquer la Borne où se situe le Combat de Boue, vous devez désormais poser quatre cartes de chaque côté decelle-ci.(se joue sur une borne)"),
        CHASSEURDETETE("Piochez trois cartes d’une seule ou des deux pioches. Choisissez deux cartes parmi toutes celles de votre main et placez-les en dessous de la pioche correspondante.(se joue face visible à côté de la pioche)"),
        STRATEGE("Choisissez une carte Normale ou Tactique de votre côté de la frontière sur une Borne non revendiquée. Placez-la face visible sur une autre Borne non revendiquée ou défaussez-la face visible à côté de la pioche.(se joue face visible à côté de la pioche)"),
        BANSHEE("Choisissez une carte Normale ou Tactique du côté adverse de la frontière sur une Borne non revendiquée et défaussez-la face visible à côté de la pioche.(se joue face visible à côté de la pioche)"),
        TRAITRE("Choisissez une carte Normale du côté adverse de la frontière devant une Borne non revendiquée et placez-la devant une Borne non revendiquée de votre côté.(se joue face visible à côté de la pioche)");

        private final String description;

        TypeTactique(String description){
            this.description = description;
        }

        public String getDescription(){ return this.description; }
    }

    private TypeTactique type;


    public CarteTactique(TypeTactique type){
        super(type.name());
        this.type = type;
    }

    public TypeTactique getType(){ return this.type;}

    /// Ici viendront les méthodes spécifiques aux cartes tactiques

    @Override
    public String toString() {
        return "Tactique: " + type.name();
    }
}
