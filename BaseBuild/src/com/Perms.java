package com;

public enum Perms {
	
	Build("build", "Permission to build a base"),
	BuildAnywhere("build.anywhere", "Permission to build a base anywhere");
	
	
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
