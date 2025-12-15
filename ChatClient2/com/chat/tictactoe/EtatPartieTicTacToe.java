package com.chat.tictactoe;

import observer.Observable;
import observer.Observateur;

public class EtatPartieTicTacToe extends Observable {
    private char[][] etatPlateau = new char[3][3];

    public EtatPartieTicTacToe() {
        etatPlateau = new char[][]{
                {'.','.','.'},
                {'.','.','.'},
                {'.','.','.'}
        };
    }
    public boolean coup(String strCoup) {
        boolean res = false;

        if (strCoup == null) return false;

        //q4.2
        String[] parties = strCoup.split(" ");
        if (parties.length != 3) return false;

        char symbole = parties[0].charAt(0);
        int ligne;
        int colonne;

        try {
            ligne = Integer.parseInt(parties[1]);
            colonne = Integer.parseInt(parties[2]);
        } catch (NumberFormatException e) {
            return false;
        }

        if (symbole != 'X' && symbole != 'O') return false;
        if (ligne < 0 || ligne > 2 || colonne < 0 || colonne > 2) return false;
        if (etatPlateau[ligne][colonne] != '.') return false;

        etatPlateau[ligne][colonne] = symbole;

        notifierObservateurs();


        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char c = etatPlateau[i][j];
                sb.append(c).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public char[][] getEtatPlateau() {
        return etatPlateau;
    }

    public void setEtatPlateau(char[][] etatPlateau) {
        this.etatPlateau = etatPlateau;
    }
}
