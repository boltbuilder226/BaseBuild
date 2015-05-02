package com;

public enum Perms {
	
	Default("default", "Command description");
	
	
	private String perm;
	private String desc;
	private String prefix = "basebuild";
	
	Perms(String perm, String desc){
		this.perm = perm;
		this.desc = desc;
		
		
	}

	public String getPerm() {
		return prefix + "." + perm;
	}

	public String getDesc(){
		
		return desc;
				
	}
	

}
