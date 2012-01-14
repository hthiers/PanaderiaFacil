package cl.ht.facturacion.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cl.ht.facturacion.client.ui.*;
import cl.ht.facturacion.dao.impl.DAOClientesImpl;
import cl.ht.facturacion.dao.impl.DAOFacturasImpl;
import cl.ht.facturacion.dao.impl.DAOGuiasImpl;

public class MainClient {

	static Properties props = new Properties();
	static String version = "0.4.6";
	
	Button btnGuia;
	Button btnFactura;
	Button btnClientes;
	Button btnEmisiones;
	Button btnEstadisticas;
	Button btnPropiedades;
	
	public Shell createShell(final Display display) {
		
		final Shell shell = new Shell(display);
		final Image imgicon = new Image(display, "media/bread-icon-3232.png");
		
		shell.setText("Panadería Fácil - Facturación y Administración");
		shell.setImage(imgicon);
		
		/*
		 * Menu barra dinámico
		 * Debe existir un menu barra principal ya existente (menuBar)
		 * Este debe cargar items segun plugins activados
		 */
//		Menu menuBar = new Menu(shell, SWT.BAR);
//		shell.setMenuBar(MenuSistema.loadMenu(menuBar, display));
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginBottom = 10;
		gridLayout.marginLeft = 5;
		gridLayout.marginTop = 10;
		gridLayout.marginRight = 10;
		
		shell.setLayout(gridLayout);
		
		/*
		 * Ghost para dejar espacio entre elementos
		 */
		GridData gridGhost = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridGhost.horizontalSpan = 4;
		gridGhost.heightHint = 1;
		Label lblghost = new Label(shell, SWT.NONE);
		lblghost.setText("");
		lblghost.setLayoutData(gridGhost);
		
		/*
		 * Menu (izquierda)
		 */
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.marginWidth = 5;
		fillLayout.marginHeight = 5;
		
		GridData gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridData.widthHint = 150;
		
		Composite compoMenu = new Composite(shell, SWT.BORDER);
		compoMenu.setLayout(fillLayout);
		compoMenu.setLayoutData(gridData);
		
		btnGuia = new Button(compoMenu, SWT.PUSH);
		btnGuia.setText("Nueva &Guía    ");
		btnGuia.setImage(new Image(display, "media/Properties-icon.png"));
		
		btnGuia.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Shell shellNueva = new NuevaGuiaDespacho().createShell(display);
				shellNueva.pack();
				shellNueva.open();
			}
		});
		
		btnFactura = new Button(compoMenu, SWT.PUSH);
		btnFactura.setText("Nueva &Factura");
		btnFactura.setImage(new Image(display, "media/Properties-icon.png"));
		
		btnFactura.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Shell shellNueva = new NuevaFactura().createShell(display);
				shellNueva.pack();
				shellNueva.open();
			}
		});
		
		btnClientes = new Button(compoMenu, SWT.PUSH);
		btnClientes.setText("&Clientes          ");
		btnClientes.setImage(new Image(display, "media/User-icon.png"));
		btnClientes.setAlignment(SWT.LEFT);
		
		btnClientes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				Shell shellMantClientes = new MantenedorClientes().createShell(display);
				shellMantClientes.pack();
				shellMantClientes.open();
			}
		});
		
		btnEmisiones = new Button(compoMenu, SWT.PUSH);
		btnEmisiones.setText("&Documentos  ");
		btnEmisiones.setImage(new Image(display, "media/Folder-icon.png"));
		
		btnEmisiones.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				Shell shellMantDocs = new MantenedorDocumentos().createShell(display);
				shellMantDocs.pack();
				shellMantDocs.open();
			}
		});
		
		btnEstadisticas = new Button(compoMenu, SWT.PUSH);
		btnEstadisticas.setText("&Estadisticas  ");
		btnEstadisticas.setImage(new Image(display, "media/pie-chart-icon.png"));
		btnEstadisticas.setEnabled(false);
		
		btnEstadisticas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				Shell shellEstadisticas = new Estadisticas().createShell(display);
				shellEstadisticas.pack();
				shellEstadisticas.open();
			}
		});
		
		btnPropiedades = new Button(compoMenu, SWT.PUSH);
		btnPropiedades.setText("&Propiedades  ");
		btnPropiedades.setImage(new Image(display, "media/Settings-icon.png"));
		
		btnPropiedades.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				Shell shellMantProp = new MantenedorConfiguraciones().createShell(display);
				shellMantProp.pack();
				shellMantProp.open();
			}
		});
		
		/*
		 * Vista resumen (derecha)
		 */
		RowLayout rowLayout = new RowLayout();
		
		GridData gridResumen = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridResumen.widthHint = 300;
		
		compoMenu = new Composite(shell, SWT.NONE);
		compoMenu.setLayout(rowLayout);
		compoMenu.setLayoutData(gridResumen);
		
		RowData rowData = new RowData(130,20);

		Label lblResumen = new Label(compoMenu, SWT.BEGINNING);
		lblResumen.setText("Información general");
		lblResumen.setLayoutData(rowData);

		rowData = new RowData(250,180);
		
		Text text = new Text(compoMenu, SWT.BORDER | SWT.V_SCROLL);
		text.setEditable(false);
		text.setLayoutData(rowData);
		
		/*
		 * Carga de datos para informacion general
		 */
		DAOClientesImpl daoclientes = new DAOClientesImpl();
		DAOGuiasImpl daoguias = new DAOGuiasImpl();
		DAOFacturasImpl daofacturas = new DAOFacturasImpl();
		Calendar fechaHoy = Calendar.getInstance();
		fechaHoy.add(Calendar.MONTH, +1);//Mostrar número del mes y no posición
		int numeroClientes =  daoclientes.getAllClientes(false).size();
		int UltimaGuia = daoguias.getLastGuia().getNumero();
		int UltimaFactura = daofacturas.getLastFactura().getNumero();
		
		try {
			props.load(new FileInputStream("panaderia.properties"));
			
			if(props.getProperty("infoempresa").equals("1"))
				text.append(props.getProperty("empresa") +"\n\n");
			if(props.getProperty("infofecha").equals("1"))
				text.append("Fecha: " +fechaHoy.get(Calendar.DATE)+"/"+fechaHoy.get(Calendar.MONTH)+"/"+fechaHoy.get(Calendar.YEAR) +"\n");
			if(props.getProperty("infoclientes").equals("1"))
				text.append("Clientes registrados: " +numeroClientes +"\n");
			if(props.getProperty("infoultimaguia").equals("1"))
				text.append("Última guia emitida: " +UltimaGuia +"\n");
			if(props.getProperty("infoultimafactura").equals("1"))
				text.append("Última factura emitida: " +UltimaFactura +"\n");
		    text.append("\n");
		    if(props.getProperty("infonuevomodulo").equals("1"))
		    	text.append("Nuevo módulo de clientes."+"\n");
		    text.append("\n");
		    if(props.getProperty("infomensaje").equals("1")) {
			    text.append("Si existen problemas en la "+"\n");
			    text.append("impresión de guias o facturas "+"\n");
			    text.append("es necesario contactar al "+"\n");
			    text.append("programador del sistema."+"\n");
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    /*
		 * Ghost para dejar espacio entre elementos
		 */
		gridGhost = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridGhost.horizontalSpan = 4;
		gridGhost.heightHint = 10;
		lblghost = new Label(shell, SWT.NONE);
		lblghost.setText("");
		lblghost.setLayoutData(gridGhost);
	    
		/*
		 * Boton Salir (bottom)
		 */
		Image imagen = new Image(display, "media/Log-Out-icon.png");
		
		GridData gridBoton = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridBoton.horizontalSpan = 2;
		
		Button btnSalir = new Button(shell, SWT.PUSH);
		btnSalir.setAlignment(SWT.CENTER);
		btnSalir.setImage(imagen);
		btnSalir.setLayoutData(gridBoton);
		
		btnSalir.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
			}
		});
		
		GridData gridBottom = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
		gridBottom.horizontalSpan = 2;
		Label lblSysInfo = new Label(shell, SWT.NONE);
		lblSysInfo.setText("Producto licenciado bajo GNU GPL v3.               Panadería Fácil versión: "+version);
		lblSysInfo.setLayoutData(gridBottom);
		
		shell.addListener(SWT.Traverse, new Listener() {
		      public void handleEvent(Event event) {
		        if(event.detail == SWT.TRAVERSE_ESCAPE) {
		          shell.close();
		        }
		      }
		});
		
		shell.addListener(SWT.Close, new Listener() {
		      public void handleEvent(Event event) {
		        exitProgram(shell);
		      }
		});
		
		return shell;
		
	}
	
	public void exitProgram(Shell shell) {
		System.exit(0);
	}
}