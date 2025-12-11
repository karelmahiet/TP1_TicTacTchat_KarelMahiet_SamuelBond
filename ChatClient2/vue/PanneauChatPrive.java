package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauChatPrive extends PanneauChat {
    private JButton bAccepterOuInviter, bRefuser;
    private FenetreTicTacToe fenetreTicTacToe;
    public PanneauChatPrive() {
        bAccepterOuInviter = new JButton("Inviter TTT");
        bRefuser = new JButton("Refuser");

        bAccepterOuInviter.setActionCommand("ACCEPTER");
        bRefuser.setActionCommand("REFUSER");

        //q3.4
        JPanel pNord = new JPanel();
        pNord.add(bAccepterOuInviter);
        pNord.add(bRefuser);
        this.add(pNord, BorderLayout.NORTH);
    }
    @Override
    public void setEcouteur(ActionListener ecouteur) {
        super.setEcouteur(ecouteur);
        bAccepterOuInviter.addActionListener(ecouteur);
        bRefuser.addActionListener(ecouteur);
    }
    public void invitationAJouerRecue() {
        //q3.5
        bAccepterOuInviter.setText("Accepter");
        bRefuser.setVisible(true);
    }
    public void invitationAJouerAnnulee() {
        //q3.5 aussi
        bAccepterOuInviter.setText("Inviter TTT");
        bRefuser.setVisible(false);
    }

    public void setFenetreJeu(FenetreTicTacToe fenetreTicTacToe) {
        if (this.fenetreTicTacToe !=null){
            this.fenetreTicTacToe.setVisible(false);
            this.fenetreTicTacToe.dispose();
        }
        this.fenetreTicTacToe = fenetreTicTacToe;
    }
    public FenetreTicTacToe getFenetreJeu() {
        return this.fenetreTicTacToe;
    }
}