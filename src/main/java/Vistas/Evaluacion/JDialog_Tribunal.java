package Vistas.Evaluacion;

import Controladores.Ctrl_Estudiantes;
import Controladores.Ctrl_ProfesorTribunal;
import Controladores.Ctrl_Tribunales;
import Controladores.EMFProvider;
import Controladores.GeneradorDocumentosTFG;
import Modelos.Estudiante;
import Modelos.SesionUsuario;
import Modelos.Tribunal;
import Modelos.Usuario;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManagerFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class JDialog_Tribunal extends javax.swing.JDialog {

    Usuario usuarioActual = SesionUsuario.getInstancia().getUsuario();
    private EntityManagerFactory emf;
    private DefaultTableModel dtm_tablaAlumnosTFG;
    private Ctrl_Estudiantes ctrl_estudiantes;
    private Ctrl_Tribunales ctrl_tribunales;
    private Ctrl_ProfesorTribunal ctrl_pt;
    private List<Estudiante> listaEstudiantes;
    private Tribunal tribunal;
    private Estudiante e;

    public JDialog_Tribunal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JDialog_Tribunal(java.awt.Frame parent, boolean modal, String curso) {
        super(parent, modal);
        initComponents();

        setTitle("Evaluación como Tribunal");

        try {
            emf = EMFProvider.getEmf();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); 
        }



        ctrl_estudiantes = new Ctrl_Estudiantes(emf);
        ctrl_tribunales = new Ctrl_Tribunales(emf);
        ctrl_pt = new Ctrl_ProfesorTribunal(emf);

        tribunal = ctrl_tribunales.tribunalDeUnProfesorEnCurso(usuarioActual.getProfesor().getCodProfesor(), curso);
        txt_tribunal.setText(tribunal.toString() + " - " + usuarioActual.getProfesor().getNombre());
        txt_rol.setText(txt_rol.getText() + ctrl_pt.sacarRolProfesor(tribunal.getCodTribunal(), usuarioActual.getProfesor().getCodProfesor()));

        dtm_tablaAlumnosTFG = (DefaultTableModel) table_estudiantesTFGS.getModel();
        table_estudiantesTFGS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        rellenarTabla();
    }

    //RELLENAR TABLA ESTUDIANTES
    public void rellenarTabla() {
        dtm_tablaAlumnosTFG.setRowCount(0);
        listaEstudiantes = ctrl_estudiantes.obtenerEstudiantesPorTribunal(tribunal.getCodTribunal());
        for (Estudiante e : listaEstudiantes) {
            dtm_tablaAlumnosTFG.addRow(new Object[]{
                e.getCodEstudiante(),
                e.getNombre(),
                e.getApellidos(),
                e.getTfg().getCodTFG(),
                e.getCalificacionTutor(),
                e.getCalificacionTribunal()
            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_tribunal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_estudiantesTFGS = new javax.swing.JTable();
        bt_evaluar = new javax.swing.JButton();
        txt_rol = new javax.swing.JLabel();
        bt_generarWord = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("EVALUACIÓN DE TFGS COMO TRIBUNAL:");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        jLabel2.setText("Lista de Alumnos que evalúan el tribunal: ");

        txt_tribunal.setFont(new java.awt.Font("Helvetica Neue", 3, 18)); // NOI18N
        txt_tribunal.setText("x");

        table_estudiantesTFGS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Apellidos", "CodTFG", "NotaTutor", "NotaTribunal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table_estudiantesTFGS);
        if (table_estudiantesTFGS.getColumnModel().getColumnCount() > 0) {
            table_estudiantesTFGS.getColumnModel().getColumn(0).setMinWidth(50);
            table_estudiantesTFGS.getColumnModel().getColumn(0).setMaxWidth(50);
            table_estudiantesTFGS.getColumnModel().getColumn(3).setMinWidth(60);
            table_estudiantesTFGS.getColumnModel().getColumn(3).setMaxWidth(60);
            table_estudiantesTFGS.getColumnModel().getColumn(4).setMinWidth(80);
            table_estudiantesTFGS.getColumnModel().getColumn(4).setMaxWidth(80);
            table_estudiantesTFGS.getColumnModel().getColumn(5).setMinWidth(80);
            table_estudiantesTFGS.getColumnModel().getColumn(5).setMaxWidth(80);
        }

        bt_evaluar.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        bt_evaluar.setText("EVALUAR ESTUDIANTE SELECCIONADO");
        bt_evaluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_evaluarActionPerformed(evt);
            }
        });

        txt_rol.setText("ROL: ");

        bt_generarWord.setText("CERTIFICADO TFG");
        bt_generarWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_generarWordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_rol, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(bt_evaluar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bt_generarWord))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(221, 221, 221))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(txt_tribunal, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(29, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_tribunal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_rol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_evaluar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_generarWord, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(152, 152, 152)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(152, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bt_evaluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_evaluarActionPerformed

        int alumnoSeleccionado = table_estudiantesTFGS.getSelectedRow();
        if (alumnoSeleccionado != -1) {
            if (ctrl_pt.sacarRolProfesor(tribunal.getCodTribunal(), usuarioActual.getProfesor().getCodProfesor()) != "Presidente") {
                JOptionPane.showMessageDialog(this, "Debes ser PRESIDENTE para evaluar.");
            } else {
                int idEstudiante = (int) dtm_tablaAlumnosTFG.getValueAt(alumnoSeleccionado, 0);
                JDialog_EvaluacionTribunal jd_evalTribunal = new JDialog_EvaluacionTribunal(this, true, ctrl_estudiantes.listarEstudianteCodigo(idEstudiante), tribunal);

                jd_evalTribunal.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        rellenarTabla(); // Vuelve a cargar los datos al cerrarse el JDialog
                    }
                });

                jd_evalTribunal.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un alumno para evaluarlo.");
        }
    }//GEN-LAST:event_bt_evaluarActionPerformed

    private void bt_generarWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_generarWordActionPerformed

        int alumnoSeleccionado = table_estudiantesTFGS.getSelectedRow();

        if (alumnoSeleccionado != -1) {

            int idEstudiante = (int) dtm_tablaAlumnosTFG.getValueAt(alumnoSeleccionado, 0);
            e = ctrl_estudiantes.listarEstudianteCodigo(idEstudiante);
            //TUTOR
            String tutor = e.getTfg().getTutor().getNombre();
            //CURSO
            String curso = tribunal.getCurso();
            //CONVOCATORIA
            String convocatoria = "";
            if (tribunal.isExtraordinaria()) {
                convocatoria = "Extraordinaria";
            } else {
                convocatoria = "Ordinaria";
            }
            //GRADO
            JComboBox<String> comboGrado = new JComboBox<>(new String[]{"Psicología", "Logopedia"});
            comboGrado.setSelectedIndex(0);

            int opcion = JOptionPane.showConfirmDialog(
                    null,
                    comboGrado,
                    "Selecciona el grado",
                    JOptionPane.OK_CANCEL_OPTION
            );

            String grado = "Psicología"; // Valor por defecto
            if (opcion == JOptionPane.OK_OPTION) {
                grado = comboGrado.getSelectedItem().toString();
            }
            //ESTUDIANTE
            String estudiante = e.getNombre() + " " + e.getApellidos();
            //TITULO
            String tituloTFG = e.getTfg().getTitulo();
            //NOTA FINAL
            String notaFinal = String.valueOf(e.getCalificacionTribunal());
            //NUMERO DE TUTORES
            String numTutores = "";
            if (e.getTfg().getProfesor2().isEmpty()) {
                numTutores = "1";
            } else {
                numTutores = "2";
            }

            //FECHA
            LocalDate fechaActual = LocalDate.now();


            String dia = String.valueOf(fechaActual.getDayOfMonth());

            //MES
            String mes = fechaActual.getMonth()
                    .getDisplayName(TextStyle.FULL, new Locale("es"))
                    .substring(0, 1).toUpperCase()
                    + fechaActual.getMonth()
                            .getDisplayName(TextStyle.FULL, new Locale("es"))
                            .substring(1);

            //AÑO
            String anio = String.valueOf(fechaActual.getYear());

            GeneradorDocumentosTFG.generarDocumentoTFG(tutor, curso, convocatoria, grado, estudiante, tituloTFG, notaFinal, numTutores, dia, mes, anio);

        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un alumno para generar su certificado.");
        }
    }//GEN-LAST:event_bt_generarWordActionPerformed

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
            java.util.logging.Logger.getLogger(JDialog_Tribunal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_Tribunal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_Tribunal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_Tribunal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_Tribunal dialog = new JDialog_Tribunal(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bt_generarWord;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_estudiantesTFGS;
    private javax.swing.JLabel txt_rol;
    private javax.swing.JLabel txt_tribunal;
    // End of variables declaration//GEN-END:variables
}
