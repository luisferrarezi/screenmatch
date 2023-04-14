package br.com.screenmatch;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrincipalBusca {

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner leitura = new Scanner(System.in);		
		String busca = "";
		List<Titulo> listTitulos = new LinkedList<>();
		
		Gson gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setPrettyPrinting()
				.create();

		while (!busca.equalsIgnoreCase("sair")) {
			System.out.println("Digite o nome do filme: ");			
			busca = leitura.nextLine();			
			
			if (busca.equalsIgnoreCase("sair")) {
				break;
			}

			final String endereco = "http://www.omdbapi.com/?t=" + busca + "&apikey=be085767";
			try {
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();
				HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
				final String json = response.body();				

				TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
				Titulo meuTitulo = new Titulo(meuTituloOmdb);
				System.out.println(meuTitulo.toPrint());

				listTitulos.add(meuTitulo);
			} catch (ErroConversaoAnoException e) {
				System.out.println(e.getMessage());
			} catch (IllegalArgumentException e) {
				System.out.println("Endereço inválido: " + e.getMessage());
			}
		}
		
		leitura.close();		
	
		FileWriter escrita = new FileWriter("filmes.json");
		escrita.write(gson.toJson(listTitulos));
		escrita.close();		
		
		System.out.println("Busca concluída");		
	}
}
