package com.vodafone.sportyShoes.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Purchase {
	public Purchase() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserId", referencedColumnName = "id")
	private User user;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ProductId", referencedColumnName = "id")
	private Product product;
	@Column(name="purchaseTimestamp", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private java.sql.Timestamp purchaseTimestamp;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public java.sql.Timestamp getPurchaseTimestamp() {
		return purchaseTimestamp;
	}
	public void setPurchaseTimestamp(java.sql.Timestamp purchaseTimestamp) {
		this.purchaseTimestamp = purchaseTimestamp;
	}
	
}
