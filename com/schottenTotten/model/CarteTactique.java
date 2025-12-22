package com.schottenTotten.model;

public class CarteTactique extends Carte{

    public enum TypeTactique{
        JOKER("Carte Normale dont vous choisissez la couleur et la valeur au moment de revendiquer la Borne où elle se trouve. Chaque joueur ne peut avoir qu’un seul Joker de son côté de la frontière. Si vous avez déjà posé un Joker et que vous piochez le second, vous devrez le garder en main jusqu’à la fin de la partie.(SE JOUE COMME UNE CARTE NORMALE)"),
        ESPION("Carte Normale de valeur 7 dont vous choisissez la couleur au moment de revendiquer la Borne où elle se trouve.(SE JOUE COMME UNE CARTE NORMALE)"),
        PORTEBOUCLIER("Carte Normale de valeur 1, 2 ou 3 dont vous choisissez la couleur et la valeur au moment de revendiquer la Borne où elle se trouve.(SE JOUE COMME UNE CARTE NORMALE)"),
        COLINMAILLARD("Pour revendiquer la Borne où se situe Colin-Maillard, additionnez uniquement la valeur des cartes jouées sur celle-ci, sans tenir compte des éventuelles combinaisons.(SE JOUE SUR UNE BORNE)"),
        COMBATDEBOUE("Pour revendiquer la Borne où se situe le Combat de Boue, vous devez désormais poser quatre cartes de chaque côté decelle-ci.(SE JOUE SUR UNE BORNE)"),
        CHASSEURDETETE("Piochez trois cartes d’une seule ou des deux pioches. Choisissez deux cartes parmi toutes celles de votre main et placez-les en dessous de la pioche correspondante.(SE JOUE FACE VISIBLE A COTE DE LA PIOCHE)"),
        STRATEGE("Choisissez une carte Normale ou Tactique de votre côté de la frontière sur une Borne non revendiquée. Placez-la face visible sur une autre Borne non revendiquée ou défaussez-la face visible à côté de la pioche.(SE JOUE FACE VISIBLE A COTE DE LA PIOCHE)"),
        BANSHEE("Choisissez une carte Normale ou Tactique du côté adverse de la frontière sur une Borne non revendiquée et défaussez-la face visible à côté de la pioche.(SE JOUE FACE VISIBLE A COTE DE LA PIOCHE)"),
        TRAITRE("Choisissez une carte Normale du côté adverse de la frontière devant une Borne non revendiquée et placez-la devant une Borne non revendiquée de votre côté.(SE JOUE FACE VISIBLE A COTE DE LA PIOCHE)");

        private final String description;

        TypeTactique(String description){
            this.description = description;
        }

        public String getDescription(){ return this.description; }
    }

    private TypeTactique type;

    public TypeTactique getTypeTactique(){ return this.type; }

    public CarteTactique(TypeTactique type){
        super(type.name());
        this.type = type;
    }

    @Override
    public String toString() {
        return "Tactique: " + type.name();
    }
}
