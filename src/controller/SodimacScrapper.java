package controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.SodimacElement;

public class SodimacScrapper {	

	private ArrayList<SodimacElement> Elementos = new ArrayList<SodimacElement>();
	private String url = "https://www.sodimac.cl/sodimac-cl/category/scat933729/Taladros";
	private String etiquetaElemento = "section.col-md-3.col-xs-12.col-sm-12.item.jq-item.one-prod";
	
	private String etiquetaMarca = "p.brand.jq-brand > a";
	private String etiquetaTitulo = "[itemprop='name']";
	private String etiquetaTituloInconcluso = "[itemprop='url']";
	
	private String etiquetaPrecioActual = "[itemprop='price']";
	private String etiquetaPrecioNormal = "p.normal-price.PLP-NORMAL-2";
	
	private String etiquetaUrlImagen = "img.lazy.hoverImg";
	private String etiquetaUrlProducto= "a.img.containerImg";
	
	private Document pagina;	

	public SodimacScrapper() throws IOException, URISyntaxException {
		pagina = Jsoup.connect(url).userAgent("USER_AGENT").get();
		do {
			this.scrapperElement(pagina);
		}while (this.nextPage());
		
		//System.out.println("\n\n\nSALÍ DEL WHILE\n\n");
	}
	
	private void scrapperElement(Document page) throws URISyntaxException {
		int precio_normal, precio_actual;
		
		Elements elementos	= page.select(etiquetaElemento);
		Element Marca, Titulo, PrecioActual, PrecioNormal, urlImagen, urlProducto;
		
		for (Element e:elementos) {	
			Marca		= e.selectFirst(etiquetaMarca);
			Titulo		= e.selectFirst(etiquetaTitulo);
			PrecioActual= e.selectFirst(etiquetaPrecioActual);
			PrecioNormal= e.selectFirst(etiquetaPrecioNormal);
			urlImagen 	= e.selectFirst(etiquetaUrlImagen);
			urlProducto = e.selectFirst(etiquetaUrlProducto);
			
			//Titulo del producto
			if (Titulo==null || Titulo.text()=="" || Titulo.text().isEmpty())
				Titulo	= e.selectFirst(etiquetaTituloInconcluso);						

			//Precio actual del producto
			precio_actual = Integer.parseInt(regxSoloEnteros(PrecioActual.text()));

			//Precio normal del producto
			if (PrecioNormal==null || PrecioNormal.text()=="" || PrecioNormal.text().isEmpty())
				precio_normal = precio_actual;
			else precio_normal = Integer.parseInt(regxSoloEnteros(PrecioNormal.text()));
			
			//Genero una copia del producto con sus atributos
			SodimacElement producto = new SodimacElement(
					Marca.text(),
					Titulo.text(), 
					precio_actual,
					precio_normal,
					urlImagen.absUrl("src"),
					urlProducto.absUrl("href")				
					);
			
			//Guardo elemento en lista
			Elementos.add(producto);
			producto.printData();
		}
	}
	
	private boolean nextPage() throws IOException {
		Element sgtePag = pagina.selectFirst("a.next");
		
		if (sgtePag==null || sgtePag.absUrl("href")==""|| sgtePag.absUrl("href").isEmpty())
			return false;		
		
		pagina = Jsoup.connect(sgtePag.absUrl("href")).userAgent("USER_AGENT").get();		
		return true;
	}
	
	private void printElements() {
		for (SodimacElement e: Elementos) {
			e.printData();
		}
	}
	
	private String regxSoloEnteros (String s) {	
		Pattern patron = Pattern.compile("[^0-9]");
		Matcher encajar = patron.matcher(s);		
		return encajar.replaceAll("");
	}
}