package controleur;

import com.chat.client.ClientChat;
import vue.PanneauChat;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurChatPrive extends EcouteurChatPublic {
    private String alias;
    public EcouteurChatPrive(String alias, ClientChat clientChat, PanneauChat panneauChat) {
        super(clientChat, panneauChat);
        this.alias = alias;
    }
    //q3.6


    @Override
    public void actionPerformed(ActionEvent evt) {
        String commande = evt.getActionCommand();
        //ok, voici la reunion familiale de if else lol...
        //je vais expliquer un peu pour demeler tout ca
        if("ACCEPTER".equals(commande)){
            //Inviter et Accpeter est la meme commande pour le serveur
            clientChat.envoyer("TTT"+alias);
        } else if ("REFUSER".equals(commande)) {
            clientChat.envoyer("DECLINE TTT"+alias);
            //cest du texte aussi
        } else if (evt.getSource() instanceof JTextField) {
            String texte = ((JTextField) evt.getSource()).getText();
            if(!texte.isEmpty()) {
                if("QUIT".equals(texte.toUpperCase())){
                    clientChat.envoyer("QUIT"+alias);
                } else if ("ABANDON".equals(texte.toUpperCase())) {
                    clientChat.envoyer("ABANDON" + alias);
                }else {
                    //message prive normal
                    clientChat.envoyer("PRV" + alias + " "+texte);
                    panneauChat.ajouter("MOI>>"+texte);
                }
                ((JTextField) evt.getSource()).setText("");
            }

        }
    }
}