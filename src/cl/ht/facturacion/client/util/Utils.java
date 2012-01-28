package cl.ht.facturacion.client.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import cl.ht.facturacion.client.vo.VOCliente;
import cl.ht.facturacion.client.vo.VOFactura;
import cl.ht.facturacion.client.vo.VOGuia;
import cl.ht.facturacion.client.vo.VOItemProducto;
import cl.ht.facturacion.client.vo.VOProducto;
import cl.ht.facturacion.dao.impl.DAOClientesImpl;
import cl.ht.facturacion.dao.impl.DAOGuiasImpl;

public class Utils {

	static Properties props = new Properties();
	static boolean dialogPrint = false;
	
	public static String getRutOnly(String cadena) {
		
		String[] stringVars;
		String rut;
		stringVars = cadena.split(",");
		rut = stringVars[0];
		
		return rut;
	}
	
	public static Calendar getFixedDate(String initDate) {
		
		Calendar fecha = Calendar.getInstance();
		String[] fechaS;
		
		Pattern p = Pattern.compile("[/,-]");
		Matcher m = p.matcher(initDate);
		if(m.find()) {
			fechaS = initDate.split(p.pattern());
			int mes = Integer.parseInt(fechaS[1]);
			mes -= 1;
			
			fecha.set(Integer.parseInt(fechaS[2]), mes, Integer.parseInt(fechaS[0]));
			
			return fecha;
		}
		else
			System.out.println("Formato de fecha incorrecto");
		
		return null;
	}
	
	public static Calendar getDateByMonth(String month, String year) {
		
		Calendar fecha = Calendar.getInstance();
		
		int numericMonth = 0;
		
		if(month.equals("Enero"))
			numericMonth = 0;
		else if (month.equals("Febrero")) {
			numericMonth = 1;
		}
		else if (month.equals("Marzo")) {
			numericMonth = 2;
		}
		else if (month.equals("Abril")) {
			numericMonth = 3;
		}
		else if (month.equals("Mayo")) {
			numericMonth = 4;
		}
		else if (month.equals("Junio")) {
			numericMonth = 5;
		}
		else if (month.equals("Julio")) {
			numericMonth = 6;
		}
		else if (month.equals("Agosto")) {
			numericMonth = 7;
		}
		else if (month.equals("Septiembre")) {
			numericMonth = 8;
		}
		else if (month.equals("Octubre")) {
			numericMonth = 9;
		}
		else if (month.equals("Noviembre")) {
			numericMonth = 10;
		}
		else if (month.equals("Diciembre")) {
			numericMonth = 11;
		}
		
		fecha.set(Calendar.DATE, 1);
		fecha.set(Calendar.MONTH, numericMonth);
		
		if(year != null)
			fecha.set(Calendar.YEAR, Integer.parseInt(year));
		
		System.out.println("fecha ini: "+fecha.get(Calendar.DATE)+","+fecha.get(Calendar.MONTH)+","+fecha.get(Calendar.YEAR));
		
		return fecha;
	}
	
	public static Calendar getNextDateByMonth(String month, String year) {
		
		Calendar fecha = Calendar.getInstance();
		
		int numericMonth = 0;
		
		if(month.equals("Enero"))
			numericMonth = 0;
		else if (month.equals("Febrero")) {
			numericMonth = 1;
		}
		else if (month.equals("Marzo")) {
			numericMonth = 2;
		}
		else if (month.equals("Abril")) {
			numericMonth = 3;
		}
		else if (month.equals("Mayo")) {
			numericMonth = 4;
		}
		else if (month.equals("Junio")) {
			numericMonth = 5;
		}
		else if (month.equals("Julio")) {
			numericMonth = 6;
		}
		else if (month.equals("Agosto")) {
			numericMonth = 7;
		}
		else if (month.equals("Septiembre")) {
			numericMonth = 8;
		}
		else if (month.equals("Octubre")) {
			numericMonth = 9;
		}
		else if (month.equals("Noviembre")) {
			numericMonth = 10;
		}
		else if (month.equals("Diciembre")) {
			numericMonth = 11;
		}
		
		fecha.set(Calendar.DATE, 1);
		numericMonth += 1;
		
		//fecha.set(Calendar.MONTH, numericMonth);
		//fecha.set(Calendar.YEAR, Integer.parseInt(year));
		
		if(numericMonth > 11) {
			fecha.set(Calendar.MONTH, 0);
			
			if(year != null)
				fecha.set(Calendar.YEAR, Integer.parseInt(year)+1);
		}
		else {
			fecha.set(Calendar.MONTH, numericMonth);
			
			if(year != null)
				fecha.set(Calendar.YEAR, Integer.parseInt(year));
		}
		
		System.out.println("fecha tope: "+fecha.get(Calendar.DATE)+","+fecha.get(Calendar.MONTH)+","+fecha.get(Calendar.YEAR));
		
		return fecha;
	}
	
