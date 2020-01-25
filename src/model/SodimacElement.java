package model;

import java.awt.Image;
import java.net.URI;
import java.net.URISyntaxException;

public class SodimacElement {

	private String title;
	private String marca;
	private int priceActual;
	private int priceNormal;
	private float descuento;
	
	private URI urlImage;
	private Image image;
	private URI urlProduct;
	
	public SodimacElement(String Marca, String titulo, int precioActual, int precioNormal, String url_img, String url_prod) throws URISyntaxException {
		marca = Marca;
		title = titulo;
		priceActual = precioActual;
		priceNormal = precioNormal;
		descuento = getDescuento(precioActual,precioNormal);
		urlImage = new URI (url_img);
		urlProduct = new URI (url_prod);
	}
	
	public SodimacElement(String titulo, int precio) {
		title = titulo;
		priceActual = precio;
		priceNormal = precio;
		descuento = getDescuento(priceNormal,priceActual);
	}

	public void printData() {
		System.out.println(this.getMarca());
		System.out.println(this.getTitle());
		System.out.println(this.getPriceActual());
		System.out.println(this.getPriceNormal());
		System.out.println(this.getDescuento());
		System.out.println(this.getUrlImage());
		System.out.println(this.getUrlProduct());
		System.out.println("\n\n");
	}
	
	public float getDescuento(float precio_actual, float precio_normal){
		return 100*(1-(precio_actual/precio_normal));
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getPriceActual() {
		return priceActual;
	}
	public void setPriceActual(int priceActual) {
		this.priceActual = priceActual;
	}

	public int getPriceNormal() {
		return priceNormal;
	}
	public void setPriceNormal(int priceNormal) {
		this.priceNormal = priceNormal;
	}

	public float getDescuento() {
		return descuento;
	}
	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public URI getUrlImage() {
		return urlImage;
}
	public void setUrlImage(URI urlImage) {
		this.urlImage = urlImage;
	}

	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}

	public URI getUrlProduct() {
		return urlProduct;
	}
	public void setUrlProduct(URI urlProduct) {
		this.urlProduct = urlProduct;
	}

}