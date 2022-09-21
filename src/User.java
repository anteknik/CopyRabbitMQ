import java.io.Serializable;

public class User {


	private long id;
	private String nama;
	private String alamat;

	public User(){}

	public User(long id, String nama,String alamat){

	this.id=id;
	this.nama=nama;
	this.alamat=alamat;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	//public String toString(){//overriding the toString() method
  	//return "User is"+nama+" "+alamat+" "+id;  
	//}	

}