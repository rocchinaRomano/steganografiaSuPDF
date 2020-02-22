/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.KeyStroke;

/**
 *
 * @author Rocchina
 */
public class Esci extends AbstractAction{

    private final SteganografiaSuPDF steganografia;
    
    public Esci(SteganografiaSuPDF steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Esci");
        this.putValue(Action.SHORT_DESCRIPTION, "Esci dall'applicazione");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
    
}
