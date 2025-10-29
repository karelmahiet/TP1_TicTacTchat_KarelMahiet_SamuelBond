package com.chat.serveur;

import com.commun.evenement.Evenement;
import com.commun.evenement.GestionnaireEvenement;
import com.commun.net.Connexion;

import java.util.Vector;

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
        String msg, typeEvenement, aliasExpediteur;
        ServeurChat serveur = (ServeurChat) this.serveur;
        Vector<Invitation> listeInvitation = new Vector<>(); //pour stocké les invitations
        Vector<SalonPrive> listeSalonPrive = new Vector<>(); //pour stocké les salons ouvert

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
                    String alias1, alias2;
                    alias1 = cnx.getAlias();
                    alias2 = evenement.getArgument();
                    if (alias1.equals(alias2)) {
                        System.out.println("Vous ne pouvez pas chatter avec vous-même");
                        break;
                    }
                    for (Invitation inv : listeInvitation) {
                        //si invitation crée
                        if ((inv.getHost().equals(alias1) && inv.getGuest().equals(alias2)) || (inv.getGuest().equals(alias1) && inv.getHost().equals(alias2))) {
                            //crée le salon privé
                            SalonPrive salonPrive = new SalonPrive(alias1, alias2);
                            SalonPrive salonTemp = new SalonPrive(alias2, alias1);
                            for (SalonPrive salon : listeSalonPrive) {
                                if (!salon.equals(salonPrive) && !salon.equals(salonTemp)){
                                    listeSalonPrive.add(salonPrive);
                                    break;
                                }
                                else{
                                    System.out.println("Vous êtes déjà dans un salon privé avec "+alias2);
                                    break;
                                }
                            }
                        }
                        else{
                            System.out.println("Une invitation à déjà été envoyé à "+alias2);
                            break;
                        }
                    }
                    Invitation invitation = new Invitation(alias1, alias2);
                    listeInvitation.add(invitation);
                    //informé alias2 de l’arrivée d’une invitation de alias1
                    for (Connexion c : serveur.connectes) {
                        if (c.getAlias().equals(alias2)) {
                            c.envoyer(alias1 +" vous a envoyé une invitation à un chat privé (JOIN/DECLINE alias " +
                                    "pour accepter ou refuser)");
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