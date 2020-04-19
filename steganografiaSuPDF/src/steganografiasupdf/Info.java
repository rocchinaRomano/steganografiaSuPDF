/*
 *  Steganografia su PDF is a software developed for embedding and extracting 
 *  a secret message within PDF files.
 *  This software is property of Rocchina Romano.
 *  mail: rocchina.romano@yahoo.it
 *
 *	Copyright Rocchina Romano 2020
 *
 *  class Info
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

public class Info extends AbstractAction{
    
    private final SteganografiaSuPDF steganografia;
    
    public Info(SteganografiaSuPDF steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Info");
        this.putValue(Action.SHORT_DESCRIPTION, "Info sull'applicazione");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String info ="STEGANOGRAFIA SU PDF\n" 
                + "\nLa seguente app consente di:\n"
                + "1. Inserire un messaggio segreto all'interno di un PDF" 
                + "\n2. estrarre un messaggio segreto da un PDF";
        this.steganografia.getSp().finestraInfo(info);
    }
    
}
