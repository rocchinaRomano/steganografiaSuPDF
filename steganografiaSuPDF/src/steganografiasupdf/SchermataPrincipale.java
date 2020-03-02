/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.embedding.SchermataEmbedding;
import steganografiasupdf.extraction.SchermataEstrazione;


/**
 *
 * @author Rocchina
 */
public class SchermataPrincipale extends javax.swing.JFrame {

    /**
     * Creates new form SchermataPrincipale
     */
    
    private final SteganografiaSuPDF steganografia;
    private final Log log = LogFactory.getLog(SchermataPrincipale.class);
    
    //SCHERMATE;
    private SchermataIniziale si;
    private SchermataEmbedding sEmbedding;
    private SchermataEstrazione sEstrazione;
    private JFileChooser jfc;
    
    public SchermataPrincipale(SteganografiaSuPDF steganografia) {
        this.steganografia = steganografia;
        initComponents();
        postInizializza();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemInserisciMess = new javax.swing.JMenuItem();
        jMenuItemEstraiMessaggio = new javax.swing.JMenuItem();
        jMenuItemInfo = new javax.swing.JMenuItem();
        jMenuItemEsci = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");

        jMenuItemInserisciMess.setText("Inserisci messaggio");
        jMenu1.add(jMenuItemInserisciMess);

        jMenuItemEstraiMessaggio.setText("Estrai Messaggio");
        jMenu1.add(jMenuItemEstraiMessaggio);

        jMenuItemInfo.setText("Info");
        jMenu1.add(jMenuItemInfo);

        jMenuItemEsci.setText("Esci");
        jMenu1.add(jMenuItemEsci);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemEsci;
    private javax.swing.JMenuItem jMenuItemEstraiMessaggio;
    private javax.swing.JMenuItem jMenuItemInfo;
    private javax.swing.JMenuItem jMenuItemInserisciMess;
    // End of variables declaration//GEN-END:variables

    private void postInizializza() {
        //inizializza componenti dello schermo principale
        //aggiungu Schermata iniziale al frame
        //aggiungere le schermate nella mappa
        aggiungiSchermataIniziale();
        aggiungiAzioni();
    }

    private void aggiungiSchermataIniziale() {
        this.setTitle("Steganografia su PDF");
        this.si = new SchermataIniziale(this);
        JScrollPane jsp = new JScrollPane(this.si);
        this.setContentPane(jsp);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void aggiungiAzioni() {
        this.jMenuItemEsci.setAction(this.getSteganografia().getEsci());
        this.jMenuItemInfo.setAction(this.getSteganografia().getInfo());
        this.jMenuItemInserisciMess.setAction(this.getSteganografia().getSe());
        this.jMenuItemEstraiMessaggio.setAction(this.steganografia.getsEstrazione());
    }

    public void finestraInfo(String info){
        JOptionPane.showMessageDialog(rootPane, info, "INFORMAZIONE", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void finestraErrore(String errore){
        JOptionPane.showMessageDialog(rootPane, errore, "ERRORE", JOptionPane.ERROR_MESSAGE);
    }
    
    public void schermataEmbedding(){
        this.jMenuItemEstraiMessaggio.setEnabled(false);
        this.sEmbedding = new SchermataEmbedding(this);
        JScrollPane js = new JScrollPane(this.getsEmbedding());
        this.getContentPane().remove(this.si);
        this.setContentPane(js);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

     
    public int pdfSElezionato(){
        log.info("nel metodo pdfSelezionato:");
        this.jfc = new JFileChooser();
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("PDF", "pdf");
        this.jfc.setFileFilter(fnef);
        return this.jfc.showOpenDialog(this);
    }
    
    public String getNomePdf(){
        return this.jfc.getSelectedFile().getName();
    }
    
    public String getPathPdf(){
        return this.jfc.getSelectedFile().getAbsolutePath();
    }
    
    /**
     * @return the steganografia
     */
    public SteganografiaSuPDF getSteganografia() {
        return steganografia;
    }

    /**
     * @return the sEmbedding
     */
    public SchermataEmbedding getsEmbedding() {
        return sEmbedding;
    }

    /**
     * @return the sEstrazione
     */
    public SchermataEstrazione getsEstrazione() {
        return sEstrazione;
    }

    public void schermataEstrazione() {
        this.jMenuItemInserisciMess.setEnabled(false);
        this.sEstrazione = new SchermataEstrazione(this);
        JScrollPane js = new JScrollPane(this.sEstrazione);
        this.getContentPane().remove(this.si);
        this.setContentPane(js);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}