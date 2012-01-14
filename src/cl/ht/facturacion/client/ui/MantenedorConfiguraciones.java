package cl.ht.facturacion.client.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MantenedorConfiguraciones {

	Properties props = new Properties();
	
	Text txtEmpresa;
	Text txtAdministrador1;
	Text txtAdministrador2;
	Button btnImpresion;
	Button btnInfoEmpresa;
	Button btnInfoFecha;
	Button btnInfoGuias;
	Button btnInfoFacturas;
	Button btnInfoClientes;
	Button btnInfoMensaje;
	Button btnInfoNuevo;
	Button btnSplashActivation;
	
	public Shell createShell(final Display display) {
	
		final Shell shell = new Shell(display);
		shell.setText("Configuraciones del Sistema");
		shell.setImage(new Image(display, "media/bread-icon-3232.png"));
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginHeight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.marginRight = 10;
		shell.setLayout(gridLayout);
		
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		
		Group group = new Group(shell, SWT.NONE);
		group.setLayoutData(gridData);
		group.setLayout(new GridLayout(4, false));
		group.setText("Empresa");
		
		Label lblNumeroGuia = new Label(group, SWT.NONE);
		lblNumeroGuia.setText("Nombre empresa");
		
		txtEmpresa = new Text(group, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtEmpresa.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblAdministrador1 = new Label(group, SWT.NONE);
		lblAdministrador1.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblAdministrador1.setText("Administrador 1");
		
		txtAdministrador1 = new Text(group, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtAdministrador1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		Label lblAdministrador2 = new Label(group, SWT.NONE);
		lblAdministrador2.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblAdministrador2.setText("Administrador 2");
		
		txtAdministrador2 = new Text(group, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		txtAdministrador2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		
		Group group2 = new Group(shell, SWT.NONE);
		group2.setLayoutData(gridData);
		group2.setLayout(new GridLayout(4, false));
		group2.setText("Impresion");
		
		Label lblImpresion = new Label(group2, SWT.NONE);
		lblImpresion.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblImpresion.setText("Imprimir por seleccion manual");
		
		btnImpresion = new Button(group2, SWT.CHECK);
		btnImpresion.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnImpresion.setToolTipText("Toda impresión se efectuará escogiendo una impresora instalada en el sistema, "+
				"de lo contrario se imprimirá con la impresora por defecto sin preguntar.");
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		
		Group group3 = new Group(shell, SWT.NONE);
		group3.setLayoutData(gridData);
		group3.setLayout(new GridLayout(2, false));
		group3.setText("Información general");
		
		Label lblEmpresa = new Label(group3, SWT.NONE);
		lblEmpresa.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblEmpresa.setText("Nombre de la empresa");
		
		btnInfoEmpresa = new Button(group3, SWT.CHECK);
		btnInfoEmpresa.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnInfoEmpresa.setToolTipText("Mostrar el nombre de la empresa en función.");
		
		Label lblFecha = new Label(group3, SWT.NONE);
		lblFecha.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblFecha.setText("Fecha actual");
		
		btnInfoFecha = new Button(group3, SWT.CHECK);
		btnInfoFecha.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnInfoFecha.setToolTipText("Mostrar la fecha actual en la pantalla principal.");
		
		Label lblClientes = new Label(group3, SWT.NONE);
		lblClientes.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblClientes.setText("Número de clientes registrados");
		
		btnInfoClientes = new Button(group3, SWT.CHECK);
		btnInfoClientes.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnInfoClientes.setToolTipText("Mostrar la cantidad de clientes registrados en el sistema.");
		
		Label lblGuias = new Label(group3, SWT.NONE);
		lblGuias.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblGuias.setText("Última guía de despacho.");
		
		btnInfoGuias = new Button(group3, SWT.CHECK);
		btnInfoGuias.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnInfoGuias.setToolTipText("Mostrar el número de la última guia de despacho ingresada en el sistema.");
		
		Label lblFacturas = new Label(group3, SWT.NONE);
		lblFacturas.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblFacturas.setText("Última factura.");
		
		btnInfoFacturas = new Button(group3, SWT.CHECK);
		btnInfoFacturas.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnInfoFacturas.setToolTipText("Mostrar el número de la última factura ingresada en el sistema.");
		
		Label lblNuevoModulo = new Label(group3, SWT.NONE);
		lblNuevoModulo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblNuevoModulo.setText("Sobre novedades.");
		
		btnInfoNuevo = new Button(group3, SWT.CHECK);
		btnInfoNuevo.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnInfoNuevo.setToolTipText("Mostrar mensaje de novedades sobre la versión del sistema.");
		
		Label lblMensaje = new Label(group3, SWT.NONE);
		lblMensaje.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblMensaje.setText("Recomendaciones del desarrollador.");
		
		btnInfoMensaje = new Button(group3, SWT.CHECK);
		btnInfoMensaje.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnInfoMensaje.setToolTipText("Mostrar mensaje de recomendaciones del desarrollador del sistema.");
		
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.horizontalSpan = 2;
		
		Group group4 = new Group(shell, SWT.NONE);
		group4.setLayoutData(gridData);
		group4.setLayout(new GridLayout(2, false));
		group4.setText("Propiedades del Sistema");
		
		Label lblSplash = new Label(group4, SWT.NONE);
		lblSplash.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		lblSplash.setText("Pantalla de arranque.");
		
		btnSplashActivation = new Button(group4, SWT.CHECK);
		btnSplashActivation.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnSplashActivation.setToolTipText("Mostrar pantalla de carga al iniciar el sistema.");
		
		/*
		 * Initial properties data
		 */
		try {
			props.load(new FileInputStream("panaderia.properties"));
			txtEmpresa.setText(props.getProperty("empresa"));
			txtAdministrador1.setText(props.getProperty("administrador1"));
			txtAdministrador2.setText(props.getProperty("administrador2"));
			if(props.getProperty("impresora").equals("1"))
				btnImpresion.setSelection(true);
			if(props.getProperty("infoempresa").equals("1"))
				btnInfoEmpresa.setSelection(true);
			if(props.getProperty("infofecha").equals("1"))
				btnInfoFecha.setSelection(true);
			if(props.getProperty("infoclientes").equals("1"))
				btnInfoClientes.setSelection(true);
			if(props.getProperty("infoultimaguia").equals("1"))
				btnInfoGuias.setSelection(true);
			if(props.getProperty("infoultimafactura").equals("1"))
				btnInfoFacturas.setSelection(true);
			if(props.getProperty("infonuevomodulo").equals("1"))
				btnInfoNuevo.setSelection(true);
			if(props.getProperty("infomensaje").equals("1"))
				btnInfoMensaje.setSelection(true);
			if(props.getProperty("splash").equals("1"))
				btnSplashActivation.setSelection(true);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Options Buttons
		 */
		Button btnAplicar = new Button(shell, SWT.PUSH);
		btnAplicar.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnAplicar.setText("Aplicar");
		
		Button btnCancelar = new Button(shell, SWT.PUSH);
		btnCancelar.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false,
				false));
		btnCancelar.setText("Cancelar");
		
		
		/*
		 * Listeners
		 */
		btnAplicar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				props.setProperty("empresa", txtEmpresa.getText());
				props.setProperty("administrador1", txtAdministrador1.getText());
				props.setProperty("administrador2", txtAdministrador2.getText());
				props.setProperty("administrador1", txtAdministrador1.getText());

				if(btnImpresion.getSelection())
					props.setProperty("impresora", "1");
				else
					props.setProperty("impresora", "0");
				
				if(btnInfoEmpresa.getSelection())
					props.setProperty("infoempresa", "1");
				else
					props.setProperty("infoempresa", "0");
				
				if(btnInfoClientes.getSelection())
					props.setProperty("infoclientes", "1");
				else
					props.setProperty("infoclientes", "0");
				
				if(btnInfoFecha.getSelection())
					props.setProperty("infofecha", "1");
				else
					props.setProperty("infofecha", "0");
				
				if(btnInfoGuias.getSelection())
					props.setProperty("infoultimaguia", "1");
				else
					props.setProperty("infoultimaguia", "0");
				
				if(btnInfoFacturas.getSelection())
					props.setProperty("infoultimafactura", "1");
				else
					props.setProperty("infoultimafactura", "0");
				
				if(btnInfoNuevo.getSelection())
					props.setProperty("infonuevomodulo", "1");
				else
					props.setProperty("infonuevomodulo", "0");
				
				if(btnInfoMensaje.getSelection())
					props.setProperty("infomensaje", "1");
				else
					props.setProperty("infomensaje", "0");
				
				if(btnSplashActivation.getSelection())
					props.setProperty("splash", "1");
				else
					props.setProperty("splash", "0");
				
				try {
					props.store(new FileOutputStream("panaderia.properties"), "Cambio de propiedades");
					
					MessageBox dialog = new MessageBox(shell);
					int style = SWT.ICON_INFORMATION | SWT.OK;
					dialog = new MessageBox(shell, style);
					dialog.setText("Cambios Guardados");
					dialog.setMessage("Los cambios han sido guardados en forma exitosa.");
					
					if(dialog.open() == SWT.OK)
						shell.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnCancelar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				MessageBox dialog = new MessageBox(shell);
				int style = SWT.ICON_WARNING | SWT.YES | SWT.NO;
				dialog = new MessageBox(shell, style);
				dialog.setText("Cancelar Cambios");
				dialog.setMessage("¿Esta seguro que desea salir y perder los cambios?");
				
				if(dialog.open() == SWT.YES)
					shell.close();
			}
		});
		
		return shell;
	}//end createshell
	
}
