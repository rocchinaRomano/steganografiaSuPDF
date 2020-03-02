/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.embedding;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfImageObject;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.KeyStroke;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.Costanti;
import steganografiasupdf.F5.EmbeddingF5;
import steganografiasupdf.SteganografiaSuPDF;

/**
 *
 * @author Rocchina
 */
public class InserisciMessaggio extends AbstractAction{

    private final Log log = LogFactory.getLog(InserisciMessaggio.class);
    private final SteganografiaSuPDF steganografia;
    
    private String messaggio;
    private String messBinario;
    private String password; // per l'algoritmo F5
    private String cover;
    
    //SCHEMA DI CODIFICA/DECODIFICA:
    //IMMAGINI:
    //immagine 4x4 -> 00
    private final String immagine1 = "embedding/input/immaginiBianche/immagine1.jpg";
    //immagine 8x8-> 01
    private final String immagine2 = "embedding/input/immaginiBianche/immagine2.jpg";
    //immagine 16x16 -> 10
    private final String immagine3 = "embedding/input/immaginiBianche/immagine3.jpg";
    //immagine 32x32 -> 11
    private final String immagine4 = "embedding/input/immaginiBianche/immagine4.jpg";
    
    //lista delle sottosequenze del mess binario da inserire nel PDF:
    private final List listaSottosequenze = new ArrayList<>();
    
    private String coverAppoggio;
    
    //path dei file temporanei:
    private final String pathTemp = "embedding/temp/";
    
    //percorso dove salvare lo stegoPDF:
    private final String stego = "embedding/output/stego.pdf";
    
    //contatore per il salvataggio delle immagini cover per F5
    private int numImmagini = 1;
    
    //path immagini cover per F5
    private final String pathImmCoverF5 = "embedding/immaginiCoverF5/";
    
    //lista per ordinare le immagini cover
    private static List<String> listaPathFileCover = new ArrayList<>();
    
    //path immagini stego per F5
    private final String pathImmStegoF5 = "embedding/immaginiStegoF5/";
    
    //lista per ordinare le immagini stego
    private static List<String> listaPathFileStego = new ArrayList<>();
    
    //path di destinazione dopo l'embedding con F5
    private final String stegoF5 = "embedding/output/stegoF5.pdf";
    
    //path di appoggio per salvare il pdf con le immagini stego
    private String pdfAppoggioF5;
    private int numPdfF5 = 1;
    
    
    public InserisciMessaggio(SteganografiaSuPDF steganografia){
        this.steganografia = steganografia;
        this.putValue(Action.NAME, "Inserisci Messaggio");
        this.putValue(Action.SHORT_DESCRIPTION, "Inserisci il messaggio nel PDF selezionato");
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_M);
        this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl M"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        log.info("In InserisciMessaggio");
        //Svuotare la cartella dei file temporanei:
        svuotaCartella(this.pathTemp);
        //Azzerare la lista delle sottosequenze:
        this.listaSottosequenze.removeAll(this.listaSottosequenze);
        //svuota cartella immaginiCoverF5
        svuotaCartella(this.pathImmCoverF5);
        numImmagini = 1;
        //CONTROLLI: mess e password inseriti e pdf selezionato
        this.messaggio = (String)this.steganografia.getObject(Costanti.messaggioInserito);
        this.password = (String)this.steganografia.getObject(Costanti.passwordEmbedding);
        this.cover = (String)this.steganografia.getObject(Costanti.coverPdf);
        
