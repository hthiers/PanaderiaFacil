package cl.ht.facturacion.client.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import cl.ht.facturacion.client.util.Utils;
import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.client.vo.VOGuia;
import cl.ht.facturacion.client.vo.VOItemProducto;
import cl.ht.facturacion.client.vo.VOProducto;
import cl.ht.facturacion.dao.impl.DAOClientesImpl;
import cl.ht.facturacion.dao.impl.DAOGuiasImpl;
import cl.ht.facturacion.dao.impl.DAOProductosImpl;

public class NuevaGuiaDespacho {

	Text txtnumeroguia;
	Text txtfechaguia;
	
	Text rut;
	Text nombre;
	Text apellido;
	Text ciudad;
	Text comuna;
	Text direccion;
	Text giro;
	
	Text codigo;
	Text cantidad;
	Text detalle;
	Text precioUnit;
	Text total;
	Text codigo2;
	Text cantidad2;
	Text detalle2;
	Text precioUnit2;
	Text total2;
	Text codigo3;
	Text cantidad3;
	Text detalle3;
	Text precioUnit3;
	Text total3;
	
	Text totalFinal;
	
	VOProducto producto;
	
	//ArrayList<VOItemProducto> listaItems;
	//TEST BIGDECIMAL
	ArrayList<VOItemProducto> listaItems;
	
	int numeroGuia = 1000;
	
	//int valorTotalFinal = 0;
	//TEST BIGDECIMAL
	BigDecimal valorTotalFinal = new BigDecimal(0);
	
	String dia;
	String mes;
	String ano;
	
	boolean nuevoCliente = false;
	
	Table table;
	
