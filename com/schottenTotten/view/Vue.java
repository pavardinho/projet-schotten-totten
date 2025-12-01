package com.schottenTotten.view;

import java.util.List;
import com.schottenTotten.model.*;
import com.schottenTotten.controller.*;

public interface Vue {
    public void afficherMenuPrincipal();
    
    public void afficherPlateau(Plateau p);

    public void afficherParamètres(Jeu j);

    public void afficherMain(List<Carte> main);
}
