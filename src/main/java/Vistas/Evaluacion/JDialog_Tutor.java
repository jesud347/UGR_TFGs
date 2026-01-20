package Vistas.Evaluacion;

import Controladores.Ctrl_Estudiantes;
import Controladores.EMFProvider;
import Modelos.Estudiante;
import Modelos.SesionUsuario;
import Modelos.Usuario;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JDialog_Tutor extends javax.swing.JDialog {

    Usuario usuarioActual = SesionUsuario.getInstancia().getUsuario();
    private EntityManagerFactory emf;
    private DefaultTableModel dtm_tablaAlumnosTFG;
    private Ctrl_Estudiantes ctrl_estudiantes;
    private List<Estudiante> listaEstudiantes;

    public JDialog_Tutor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        setTitle("Evaluación como Tutor/a");
        
        try {
            emf = EMFProvider.getEmf();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); 
        }



        ctrl_estudiantes = new Ctrl_Estudiantes(emf);

        txt_nombreProfesor.setText(usuarioActual.toString());

        dtm_tablaAlumnosTFG = (DefaultTableModel) table_estudiantesTFGS.getModel();
        table_estudiantesTFGS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        rellenarTabla();
    }

    //RELLENAR TABLA ESTUDIANTES
    public void rellenarTabla() {
        dtm_tablaAlumnosTFG.setRowCount(0);
        listaEstudiantes = ctrl_estudiantes.obtenerEstudiantesPorTutor(usuarioActual.getProfesor().getCodProfesor());
        for (Estudiante e : listaEstudiantes) {
            dtm_tablaAlumnosTFG.addRow(new Object[]{
                e.getCodEstudiante(),
                e.getNombre(),
                e.getApellidos(),
                e.getTfg().getCodTFG(),
                e.getCalificacionTutor()
            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_nombreProfesor = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_estudiantesTFGS = new javax.swing.JTable();
        bt_evaluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("EVALUACIÓN DE TFGS COMO TUTOR/A");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        jLabel2.setText("Lista de Alumnos que tutoriza el profesor/a: ");

        txt_nombreProfesor.setFont(new java.awt.Font("Helvetica Neue", 2, 18)); // NOI18N
        txt_nombreProfesor.setText("x");

        table_estudiantesTFGS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Apellidos", "CodTFG", "NotaTutor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_estudiantesTFGS);
        if (table_estudiantesTFGS.getColumnModel().getColumnCount() > 0) {
            table_estudiantesTFGS.getColumnModel().getColumn(0).setMinWidth(50);
            table_estudiantesTFGS.getColumnModel().getColumn(0).setMaxWidth(50);
            table_estudiantesTFGS.getColumnModel().getColumn(3).setMinWidth(70);
            table_estudiantesTFGS.getColumnModel().getColumn(3).setMaxWidth(70);
            table_estudiantesTFGS.getColumnModel().getColumn(4).setMinWidth(80);
            table_estudiantesTFGS.getColumnModel().getColumn(4).setMaxWidth(80);
        }

        bt_evaluar.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        bt_evaluar.setText("EVALUAR ALUMNO SELECCIONADO");
        bt_evaluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_evaluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txt_nombreProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(bt_evaluar)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_nombreProfesor))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(bt_evaluar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //EVALUAR ALUMNO SELECCIONADO
    private void bt_evaluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_evaluarActionPerformed

        int alumnoSeleccionado = table_estudiantesTFGS.getSelectedRow();
        if (alumnoSeleccionado != -1) {
            int idEstudiante = (int) dtm_tablaAlumnosTFG.getValueAt(alumnoSeleccionado, 0);
            JDialog_EvaluacionTutor jd_evalTutor = new JDialog_EvaluacionTutor(this, true, ctrl_estudiantes.listarEstudianteCodigo(idEstudiante));

            jd_evalTutor.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    rellenarTabla(); // Vuelve a cargar los datos al cerrarse el JDialog
                }
            });

            jd_evalTutor.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un alumno para evaluarlo.");
        }

    }//GEN-LAST:event_bt_evaluarActionPerformed

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
            java.util.logging.Logger.getLogger(JDialog_Tutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_Tutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_Tutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_Tutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_Tutor dialog = new JDialog_Tutor(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bt_evaluar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_estudiantesTFGS;
    private javax.swing.JLabel txt_nombreProfesor;
    // End of variables declaration//GEN-END:variables
}
