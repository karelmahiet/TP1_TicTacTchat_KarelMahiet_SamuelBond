package controleur;

import com.chat.client.ClientChat;
import vue.PanneauInvitations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//q3.2
public class EcouteurInvitations implements ActionListener {

    private ClientChat clientChat;
    private PanneauInvitations panneauInvitations;

    public EcouteurInvitations(ClientChat clientChat, PanneauInvitations panneauInvitations) {
        this.clientChat = clientChat;
        this.panneauInvitations = panneauInvitations;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //q3.2 suite
        String action = e.getActionCommand();
        List<String> selection = panneauInvitations.getElementsSelectionnes();

        for(String alias : selection) {
            if ("ACCEPTER".equals(action)){
                clientChat.envoyer("JOIN" + alias);
            } else if ("REFUSER".equals(action)){
                clientChat.envoyer("DECLINE" + alias);
            }
            panneauInvitations.retirerInvitationRecue(alias);
        }
    }
}
