package com.checkspeed;

public class UserDetails {
	public String User_Name;
	public String User_Contact;
	public int User_ID;

	public UserDetails(){
		
	}
	
	public UserDetails(int id, String name, String _phone_number) {
		this.User_ID = id;
		this.User_Name = name;
		this.User_Contact = _phone_number;
		

	    }
	
	public UserDetails( String name, String _phone_number) {
		
		this.User_Name = name;
		this.User_Contact = _phone_number;
		

	    }
	
	public String getUserName() {
		return this.User_Name;
	}

	public void setUserName(String name) {
		this.User_Name = name;
	}

	public String getUser_Contact() {
		return this.User_Contact;
	}

	public void setUser_Contact(String name) {
		this.User_Contact = name;
	}

	public int  getUser_ID() {
		return this.User_ID;
	}

	public void setUser_ID(int name) {
		this.User_ID = name;
	}

}
