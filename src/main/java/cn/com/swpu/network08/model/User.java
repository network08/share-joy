package cn.com.swpu.network08.model;
/**
 * @author xkk
 *
 */
public class User {
	private String id;
	private String name;
	private String phone;
	private String email;
	
	public User() {
	}
	
	public User(String name, String phone, String email) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "User [" + (id != null ? "id=" + id + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (phone != null ? "phone=" + phone + ", " : "")
				+ (email != null ? "email=" + email : "") + "]";
	}
	
}
