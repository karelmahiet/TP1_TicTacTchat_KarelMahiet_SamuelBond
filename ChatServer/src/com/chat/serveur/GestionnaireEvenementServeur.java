package com.chat.serveur;

import com.commun.evenement.Evenement;
import com.commun.evenement.EvenementUtil;
import com.commun.evenement.GestionnaireEvenement;
import com.commun.net.Connexion;

import java.util.Vector;

import static com.chat.serveur.Invitation.envoyerInvitation;

/**
 * Cette classe représente un gestionnaire d'événement d'un serveur. Lorsqu'un serveur reçoit un texte d'un client,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;
    Vector<Invitation> listeInvitation = new Vector<>(); //pour stocké les invitations
    Vector<SalonPrive> listeSalonPrive = new Vector<>(); //pour stocké les salons ouvert

    /**
     * Construit un gestionnaire d'événements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un client.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String msg, typeEvenement, aliasExpediteur, host, guest;
        SalonPrive salonTemp1, salonTemp2;
        ServeurChat serveur = (ServeurChat) this.serveur;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            System.out.println("SERVEUR-Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                case "EXIT": //Ferme la connexion avec le client qui a envoyé "EXIT":
                    cnx.envoyer("END");
                    serveur.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des alias des personnes connectées :
                    cnx.envoyer("LIST " + serveur.list());
                    break;

                //Ajoutez ici d’autres case pour gérer d’autres commandes.

                case "MSG":
                    aliasExpediteur = cnx.getAlias(); //celui qui a send le msg
                    msg = evenement.getArgument(); //msg en tant que tel
                    String messageComplet = aliasExpediteur + ">>" + msg; //format demande par vous (prof)

                    //send a tous sauf expediteur
                    serveur.envoyerATousSauf(messageComplet, aliasExpediteur);

                    //QUESTION 2
                    serveur.ajouterHistorique(messageComplet);
                    break;

                case "JOIN":
                    host = cnx.getAlias();
                    guest = evenement.getArgument();
                    boolean salonExiste = false;
                    boolean invitationExiste = false;
                    boolean invite = false;
                    int positionInv = 0;
                    SalonPrive salonPrive = new SalonPrive(host, guest);
                    SalonPrive salonTemp = new SalonPrive(guest, host);
                    Invitation invitation = new Invitation(host, guest);
                    Invitation invitee = new Invitation(guest, host);
                    if (host.equals(guest)) {
                        cnx.envoyer("Vous ne pouvez pas chatter avec vous-meme");
                        break;
                    }
                    if (listeInvitation.isEmpty()) {
                        if (listeSalonPrive.isEmpty()) {
                            listeInvitation.add(invitation);
                            envoyerInvitation(serveur, host, guest);
                            break;
                        }else {
                            for (SalonPrive salon : listeSalonPrive) {
                                if ((salon.equals(salonPrive) || salon.equals(salonTemp))){
                                    salonExiste = true;
                                    break;
                                }
                            }
                            if (salonExiste) {
                                cnx.envoyer("Vous etes deja dans un salon prive avec "+ guest);
                                break;
                            }else {
                                listeInvitation.add(invitation);
                                envoyerInvitation(serveur, host, guest);
                            }

                        }
                    }
                    else{
                        for (Invitation inv : listeInvitation) {
                            if (inv.equals(invitation)){
                                invitationExiste = true;
                                break;
                            } else if (inv.equals(invitee)) {
                                invite = true;
                                positionInv = listeInvitation.indexOf(inv);
                                break;
                            }
                        }

                        if (invitationExiste){
                            cnx.envoyer("Vous avez deja envoye une invitation a " + guest);
                            break;
                        } else if (invite) {
                            listeSalonPrive.add(salonPrive);
                            listeInvitation.remove(positionInv);
                            cnx.envoyer("Vous etes maintenant dans un salon prive avec " + guest);
                            for (Connexion c : serveur.connectes) {
                                if (c.getAlias().equals(guest)) {
                                    c.envoyer("Vous etes maintenant dans un salon prive avec " + host);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;

                case "DECLINE":
                    host = cnx.getAlias();
                    guest = evenement.getArgument();
                    for (Invitation inv : listeInvitation) {
                        if (inv.getHost().equals(guest) && inv.getGuest().equals(host)){
                            for (Connexion c : serveur.connectes) {
                                if (c.getAlias().equals(guest)) {
                                    c.envoyer(host +" a refuse/annule l'invitation a chatter en prive.");
                                    break;
                                }
                            }
                            listeInvitation.remove(inv);
                            break;
                        }
                        else{
                            cnx.envoyer("Vous n'avez pas reçu d'invitation de la part de " + guest);
                            break;
                        }
                    }
                    break;
                case "INV":
                    host = cnx.getAlias();
                    for (Invitation inv : listeInvitation) {
                        if (inv.getGuest().equals(host)){
                            cnx.envoyer(inv.getHost());
                        }
                    }
                    break;

                case "PRV":
                    //utilisation d'une fonction déjà présente pour extraire le message
                    String[] test = EvenementUtil.extraireInfosEvenement(evenement.getArgument());
                    host = cnx.getAlias();
                    guest = test[0];
                    msg = test[1];
                    salonTemp1 = new SalonPrive(guest, host);
                    salonTemp2 = new SalonPrive(host, guest);
                    for (SalonPrive salon : listeSalonPrive) {
                        if (salon.equals(salonTemp1) || salon.equals(salonTemp2)){
                            for (Connexion c : serveur.connectes) {
                                if (c.getAlias().equals(guest)) {
                                    c.envoyer(host +"> " + msg);
                                    break;
                                }
                            }
                        }
                        else{
                            cnx.envoyer("Le salon n'existe de pas");
                        }
                    }
                    break;
                case "QUIT":
                    host = cnx.getAlias();
                    guest = evenement.getArgument();
                    salonTemp1 = new SalonPrive(guest, host);
                    salonTemp2 = new SalonPrive(host, guest);
                    for (SalonPrive salon : listeSalonPrive) {
                        if (salon.equals(salonTemp1) || salon.equals(salonTemp2)){
                            listeSalonPrive.remove(salon);
                            cnx.envoyer("Vous avez quitte le salon prive avec " + guest);
                            for (Connexion c : serveur.connectes) {
                                if (c.getAlias().equals(guest)) {
                                    c.envoyer(host + " a quitter le salon prive");
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}