package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import boleto.controller.BoletoListarRequest;
import boleto.controller.BoletoListarResponse;
import boleto.controller.BoletoRequest;
import boleto.model.Boleto;
import controller.BoletoWs;

@ManagedBean
@ViewScoped
public class BoletoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dataDe;
	private String dataAte;
	private Integer codTitulo;
	private String codIntegracaoTitulo;
	private String nossoNumero;
	private String numero;
	private List<BoletoData> boletos;
	private List<BoletoData> boletosSelected;
	private Integer currentPage;
	private Integer totalPages;

	public BoletoBean() {
		super();
		// TODO Auto-generated constructor stub

		this.setBoletos(new ArrayList<BoletoData>());
		this.setBoletosSelected(new ArrayList<BoletoData>());
		this.setCurrentPage(1);
		this.setTotalPages(1);
	}

	public BoletoBean(String dataDe, String dataAte, Integer codTitulo, String codIntegracaoTitulo, String nossoNumero,
			String numero, List<BoletoData> boletos, List<BoletoData> boletosSelected, Integer currentPage,
			Integer totalPages) {
		super();
		this.dataDe = dataDe;
		this.dataAte = dataAte;
		this.codTitulo = codTitulo;
		this.codIntegracaoTitulo = codIntegracaoTitulo;
		this.nossoNumero = nossoNumero;
		this.numero = numero;
		this.boletos = boletos;
		this.boletosSelected = boletosSelected;
		this.currentPage = currentPage;
		this.totalPages = totalPages;
	}

	public String getDataDe() {
		return dataDe;
	}

	public void setDataDe(String dataDe) {
		this.dataDe = dataDe;
	}

	public String getDataAte() {
		return dataAte;
	}

	public void setDataAte(String dataAte) {
		this.dataAte = dataAte;
	}

	public Integer getCodTitulo() {
		return codTitulo;
	}

	public void setCodTitulo(Integer codTitulo) {
		this.codTitulo = codTitulo;
	}

	public String getCodIntegracaoTitulo() {
		return codIntegracaoTitulo;
	}

	public void setCodIntegracaoTitulo(String codIntegracaoTitulo) {
		this.codIntegracaoTitulo = codIntegracaoTitulo;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<BoletoData> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<BoletoData> boletos) {
		this.boletos = boletos;
	}

	public List<BoletoData> getBoletosSelected() {
		return boletosSelected;
	}

	public void setBoletosSelected(List<BoletoData> boletosSelected) {
		this.boletosSelected = boletosSelected;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void doSearch(Integer pagina, Boolean today) {
		try {
			String propPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
			FileInputStream fileInputStream = new FileInputStream(propPath + "/parameters/boletojson.properties");
			Properties properties = new Properties();
			properties.load(fileInputStream);

			BoletoRequest boletoRequest = new BoletoRequest();
			boletoRequest.setAppKey(properties.getProperty("boleto.appkey"));
			boletoRequest.setAppSecret(properties.getProperty("boleto.appsecret"));
			boletoRequest.setCall("Listar");

			BoletoListarRequest boletoListarRequest = new BoletoListarRequest();
			boletoListarRequest.setPagina(pagina);
			// boletoListarRequest.setRegistrosPagina(20);

			if (today == true) {
				boletoListarRequest.setDataDe(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now()));
				boletoListarRequest.setDataAte(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now()));

			} else {
				boletoListarRequest.setDataDe(this.getDataDe());
				boletoListarRequest.setDataAte(this.getDataAte());
				boletoListarRequest.setCodTitulo(this.getCodTitulo());
				boletoListarRequest.setCodIntegracaoTitulo(this.getCodIntegracaoTitulo());
				boletoListarRequest.setNossoNumero(this.getNossoNumero());
				boletoListarRequest.setNumero(this.getNumero());

			}

			boletoRequest.getBoletoListarRequests().add(boletoListarRequest);

			BoletoWs boletoWs = new BoletoWs();

			this.setBoletos(new ArrayList<BoletoData>());
			List<BoletoListarResponse> boletoListarResponses = boletoWs.searchBoletos(boletoRequest);
			for (BoletoListarResponse boletoListarResponse : boletoListarResponses) {
				for (Boleto boleto : boletoListarResponse.getBoletos()) {
					BoletoData boletoData = new BoletoData();
					boletoData.setBoleto(boleto);

					this.getBoletos().add(boletoData);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void search() {
		this.doSearch(1, false);
		this.setBoletosSelected(new ArrayList<BoletoData>());
	}

	public void today() {
		this.doSearch(1, true);
		this.setBoletosSelected(new ArrayList<BoletoData>());
	}

	public void nextPage() {
		/*
		 * if (this.getCurrentPage() == this.getTotalPages()) return;
		 * 
		 * this.doSearch(this.getCurrentPage() + 1, false); this.updateSelected();
		 */

	}

	public void previousPage() {
		/*
		 * if (this.getCurrentPage() == 1) return;
		 * 
		 * if (this.getCurrentPage() == this.getTotalPages()) return;
		 * 
		 * this.doSearch(this.getCurrentPage() - 1, false); this.updateSelected();
		 */

	}

	public void updateSelected() {
		for (BoletoData boletoData : this.getBoletos()) {
			if (boletoData.getSelected() == true && !this.getBoletosSelected().contains(boletoData)) {
				this.getBoletosSelected().add(boletoData);

			}

		}

		for (BoletoData boletoData : this.getBoletosSelected()) {
			if (boletoData.getSelected() == false && this.getBoletosSelected().contains(boletoData)) {
				this.getBoletosSelected().remove(this.getBoletosSelected().indexOf(boletoData));

			}

		}

	}

}