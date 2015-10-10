package com.example.birdnest.database;

import java.io.Serializable;


public class User implements Serializable
{
	public enum USER_TYPE {SUPER_RUSER, DEPARTMENT_CHIEF ,NORMAL};
	
	//public static final String ID = "ID";
	public static final String USERID = "ID";
//	public static final String TOKEN = "token";
	public static final String NAME = "name";
	public static final String DEPARTMENT = "department";
//	public static final String USERNAME = "userName";
	//public static final String USERICON = "userIcon";
 //	public static final String URL = "imageUrl";
	public static final String QQ = "qq";
	public static final String LIMITION =  "limitation";
	public static final String GENDER = "gender";
	public static final String HOMETOWN = "hometown";
	public static final String SCHOOL = "school";	
	public static final String TEL = "tel";	
	public static final String BIRTHDAY = "birthday";
	public static final String SPECIALITY = "speciality";
	public static final String HOBBY = "hobby";
	public static final String USER_TYPE = "user_type";
	public static final String REMARKS = "remarks";
	public static final String GRADE  = "grade";
	private String ID;
	private String name ;
	private String department ;
	private String gender;  
	private String hometown ;
	private String school;
	private String tel ;
	private String birthday ;
	private String specality ;
	private String hobby ;
	private String limit;
	
	private USER_TYPE userType; 
	
	public User()
	{
		
	}
	public User(String id,String name,String department,
			String gender,String hometown,String school ,
			String tel,String birthday, String specality,String hobby,USER_TYPE usertype,String lmt )
	{
		this.ID = id;
		this.name = name ;
		this.department = department ;
		this.gender = gender;
		this.hometown = hometown ;
		this.school = school ;
		this.tel = tel ;
		this.birthday = birthday ;
		this.hobby = hobby; 
		this.userType = usertype ;
		this.specality = specality ;
	}
	
	
	public void setLimit(String lmt)
	{
		this.limit = lmt ;
	}
	public String getLimit()
	{
		return this.limit ;
	}
	
	public void setSchool(String school)
	{
		this.school =  school ;
	}
	public void  setTel(String tel)
	{
		this.tel = tel ;
	}
	public void  setID(String id)
	{
		 this.ID = id ;
	}
	public void setName(String nm)
	{
		this.name = nm;
	}
	public void  setDepartment(String dpt)
	{
		 this.department  = dpt; 
	}
	public void setGender(String g)
	{
		  this.gender = g;
	}
	public void   setHometown(String h)
	{
		 this.hometown = h ;
	}
	public void setSpecality(String s)
	{
		this.specality =  s ;
	} 
	public void   setBirthday(String b)
	{
		 this.birthday = b; 
	}
	public void setHobby(String h)
	{
		this.hobby = h;
	}
	public void setUserType(USER_TYPE t)
	{
		  this.userType = t;
	}
	
/*	public void setUserType(String t)
	{
		  if(t.equals(SUPER))
		  {
			  
		  }
	}*/
	
	/////////////////////
	public String  getSchool()
	{
		return school ;
	}
	
	public String getTel()
	{
		return tel ;
	}
	public String  getID()
	{
		return ID;
	}
	public String  getName()
	{
		return name;
	}
	public String  getDepartment()
	{
		return department;
	}
	public String  getGender()
	{
		return gender;
	}
	public String  getHometown()
	{
		return hometown;
	}
	public String  getSpecality()
	{
		return specality;
	}
	public String  getBirthday()
	{
		return birthday;
	}
	public String  getHobby()
	{
		return hobby;
	}
	public USER_TYPE getUserType()
	{
		return userType;
	}
}
