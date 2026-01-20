package Vistas.Coordinacion;

import Controladores.Ctrl_Estudiantes;
import Controladores.EMFProvider;
import Modelos.Estudiante;
import java.util.List;
import javax.persistence.*;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JDialog_Estudiantes extends javax.swing.JDialog {

    private EntityManagerFactory emf;
    private Ctrl_Estudiantes ctrl_estudiantes;
    private DefaultTableModel dtm_estudiantes;
    private List<Estudiante> listaEstudiantes;
    private int idEstudiante;

    public JDialog_Estudiantes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Gestión de Estudiantes");
        
        try {
            emf = EMFProvider.getEmf();
            // Ya estás seguro de que funciona
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // o redirigir al login
        }

        ctrl_estudiantes = new Ctrl_Estudiantes(emf);

        dtm_estudiantes = (DefaultTableModel) table_estudiantes.getModel();
        table_estudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        rellenarTablaEstudiantes();
    }

    //RELLENAR TABLA ESTUDIANTES
    public void rellenarTablaEstudiantes() {
        dtm_estudiantes.setRowCount(0);
        listaEstudiantes = ctrl_estudiantes.listarEstudiantes();
        for (Estudiante e : listaEstudiantes) {
            String codTFG = (e.getTfg() != null) ? e.getTfg().getCodTFG() : "Sin asignar";

            String codTribunal = "Sin asignar";
            if (e.getTfg() != null && e.getTfg().getTribunal() != null && e.getTfg().getTribunal().getCodTribunal() != null) {
                codTribunal = e.getTfg().getTribunal().getCodTribunal();
            }

            String codTutor = "Sin asignar";
            if (e.getTfg() != null && e.getTfg().getTutor() != null && e.getTfg().getTutor().getCodProfesor() != null) {
                codTutor = e.getTfg().getTutor().getCodProfesor();
            }
            dtm_estudiantes.addRow(new Object[]{
                e.getCodEstudiante(),
                e.getNombre(),
                e.getApellidos(),
                e.getEmail(),
                codTFG,
                e.getSeminario(),
                e.getCalificacionTutor(),
                e.getCalificacionTribunal(),
                codTutor,
                codTribunal
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_estudiantes = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        bt_asignarTFG = new javax.swing.JMenu();
        bt_eliminarTFG = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("LISTADO DE ESTUDIANTES:");

        table_estudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Apellidos", "Email", "CodTFG", "Seminario", "Cal. Tutor", "Cal. Tribunal", "Profesor", "Tribunal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_estudiantes);
        if (table_estudiantes.getColumnModel().getColumnCount() > 0) {
            table_estudiantes.getColumnModel().getColumn(0).setMinWidth(70);
            table_estudiantes.getColumnModel().getColumn(0).setMaxWidth(70);
            table_estudiantes.getColumnModel().getColumn(1).setResizable(false);
            table_estudiantes.getColumnModel().getColumn(2).setResizable(false);
            table_estudiantes.getColumnModel().getColumn(3).setResizable(false);
            table_estudiantes.getColumnModel().getColumn(4).setMinWidth(100);
            table_estudiantes.getColumnModel().getColumn(4).setMaxWidth(100);
            table_estudiantes.getColumnModel().getColumn(5).setMinWidth(70);
            table_estudiantes.getColumnModel().getColumn(5).setMaxWidth(70);
            table_estudiantes.getColumnModel().getColumn(6).setMinWidth(80);
            table_estudiantes.getColumnModel().getColumn(6).setMaxWidth(80);
            table_estudiantes.getColumnModel().getColumn(7).setMinWidth(80);
            table_estudiantes.getColumnModel().getColumn(7).setMaxWidth(80);
            table_estudiantes.getColumnModel().getColumn(8).setMinWidth(100);
            table_estudiantes.getColumnModel().getColumn(8).setMaxWidth(100);
            table_estudiantes.getColumnModel().getColumn(9).setMinWidth(100);
            table_estudiantes.getColumnModel().getColumn(9).setMaxWidth(100);
            table_estudiantes.getColumnModel().getColumn(9).setHeaderValue("Tribunal");
        }

        bt_asignarTFG.setText("   ASIGNAR TFG A ESTUDIANTE   ");
        bt_asignarTFG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_asignarTFGMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_asignarTFG);

        bt_eliminarTFG.setText("   ELIMINAR TFG A ESTUDIANTE   ");
        bt_eliminarTFG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_eliminarTFGMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_eliminarTFG);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel1)
                .addGap(65, 65, 65)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //ASIGNAR TFG A ESTUDIANTE SELECCIONADO
    private void bt_asignarTFGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_asignarTFGMouseClicked

        int estudianteSeleccionado = table_estudiantes.getSelectedRow();
        if (estudianteSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Para asignar un TFG a un ESTUDIANTE, deberas seleccionar el ESTUDIANTE primero");
        } else {
            idEstudiante = (int) dtm_estudiantes.getValueAt(estudianteSeleccionado, 0);
            JDialog_AsignarTFG jd_asignarTFG = new JDialog_AsignarTFG(this, true, ctrl_estudiantes.listarEstudianteCodigo(idEstudiante));
            jd_asignarTFG.setVisible(true);

            // Escuchar el cierre del JDialog
            jd_asignarTFG.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    rellenarTablaEstudiantes();
                }
            });

        }

    }//GEN-LAST:event_bt_asignarTFGMouseClicked

    private void bt_eliminarTFGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_eliminarTFGMouseClicked

        int estudianteSeleccionado = table_estudiantes.getSelectedRow();
        if (estudianteSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Para quitar el TFG a un ESTUDIANTE, deberás seleccionar el ESTUDIANTE primero");
        } else {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que quieres eliminar el TFG asignado a este estudiante?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                idEstudiante = (int) dtm_estudiantes.getValueAt(estudianteSeleccionado, 0);
                int resultado = ctrl_estudiantes.eliminarTFG(idEstudiante);
                if (resultado == 1) {
                    JOptionPane.showMessageDialog(this, "TFG quitado del estudiante con éxito");
                    rellenarTablaEstudiantes();
                } else {
                    JOptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar eliminar el TFG del estudiante");
                }
            }
        }


    }//GEN-LAST:event_bt_eliminarTFGMouseClicked

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
            java.util.logging.Logger.getLogger(JDialog_Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_Estudiantes dialog = new JDialog_Estudiantes(new javax.swing.JFrame(), true);
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
    private javax.swing.JMenu bt_asignarTFG;
    private javax.swing.JMenu bt_eliminarTFG;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_estudiantes;
    // End of variables declaration//GEN-END:variables
}
