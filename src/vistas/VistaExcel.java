package vistas;

import beans.ProductoBean;
import constantes.ConstantesProperties;
import consumewebservices.WSDatosEmpresa;
import consumewebservices.WSInventarios;
import consumewebservices.WSSistema;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import mensajes.Correcto;
import mensajes.ErrorMsg;
import mensajes.Warning;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.Renderer;
import util.Util;
import static vistas.Principal.datosEmpresaBean;
import static vistas.Principal.datosSistemaBean;
import static vistas.Principal.productos;

public class VistaExcel extends javax.swing.JFrame {
    Correcto iconCorrecto = new Correcto();
    ErrorMsg iconError = new ErrorMsg();
    Warning iconWarning = new Warning();
    
    Workbook wb;
    JFileChooser selecArchivo = new JFileChooser();
    File archivo;
    int contAccion=0;
    Map<Integer,Integer> errores = new HashMap();
    Renderer r = new Renderer();
    DefaultTableModel dt;

    Util util = new Util();
    //WS
    Properties constantes = new ConstantesProperties().getProperties();
    WSDatosEmpresa hiloEmpresa;
    WSSistema hiloSistema;
    WSInventarios hiloInventarios;
    //Fin WS
    
    public VistaExcel() {
        initComponents();
        // Carga datos de la empresa
        hiloSistema = new WSSistema();
        String rutaWS = constantes.getProperty("IP") + constantes
                .getProperty("GETDATOSSISTEMA");
        datosSistemaBean = hiloSistema.ejecutaWebService(rutaWS,"1");
        // Fin Carga datos de la empresa
        
        this.setTitle(datosEmpresaBean.getNombreEmpresa());        
        lblUsuario.setText("Usuario : " + Ingreso.usuario.getNombre()
            + " " + Ingreso.usuario.getApellido_paterno()
            + " " + Ingreso.usuario.getApellido_materno()
        );
        
        //carga categorias
        Iterator it = Principal.categoriasHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboCategoria.addItem(Principal.categoriasHM.get(key));
        }        
        
