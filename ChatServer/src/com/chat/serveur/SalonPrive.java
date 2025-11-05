package com.chat.serveur;

import java.util.Objects;

/**
 * Cette classe représente un Salon privé //Question 3
 *
 * @author Samuel Bond
 * @version 1.0
 * @since 2025-10-24
 */

public class SalonPrive {

    private String host;
    private String guest;

    public SalonPrive(String host, String guest) {
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
        SalonPrive that = (SalonPrive) o;
        //Sam jai ajouté le || pcq sinon ca comparait pas si c'était dans le désordre,
        //je voulais que SalonPrive(A,B) = SalonPrive(B,A)
        return (Objects.equals(host, that.host) && Objects.equals(guest, that.guest))
                || (Objects.equals(host, that.guest) && Objects.equals(guest, that.host)); //ici
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, guest);
    }
}
