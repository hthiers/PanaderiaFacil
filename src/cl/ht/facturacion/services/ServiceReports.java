package cl.ht.facturacion.services;

import java.util.ArrayList;

public interface ServiceReports {

	public ArrayList<String> getGuiasReportByDate(String mes);
	public ArrayList<String> getFacturasReportByDate(String mes);
}