        //carga proveedores
        it = Principal.proveedoresHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboProveedor.addItem(Principal.proveedoresHM.get(key));
        }        
        
        //carga sucursales
        it = Principal.sucursalesHM.keySet().iterator();
        while(it.hasNext()){
          Object key = it.next();
          cboSucursal.addItem(Principal.sucursalesHM.get(key));
        }        

        //cambia formato de fecha a tipo datetime xq asi esta en bd remota
        jCalFechaIngresoProd.setDate(new Date());
        jCalFechaIngresoProd.setDateFormatString("yyyy-MM-dd HH:mm:ss");
        this.setIcon();
    }
    
    public void setIcon() {
        ImageIcon icon;
        icon = new ImageIcon("logo.png");
        setIconImage(icon.getImage());
    }    

    public void AgregarFiltro(){
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xls)"
                , "xls"));
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)"
                , "xlsx"));
    }
    
    public String Importar(File archivo, JTable tablaD) throws InvalidFormatException{
        //para validar numero correcto de columnas
        int numColumnas = 0;
        boolean sigueImportacion = true;
        boolean sigueImportacionEsnumero = true;
        int renglon = 0;
        int columna = 0;
        
        String respuesta="No se pudo realizar la importación.";
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        tablaD.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        try {
            wb = WorkbookFactory.create(new FileInputStream(archivo));
            Sheet hoja = wb.getSheetAt(0);
            Iterator filaIterator = hoja.rowIterator();
            int indiceFila=-1;
            while (filaIterator.hasNext()) {
                if (!sigueImportacion) {
                    break;
                }
                indiceFila++;
                Row fila = (Row) filaIterator.next();
                Iterator columnaIterator = fila.cellIterator();
                Object[] listaColumna = new Object[1000];
                int indiceColumna=-1;
                while (columnaIterator.hasNext()) {                    
                    indiceColumna++;
                    Cell celda = (Cell) columnaIterator.next();
                    if(indiceFila==0){
                        numColumnas++;
                        modeloT.addColumn(celda.getStringCellValue());
                    }else{
                        //verifica numero de columnas adecuado
                        if (numColumnas != 9){
                            sigueImportacion = false;
                            break;                            
                        }
                        //fin verifica numero de columnas adecuado
                        
                        //verifica formato de cantidades
                        if ((indiceColumna > 1) && (indiceColumna < 6)) {
                            if (celda.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                if (!util.esNumero("" + 
                                        celda.getNumericCellValue())) {
                                    listaColumna[indiceColumna] = "ERROR " + celda;
                                    sigueImportacionEsnumero = false;                                    
                                    errores.put(renglon, columna);
                                } else {
                                    listaColumna[indiceColumna]= celda;
                                }
                            } else {
                                listaColumna[indiceColumna] = "ERROR " + celda;
                                sigueImportacionEsnumero = false;
                                errores.put(renglon, columna);
                            }
                        } else {
                            //si es codigo quita .0 que componente asigna
                            String codigo = "";
                            if (indiceColumna==0) {
                                //celda.setCellType(Cell.CELL_TYPE_STRING);
                                codigo = "" + celda;
                                codigo = codigo.replace(".0", "");
                                int indice = codigo.indexOf(".");
                                //verifica si codigo esta en formato exponencial
                                if (indice != -1) {
                                    errores.put(renglon, columna);
                                    listaColumna[indiceColumna]= "E.FORMATO " + codigo;
                                } else {
                                    listaColumna[indiceColumna]= codigo;
                                }
                                //fin verifica si codigo esta en formato exponencial
                            } else {
                                listaColumna[indiceColumna]= celda;
                            }
                            //si es codigo quita .0 que componente asigna
                        }
                        //fin verifica formato de cantidades
                    }
                    columna++;
                }
                columna = 0;
                renglon++;
                if(indiceFila!=0)modeloT.addRow(listaColumna);
            }
            respuesta="Importación exitosa";
        } catch (IOException | InvalidFormatException | EncryptedDocumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage()
                ,Principal.datosEmpresaBean.getNombreEmpresa()
                ,JOptionPane.ERROR_MESSAGE,iconError
            );
        }
        if (!sigueImportacion) {
            respuesta="No se pudo realizar la importación. "
                    + "\nEstructura de archivo incorrecta";
            return respuesta;
        }
        if (!sigueImportacionEsnumero) {
            respuesta="No se pudo realizar la importación. "
                    + "\nDatos Incorrectos";
            return respuesta;
        }
        return respuesta;
    }
    
    public String Exportar(File archivo, JTable tablaD){
        String respuesta="No se realizo con exito la exportación.";
        int numFila=tablaD.getRowCount(), numColumna=tablaD.getColumnCount();
        if(archivo.getName().endsWith("xls")){
            wb = new HSSFWorkbook();
        }else{
            wb = new XSSFWorkbook();
        }
        Sheet hoja = wb.createSheet("Pruebita");
        try {
            for (int i = -1; i < numFila; i++) {
                Row fila = hoja.createRow(i+1);
                for (int j = 0; j < numColumna; j++) {
                    Cell celda = fila.createCell(j);
                    if(i==-1){
                        celda.setCellValue(String.valueOf(tablaD.getColumnName(j)));
                    }else{
                        celda.setCellValue(String.valueOf(tablaD.getValueAt(i, j)));
                    }
                    wb.write(new FileOutputStream(archivo));
                }
            }
            respuesta="Exportación exitosa.";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()
                ,Principal.datosEmpresaBean.getNombreEmpresa()
                ,JOptionPane.ERROR_MESSAGE,iconError
            );
        }
        return respuesta;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnImportar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDatos = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cboSucursal = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        cboCategoria = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cboProveedor = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jCalFechaIngresoProd = new com.toedter.calendar.JDateChooser();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnImportar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/xlsx.png"))); // NOI18N
        btnImportar.setText("IMPORTAR");
        btnImportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/xls.png"))); // NOI18N
        jLabel1.setText("IMPORTAR A EXCEL ");

        jtDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Descripción", "Precio Costo", "Precio Público", "Existencia", "Existencia Minima", "Ubicación", "Observaciones "
            }
        ));
        jtDatos.setAutoscrolls(false);
        jtDatos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtDatos.getTableHeader().setResizingAllowed(false);
        jScrollPane1.setViewportView(jtDatos);

        btnRegresar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/regresar.jpg"))); // NOI18N
        btnRegresar.setText("REGRESAR");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        lblUsuario.setText("Usuario :");

        jLabel10.setText("Sucursal : ");

        cboSucursal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboSucursalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cboSucursalMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cboSucursalMousePressed(evt);
            }
        });
        cboSucursal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSucursalItemStateChanged(evt);
            }
        });
        cboSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSucursalActionPerformed(evt);
            }
        });
        cboSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cboSucursalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboSucursalKeyTyped(evt);
            }
        });

        jLabel13.setText("Categoria :");

        cboCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboCategoriaItemStateChanged(evt);
            }
        });
        cboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCategoriaActionPerformed(evt);
            }
        });
        cboCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cboCategoriaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboCategoriaKeyTyped(evt);
            }
        });

        jLabel2.setText("Proveedor");

        cboProveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar..." }));
        cboProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProveedorActionPerformed(evt);
            }
        });
        cboProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cboProveedorKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Selecciona los datos para la importación : ");

        jLabel12.setText("Fecha Ingreso :");

        jCalFechaIngresoProd.setDateFormatString("yyyy-MM-d");

        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exit.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel13)
                                        .addGap(5, 5, 5)
                                        .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCalFechaIngresoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 102, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(btnImportar)
                .addGap(147, 147, 147)
                .addComponent(btnRegresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(cboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(cboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12)
                        .addComponent(jCalFechaIngresoProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnImportar)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRegresar)
                        .addComponent(btnSalir)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        if (cboCategoria.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
            || cboSucursal.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
            || cboProveedor.getSelectedItem().toString().
                equalsIgnoreCase("Seleccionar...")
            || jCalFechaIngresoProd.getDate() == null
            ) {
            util.errorSeleccion();
            return;
        }
        contAccion++;
        if(contAccion==1)AgregarFiltro();
        if(selecArchivo.showDialog(null, "Seleccionar archivo") == 
                JFileChooser.APPROVE_OPTION){
            archivo=selecArchivo.getSelectedFile();
            if(archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx")){
                try {
                    String msg = Importar(archivo, jtDatos);
                    r.setErrores(errores);
                    dt = (DefaultTableModel) jtDatos.getModel();
                    jtDatos.setDefaultRenderer(Object.class, r);
                    jtDatos.setEnabled(false);

                    //cambio ancho de columnas
                    TableColumnModel columnModel = jtDatos.getColumnModel();
                    columnModel.getColumn(0).setPreferredWidth(100);
                    columnModel.getColumn(1).setPreferredWidth(250);
                    columnModel.getColumn(2).setPreferredWidth(100);
                    columnModel.getColumn(3).setPreferredWidth(100);
                    columnModel.getColumn(4).setPreferredWidth(100);
                    columnModel.getColumn(5).setPreferredWidth(100);
                    columnModel.getColumn(6).setPreferredWidth(100);
                    columnModel.getColumn(7).setPreferredWidth(100);
                    columnModel.getColumn(8).setPreferredWidth(100);
                    //fin cambio ancho de columnas
                    
                    
                    if (errores.size() == 0) {
                        if (guardaDatosImportados()) {
                            JOptionPane.showMessageDialog(null,
                                    msg + "\n Formato ."+ archivo.getName()
                                            .substring(archivo.getName()
                                                    .lastIndexOf(".")+1)
                                    ,Principal.datosEmpresaBean.getNombreEmpresa()
                                    ,JOptionPane.INFORMATION_MESSAGE,iconCorrecto
                            );
                        } else {
                            JOptionPane.showMessageDialog(null, "Los datos de "
                                    + "la tabla no se"
                            + " importaron"
                            ,Principal.datosEmpresaBean.getNombreEmpresa()
                            ,JOptionPane.ERROR_MESSAGE,iconError
                            );
                        }
                    }
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(VistaExcel.class.getName()).log(Level.SEVERE
                            , null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Elija un formato valido."
                    ,Principal.datosEmpresaBean.getNombreEmpresa()
                    ,JOptionPane.WARNING_MESSAGE,iconWarning
                );
            }
        }
        btnImportar.setEnabled(false);
    }//GEN-LAST:event_btnImportarActionPerformed

    private boolean guardaDatosImportados(){
        boolean importados = true;
        for (int filaTabla = 0; filaTabla < jtDatos.getRowCount(); filaTabla++) {
            //Arma objeto producto por renglon de la tabla
            ProductoBean p = new ProductoBean();
            p.setCodigo(String.valueOf(jtDatos.getModel()
                    .getValueAt(filaTabla,0)));
            p.setDescripcion(String.valueOf(jtDatos.getModel()
                    .getValueAt(filaTabla,1)));
            p.setExistencia(Double.parseDouble(
                    String.valueOf(jtDatos.getModel()
                    .getValueAt(filaTabla,4))));
            p.setExistenciaMinima(Double.parseDouble(
                    String.valueOf(jtDatos.getModel()
                    .getValueAt(filaTabla,5))));

            //cambia formato de fecha a tipo datetime xq asi esta en bd remota
            jCalFechaIngresoProd.setDateFormatString("yyyy/MM/dd HH:mm:ss");
            p.setFechaIngreso(jCalFechaIngresoProd.getDate());
            //cambia formato para enviarla como string a ws
            p.setIdCategoria(util.buscaIdCat(Principal.categoriasHM, 
                    cboCategoria.getSelectedItem().toString()));
            p.setIdProveedor(util.buscaIdProv(Principal.proveedoresHM, 
                    cboProveedor.getSelectedItem().toString()));
            p.setIdSucursal(util.buscaIdSuc(Principal.sucursalesHM, 
                    cboSucursal.getSelectedItem().toString()));
            
            p.setObservaciones(String.valueOf(jtDatos.getModel()
                    .getValueAt(filaTabla,7)));
            //Obtiene porcentaje impuesto
            double precioCosto = Double.parseDouble(String.valueOf(jtDatos.getModel()
                    .getValueAt(filaTabla,2)));
            double precioPublico = Double.parseDouble(String.valueOf(jtDatos.getModel()
                    .getValueAt(filaTabla,3)));
            double ganancia = precioCosto - precioPublico;
            double porcentajeImpuesto = (ganancia * 100) / precioCosto;
            //Fin Obtiene porcentaje impuesto
            
            p.setPorcentajeImpuesto(porcentajeImpuesto);
            p.setPrecioCosto(precioCosto);
            p.setPrecioUnitario(precioPublico);
            p.setUbicacion(String.valueOf(jtDatos.getModel()
                    .getValueAt(filaTabla,6)));
            p.setUnidadMedida(String.valueOf(jtDatos.getModel()
                    .getValueAt(filaTabla,8)));
            p.setFechaCaducidad(jCalFechaIngresoProd.getDate());
            
            //Arma objeto producto por renglon de la tabla
            
            //verifica si el producto se encuentra registrado 
            //en la sucursal y si no lo inserta lo lo borra de la tabla
            if (util.buscaProdDuplicadoEnSucursal(Principal.productos, 
                    p.getCodigo().trim(), 
                    p.getIdSucursal())) {
                importados = false;
            } else {
                //guardar producto
                hiloInventarios = new WSInventarios();
                String rutaWS = constantes.getProperty("IP") + constantes
                        .getProperty("GUARDAPRODUCTO");
                ProductoBean productoInsertado = hiloInventarios
                        .ejecutaWebService(rutaWS,"1"
                        ,p.getCodigo()
                        ,p.getDescripcion()
                        ,"" + p.getPrecioCosto()
                        ,"" + p.getPrecioUnitario()
                        ,"" + p.getPorcentajeImpuesto()
                        ,"" + p.getExistencia()
                        ,"" + p.getExistenciaMinima()
                        ,p.getUbicacion()
                        ,p.getFechaIngreso().toLocaleString()
                        ,"" + p.getIdProveedor()
                        ,"" + p.getIdCategoria()
                        ,"" + p.getIdSucursal()
                        ,""
                        ,p.getObservaciones()
                        ,p.getUnidadMedida()
                        ,p.getFechaCaducidad().toLocaleString()
                );
                if (productoInsertado != null) {
                    DefaultTableModel modelo = (DefaultTableModel)jtDatos
                            .getModel(); 
                    //modelo.removeRow(filaTabla);
                    importados = true;
                } else {
                    importados = false;
                }
                //inserta y borra de la tabla
            }
            //fin verifica si el producto se encuentra registrado en la sucursal
        }
        //Fin Arma objeto producto por renglon de la tabla
        return importados;
    }

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.setVisible(false);
        this.dispose();
        //Carga productos
        Principal principal = new Principal();
        principal.cargaProductos();
        FrmProducto frmProducto = new FrmProducto(0);
        frmProducto.actualizarBusquedaProducto(); 
        frmProducto.recargarTableProductos(productos);
        frmProducto.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void cboSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSucursalMouseClicked

    }//GEN-LAST:event_cboSucursalMouseClicked

    private void cboSucursalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSucursalMouseEntered
    }//GEN-LAST:event_cboSucursalMouseEntered

    private void cboSucursalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSucursalMousePressed
    }//GEN-LAST:event_cboSucursalMousePressed

    private void cboSucursalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSucursalItemStateChanged
    }//GEN-LAST:event_cboSucursalItemStateChanged

    private void cboSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSucursalActionPerformed
    }//GEN-LAST:event_cboSucursalActionPerformed

    private void cboSucursalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSucursalKeyReleased
    }//GEN-LAST:event_cboSucursalKeyReleased

    private void cboSucursalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboSucursalKeyTyped
    }//GEN-LAST:event_cboSucursalKeyTyped

    private void cboCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboCategoriaItemStateChanged

    }//GEN-LAST:event_cboCategoriaItemStateChanged

    private void cboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaActionPerformed

    }//GEN-LAST:event_cboCategoriaActionPerformed

    private void cboCategoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCategoriaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCategoriaKeyPressed

    private void cboCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboCategoriaKeyTyped
    }//GEN-LAST:event_cboCategoriaKeyTyped

    private void cboProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProveedorActionPerformed

    }//GEN-LAST:event_cboProveedorActionPerformed

    private void cboProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cboProveedorKeyTyped
    }//GEN-LAST:event_cboProveedorKeyTyped

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

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
            java.util.logging.Logger.getLogger(VistaExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaExcel().setVisible(true);
            }
        });
    }

    static public class MyRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected,
                    hasFocus, row, column);
            c.setForeground(Color.RED);
            return c;
        }
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnImportar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cboCategoria;
    private javax.swing.JComboBox cboProveedor;
    private javax.swing.JComboBox cboSucursal;
    private com.toedter.calendar.JDateChooser jCalFechaIngresoProd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jtDatos;
    private javax.swing.JLabel lblUsuario;
    // End of variables declaration//GEN-END:variables
}
