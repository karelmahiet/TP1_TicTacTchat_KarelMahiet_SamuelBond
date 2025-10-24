package com.chat.serveur;

import java.util.Objects;

/**
 * Cette classe représente un Salon privé // Question 3
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SalonPrive that = (SalonPrive) o;
        return Objects.equals(host, that.host) && Objects.equals(guest, that.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, guest);
    }
}
