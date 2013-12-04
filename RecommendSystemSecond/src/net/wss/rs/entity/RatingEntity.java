package net.wss.rs.entity;

public class RatingEntity {

	private int doctorId;
	private int diseaseId;
	private int rating;//’Ô¥Œ
	
	public RatingEntity() {
		super();
	}
	public RatingEntity(int doctorId, int diseaseId, int rating) {
		super();
		this.doctorId = doctorId;
		this.diseaseId = diseaseId;
		this.rating = rating;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public int getDiseaseId() {
		return diseaseId;
	}
	public void setDiseaseId(int diseaseId) {
		this.diseaseId = diseaseId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
}