	public static String cleanString(String cadena) {
		String aRemplazar = cadena;
		String remplazado = aRemplazar.replace("{", "");
		remplazado = remplazado.replace("}", "");
		
		return remplazado;
	}
	
	public static boolean checkEstado(Button estado) {
		boolean valorEstado = true;
		if(estado.getSelection() == false)
			valorEstado = false;
		
		return valorEstado;
	}

	public static FontData[] changeFont(Label text, int height, boolean bold) {
		FontData[] fontData = text.getFont().getFontData();
		for(int i = 0; i < fontData.length; ++i) {
		    fontData[i].setHeight(height);
		    if(bold)
		    	fontData[i].setStyle(SWT.BOLD);
		}

		return fontData;
	}
	
	public static FontData[] changeFont(Text text, int height, boolean bold) {
		FontData[] fontData = text.getFont().getFontData();
		for(int i = 0; i < fontData.length; ++i) {
		    fontData[i].setHeight(height);
		    if(bold)
		    	fontData[i].setStyle(SWT.BOLD);
		}

		return fontData;
	}

	public static void populateItem(TableItem item, VOCliente cliente, Shell shell) {
		if(cliente.getEstado() == 2) {
    		item.setBackground(new Color(shell.getDisplay(), new RGB(255,255,153)));
    		item.setForeground(new Color(shell.getDisplay(), new RGB(255,0,0)));
    	}
	    item.setText(new String[] {cliente.getRut(), cliente.getNombres(), cliente.getApellidos(), cliente.getCiudad(), cliente.getComuna(), cliente.getDireccion(), cliente.getGiro(), Integer.toString(cliente.getEstado())});
	}
	
	public static String monthToString(int mes){
		
		String stringMes = "";
		
		if(mes == 1)
			stringMes = "Enero";
		else if (mes == 2) {
			stringMes = "Febrero";
		}
		else if (mes == 3) {
			stringMes = "Marzo";
		}
		else if (mes == 4) {
			stringMes = "Abril";
		}
		else if (mes == 5) {
			stringMes = "Mayo";
		}
		else if (mes == 6) {
			stringMes = "Junio";
		}
		else if (mes == 7) {
			stringMes = "Julio";
		}
		else if (mes == 8) {
			stringMes = "Agosto";
		}
		else if (mes == 9) {
			stringMes = "Septiembre";
		}
		else if (mes == 10) {
			stringMes = "Octubre";
		}
		else if (mes == 11) {
			stringMes = "Noviembre";
		}
		else if (mes == 12) {
			stringMes = "Diciembre";
		}
		else
			stringMes = "Enero";
		
		return stringMes;
	}
	
