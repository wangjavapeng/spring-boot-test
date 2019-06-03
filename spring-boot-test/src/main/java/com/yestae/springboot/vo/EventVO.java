package com.yestae.springboot.vo;

import java.io.Serializable;
import java.util.List;

public class EventVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5332858575570844910L;
	
	private List<String> uids;

	public List<String> getUids() {
		return uids;
	}

	public void setUids(List<String> uids) {
		this.uids = uids;
	}

	@Override
	public String toString() {
		return "EventVO [uids=" + uids + "]";
	}
	
}
