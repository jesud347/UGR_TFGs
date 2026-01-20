package Vistas.Coordinacion;

import Controladores.*;
import Modelos.*;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class JDialog_Docentes extends javax.swing.JDialog {

    private Ctrl_Profesores ctrl_profesores;
    private Ctrl_ProfesorTribunal ctrl_profesoresTribunales;
    private Ctrl_Estudiantes ctrl_estudiantes;
    private EntityManagerFactory emf;
    private DefaultTableModel dtm_docentes;
    private DefaultTableModel dtm_tribunales;
    private DefaultTableModel dtm_estudiantes;
    private List<Profesor> listaProfesores;
    private List<Estudiante> listaEstudiantes;
    private List<Object[]> listaTribunales;

    public JDialog_Docentes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        setTitle("Gestión de Docentes");

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
        ctrl_profesoresTribunales = new Ctrl_ProfesorTribunal(emf);
        ctrl_estudiantes = new Ctrl_Estudiantes(emf);

        dtm_docentes = (DefaultTableModel) table_docentes.getModel();
        dtm_estudiantes = (DefaultTableModel) table_estudiantes.getModel();
        dtm_tribunales = (DefaultTableModel) table_tribunales.getModel();
        table_docentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        rellenarTablaDocentes();
        rellenarComboboxDepartamentos();

        table_docentes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int filaSeleccionada = table_docentes.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String codProfesor = (String) table_docentes.getValueAt(filaSeleccionada, 0); // Suponiendo que la columna 0 es el código
                        rellenarTablaTribunales(codProfesor);
                        rellenarTablaEstudiantes(codProfesor);
                    }
                }
            }
        });

    }

    //RELLENAR TABLA DOCENTES
    public void rellenarTablaDocentes() {
        dtm_docentes.setRowCount(0);
        listaProfesores = ctrl_profesores.listaProfesores();
        for (Profesor p : listaProfesores) {
            dtm_docentes.addRow(new Object[]{
                p.getCodProfesor(),
                p.getNombre(),
                p.getDepartamento()
            });
        }
    }

    //RELLENAR TABLA TRIBUNALES AL PULSAR EN UN DOCENTE
    public void rellenarTablaTribunales(String codProfesor) {
        dtm_tribunales.setRowCount(0);

        listaTribunales = ctrl_profesoresTribunales.listarTribunalesPorCodProfesor(codProfesor);
        for (Object[] fila : listaTribunales) {
            dtm_tribunales.addRow(fila);
        }
    }

    //RELLENAR TABLA ESTUDIANTES AL PULSAR EN UN DOCENTE
    public void rellenarTablaEstudiantes(String codProfesor) {
        dtm_estudiantes.setRowCount(0);

        listaEstudiantes = ctrl_estudiantes.obtenerEstudiantesPorTutor(codProfesor);
        for (Estudiante e : listaEstudiantes) {
            dtm_estudiantes.addRow(new Object[]{
                e.toString()
            });
        }

    }

    //RELLENAR COMBOBOX DEPARTAMENTOS
    public void rellenarComboboxDepartamentos() {
        combobox_departamentos.removeAllItems();
        List<String> departamentos = ctrl_profesores.sacarDepartamentos();
        for (String dpt : departamentos) {
            combobox_departamentos.addItem(dpt);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_docentes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_tribunales = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        bt_buscarCodigo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        combobox_departamentos = new javax.swing.JComboBox<>();
        bt_buscarDepartamento = new javax.swing.JButton();
        bt_quitarFiltro = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        bt_buscarNombre = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_estudiantes = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        bt_crearDocente = new javax.swing.JMenu();
        bt_eliminarDocente = new javax.swing.JMenu();
        bt_editarDocente = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        table_docentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre Completo", "Departamento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_docentes.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table_docentes);
        if (table_docentes.getColumnModel().getColumnCount() > 0) {
            table_docentes.getColumnModel().getColumn(0).setMinWidth(60);
            table_docentes.getColumnModel().getColumn(0).setMaxWidth(60);
            table_docentes.getColumnModel().getColumn(1).setResizable(false);
            table_docentes.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Estudiantes que tutoriza el docente seleccionado");

        table_tribunales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CodTribunal", "Curso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(table_tribunales);
        if (table_tribunales.getColumnModel().getColumnCount() > 0) {
            table_tribunales.getColumnModel().getColumn(0).setResizable(false);
            table_tribunales.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Listado completo de todos los docentes.");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Filtrar docente por nombre.");

        bt_buscarCodigo.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        bt_buscarCodigo.setText("BUSCAR");
        bt_buscarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_buscarCodigoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("Filtrar docente por departamento.");

        bt_buscarDepartamento.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        bt_buscarDepartamento.setText("BUSCAR");
        bt_buscarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_buscarDepartamentoActionPerformed(evt);
            }
        });

        bt_quitarFiltro.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        bt_quitarFiltro.setText("QUITAR FILTROS");
        bt_quitarFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_quitarFiltroActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setText("Filtrar docente por código.");

        bt_buscarNombre.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        bt_buscarNombre.setText("BUSCAR");
        bt_buscarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_buscarNombreActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setText("Tribunal/es al que pertenece el docente seleccionado");

        table_estudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código y Nombre Completo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(table_estudiantes);
        if (table_estudiantes.getColumnModel().getColumnCount() > 0) {
            table_estudiantes.getColumnModel().getColumn(0).setResizable(false);
        }

        bt_crearDocente.setText("CREAR DOCENTE");
        bt_crearDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_crearDocenteMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_crearDocente);

        bt_eliminarDocente.setText("ELIMINAR DOCENTE");
        bt_eliminarDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_eliminarDocenteMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_eliminarDocente);

        bt_editarDocente.setText("EDITAR DOCENTE");
        bt_editarDocente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_editarDocenteMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_editarDocente);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(97, 97, 97)
                                        .addComponent(jLabel1)))
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(59, 59, 59)
                                    .addComponent(bt_buscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel3)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(59, 59, 59)
                                    .addComponent(bt_buscarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel4)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(combobox_departamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(72, 72, 72)
                                    .addComponent(bt_buscarDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(bt_quitarFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2)))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(bt_buscarCodigo)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(bt_buscarNombre)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combobox_departamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(bt_buscarDepartamento)))
                        .addGap(30, 30, 30)
                        .addComponent(bt_quitarFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //BOTON BUSCAR POR DEPARTAMENTO
    private void bt_buscarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_buscarDepartamentoActionPerformed
        String departamento = (String) combobox_departamentos.getSelectedItem();
        dtm_docentes.setRowCount(0);
        listaProfesores = ctrl_profesores.listaProfesoresDepartamento(departamento);
        for (Profesor p : listaProfesores) {
            dtm_docentes.addRow(new Object[]{
                p.getCodProfesor(),
                p.getNombre(),
                p.getDepartamento()
            });
        }
    }//GEN-LAST:event_bt_buscarDepartamentoActionPerformed

    //QUITAR FILTRO
    private void bt_quitarFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_quitarFiltroActionPerformed
        rellenarTablaDocentes();
        txt_codigo.setText("");
        txt_nombre.setText("");
    }//GEN-LAST:event_bt_quitarFiltroActionPerformed

    //BOTON FILTRAR POR CODIGO
    private void bt_buscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_buscarCodigoActionPerformed
        String codigo = txt_codigo.getText().trim();
        if (!codigo.isEmpty()) {
            dtm_docentes.setRowCount(0);
            listaProfesores = ctrl_profesores.listaProfesoresCodigo(codigo);
            for (Profesor p : listaProfesores) {
                dtm_docentes.addRow(new Object[]{
                    p.getCodProfesor(),
                    p.getNombre(),
                    p.getDepartamento()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "El campo no puede estar vacio.");
        }

    }//GEN-LAST:event_bt_buscarCodigoActionPerformed

    //BOTON FILTRAR POR NOMBRE
    private void bt_buscarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_buscarNombreActionPerformed
        String nombre = txt_nombre.getText().trim();
        if (!nombre.isEmpty()) {
            dtm_docentes.setRowCount(0);
            listaProfesores = ctrl_profesores.listaProfesoresNombre(nombre);
            for (Profesor p : listaProfesores) {
                dtm_docentes.addRow(new Object[]{
                    p.getCodProfesor(),
                    p.getNombre(),
                    p.getDepartamento()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "El campo no puede estar vacio.");
        }
    }//GEN-LAST:event_bt_buscarNombreActionPerformed

    //ALTA DOCENTES
    private void bt_crearDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_crearDocenteMouseClicked
        JDialog_DocentesAlta jd_docentesAlta = new JDialog_DocentesAlta(this, true, this);
        jd_docentesAlta.setVisible(true);
    }//GEN-LAST:event_bt_crearDocenteMouseClicked

    //ELIMINAR DOCENTE SELECCIONADO
    private void bt_eliminarDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_eliminarDocenteMouseClicked
        int docenteSeleccionado = table_docentes.getSelectedRow();
        if (docenteSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Para ELIMINAR un docente, deberás seleccionarlo en la tabla.");
        } else {
            String codProfesor = (String) dtm_docentes.getValueAt(docenteSeleccionado, 0);
            int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar al profesor? También se eliminará su usuario.", "AVISO", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                int resultado = ctrl_profesores.eliminarDocente(new Profesor(codProfesor));
                if (resultado == 1) {
                    JOptionPane.showMessageDialog(this, "Profesor eliminado con éxito.");
                    rellenarTablaDocentes();
                }
                if (resultado == -3) {
                    JOptionPane.showMessageDialog(this, "El profesor no se puede borrar ya que pertenece a un tribunal, para borrarlo deberás eliminarlo de los tribunales a los que pertenece");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Operación cancelada.");
            }
        }

    }//GEN-LAST:event_bt_eliminarDocenteMouseClicked

    //EDITAR DOCENTE SELECCIONADO
    private void bt_editarDocenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_editarDocenteMouseClicked
        int docenteSeleccionado = table_docentes.getSelectedRow();
        if (docenteSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Para EDITAR un docente, deberás seleccionarlo en la tabla.");
        } else {
            String codProfesor = (String) dtm_docentes.getValueAt(docenteSeleccionado, 0);
            JDialog_DocentesEditar jd_docentesEditar = new JDialog_DocentesEditar(this, true, codProfesor, this);
            jd_docentesEditar.setVisible(true);
        }


    }//GEN-LAST:event_bt_editarDocenteMouseClicked

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
            java.util.logging.Logger.getLogger(JDialog_Docentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_Docentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_Docentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_Docentes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_Docentes dialog = new JDialog_Docentes(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton bt_buscarCodigo;
    private javax.swing.JButton bt_buscarDepartamento;
    private javax.swing.JButton bt_buscarNombre;
    private javax.swing.JMenu bt_crearDocente;
    private javax.swing.JMenu bt_editarDocente;
    private javax.swing.JMenu bt_eliminarDocente;
    private javax.swing.JButton bt_quitarFiltro;
    private javax.swing.JComboBox<String> combobox_departamentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable table_docentes;
    private javax.swing.JTable table_estudiantes;
    private javax.swing.JTable table_tribunales;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_nombre;
    // End of variables declaration//GEN-END:variables
}
