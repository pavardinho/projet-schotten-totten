package com.schottenTotten.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.schottenTotten.model.*;
import com.schottenTotten.model.Borne.Statut;
import com.schottenTotten.model.CarteTactique.TypeTactique;
import com.schottenTotten.ai.Strategie;

public class ModeTactique implements ModeDeJeu{

    private int nbBornes = 9;
    private int cartesMaxParBornes = 3;
    private int nbCarteMain = 7;
    
    public int getNbBornes() { return this.nbBornes; }
    public int getCartesMaxParBornes() { return this.cartesMaxParBornes; }
    public int getNbCarteMain() { return this.nbCarteMain; }

    public void setNbBornes(int nb) { this.nbBornes = nb; }
    public void setCartesMaxParBornes(int nb) { this.cartesMaxParBornes = nb; }
    public void setNbCarteMain(int nb) {this.nbCarteMain = nb; }

    public void initialiserJeu(Jeu j){
        j.setIndexJoueurCourant(0);
        
        Plateau p = new Plateau(nbBornes);
        j.setPlateau(p);
        for (int i = 0; i < j.getNbJoueurs(); i++) {
            Joueur joueur = j.getJoueur(i);
            joueur.resetMain();
            joueur.resetNbBornesControllees();
            joueur.resetNbCartesTactiquesJouees();
            for (int k = 0; k < nbCarteMain; k++) {
                joueur.recevoirCarte(p.piocher());
            }
        }
        for (Borne b : j.getPlateau().getBornes()) {
            b.setJoueurs(j.getJoueur(0), j.getJoueur(1));
        }

        // Initialisation de la pioche tactique
        List<Carte> piocheTactique = new ArrayList<Carte>();
        for (CarteTactique.TypeTactique cTactique : CarteTactique.TypeTactique.values()) {
            piocheTactique.add(new CarteTactique(cTactique));
            if (cTactique == CarteTactique.TypeTactique.JOKER) {
                piocheTactique.add(new CarteTactique(cTactique));                
            }
        }
        Collections.shuffle(piocheTactique);
        p.setPiocheTactique(piocheTactique);
    }

    public boolean estTroupeElite(Carte c) {
        if (!(c instanceof CarteTactique)) return false;
        CarteTactique.TypeTactique t = ((CarteTactique) c).getTypeTactique();
        return t == TypeTactique.JOKER || t == TypeTactique.ESPION || t == TypeTactique.PORTEBOUCLIER;
    }

    public boolean contientTroupeElite(List<Carte> cartes) {
        for (Carte c : cartes) {
            if (estTroupeElite(c)) return true;
        }
        return false;
    }

    public int nbCartesRequisesPourReclamer(Borne b) {
        CarteTactique ct = b.getCarteTactique();
        if (ct != null && ct.getTypeTactique() == TypeTactique.COMBATDEBOUE) return 4;
        return 3;
    }

    public CarteNormale.Couleur demanderCouleur(Scanner sc) {
        CarteNormale.Couleur[] couleurs = CarteNormale.Couleur.values();
        System.out.println("Choisis une couleur :");
        for (int i = 0; i < couleurs.length; i++) {
            System.out.println((i+1) + " - " + couleurs[i]);
        }
        int choix = sc.nextInt();
        while (choix < 1 || choix > couleurs.length) {
            System.out.println("Choix invalide, recommence : ");
            choix = sc.nextInt();
        }
        return couleurs[choix - 1];
    }

