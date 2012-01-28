package cl.ht.facturacion.client.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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
import cl.ht.facturacion.client.vo.VOFactura;
import cl.ht.facturacion.client.vo.VOGuia;
import cl.ht.facturacion.dao.impl.DAOClientesImpl;
import cl.ht.facturacion.dao.impl.DAOFacturasImpl;
import cl.ht.facturacion.dao.impl.DAOGuiasImpl;

public class NuevaFactura {

	/*
	 * Elementos
	 */
	Text txtNumeroFactura;
	Text txtFechaFactura;
	
	Text txtCliente;
	
	Combo mesFacturacion;
	
	Label lblImgFound = null;
	Label lblImgNotFound = null;
	
	Text infoArea;
	
	Button btnSave;
	Button btnSavePrint;
	
	int numeroFactura = 1000;
	
	Table table;
	
	public Shell createShell(final Display display) {
		
		final Shell shell = new Shell(display);
		shell.setText("Facturacion");
		
		final Shell popupShell = new Shell(display, SWT.ON_TOP);
		popupShell.setLayout(new FillLayout());
		
		/*
		 * Grilla principal
		 */
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gridLayout.marginHeight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginRight = 10;
		
		shell.setLayout(gridLayout);
		shell.setImage(new Image(display, "media/bread-icon-3232.png"));

		/*
		 * Numero y Fecha de Factura
		 */
		GridData gridDatosTop = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		
		Label lblNumeroFactura = new Label(shell, SWT.NONE);
		lblNumeroFactura.setText("NUMERO:");
		
		gridDatosTop = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridDatosTop.widthHint = 80;
		
		txtNumeroFactura = new Text(shell, SWT.SINGLE | SWT.BORDER);
		txtNumeroFactura.setLayoutData(gridDatosTop);
		
		/*
		 * Numeracion de factura dinamica
		 */
		DAOFacturasImpl daoFacturas = new DAOFacturasImpl();
		
		if(daoFacturas.getLastFactura() != null) {
			if(daoFacturas.getLastFactura().getNumero() > 0) {
				numeroFactura = daoFacturas.getLastFactura().getNumero();
				numeroFactura += 1;
			}
			
			txtNumeroFactura.setText(""+numeroFactura);
		}
		else
			txtNumeroFactura.setText(""+numeroFactura);
		
		gridDatosTop = new GridData(GridData.END, GridData.CENTER, false, false);
		
		Label lblFechaFactura = new Label(shell, SWT.NONE);
		lblFechaFactura.setText("FECHA (d/m/aaaa):");
		lblFechaFactura.setLayoutData(gridDatosTop);
		
		gridDatosTop = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridDatosTop.horizontalAlignment = SWT.BEGINNING;
		gridDatosTop.widthHint = 80;
		
		//Fecha de hoy
		//Encontrar fecha de hoy
		String dia = ""+Calendar.getInstance().get(Calendar.DATE);
		int mesInt = Calendar.getInstance().get(Calendar.MONTH);
		mesInt += 1;
		String mes = ""+mesInt;
		String ano = ""+Calendar.getInstance().get(Calendar.YEAR);
		
		txtFechaFactura = new Text(shell, SWT.SINGLE | SWT.BORDER);
		txtFechaFactura.setLayoutData(gridDatosTop);
		txtFechaFactura.setText(dia+"/"+mes+"/"+ano);
		
		/*
		 * Ghost para dejar espacio entre elementos
		 */
		GridData gridGhost = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridGhost.horizontalSpan = 4;
		gridGhost.heightHint = 10;
		Label lblghost = new Label(shell, SWT.NONE);
		lblghost.setText("");
		lblghost.setLayoutData(gridGhost);
		
		/*
		 * Elementos para confeccion de factura
		 */
		GridData gridDatos = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridDatos.horizontalSpan= 4;
		
		GridLayout gridLayoutDatos = new GridLayout(4, false);
		gridLayoutDatos.marginBottom = 10;
		gridLayoutDatos.marginTop = 5;
		
		final Group groupDatos = new Group(shell, SWT.SHADOW_IN);
		groupDatos.setText("Datos de facturación");
		groupDatos.setLayoutData(gridDatos);
		groupDatos.setLayout(gridLayoutDatos);
		
		GridData gridEtiqueta = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridEtiqueta.horizontalSpan = 2;
		
		Label lblCliente = new Label(groupDatos, SWT.NONE);
		lblCliente.setText("Cliente");
		lblCliente.setLayoutData(gridEtiqueta);
		
		gridEtiqueta = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridEtiqueta.horizontalSpan = 2;
		
		Label lblFechaDesde = new Label(groupDatos, SWT.NONE);
		lblFechaDesde.setText("Mes");
		lblFechaDesde.setLayoutData(gridEtiqueta);
		
		GridData gridText = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridText.horizontalSpan = 2;
		gridText.widthHint = 150;
		
		txtCliente = new Text(groupDatos, SWT.SINGLE | SWT.BORDER);
		txtCliente.setLayoutData(gridText);
		
		//Lista clientes para seleccionar
		table = new Table(popupShell, SWT.SINGLE);
		
		gridText = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridText.horizontalSpan = 2;
		gridText.widthHint = 130;
		
		String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		mesFacturacion = new Combo(groupDatos, SWT.DROP_DOWN);
	    mesFacturacion.setItems(meses);
	    mesFacturacion.setLayoutData(gridText);
	    mesInt -= 2;
	    System.out.println("mes int: "+mesInt);
	    mesFacturacion.select(mesInt); //mes anterior preseleccionado
	    
		/*
		 * Ghost para dejar espacio entre elementos
		 */
		gridGhost = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridGhost.horizontalSpan = 4;
		gridGhost.heightHint = 5;
		lblghost = new Label(groupDatos, SWT.NONE);
		lblghost.setText("");
		lblghost.setLayoutData(gridGhost);
		
		gridText = new GridData(SWT.FILL, GridData.CENTER, false, false);
		gridText.horizontalSpan = 3;
		gridText.heightHint = 80;
		
		infoArea = new Text(groupDatos, SWT.MULTI | SWT.BORDER);
		infoArea.setLayoutData(gridText);
		infoArea.setEditable(false);
		
		gridText = new GridData(SWT.FILL, GridData.CENTER, false, false);
		gridText.horizontalSpan = 1;
		gridText.heightHint = 80;

		lblImgFound = new Label(groupDatos, SWT.NONE);
		lblImgFound.setLayoutData(gridText);
		lblImgFound.setAlignment(SWT.CENTER);

		/*
		 * Botones de accion
		 */
		Image imagen;
		
		GridData gridBoton = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridBoton.horizontalSpan = 2;
		
		imagen = new Image(display, "media/Save-icon.png");
		
		btnSave = new Button(shell, SWT.PUSH);
		btnSave.setEnabled(false);
		btnSave.setAlignment(SWT.CENTER);
		btnSave.setImage(imagen);
		btnSave.setText("Guardar");
		btnSave.setLayoutData(gridBoton);
		
		/*
		 * Guardar
		 */
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				Calendar fechaFactura = Utils.getFixedDate(txtFechaFactura.getText());
				Calendar fechaDesde = Utils.getDateByMonth(mesFacturacion.getText(),null);
				Calendar fechaHasta = Utils.getNextDateByMonth(mesFacturacion.getText(),null);
				
				System.out.println("mes entrado: "+mesFacturacion.getText());
				System.out.println("fecha desde: "+fechaDesde.getTime());
				System.out.println("fecha hasta: "+fechaHasta.getTime());
				
				DAOClientesImpl daoClientes = new DAOClientesImpl();
				DAOGuiasImpl daoGuias = new DAOGuiasImpl();
				DAOFacturasImpl daoFacturas = new DAOFacturasImpl();
				ArrayList<VOGuia> listaGuias = new ArrayList<VOGuia>();
				VOFactura factura = null;
				
				VOCliente cliente = daoClientes.getClienteByRut(txtCliente.getText());

				/*
				 * Ingresar nueva factura
				 */
				listaGuias = daoGuias.getAllGuiasByClienteDateNoFacturadas(cliente, fechaDesde, fechaHasta, false);
				
				MessageBox dialog;
				
				if(listaGuias.isEmpty()) {
					int style = SWT.ICON_ERROR | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Resultado");
					dialog.setMessage("No existen guías sin facturar durante este período");
					dialog.open();
					
					mesFacturacion.setFocus();
				}
				else {
					String guiasFacturar = "";
					Iterator<VOGuia> iter = listaGuias.iterator();
					while(iter.hasNext()) {
						guiasFacturar += iter.next().getNumero()+",";
					}
					
					int style = SWT.ICON_QUESTION | SWT.YES | SWT.NO;
					dialog = new MessageBox(shell, style);
					dialog.setText("Confirmar nueva factura");
					dialog.setMessage("ATENCION! ¿Esta seguro que sea facturar las siguientes guias?\n"+guiasFacturar);
					
					if(dialog.open() == SWT.YES) {
						daoFacturas.newFactura(Integer.parseInt(txtNumeroFactura.getText()), cliente, listaGuias, fechaFactura);
						factura = daoFacturas.getFacturaByNumero(Integer.parseInt(txtNumeroFactura.getText()));
					}
					
					if(factura != null) {
						/*
						 * Establecer guias como facturadas
						 */
						VOGuia guia;
						iter = listaGuias.iterator();
						while(iter.hasNext()){
							guia = iter.next();
							daoGuias.facturarGuia(guia, factura);
						}
						
						style = SWT.ICON_INFORMATION | SWT.OK;
						dialog = new MessageBox(shell, style);
						dialog.setText("Resultado");
						dialog.setMessage("Factura ingresada.");
						dialog.open();
						
						shell.close();
					}
					else{
						style = SWT.ICON_ERROR | SWT.OK;
						dialog = new MessageBox(shell, style);
						dialog.setText("Error");
						dialog.setMessage("La factura no ha sido ingresada.");
						dialog.open();
						
						shell.close();
					}
						
				}

			}
		});
		
		gridBoton = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridBoton.horizontalSpan = 2;
		
		imagen = new Image(display, "media/Print-icon.png");
		
		/*
		 * Guardar e Imprimir
		 */
		btnSavePrint = new Button(shell, SWT.PUSH);
		btnSavePrint.setEnabled(false);
		btnSavePrint.setAlignment(SWT.CENTER);
		btnSavePrint.setImage(imagen);
		btnSavePrint.setText("Guardar e Imprimir");
		btnSavePrint.setLayoutData(gridBoton);
		
		btnSavePrint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				Calendar fechaFactura = Utils.getFixedDate(txtFechaFactura.getText());
				Calendar fechaDesde = Utils.getDateByMonth(mesFacturacion.getText(),null);
				Calendar fechaHasta = Utils.getNextDateByMonth(mesFacturacion.getText(),null);
				
				System.out.println("mes entrado: "+mesFacturacion.getText());
				System.out.println("fecha desde: "+fechaDesde.getTime());
				System.out.println("fecha hasta: "+fechaHasta.getTime());
				
				DAOClientesImpl daoClientes = new DAOClientesImpl();
				DAOGuiasImpl daoGuias = new DAOGuiasImpl();
				DAOFacturasImpl daoFacturas = new DAOFacturasImpl();
				ArrayList<VOGuia> listaGuias = new ArrayList<VOGuia>();
				VOFactura factura = null;
				
				VOCliente cliente = daoClientes.getClienteByRut(txtCliente.getText());
				
				/*
				 * Conseguir guias sin facturar de esa fecha
				 */
				listaGuias = daoGuias.getAllGuiasByClienteDateNoFacturadas(cliente, fechaDesde, fechaHasta, false);
				
				//Muestra mensaje de resultado
				MessageBox dialog;
				
				if(listaGuias.isEmpty()) {
					int style = SWT.ICON_ERROR | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Resultado");
					dialog.setMessage("No existen guías sin facturar durante este período");
					dialog.open();
					
					mesFacturacion.setFocus();
				}
				else {
					int style = SWT.ICON_INFORMATION | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Resultado");
					
					String guiasFacturar = "";
					Iterator<VOGuia> iter = listaGuias.iterator();
					while(iter.hasNext()) {
						guiasFacturar += iter.next().getNumero()+",";
					}
					
					style = SWT.ICON_QUESTION | SWT.YES | SWT.NO;
					dialog = new MessageBox(shell, style);
					dialog.setText("Confirmar nueva factura");
					dialog.setMessage("ATENCION! ¿Esta seguro que sea facturar las siguientes guias?\n"+guiasFacturar);

					if(dialog.open() == SWT.YES) {
						daoFacturas.newFactura(Integer.parseInt(txtNumeroFactura.getText()), cliente, listaGuias, fechaFactura);
						factura = daoFacturas.getFacturaByNumero(Integer.parseInt(txtNumeroFactura.getText()));
					}
					
					if(factura != null){
						/*
						 * Establecer guias como facturadas
						 */
						VOGuia guia;
						iter = listaGuias.iterator();
						while(iter.hasNext()){
							guia = iter.next();
							daoGuias.facturarGuia(guia, factura);
						}
						
						style = SWT.ICON_INFORMATION | SWT.OK;
						dialog = new MessageBox(shell, style);
						dialog.setText("Resultado");
						dialog.setMessage("Factura ingresada");
						dialog.open();
						
						dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
						dialog.setText("Impresión");
						dialog.setMessage("Prepare impresora. ¿Continuar imprimiendo?");
						
						if(dialog.open() == SWT.YES) {
							Utils.imprimirFactura(shell, factura, cliente, txtFechaFactura.getText());
							
							shell.close();
						}
						else {
							int style2 = SWT.ICON_INFORMATION | SWT.OK;
							dialog = new MessageBox(shell, style2);
							dialog.setText("Aviso");
							dialog.setMessage("No se ha impreso la factura.");
							dialog.open();
							
							shell.close();
						}
					}
					else{
						style = SWT.ICON_ERROR | SWT.OK;
						dialog = new MessageBox(shell, style);
						dialog.setText("Error");
						dialog.setMessage("No ha sido posible ingresar la factura.");
						dialog.open();
						
						shell.close();
					}
				}
				
			}
		});

		/*
		 * Listeners
		 */
		txtNumeroFactura.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(txtNumeroFactura.getText().isEmpty() || txtNumeroFactura.getText().equals("")){
					txtNumeroFactura.setText(""+numeroFactura);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				txtNumeroFactura.setSelection(0,txtNumeroFactura.getCharCount());
			}
		});
		
		txtNumeroFactura.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					txtFechaFactura.setFocus();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		txtFechaFactura.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					txtCliente.forceFocus();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		txtFechaFactura.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				txtFechaFactura.setSelection(0,txtFechaFactura.getCharCount());
			}
		});

		txtCliente.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				DAOClientesImpl daoClientes = new DAOClientesImpl();
				VOCliente cliente = new VOCliente();
				
				cliente = daoClientes.getClienteByRut(txtCliente.getText());
				
				if(cliente != null) {
					infoArea.setText("");
					
					infoArea.setFont(new Font(display, Utils.changeFont(infoArea, 11, false)));
					infoArea.setForeground(new Color(shell.getDisplay(), new RGB(0,128,0)));
					infoArea.append("Cliente encontrado: \n");
					infoArea.append(cliente.getNombres()+" "+cliente.getApellidos());

					lblImgFound.setImage(new Image(display, "media/Check-icon.png"));

					btnSave.setEnabled(true);
					btnSavePrint.setEnabled(true);
				}
				else {
					infoArea.setText("");
					
					infoArea.setFont(new Font(display, Utils.changeFont(infoArea, 11, false)));
					infoArea.setForeground(new Color(shell.getDisplay(), new RGB(255,0,0)));
					infoArea.append("Cliente no encontrado.");
					
					lblImgFound.setImage(new Image(display, "media/Delete-icon.png"));

					btnSave.setEnabled(false);
					btnSavePrint.setEnabled(false);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				txtCliente.setSelection(0,txtCliente.getCharCount());
			}
		});
		
		txtCliente.addListener(SWT.KeyDown, new Listener() {
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
							txtCliente.setText(Utils.getRutOnly(table.getSelection()[0].getText()));
							
							System.out.println("si hay rut en input");
							mesFacturacion.setFocus();
//							mesFacturacion.select(0);
							
							popupShell.setVisible(false);
						}
						//no hay seleccion de tabla al presionar Enter
						else if(popupShell.isVisible() && table.getSelectionIndex() == -1){
							
							if(txtCliente.getText().equals("")){
								MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								dialog.setText("Aviso");
								dialog.setMessage("Debe ingresar o seleccionar un cliente.");
								dialog.open();
								
								txtCliente.setFocus();
							}
							else {
								popupShell.setVisible(false);
								
								mesFacturacion.setFocus();
//								mesFacturacion.select(0);
							}
						}
						//no hay tabla
						else {
							if(txtCliente.getText().equals("")) {
								MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								dialog.setText("Aviso");
								dialog.setMessage("Debe ingresar un cliente.");
								dialog.open();
								
								txtCliente.setFocus();
							}
							else {
								System.out.println("si hay rut en input");
								mesFacturacion.setFocus();
//								mesFacturacion.select(0);
							}
						}
						break;
					case SWT.KEYPAD_CR:
						//si hay seleccion de tabla al presionar Enter
						if (popupShell.isVisible() && table.getSelectionIndex() != -1) {
							txtCliente.setText(Utils.getRutOnly(table.getSelection()[0].getText()));
							
							System.out.println("si hay rut en input");
							mesFacturacion.setFocus();
//							mesFacturacion.select(0);
							
							popupShell.setVisible(false);
						}
						//no hay seleccion de tabla al presionar Enter
						else if(popupShell.isVisible() && table.getSelectionIndex() == -1){
							
							if(txtCliente.getText().equals("")){
								MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								dialog.setText("Aviso");
								dialog.setMessage("Debe ingresar o seleccionar un cliente.");
								dialog.open();
								
								txtCliente.setFocus();
							}
							else {
								popupShell.setVisible(false);
								
								mesFacturacion.setFocus();
//								mesFacturacion.select(0);
							}
						}
						//no hay tabla
						else {
							if(txtCliente.getText().equals("")) {
								MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								dialog.setText("Aviso");
								dialog.setMessage("Debe ingresar un cliente.");
								dialog.open();
								
								txtCliente.setFocus();
							}
							else {
								System.out.println("si hay rut en input");
								mesFacturacion.setFocus();
//								mesFacturacion.select(0);
							}
						}
						break;
					case SWT.ESC:
						popupShell.setVisible(false);
						break;
				}
			}
		});

		txtCliente.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				String string = txtCliente.getText();
				if (string.length() == 0) {
					popupShell.setVisible(false);
				} 
				else if(string.length() > 0) {
					Utils.buildFoundTable(table, txtCliente.getText());
					
					Rectangle textBounds = display.map(shell, null, txtCliente.getBounds());
					popupShell.setBounds(textBounds.x, textBounds.y + 80, textBounds.width + 100, 280);
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
				txtCliente.setText(Utils.getRutOnly(table.getSelection()[0].getText()));
				txtCliente.forceFocus();
				txtCliente.setSelection(0, txtCliente.getCharCount());
				
				popupShell.setVisible(false);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		mesFacturacion.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					if(mesFacturacion.getSelectionIndex() > -1) {
						btnSavePrint.setFocus();
						btnSavePrint.setSelection(true);
					}
					else
						System.out.println("no hay mes seleccionado");
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
		
		shell.pack();
		
		return shell;
	}
	
}
