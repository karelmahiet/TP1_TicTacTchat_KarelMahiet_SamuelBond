package com.atoudeft.tictactoe.classes;

import com.atoudeft.tictactoe.MethodeNonImplementeeException;

import java.util.List;
public final class Plateau {
    private final Symbole[][] grille = new Symbole[3][3];
    private int casesRemplies = 0;
    public Symbole get(int ligne, int colonne) { return grille[ligne][colonne]; }
    public boolean estVide(Position p) { return grille[p.getLigne()][p.getColonne()] == null; }
    public int getNombreCasesRemplies() { return casesRemplies; }
    public boolean estPlein() { return casesRemplies == 9; }

    //4.3 - check si case est pleine et si non, ajoute le symbole du joueur
    public boolean placer(Coup coup) {
        Position p = coup.getPosition();
        if (!estVide(p)){
            return false;
        }

        grille[p.getLigne()][p.getColonne()] = coup.getSymbole();
        casesRemplies++;

        return true;
    }

    //4.4
    public List<Position> ligneGagnante() {
        int[][][] lignes = {
            {{0,0},{0,1},{0,2}}, {{1,0},{1,1},{1,2}}, {{2,0},{2,1},{2,2}},
            {{0,0},{1,0},{2,0}}, {{0,1},{1,1},{2,1}}, {{0,2},{1,2},{2,2}},
            {{0,0},{1,1},{2,2}}, {{0,2},{1,1},{2,0}}
        };
        //combinaisons gagnantes
        for (int[][] ligne : lignes){
            Symbole s1 = grille[ligne[0][0]][ligne[0][1]];
            Symbole s2 = grille[ligne[1][0]][ligne[1][1]];
            Symbole s3 = grille[ligne[2][0]][ligne[2][1]];

            //si 3 en ligne, gagne
            if (s1 != null && s1 == s2 && s2 == s3){
                return List.of(
                        new Position(ligne[0][0], ligne[0][1]),
                        new Position(ligne[1][0], ligne[2][1]),
                        new Position(ligne[2][0], ligne[2][1])
                );
            }
        }

        //si rien
        return List.of();
    }
}