    public List<Carte> normaliserTroupesElite(Scanner sc, List<Carte> cartes, String nomJoueur) {
        List<Carte> out = new ArrayList<>();

        for (Carte c : cartes) {
            if (!(c instanceof CarteTactique)) {
                out.add(c);
                continue;
            }

            TypeTactique t = ((CarteTactique) c).getTypeTactique();
            if (t == TypeTactique.JOKER) {
                System.out.println("Choix pour JOKER : à "+ nomJoueur +" de choisir la couleur et la valeur.");
                CarteNormale.Couleur couleur = demanderCouleur(sc);

                System.out.println("Choisis une valeur (1 à 9) : ");
                int v = sc.nextInt();
                while (v < 1 || v > 9) {
                    System.out.println("Valeur invalide (1..9) : ");
                    v = sc.nextInt();
                }
                out.add(new CarteNormale(CarteNormale.Valeur.values()[v - 1], couleur));
            }
            else if (t == TypeTactique.ESPION) {
                System.out.println("Choix pour ESPION (valeur = 7) : à "+ nomJoueur +" de choisir la couleur.");
                CarteNormale.Couleur couleur = demanderCouleur(sc);
                out.add(new CarteNormale(CarteNormale.Valeur.values()[6], couleur));
            }
            else if (t == TypeTactique.PORTEBOUCLIER) {
                System.out.println("Choix pour PORTE-BOUCLIER : à "+ nomJoueur +" de choisir la couleur et la valeur.");
                CarteNormale.Couleur couleur = demanderCouleur(sc);

                System.out.println("Choisis une valeur (1, 2 ou 3) : ");
                int v = sc.nextInt();
                while (v < 1 || v > 3) {
                    System.out.println("Valeur invalide (1..3) : ");
                    v = sc.nextInt();
                }
                out.add(new CarteNormale(CarteNormale.Valeur.values()[v - 1], couleur));
            } else {
                // On garde la carte telle quelle si ce n'est pas une troupe d'élite
                out.add(c);
            }
        }

        return out;
    }

    public boolean peutReclamerBorne(List<Carte> cj_reclame, List<Carte> cj_autre, Borne b){
        if (b.getStatut() != Borne.Statut.NON_RECLAMEE) return false;

        int requis = nbCartesRequisesPourReclamer(b);

        boolean elitePresente = contientTroupeElite(cj_reclame) || contientTroupeElite(cj_autre);
        if (elitePresente) {
            return cj_reclame.size() == requis && cj_autre.size() == requis;
        }

        Combinaison combj_reclame = new Combinaison(cj_reclame);
        Combinaison combj_autre = new Combinaison(cj_autre);

        CarteTactique cTactique = b.getCarteTactique();
        if (cTactique != null) {
            if (cTactique.getTypeTactique() == TypeTactique.COLINMAILLARD) {
                if (b.getStatut() != Borne.Statut.NON_RECLAMEE || cj_reclame.size() != 3 || cj_autre.size() != 3) {
                    return false;
                }

                return combj_reclame.getSommeValeur() > combj_autre.getSommeValeur();
            }
            if (cTactique.getTypeTactique() == TypeTactique.COMBATDEBOUE) {
                if (b.getStatut() != Borne.Statut.NON_RECLAMEE || cj_reclame.size() != 4 || cj_autre.size() != 4) {
                    return false;
                }

                Combinaison combj_reclame_modifiee = new Combinaison(cj_reclame.subList(0, 3));
                Combinaison combj_autre_modifiee = new Combinaison(cj_autre.subList(0, 3));

                int compare = Combinaison.compareCombinaison(combj_reclame_modifiee, combj_autre_modifiee);
                
                if (compare > 0) {
                    return true;
                } else if (compare < 0){
                    return false;
                } else {
                    // gerer le cas ou le type et la somme des valeurs et la même -> regardé qui a posé la 3eme carte en premier
                    return true;
                }
            }
            return false;
        } else {
            if (b.getStatut() != Borne.Statut.NON_RECLAMEE || cj_reclame.size() != 3 || cj_autre.size() != 3) {
                return false;
            }

            int compare = Combinaison.compareCombinaison(combj_reclame, combj_autre);
            
            if (compare > 0) {
                return true;
            } else if (compare < 0){
                return false;
            } else {
                // gerer le cas ou le type et la somme des valeurs et la même -> regardé qui a posé la 3eme carte en premier
                return true;
            }
        }
    }

    public boolean peutJouerCarteTactique(CarteTactique c, Joueur j, Joueur autreJoueur, Plateau p){
        int nbCartesJoueeJoueur = j.getNbCartesTactiquesJouees();
        int nbCartesJoueeAutreJoueur = autreJoueur.getNbCartesTactiquesJouees();
        
        // On peut jouer une carte tactique si on a joué autant de cartes que l'adversaire ou une de moins que l'adversaire
        return nbCartesJoueeJoueur <= nbCartesJoueeAutreJoueur;
    }

