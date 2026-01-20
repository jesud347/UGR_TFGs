package Vistas.Coordinacion;

import Controladores.Ctrl_Tfgs;
import Controladores.EMFProvider;
import Modelos.Tfg;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JDialog_TFGS extends javax.swing.JDialog {

    private EntityManagerFactory emf;
    private Ctrl_Tfgs ctrl_tfg;
    private DefaultTableModel dtm_tfgs;
    private List<Tfg> listaTfgs;
    private String codTFG;

    public JDialog_TFGS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Gestión de TFGs");
        
        try {
            emf = EMFProvider.getEmf();
            // Ya estás seguro de que funciona
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // o redirigir al login
        }

        ctrl_tfg = new Ctrl_Tfgs(emf);

        dtm_tfgs = (DefaultTableModel) table_tfgs.getModel();
        table_tfgs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        rellenarTablaTfgs();
    }

    //RELLENAR TABLA TFGS
    public void rellenarTablaTfgs() {
        dtm_tfgs.setRowCount(0);
        listaTfgs = ctrl_tfg.listarTfgs();
        for (Tfg tfg : listaTfgs) {
            String codTribunal = (tfg.getTribunal() != null) ? tfg.getTribunal().getCodTribunal() : "";

            dtm_tfgs.addRow(new Object[]{
                tfg.getTutor(), // Aquí también podrías comprobar si es null si lo necesitas
                tfg.getCodTFG(),
                tfg.getTitulo(),
                tfg.getProfesor2(),
                codTribunal,
                tfg.getEspecial()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_tfgs = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        bt_asignarTribunal = new javax.swing.JMenu();
        bt_quitarTribunal = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setText("GESTIÓN DE TFGS");

        table_tfgs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tutor", "CodTFG", "Titulo", "Profesor 2", "Tribunal", "Especial"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_tfgs);
        if (table_tfgs.getColumnModel().getColumnCount() > 0) {
            table_tfgs.getColumnModel().getColumn(0).setMinWidth(300);
            table_tfgs.getColumnModel().getColumn(0).setMaxWidth(300);
            table_tfgs.getColumnModel().getColumn(1).setMinWidth(70);
            table_tfgs.getColumnModel().getColumn(1).setMaxWidth(70);
            table_tfgs.getColumnModel().getColumn(3).setMinWidth(150);
            table_tfgs.getColumnModel().getColumn(3).setMaxWidth(150);
            table_tfgs.getColumnModel().getColumn(4).setMinWidth(100);
            table_tfgs.getColumnModel().getColumn(4).setMaxWidth(100);
            table_tfgs.getColumnModel().getColumn(5).setMinWidth(60);
            table_tfgs.getColumnModel().getColumn(5).setMaxWidth(60);
        }

        bt_asignarTribunal.setText("   ASIGNAR TRIBUNAL AL TFG   ");
        bt_asignarTribunal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_asignarTribunalMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_asignarTribunal);

        bt_quitarTribunal.setText("   ELIMINAR TRIBUNAL DEL TFG   ");
        bt_quitarTribunal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_quitarTribunalMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_quitarTribunal);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //BOTON ASIGNAR TRIBUNAL AL TFG SELECCIONADO
    private void bt_asignarTribunalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_asignarTribunalMouseClicked

        int tfgSeleccionado = table_tfgs.getSelectedRow();
        if (tfgSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Para asignar un TRIBUNAL a un TFG, deberas seleccionar el TFG primero");
        } else {
            codTFG = (String) dtm_tfgs.getValueAt(tfgSeleccionado, 1);
            JDialog_AsignarTribunal jd_asignarTribunal = new JDialog_AsignarTribunal(this, true, ctrl_tfg.buscarTfg(codTFG));
            jd_asignarTribunal.setVisible(true);

            // Escuchar el cierre del JDialog
            jd_asignarTribunal.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    rellenarTablaTfgs();
                }
            });

        }


    }//GEN-LAST:event_bt_asignarTribunalMouseClicked

    //BOTON QUITAR TRIBUNAL AL TFG SELECCIONADO
    private void bt_quitarTribunalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_quitarTribunalMouseClicked

        int tfgSeleccionado = table_tfgs.getSelectedRow();
        if (tfgSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Para quitar el TRIBUNAL a un TFG, deberas seleccionar el TFG primero");
        } else {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que quieres eliminar el TRIBUNAL asignado a este TFG?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                codTFG = (String) dtm_tfgs.getValueAt(tfgSeleccionado, 1);
                int resultado = ctrl_tfg.quitarTribunalTFG(codTFG);
                if (resultado == 1) {
                    JOptionPane.showMessageDialog(this, "TRIBUNAL quitado del TFG con éxito");
                    rellenarTablaTfgs();
                } else {
                    JOptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar eliminar el TRIBUNAL del TFG");
                }
            }
        }

    }//GEN-LAST:event_bt_quitarTribunalMouseClicked

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
            java.util.logging.Logger.getLogger(JDialog_TFGS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_TFGS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_TFGS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_TFGS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_TFGS dialog = new JDialog_TFGS(new javax.swing.JFrame(), true);
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
    private javax.swing.JMenu bt_asignarTribunal;
    private javax.swing.JMenu bt_quitarTribunal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_tfgs;
    // End of variables declaration//GEN-END:variables
}