        if(this.messaggio == null || this.password == null || this.cover == null){
            String errore = "ATTENZIONE!\n"
                    + "1. Il campo MESSAGGIO non può essere vuoto!\n"
                    + "2. Il campo PASSWORD non può essere vuoto!\n"
                    + "3. Errore durante il caricamento del PDF!";
            this.steganografia.getSp().finestraErrore(errore);
        }else{
            log.info("Messaggio inserito: " + this.messaggio);
            log.info("Password inserita: " + this.password);
            log.info("Path cover PDF: " + this.cover);
            //controllare se il PDF contiene immagini:
            int numImmagini = contieneImmagini();
            log.info("Numero di immagini nel PDF: " + numImmagini);
            if(numImmagini == 0){
                log.info("Il PDF NON contiene immagini!");
                //il Pdf non contiene immagini:
                //usare solo l'algoritmo mio:
                convertiMessaggio(this.messaggio);
                log.info("Messaggio: " + messaggio + "\nMessaggio binario: " 
                        + this.messBinario);
                //Suddividere il messaggio binario in sottosequenze di 2 bit
                //inizializzare la lista delle sottosequenze:
                inizializzaListaSottoseq();
                log.info("Dimensione lista sottosequenze: " + this.listaSottosequenze.size());
                //aggiungere le immagini bianche nel PDF:
                inserisciMessaggio();
                log.info("Messaggio inserito!");
                File fileStego = new File(this.stego);
                String pathStego = fileStego.getAbsolutePath();
                String info = "Il PDF è stato salvato qui: \n"
                        + pathStego;
                this.steganografia.getSp().finestraInfo(info);
            }else{
                log.info("Il PDF contiene immagini!");
                //il PDF contiene immagini:
                estraiImmagini();
                //ATTENZIONE--->>
                /* Prima di fare l'embedding con F5, devo ordinare anche le
                    immagini cover.Fare un listaPathFileCover.
                */
                ordinaLista(pathImmCoverF5, listaPathFileCover);
                /*log.info("***Lista DOPO l'ordinamento:");
                for(int i = 0; i < listaPathFileCover.size(); i++){
                    log.info(listaPathFileCover.get(i));
                }*/
                embeddingF5();
                ordinaLista(pathImmStegoF5, listaPathFileStego);
                log.info("***Lista DOPO l'ordinamento:");
                /*for(int i = 0; i < listaPathFileStego.size(); i++){
                    log.info(listaPathFileStego.get(i));
                }*/
                sostituisciImmagini();
                svuotaCartella(pathTemp);
                svuotaCartella(pathImmCoverF5);
                svuotaCartella(pathImmStegoF5);
                
                //inizia l'inserimento con l'algoritmo mio
                
                this.cover = this.stegoF5;
                log.info("Input per l'algoritmo mio: " + this.cover);
                convertiMessaggio(this.messaggio);
                log.info("Messaggio: " + messaggio + "\nMessaggio binario: " 
                        + this.messBinario);
                //Suddividere il messaggio binario in sottosequenze di 2 bit
                //inizializzare la lista delle sottosequenze:
                inizializzaListaSottoseq();
                log.info("Dimensione lista sottosequenze: " + this.listaSottosequenze.size());
                //aggiungere le immagini bianche nel PDF:
                inserisciMessaggio();
                log.info("Messaggio inserito!");
                File fileStego = new File(this.stego);
                String pathStego = fileStego.getAbsolutePath();
                String info = "Il PDF è stato salvato qui: \n"
                        + pathStego;
                this.steganografia.getSp().finestraInfo(info);
                svuotaCartella(pathTemp);
            }
        }
    }

    private int contieneImmagini() {
        int cont = 0;
        try {
            //controlla se il PDF contiene immagini:
            PdfReader reader = new PdfReader(this.cover);
            log.info("Numero pag. PDF: " + reader.getNumberOfPages());
            for(int i = 1; i <= reader.getXrefSize(); i++){
                PdfObject pdfobj = reader.getPdfObject(i);
                if(pdfobj != null && pdfobj.isStream()){
                    PRStream stream = (PRStream)pdfobj;
                    PdfObject type = stream.get(PdfName.SUBTYPE);
                    log.info("Subtype: " + type);
                    if(type != null && type.toString().equals(PdfName.IMAGE.toString())){
                        cont++;
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

    private void svuotaCartella(String pathTemp) {
        File directory = new File(pathTemp);
        File[] files = directory.listFiles();
        for(File f:files){
            f.delete();
        }
    }

    private void convertiMessaggio(String messaggio) {
         /* Converte il messaggio nella stringa binaria corrispondente
            utilizzando la TEBELLA ASCII */
        this.messBinario = "";
        for(int i = 0; i < this.messaggio.length(); i++){
            String s = Integer.toBinaryString(this.messaggio.charAt(i));
            log.info("Stringa parziale: " + s);
            /* Dato che 1 byte è uguale a 8bit, dobbiamo assicurarci
                che ciascun carattere contenga 8 bit.
                Se così non fosse, aggiungiamo tanti zeri
                fino a quando non ho una stringa di 8 bit. */
            while(s.length() < 8){
                s = "0" + s;
            }
            this.messBinario = this.messBinario + s;
        }
        log.info("Messaggio binario: " + this.messBinario);
    }

    private void inizializzaListaSottoseq() {
        String mess = this.messBinario;
        int i = 0;
        int j = 2;
        while(mess.length() != 0){
            String sottoseq = mess.substring(i, j);
            log.info("Sottosequenza: " + sottoseq);
            this.listaSottosequenze.add(sottoseq);
            /* Togliamo la sottosequenza di 2 bit appena esaminata dal testo in binario.
                Pertanto, creiamo una stringa di appoggio: */
            String copy = mess;
            mess = copy.substring(j, copy.length());
        }
    }

    private void inserisciMessaggio(){
        numImmagini = 1;
        try {
            log.info("Path coverPdf: " + this.cover);
            coverAppoggio = this.cover;
            log.info("Path coverPdfAppoggio: " + coverAppoggio);
            //per ogni pag del PDF aggiungo una sottosequenza e così via...
            //prelevo una sottosequenza e l'aggiungo in tutte le pag del PDF.
            float x1 = 1;
            float y1 = 773;
            for(int i = 0; i < this.listaSottosequenze.size(); i++){
                // ciclo della lista sottosequenze:
                // i -> sottosequenza i-esima
                String sottoSeq = (String)this.listaSottosequenze.get(i);
                log.info("Sottosequenza prelevata: " + sottoSeq);
                /*  in base alla sottosequenza effettuiamo l'inserimento
                    dell'immagine nel PDF:
                    1. 00 -> immagine 4x4
                    2. 01 -> immagine 8x8
                    3. 10 -> immagine 16x16
                    4. 11 -> immagine 32x32
                */
                // se la sottosequenza è 00, ad esempio, inseriamo un immagine
                // 4x4 in tutte le pagine del pdf
                String pdfStego = "embedding/temp/stego";
                
                if(sottoSeq.equalsIgnoreCase("00")){
                    //inserire immagine 4x4
                    if(i == this.listaSottosequenze.size()-1){
                        pdfStego = stego;
                    }else{
                        pdfStego = pdfStego + i + ".pdf";
                    }
                    Image image = Image.getInstance(immagine1);
                    aggiungiSottosequenza(x1, y1, image, pdfStego);
                    x1 = x1 + (float)0.5 + image.getWidth();
                    this.coverAppoggio = pdfStego;
                }else if(sottoSeq.equalsIgnoreCase("01")){
                    //inserire immagine 8x8
                    if(i == this.listaSottosequenze.size()-1){
                        pdfStego = stego;
                    }else{
                        pdfStego = pdfStego + i + ".pdf";
                    }
                    Image image = Image.getInstance(immagine2);
                    aggiungiSottosequenza(x1, y1, image, pdfStego);
                    x1 = x1 + (float)0.5 + image.getWidth();
                    this.coverAppoggio = pdfStego;
                }else if(sottoSeq.equalsIgnoreCase("10")){
                    //inserire immagine 16x16
                    if(i == this.listaSottosequenze.size()-1){
                        pdfStego = stego;
                    }else{
                        pdfStego = pdfStego + i + ".pdf";
                    }
                    Image image = Image.getInstance(immagine3);
                    aggiungiSottosequenza(x1, y1, image, pdfStego);
                    x1 = x1 + (float)0.5 + image.getWidth();
                    this.coverAppoggio = pdfStego;
                }else if(sottoSeq.equalsIgnoreCase("11")){
                    //inserire immagine 32x32
                    if(i == this.listaSottosequenze.size()-1){
                        pdfStego = stego;
                    }else{
                        pdfStego = pdfStego + i + ".pdf";
                    }
                    Image image = Image.getInstance(immagine4);
                    aggiungiSottosequenza(x1, y1, image, pdfStego);
                    x1 = x1 + (float)0.5 + image.getWidth();
                    this.coverAppoggio = pdfStego;
                }
                
                if(x1 > 550){
                    log.info("nell'if x1 > 550");
                    //inserisce l'immagine nella riga successiva:
                    x1 = 1;
                    y1 = y1 - 32 - 1;
                }
            }
            svuotaCartella(this.pathTemp);
        } catch (IOException | BadElementException ex) {
            String errore = "ATTENZIONE!!!\n"
                + ("Errore durante il processo di Embedding!");
            this.steganografia.getSp().finestraErrore(errore);
        }
    }

    private void aggiungiSottosequenza(float x1, float y1, Image image, String stegoPdf) {
        try {
            PdfReader reader = new PdfReader(this.coverAppoggio);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(stegoPdf));
            for(int i = 1; i <= reader.getNumberOfPages(); i++ ){
                image.setAbsolutePosition(x1, y1);
                stamper.getUnderContent(i).addImage(image);
            }
            stamper.close();
            reader.close();
        } catch (IOException | DocumentException ex) {
            String errore = "ATTENZIONE!\n"
                    + "Errore durante il salvataggio del file!";
            this.steganografia.getSp().finestraErrore(errore);
        }
    }

    private void estraiImmagini() {
        try {
            log.info("\n***ESTRAI LE IMMAGINI DAL PDF***");
            PdfReader reader = new PdfReader(this.cover);
            log.info("Numero pag PDF: " + reader.getNumberOfPages());
            for(int i = 1; i <= reader.getXrefSize(); i++){
                PdfObject pdfo = reader.getPdfObject(i);
                if(pdfo != null && pdfo.isStream()){
                    PRStream stream = (PRStream)pdfo;
                    PdfObject type = stream.get(PdfName.SUBTYPE);
                    log.info("Type: " + type);
                    if(type != null && type.toString().equals(PdfName.IMAGE.toString())){
                        PdfImageObject pio = new PdfImageObject(stream);
                        BufferedImage bi = pio.getBufferedImage();
                        String path = "embedding/immaginiCoverF5/"
                                + "immagine_" + numImmagini + ".jpg";
                        File file = new File(path);
                        ImageIO.write(bi, "jpg", file);
                        numImmagini++;
                    }
                }
            }
            reader.close();
        } catch (IOException ex) {
            String errore = "ATTENZIONE!\n"
                    + "Errore durante il caricamento del file!";
            this.steganografia.getSp().finestraErrore(errore);
        }
    }

    private void ordinaLista(String path, List<String> lista) {
        log.info("Nel metodo ordinaLista");
        File file = new File(path);
        File[] files = file.listFiles();
        Arrays.sort(files, new Comparator<File>() {

            @Override
            public int compare(File o1, File o2) {
                int n1 = estraiNumero(o1.getName());
                int n2 = estraiNumero(o2.getName());
                return n1 - n2;
            }

            private int estraiNumero(String name) {
                int i = 0;
                try {
                    int s = name.indexOf('_')+1;
                    int e = name.lastIndexOf('.');
                    String number = name.substring(s, e);
                    i = Integer.parseInt(number);
                } catch(Exception e) {
                    i = 0; // if filename does not match the format
                           // then default to 0
                }
                return i;
            }
        });
        for(File f : files){
            String pathFile = f.getPath();
            lista.add(pathFile);
        }
    }

    private void embeddingF5() {
        log.info("\n*** EMBEDDING F5 ***");
        log.info("Messaggio da inserire: " + this.messaggio);
        log.info("Password da inserire: " + this.password);
        for(int i = 0; i < listaPathFileCover.size(); i++){
            String path = listaPathFileCover.get(i);
            log.info("Path immagine F5 estratto: " + path);
            File fileCover = new File(path);
            String numero = estraiNumero(fileCover.getName());
            log.info("Numero estratto: " + numero);
            String pathStego = "embedding/" + "immaginiStegoF5/" 
                    + "immStego_" + numero + ".jpg";
            log.info("Path stego F5: " + pathStego);
            File fileStego = new File(pathStego);
            EmbeddingF5 f5 = new EmbeddingF5(this.steganografia, this.messaggio, this.password, fileCover, fileStego);
            log.info("Embedding con F5 eseguito..");
        }
     
    }

    private String estraiNumero(String name) {
        int s = name.indexOf('_')+1;
        int e = name.lastIndexOf('.');
        String number = name.substring(s, e);
        return number;
    }

    private void sostituisciImmagini() {
        try {
            log.info("\n*** SOSTITUISCI IMMAGINI *** \n");
            this.pdfAppoggioF5 = this.cover;
            PdfReader reader = new PdfReader(this.cover);
            int numPagine = reader.getNumberOfPages();
            reader.close();
            log.info("Numero pagine PDF: " + numPagine);
            //fare il ciclo for al cotrario
            //sostituisco le immagini dall'ultma pagina alla prima
            for(int i = numPagine; i >= 1; i--){
                log.info("Pagina esaminta: " + i);
                sostituisci(i);
                log.info("SOSTITUZIONE EFFETTUATA.");
            }
        } catch (IOException ex) {
            String errore = "ATTENZIONE!\n"
                    + "Errore durante il caricamento del file!";
            this.steganografia.getSp().finestraErrore(errore);
        }
    }

    private void sostituisci(int pag) {
        try {
            log.info("Nel metodo sostituisci(int pag) ");
            String appoggio;
            if(pag == 1){
                //ultima iterazione
                appoggio = stegoF5;
            }else{
                appoggio = pathTemp + "out_" + numPdfF5 + ".pdf";
            }
            log.info("Nome pdf dopo embedding con F5: " + appoggio);
            PdfReader reader = new PdfReader(pdfAppoggioF5);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(appoggio));
            PdfWriter writer = stamper.getWriter();
            PdfDictionary pg = reader.getPageN(pag);
            PdfDictionary res = (PdfDictionary)PdfReader.getPdfObject(pg.get(PdfName.RESOURCES));
            PdfDictionary xobj = (PdfDictionary)PdfReader.getPdfObject(res.get(PdfName.XOBJECT));
            if(xobj != null){
                int keys = xobj.getKeys().size();
                log.info("Keys: " + keys);
                Set<PdfName> setKey = xobj.getKeys();
                log.info(setKey.toString());
                int i = 0;
                String[] resImage = new String[keys];
                for(PdfName name : setKey){
                    resImage[i] = name.toString();
                    i++;
                }
                log.info("Dimensione resImage: " + resImage.length);
                for(int j = 0; j <= resImage.length-1; j++){
                    log.info("Elemento: " + resImage[j]);
                }
                    
                //Esamino l'array resImage
                //scorro il flusso setKey
                //cerco l'oggetto di setKey che ha quel nome e lo sostituisco
                
                for(int j = 0; j <= resImage.length-1; j++){
                    String s = resImage[j];
                    log.info("Iterazione " + j);
                    log.info("Stringa: " + s);
                    for(PdfName name : setKey){
                        String sPdf = name.toString();
                        if(s.equals(sPdf)){
                            log.info("Uguali.");
                            log.info("s: " + s + ", sPdf: " + sPdf);
                            PdfObject obj = xobj.get(name);
                            log.info("obj _ " + obj);
                            if(obj.isIndirect()){
                                log.info("obj.isIndirect()");
                                PdfDictionary tg = (PdfDictionary)PdfReader.getPdfObject(obj);
                                PdfName type = (PdfName)PdfReader.getPdfObject(tg.get(PdfName.SUBTYPE));
                                if(PdfName.IMAGE.equals(type)){
                                    log.info("PdfName.IMAGE");
                                    PdfReader.killIndirect(obj);
                                    String path = cercaImmagine(listaPathFileStego);
                                    log.info("Path immagine stego in "
                                        + "sostituisci immagini: " + path);
                                    Image img = Image.getInstance(path);
                                    Image imageMask = img.getImageMask();
                                    if(imageMask != null){
                                        writer.addDirectImageSimple(imageMask);
                                    }
                                    writer.addDirectImageSimple(img, (PRIndirectReference)obj);
                                    eliminaImmagine(path);
                                    break;
                                }
                            }
                        }
                        
                    }
                    
                }
            }
            stamper.close();
            reader.close();
            pdfAppoggioF5 = appoggio;
            numPdfF5++;
        } catch (IOException | DocumentException ex) {
            String errore = "ATTENZIONE!\n"
                    + "Errore durante il caricamento del file!";
            this.steganografia.getSp().finestraErrore(errore);
        }
    }

    private String cercaImmagine(List<String> lista) {
        log.info("IN cercaImmagine(List<String> listaPathFileStego)");
        //Scorrere listaPathFileStego prendere ultimo elemento
        //private static int elementoSelezionato = 0; 
        //Ritorno la stringa
        //Dopo cancellare
        String path = lista.get(lista.size()-1);
        log.info("Path: " + path);
        return path;
    }

    private void eliminaImmagine(String path) {
        log.info("In eliminaImmagine(String path)");
        System.out.println("File da eliminare_: " + path);
        for(int i = 0; i < listaPathFileStego.size(); i++){
            String s = listaPathFileStego.get(i);
            //log.info("Stringa estratta: " + s);
            if(s.equals(path)){
                listaPathFileStego.remove(s);
                log.info("path rimosso.");
            }
        }
        log.info("Dimensione lista dopo la rimozione: " 
                + listaPathFileStego.size());
        /*for(int i = 0; i < listaPathFileStego.size(); i++){
            String s = listaPathFileStego.get(i);
            log.info("Stringa estratta dopo la rimozione: " + s);
        }*/
    }
    
}