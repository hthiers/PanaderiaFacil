package cl.ht.facturacion.client.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.client.vo.VOProducto;
import cl.ht.facturacion.dao.impl.DAOProductosImpl;

public class FormProducto {
	
	Text codigo;
	Text nombre;
	Text descripcion;
	Text precio;
	
	Button estadoAct;
	Button estadoDes;
	Button enter;
	
	MessageBox dialog;
	
	VOProducto producto = null;
	VOCliente cliente = null;
	
	/*
	 * accion = 1 (default): nuevo producto
	 * accion = 2: actualizar producto
	 */
	int accion = 1;
	
	/*
	 * Estructura Producto
	 */
	public Shell createShell(final Display display, String codigoProducto) {
		
		if(!codigoProducto.equals(""))
			accion = 2;
		
		final Shell shell = new Shell(display);
		
		if(accion == 1)
			shell.setText("Nuevo Producto");
		else
			shell.setText("Actualizar Producto");
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gridLayout.marginHeight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginRight = 10;
		shell.setLayout(gridLayout);
		
		/*
		 * Datos de producto
		 */
		GridData gridPersonales = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridPersonales.horizontalSpan= 4;
		GridLayout gridLayoutPers = new GridLayout(4, false);
		gridLayoutPers.marginBottom = 15;
		gridLayoutPers.marginTop = 5;
		
		Group groupPersonales = new Group(shell, SWT.SHADOW_IN);
		groupPersonales.setText("Datos personales");
		groupPersonales.setLayoutData(gridPersonales);
		groupPersonales.setLayout(gridLayoutPers);
		
		GridData gridText = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridText.widthHint = 200;
		
		new Label(groupPersonales, SWT.NONE).setText("Código:");
		codigo = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		codigo.setLayoutData(gridText);
		codigo.setTextLimit(10);
		codigo.setFocus();

		new Label(groupPersonales, SWT.NONE).setText("Nombre:");
		nombre = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		nombre.setLayoutData(gridText);
		
		new Label(groupPersonales, SWT.NONE).setText("Descripción:");
		descripcion = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		descripcion.setLayoutData(gridText);
		
		new Label(groupPersonales, SWT.NONE).setText("Precio:");
		precio = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		precio.setLayoutData(gridText);
		
		new Label(shell, SWT.NONE).setText("Estado:");
		estadoAct = new Button(shell, SWT.RADIO);
		estadoAct.setText("Activado");
		estadoAct.setEnabled(false);
		
		estadoDes = new Button(shell, SWT.RADIO);
		estadoDes.setText("Desactivado");
		estadoDes.setEnabled(false);
		
		/*
		 * Autollenado en caso de ser Edición
		 */
		if(codigoProducto != null) {
			if(!codigoProducto.isEmpty() || !codigoProducto.equals("")) {
				
				accion = 2;
				
				//debug
				System.out.println("llego el codigo: "+codigoProducto);
				
				producto = new DAOProductosImpl().getProductoByCodigo(codigoProducto);
				
				if(producto != null) {
					codigo.setText(producto.getCodigo());
					nombre.setText(producto.getNombre());
					descripcion.setText(producto.getDescripcion());
					precio.setText(""+producto.getPrecio());
					
//					if(cliente.getEstado() == 1) {
//						estadoAct.setSelection(true);
//						estadoDes.setSelection(false);
//					}
//					else {
//						estadoAct.setSelection(false);
//						estadoDes.setSelection(true);
//					}
				}
				else {
					int style = SWT.ICON_ERROR | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Error");
					dialog.setMessage("No se ha encontrado ningún producto con este Código.");
					dialog.open();
					
					shell.close();
				}
			}
			else {
				System.out.println("llego vacio");
				estadoAct.setSelection(true);
			}
		}
		
		
		/*
		 * Botones
		 */
		enter = new Button(shell, SWT.PUSH);
		enter.setAlignment(SWT.CENTER);
		enter.setImage(new Image(display, "media/Save-icon.png"));
		
		if(accion == 1)
			enter.setText("Guardar");
		else
			enter.setText("Actualizar");
		
		enter.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, false, false));
		
		final DAOProductosImpl daoProductos = new DAOProductosImpl();
		
		
		/*
		 * Listeners
		 */
		enter.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println("\nCodigo: " + codigo.getText());
				System.out.println("Nombre: " + nombre.getText());
				System.out.println("Descripcion: " + descripcion.getText());
				System.out.println("Precio: " + precio.getText());
				
				if(codigo.getText().equals("") || nombre.getText().equals("") || descripcion.getText().equals("")){
					int style = SWT.ICON_WARNING | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Faltan Datos");
					dialog.setMessage("Los campos Codigo, Nombre y Descripcion son obligatorios");
					dialog.open();
					
					codigo.setFocus();
					codigo.setSelection(0);
				}
				else {
					if(accion == 1) {
					
						VOProducto productoCheck = daoProductos.getProductoByCodigo(codigo.getText());
						
						//Verificar si ya existe producto con ese codigo
						if(productoCheck == null) {
							
							System.out.println("@codigo a ingresar: "+codigo.getText());
							producto = new VOProducto();
							producto.setCodigo(codigo.getText());
							producto.setNombre(nombre.getText());
							producto.setDescripcion(descripcion.getText());
							producto.setPrecio(Integer.parseInt(precio.getText()));
							
							daoProductos.newProducto(producto);
							
							int style = SWT.ICON_INFORMATION | SWT.OK;
							dialog = new MessageBox(shell, style);
							dialog.setText("Resultado");
							dialog.setMessage("Nuevo producto ingresado.");
							dialog.open();
							
							shell.close();
						}
						else {
							System.out.println("producto ya existente id: "+productoCheck.getId());
							
							int style = SWT.ICON_ERROR | SWT.OK;
							dialog = new MessageBox(shell, style);
							dialog.setText("Error");
							dialog.setMessage("Ya existe un producto con ese Código. Intente nuevamente.");
							dialog.open();
							
							codigo.setText("");
							codigo.setFocus();
							nombre.setText("");
							descripcion.setText("");
							precio.setText("");
						}	
					}
					else if (accion == 2) {
						
						System.out.println("@producto id a actualizar: "+ producto.getId());
						
						//producto = daoProductos.getProductoByCodigo(codigo.getText());
						producto.setCodigo(codigo.getText());
						producto.setNombre(nombre.getText());
						producto.setDescripcion(descripcion.getText());
						producto.setPrecio(Integer.parseInt(precio.getText()));
						
						daoProductos.updateProducto(producto);
						
						int style = SWT.ICON_INFORMATION | SWT.OK;
						dialog = new MessageBox(shell, style);
						dialog.setText("Resultado");
						dialog.setMessage("Producto actualizado");
						dialog.open();
						
						shell.close();
					}
					else {
						int style = SWT.ICON_ERROR | SWT.OK;
						dialog = new MessageBox(shell, style);
						dialog.setText("Error");
						dialog.setMessage("Ha ocurrido una falla grave");
						dialog.open();
						
						shell.close();
					}
					
				}//end if campos.equals("")
				
			}//end save button action
		});
		
		codigo.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					nombre.setFocus();
					nombre.setSelection(0, nombre.getCharCount());
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		codigo.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("out of codigo");
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		nombre.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					descripcion.setFocus();
					descripcion.setSelection(0, descripcion.getCharCount());
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
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
				System.out.println("in nombre");

				if(codigo.getText().equals("") || codigo.getCharCount() < 1) {
					codigo.setText("");
					codigo.setFocus();
					codigo.setSelection(0);
					
					int style = SWT.ICON_ERROR | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Error");
					dialog.setMessage("Complete el código.");
					dialog.open();
				}
			}
		});
		
		descripcion.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					precio.setFocus();
					precio.setSelection(0, precio.getCharCount());
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		descripcion.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(nombre.getText().equals("")) {
					nombre.setFocus();
					nombre.setSelection(0);
					
					int style = SWT.ICON_ERROR | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Error");
					dialog.setMessage("Es necesario incluir el nombre del producto");
					dialog.open();
				}
			}
		});
		
		precio.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					enter.setFocus();
					enter.setSelection(true);
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
		          shell.close();
		          event.detail = SWT.TRAVERSE_NONE;
		          event.doit = false;
		        }
		      }
		});
		
		shell.pack();
		
		return shell;
	}
}