package net.wss.rs.entity;

public class ZhengRatingEntity {
	private int doctorId;
	private int ZhengId;
	private int rating;//’Ô¥Œ
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public int getZhengId() {
		return ZhengId;
	}
	public void setZhengId(int zhengId) {
		ZhengId = zhengId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public ZhengRatingEntity(int doctorId, int zhengId, int rating) {
		super();
		this.doctorId = doctorId;
		ZhengId = zhengId;
		this.rating = rating;
	}
	public ZhengRatingEntity() {
		super();
	}
	
	
	
}
