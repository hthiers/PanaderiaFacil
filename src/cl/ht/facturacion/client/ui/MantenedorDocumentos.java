package cl.ht.facturacion.client.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import cl.ht.facturacion.client.util.Utils;
import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.client.vo.VOFactura;
import cl.ht.facturacion.client.vo.VOGuia;
import cl.ht.facturacion.client.vo.VOItemProducto;
import cl.ht.facturacion.client.vo.VOItemProductoX;
import cl.ht.facturacion.dao.impl.DAOClientesImpl;
import cl.ht.facturacion.dao.impl.DAOFacturasImpl;
import cl.ht.facturacion.dao.impl.DAOGuiasImpl;

public class MantenedorDocumentos {

	ArrayList<VOGuia> listaGuias;
	ArrayList<VOFactura> listaFacturas;
	
	int tipo = 1; //1 = guias, 2 = facturas
	
	Label lblBusqRut;
	Text txtBusqueda;
	Label lblBusqMes;
	Combo cboBusqueda;
	Button btnBusqueda;
	Button btnBusqOpcion1;
	Button btnBusqOpcion2;
	
	VOCliente clienteBusqueda;
	Calendar fechaDesdeBusqueda;
	Calendar fechaHastaBusqueda;
	
	Table tableSel;
	
	public Shell createShell(final Display display) {
		
	    // create a virtual table to display data
	    final Shell shell = new Shell(display);
	    shell.setText("Adminsitración de Guías y Facturas");
	    
	    final Shell popupShell = new Shell(display, SWT.ON_TOP);
		popupShell.setLayout(new FillLayout());
	    
	    GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 5;
		gridLayout.marginHeight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginRight = 10;
		
		shell.setLayout(gridLayout);
		
		/*
		 * Buscador
		 */
		Composite compoBusqueda = new Composite(shell, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 7;
		compoBusqueda.setLayout(gridLayout);
		
		GridData gridData = new GridData();
		gridData.horizontalSpan = 7;
		compoBusqueda.setLayoutData(gridData);
		
		lblBusqRut = new Label(compoBusqueda, SWT.NONE);
		lblBusqRut.setText("RUT Cliente");
		lblBusqRut.setLayoutData(new GridData(GridData.END, GridData.CENTER, true, false));
		
		txtBusqueda = new Text(compoBusqueda, SWT.BORDER);
		gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridData.widthHint = 180;
		txtBusqueda.setLayoutData(gridData);
		txtBusqueda.setFocus();
		
		//Lista clientes para seleccionar
		tableSel = new Table(popupShell, SWT.SINGLE);
		
		lblBusqMes = new Label(compoBusqueda, SWT.NONE);
		lblBusqMes.setText("Mes");
		lblBusqMes.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		
		cboBusqueda = new Combo(compoBusqueda, SWT.DROP_DOWN | SWT.READ_ONLY);
		String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
	    cboBusqueda.setItems(meses);
	    cboBusqueda.select(0);
	    cboBusqueda.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

	    btnBusqueda = new Button(compoBusqueda, SWT.PUSH);
	    btnBusqueda.setText("Buscar");
	    btnBusqueda.setImage(new Image(display, "media/Search-icon.png"));
	    btnBusqueda.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		
	    btnBusqOpcion1 = new Button(compoBusqueda, SWT.RADIO);
	    btnBusqOpcion1.setText("Guias");
	    btnBusqOpcion1.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
	    btnBusqOpcion1.setSelection(true);
	    
	    btnBusqOpcion2 = new Button(compoBusqueda, SWT.RADIO);
	    btnBusqOpcion2.setText("Facturas");
	    btnBusqOpcion2.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
	    
		/*
		 * Menu de tabla
		 */
		final Menu menu = new Menu(shell, SWT.POP_UP);
		
		/*
		 * Tabla
		 */
		final Table table = new Table(shell, SWT.NONE);
		table.setSize(500, 400);
		table.setHeaderVisible(true);
	    table.setLinesVisible(true);
	    table.setMenu(menu);
	    
	    GridData data = new GridData(GridData.FILL_BOTH);
	    data.widthHint = 650;
	    data.heightHint = 500;
		data.horizontalSpan = 4;
		
		table.setLayoutData(data);
	    
	    /*
	     * Estructura tabla inicial
	     */
		for(int x=1; x<=8; x++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
		    column.setText("                         ");
		}

	    /*
	     * DAO para busquedas
	     */
	    final DAOGuiasImpl daoGuias = new DAOGuiasImpl();
	    final DAOFacturasImpl daoFacturas = new DAOFacturasImpl();
	    final DAOClientesImpl daoClientes = new DAOClientesImpl();
	    
	    /*
	     * Pack tabla
	     */
	    final TableColumn[] columns = table.getColumns();
	    for (int i=0; i<columns.length; i++) columns [i].pack();
	    
	    /*
	     * Espacio muerto (bajo menu opciones)
	     */
	    Label lblGhost = new Label(shell, SWT.NONE);
	    lblGhost.setLayoutData(new GridData());
	    
	    /*
	     * Botones y Listeners
	     */   
	    btnBusqueda.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				
				clienteBusqueda = daoClientes.getClienteByRut(txtBusqueda.getText());
			    fechaDesdeBusqueda = Utils.getDateByMonth(cboBusqueda.getText());
				fechaHastaBusqueda = Utils.getNextDateByMonth(cboBusqueda.getText());
				
				if(btnBusqOpcion2.getSelection())
					tipo = 2;
				else
					tipo = 1;
				
				System.out.println("guia?: "+btnBusqOpcion1.getSelection());

				table.removeAll();
				table.clearAll();
				TableColumn[] columnas = table.getColumns();
				
				if(tipo == 1) {
					columnas[0].setText("Numero");
				    columnas[1].setText("Fecha");
				    columnas[2].setText("Nombres Cliente");
				    columnas[3].setText("Apellidos Cliente");
				    columnas[4].setText("Kg");
				    columnas[5].setText("Total");
				    columnas[6].setText("");
				    columnas[7].setText("");
					
				    if(txtBusqueda.getText().equals("") || txtBusqueda.getText().isEmpty()) {
				    	listaGuias = daoGuias.getAllGuiasByDate(fechaDesdeBusqueda, fechaHastaBusqueda, true);
				    	
				    	Iterator<VOGuia> itGuias = listaGuias.iterator();
				    	System.out.println("entering");
				    	
					    while(itGuias.hasNext()){
					    	VOGuia guia = itGuias.next();
					    	VOCliente cliente = daoClientes.getClienteByID(guia.getIdcliente());
					    	TableItem item = new TableItem (table, SWT.NONE);
					    	int mesFixed = guia.getFecha().get(Calendar.MONTH) + 1;
					    	System.out.println("mes normal: "+guia.getFecha().get(Calendar.MONTH));
					    	System.out.println("mes fixed: "+mesFixed);
					    	String fecha = guia.getFecha().get(Calendar.DATE)+"/"+mesFixed+"/"+guia.getFecha().get(Calendar.YEAR);
					    	int kg = daoGuias.getPesoVentaByGuia(guia);
							item.setText(new String[] {Integer.toString(guia.getNumero()), fecha, cliente.getNombres(), cliente.getApellidos(),""+kg, ""+Utils.doubleToInt(guia.getTotal())});
							
							//Check guia nula para destacar
							if(guia.isNula()) {
								item.setBackground(new Color(shell.getDisplay(), new RGB(255,255,153)));
					    		item.setForeground(new Color(shell.getDisplay(), new RGB(255,0,0)));
							}
								
					    }
				    }
				    else {
				    	if(clienteBusqueda != null) {
				    		listaGuias = daoGuias.getAllGuiasByClienteDate(clienteBusqueda, fechaDesdeBusqueda, fechaHastaBusqueda, true);
				    		
				    		Iterator<VOGuia> itGuias = listaGuias.iterator();
						    
						    while(itGuias.hasNext()){
						    	VOGuia guia = itGuias.next();
						    	VOCliente cliente = daoClientes.getClienteByID(guia.getIdcliente());
						    	TableItem item = new TableItem (table, SWT.NONE);
						    	int mesFixed = guia.getFecha().get(Calendar.MONTH) + 1;
						    	String fecha = guia.getFecha().get(Calendar.DATE)+"/"+mesFixed+"/"+guia.getFecha().get(Calendar.YEAR);
						    	int kg = daoGuias.getPesoVentaByGuia(guia);
								item.setText(new String[] {Integer.toString(guia.getNumero()), fecha, cliente.getNombres(), cliente.getApellidos(),""+kg, ""+Utils.doubleToInt(guia.getTotal())});
								
								//Check guia nula para destacar
								if(guia.isNula()) {
									item.setBackground(new Color(shell.getDisplay(), new RGB(255,255,153)));
						    		item.setForeground(new Color(shell.getDisplay(), new RGB(255,0,0)));
								}
						    }
				    	}
				    	else {
				    		MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
							dialog.setText("Error en búsqueda");
							dialog.setMessage("No se ha encontrado cliente con este RUT");
							dialog.open();
				    	}
				    }
				   
				}
				//Documento tipo factura
				else {
					columnas[0].setText("Numero");
				    columnas[1].setText("Fecha");
				    columnas[2].setText("Nombres Cliente");
				    columnas[3].setText("Apellidos Cliente");
				    columnas[4].setText("Kg");
				    columnas[5].setText("Neto");
				    columnas[6].setText("IVA");
				    columnas[7].setText("Total");
				    
				    if(txtBusqueda.getText().equals("") || txtBusqueda.getText().isEmpty())
				    	listaFacturas = daoFacturas.getAllFacturasByDate(fechaDesdeBusqueda, fechaHastaBusqueda);
				    else
				    	listaFacturas = daoFacturas.getAllFacturasByClienteDate(clienteBusqueda, fechaDesdeBusqueda, fechaHastaBusqueda);
				    
				    Iterator<VOFactura> itFacs = listaFacturas.iterator();
				    
				    while(itFacs.hasNext()){
				    	VOFactura factura = itFacs.next();
				    	VOCliente cliente = daoClientes.getClienteByID(factura.getIdCliente());
				    	ArrayList<VOGuia> guias = daoGuias.getAllGuiasByFactura(factura.getId());
				    	ArrayList<VOItemProductoX> items = new ArrayList<VOItemProductoX>();
				    	int kg = 0;
				    	
				    	for(int x=0; x<guias.size(); x++) {
				    		 items = daoGuias.getAllItemProductoByGuia(guias.get(x));
				    		 for(int i=0; i<items.size(); i++)
				    			 kg += items.get(i).getCantidad();
				    	}
				    	
				    	TableItem item = new TableItem (table, SWT.NONE);
				    	int mesFixed = factura.getFecha().get(Calendar.MONTH) + 1;
				    	String fecha = factura.getFecha().get(Calendar.DATE)+"/"+mesFixed+"/"+factura.getFecha().get(Calendar.YEAR);
				    	item.setText(new String[] {Integer.toString(factura.getNumero()), fecha, cliente.getNombres(), cliente.getApellidos(),""+kg,""+Utils.doubleToInt(factura.getValorNeto()), ""+Utils.doubleToInt(factura.getValorIva()), ""+Utils.doubleToInt(factura.getValorTotal())});
				    }
				}
					
			}
		});
	    
	    Button btnEditar = new Button(shell, SWT.PUSH);
	    btnEditar.setText("Editar");
	    btnEditar.setImage(new Image(display, "media/Edit-icon.png"));
	    btnEditar.setLayoutData(new GridData());
