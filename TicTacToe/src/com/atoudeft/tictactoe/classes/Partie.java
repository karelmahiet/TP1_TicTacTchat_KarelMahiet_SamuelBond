package com.atoudeft.tictactoe.classes;

import com.atoudeft.tictactoe.MethodeNonImplementeeException;

import java.util.List;

public final class Partie {
    private final Plateau plateau = new Plateau();
    private Symbole joueurCourant;
    private StatutPartie statut;

    public Plateau getPlateau()       {
        return plateau;
    }
    public Symbole getJoueurCourant() {
       return joueurCourant;
    }
    public StatutPartie getStatut()   {
        return statut;
    }

    public Partie(Symbole joueurCourant) {
        this.joueurCourant = joueurCourant;
        statut = StatutPartie.EN_COURS;
    }
    public Partie() {
        this(Symbole.X);
    }

    public boolean jouer(Symbole symbole, Position position) {
        throw new MethodeNonImplementeeException("***** Vous n'avez pas encore implemente la methode : "
                +Thread.currentThread().getStackTrace()[1].getMethodName()
                +"() de la classe "+this.getClass().getName());
    }
    public boolean isPartieEnCours() {
        if (statut != StatutPartie.EN_COURS) {
            return false;
        }
        return true;
    }

    //4.5 -
    //check si gagnant ou nulle sinon rien
    private void mettreAJourStatutApresCoup() {
        List<Position> gagnante = plateau.ligneGagnante();

        if (!gagnante.isEmpty()) {
            //cas gagnant
            if (joueurCourant == Symbole.X){
                statut = StatutPartie.X_GAGNE;
            } else {
                statut = StatutPartie.O_GAGNE;
            }//cas nulle
        } else if (plateau.estPlein()) {
            statut = StatutPartie.NULLE;
        } else { //cas rien
            statut = StatutPartie.EN_COURS;
        }
    }

    @Override
    public String toString() {
        String str = "";
        str = plateau +"\n"
                +"Joueur Courant : " + joueurCourant +"\n"
                +"Etat : " + statut + "\n";
        return str;
    }
}