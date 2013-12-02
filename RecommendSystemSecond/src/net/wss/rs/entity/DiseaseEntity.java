package net.wss.rs.entity;

public class DiseaseEntity {
private int id;
private String name;
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
public DiseaseEntity(int id, String name) {
	super();
	this.id = id;
	this.name = name;
}
public DiseaseEntity() {
	super();
}

}
