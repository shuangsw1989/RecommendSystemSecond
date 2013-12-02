package net.wss.rs.entity;

public class RatingEntity {

	private int userId;
	private int itemId;
	private int rating;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public RatingEntity(int userId, int itemId, int rating) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.rating = rating;
	}
	public RatingEntity() {
		super();
	} 
	
}
