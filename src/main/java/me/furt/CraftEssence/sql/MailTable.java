package me.furt.CraftEssence.sql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name="ce_mail")
public class MailTable {
	@Id
    private int id;
	
	@NotEmpty
	private String sender;
	
	@NotEmpty
	private String reciever;
	
	@NotNull
	private String message;
	
	public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getReciever() {
		return reciever;
	}
	
	public void setReciever(String reciever) {
		this.reciever = reciever;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