//	    btnEditar.addListener(SWT.Selection, new Listener() {
//			public void handleEvent(Event event) {
//				Shell shellFrmCliente = new FormCliente().createShell(display, table.getItem(table.getSelectionIndex()).getText());
//				shellFrmCliente.pack();
//				shellFrmCliente.open();
//			}
//		});
	    
	    Button btnAnular = new Button(shell, SWT.PUSH);
	    btnAnular.setText("Anular/Activar");
	    btnAnular.setToolTipText("Anular o activar un documento según su actual estado.");
	    btnAnular.setImage(new Image(display, "media/Remove-icon.png"));
	    btnAnular.setLayoutData(new GridData());
	    btnAnular.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				//Check tipo documento
				if(tipo == 1) {
					//Check selección
					if(!(table.getSelectionIndex() == -1)) {
						VOGuia guia = daoGuias.getGuiaByNumero(Integer.parseInt(table.getItem(table.getSelectionIndex()).getText()),true);
						
						//Check si ya esta anulada
						if(!guia.isNula()) {
						
							//Check guia facturada
							if(daoGuias.checkGuiaFacturada(guia.getNumero())) {
								MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								dialog.setText("Imposible continuar");
								dialog.setMessage("Esta guia ya ha sido facturada. Anule primero su factura.");
								dialog.open();
							}
							else {
								MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
								dialog.setText("Confirmar Orden");
								dialog.setMessage("¿Esta seguro que desea anular la guía #"+guia.getNumero()+"?");
								
								if(dialog.open() == SWT.YES) {
									daoGuias.anularGuia(guia);
								
									//Check guia ahora nula
									boolean nulabilidad = daoGuias.getGuiaByNumero(guia.getNumero(),true).isNula();
									if(nulabilidad) {
										dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
										dialog.setText("Anulación de Guía");
										dialog.setMessage("La guía ha sido anulada.");
										dialog.open();
									}
									else {
										dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
										dialog.setText("Error del sistema");
										dialog.setMessage("Ha ocurrido un error grave y la guía no ha podido ser anulada.");
										dialog.open();
									}
								}
							}

						}
						//Desanular guia
						else {
							MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
							dialog.setText("Confirmar Orden");
							dialog.setMessage("¿Esta seguro que desea volver activar la guía #"+guia.getNumero()+"?");
							
							if(dialog.open() == SWT.YES) {
								daoGuias.desanularGuia(guia);
							
								//Check guia ahora activa
								boolean nulabilidad = daoGuias.getGuiaByNumero(guia.getNumero(),true).isNula();
								if(!nulabilidad) {
									dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
									dialog.setText("Resultado");
									dialog.setMessage("La guía ha sido activada otra vez.");
									dialog.open();
								}
								else {
									dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
									dialog.setText("Error del sistema");
									dialog.setMessage("Ha ocurrido un error grave y la guía no ha podido ser anulada.");
									dialog.open();
								}
							}
						}

					}
					else {
						MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
						dialog.setText("Acción incorrecta");
						dialog.setMessage("Debe primero seleccionar un documento");
						dialog.open();
					}
				}
				else if(tipo == 2) {
					MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					dialog.setText("Información");
					dialog.setMessage("Esta funcionalidad aún no esta disponible");
					dialog.open();
				}
				else {
					MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
					dialog.setText("Error del sistema");
					dialog.setMessage("Ha ocurrido un error grave!");
					dialog.open();
				}
			}
		});

		Button btnExportar = new Button(shell, SWT.PUSH);
		btnExportar.setText("Exportar");
		btnExportar.setImage(new Image(display, "media/Copy-v2-icon.png"));
		btnExportar.setLayoutData(new GridData());
		btnExportar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				System.out.println("Exportar a excel");
			}
		});

		Button btnImprimir = new Button(shell, SWT.PUSH);
		btnImprimir.setText("Imprimir");
		btnImprimir.setImage(new Image(display, "media/Print-icon.png"));
		btnImprimir.setLayoutData(new GridData());
		btnImprimir.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if(tipo == 1) {
					if(!(table.getSelectionIndex() == -1)) {
						VOGuia guia = daoGuias.getGuiaByNumero(Integer.parseInt(table.getItem(table.getSelectionIndex()).getText()),true);
						VOCliente cliente = daoClientes.getClienteByID(guia.getIdcliente());
						ArrayList<VOItemProductoX> listaItemsProd = daoGuias.getAllItemProductoByGuia(guia);
						String fecha = guia.getFecha().get(Calendar.DATE)+"/"+guia.getFecha().get(Calendar.MONTH)+"/"+guia.getFecha().get(Calendar.YEAR);
						
						MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
						dialog.setText("Imprimir guia de despacho");
						dialog.setMessage("¿Esta seguro de imprimir la guia nº "+guia.getNumero()+"?");
						
						if(dialog.open() == SWT.YES)
							Utils.imprimirGuia(shell, guia, cliente, listaItemsProd, fecha);
					}
					else {
						MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
						dialog.setText("Acción incorrecta");
						dialog.setMessage("Debe primero seleccionar un documento");
						dialog.open();
					}
						
				}
				else {
					if(!(table.getSelectionIndex() == -1)) {
						VOFactura factura = daoFacturas.getFacturaByNumero(Integer.parseInt(table.getItem(table.getSelectionIndex()).getText()));
						VOCliente cliente = daoClientes.getClienteByID(factura.getIdCliente());
						String fecha = factura.getFecha().get(Calendar.DATE)+"/"+factura.getFecha().get(Calendar.MONTH)+"/"+factura.getFecha().get(Calendar.YEAR);
						
						MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
						dialog.setText("Imprimir factura");
						dialog.setMessage("¿Esta seguro de imprimir la factura nº "+factura.getNumero()+"?");
						
						if(dialog.open() == SWT.YES)
							Utils.imprimirFactura(shell, factura, cliente, fecha);
					}
					else {
						MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
						dialog.setText("Acción incorrecta");
						dialog.setMessage("Debe primero seleccionar un documento");
						dialog.open();
					}
				}
			}
		});

		menu.addListener(SWT.Show, new Listener() {
			
		      public void handleEvent(Event event) {
		    	  
		        MenuItem[] menuItems = menu.getItems();
		        
		        for (int i = 0; i < menuItems.length; i++) {
		          menuItems[i].dispose();
		        }
		        
		        MenuItem menuEditar = new MenuItem(menu, SWT.PUSH);
		        menuEditar.setText("Editar");
//		        menuEditar.addListener(SWT.Selection, new Listener() {
//		            public void handleEvent(Event e) {
//		            	Shell shellFrmDocs = new FormCliente().createShell(display, table.getItem(table.getSelectionIndex()).getText());
//		            	shellFrmDocs.pack();
//		            	shellFrmDocs.open();
//		            }
//		        });
		        
		        MenuItem menuImprimir = new MenuItem(menu, SWT.PUSH);
		        menuImprimir.setText("Imprimir");
		        menuImprimir.addListener(SWT.Selection, new Listener() {
		            public void handleEvent(Event e) {
		            	if(tipo == 1) {
		            		if(!(table.getSelectionIndex() == -1)) {
								VOGuia guia = daoGuias.getGuiaByNumero(Integer.parseInt(table.getItem(table.getSelectionIndex()).getText()),true);
								VOCliente cliente = daoClientes.getClienteByID(guia.getIdcliente());
								ArrayList<VOItemProductoX> listaItemsProd = daoGuias.getAllItemProductoByGuia(guia);
								String fecha = guia.getFecha().get(Calendar.DATE)+"/"+guia.getFecha().get(Calendar.MONTH)+"/"+guia.getFecha().get(Calendar.YEAR);
								
								if(cliente != null) {
									MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
									dialog.setText("Imprimir guia de despacho");
									dialog.setMessage("¿Esta seguro de imprimir la guia nº "+guia.getNumero()+"?");
									
									if(dialog.open() == SWT.YES)
										Utils.imprimirGuia(shell, guia, cliente, listaItemsProd, fecha);
								}
								else
									System.out.println("No se ha encontrado cliente");
		            		}
		            		else {
		            			MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
								dialog.setText("Acción incorrecta");
								dialog.setMessage("Debe primero seleccionar un documento");
								dialog.open();
		            		}
						}
						else {
							if(!(table.getSelectionIndex() == -1)) {
								VOFactura factura = daoFacturas.getFacturaByNumero(Integer.parseInt(table.getItem(table.getSelectionIndex()).getText()));
								VOCliente cliente = daoClientes.getClienteByID(factura.getIdCliente());
								String fecha = factura.getFecha().get(Calendar.DATE)+"/"+factura.getFecha().get(Calendar.MONTH)+"/"+factura.getFecha().get(Calendar.YEAR);
								
								if(cliente != null) {
									MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
									dialog.setText("Imprimir factura");
									dialog.setMessage("¿Esta seguro de imprimir la factura nº "+factura.getNumero()+"?");
									
									if(dialog.open() == SWT.YES)
										Utils.imprimirFactura(shell, factura, cliente, fecha);
								}
								else
									System.out.println("No se ha encontrado cliente");
							}
							else {
								MessageBox dialog = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
								dialog.setText("Acción incorrecta");
								dialog.setMessage("Debe primero seleccionar un documento");
								dialog.open();
							}
						}
		            }
		        });
		        
		      }
		      
		});
		
		txtBusqueda.addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				switch (event.keyCode) {
					case SWT.ARROW_DOWN:
						int index = (tableSel.getSelectionIndex() + 1) % tableSel.getItemCount();
						tableSel.setSelection(index);
						event.doit = false;
						break;
					case SWT.ARROW_UP:
						index = tableSel.getSelectionIndex() - 1;
						if (index < 0) index = tableSel.getItemCount() - 1;
						tableSel.setSelection(index);
						event.doit = false;
						break;
					case SWT.CR:
						if (popupShell.isVisible() && tableSel.getSelectionIndex() != -1) {
							txtBusqueda.setText(Utils.getRutOnly(tableSel.getSelection()[0].getText()));
							cboBusqueda.setFocus();
							
							popupShell.setVisible(false);
						}
						break;
					case SWT.KEYPAD_CR:
						if (popupShell.isVisible() && tableSel.getSelectionIndex() != -1) {
							txtBusqueda.setText(Utils.getRutOnly(tableSel.getSelection()[0].getText()));
							cboBusqueda.setFocus();
							
							popupShell.setVisible(false);
						}
						break;
					case SWT.ESC:
						popupShell.setVisible(false);
						break;
				}
			}
		});
		
		txtBusqueda.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				String string = txtBusqueda.getText();
				if (string.length() == 0) {
					popupShell.setVisible(false);
				} 
				else if(string.length() > 0) {
					Utils.buildFoundTable(tableSel, txtBusqueda.getText());
					
					Rectangle textBounds = display.map(shell, null, txtBusqueda.getBounds());
					popupShell.setBounds(textBounds.x, textBounds.y + 40, textBounds.width + 60, 280);
					popupShell.setVisible(true);
				}
			}
		});
		
		tableSel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.println("@down!");
				txtBusqueda.setText(Utils.getRutOnly(tableSel.getSelection()[0].getText()));
				txtBusqueda.forceFocus();
				txtBusqueda.setSelection(0, txtBusqueda.getCharCount());
				
				popupShell.setVisible(false);
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
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
						if (control == null || (control != txtBusqueda && control != tableSel)) {
							popupShell.setVisible(false);
						}
					}
				});
			}
		};
		
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
		
		tableSel.addListener(SWT.FocusOut, focusOutListener);
		
		txtBusqueda.addListener(SWT.FocusOut, focusOutListener);
		
		return shell;
	}
	
}
