package controleur;

import com.chat.client.ClientChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurTicTacToe implements ActionListener {

    private ClientChat clientChat;
    private String symbole;

    public EcouteurTicTacToe(ClientChat clientChat, String symbole) {
        this.clientChat = clientChat;
        this.symbole = symbole;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd = e.getActionCommand();

        int ligne = cmd.charAt(0) - '0';
        int colonne = cmd.charAt(1) - '0';


        clientChat.envoyer("COUP " + symbole + " " + ligne + " " + colonne);
    }
}
