package com.chat.serveur;

import com.commun.net.Connexion;

import java.util.Objects;

/**
 * Cette classe représente une invitation pour chatter en privé // Question 3
 *
 * @author Samuel Bond
 * @version 1.0
 * @since 2025-10-24
 */

public class Invitation {

    private String host;
    private String guest;

    public Invitation(String host, String guest) {
        this.host = host;
        this.guest = guest;
    }

    public String getHost() {
        return host;
    }

    public String getGuest() {
        return guest;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Invitation that = (Invitation) o;
        return Objects.equals(host, that.host) && Objects.equals(guest, that.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, guest);
    }

    // J'ai crée une méthode pour facilité la lisibilité du code
    public static void envoyerInvitation(ServeurChat serveur, String host, String guest)  {
        for (Connexion c : serveur.connectes) {
            if (c.getAlias().equals(guest)) {
                c.envoyer(host +" vous a envoye une invitation à un chat prive (JOIN/DECLINE alias " +
                        "pour accepter ou refuser)");
                break;
            }
        }
    }
}
