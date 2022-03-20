package com.red.security.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class CodeConfirm {
	
	public CodeConfirm() {}
	
	public CodeConfirm(String code, User user) {
		this.code = code;
		this.user = user;
		this.expiryDate = this.calculateExpiryDate(EXPIRATION);
		this.enable = true;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public boolean getEnable() {
		return enable;
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

    public void updateCode(final String code) {
        this.code = code;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
	
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        
    	Calendar cal = Calendar.getInstance();
        
    	cal.setTime(new Timestamp(cal.getTime().getTime()));
        
    	cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        
    	return new Date(cal.getTime().getTime());
    }
    
    
	
    private static final int EXPIRATION = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String code;
  
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userId")
    private User user;
    
    private Date expiryDate;
    
    private boolean enable;

}
