/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganografiasupdf.extraction;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import steganografiasupdf.SchermataPrincipale;

/**
 *
 * @author Rocchina
 */
public class SchermataEstrazione extends javax.swing.JPanel {
    
    private final SchermataPrincipale sPrincipale;
    private final Log log = LogFactory.getLog(SchermataEstrazione.class);
    
    /**
     * Creates new form SchermataEstrazione
     * @param sPrincipale
     */
    public SchermataEstrazione(SchermataPrincipale sPrincipale) {
        this.sPrincipale = sPrincipale;
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

        jTextFieldPdfSelezionato = new javax.swing.JTextField();
        jButtonApriPDF = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaMessEstratto = new javax.swing.JTextArea();
        jButtonEstraiMessaggio = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jTextFieldPdfSelezionato.setForeground(new java.awt.Color(51, 51, 255));
        jTextFieldPdfSelezionato.setText("Seleziona PDF");

        jButtonApriPDF.setText("Apri");

        jTextAreaMessEstratto.setColumns(20);
        jTextAreaMessEstratto.setForeground(new java.awt.Color(255, 0, 51));
        jTextAreaMessEstratto.setRows(5);
        jScrollPane1.setViewportView(jTextAreaMessEstratto);

        jButtonEstraiMessaggio.setText("Estrai Messaggio");

        jLabel1.setText("Messaggio estratto:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jButtonEstraiMessaggio)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldPdfSelezionato, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(jButtonApriPDF)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPdfSelezionato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonApriPDF))
                .addGap(26, 26, 26)
                .addComponent(jButtonEstraiMessaggio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApriPDF;
    private javax.swing.JButton jButtonEstraiMessaggio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaMessEstratto;
    private javax.swing.JTextField jTextFieldPdfSelezionato;
    // End of variables declaration//GEN-END:variables

    private void postInizializza() {
        log.info("In postInizializza");
        this.jButtonApriPDF.setAction(this.sPrincipale.getSteganografia().geteRestrazione());
        this.jButtonEstraiMessaggio.setAction(this.sPrincipale.getSteganografia().geteMessaggio());
    }

    public void mostraPath(String pathFile) {
        this.jTextFieldPdfSelezionato.setText(pathFile);
    }

    public void mostraMessaggio(List<MessEstrattoPerPagina> listaMessEstratti) {
        this.jTextAreaMessEstratto.setText("");
        String messDaMostrare = "";
        for(int i = 0; i < listaMessEstratti.size(); i++){
            int pag = listaMessEstratti.get(i).getNumPagina();
            String mess = listaMessEstratti.get(i).getMessEstratto();
            messDaMostrare = messDaMostrare + "Pagina " + pag +
                    "\nMessaggio Estratto: " + mess + "\n" + "\n";
        }
        log.info("MESSAGGIO DA MOSTRARE: " + messDaMostrare);
        this.jTextAreaMessEstratto.setText(messDaMostrare);
    }

    public void mostraMessaggio(String messEstratto) {
        this.jTextAreaMessEstratto.setText(messEstratto);
    }
}
