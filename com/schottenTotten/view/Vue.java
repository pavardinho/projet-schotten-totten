package com.schottenTotten.view;

import java.util.List;
import com.schottenTotten.model.*;
import com.schottenTotten.controller.*;

public interface Vue {
    public void afficherMenuPrincipal();
    
    public void afficherPlateau(Plateau p, ModeDeJeu mode);
    public void afficherMain(String joueurName, List<Carte> main);

    public void afficherParamètres(Jeu j);

}
