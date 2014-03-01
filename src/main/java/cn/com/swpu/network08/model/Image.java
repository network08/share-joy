package cn.com.swpu.network08.model;

/**
 * @author xkk
 *
 */
public class Image {
	private String id;
	private String name;
	private byte[] image;
	
	public Image(){
	}
	public Image(String name, byte[] image) {
		super();
		this.name = name;
		this.image = image;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
