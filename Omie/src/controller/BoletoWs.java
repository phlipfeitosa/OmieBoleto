package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import boleto.controller.BoletoClient;
import boleto.controller.BoletoListarResponse;
import boleto.controller.BoletoRequest;

public class BoletoWs implements Serializable {
	private static final long serialVersionUID = 1L;

	public BoletoWs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<BoletoListarResponse> searchBoletos(BoletoRequest boletoRequest) {
		BoletoClient boletoClient = new BoletoClient();
		List<BoletoListarResponse> boletoListarResponses = new ArrayList<BoletoListarResponse>();
		BoletoListarResponse boletoListarResponse = boletoClient.execute(boletoRequest);
		boletoListarResponses.add(boletoListarResponse);

		Integer pagina = boletoListarResponse.getPagina();
		System.out.println("Página " + pagina + "\nTotal " + boletoListarResponse.getTotalPaginas());
		while (pagina < boletoListarResponse.getTotalPaginas()) {
			pagina++;
			boletoRequest.getBoletoListarRequests().get(0).setPagina(pagina);

			boletoListarResponses.add(boletoClient.execute(boletoRequest));
			System.out.println("Página " + pagina + "\nTotal " + boletoListarResponse.getTotalPaginas());

		}

		return boletoListarResponses;

	}

}