/*
 *  Steganografia su PDF is a software developed for embedding and extracting 
 *  a secret message within PDF files.
 *  This software is property of Rocchina Romano.
 *  mail: rocchina.romano@yahoo.it
 *
 *	Copyright Rocchina Romano 2020
 *
 *  class AggiungiSchermataEstrazione
 */
package steganografiasupdf.extraction;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.KeyStroke;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.SteganografiaSuPDF;

/**
 *
 * @author Rocchina
 */
public class AggiungiSchermataEstrazione extends AbstractAction{

    private final Log log = LogFactory.getLog(AggiungiSchermataEstrazione.class);
    private SteganografiaSuPDF steganografia;
    
    public AggiungiSchermataEstrazione(SteganografiaSuPDF steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Estrai Messaggio");
        this.putValue(Action.SHORT_DESCRIPTION, "Estrai il messaggio segreto dal PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In AggiungiSchermataEstrazione");
        this.steganografia.getSp().schermataEstrazione();
    }
    
}
