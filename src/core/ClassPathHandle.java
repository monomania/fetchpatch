package core;

import core.cache.ConfCache;

public enum ClassPathHandle {
	HOST_OF_APPS_WEBCONTENT("apps\\webcontent") {
		@Override
		public String handle(String sourcePath) {
			String temp = sourcePath.toLowerCase();
			if(temp.contains((host+value).toLowerCase())){
				int index  = temp.indexOf(value);
				sourcePath = sourcePath.substring(index+value.length());
				return sourcePath;
			}
			return null;
		}
	},
	HOST_OF_APPS_SRC("src") {
		@Override
		public String handle(String sourcePath) {
			return ClassPathHandle.HOST_OF_APPS_SRC.class_common_handle(sourcePath);
		}
		
	},
	HOST_OF_APPS_PRODUCTCODE("productcode") {
		@Override
		public String handle(String sourcePath) {
			return ClassPathHandle.HOST_OF_APPS_PRODUCTCODE.class_common_handle(sourcePath);
		}
		
	},
	DB("db") {
		@Override
		public String handle(String sourcePath) {
			return null;
		}
	},
	WEBCONTENT_APP("app") {

		@Override
		public String handle(String sourcePath) {
			String temp = sourcePath.toLowerCase();
			int index = 0;
			String flag = "\\"+value+"\\";
			if(0 < (index = temp.indexOf(flag))){
				sourcePath = "\\apps\\"+ sourcePath.substring(index+flag.length());
				return sourcePath;
			}
			return null;
		}
		
	},
	SRC("src") {
		@Override
		public String handle(String sourcePath) {
			return ClassPathHandle.SRC.class_common_handle(sourcePath);
		}
		
	},
	PRODUCTCODE("productcode") {
		@Override
		public String handle(String sourcePath) {
			return ClassPathHandle.PRODUCTCODE.class_common_handle(sourcePath);
		}
	};
	
	private String class_common_handle(String sourcePath){
		String temp = sourcePath.toLowerCase();
		int index = 0;
		if(0 < (index = temp.indexOf(this.value))){
			sourcePath =  ConfCache.get("classPathFlag")
					+ sourcePath.substring(index+value.length());
			return sourcePath;
		}
		return null;
	}

	private ClassPathHandle(String value) {
		this.value = value;
	}

	private ClassPathHandle(String host, String value) {
		this.host = host;
		this.value = value;
	}

	public abstract String handle(String sourcePath);

	/**
	 * HOST的值为: 如-E:\workspace\SZ_YD\EAS_CJ000_ShareLib
	 */
	public String	host;

	public String	value;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public static void main(String[] args) {}

}
