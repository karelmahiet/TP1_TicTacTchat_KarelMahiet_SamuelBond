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
public class PanneauChat extends JPanel {
    protected JTextArea zoneChat;
    protected JTextField champDeSaisie;

    public PanneauChat() {
        //q2.1
        this.setLayout(new BorderLayout());

        zoneChat= new JTextArea();
        zoneChat.setEditable(false);
        champDeSaisie = new JTextField();
        JScrollPane scrollPane = new JScrollPane(zoneChat);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(champDeSaisie, BorderLayout.SOUTH);
    }

    public void ajouter(String msg) {
        zoneChat.append("\n"+msg);
    }
    public void setEcouteur(ActionListener ecouteur) {
        //q2.3
        if (champDeSaisie != null) {
            champDeSaisie.addActionListener(ecouteur);
        }
    }

    public void vider() {
        this.zoneChat.setText("");
    }
}