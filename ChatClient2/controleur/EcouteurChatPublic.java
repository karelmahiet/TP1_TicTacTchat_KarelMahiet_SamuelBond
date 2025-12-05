package controleur;

import com.chat.client.ClientChat;
import vue.PanneauChat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurChatPublic implements ActionListener {
    protected ClientChat clientChat;
    protected PanneauChat panneauChat;

    public EcouteurChatPublic(ClientChat clientChat, PanneauChat panneauChat) {
        this.clientChat = clientChat;
        this.panneauChat = panneauChat;
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        //q2.2
        Object source = evt.getSource();

        if (source instanceof JTextField) {
            String texte = ((JTextField) source).getText();
            if (!texte.isEmpty()){
                clientChat.envoyer("MSG "+texte);
                panneauChat.ajouter("MOI>>"+ texte);
                ((JTextField) source).setText("");
            }
        }
    }
}