    public void joueChasseurDeTete(Jeu jeu, Scanner sc, Joueur j){
        System.out.println("Combien de carte de la pioche Clan voulez-vous tirez ? (Le reste sera des cartes Tactiques) (1-3)");
        int e = sc.nextInt();
        while (!(0 < e && e <= 3)) {
            System.out.println("Vous devez choisir un numéro entre 1 et 3 compris");
            e = sc.nextInt();
        }
        List<Carte> carteTire = new ArrayList<Carte>();
        for (int i = 0; i < e; i++) {
            carteTire.add(jeu.getPlateau().piocher());
        }
        for (int i = 0; i < 3 - e; i++) {
            carteTire.add(jeu.getPlateau().piocherTactique());      
        }
        List<Carte> main = j.getMain();
        for (Carte carte : carteTire) {
            j.recevoirCarte(carte);
        }
        jeu.getVue().afficherMain(j.getNom(), main);
        System.out.println("Quelle carte voulez vous remettre dans la pioche ? (1-"+main.size()+")");
        while (true) {
            e = sc.nextInt();
            if (!(0 < e && e <= main.size())) {
                System.out.println("Vous devez choisir un numéro entre 1 et "+main.size()+" compris");
                continue;
            }

            if (main.get(e-1) instanceof CarteTactique && (((CarteTactique) main.get(e-1)).getTypeTactique() == TypeTactique.CHASSEURDETETE)) {
                System.out.println("Vous ne pouvez pas choisir la carte ChasseurDeTete, choisissez une autre carte :");
                continue;
            }
            break;
        }
        Carte c = main.get(e-1);
        if (c instanceof CarteTactique) {
            jeu.getPlateau().addPiocheTactique(c);            
        } else {
            jeu.getPlateau().addPioche(c);
        }
        j.retirerCarte(c);

        jeu.getVue().afficherMain(j.getNom(), main);
        System.out.println("Il faut remettre une deuxième carte dans la pioche. (1-"+main.size()+")");
        while (true) {
            e = sc.nextInt();
            if (!(0 < e && e <= main.size())) {
                System.out.println("Vous devez choisir un numéro entre 1 et "+main.size()+" compris");
                continue;
            }

            if (main.get(e-1) instanceof CarteTactique && (((CarteTactique) main.get(e-1)).getTypeTactique() == TypeTactique.CHASSEURDETETE)) {
                System.out.println("Vous ne pouvez pas choisir la carte ChasseurDeTete, choisissez une autre carte :");
                continue;
            }
            break;
        }
        c = main.get(e-1);
        if (c instanceof CarteTactique) {
            jeu.getPlateau().addPiocheTactique(c);            
        } else {
            jeu.getPlateau().addPioche(c);
        }
        j.retirerCarte(c);
    }

    public void joueStratege(Jeu jeu, Scanner sc, Joueur j){
        System.out.println("Choisissez la borne dont vous voulez déplacer la carte. (1-9)");
        int b;
        while (true) {
            b = sc.nextInt();
            if (!(0 < b && b <= 9)) {
                System.out.println("Entrez un numéro valide de borne (1-9) : ");
                continue;
            }
            if (jeu.getPlateau().getBornes().get(b-1).getStatut() != Statut.NON_RECLAMEE || jeu.getPlateau().getBornes().get(b-1).getCartesJoueur(j).size() <= 0) {
                System.out.println("La borne choisit doit contenir des cartes et ne dois pas avoir été revendiqué. (1-9)");
                continue;
            }
            break;
        }
        Borne borne = jeu.getPlateau().getBornes().get(b-1);
        int nbCarteBorne = borne.getCartesJoueur(j).size();
        System.out.println("Choisissez la carte que vous voulez déplacer. (1-"+nbCarteBorne+")");
        int c = sc.nextInt();
        while (!(0 < c && c <= nbCarteBorne)) {
            System.out.println("Entrez un nombre dans cette intervalle : 1-"+nbCarteBorne+")");
            c = sc.nextInt();
        }
        Carte carte = borne.getCartesJoueur(j).remove(c-1);
        System.out.println("Choisissez la borne où vous voulez placer la carte séléctionner. (1-9)");
        int b2;
        while (true) {
            b2 = sc.nextInt();
            if (!(0 < b2 && b2 <= 9)) {
                System.out.println("Entrez un numéro valide de borne (1-9) : ");
                continue;
            }
            if (b == b2) {
                System.out.println("Vous ne pouvez pas choisir la borne dont vient la carte. (1-9)");
                continue;
            }
            if (jeu.getPlateau().getBornes().get(b2-1).estPleine(j)) {
                System.out.println("Vous ne pouvez pas choisir une borne pleine. (1-9)");
                continue;
            }
            break;
        }
        jeu.getPlateau().getBornes().get(b2-1).addCarte(j, carte);
    }

