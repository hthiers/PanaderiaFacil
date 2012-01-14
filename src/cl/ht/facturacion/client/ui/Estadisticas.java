package cl.ht.facturacion.client.ui;

import java.util.ArrayList;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import cl.ht.facturacion.services.impl.ServiceReportsImpl;

public class Estadisticas {

	Combo mesReporte;
	Text txtHistorial;
	Button btnGenerarR;
	
	public Shell createShell(final Display display) {
		
		final Shell shell = new Shell(display);
	    shell.setText("Estadísticas e Historial");
		shell.setImage(new Image(display, "media/bread-icon-3232.png"));
		 
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginBottom = 10;
		gridLayout.marginLeft = 5;
		gridLayout.marginTop = 10;
		gridLayout.marginRight = 10;
		
		shell.setLayout(gridLayout);
		
	    TabFolder folder = new TabFolder(shell, SWT.NONE);
 
	    /*
	     * Tab 1: Historial
	     * Ventas segun guias
	     * IVA segun guias
	     * Precio mas alto
	     * Precio mas bajo
	     */
	    TabItem tab1 = new TabItem(folder, SWT.NONE);
	    tab1.setText("Historial");
 
	    gridLayout = new GridLayout();
		gridLayout.numColumns = 3;

	    Group grpHistorial = new Group(folder, SWT.NONE);
	    grpHistorial.setText("Historial General");
	    grpHistorial.setLayout(gridLayout);

	    GridData gridData = new GridData();
	    gridData.horizontalAlignment = SWT.BEGINNING;
	    
	    Label lblMes = new Label(grpHistorial, SWT.NONE);
	    lblMes.setText("Mes de reporte");
	    lblMes.setLayoutData(gridData);	    
	    
	    gridData = new GridData();
	    gridData.horizontalAlignment = SWT.BEGINNING;
	    gridData.widthHint = 120;
	    
	    String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		mesReporte = new Combo(grpHistorial, SWT.DROP_DOWN);
	    mesReporte.setItems(meses);
	    mesReporte.setLayoutData(gridData);
	    
	    int mesInt = Calendar.getInstance().get(Calendar.MONTH);
	    mesReporte.select(mesInt);
	    
	    gridData = new GridData();
	    gridData.horizontalAlignment = SWT.BEGINNING;
	    
	    btnGenerarR = new Button(grpHistorial, SWT.PUSH);
	    btnGenerarR.setText("Generar");
	    btnGenerarR.setToolTipText("Generar reporte para el mes seleccionado");
	    btnGenerarR.setLayoutData(gridData);
	    
	    gridData = new GridData();
	    gridData.widthHint = 500;
	    gridData.heightHint = 500;
	    gridData.horizontalSpan = 3;
	    gridData.grabExcessHorizontalSpace = true;
	    gridData.grabExcessVerticalSpace = true;

	    /*
	     * Ventas
	     */
	    txtHistorial = new Text(grpHistorial, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	    txtHistorial.setEditable(false);
	    txtHistorial.setLayoutData(gridData);

	    tab1.setControl(grpHistorial);	    
	    
	    /*
	     * Tab 2: Gastos
	     */
	    TabItem tab2 = new TabItem(folder, SWT.NONE);
	    tab2.setText("Tab 2");
 
	    Group group = new Group(folder, SWT.NONE);
	    group.setText("Group in Tab 2");
 
	    Label label = new Label(group, SWT.BORDER);
	    label.setText("Label in Tab 2");
	    label.setBounds(10, 100, 100, 100);
 
	    Text text = new Text(group, SWT.NONE);
	    text.setText("Gastosss");
	    text.setBounds(10, 200, 100, 100);
 
	    tab2.setControl(group);
		
	    final ServiceReportsImpl servReport = new ServiceReportsImpl();
	    
	    /*
	     * Listeners
	     */
	    btnGenerarR.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		// TODO Auto-generated method stub
	    		txtHistorial.setText("");
	    		
	    		ArrayList<String> reportesGuias = servReport.getGuiasReportByDate(mesReporte.getText());
	    		txtHistorial.append("Guias:\n");
	    	    txtHistorial.append(reportesGuias.get(0)+"\n");
	    	    txtHistorial.append(reportesGuias.get(1)+"\n");

	    	    ArrayList<String> reportesFacts = servReport.getFacturasReportByDate(mesReporte.getText());
	    	    txtHistorial.append("\nFacturado:\n");
	    	    txtHistorial.append(reportesFacts.get(0)+"\n");
	    	    txtHistorial.append(reportesFacts.get(1)+"\n");
	    	    txtHistorial.append(reportesFacts.get(2)+"\n");
	    	    txtHistorial.append(reportesFacts.get(3)+"\n");
	    	    
	    	    /*
	    	     * COMPLETAR CON REPORTE DE FACTURAS
	    	     * COMPLETAR CON DATOS DE VENTAS
	    	     * COMPLETAR CON KILOS SEGUN TIPO DE PRODUCTO
	    	     * COMPLETAR CON KILOS SEGUN CLIENTE
	    	     * COMPLETAR CON OPCION DE RESUMEN VENTA ANUAL
	    	     */
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
	    
		return shell;
	}
}
