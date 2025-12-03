package vue;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauConfigServeur extends JPanel {
    private JTextField txtAdrServeur, txtNumPort;

    public PanneauConfigServeur(String adr, int port) {
        //q1.2
        this.setLayout(new GridLayout(2,2,5,5));

        //label et txtfld
        JLabel lblIp = new JLabel("Adresse IP: ");
        JLabel lblPort = new JLabel("Port: ");
        txtAdrServeur = new JTextField(adr);
        txtNumPort = new JTextField(String.valueOf(port));

        //ajouter au panneau
        this.add(lblPort);
        this.add(lblIp);
        this.add(txtAdrServeur);
        this.add(txtNumPort);
    }
    public String getAdresseServeur() {
        return txtAdrServeur.getText();
    }
    public String getPortServeur() {
        return txtNumPort.getText();
    }
}
