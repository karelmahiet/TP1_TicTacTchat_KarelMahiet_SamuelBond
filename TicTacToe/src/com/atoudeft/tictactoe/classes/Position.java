package com.atoudeft.tictactoe.classes;

public final class Position {
    private final int ligne;
    private final int colonne;
    public Position(int ligne, int colonne) {
        if (ligne < 0 || ligne > 2 || colonne < 0 || colonne > 2) {
            throw new IllegalArgumentException("Position hors plateau: (" + ligne + "," + colonne + ")");
        }
        this.ligne = ligne;
        this.colonne = colonne;
    }
    public int getLigne()   { return ligne; }
    public int getColonne() { return colonne; }

    @Override public String toString() { return "(" + ligne + "," + colonne + ")"; }

    //Q4 - 4.2: equals
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position p = (Position) o;
        return this.ligne == p.ligne && this.colonne == p.colonne;

    }
    //hashFunction pour gerer collections
    public int hashCode(){
       return 31 * ligne + colonne;
    }
}