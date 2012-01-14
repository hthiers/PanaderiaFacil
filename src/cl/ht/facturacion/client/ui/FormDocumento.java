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
import cl.ht.facturacion.dao.impl.DAOClientesImpl;

public class FormDocumento {
	
	Text rut;
	Text nombre;
	Text apellido;
	Text ciudad;
	Text comuna;
	Text direccion;
	Text giro;
	
	Button estadoAct;
	Button estadoDes;
	Button enter;
	
	MessageBox dialog;
	
	VOCliente cliente = null;
	
	/*
	 * accion = 1: nuevo cliente
	 * accion = 2: actualizar cliente
	 */
	int accion = 1;
	
	/*
	 * Estructura Guia de Despacho
	 */
	public Shell createShell(final Display display, String rutCliente) {
		
		if(!rutCliente.equals(""))
			accion = 2;
		
		final Shell shell = new Shell(display);
		
		if(accion == 1)
			shell.setText("Nuevo Cliente");
		else
			shell.setText("Actualizar Cliente");
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		gridLayout.marginHeight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginRight = 10;
		shell.setLayout(gridLayout);
		
		/*
		 * Datos personales
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
		
		new Label(groupPersonales, SWT.NONE).setText("RUT:");
		rut = new Text(groupPersonales, SWT.SINGLE | SWT.BORDER);
		rut.setLayoutData(gridText);
		rut.setTextLimit(10);
		rut.setFocus();

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
		
		new Label(shell, SWT.NONE).setText("Estado:");
		estadoAct = new Button(shell, SWT.RADIO);
		estadoAct.setText("Activado");
		
		estadoDes = new Button(shell, SWT.RADIO);
		estadoDes.setText("Desactivado");
		
		if(rutCliente != null) {
			if(!rutCliente.isEmpty() || !rutCliente.equals("")) {
				
				accion = 2;
				
				System.out.println("llego el cliente: "+rutCliente);
				cliente = new DAOClientesImpl().getClienteByRut(rutCliente);
				
				if(cliente != null) {
					rut.setText(cliente.getRut());
					nombre.setText(cliente.getNombres());
					apellido.setText(cliente.getApellidos());
					ciudad.setText(cliente.getCiudad());
					comuna.setText(cliente.getComuna());
					direccion.setText(cliente.getDireccion());
					giro.setText(cliente.getGiro());
					
					if(cliente.getEstado() == 1) {
						estadoAct.setSelection(true);
						estadoDes.setSelection(false);
					}
					else {
						estadoAct.setSelection(false);
						estadoDes.setSelection(true);
					}
				}
				else {
					int style = SWT.ICON_ERROR | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Error");
					dialog.setMessage("No se ha encontrado ningún cliente con este RUT.");
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
		
		final DAOClientesImpl daoClientes = new DAOClientesImpl();
		
		/*
		 * Listeners
		 */
		enter.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println("\nRut: " + rut.getText());
				System.out.println("Nombres: " + nombre.getText());
				System.out.println("Apellidos: " + apellido.getText());
				System.out.println("Ciudad: " + ciudad.getText());
				System.out.println("Comuna: " + comuna.getText());
				System.out.println("Giro: " + giro.getText());
				
				if(rut.getText().equals("") || nombre.getText().equals("") || apellido.getText().equals("")){
					int style = SWT.ICON_WARNING | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Faltan Datos");
					dialog.setMessage("Los campos RUT, Nombres y Apellidos son obligatorios");
					dialog.open();
					
					rut.setFocus();
					rut.setSelection(0);
				}
				else {
					if(accion == 1) {
					
						VOCliente clienteCheck = daoClientes.getClienteByRut(rut.getText());
						
						if(clienteCheck == null) {
							daoClientes.newCliente(rut.getText(), nombre.getText(), apellido.getText(), ciudad.getText(), comuna.getText(), direccion.getText(), giro.getText(), estadoAct.getSelection());
							
							int style = SWT.ICON_INFORMATION | SWT.OK;
							dialog = new MessageBox(shell, style);
							dialog.setText("Resultado");
							dialog.setMessage("Nuevo cliente ingresado.");
							dialog.open();
							
							shell.close();
						}
						else {
							System.out.println("cliente ya existente id: "+clienteCheck.getId());
							
							int style = SWT.ICON_ERROR | SWT.OK;
							dialog = new MessageBox(shell, style);
							dialog.setText("Error");
							dialog.setMessage("Ya existe un cliente con ese RUT. Intente nuevamente.");
							dialog.open();
							
							rut.setText("");
							rut.setFocus();
							nombre.setText("");
							apellido.setText("");
							ciudad.setText("");
							comuna.setText("");
							direccion.setText("");
							giro.setText("");
						}	
					}
					else if (accion == 2) {
						daoClientes.updateCliente(Integer.parseInt(cliente.getId()), rut.getText(), nombre.getText(), apellido.getText(), ciudad.getText(), comuna.getText(), direccion.getText(), giro.getText(), estadoAct.getSelection());
						
						int style = SWT.ICON_INFORMATION | SWT.OK;
						dialog = new MessageBox(shell, style);
						dialog.setText("Resultado");
						dialog.setMessage("Cliente actualizado");
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
		
		rut.addKeyListener(new KeyListener() {
			
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
		
		rut.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("out of rut");
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
					apellido.setFocus();
					apellido.setSelection(0, apellido.getCharCount());
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

				if(rut.getText().equals("") || rut.getCharCount() < 9) {
					rut.setText("");
					rut.setFocus();
					rut.setSelection(0);
					
					int style = SWT.ICON_ERROR | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Error");
					dialog.setMessage("El RUT debe contener al menos 9 caracteres.");
					dialog.open();
				}
			}
		});
		
		apellido.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					ciudad.setFocus();
					ciudad.setSelection(0, ciudad.getCharCount());
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
				if(nombre.getText().equals("")) {
					nombre.setFocus();
					nombre.setSelection(0);
					
					int style = SWT.ICON_ERROR | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Error");
					dialog.setMessage("Es necesario incluir el nombre del cliente");
					dialog.open();
				}
			}
		});
		
		ciudad.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					comuna.setFocus();
					comuna.setSelection(0, comuna.getCharCount());
				}
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
				if(apellido.getText().equals("")) {
					apellido.setFocus();
					apellido.setSelection(0);
					
					int style = SWT.ICON_ERROR | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Error");
					dialog.setMessage("Es necesario incluir el apellido del cliente");
					dialog.open();
				}
			}
		});
		
		comuna.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					direccion.setFocus();
					direccion.setSelection(0, direccion.getCharCount());
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		direccion.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					giro.setFocus();
					giro.setSelection(0, giro.getCharCount());
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		giro.addKeyListener(new KeyListener() {
			
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