	public static void imprimirGuia(Shell shell, VOGuia guia, VOCliente cliente, ArrayList<VOItemProducto> listaItems, String fecha) {
		PrintDialog dialog = new PrintDialog(shell);
	    // Opens a dialog and let use user select the 
	    // target printer and configure various settings.
		
		try {
			props.load(new FileInputStream("panaderia.properties"));
			
			if(props.getProperty("impresora").equals("1"))
				dialogPrint = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//debug
		System.out.println("guia a imprimir: "+guia.getNumero());
		System.out.println("cliente a imprimir: "+cliente.getRut());
		System.out.println("cliente direccion a imprimir: "+cliente.getDireccion());
		System.out.println("numero items a imprimir "+listaItems.size());
		
		//Impresion por seleccion de impresora
		if(dialogPrint){
		    PrinterData printerData = dialog.open();
		    if(printerData != null) { // If a printer is selected
		      // Creates a printer.
		      Printer printer = new Printer(printerData);
		      
		      // Starts the print job.
		      if(printer.startJob("guia despacho")) {
		        GC gc = new GC(printer);
		        Font fuente = new Font(printer, "Arial", 10, SWT.NONE);
		        gc.setFont(fuente);
	
		        // Starts a new page.
		        if(printer.startPage()) {
		        	
		        	//inicio
		        	int fila = 109;
		        	int columna = 38;
		        	
		        	//Fecha
		        	String[] fechaS;
		    		fechaS = fecha.split("/");
		    		
		        	gc.drawString(""+fechaS[0], columna, fila);
		        	columna = 120;
		        	gc.drawString(monthToString(Integer.parseInt(fechaS[1])).toUpperCase(), columna, fila);
		        	columna = 310;
		        	gc.drawString(""+fechaS[2], columna, fila);
		        	
		        	fila += 12;
		        	columna = 65;
		        	
		        	//Datos cliente
		        	gc.drawString(cliente.getNombres().toUpperCase()+" "+cliente.getApellidos().toUpperCase(), columna, fila);
		        	
		        	fila += 12; 
		        	gc.drawString(cliente.getRut().toUpperCase(), columna, fila);
		        	
		        	columna = 350;
		        	gc.drawString(cliente.getGiro().toUpperCase(), columna, fila);
		        	
		        	columna = 810;
		        	gc.drawString(cliente.getCiudad().toUpperCase(), columna, fila);
		        	
		        	columna = 65;
		        	fila += 12;
		        	gc.drawString(cliente.getDireccion().toUpperCase(), columna, fila);
		        	
		        	columna = 810;
		        	gc.drawString(cliente.getComuna().toUpperCase(), columna, fila);

		        	//Detalle venta
		        	fila = 205;
		        	
		        	Iterator<VOItemProducto> itemsIt = listaItems.iterator();
		        	while(itemsIt.hasNext()) {
		        		VOItemProducto item = itemsIt.next();

		        		System.out.println("guia #"+guia.getNumero());
		        		System.out.println("item ID:"+item.getId());
		        		System.out.println("cantidad: "+item.getCantidad().toString());
		        		System.out.println("detalle: "+item.getIdprod().getDescripcion());
		        		System.out.println("precio unit: "+item.getPrecio());
		        		System.out.println("total unit: "+item.getTotal().toString());
		        		
		        		columna = 10;
		        		gc.drawString(item.getCantidad().toString(), columna, fila);
		        		
		        		columna = 110;
		        		gc.drawString(item.getIdprod().getDescripcion(), columna, fila);

		        		columna = 680;
		        		gc.drawString(""+item.getPrecio(), columna, fila);
		        		
		        		columna = 810;
		        		gc.drawString(item.getTotal().toString(), columna, fila);
		        		
		        		fila += 10;
		        	}
		        	
		        	//total de venta (guia)
		        	fila = 380;
		        	int totalGuia = guia.getTotal().toBigInteger().intValue();
		        	gc.drawString(""+totalGuia, columna, fila);
		        	
		          // Finishes the page. 
		          printer.endPage();
		        }
		        
		        gc.dispose();
		        
		        // Ends the job.
		        printer.endJob();
		      }
		      
		      // Disposes the printer object after use. 
		      printer.dispose();
		      
		      if(printer.isDisposed())
		    	  System.out.println("PRINT IS FREE");
		      
		      System.out.println("Print job done.");
		    }
		}
		//Impresion por impresora por defecto
		else{
			// Creates a printer.
			PrinterData defaultPrinterData = Printer.getDefaultPrinterData();
		    Printer printer = new Printer(defaultPrinterData);
		      
		    //Starts the print job.
		    if(printer.startJob("guia despacho")) {
		    	GC gc = new GC(printer);
		        Font fuente = new Font(printer, "Arial", 10, SWT.NONE);
		        gc.setFont(fuente);
	
		        // Starts a new page.
		        if(printer.startPage()) {
		        	
		        	//inicio
		        	int fila = 109;
		        	int columna = 38;
		        	
		        	//Fecha
		        	String[] fechaS;
		    		fechaS = fecha.split("/");
		    		
		        	gc.drawString(""+fechaS[0], columna, fila);
		        	columna = 120;
		        	gc.drawString(monthToString(Integer.parseInt(fechaS[1])).toUpperCase(), columna, fila);
		        	columna = 310;
		        	gc.drawString(""+fechaS[2], columna, fila);
		        	
		        	fila += 11;
		        	columna = 65;
		        	
		        	//Datos cliente
		        	gc.drawString(cliente.getNombres().toUpperCase()+" "+cliente.getApellidos().toUpperCase(), columna, fila);
		        	
		        	fila += 11; 
		        	gc.drawString(cliente.getRut().toUpperCase(), columna, fila);
		        	
		        	columna = 350;
		        	gc.drawString(cliente.getGiro().toUpperCase(), columna, fila);
		        	
		        	columna = 810;
		        	gc.drawString(cliente.getCiudad().toUpperCase(), columna, fila);
		        	
		        	columna = 65;
		        	fila += 11;
		        	gc.drawString(cliente.getDireccion().toUpperCase(), columna, fila);
		        	
		        	columna = 810;
		        	gc.drawString(cliente.getComuna().toUpperCase(), columna, fila);

		        	//Detalle venta
		        	fila = 205;
		        	
		        	Iterator<VOItemProducto> itemsIt = listaItems.iterator();
		        	while(itemsIt.hasNext()) {
		        		VOItemProducto item = itemsIt.next();

		        		System.out.println("guia #"+guia.getNumero());
		        		System.out.println("item ID:"+item.getId());
		        		System.out.println("cantidad: "+item.getCantidad().toString());
		        		System.out.println("detalle: "+item.getIdprod().getDescripcion());
		        		System.out.println("precio unit: "+item.getPrecio());
		        		System.out.println("total unit: "+item.getTotal().toString());
		        		
		        		columna = 10;
		        		gc.drawString(""+item.getCantidad().toString(), columna, fila);
		        		
		        		columna = 110;
		        		gc.drawString(item.getIdprod().getDescripcion(), columna, fila);
		        		
		        		columna = 680;
		        		gc.drawString(""+item.getPrecio(), columna, fila);
		        		
		        		columna = 810;
		        		gc.drawString(""+item.getTotal().toString(), columna, fila);
		        		
		        		fila += 10;
		        	}
		        	
		        	//total de venta (guia)
		        	fila = 380;
		        	int totalGuia = guia.getTotal().toBigInteger().intValue();
		        	gc.drawString(""+totalGuia, columna, fila);
		        	
		          // Finishes the page. 
		          printer.endPage();
		        }
		        
		        gc.dispose();
		        
		        // Ends the job.
		        printer.endJob();
		      }
		      
		      // Disposes the printer object after use. 
		      printer.dispose();
		      
		      if(printer.isDisposed())
		    	  System.out.println("PRINT IS FREE");
		      
		      System.out.println("Print job done.");
		    }
	}
	
	public static void imprimirFactura(Shell shell, VOFactura factura, VOCliente cliente, String fecha) {
		PrintDialog dialog = new PrintDialog(shell);
	    // Opens a dialog and let use user select the 
	    // target printer and configure various settings.
		
		try {
			props.load(new FileInputStream("panaderia.properties"));
			
			if(props.getProperty("impresora").equals("1"))
				dialogPrint = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Impresion por seleccion de impresora
		if(dialogPrint){
		    PrinterData printerData = dialog.open();
		    if(printerData != null) { // If a printer is selected
		      // Creates a printer.
		      Printer printer = new Printer(printerData);
		      
		      // Starts the print job.
		      if(printer.startJob("factura")) {
		        GC gc = new GC(printer);
		        Font fuente = new Font(printer, "Arial", 10, SWT.NONE);
		        gc.setFont(fuente);
	
		        // Starts a new page.
		        if(printer.startPage()) {
		        	
		        	//inicio
		        	int fila = 109;
		        	int columna = 39;
		        	
		        	/*
		        	 * Fecha
		        	 */
		        	String[] fechaS;
		    		fechaS = fecha.split("/");
		    		
		        	gc.drawString(""+fechaS[0], columna, fila);
		        	columna = 120;
		        	gc.drawString(monthToString(Integer.parseInt(fechaS[1])).toUpperCase(), columna, fila);
		        	columna = 310;
		        	gc.drawString(""+fechaS[2], columna, fila);
		        	
		        	fila += 11;
		        	columna = 65;
		        	
		        	/*
		        	 * Datos cliente
		        	 */
		        	gc.drawString(cliente.getNombres().toUpperCase()+" "+cliente.getApellidos().toUpperCase(), columna, fila);
		        	
		        	fila += 11; 
		        	gc.drawString(cliente.getRut().toUpperCase(), columna, fila);
		        	
		        	columna = 350;
		        	gc.drawString(cliente.getGiro().toUpperCase(), columna, fila);
		        	
		        	columna = 810;
		        	gc.drawString(cliente.getCiudad().toUpperCase(), columna, fila);
		        	
		        	columna = 65;
		        	fila += 11;
		        	gc.drawString(cliente.getDireccion().toUpperCase(), columna, fila);
		        	
		        	columna = 810;
		        	gc.drawString(cliente.getComuna().toUpperCase(), columna, fila);

		        	/*
		        	 * Detalle venta
		        	 */
		        	fila = 205;
		        	
		        	//Obtener numero de productos existentes en guias
		        	DAOGuiasImpl daoGuias = new DAOGuiasImpl();
		        	ArrayList<VOProducto> listaProductos = daoGuias.getAllProductosInGuiasByFactura(factura.getId());
		        	
		        	//Extraer guias segun producto y factura e imprimir segun producto
		        	ArrayList<VOGuia> listaGuias;
		        	VOItemProducto itemProducto;
		        	
		        	for(int x=0; x<listaProductos.size(); x++){
		        		listaGuias = new ArrayList<VOGuia>();
			        	listaGuias = daoGuias.getAllGuiasByFacturaProducto(factura.getId(),listaProductos.get(x).getId());
			        	
			        	BigDecimal cantidadProducto = new BigDecimal(0);
			        	int valorProducto = 0;
			        	BigDecimal totalFinalProducto = new BigDecimal(0);
			        	
			        	Iterator<VOGuia> guiasIt = listaGuias.iterator();
			        	while(guiasIt.hasNext()) {
			        		VOGuia guia = guiasIt.next();
			        		itemProducto = daoGuias.getItemProductoByProductoYGuia(listaProductos.get(x).getId(), guia.getId());
			        		
			        		valorProducto = itemProducto.getPrecio();
			        		cantidadProducto = itemProducto.getCantidad();
			        		totalFinalProducto = itemProducto.getTotal();
			        	}
			        	
			        	/*
			        	 * PARCHE MOMENTANEO
			        	 */
			        	//Obtener precio segun cantidad y total ingresados
//			        	valorProducto = totalFinalProducto / cantidadProducto;
			        	
			        	//Imprimir cantidad, detalle nombre y total vendido del producto x de todas las guias en cuestion (segun factura)

			        	System.out.println("@kg: "+cantidadProducto.toString());
			        	System.out.println("@precio: "+valorProducto);
			        	System.out.println("@total (kg x precio): "+totalFinalProducto.toString());
			        	
			        	columna = 10;
		        		gc.drawString(cantidadProducto.toString(), columna, fila);
		        		
		        		columna = 110;
		        		gc.drawString(""+listaProductos.get(x).getDescripcion().toUpperCase(), columna, fila);
		        		
		        		columna = 660;
		        		gc.drawString(""+valorProducto, columna, fila);
		        		
			        	columna = 810;
			        	gc.drawString(totalFinalProducto.toString(), columna, fila);
			        	
			        	fila += 10;
		        	}

		        	//Lista de guias que corresponden a la factura
		        	listaGuias = daoGuias.getAllGuiasByFactura(factura.getId());

		        	columna = 110;
		        	gc.drawString("SEGÚN GUÍAS: ", columna, fila);
		        	fila += 10;

		        	Iterator<VOGuia> guiasIt = listaGuias.iterator();
		        	while(guiasIt.hasNext()) {
		        		VOGuia guia = guiasIt.next();

		        		System.out.println("guia #"+guia.getNumero());
		        		
		        		if(columna >= 610){
		        			columna = 110;
		        			fila += 10;
		        		}
		        		
		        		gc.drawString(""+guia.getNumero(), columna, fila);
		        		columna += 45;
		        	}
		        	
		        	//totales de venta (factura)

		        	/*
		        	 * ARREGLAR FILA EN VALORES FINALES
		        	 */
		        	fila = 393;
		        	columna = 810;
		        	
		        	gc.drawString(factura.getValorNeto().toString(), columna, fila);
		        	
		        	fila += 12;
		        	gc.drawString(factura.getValorIva().toString(), columna, fila);
		        	
		        	fila += 12;
		        	gc.drawString(factura.getValorTotal().toString(), columna, fila);

		        	//DEBUG
		        	System.out.println("neto: "+factura.getValorNeto().toString());
		        	System.out.println("iva: "+factura.getValorIva().toString());
		        	System.out.println("total: "+factura.getValorTotal().toString());

		          // Finishes the page. 
		          printer.endPage();
		        }
		        
		        gc.dispose();
		        
		        // Ends the job.
		        printer.endJob();
		      }
		      
		      // Disposes the printer object after use. 
		      printer.dispose();
		      
		      if(printer.isDisposed())
		    	  System.out.println("PRINT IS FREE");
		      
		      System.out.println("Print job done.");
		    }
		}
		//Impresion por impresora por defecto
		else{
			// Creates a printer.
			PrinterData defaultPrinterData = Printer.getDefaultPrinterData();
		    Printer printer = new Printer(defaultPrinterData);
		      
		    //Starts the print job.
		    if(printer.startJob("factura")) {
		    	GC gc = new GC(printer);
		        Font fuente = new Font(printer, "Arial", 10, SWT.NONE);
		        gc.setFont(fuente);
	
		        // Starts a new page.
		        if(printer.startPage()) {
		        	
		        	//inicio
		        	int fila = 109;
		        	int columna = 38;
		        	
		        	//Fecha
		        	String[] fechaS;
		    		fechaS = fecha.split("/");
		    		
		        	gc.drawString(""+fechaS[0], columna, fila);
		        	columna = 120;
		        	gc.drawString(monthToString(Integer.parseInt(fechaS[1])).toUpperCase(), columna, fila);
		        	columna = 310;
		        	gc.drawString(""+fechaS[2], columna, fila);
		        	
		        	fila += 11;
		        	columna = 65;
		        	
		        	//Datos cliente
		        	gc.drawString(cliente.getNombres().toUpperCase()+" "+cliente.getApellidos().toUpperCase(), columna, fila);
		        	
		        	fila += 11; 
		        	gc.drawString(cliente.getRut().toUpperCase(), columna, fila);
		        	
		        	columna = 350;
		        	gc.drawString(cliente.getGiro().toUpperCase(), columna, fila);
		        	
		        	columna = 810;
		        	gc.drawString(cliente.getCiudad().toUpperCase(), columna, fila);
		        	
		        	columna = 65;
		        	fila += 11;
		        	gc.drawString(cliente.getDireccion().toUpperCase(), columna, fila);
		        	
		        	columna = 810;
		        	gc.drawString(cliente.getComuna().toUpperCase(), columna, fila);

		        	//Detalle venta
		        	fila = 205;
		        	
		        	//Obtener numero de productos existentes en guias
		        	DAOGuiasImpl daoGuias = new DAOGuiasImpl();
		        	ArrayList<VOProducto> listaProductos = daoGuias.getAllProductosInGuiasByFactura(factura.getId());
		        	
		        	//Extraer guias segun producto y factura e imprimir segun producto
		        	ArrayList<VOGuia> listaGuias;
		        	VOItemProducto itemProducto;
		        	
		        	for(int x=0; x<listaProductos.size(); x++){
		        		listaGuias = new ArrayList<VOGuia>();
			        	listaGuias = daoGuias.getAllGuiasByFacturaProducto(factura.getId(),listaProductos.get(x).getId());
			        	
			        	BigDecimal cantidadProducto = new BigDecimal(0);
			        	int valorProducto = 0;
			        	BigDecimal totalFinalProducto = new BigDecimal(0);
			        	
			        	Iterator<VOGuia> guiasIt = listaGuias.iterator();
			        	while(guiasIt.hasNext()) {
			        		VOGuia guia = guiasIt.next();
			        		itemProducto = daoGuias.getItemProductoByProductoYGuia(listaProductos.get(x).getId(), guia.getId());
			        		
			        		valorProducto = itemProducto.getPrecio();
			        		cantidadProducto = itemProducto.getCantidad();
			        		totalFinalProducto = itemProducto.getTotal();
			        	}
			        	
			        	//Imprimir cantidad, detalle nombre y total vendido del producto x de todas las guias en cuestion (segun factura)
			        	
			        	columna = 10;
		        		gc.drawString(cantidadProducto.toString(), columna, fila);
		        		
		        		columna = 110;
		        		gc.drawString(""+listaProductos.get(x).getDescripcion().toUpperCase(), columna, fila);
		        		
		        		columna = 660;
		        		gc.drawString(""+valorProducto, columna, fila);
		        		
			        	columna = 810;
			        	gc.drawString(totalFinalProducto.toString(), columna, fila);
			        	
			        	fila += 10;
		        	}

		        	//Lista de guias que corresponden a la factura
		        	listaGuias = daoGuias.getAllGuiasByFactura(factura.getId());

		        	columna = 110;
		        	gc.drawString("SEGÚN GUÍAS: ", columna, fila);
		        	fila += 10;

		        	Iterator<VOGuia> guiasIt = listaGuias.iterator();
		        	while(guiasIt.hasNext()) {
		        		VOGuia guia = guiasIt.next();

		        		System.out.println("guia #"+guia.getNumero());
		        		
		        		if(columna >= 610){
		        			System.out.println("llego al tope!");
		        			columna = 110;
		        			fila += 10;
		        		}
		        		
		        		gc.drawString(""+guia.getNumero(), columna, fila);
		        		columna += 45;
		        	}
		        	
		        	//totales de venta (factura)
		        	fila = 393;
		        	columna = 810;
		        	
		        	gc.drawString(factura.getValorNeto().toString(), columna, fila);
		        	
		        	fila += 12;
		        	gc.drawString(factura.getValorIva().toString(), columna, fila);
		        	
		        	fila += 12;
		        	gc.drawString(factura.getValorTotal().toString(), columna, fila);
		        	
		          // Finishes the page. 
		          printer.endPage();
		        }
		        
		        gc.dispose();
		        
		        // Ends the job.
		        printer.endJob();
		      }
		      
		      // Disposes the printer object after use. 
		      printer.dispose();
		      
		      if(printer.isDisposed())
		    	  System.out.println("PRINT IS FREE");
		      
		      System.out.println("Print job done.");
		    }
	}
	
	public static void imprimirTest(Shell shell) {
		PrintDialog dialog = new PrintDialog(shell);
	    // Opens a dialog and let use user select the 
	    // target printer and configure various settings.
		boolean dialogPrint = true;
		
		//Impresion por seleccion de impresora
		if(dialogPrint){
		    PrinterData printerData = dialog.open();
		    if(printerData != null) { // If a printer is selected
		      // Creates a printer.
		      Printer printer = new Printer(printerData);
		      
		      // Starts the print job.
		      if(printer.startJob("guia despacho")) {
		        GC gc = new GC(printer);
		        Font fuente = new Font(printer, "Arial", 8, SWT.NONE);
		        gc.setFont(fuente);
	
		        // Starts a new page.
		        if(printer.startPage()) {
	
		        	gc.drawString("10-10", 10, 10);
		        	gc.drawString("10-100", 10, 100);
		        	gc.drawString("10-200", 10, 200);
		        	gc.drawString("10-300", 10, 300);
		        	gc.drawString("10-400", 10, 400);
		        	gc.drawString("10-500", 10, 500);
		        	
		        	gc.drawString("100-10", 100, 10);
		        	gc.drawString("100-100", 100, 100);
		        	gc.drawString("100-200", 100, 200);
		        	gc.drawString("100-300", 100, 300);
		        	gc.drawString("100-400", 100, 400);
		        	gc.drawString("100-500", 100, 500);
		        	
		        	gc.drawString("200-10", 200, 10);
		        	gc.drawString("200-100", 200, 100);
		        	gc.drawString("200-200", 200, 200);
		        	gc.drawString("200-300", 200, 300);
		        	gc.drawString("200-400", 200, 400);
		        	gc.drawString("200-500", 200, 500);
		        	
		        	gc.drawString("300-10", 300, 10);
		        	gc.drawString("300-100", 300, 100);
		        	gc.drawString("300-200", 300, 200);
		        	gc.drawString("300-300", 300, 300);
		        	gc.drawString("300-400", 300, 400);
		        	gc.drawString("300-500", 300, 500);
		        	
		        	gc.drawString("400-10", 400, 10);
		        	gc.drawString("400-100", 400, 100);
		        	gc.drawString("400-200", 400, 200);
		        	gc.drawString("400-300", 400, 300);
		        	gc.drawString("400-400", 400, 400);
		        	gc.drawString("400-500", 400, 500);
		        	
		        	gc.drawString("500-10", 500, 10);
		        	gc.drawString("500-100", 500, 100);
		        	gc.drawString("500-200", 500, 200);
		        	gc.drawString("500-300", 500, 300);
		        	gc.drawString("500-400", 500, 400);
		        	gc.drawString("500-500", 500, 500);
		        			        	
		        	// Finishes the page. 
		        	printer.endPage();
		        }

		        gc.dispose();
		        
		        // Ends the job.
		        printer.endJob();
		      }
		      
		      // Disposes the printer object after use. 
		      printer.dispose();
		      
		      System.out.println("Print job done.");
		    }
		}		
		
	}
	
	public static int doubleToInt(double valordecimal){
		int valorEntero;
		
		BigDecimal valorDecimal = new BigDecimal(valordecimal);
		valorEntero = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
		
		return valorEntero;
	}
	
	public static double redondeaDecimal(double valordecimal){
		double redondeado;
		
		BigDecimal valorDecimal = new BigDecimal(valordecimal);
		redondeado = valorDecimal.setScale(0,BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return redondeado;
	}
	
	public static void buildFoundTable(Table table, String filter) {
		table.clearAll();
		table.removeAll();
		
		ArrayList<VOCliente> listaClientes = new DAOClientesImpl().getAllClientes(true);
		for(int x=0; x < listaClientes.size(); x++){
			if(listaClientes.get(x).getRut().startsWith(filter)){
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(listaClientes.get(x).getRut() +" , "+ listaClientes.get(x).getNombres() +" "+ listaClientes.get(x).getApellidos());
			}
		}
	}
	
//	public static ArrayList<?> getPagedArray(ArrayList<?> arreg, int pagina, int filas) {
//		
//		ArrayList arregPaginado = new ArrayList();
//		
//		System.out.println("@DEBUG num registros: "+arreg.size());
//		
//		for(int x=pagina; x<filas; x++) {
//			if(!(arreg.get(x) == null))
//				arregPaginado.add(arreg.get(x));
//			else
//				System.out.println("nada!");
//		}
//		
//		return arregPaginado;
//	}
}