    public void joueBanshee(Jeu jeu, Scanner sc, Joueur j){
        System.out.println("Choisissez la borne dont vous voulez défaussez une carte adverse. (1-9)");
        int b;
        while (true) {
            b = sc.nextInt();
            if (!(0 < b && b <= 9)) {
                System.out.println("Entrez un numéro valide de borne (1-9) : ");
                continue;
            }
            if (jeu.getPlateau().getBornes().get(b-1).getStatut() != Statut.NON_RECLAMEE || jeu.getPlateau().getBornes().get(b-1).getCartesAutreJoueur(j).size() <= 0) {
                System.out.println("La borne choisit doit contenir des cartes et ne dois pas avoir été revendiqué. (1-9)");
                continue;
            }
            break;
        }
        Borne borne = jeu.getPlateau().getBornes().get(b-1);
        int nbCarteBorne = borne.getCartesAutreJoueur(j).size();
        System.out.println("Choisissez la carte que vous voulez défaussez. (1-"+nbCarteBorne+")");
        int c = sc.nextInt();
        while (!(0 < c && c <= nbCarteBorne)) {
            System.out.println("Entrez un nombre dans cette intervalle : 1-"+nbCarteBorne);
            c = sc.nextInt();
        }
        Carte carte = borne.getCartesAutreJoueur(j).remove(c-1);
        jeu.getPlateau().addDefausse(carte);
    }

    public void joueTraitre(Jeu jeu, Scanner sc, Joueur j){
        System.out.println("Choisissez la borne dont vous voulez déplacer une carte adverse. (1-9)");
        int b;
        while (true) {
            b = sc.nextInt();
            if (!(0 < b && b <= 9)) {
                System.out.println("Entrez un numéro valide de borne (1-9) : ");
                continue;
            }
            if (jeu.getPlateau().getBornes().get(b-1).getStatut() != Statut.NON_RECLAMEE || jeu.getPlateau().getBornes().get(b-1).getCartesAutreJoueur(j).size() <= 0) {
                System.out.println("La borne choisit doit contenir des cartes et ne dois pas avoir été revendiqué. (1-9)");
                continue;
            }
            break;
        }
        Borne borne = jeu.getPlateau().getBornes().get(b-1);
        int nbCarteBorne = borne.getCartesAutreJoueur(j).size();
        System.out.println("Choisissez la carte que vous voulez déplacer. (1-"+nbCarteBorne+")");
        int c = sc.nextInt();
        while (!(0 < c && c <= nbCarteBorne)) {
            System.out.println("Entrez un nombre dans cette intervalle : 1-"+nbCarteBorne+")");
            c = sc.nextInt();
        }
        Carte carte = borne.getCartesAutreJoueur(j).remove(c-1);
        System.out.println("Choisissez la borne où vous voulez placer la carte séléctionner. (1-9)");
        int b2;
        while (true) {
            b2 = sc.nextInt();
            if (!(0 < b2 && b2 <= 9)) {
                System.out.println("Entrez un numéro valide de borne (1-9) : ");
                continue;
            }
            if (jeu.getPlateau().getBornes().get(b2-1).estPleine(j)) {
                System.out.println("Vous ne pouvez pas choisir une borne pleine. (1-9)");
                continue;
            }
            break;
        }
        jeu.getPlateau().getBornes().get(b2-1).addCarte(j, carte);
    }

    public boolean joueurAJokerPose(Plateau p, Joueur j) {
    for (Borne b : p.getBornes()) {
        for (Carte c : b.getCartesJoueur(j)) {
            if (c instanceof CarteTactique && ((CarteTactique) c).getTypeTactique() == TypeTactique.JOKER) {
                return true;
            }
        }
    }
    return false;
}

