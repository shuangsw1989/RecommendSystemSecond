package net.wss.rs.entity;

public class DoctorEntity {
private int id;
private String name;
private String cureDisease;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getCureDisease() {
	return cureDisease;
}
public void setCureDisease(String cureDisease) {
	this.cureDisease = cureDisease;
}
public DoctorEntity(int id, String name, String cureDisease) {
	super();
	this.id = id;
	this.name = name;
	this.cureDisease = cureDisease;
}
public DoctorEntity() {
	super();
}

}
