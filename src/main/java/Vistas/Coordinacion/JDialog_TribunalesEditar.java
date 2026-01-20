package Vistas.Coordinacion;

import Controladores.Ctrl_ProfesorTribunal;
import Controladores.Ctrl_Profesores;
import Controladores.Ctrl_Tribunales;
import Modelos.Profesor;
import Modelos.RolTribunal;
import Modelos.Tribunal;
import Controladores.ColoresExcel;
import Controladores.EMFProvider;
import java.awt.Dialog;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class JDialog_TribunalesEditar extends javax.swing.JDialog {

    private EntityManagerFactory emf;
    private Ctrl_Tribunales ctrl_tribunales;
    private Ctrl_Profesores ctrl_profesores;
    private Ctrl_ProfesorTribunal ctrl_pt;
    private DefaultTableModel dtm_tribunales;
    private DefaultTableModel dtm_docentes;
    private DefaultTableModel dtm_docentesLibres;
    private List<Object[]> tribunalesConEstado;
    private List<Object[]> docentesTribunalSeleccionado;
    private List<Object[]> docentesSinTribunal;
    private String codProfesor;
    private String codTribunal;
    private String codProfesorLibre;

    public JDialog_TribunalesEditar(Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();

        setTitle("Editar/Organizar Tribunales");

        try {
            emf = EMFProvider.getEmf();
            // Ya estás seguro de que funciona
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // o redirigir al login
        }



        ctrl_tribunales = new Ctrl_Tribunales(emf);
        ctrl_profesores = new Ctrl_Profesores(emf);
        ctrl_pt = new Ctrl_ProfesorTribunal(emf);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioButton_ordinaria);
        grupo.add(radioButton_extraordinaria);

        dtm_tribunales = (DefaultTableModel) tableTribunales.getModel();
        tableTribunales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //LOS REGISTROS INCOMPLETOS APARECERAN EN ROJO
        for (int i = 0; i < tableTribunales.getColumnCount(); i++) {
            tableTribunales.getColumnModel().getColumn(i).setCellRenderer(new ColoresExcel());
        }

        dtm_docentes = (DefaultTableModel) tableDocentes.getModel();
        tableDocentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        dtm_docentesLibres = (DefaultTableModel) tableDocentesDisponibles.getModel();
        tableDocentesDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //LISTENER TABLA TRIBUNALES
        tableTribunales.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int tribunalSeleccionado = tableTribunales.getSelectedRow();
                    if (tribunalSeleccionado != -1) {
                        codTribunal = (String) tableTribunales.getValueAt(tribunalSeleccionado, 1);
                        rellenarTablaDocentesTribunal(codTribunal);

                        // Obtener valor de la columna "Extraordinaria"
                        String valorExtraordinaria = (String) tableTribunales.getValueAt(tribunalSeleccionado, 2);

                        if (valorExtraordinaria.equalsIgnoreCase("SI")) {
                            radioButton_extraordinaria.setSelected(true);
                        } else {
                            radioButton_ordinaria.setSelected(true);
                        }
                    }
                }
            }
        });

        //LISTENER TABLA DOCENTES (tabla arriba)
        tableDocentes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int docenteSeleccionado = tableDocentes.getSelectedRow();
                    if (docenteSeleccionado != -1) {
                        codProfesor = (String) tableDocentes.getValueAt(docenteSeleccionado, 0);
                    }
                }
            }
        });

        //LISTENER TABLA DOCENTES DISPONIBLES (tabla abajo)
        tableDocentesDisponibles.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int docenteSeleccionadoLibre = tableDocentesDisponibles.getSelectedRow();
                    if (docenteSeleccionadoLibre != -1) {
                        codProfesorLibre = (String) tableDocentesDisponibles.getValueAt(docenteSeleccionadoLibre, 0);
                    }
                }
            }
        });

        rellenarComboboxCursos();
        rellenarComboboxRoles();
        rellenarTablaTribunales();
    }

    //RELLENAR COMBOBOX CURSOS
    public void rellenarComboboxCursos() {
        combobox_cursos.removeAllItems();
        List<String> cursos = ctrl_tribunales.sacarCursos();
        for (String c : cursos) {
            combobox_cursos.addItem(c);
        }
    }

    //RELLENAR COMBOBOX ROLES
    public void rellenarComboboxRoles() {
        combobox_roles.removeAllItems();
        for (RolTribunal rol : RolTribunal.values()) {
            combobox_roles.addItem(String.valueOf(rol));
        }
    }

    //RELLENAR TABLA TRIBUNALES
    public void rellenarTablaTribunales() {
        dtm_tribunales.setRowCount(0);
        tribunalesConEstado = ctrl_tribunales.listarTribunalesPorCursoConEstado((String) combobox_cursos.getSelectedItem());
        for (Object[] fila : tribunalesConEstado) {
            String extraordinariaStr = ((Boolean) fila[2]) ? "SI" : "NO";
            dtm_tribunales.addRow(new Object[]{fila[0], fila[1], extraordinariaStr, fila[3]});
        }
    }

    //RELLENAR TABLA DOCENTES DE UN TRIBUNAL AL PULSAR EN ESE TRIBUNAL
    public void rellenarTablaDocentesTribunal(String codTribunal) {
        dtm_docentes.setRowCount(0);
        docentesTribunalSeleccionado = ctrl_tribunales.listarProfesoresDeTribunal(codTribunal);
        for (Object[] docentes : docentesTribunalSeleccionado) {
            dtm_docentes.addRow(docentes);
        }
    }

    //RELLENAR TABLA DOCENTES SIN TRIBUNAL DEL CURSO SELECCIONADO
    public void rellenarTablaDocentesSinTribunalCurso(String curso) {
        dtm_docentesLibres.setRowCount(0);
        docentesSinTribunal = ctrl_profesores.listarProfesoresSinTribunalPorCurso(curso);
        for (Object[] libres : docentesSinTribunal) {
            dtm_docentesLibres.addRow(libres);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTribunales = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        combobox_cursos = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDocentes = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        bt_expulsarDocente = new javax.swing.JButton();
        bt_añadir = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableDocentesDisponibles = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        radioButton_ordinaria = new javax.swing.JRadioButton();
        radioButton_extraordinaria = new javax.swing.JRadioButton();
        combobox_roles = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        bt_actualizarRol = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        bt_eliminarTribunal = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setText("EDITAR Y ORGANIZAR TRIBUNALES");

        tableTribunales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Curso", "CodTribunal", "Extraordinaria", "¿Completo?"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableTribunales);
        if (tableTribunales.getColumnModel().getColumnCount() > 0) {
            tableTribunales.getColumnModel().getColumn(0).setResizable(false);
            tableTribunales.getColumnModel().getColumn(1).setResizable(false);
            tableTribunales.getColumnModel().getColumn(2).setResizable(false);
            tableTribunales.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel2.setText("Selecciona el CURSO para ver sus TRIBUNALES:");

        combobox_cursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combobox_cursosActionPerformed(evt);
            }
        });

        tableDocentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre Completo", "Departamento", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableDocentes);
        if (tableDocentes.getColumnModel().getColumnCount() > 0) {
            tableDocentes.getColumnModel().getColumn(0).setMinWidth(70);
            tableDocentes.getColumnModel().getColumn(0).setMaxWidth(70);
            tableDocentes.getColumnModel().getColumn(1).setResizable(false);
            tableDocentes.getColumnModel().getColumn(2).setResizable(false);
            tableDocentes.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel3.setText("Profesores del departamento seleccionado:");

        bt_expulsarDocente.setText("EXPULSAR DOCENTE DEL TRIBUNAL");
        bt_expulsarDocente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_expulsarDocenteActionPerformed(evt);
            }
        });

        bt_añadir.setText("AÑADIR DOCENTE AL TRIBUNAL");
        bt_añadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_añadirActionPerformed(evt);
            }
        });

        tableDocentesDisponibles.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableDocentesDisponibles);
        if (tableDocentesDisponibles.getColumnModel().getColumnCount() > 0) {
            tableDocentesDisponibles.getColumnModel().getColumn(0).setMinWidth(70);
            tableDocentesDisponibles.getColumnModel().getColumn(0).setMaxWidth(70);
            tableDocentesDisponibles.getColumnModel().getColumn(1).setResizable(false);
            tableDocentesDisponibles.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel4.setText("Profesores disponibles (SIN TRIBUNAL)");

        jLabel5.setText("Selecciona para cambiar si el tribunal será de la ORDINARIA o de la EXTRAORDINARIA");

        radioButton_ordinaria.setText("ORDINARIA");
        radioButton_ordinaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButton_ordinariaActionPerformed(evt);
            }
        });

        radioButton_extraordinaria.setText("EXTRAORDINARIA");
        radioButton_extraordinaria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButton_extraordinariaActionPerformed(evt);
            }
        });

        jLabel6.setText("Cambia el rol del docente dentro del tribunal");

        bt_actualizarRol.setText("ACTUALIZAR ROL");
        bt_actualizarRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_actualizarRolActionPerformed(evt);
            }
        });

        bt_eliminarTribunal.setText("   ELIMINAR TRIBUNAL SELECCIONADO   ");
        bt_eliminarTribunal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_eliminarTribunalMouseClicked(evt);
            }
        });
        jMenuBar1.add(bt_eliminarTribunal);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(combobox_cursos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(bt_expulsarDocente)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                                    .addComponent(bt_añadir, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane3))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(combobox_roles, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bt_actualizarRol, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(50, 50, 50))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(radioButton_ordinaria)
                                .addGap(69, 69, 69)
                                .addComponent(radioButton_extraordinaria)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combobox_cursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bt_expulsarDocente)
                            .addComponent(bt_añadir))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioButton_ordinaria)
                            .addComponent(radioButton_extraordinaria)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(combobox_roles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bt_actualizarRol)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //AL CAMBIAR COMBO SE ACTUALIZA LA TABLA
    private void combobox_cursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combobox_cursosActionPerformed
        rellenarTablaTribunales();
        rellenarTablaDocentesSinTribunalCurso((String) combobox_cursos.getSelectedItem());
    }//GEN-LAST:event_combobox_cursosActionPerformed

    //ELIMINAR DOCENTE DEL TRIBUNAL SELECCIONADO
    private void bt_expulsarDocenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_expulsarDocenteActionPerformed
        int docenteTribunalSeleccionado = tableDocentes.getSelectedRow();

        if (docenteTribunalSeleccionado != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar al profesor del tribunal?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                int resultado = ctrl_tribunales.eliminarProfesorDeTribunal(codTribunal, codProfesor);

                if (resultado == 1) {
                    JOptionPane.showMessageDialog(this, "Profesor eliminado del tribunal con éxito.");
                    rellenarTablaTribunales();
                    rellenarTablaDocentesTribunal(codTribunal);
                    rellenarTablaDocentesSinTribunalCurso((String) combobox_cursos.getSelectedItem());

                } else if (resultado == -2) {
                    JOptionPane.showMessageDialog(this, "El profesor no estaba en el tribunal.");
                } else {
                    JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar al profesor.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Para expulsar a un docente del tribunal, deberás seleccionarlo primero.");
        }
    }//GEN-LAST:event_bt_expulsarDocenteActionPerformed

    //BOTON AÑADIR DOCENTE LIBRE A TRIBUNAL
    private void bt_añadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_añadirActionPerformed
        int docenteLibreSeleccionado = tableDocentesDisponibles.getSelectedRow();

        if (docenteLibreSeleccionado != -1) {
            // Buscar el profesor para mostrar su nombre en la confirmación
            Profesor p = ctrl_profesores.buscarProfesor(codProfesorLibre);

            // Mostrar cuadro de confirmación
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Deseas añadir al docente " + p.getNombre() + " al tribunal " + codTribunal + "?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {

                if (dtm_docentes.getRowCount() == 3) {
                    JOptionPane.showMessageDialog(this, "No se ha podido añadir el docente porque el tribunal ya cuenta con 3 docentes.", "AVISO ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int resultado = ctrl_tribunales.addProfesorTribunal(codTribunal, codProfesorLibre);

                if (resultado == 1) {

                    rellenarTablaTribunales();
                    rellenarTablaDocentesTribunal(codTribunal);
                    rellenarTablaDocentesSinTribunalCurso((String) combobox_cursos.getSelectedItem());

                } else if (resultado == -1) {
                    JOptionPane.showMessageDialog(this, "Error: Profesor o tribunal no encontrado.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al añadir al docente al tribunal.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Para agregar un docente libre a un tribunal, deberás seleccionarlo primero.");
        }
    }//GEN-LAST:event_bt_añadirActionPerformed

    //BOTON RADIO BUTTON PARA MARCAR COMO ORDINARIA
    private void radioButton_ordinariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButton_ordinariaActionPerformed
        if (codTribunal == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un tribunal");
            return;
        }
        ctrl_tribunales.marcarOrdinaria(new Tribunal(codTribunal));
        JOptionPane.showMessageDialog(this, "Has cambiado el tribunal " + codTribunal + " a ORDINARIA");
        rellenarTablaTribunales();
    }//GEN-LAST:event_radioButton_ordinariaActionPerformed

    //BOTON RADIO BUTTON PARA MARCAR COMO EXTRAORDINARIA
    private void radioButton_extraordinariaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButton_extraordinariaActionPerformed
        if (codTribunal == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un tribunal");
            return;
        }
        ctrl_tribunales.marcarExtraordinaria(new Tribunal(codTribunal));
        JOptionPane.showMessageDialog(this, "Has cambiado el tribunal " + codTribunal + " a EXTRAORDINARIA");
        rellenarTablaTribunales();
    }//GEN-LAST:event_radioButton_extraordinariaActionPerformed

    //BOTON ACTUALIZAR ROL DE PROFESOR SELECCIONADOD DE TRIBUNAL SELECCIONADO
    private void bt_actualizarRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_actualizarRolActionPerformed
        if (codTribunal == null) {
            JOptionPane.showMessageDialog(this, "Primero deberás de seleccionar un tribunal");
            return;
        } else if (codProfesor == null) {
            JOptionPane.showMessageDialog(this, "Ahora deberás de seleccionar un profesor");
            return;
        } else {
            String rolSeleccionado = (String) combobox_roles.getSelectedItem();
            int resultado = ctrl_pt.actualizarRol(codTribunal, codProfesor, rolSeleccionado);

            switch (resultado) {
                case 1:
                    JOptionPane.showMessageDialog(this, "Rol actualizado correctamente.");
                    rellenarTablaDocentesTribunal(codTribunal);
                    break;
                case 0:
                    JOptionPane.showMessageDialog(this, "No se encontró la relación entre el profesor y el tribunal.");
                    break;
                case 2:
                    JOptionPane.showMessageDialog(this, "Ya hay un presidente asignado a este tribunal.");
                    break;
                case -1:
                    JOptionPane.showMessageDialog(this, "Error al actualizar el rol. Revisa los datos e intenta de nuevo.");
                    break;
            }
        }

    }//GEN-LAST:event_bt_actualizarRolActionPerformed

    //BOTON ELIMINAR TRIBUNAL SELECCIONADO
    private void bt_eliminarTribunalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_eliminarTribunalMouseClicked
        int tribunalSeleccionado = tableTribunales.getSelectedRow();
        if (tribunalSeleccionado != -1) {
            codTribunal = (String) tableTribunales.getValueAt(tribunalSeleccionado, 1);

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar el tribunal con código: " + codTribunal + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                int resultado = ctrl_pt.eliminarTribunal(codTribunal);
                if (resultado == 1) {
                    JOptionPane.showMessageDialog(this, "Tribunal eliminado con éxito.");
                    rellenarTablaTribunales();
                    rellenarTablaDocentesSinTribunalCurso((String) combobox_cursos.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(this, "Ha ocurrido un error al eliminar el tribunal.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Para ELIMINAR un TRIBUNAL, deberás seleccionarlo primero.");
        }
    }//GEN-LAST:event_bt_eliminarTribunalMouseClicked

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
            java.util.logging.Logger.getLogger(JDialog_TribunalesEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_TribunalesEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_TribunalesEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_TribunalesEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_TribunalesEditar dialog = new JDialog_TribunalesEditar(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton bt_actualizarRol;
    private javax.swing.JButton bt_añadir;
    private javax.swing.JMenu bt_eliminarTribunal;
    private javax.swing.JButton bt_expulsarDocente;
    private javax.swing.JComboBox<String> combobox_cursos;
    private javax.swing.JComboBox<String> combobox_roles;
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
    private javax.swing.JRadioButton radioButton_extraordinaria;
    private javax.swing.JRadioButton radioButton_ordinaria;
    private javax.swing.JTable tableDocentes;
    private javax.swing.JTable tableDocentesDisponibles;
    private javax.swing.JTable tableTribunales;
    // End of variables declaration//GEN-END:variables
}
