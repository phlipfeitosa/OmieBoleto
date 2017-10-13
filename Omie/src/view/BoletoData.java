package view;

import java.io.Serializable;

import boleto.model.Boleto;

public class BoletoData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean selected;
	private Boleto boleto;

	public BoletoData() {
		super();
		// TODO Auto-generated constructor stub
		this.setSelected(false);
	}

	public BoletoData(Boolean selected, Boleto boleto) {
		super();
		this.selected = selected;
		this.boleto = boleto;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}