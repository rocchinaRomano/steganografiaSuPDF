/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.extraction;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.KeyStroke;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.Costanti;
import steganografiasupdf.SteganografiaSuPDF;

/**
 *
 * @author Rocchina
 */
public class EstraiMessaggio extends AbstractAction{

    private final Log log = LogFactory.getLog(EstraiMessaggio.class);
    private final SteganografiaSuPDF steganografia;
    private String stego;
    
    //Lista sottosequenze estratte:
    private final List listaSottoseq = new ArrayList();
    
    //Lista pagina:
    private final static List<MessEstrattoPerPagina> listaMessEstratti = new ArrayList();
    
    private static int numPagina = 0;
    
    public EstraiMessaggio(SteganografiaSuPDF steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Estrai Messaggio");
        this.putValue(Action.SHORT_DESCRIPTION, "Estrai il messaggio dal PDF");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_M);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl M"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In EstraiMessaggio");
        //verificare che sia stato selezionato il pdf
        //Poi procedere con l'estrazione
        //svuota lista sottosequenze
        this.listaSottoseq.removeAll(this.listaSottoseq);
        this.listaMessEstratti.removeAll(this.listaMessEstratti);
        numPagina = 0;
        this.stego = (String)this.steganografia.getObject(Costanti.stegoPdf);
        if(this.stego == null){
            String errore = "ATTENZIONE!!!\n"
                    + "Non è stato selezionato nessun file PDF!";
            this.steganografia.getSp().finestraErrore(errore);
        }else{
            log.info("PDF selezionato: " + this.stego);
            int contaImmagini = contaImmagini();
            log.info("Numero immagini steganografiche nel PDF: " + contaImmagini);
            if(contaImmagini == 0){
                //non c'è contenuto steganografico
                String info = "Il PDF non contiene alcuna informazione steganografica!";
                this.steganografia.getSp().finestraInfo(info);
            }else{
                //C'è contenuto steganografico
                try{
                    estraiMessaggio();
                }catch(StringIndexOutOfBoundsException ex){
                    String errore = "ATTENZIONE!!!\n"
                        + "Contenuto steganografico CORROTTO!";
                    this.steganografia.getSp().finestraErrore(errore);
                }
            }
        }
    }

    private void estraiMessaggio() {
        try {
            log.info("In estraiMessaggio() ");
            PdfReader reader = new PdfReader(this.stego);
            int numPagine = reader.getNumberOfPages();
            log.info("Numero pag PDF selezionato: " + numPagine);
            estraiMessaggioPagina(1, reader);
            reader.close();
            log.info("Dimensione ListaSottoseq: " + this.listaSottoseq.size());
                String messEstratto = "";
                for(int j = listaSottoseq.size()-1; j >= 0; j--){
                    String s = (String)listaSottoseq.get(j);
                    messEstratto = messEstratto + s;
                }
                log.info("Messaggio BINARIO estratto: " + messEstratto);
                //procedura per convertire la stringa binaria nel messaggio:
                if(listaSottoseq.isEmpty()){
                    //nessun messaggio estratto
                    String info = "Il PDF non contiene alcuna informazione "
                            + "steganografica!";
                    this.steganografia.getSp().finestraInfo(info);
                }else{
                    //conversione del messaggio
                    log.info("Conversione del MESSAGGIO DA BINARIO");
                    String mess = convertiMessaggio(messEstratto);
                    log.info("MESSAGGIO CONVERTITO: " + mess);
                    
                    this.steganografia.getSp().getsEstrazione().mostraMessaggio(mess);
                }
        } catch (IOException ex) {
            String errore = "ATTENZIONE!!!\n"
                    + "Errore durante il caricamento da file!";
            this.steganografia.getSp().finestraErrore(errore);
        }
    }

    private void estraiMessaggioPagina(int pag, PdfReader reader) throws IOException {
        for(int i = 1; i <= reader.getXrefSize(); i++){
            PdfObject pdfo = reader.getPdfObject(i);
            if(pdfo != null && pdfo.isStream()){
                PRStream stream = (PRStream)pdfo;
                PdfObject type = stream.get(PdfName.SUBTYPE);
                if(type != null && type.toString().equals(PdfName.IMAGE.toString())){
                    log.info("Type: " + type);
                    PdfImageObject pio = new PdfImageObject(stream);
                    BufferedImage bi = pio.getBufferedImage();
                    if(bi.getHeight()== 4 && bi.getWidth() == 4){
                        log.info("Sottosequenza 00");
                        listaSottoseq.add("00");
                    }else if(bi.getHeight() == 8 && bi.getWidth() == 8){
                        log.info("Sottosequenza 01");
                        listaSottoseq.add("01");
                    }else if(bi.getHeight() == 16 && bi.getWidth() == 16){
                        log.info("Sottosequenza 10");
                            listaSottoseq.add("10");
                    }else if(bi.getHeight() == 32 && bi.getWidth() == 32){
                        log.info("Sottosequenza 11");
                        listaSottoseq.add("11");
                    }
                }
            }
        }
    }

    private String convertiMessaggio(String messEstratto) {
        log.info("convertiMessaggio(String messEstratto)");
        log.info("Messaggio da convertire: " + messEstratto);
        String risultato = "";
        /* Prendiamo la stringa binaria e creiamo delle sottosequenze di 8 caratteri
            dato che 1 byte = 8 bit
        */
        int i = 0;
        int c = 8;
        while(messEstratto.length() != 0){
            String sottos = messEstratto.substring(i, c);
            log.info("Sottosequenza: " + sottos);
            /* Togliamo la sottosequenza di 8 bit appena esaminata dal testo 
                in binario: creiamo una stringa di appoggio per fare ciò
            */
            String copy = messEstratto;
            messEstratto = copy.substring(c, copy.length());
            //chiamare la funzione che mi calcola il carattere corrispondente
            risultato = risultato + converti(sottos);
        }
        log.info("Messaggio convertito: " + risultato);
        return risultato;
    }

    private String converti(String sottos) {
        String carattere = "";
        //codice ascii corrispondente agli 8 bit:
        int ascii = Integer.parseInt(sottos, 2); 
        //mi ricavo la lettera corrispondente al codice ascii:
        char c = (char)ascii;
        carattere = carattere + c;
        return carattere;
    }

    private int contaImmagini() {
        int cont = 0;
        try {
            //controlla se il PDF contiene immagini:
            PdfReader reader = new PdfReader(this.stego);
            log.info("Numero pag. PDF: " + reader.getNumberOfPages());
            for(int i = 1; i <= reader.getXrefSize(); i++){
                PdfObject pdfobj = reader.getPdfObject(i);
                if(pdfobj != null && pdfobj.isStream()){
                    PRStream stream = (PRStream)pdfobj;
                    PdfObject type = stream.get(PdfName.SUBTYPE);
                    log.info("Subtype: " + type);
                    if(type != null && type.toString().equals(PdfName.IMAGE.toString())){
                        log.info("Type: " + type);
                    PdfImageObject pio = new PdfImageObject(stream);
                    BufferedImage bi = pio.getBufferedImage();
                    if(bi.getHeight()== 4 && bi.getWidth() == 4){
                        log.info("Sottosequenza 00");
                        cont++;
                    }else if(bi.getHeight() == 8 && bi.getWidth() == 8){
                        log.info("Sottosequenza 01");
                        cont++;
                    }else if(bi.getHeight() == 16 && bi.getWidth() == 16){
                        log.info("Sottosequenza 10");
                        cont++;
                    }else if(bi.getHeight() == 32 && bi.getWidth() == 32){
                        log.info("Sottosequenza 11");
                        cont++;
                    }
                }
            }
        }
        reader.close();
        } catch (IOException ex) {
            String errore = "ATTENZIONE!\n"
                    + "Errore durante il caricamento del PDF!";
            this.steganografia.getSp().finestraErrore(errore);
        }
        log.info("Numero immagini nel PDF: " + cont);
        return cont;
    }
}