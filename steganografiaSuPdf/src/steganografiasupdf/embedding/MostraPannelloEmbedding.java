/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import steganografiasupdf.SteganografiaSuPdf;

/**
 *
 * @author Rocchina
 */
public class MostraPannelloEmbedding extends AbstractAction{

    private final SteganografiaSuPdf steganografia;
    private final Log log = LogFactory.getLog(MostraPannelloEmbedding.class);

    public MostraPannelloEmbedding(SteganografiaSuPdf steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Inserisci Messaggio");
        this.putValue(Action.SHORT_DESCRIPTION, "Inserisci il messaggio nel PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In MostraPannelloEmbedding");
        this.steganografia.getFrame().mostraPannelloEmbedding();
    }
    
}
