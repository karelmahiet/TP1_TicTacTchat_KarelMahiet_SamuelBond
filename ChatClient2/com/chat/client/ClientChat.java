package com.chat.client;

import com.chat.tictactoe.EtatPartieTicTacToe;

/**
 * Cette classe étend la classe Client pour lui ajouter des fonctionnalités
 * spécifiques au chat et au jeu d'échecs en réseau.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class ClientChat extends Client {
    //Cette classe est pour le moment vide et sera compléter dans le TP.
    private EtatPartieTicTacToe etatPartieTicTacToe;
    private String alias;

    public EtatPartieTicTacToe getEtatPartieEchecs() {
        return etatPartieTicTacToe;
    }
    public void nouvellePartie() {
            this.etatPartieTicTacToe = new EtatPartieTicTacToe();
    }
    public void setEtatPartieEchecs(EtatPartieTicTacToe etatPartieEchecs) {
        this.etatPartieTicTacToe = etatPartieTicTacToe;
    }
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
