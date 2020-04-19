/*
 *  Steganografia su PDF is a software developed for embedding and extracting 
 *  a secret message within PDF files.
 *  This software is property of Rocchina Romano.
 *  mail: rocchina.romano@yahoo.it
 *
 *	Copyright Rocchina Romano 2020
 *
 *  class AggiungiSchermataEmbedding
 */
package steganografiasupdf.embedding;

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
public class AggiungiSchermataEmbedding extends AbstractAction{

    private final SteganografiaSuPDF steganografia;
    
    private final Log log = LogFactory.getLog(AggiungiSchermataEmbedding.class);
    
    public AggiungiSchermataEmbedding(SteganografiaSuPDF steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Inserisci Messaggio");
        this.putValue(Action.SHORT_DESCRIPTION, "Inserisci il messaggio segreto nel PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In AggiungiSchermataEmbedding");
        this.steganografia.getSp().schermataEmbedding();
    }
    
}
