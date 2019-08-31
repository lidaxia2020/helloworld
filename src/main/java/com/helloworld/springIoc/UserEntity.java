
package com.helloworld.springIoc;


public class UserEntity {
	private String userId;
	private String userName;
	public UserEntity(){
		System.out.println("使用反射技术 ，执行无参数构造 函数");
	}
	public UserEntity(String userId) {
	  System.out.println("使用反射技术 执行 有参构造函数 userId:"+userId);
	}

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

}