    public void jouerTour(Jeu jeu, Scanner sc, Joueur j) {
        Plateau plateau = jeu.getPlateau();
        Joueur autreJoueur = jeu.getAutreJoueur(j);
        int e;
        Carte c;
        System.out.println("Entrez le numéro de la carte que vous voulez jouez (1-"+nbCarteMain+") : ");
        while (true) {
            e = sc.nextInt();
            if (!(0 < e && e <= nbCarteMain)) {
                System.out.println("Entrez le numéro de la carte que vous voulez jouez (1-"+nbCarteMain+") : ");
                continue;
            }
            c = j.getMain().get(e-1);
            if (c instanceof CarteTactique && !peutJouerCarteTactique((CarteTactique) c, j, autreJoueur, plateau)) {
                System.out.println("Vous ne pouvez pas jouer de carte tactique. Vous avez déjà joué plus de cartes tactiques que votre adversaire.");
                continue;
            }
            if (c instanceof CarteTactique && ((CarteTactique) c).getTypeTactique() == TypeTactique.JOKER
            && joueurAJokerPose(plateau, j)) {
                System.out.println("Vous avez déjà un Joker posé : le 2e doit rester en main jusqu'à la fin.");
                continue;
            }
            break;
        }

        if (c instanceof CarteTactique) {
            CarteTactique carteTactique = (CarteTactique) c;
            j.addNbCartesTactiquesJouees();
            TypeTactique type = carteTactique.getTypeTactique();
            if (type == TypeTactique.CHASSEURDETETE || type == TypeTactique.STRATEGE || type == TypeTactique.BANSHEE || type == TypeTactique.TRAITRE) {
                if (type == TypeTactique.CHASSEURDETETE) {
                    joueChasseurDeTete(jeu, sc, j);
                } else if (type == TypeTactique.STRATEGE) {
                    joueStratege(jeu, sc, j);
                } else if (type == TypeTactique.BANSHEE) {
                    joueBanshee(jeu, sc, j);
                } else if (type == TypeTactique.TRAITRE) {
                    joueTraitre(jeu, sc, j);
                }

                plateau.addDefausse(c);
                j.retirerCarte(c);
            } else {
                System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+nbBornes+") : ");
                e = sc.nextInt();
                while (!(0 < e && e <= nbBornes)) {
                    System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+nbBornes+") : ");
                    e = sc.nextInt();
                }

                if (type == TypeTactique.COLINMAILLARD || type == TypeTactique.COMBATDEBOUE) {
                    while (plateau.getBornes().get(e-1).getCarteTactique() != null) {
                        System.out.println("Il y a déjà une carte Tactique dessus, choisissez-en une autre (1-"+nbBornes+") : ");
                        e = sc.nextInt();
                    }

                    plateau.getBornes().get(e-1).setCarteTactique((CarteTactique) c);
                    j.retirerCarte(c);
                }
                else {
                    while (plateau.getBornes().get(e-1).estPleine(j) || !(0 < e && e <= nbBornes)) {
                        System.out.println("La borne choisit est déja remplie, choisissez-en une autre (1-"+nbBornes+") : ");
                        e = sc.nextInt();
                    }
            
                    Borne b = plateau.getBornes().get(e-1);
                    jeu.jouerCarte(j, c, b);
                }
            }
        } else {
            System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+nbBornes+") : ");
            e = sc.nextInt();
            while (!(0 < e && e <= nbBornes)) {
                System.out.println("Entrez le numéro de la borne où vous voulez placez la carte (1-"+nbBornes+") : ");
                e = sc.nextInt();
            }
    
            while (plateau.getBornes().get(e-1).estPleine(j) || !(0 < e && e <= nbBornes)) {
                System.out.println("La borne choisit est déja remplie, choisissez-en une autre (1-"+nbBornes+") : ");
                e = sc.nextInt();
            }
    
            Borne b = plateau.getBornes().get(e-1);
            jeu.jouerCarte(j, c, b);
        }


        // On regarde si on peut reclamer des bornes 
        List<Borne> bornes_reclamable = new ArrayList<Borne>();
        for (Borne borne : plateau.getBornes()) {
            if (peutReclamerBorne(borne.getCartesJoueur(j), borne.getCartesAutreJoueur(j), borne)) {
                bornes_reclamable.add(borne);
            }
        }

        if (bornes_reclamable.size() > 0) {
            System.out.println("Tu peux réclamer les bornes suivantes : ");
            for (Borne borne : bornes_reclamable) {
                System.out.println(borne.getNumero());
            }
            System.out.println("Veux-tu en réclamer une ? ('N' si tu ne veux pas/plus en réclamer sinon entre le numéro de la borne que tu veux réclamer)");
            String s = sc.next();
            while (!s.equals("N")) {

                int num = Integer.parseInt(s);
                boolean peutChoisir = false;
                for (Borne borne : bornes_reclamable) {
                    if (borne.getNumero() == num) {
                        peutChoisir = true;

                        Joueur autre = jeu.getAutreJoueur(j);

                        if (contientTroupeElite(borne.getCartesJoueur(j)) || contientTroupeElite(borne.getCartesAutreJoueur(j))) {
                            List<Carte> normJ = normaliserTroupesElite(sc, borne.getCartesJoueur(j), j.getNom());
                            List<Carte> normA = normaliserTroupesElite(sc, borne.getCartesAutreJoueur(j), autre.getNom());

                            if (!peutReclamerBorne(normJ, normA, borne)) {
                                System.out.println("Après normalisation des troupes d'élite, tu ne gagnes pas cette borne.");
                                continue;
                            }
                        }

                        borne.reclamerBorne(j);
                        j.addNbBornesControllees();
                        System.out.println("Borne revendiquée ! Veux tu en revendiquer d'autre ?");
                    }
                }
                if (!peutChoisir) {
                    System.out.println("Tu ne peux pas choisir cette Borne");
                }
                s = sc.next();
            }
        }

        // Pioche d'une carte normale ou tactique
        if (plateau.getNbCartesPioche() > 0 && plateau.getNbCartesPiocheTactique() > 0) {
            System.out.println("Tirez une carte normale (N) ou tactique (T) ?");
            String s = sc.next();
            while (!(s.equals("N") || s.equals("T"))) {
                System.out.println("Entrez 'N' pour une carte normale ou 'T' pour une carte tactique : ");
                s = sc.next();
            }
            if (s.equals("N")) {
                j.recevoirCarte(plateau.piocher());
            } else {
                j.recevoirCarte(plateau.piocherTactique());
            }
        }
        else if (plateau.getNbCartesPioche() > 0) {
            j.recevoirCarte(plateau.piocher());
        }
        else if (plateau.getNbCartesPiocheTactique() > 0) {
            j.recevoirCarte(plateau.piocherTactique());
        }
    }

    public void joueTourIA(Jeu jeu, Joueur j){
        Plateau plateau = jeu.getPlateau();
        Joueur autreJoueur = jeu.getAutreJoueur(j);
        
        Strategie s = j.getStrategie();
        Action a = s.choisirAction(jeu, j);
        if (a != null){
            Carte c = a.getCarte();
            
            if (c instanceof CarteTactique) {
                CarteTactique carteTactique = (CarteTactique) c;
                j.addNbCartesTactiquesJouees();
                TypeTactique type = carteTactique.getTypeTactique();
            }
            
            jeu.jouerCarte(j, c, a.getBorne());
        }
        
        // On regarde si on peut reclamer des bornes 
        List<Borne> bornes_reclamable = new ArrayList<Borne>();
        for (Borne borne : plateau.getBornes()) {
            if (peutReclamerBorne(borne.getCartesJoueur(j), borne.getCartesAutreJoueur(j), borne)) {
                bornes_reclamable.add(borne);
            }
        }
        
        for (Borne borne : bornes_reclamable) {
            borne.reclamerBorne(j);
            j.addNbBornesControllees();
        }
        
        // Pioche d'une carte normale ou tactique
        if (plateau.getNbCartesPioche() > 0 && plateau.getNbCartesPiocheTactique() > 0) {
            // L'IA choisit de manière aléatoire
            if (Math.random() < 0.5) {
                j.recevoirCarte(plateau.piocher());
            } else {
                j.recevoirCarte(plateau.piocherTactique());
            }
        }
        else if (plateau.getNbCartesPioche() > 0) {
            j.recevoirCarte(plateau.piocher());
        }
        else if (plateau.getNbCartesPiocheTactique() > 0) {
            j.recevoirCarte(plateau.piocherTactique());
        }
    }
    
    public String getName(){ return "Tactique"; }
    
}
