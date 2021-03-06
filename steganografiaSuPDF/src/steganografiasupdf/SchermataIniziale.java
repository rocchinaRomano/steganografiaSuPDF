/*
 *  Steganografia su PDF is a software developed for embedding and extracting 
 *  a secret message within PDF files.
 *  This software is property of Rocchina Romano.
 *  mail: rocchina.romano@yahoo.it
 *
 *	Copyright Rocchina Romano 2020
 *
 *  class SchermataIniziale
 */
package steganografiasupdf;

/**
 *
 * @author Rocchina
 */
public class SchermataIniziale extends javax.swing.JPanel {

    private final SchermataPrincipale sp;
    
    /**
     * Creates new form SchermataIniziale
     * @param sp
     */
    
    public SchermataIniziale(SchermataPrincipale sp) {
        this.sp = sp;
        initComponents();
        //per disabilitare la possibilità di scrivere nella TextArea:
        this.jTextArea1.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(0, 51, 204));
        jTextArea1.setRows(5);
        jTextArea1.setText("La seguente app consente di effettuare l'inserimento e l'estrazione \ndi un messaggio segreto in/da un file \"PDF\".\n\n1. Per inserire un messaggio segreto nel PDF, fare click su\n\n-> \"File\"\n-> \"Inserisci Messaggio\"\n\n2. Per estrarre un messaggio segreto dal PDF, fare click su\n\n->  \"File\"\n-> \"Estrai Messaggio\"");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
