package Vistas.Coordinacion;

import Controladores.Ctrl_ProfesorTribunal;
import Controladores.Ctrl_Tfgs;
import Controladores.Ctrl_Tribunales;
import Controladores.EMFProvider;
import Modelos.Tfg;
import java.awt.Dialog;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JDialog_AsignarTribunal extends javax.swing.JDialog {

    private Tfg tfg;
    private EntityManagerFactory emf;
    private Ctrl_Tfgs ctrl_tfg;
    private Ctrl_Tribunales ctrl_tribunales;
    private Ctrl_ProfesorTribunal ctrl_pt;

    private DefaultTableModel dtm_tribunales;
    private List<Object[]> listapt;
    private String codTribunal;

    public JDialog_AsignarTribunal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //NO SE USA
    }

    public JDialog_AsignarTribunal(Dialog parent, boolean modal, Tfg tfg_seleccionado) {
        super(parent, modal);
        initComponents();
        
        setTitle("Asignar Tribunal");
        
        try {
            emf = EMFProvider.getEmf();
            // Ya estás seguro de que funciona
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // o redirigir al login
        }
        this.tfg = tfg_seleccionado;

        ctrl_tfg = new Ctrl_Tfgs(emf);
        ctrl_tribunales = new Ctrl_Tribunales(emf);
        ctrl_pt = new Ctrl_ProfesorTribunal(emf);

        dtm_tribunales = (DefaultTableModel) table_tribunales.getModel();
        table_tribunales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        txt_codTfg.setText(txt_codTfg.getText() + " " + tfg.getCodTFG());
        txt_tutor.setText(txt_tutor.getText() + " " + tfg.getTutor());
        txt_titulo.setText(txt_titulo.getText() + " " + tfg.getTitulo());

        rellenarComboboxCursos();
        rellenarTablaTribunalesCurso((String) combobox_cursos.getSelectedItem());
    }

    //RELLENAR COMBOBOX CURSOS
    public void rellenarComboboxCursos() {
        combobox_cursos.removeAllItems();
        List<String> cursos = ctrl_tribunales.sacarCursos();
        for (String c : cursos) {
            combobox_cursos.addItem(c);
        }
    }

    //RELLENAR TABLA TRIBUNALES POR CURSO
    public void rellenarTablaTribunalesCurso(String curso) {
        dtm_tribunales.setRowCount(0);
        listapt = ctrl_pt.listarProfesores123TribunalesCurso(curso);
        for (Object[] fila : listapt) {
            dtm_tribunales.addRow(fila);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_tribunalSeleccionado = new javax.swing.JLabel();
        txt_codTfg = new javax.swing.JLabel();
        txt_tutor = new javax.swing.JLabel();
        txt_titulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_tribunales = new javax.swing.JTable();
        combobox_cursos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        bt_asignarTFG = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("ASIGNAR TRIBUNAL AL TFG SELECCIONADO");

        txt_tribunalSeleccionado.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        txt_tribunalSeleccionado.setText("VAS A ASIGNARLE UN TRIBUNAL AL TFG:");

        txt_codTfg.setText("COD TFG: ");

        txt_tutor.setText("TUTOR TFG: ");

        txt_titulo.setText("TÍTULO: ");

        table_tribunales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tribunal", "Curso", "Profesor1", "Profesor2", "Profesor3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_tribunales);
        if (table_tribunales.getColumnModel().getColumnCount() > 0) {
            table_tribunales.getColumnModel().getColumn(0).setMinWidth(100);
            table_tribunales.getColumnModel().getColumn(0).setMaxWidth(100);
            table_tribunales.getColumnModel().getColumn(1).setMinWidth(50);
            table_tribunales.getColumnModel().getColumn(1).setMaxWidth(50);
            table_tribunales.getColumnModel().getColumn(2).setResizable(false);
            table_tribunales.getColumnModel().getColumn(3).setResizable(false);
            table_tribunales.getColumnModel().getColumn(4).setResizable(false);
        }

        combobox_cursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox_cursosActionPerformed(evt);
            }
        });

        jLabel2.setText("Selecciona un curso para ver sus tribunales:");

        bt_asignarTFG.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        bt_asignarTFG.setText("ASIGNAR TFG");
        bt_asignarTFG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_asignarTFGActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tribunalSeleccionado)
                    .addComponent(txt_codTfg, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tutor, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jLabel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(combobox_cursos, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(358, 358, 358))
                                .addComponent(txt_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_asignarTFG, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addComponent(txt_tribunalSeleccionado)
                .addGap(18, 18, 18)
                .addComponent(txt_codTfg)
                .addGap(18, 18, 18)
                .addComponent(txt_tutor)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_titulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combobox_cursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bt_asignarTFG, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //ACTION DEL COMBOBOX
    private void combobox_cursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox_cursosActionPerformed
        rellenarTablaTribunalesCurso((String) combobox_cursos.getSelectedItem());
    }//GEN-LAST:event_combobox_cursosActionPerformed

    //BOTON ASIGNAR TFG A TRIBUNAL 
    private void bt_asignarTFGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_asignarTFGActionPerformed

        int tribunalSeleccionado = table_tribunales.getSelectedRow();
        if (tribunalSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Para asignar un tribunal a un tfg, deberas seleccionar el Tribunal");
        } else {
            codTribunal = (String) dtm_tribunales.getValueAt(tribunalSeleccionado, 0);
            int resultado = ctrl_tfg.asignarTribunalTfg(tfg.getCodTFG(), codTribunal);
            if (resultado != 1) {
                JOptionPane.showMessageDialog(this, "ERROR");
            }else{
                JOptionPane.showMessageDialog(this, "Tribunal asignado a TFG con exito.");
            }
        }

    }//GEN-LAST:event_bt_asignarTFGActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialog_AsignarTribunal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_AsignarTribunal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_AsignarTribunal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_AsignarTribunal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_AsignarTribunal dialog = new JDialog_AsignarTribunal(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_asignarTFG;
    private javax.swing.JComboBox<String> combobox_cursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_tribunales;
    private javax.swing.JLabel txt_codTfg;
    private javax.swing.JLabel txt_titulo;
    private javax.swing.JLabel txt_tribunalSeleccionado;
    private javax.swing.JLabel txt_tutor;
    // End of variables declaration//GEN-END:variables
}
