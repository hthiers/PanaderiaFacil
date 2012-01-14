package cl.ht.facturacion.client.ui;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.dao.impl.DAOClientesImpl;

public class MantenedorClientes {

	ArrayList<VOCliente> listaClientes;
	
	public Shell createShell(final Display display) {
		
		final DAOClientesImpl daoClientes = new DAOClientesImpl();
		listaClientes = daoClientes.getAllClientes(false);
		
	    // create a virtual table to display data
	    final Shell shell = new Shell(display);
	    shell.setText("Adminsitración de Clientes");
	    
	    GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gridLayout.marginHeight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginRight = 10;
		
		shell.setLayout(gridLayout);
		shell.setImage(new Image(display, "media/bread-icon-3232.png"));
		
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
		 * Columnas
		 */
	    final TableColumn column1 = new TableColumn(table, SWT.NONE);
	    column1.setText("RUT");
	    
	    final TableColumn column2 = new TableColumn(table, SWT.NONE);
	    column2.setText("Nombres");
	    
	    final TableColumn column3 = new TableColumn(table, SWT.NONE);
	    column3.setText("Apellidos");
	    
	    final TableColumn column4 = new TableColumn(table, SWT.NONE);
	    column4.setText("Ciudad");
	    
	    final TableColumn column5 = new TableColumn(table, SWT.NONE);
	    column5.setText("Comuna");
	    
	    final TableColumn column6 = new TableColumn(table, SWT.NONE);
	    column6.setText("Dirección");
	    
	    final TableColumn column7 = new TableColumn(table, SWT.NONE);
	    column7.setText("Giro");
	    
	    final TableColumn column8 = new TableColumn(table, SWT.NONE);
	    column8.setText("Estado");
	    
	    /*
	     * Llenado de tabla
	     */
	    Iterator<VOCliente> itcli = listaClientes.iterator();
	    
	    while(itcli.hasNext()){
	    	VOCliente cliente = itcli.next();
	    	TableItem item = new TableItem (table, SWT.NONE);
	    	if(cliente.getEstado() == 2) {
	    		item.setBackground(new Color(shell.getDisplay(), new RGB(255,255,153)));
	    		item.setForeground(new Color(shell.getDisplay(), new RGB(255,0,0)));
	    	}
			item.setText(new String[] {cliente.getRut(), cliente.getNombres(), cliente.getApellidos(), cliente.getCiudad(), cliente.getComuna(), cliente.getDireccion(), cliente.getGiro(), Integer.toString(cliente.getEstado())});
	    }
	    
	    menu.addListener(SWT.Show, new Listener() {
			
		      public void handleEvent(Event event) {
		    	  
		        MenuItem[] menuItems = menu.getItems();
		        
		        for (int i = 0; i < menuItems.length; i++) {
		          menuItems[i].dispose();
		        }
		        
		        MenuItem menuEditar = new MenuItem(menu, SWT.PUSH);
		        menuEditar.setText("Editar");
		        menuEditar.addListener(SWT.Selection, new Listener() {
		            public void handleEvent(Event e) {
		            	Shell shellFrmCliente = new FormCliente().createShell(display, table.getItem(table.getSelectionIndex()).getText());
						shellFrmCliente.pack();
						shellFrmCliente.open();
		            }
		        });
		        
		        MenuItem menuCopiar = new MenuItem(menu, SWT.PUSH);
		        menuCopiar.setText("Copiar RUT");
		        menuCopiar.addListener(SWT.Selection, new Listener() {
		            public void handleEvent(Event e) {
		              System.out.println("Copiar RUT cliente");
		            }
		        });
		        
		      }
		      
		});
	    
	    final TableColumn[] columns = table.getColumns();
	    for (int i=0; i<columns.length; i++) columns [i].pack ();
	    
	    /*
	     * Botones
	     */
	    Button btnAgregar = new Button(shell, SWT.PUSH);
	    btnAgregar.setText("Agregar");
	    btnAgregar.setImage(new Image(display, "media/Add-icon.png"));
	    btnAgregar.setLayoutData(new GridData());
	    btnAgregar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Shell shellFrmCliente = new FormCliente().createShell(display, "");
				shellFrmCliente.pack();
				shellFrmCliente.open();
			}
		});
	    
	    Button btnEditar = new Button(shell, SWT.PUSH);
	    btnEditar.setText("Editar");
	    btnEditar.setImage(new Image(display, "media/Edit-icon.png"));
	    btnEditar.setLayoutData(new GridData());
	    btnEditar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Shell shellFrmCliente = new FormCliente().createShell(display, table.getItem(table.getSelectionIndex()).getText());
				shellFrmCliente.pack();
				shellFrmCliente.open();
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
		
		Button btnRefrescar = new Button(shell, SWT.PUSH);
		btnRefrescar.setText("Refrescar");
		btnRefrescar.setImage(new Image(display, "media/Refresh-icon.png"));
		btnRefrescar.setLayoutData(new GridData());
		btnRefrescar.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				table.removeAll();
				table.clearAll();
				
				listaClientes = daoClientes.getAllClientes(false);
				Iterator<VOCliente> itcli = listaClientes.iterator();
				
				 while(itcli.hasNext()){
				    	VOCliente cliente = itcli.next();
				    	TableItem item = new TableItem(table, SWT.NONE);
				    	if(cliente.getEstado() == 2) {
				    		item.setBackground(new Color(shell.getDisplay(), new RGB(255,255,153)));
				    		item.setForeground(new Color(shell.getDisplay(), new RGB(255,0,0)));
				    	}
						item.setText(new String[] {cliente.getRut(), cliente.getNombres(), cliente.getApellidos(), cliente.getCiudad(), cliente.getComuna(), cliente.getDireccion(), cliente.getGiro(), Integer.toString(cliente.getEstado())});
				 }
				
			}
		});
		
	    // Add sort indicator and sort data when column selected
//	    Listener sortListener = new Listener() {
//	      public void handleEvent(Event e) {
//	        // determine new sort column and direction
//	        TableColumn sortColumn = table.getSortColumn();
//	        TableColumn currentColumn = (TableColumn) e.widget;
//	        int dir = table.getSortDirection();
//	        if (sortColumn == currentColumn) {
//	          dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
//	        } else {
//	          table.setSortColumn(currentColumn);
//	          dir = SWT.UP;
//	        }
//	        // sort the data based on column and direction
//	        final int index = currentColumn == column1 ? 0 : 1;
//	        final int direction = dir;
//	        Arrays.sort(data, new Comparator() {
//	          public int compare(Object arg0, Object arg1) {
//	            int[] a = (int[]) arg0;
//	            int[] b = (int[]) arg1;
//	            if (a[index] == b[index])
//	              return 0;
//	            if (direction == SWT.UP) {
//	              return a[index] < b[index] ? -1 : 1;
//	            }
//	            return a[index] < b[index] ? 1 : -1;
//	          }
//	        });
//	        // update data displayed in table
//	        table.setSortDirection(dir);
//	        table.clearAll();
//	      }
//	    };
	    
//	    column1.addListener(SWT.Selection, sortListener);
//	    column2.addListener(SWT.Selection, sortListener);
	    
	    table.setSortColumn(column1);
	    table.setSortDirection(SWT.UP);
		
	    shell.addListener(SWT.Traverse, new Listener() {
		      public void handleEvent(Event event) {
		        if(event.detail == SWT.TRAVERSE_ESCAPE) {
		          shell.close();
		          event.detail = SWT.TRAVERSE_NONE;
		          event.doit = false;
		        }
		      }
		});
	    
		return shell;
	}
	
}
