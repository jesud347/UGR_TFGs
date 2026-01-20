package Vistas.Coordinacion;

import Controladores.Ctrl_ProfesorTribunal;
import Controladores.Ctrl_Profesores;
import Controladores.Ctrl_Tribunales;
import Controladores.EMFProvider;
import Controladores.ExportadorExcelTribunales;
import java.awt.Dialog;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JDialog_Suplentes extends javax.swing.JDialog {

    private EntityManagerFactory emf;
    private Ctrl_Profesores ctrl_profesores;
    private Ctrl_Tribunales ctrl_tribunales;
    private Ctrl_ProfesorTribunal ctrl_pt;
    private DefaultTableModel dtm_pt;
    private List<Object[]> listapt;
    private ExportadorExcelTribunales exportador;

    public JDialog_Suplentes(Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Ver Suplentes");
        
        try {
            emf = EMFProvider.getEmf();
            // Ya estás seguro de que funciona
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // o redirigir al login
        }

        ctrl_profesores = new Ctrl_Profesores(emf);
        ctrl_tribunales = new Ctrl_Tribunales(emf);
        ctrl_pt = new Ctrl_ProfesorTribunal(emf);

        dtm_pt = (DefaultTableModel) table_suplentes.getModel();
        rellenarComboboxCursos();
    }

    //RELLENAR TABLA SUPLENTES POR CURSO
    public void rellenarTablaSuplentes(String curso) {
        dtm_pt.setRowCount(0);
        listapt = ctrl_pt.listarProfesoresSuplentesCurso(curso);
        for (Object[] fila : listapt) {
            dtm_pt.addRow(fila);
        }
    }

    //RELLENAR COMBOBOX CURSOS
    public void rellenarComboboxCursos() {
        combobox_cursos.removeAllItems();
        List<String> cursos = ctrl_tribunales.sacarCursos();
        for (String c : cursos) {
            combobox_cursos.addItem(c);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_suplentes = new javax.swing.JTable();
        bt_buscar = new javax.swing.JButton();
        txt_contador = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        combobox_cursos = new javax.swing.JComboBox<>();
        txt_contador1 = new javax.swing.JLabel();

        table_suplentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tribunal", "Curso", "CodProfesor", "Nombre", "Departamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_suplentes);
        if (table_suplentes.getColumnModel().getColumnCount() > 0) {
            table_suplentes.getColumnModel().getColumn(0).setMinWidth(80);
            table_suplentes.getColumnModel().getColumn(0).setMaxWidth(80);
            table_suplentes.getColumnModel().getColumn(1).setMinWidth(80);
            table_suplentes.getColumnModel().getColumn(1).setMaxWidth(80);
            table_suplentes.getColumnModel().getColumn(2).setMinWidth(80);
            table_suplentes.getColumnModel().getColumn(2).setMaxWidth(80);
        }

        bt_buscar.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        bt_buscar.setText("BUSCAR");
        bt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_buscarActionPerformed(evt);
            }
        });

        txt_contador.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_contador.setText("x");

        jLabel2.setFont(new java.awt.Font("Dialog", 3, 36)); // NOI18N
        jLabel2.setText("BÚSQUEDA DE SUPLENTES");

        jLabel3.setFont(new java.awt.Font("Dialog", 2, 18)); // NOI18N
        jLabel3.setText("Selecciona un curso:");

        combobox_cursos.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        txt_contador1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_contador1.setText("CONTADOR:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(txt_contador1)
                                .addGap(18, 18, 18)
                                .addComponent(txt_contador, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combobox_cursos, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(294, 294, 294))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_buscar)
                    .addComponent(combobox_cursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_contador)
                    .addComponent(txt_contador1))
                .addGap(15, 15, 15))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //BOTON BUSCAR POR CURSO
    private void bt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_buscarActionPerformed
        String curso = (String) combobox_cursos.getSelectedItem();

        rellenarTablaSuplentes(curso);
        int contador = table_suplentes.getRowCount();
        txt_contador.setText(String.valueOf(contador));
    }//GEN-LAST:event_bt_buscarActionPerformed

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(JDialog_Suplentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_Suplentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_Suplentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_Suplentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_Suplentes dialog = new JDialog_Suplentes(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton bt_buscar;
    private javax.swing.JComboBox<String> combobox_cursos;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_suplentes;
    private javax.swing.JLabel txt_contador;
    private javax.swing.JLabel txt_contador1;
    // End of variables declaration//GEN-END:variables

}