	/*
	 * Estructura Guia de Despacho
	 */
	public Shell createShell(final Display display) {
		
		final Shell shell = new Shell(display);
		shell.setText("Guia de despacho");
		shell.setImage(new Image(display, "media/bread-icon-3232.png"));
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gridLayout.marginHeight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginRight = 10;
		shell.setLayout(gridLayout);
		
		final Shell popupShell = new Shell(display, SWT.ON_TOP);
		popupShell.setLayout(new FillLayout());
		
		DAOGuiasImpl daoGuias = new DAOGuiasImpl();
		
		GridData gridTitulo = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridTitulo.horizontalSpan = 4;
		
		Label lblNumeroGuia = new Label(shell, SWT.NONE);
		lblNumeroGuia.setText("NUMERO:");
		
		GridData gridNumero = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		txtnumeroguia = new Text(shell, SWT.SINGLE | SWT.BORDER);
		txtnumeroguia.setLayoutData(gridNumero);
		
		if(daoGuias.getLastGuia() != null) {
			if(daoGuias.getLastGuia().getNumero() > 0) {
				numeroGuia = daoGuias.getLastGuia().getNumero();
				numeroGuia += 1;
			}
			
			txtnumeroguia.setText(""+numeroGuia);
		}
		else
			txtnumeroguia.setText(""+numeroGuia);
		
		Label lblFecha = new Label(shell, SWT.NONE);
		lblFecha.setText("FECHA (d/m/aaaa):");
		
		//Encontrar fecha de hoy
		dia = ""+Calendar.getInstance().get(Calendar.DATE);
		int mesInt = Calendar.getInstance().get(Calendar.MONTH);
		mesInt += 1;
		mes = ""+mesInt;
		ano = ""+Calendar.getInstance().get(Calendar.YEAR);
		
		GridData gridFecha = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		txtfechaguia = new Text(shell, SWT.SINGLE | SWT.BORDER);
		txtfechaguia.setLayoutData(gridFecha);
		txtfechaguia.setText(dia+"/"+mes+"/"+ano);
		
		/*
		 * Datos personales
		 */
		GridData gridPersonales = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridPersonales.horizontalSpan= 4;
		GridLayout gridLayoutPers = new GridLayout(4, false);
		gridLayoutPers.marginBottom = 15;
		gridLayoutPers.marginTop = 5;
		
		Group groupPersonales = new Group(shell, SWT.SHADOW_IN);
		groupPersonales.setText("Detalle de Venta");
		groupPersonales.setLayoutData(gridPersonales);
		groupPersonales.setLayout(gridLayoutPers);
		
		Label lblRut = new Label(groupPersonales, SWT.NONE);
		lblRut.setText("RUT:");
		GridData gridRut = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		lblRut.setLayoutData(gridRut);
		
		GridData gridText = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridText.widthHint = 200;
		
		rut = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		rut.setLayoutData(gridText);
		rut.setTextLimit(10);
		
		//Selector de clientes
		table = new Table(popupShell, SWT.SINGLE);
		
		new Label(groupPersonales, SWT.NONE).setText("Nombres:");
		
		nombre = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		nombre.setLayoutData(gridText);
		
		new Label(groupPersonales, SWT.NONE).setText("Apellidos:");
		
		apellido = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		apellido.setLayoutData(gridText);
		
		new Label(groupPersonales, SWT.NONE).setText("Ciudad:");
		
		ciudad = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		ciudad.setLayoutData(gridText);
		
		new Label(groupPersonales, SWT.NONE).setText("Comuna:");
		
		comuna = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		comuna.setLayoutData(gridText);
		
		new Label(groupPersonales, SWT.NONE).setText("Dirección:");
		
		direccion = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		direccion.setLayoutData(gridText);
		
		new Label(groupPersonales, SWT.NONE).setText("Giro:");
		
		giro = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		giro.setLayoutData(gridText);
		
		/*
		 * Items
		 */
		GridData gridItems = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridItems.horizontalSpan= 4;
		GridLayout gridLayoutGrp = new GridLayout(5, false);
		gridLayoutGrp.marginTop = 5;
		gridLayoutGrp.marginBottom = 10;
		
		Group groupItems = new Group(shell, SWT.SHADOW_IN);
		groupItems.setText("Detalle de Venta");
		groupItems.setLayoutData(gridItems);
		groupItems.setLayout(gridLayoutGrp);
		
		Label titCodigo = new Label(groupItems, SWT.NONE);
		titCodigo.setText("Codigo");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		titCodigo.setLayoutData(gridData);
		
		Label titCantidad = new Label(groupItems, SWT.NONE);
		titCantidad.setText("Cantidad");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		titCantidad.setLayoutData(gridData);
		
		Label titDetalle = new Label(groupItems, SWT.NONE);
		titDetalle.setText("Detalle");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.minimumWidth = 250;
		titDetalle.setLayoutData(gridData);
		
		Label titPUnit = new Label(groupItems, SWT.NONE);
		titPUnit.setText("P. Unitario");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		titPUnit.setLayoutData(gridData);
		
		Label titTotal = new Label(groupItems, SWT.NONE);
		titTotal.setText("Total");
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		titTotal.setLayoutData(gridData);
		
		/*
		 * Detalle venta
		 */
		
		/*
		 * fila 1
		 */
		codigo = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		codigo.setLayoutData(gridData);
		
		cantidad = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		cantidad.setLayoutData(gridData);
		
		detalle = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		detalle.setLayoutData(gridData);
		detalle.setEditable(false);
		
		precioUnit = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		precioUnit.setLayoutData(gridData);
		
		total = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		total.setText(""+0);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		total.setLayoutData(gridData);
		total.setEditable(false);
		
		/*
		 * fila 2
		 */
		codigo2 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		codigo2.setLayoutData(gridData);
		
		cantidad2 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		cantidad2.setLayoutData(gridData);
		
		detalle2 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		detalle2.setLayoutData(gridData);
		detalle2.setEditable(false);
		
		precioUnit2 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		precioUnit2.setLayoutData(gridData);
		
		total2 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		total2.setText(""+0);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 100;
		total2.setLayoutData(gridData);
		total2.setEditable(false);
		
		/*
		 * fila 3
		 */
		codigo3 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		codigo3.setLayoutData(gridData);
		
		cantidad3 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		cantidad3.setLayoutData(gridData);
		
		detalle3 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		detalle3.setLayoutData(gridData);
		detalle3.setEditable(false);
		
		precioUnit3 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		precioUnit3.setLayoutData(gridData);
		
		total3 = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		total3.setText(""+0);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		total3.setLayoutData(gridData);
		total3.setEditable(false);
		
		totalFinal = new Text(groupItems, SWT.SINGLE | SWT.BORDER);
		totalFinal.setText(""+0);
		totalFinal.setEditable(false);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.widthHint = 100;
		gridData.horizontalAlignment = SWT.END;
		gridData.horizontalSpan = 5;
		totalFinal.setLayoutData(gridData);
		totalFinal.setEditable(false);
		
		/*
		 * Botones
		 */
		Image imagen;
		
		final Button enter = new Button(shell, SWT.PUSH);
		gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 110;
		imagen = new Image(display, "media/Save-icon.png");
		
		enter.setAlignment(SWT.CENTER);
		enter.setImage(imagen);
		enter.setText("Guardar");
		enter.setLayoutData(gridData);
		
		final Button print = new Button(shell, SWT.PUSH);
		imagen = new Image(display, "media/Print-icon.png");
		print.setImage(imagen);
		print.setText("Guardar e imprimir");
		GridData gridPrint = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridPrint.horizontalSpan = 2;
		print.setLayoutData(gridPrint);
		
		/*
		 * Listeners
		 */
		enter.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				MessageBox dialog;
				
				/*
				 * Check valor campos
				 */
				if(!(txtnumeroguia.getText().equals("")) || !(txtfechaguia.getText().equals(""))) {
				
					/*
					 * Check cliente
					 */
					VOCliente cliente;
					DAOClientesImpl daoClientes = new DAOClientesImpl();					
					
					if(daoClientes.getClienteByRut(rut.getText()) != null){
						
						/*
						 * Check items
						 */
						
						
						if(!new BigDecimal(totalFinal.getText()).equals(0)) {
						
							System.out.println("Cliente ya existe y el total es mayor a 0");
						
							cliente = daoClientes.getClienteByRut(rut.getText());
						
							/*
							 * Cliente segun datos en campos
							 * Esto permite cambiar datos al momento de imprimir una factura
							 * Estos datos no seran guardados en la base de datos
							 */
							cliente.setDireccion(direccion.getText());
							cliente.setCiudad(ciudad.getText());
							cliente.setComuna(comuna.getText());
							cliente.setGiro(giro.getText());
							
							/*
							 * Verificar nueva guia antes de ingresar
							 */
							int style = SWT.ICON_QUESTION | SWT.YES | SWT.NO;
							dialog = new MessageBox(shell, style);
							dialog.setText("Confirmar Nueva Guía");
							dialog.setMessage("ATENCIÓN!\n ¿Esta seguro que desea ingresar la siguiente guía de despacho?\n\n"+
									"Fecha: "+txtfechaguia.getText()+" \n"+
									"Cliente: "+cliente.getNombres()+" "+cliente.getApellidos()+", "+cliente.getRut()+"\n" +
									""+cantidad.getText()+"Kg de "+detalle.getText()+" a $"+precioUnit.getText()+"\n"+
									"Total: $"+totalFinal.getText());
							
							if(dialog.open() == SWT.YES) {
							
								/*
								 * Guia de despacho
								 */
								DAOGuiasImpl daoGuias = new DAOGuiasImpl();
								
								
								System.out.println("fecha en campo: "+txtfechaguia.getText());
								System.out.println("total guia sin decimal: "+totalFinal.getText().substring(0, totalFinal.getText().indexOf(".")));
								
								daoGuias.newGuia(Integer.parseInt(txtnumeroguia.getText()), cliente, Utils.getFixedDate(txtfechaguia.getText()), new BigDecimal(totalFinal.getText()).setScale(0,BigDecimal.ROUND_HALF_UP).toBigInteger().intValue());
								VOGuia guia = daoGuias.getGuiaByNumero(Integer.parseInt(txtnumeroguia.getText()), false);
								
								/*
								 * items de productos
								 */
								DAOProductosImpl daoProductos = new DAOProductosImpl();
								VOItemProducto itemProducto = null;
								
								VOProducto producto;
								
								//Ingresar solo items donde el codigo este ingresado
								if(!codigo.getText().isEmpty()){
									System.out.println("Hay 1 producto");
									producto = daoProductos.getProductoByCodigo(codigo.getText());
									itemProducto = new VOItemProducto(producto,guia,Integer.parseInt(precioUnit.getText()),new BigDecimal(total.getText()),new BigDecimal(cantidad.getText()));
									daoGuias.newItemProducto(itemProducto);
								}
								if(!codigo2.getText().isEmpty()) {
									System.out.println("Hay 2 productos");
									producto = daoProductos.getProductoByCodigo(codigo2.getText());
									itemProducto = new VOItemProducto(producto,guia,Integer.parseInt(precioUnit2.getText()),new BigDecimal(total2.getText()),new BigDecimal(cantidad2.getText()));
									daoGuias.newItemProducto(itemProducto);
								}
								if(!codigo3.getText().isEmpty()) {
									System.out.println("Hay 3 productos");
									producto = daoProductos.getProductoByCodigo(codigo3.getText());
									itemProducto = new VOItemProducto(producto,guia,Integer.parseInt(precioUnit3.getText()),new BigDecimal(total3.getText()),new BigDecimal(cantidad3.getText()));
									daoGuias.newItemProducto(itemProducto);
								}
								
								//*********** DEBUG ************
								//System.out.println(itemProducto.getCantidad().toString());
								
								
								//get all items
								listaItems = daoGuias.getAllItemProductoByGuia(guia);
								
								//Muestra mensaje de resultado
								if(!listaItems.isEmpty()) {
									style = SWT.ICON_INFORMATION | SWT.OK;
									dialog = new MessageBox(shell, style);
									dialog.setText("Resultado");
									dialog.setMessage("Nueva guia de despacho ingresada.");
									dialog.open();
									shell.close();
								}
								else {
									style = SWT.ICON_ERROR | SWT.OK;
									dialog = new MessageBox(shell, style);
									dialog.setText("Error");
									dialog.setMessage("La guia no ha podido ser ingresada.");
									dialog.open();
									shell.close();
								}
								
							}//verificar nueva guia
						}
						else {
							int style = SWT.ICON_WARNING | SWT.OK;
							dialog = new MessageBox(shell, style);
							dialog.setText("Faltan Productos");
							dialog.setMessage("No se han ingresado productos. Es necesario al menos 1.");
							dialog.open();
							
							codigo.setFocus();
						}
					}
					else {
						int style = SWT.ICON_WARNING | SWT.OK;
						dialog = new MessageBox(shell, style);
						dialog.setText("Falta Cliente");
						dialog.setMessage("Es necesario encontrar un cliente para crear la guía.");
						dialog.open();
						
						rut.setText("");
						rut.setFocus();
					}
				}
				else {
					int style = SWT.ICON_WARNING | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Faltan Datos");
					dialog.setMessage("Es necesario ingresar número y fecha de guía.");
					dialog.open();
					
					txtnumeroguia.setFocus();
				}
					
			}//end save button action
		});
		
		print.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				
				boolean exCliente = false;
				boolean exItem = false;
				boolean exGuia = false;
				
				DAOClientesImpl daoClientes = new DAOClientesImpl();
				DAOGuiasImpl daoGuias = new DAOGuiasImpl();
				MessageBox dialog;
				
				/*
				 * Check fields
				 */
				if(daoClientes.getClienteByRut(rut.getText()) != null)
					exCliente = true;
				if(!new BigDecimal(totalFinal.getText()).equals(0))
					exItem = true;
					
				//check cliente
				if(!exCliente) {
					int style = SWT.ICON_WARNING | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Falta Cliente");
					dialog.setMessage("Es necesario seleccionar un cliente.");
					dialog.open();
					
					rut.setText("");
					rut.setFocus();
				}
				//check items
				else if(!exItem) {
					int style = SWT.ICON_WARNING | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Faltan Productos");
					dialog.setMessage("Es necesario ingresar al menos 1 producto (item) de venta.");
					dialog.open();
					
					codigo.setText("");
					codigo.setFocus();
				}
				//all checks good!
				else {
					/*
					 * cliente
					 */
					VOCliente cliente = daoClientes.getClienteByRut(rut.getText());

					/*
					 * Cliente segun datos en campos
					 * Esto permite cambiar datos al momento de imprimir una factura
					 * Estos datos no seran guardados en la base de datos
					 */
					cliente.setDireccion(direccion.getText());
					cliente.setCiudad(ciudad.getText());
					cliente.setComuna(comuna.getText());
					cliente.setGiro(giro.getText());
					
					/*
					 * Verificar nueva guia antes de ingresar
					 */
					int style = SWT.ICON_QUESTION | SWT.YES | SWT.NO;
					dialog = new MessageBox(shell, style);
					dialog.setText("Confirmar Nueva Guía");
					dialog.setMessage("ATENCIÓN!\n ¿Esta seguro que desea ingresar la siguiente guía de despacho?\n\n"+
							"Fecha: "+txtfechaguia.getText()+" \n"+
							"Cliente: "+cliente.getNombres()+" "+cliente.getApellidos()+", "+cliente.getRut()+"\n" +
							""+cantidad.getText()+"Kg de "+detalle.getText()+" a $"+precioUnit.getText()+"\n"+
							"Total: $"+totalFinal.getText());
					
					if(dialog.open() == SWT.YES) {
					
						/*
						 * guia de despacho
						 */
						daoGuias.newGuia(Integer.parseInt(txtnumeroguia.getText()), cliente, Utils.getFixedDate(txtfechaguia.getText()), new BigDecimal(totalFinal.getText()).setScale(0,BigDecimal.ROUND_HALF_UP).toBigInteger().intValue());
						VOGuia guia = daoGuias.getGuiaByNumero(Integer.parseInt(txtnumeroguia.getText()),false);
						
						/*
						 * Check if guia exists
						 */
						if(guia.getNumero() == Integer.parseInt(txtnumeroguia.getText()))
							exGuia = true;
						
						//guia check good
						if(exGuia) {
							style = SWT.ICON_INFORMATION | SWT.OK;
							dialog = new MessageBox(shell, style);
							dialog.setText("Resultado");
							dialog.setMessage("Nueva guia de despacho ingresada");
							dialog.open();
							
							/*
							 * items de productos
							 */
							DAOProductosImpl daoProductos = new DAOProductosImpl();
							VOItemProducto itemProducto;
							
							VOProducto producto;
							
							//Ingresar solo items donde el codigo este ingresado
							if(!codigo.getText().isEmpty()){
								System.out.println("Hay 1 producto");
								producto = daoProductos.getProductoByCodigo(codigo.getText());
								itemProducto = new VOItemProducto(producto,guia,Integer.parseInt(precioUnit.getText()),new BigDecimal(total.getText()),new BigDecimal(cantidad.getText()));
								daoGuias.newItemProducto(itemProducto);
							}
							if(!codigo2.getText().isEmpty()) {
								System.out.println("Hay 2 productos");
								producto = daoProductos.getProductoByCodigo(codigo2.getText());
								itemProducto = new VOItemProducto(producto,guia,Integer.parseInt(precioUnit2.getText()),new BigDecimal(total2.getText()),new BigDecimal(cantidad2.getText()));
								daoGuias.newItemProducto(itemProducto);
							}
							if(!codigo3.getText().isEmpty()) {
								System.out.println("Hay 3 productos");
								producto = daoProductos.getProductoByCodigo(codigo3.getText());
								itemProducto = new VOItemProducto(producto,guia,Integer.parseInt(precioUnit3.getText()),new BigDecimal(total3.getText()),new BigDecimal(cantidad3.getText()));
								daoGuias.newItemProducto(itemProducto);
							}
							
							//get all items
							listaItems = daoGuias.getAllItemProductoByGuia(guia);
							System.out.println("LGE numero de guias a imprimir: "+listaItems.size());
							
							if(!listaItems.isEmpty()) {
								style = SWT.ICON_QUESTION | SWT.YES | SWT.NO;
								dialog = new MessageBox(shell, style);
								dialog.setText("Impresión");
								dialog.setMessage("Prepare impresora. ¿Continuar imprimiendo?");
								
								if(dialog.open() == SWT.YES) {
									Utils.imprimirGuia(shell, guia, cliente, listaItems, txtfechaguia.getText());
									shell.close();
								}
								else {
									int style2 = SWT.ICON_INFORMATION | SWT.OK;
									dialog = new MessageBox(shell, style2);
									dialog.setText("Aviso");
									dialog.setMessage("No se ha impreso la guia de despacho.");
									dialog.open();
									shell.close();
								}
							}
							
						}
						else {
							style = SWT.ICON_ERROR | SWT.OK;
							dialog = new MessageBox(shell, style);
							dialog.setText("Error");
							dialog.setMessage("La guia no ha podido ser ingresada.");
							dialog.open();
							
							shell.close();
						}
						
					}//end verificar guia
					
				}//end guia existent fields check
				
			}
		});
		
		txtnumeroguia.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txtnumeroguia.getText().isEmpty() || txtnumeroguia.getText().equals("")){
					txtnumeroguia.setText(""+numeroGuia);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				txtnumeroguia.setSelection(0,txtnumeroguia.getCharCount());
			}
		});
		
		txtnumeroguia.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					txtfechaguia.forceFocus();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		txtfechaguia.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txtfechaguia.getText().isEmpty() || txtfechaguia.getText().equals("")){
					txtfechaguia.setText(dia+"/"+mes+"/"+ano);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				txtfechaguia.setSelection(0,txtfechaguia.getCharCount());
			}
		});

		txtfechaguia.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					rut.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		rut.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				
				//debug
				System.out.println("rut input: "+rut.getText());
				
				//Obtener datos de cliente cuando el rut es encontrado
				if(!rut.getText().equals("") || !rut.getText().isEmpty()){
					
					DAOClientesImpl daoClientes = new DAOClientesImpl();
					VOCliente cliente = daoClientes.getClienteByRut(rut.getText());
					
					//Completar campos si el objeto cliente existe
					if(cliente != null){
						if(!cliente.getNombres().isEmpty())
							nombre.setText(cliente.getNombres());
						else
							nombre.setText("");
						if(!cliente.getApellidos().isEmpty())
							apellido.setText(cliente.getApellidos());
						else
							apellido.setText("");
						if(!cliente.getCiudad().isEmpty())
							ciudad.setText(cliente.getCiudad());
						else
							ciudad.setText("");
						if(!cliente.getComuna().isEmpty())
							comuna.setText(cliente.getComuna());
						else
							comuna.setText("");
						if(!cliente.getDireccion().isEmpty())
							direccion.setText(cliente.getDireccion());
						else
							direccion.setText("");
						if(!cliente.getGiro().isEmpty())
							giro.setText(cliente.getGiro());
						else
							giro.setText("");
						
						codigo.setFocus();
					}
					else {
						System.out.println("no hay cliente");
						int style = SWT.ICON_QUESTION | SWT.YES | SWT.NO;
						MessageBox dialog = new MessageBox(shell, style);
						dialog.setText("Cliente no encontrado");
						dialog.setMessage("Este cliente no existe. ¿Desea crearlo?");
						
						if(dialog.open() == SWT.YES) {
							Shell shellFrmCliente = new FormCliente().createShell(display, "");
							shellFrmCliente.pack();
							shellFrmCliente.open();
						}
						else {
							rut.setFocus();
							rut.setSelection(0, rut.getCharCount());
						}
					}
				}
				else
					System.out.println("Rut vacio");
			}
			
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				rut.setSelection(0, rut.getCharCount());
			}
		});

		rut.addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				switch (event.keyCode) {
					case SWT.ARROW_DOWN:
						int index = (table.getSelectionIndex() + 1) % table.getItemCount();
						table.setSelection(index);
						event.doit = false;
						break;
					case SWT.ARROW_UP:
						index = table.getSelectionIndex() - 1;
						if (index < 0) index = table.getItemCount() - 1;
						table.setSelection(index);
						event.doit = false;
						break;
					case SWT.CR:
						//si hay seleccion de tabla al presionar Enter
						if (popupShell.isVisible() && table.getSelectionIndex() != -1) {
							rut.setText(Utils.getRutOnly(table.getSelection()[0].getText()));
							
							if(rut.getText().equals("")) {
								MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								dialog.setText("Aviso");
								dialog.setMessage("Debe ingresar un cliente.");
								dialog.open();
								
								rut.setFocus();
							}
							else {
								System.out.println("si hay rut en input");
								codigo.setFocus();
								codigo.setSelection(0, codigo.getCharCount());
							}
							
							popupShell.setVisible(false);
						}
						//no hay seleccion de tabla al presionar Enter
						else if(popupShell.isVisible() && table.getSelectionIndex() == -1){
							popupShell.setVisible(false);
							
							codigo.setFocus();
							codigo.setSelection(0, codigo.getCharCount());
						}
						else {
							if(rut.getText().equals("")) {
								MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								dialog.setText("Aviso");
								dialog.setMessage("Debe ingresar un cliente.");
								dialog.open();
								
								rut.setFocus();
							}
							else {
								System.out.println("si hay rut en input");
								codigo.setFocus();
								codigo.setSelection(0, codigo.getCharCount());
							}
						}
						break;
					case SWT.KEYPAD_CR:
						//si hay seleccion de tabla al presionar Enter
						if (popupShell.isVisible() && table.getSelectionIndex() != -1) {
							rut.setText(Utils.getRutOnly(table.getSelection()[0].getText()));
							
							if(rut.getText().equals("")) {
								MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								dialog.setText("Aviso");
								dialog.setMessage("Debe ingresar un cliente.");
								dialog.open();
								
								rut.setFocus();
							}
							else {
								System.out.println("si hay rut en input");
								codigo.setFocus();
								codigo.setSelection(0, codigo.getCharCount());
							}
							
							popupShell.setVisible(false);
						}
						//no hay seleccion de tabla al presionar Enter
						else if(popupShell.isVisible() && table.getSelectionIndex() == -1){
							popupShell.setVisible(false);
							
							codigo.setFocus();
							codigo.setSelection(0, codigo.getCharCount());
						}
						else {
							if(rut.getText().equals("")) {
								MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								dialog.setText("Aviso");
								dialog.setMessage("Debe ingresar un cliente.");
								dialog.open();
								
								rut.setFocus();
							}
							else {
								System.out.println("si hay rut en input");
								codigo.setFocus();
								codigo.setSelection(0, codigo.getCharCount());
							}
						}
						break;
					case SWT.ESC:
						if(popupShell.isVisible())
							popupShell.setVisible(false);
						break;
				}
			}
		});

		rut.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				String string = rut.getText();
				if (string.length() == 0) {
					popupShell.setVisible(false);
				} 
				else if(rut.getText().length() > 0) {
					Utils.buildFoundTable(table, rut.getText());
					
					Rectangle textBounds = display.map(shell, null, rut.getBounds());
					popupShell.setBounds(textBounds.x, textBounds.y + 60, textBounds.width + 100, 280);
					popupShell.setVisible(true);
				}
			}
		});

		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("@down!");
				rut.setText(Utils.getRutOnly(table.getSelection()[0].getText()));
				rut.forceFocus();
				rut.setSelection(0, rut.getCharCount());
				
				popupShell.setVisible(false);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		nombre.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				nombre.setSelection(0,nombre.getCharCount());
			}
		});
		
		nombre.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					apellido.setFocus();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		apellido.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				apellido.setSelection(0,apellido.getCharCount());
			}
		});
		
		apellido.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					ciudad.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ciudad.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				ciudad.setSelection(0,ciudad.getCharCount());
			}
		});
		
		ciudad.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					comuna.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		comuna.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				comuna.setSelection(0,comuna.getCharCount());
			}
		});
		
		comuna.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					direccion.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		direccion.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				direccion.setSelection(0,direccion.getCharCount());
			}
		});
		
		direccion.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					giro.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		giro.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				giro.setSelection(0,giro.getCharCount());
			}
		});
		
		giro.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					codigo.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		codigo.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				
				if(!codigo.getText().isEmpty() || !codigo.getText().equals("")){
					DAOProductosImpl daoProductos = new DAOProductosImpl();
					producto = new VOProducto();
					producto = daoProductos.getProductoByCodigo(codigo.getText());
					
					if(producto != null){
						int ultimoPrecio = getLastPrecio(rut.getText(), producto.getId());
						
						detalle.setText(producto.getDescripcion());
						
						if(ultimoPrecio == 0) {
							precioUnit.setText(""+producto.getPrecio());
							System.out.println("no se encontro un ultimo precio");
						}
						else
							precioUnit.setText(""+ultimoPrecio);
						
						valorTotalFinal = new BigDecimal(total.getText());
						System.out.println("vtf 1: "+valorTotalFinal.toString());
						
						valorTotalFinal = valorTotalFinal.add(new BigDecimal(total2.getText()));
						System.out.println("vtf 2: "+valorTotalFinal.toString());
						
						valorTotalFinal = valorTotalFinal.add(new BigDecimal(total3.getText()));
						System.out.println("vtf 3: "+valorTotalFinal.toString());
						
						totalFinal.setText(valorTotalFinal.toString());
					}
					else {
						detalle.setText("PRODUCTO NO ENCONTRADO");
						
						//debug
						System.out.println("no hay producto");
						
						int style = SWT.ICON_QUESTION | SWT.YES | SWT.NO;
						MessageBox dialog = new MessageBox(shell, style);
						dialog.setText("Producto no encontrado");
						dialog.setMessage("Este producto no existe. ¿Desea crearlo?");
						
						if(dialog.open() == SWT.YES) {
							Shell shellFrmProducto = new FormProducto().createShell(display, "");
							shellFrmProducto.pack();
							shellFrmProducto.open();
						}
						else {
							codigo.setFocus();
							codigo.setSelection(0, codigo.getCharCount());
						}
					}
				}
			}
			
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				codigo.setSelection(0,codigo.getCharCount());
			}
		});
		
		codigo.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					if(!codigo.getText().equals(""))
						cantidad.setFocus();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		cantidad.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				if(!cantidad.getText().isEmpty() || !cantidad.getText().equals("")){
					
					//replace "," with "."
					if(cantidad.getText().contains(","))
						cantidad.setText(cantidad.getText().replace(",","."));
					
					BigDecimal valorTotal = new BigDecimal(precioUnit.getText()); //test
					
					if(!precioUnit.getText().isEmpty() || !precioUnit.getText().equals("")){
						//valorTotal = Integer.parseInt(precioUnit.getText()) * Integer.parseInt(cantidad.getText());
						
						//TEST BIGDECIMAL
						//Calc total con Bigdecimal
						valorTotal = valorTotal.multiply(new BigDecimal(cantidad.getText()));
					}
					
					//testing bigdecimal
					total.setText(valorTotal.toString());
					System.out.println("cantidad = "+cantidad.getText());
					System.out.println("total.toString = "+valorTotal.toString());
					
					// AQUI
					//valorTotalFinal = Integer.parseInt(total.getText()) + Integer.parseInt(total2.getText()) + Integer.parseInt(total3.getText());
					//totalFinal.setText(""+valorTotalFinal);
					
					valorTotalFinal = new BigDecimal(total.getText());
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total2.getText()));
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total3.getText()));
					
					totalFinal.setText(valorTotalFinal.toString());
				}
			}
			
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				cantidad.setSelection(0,cantidad.getCharCount());
			}
		});
		
		cantidad.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					precioUnit.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		precioUnit.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				if(!precioUnit.getText().isEmpty() || !precioUnit.getText().equals("")){
					//int valorTotal = Integer.parseInt(precioUnit.getText()) * Integer.parseInt(cantidad.getText());
					//total.setText(""+valorTotal);
					BigDecimal valorTotal = new BigDecimal(precioUnit.getText());
					valorTotal = valorTotal.multiply(new BigDecimal(cantidad.getText()));
					
					System.out.println("total = "+valorTotal.toString());
					total.setText(valorTotal.toString());
					
					//valorTotalFinal = Integer.parseInt(total.getText()) + Integer.parseInt(total2.getText()) + Integer.parseInt(total3.getText());
					//totalFinal.setText(""+valorTotalFinal);
					
					valorTotalFinal = new BigDecimal(total.getText());
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total2.getText()));
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total3.getText()));
					
					totalFinal.setText(valorTotalFinal.toString());
				}
			}

			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				precioUnit.setSelection(0,precioUnit.getCharCount());
			}
		});
		
		precioUnit.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					codigo2.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		codigo2.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				if(!codigo2.getText().isEmpty() || !codigo2.getText().equals("")){
					DAOProductosImpl daoProductos = new DAOProductosImpl();
					producto = new VOProducto();
					producto = daoProductos.getProductoByCodigo(codigo2.getText());
					if(producto != null){
						detalle2.setText(producto.getDescripcion());
						
						int ultimoPrecio = getLastPrecio(rut.getText(), producto.getId());
						if(ultimoPrecio == 0) {
							precioUnit2.setText(""+producto.getPrecio());
							System.out.println("no se encontro un ultimo precio");
						}
						else
							precioUnit2.setText(""+ultimoPrecio);
						
						System.out.println("producto: "+producto.getNombre());
						
						//valorTotalFinal = Integer.parseInt(total.getText()) + Integer.parseInt(total2.getText()) + Integer.parseInt(total3.getText());
						//totalFinal.setText(""+valorTotalFinal);
						
						valorTotalFinal = new BigDecimal(total.getText());
						valorTotalFinal = valorTotalFinal.add(new BigDecimal(total2.getText()));
						valorTotalFinal = valorTotalFinal.add(new BigDecimal(total3.getText()));
						
						totalFinal.setText(valorTotalFinal.toString());
					}
					else
						detalle2.setText("PRODUCTO NO ENCONTRADO");
				}
			}
			
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				codigo2.setSelection(0,codigo2.getCharCount());
			}
		});
		
		codigo2.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					if(!codigo2.getText().equals(""))
						cantidad2.setFocus();
					else {
						print.setFocus();
						print.setSelection(true);
					}
				}
						
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		cantidad2.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				if(!cantidad2.getText().isEmpty() || !cantidad2.getText().equals("")){
					
					//replace "," with "."
					if(cantidad2.getText().contains(","))
						cantidad2.setText(cantidad2.getText().replace(",","."));
					
					BigDecimal valorTotal = new BigDecimal(precioUnit2.getText());
					
					if(!precioUnit2.getText().isEmpty())
						valorTotal = valorTotal.multiply(new BigDecimal(cantidad2.getText()));
					
					
					//if(!precioUnit2.getText().isEmpty())
					//	valorTotal = Integer.parseInt(precioUnit2.getText()) * Integer.parseInt(cantidad2.getText());
					
					total2.setText(valorTotal.toString());
					System.out.println("total = "+valorTotal.toString());
					
					//valorTotalFinal = Integer.parseInt(total.getText()) + Integer.parseInt(total2.getText()) + Integer.parseInt(total3.getText());
					//totalFinal.setText(""+valorTotalFinal);
					
					valorTotalFinal = new BigDecimal(total.getText());
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total2.getText()));
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total3.getText()));
					
					totalFinal.setText(valorTotalFinal.toString());
				}
			}
			
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				cantidad2.setSelection(0,cantidad2.getCharCount());
			}
		});
		
		cantidad2.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					precioUnit2.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		precioUnit2.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				if(!precioUnit2.getText().isEmpty() || !precioUnit2.getText().equals("")){
					
					
					BigDecimal valorTotal = new BigDecimal(precioUnit2.getText());
					
					if(!precioUnit2.getText().isEmpty())
						valorTotal = valorTotal.multiply(new BigDecimal(cantidad2.getText()));
					
					//int valorTotal = Integer.parseInt(precioUnit2.getText()) * Integer.parseInt(cantidad2.getText());
					//total2.setText(""+valorTotal);
					//System.out.println("total = "+valorTotal);
					
					total2.setText(valorTotal.toString());
					
					//valorTotalFinal = Integer.parseInt(total.getText()) + Integer.parseInt(total2.getText()) + Integer.parseInt(total3.getText());
					//totalFinal.setText(""+valorTotalFinal);
					
					valorTotalFinal = new BigDecimal(total.getText());
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total2.getText()));
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total3.getText()));
					
					totalFinal.setText(valorTotalFinal.toString());
				}
			}

			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				precioUnit2.setSelection(0,precioUnit2.getCharCount());
			}
		});
		
		precioUnit2.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					codigo3.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		codigo3.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				if(!codigo3.getText().isEmpty() || !codigo3.getText().equals("")){
					DAOProductosImpl daoProductos = new DAOProductosImpl();
					producto = new VOProducto();
					producto = daoProductos.getProductoByCodigo(codigo3.getText());
					if(producto != null) {
						if(!producto.getNombre().isEmpty()){
							detalle3.setText(producto.getDescripcion());
							
							int ultimoPrecio = getLastPrecio(rut.getText(), producto.getId());
							if(ultimoPrecio < 1) {
								precioUnit3.setText(""+producto.getPrecio());
								System.out.println("no se encontro un ultimo precio");
							}
							else
								precioUnit3.setText(""+ultimoPrecio);
							
							System.out.println("producto: "+producto.getNombre());
							
							//valorTotalFinal = Integer.parseInt(total.getText()) + Integer.parseInt(total2.getText()) + Integer.parseInt(total3.getText());
							//totalFinal.setText(""+valorTotalFinal);
							
							valorTotalFinal = new BigDecimal(total.getText());
							valorTotalFinal = valorTotalFinal.add(new BigDecimal(total2.getText()));
							valorTotalFinal = valorTotalFinal.add(new BigDecimal(total3.getText()));
							
							totalFinal.setText(valorTotalFinal.toString());
						}
					}
					else
						detalle3.setText("PRODUCTO NO ENCONTRADO");
				}
			}
			
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				codigo3.setSelection(0,codigo3.getCharCount());
			}
		});
		
		codigo3.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					if(!codigo3.getText().equals(""))
						cantidad3.setFocus();
					else {
						print.setFocus();
						print.setSelection(true);
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		cantidad3.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				if(!cantidad3.getText().isEmpty() || !cantidad3.getText().equals("")){
					
					//replace "," with "."
					if(cantidad3.getText().contains(","))
						cantidad3.setText(cantidad3.getText().replace(",","."));
					
					BigDecimal valorTotal = new BigDecimal(precioUnit3.getText());
					
					if(!precioUnit3.getText().isEmpty())
						valorTotal = valorTotal.multiply(new BigDecimal(cantidad3.getText()));					
					
					//int valorTotal = 0;
					//if(!precioUnit3.getText().isEmpty())
					//	valorTotal = Integer.parseInt(precioUnit3.getText()) * Integer.parseInt(cantidad3.getText());
					
					total3.setText(valorTotal.toString());
					System.out.println("total = "+valorTotal.toString());
					
					//valorTotalFinal = Integer.parseInt(total.getText()) + Integer.parseInt(total2.getText()) + Integer.parseInt(total3.getText());
					//totalFinal.setText(""+valorTotalFinal);
					
					valorTotalFinal = new BigDecimal(total.getText());
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total2.getText()));
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total3.getText()));
					
					totalFinal.setText(valorTotalFinal.toString());
				}
			}
			
			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				cantidad3.setSelection(0,cantidad3.getCharCount());
			}
		});
		
		cantidad3.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					precioUnit3.setFocus();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		precioUnit3.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(org.eclipse.swt.events.FocusEvent e) {
				if(!precioUnit3.getText().isEmpty() || !precioUnit3.getText().equals("")){
					// TODO Auto-generated method stub
					
					//int valorTotal = Integer.parseInt(precioUnit3.getText()) * Integer.parseInt(cantidad3.getText());
					//total3.setText(""+valorTotal);
					//System.out.println("total = "+valorTotal);
					
					BigDecimal valorTotal = new BigDecimal(precioUnit3.getText());
					valorTotal = valorTotal.multiply(new BigDecimal(cantidad3.getText()));
					total3.setText(valorTotal.toString());
					
					//valorTotalFinal = Integer.parseInt(total.getText()) + Integer.parseInt(total2.getText()) + Integer.parseInt(total3.getText());
					//totalFinal.setText(""+valorTotalFinal);
					
					valorTotalFinal = new BigDecimal(total.getText());
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total2.getText()));
					valorTotalFinal = valorTotalFinal.add(new BigDecimal(total3.getText()));
					
					totalFinal.setText(valorTotalFinal.toString());
				}
			}

			@Override
			public void focusGained(org.eclipse.swt.events.FocusEvent e) {
				// TODO Auto-generated method stub
				precioUnit3.setSelection(0,precioUnit3.getCharCount());
			}
		});
		
		precioUnit3.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
					print.setFocus();
					print.setSelection(true);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		Listener focusOutListener = new Listener() {
			public void handleEvent(Event event) {
				/* async is needed to wait until focus reaches its new Control */
				display.asyncExec(new Runnable() {
					public void run() {
						if (display.isDisposed()) return;
						Control control = display.getFocusControl();
						if (control == null || (control != rut && control != table)) {
							popupShell.setVisible(false);
						}
					}
				});
			}
		};

		table.addListener(SWT.FocusOut, focusOutListener);
		
		rut.addListener(SWT.FocusOut, focusOutListener);
		
		shell.addListener(SWT.Traverse, new Listener() {
		      public void handleEvent(Event event) {
		        if(event.detail == SWT.TRAVERSE_ESCAPE) {
		        	if(!popupShell.isVisible()){
			          shell.close();
			          event.detail = SWT.TRAVERSE_NONE;
			          event.doit = false;
		        	}
		        }
		      }
		});
		
		shell.addListener(SWT.Move, new Listener() {
			public void handleEvent(Event event) {
				popupShell.setVisible(false);
			}
		});
		
		shell.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(popupShell.isVisible())
					popupShell.setVisible(false);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		shell.pack();
		
		return shell;
	}
	
	public int getLastPrecio(String rut, int producto) {
		
		System.out.println("DEBUG getlastprecio: "+rut+","+producto);
		
		int ultimoPrecio = 0;
		VOCliente cliente = new DAOClientesImpl().getClienteByRut(rut);
		System.out.println("DEBUG cliente: "+cliente.getId());
		
		VOGuia guia = new DAOGuiasImpl().getLastGuiaByCliente(cliente);
		
		/*
		 * Es posible que este cliente no tenga ninguna guia
		 */
		if(guia != null) {
			System.out.println("DEBUG guia: "+guia.getNumero()+","+guia.getId());
			
			ArrayList<VOItemProducto> listaItems = new DAOGuiasImpl().getAllItemProductoByGuia(guia);
			
			for(int x=0; x<listaItems.size(); x++) {
				VOItemProducto item = listaItems.get(x);
				
				System.out.println("DEBUG item: "+item.getIdprod().getId());
				System.out.println("DEBUG item precio: "+item.getPrecio());
				if(item.getIdprod().getId() == producto)
					ultimoPrecio = item.getPrecio();
			}

		}
		else
			System.out.println("DEBUG cliente sin guias");
		
		return ultimoPrecio;
	}
	
}