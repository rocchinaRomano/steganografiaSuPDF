/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.estrazione;

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
public class MostraPannelloEstrazione extends AbstractAction{
    
    private final Log log = LogFactory.getLog(MostraPannelloEstrazione.class);
    private final SteganografiaSuPdf steganografia;
    
    public MostraPannelloEstrazione(SteganografiaSuPdf steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Estrai Messaggio");
        this.putValue(Action.SHORT_DESCRIPTION, "Estrai il messaggio dal PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In MostraPannelloEstrazione");
        this.steganografia.getFrame().mostraPannelloEstrazione();
    }
    
}
