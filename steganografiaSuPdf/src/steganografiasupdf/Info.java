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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Rocchina
 */
public class Info extends AbstractAction{

    private final SteganografiaSuPdf steganografia;
    private final Log log = LogFactory.getLog(Info.class);
    
    
    public Info(SteganografiaSuPdf steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Info");
        this.putValue(Action.SHORT_DESCRIPTION, "Info sull'applicazione");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In AzioneInfo");
        String info ="STEGANOGRAFIA SU PDF\n" 
                + "\nLa seguente app consente di:\n"
                + "1. Inserire un messaggio segreto all'interno di un PDF" 
                + "\n2. estrarre un messaggio segreto da un PDF";
        this.steganografia.getFrame().finestraInfo(info);
    }
    
}
