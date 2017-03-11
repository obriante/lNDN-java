/**
 * 
 */
package it.unirc.briante.orazio.ndn;


/**
 * @author kmonkey
 *
 */
public class URI {

	public enum Type{
		ANSWER,
		REQUEST,
		CLEAN
	}

	public final static String NDN_DELIMITER=":";
	public final static String NDN_PATH_DELIMITER="/";

	public final static String NDN_REQ="r:";
	public final static String NDN_ANS="a:";

	public final static String NDN_CLOSE_DELIMITER="//";

	public final static String NDN_JOLLY_OBJ="*";
	public final static String NDN_NOT_WELL_FORMED=" [NOT_WELL-FORMED]";


	private String object;
	private String path;
	private String value;
	private Type type;

	/**
	 * 
	 */
	public URI() {
		this.object = new String();
		this.path = new String();
		this.value = new String();
		this.type = Type.CLEAN;
	}


	/**
	 * @param object
	 * @param path
	 * @param value
	 */
	public URI(String object, String path) {
		this.object = object;
		this.path = path;
		this.value = new String();
		this.type = Type.CLEAN;
	}


	/**
	 * @param object
	 * @param path
	 * @param value
	 */
	public URI(String object, String path, Type type) {
		this.object = object;
		this.path = path;
		this.value = new String();
		this.type = type;
	}


	/**
	 * @param object
	 * @param path
	 * @param value
	 */
	public URI(String object, String path, String value) {
		this.object = object;
		this.path = path;
		this.value = value;
		this.type = Type.CLEAN;
	}


	/**
	 * @param object
	 * @param path
	 * @param value
	 */
	public URI(String object, String path, String value, Type type) {
		this.object = object;
		this.path = path;
		this.value = value;
		this.type = type;
	}

	public URI(String uri){
		this();

		if(uri.endsWith(NDN_CLOSE_DELIMITER) &&
		  (uri.startsWith(NDN_REQ)||uri.startsWith(NDN_ANS)||uri.startsWith(NDN_PATH_DELIMITER))){

			String cleanURI=new String(uri);
			
			if(cleanURI.startsWith(NDN_REQ)){
				this.type=Type.REQUEST;
				cleanURI=cleanURI.substring(cleanURI.indexOf(NDN_REQ)+NDN_REQ.length(), cleanURI.indexOf(NDN_CLOSE_DELIMITER));
			}else if(uri.startsWith(NDN_ANS)){
				this.type=Type.ANSWER;
				cleanURI=cleanURI.substring(cleanURI.indexOf(NDN_ANS)+NDN_ANS.length(), cleanURI.indexOf(NDN_CLOSE_DELIMITER));
			}else{
				this.type=Type.CLEAN;
			}
			cleanURI=cleanURI.substring(NDN_PATH_DELIMITER.length());

			this.object =this.object+ cleanURI.substring(0, cleanURI.indexOf(NDN_PATH_DELIMITER));

			if(cleanURI.indexOf(NDN_DELIMITER)>=0){
				this.path = this.path+cleanURI.substring(cleanURI.indexOf(NDN_PATH_DELIMITER), cleanURI.indexOf(NDN_DELIMITER));
				this.value = this.value+cleanURI.substring(cleanURI.indexOf(NDN_DELIMITER)+NDN_DELIMITER.length());
			}else{
				this.path = this.path+cleanURI.substring(cleanURI.indexOf(NDN_PATH_DELIMITER));
			}
		}else{
			this.object=this.object+NDN_NOT_WELL_FORMED;
		}
	}

	/**
	 * @return the object
	 */
	public String getObject() {
		return object;
	}

	/**
	 * @param object the object to set
	 */
	public void setObject(String object) {
		this.object = object;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "URI [object=" + object + ", path=" + path + ", value=" + value
				+ ", type=" + type + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		URI other = (URI) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (type != other.type)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	/**
	 * @return A URI String
	 */
	public String getURI() {

		String s=new String(NDN_PATH_DELIMITER);

		if(object!=null && object.length()>0)
			s=s+object;

		if(path!=null &&  path.length()>0)
			s=s+path;

		if(value!=null && value.length()>0)
			s=s+NDN_DELIMITER + value;

		if(this.type==Type.REQUEST){
			return NDN_REQ + s + NDN_CLOSE_DELIMITER;
		}else if(this.type==Type.ANSWER){
			return NDN_ANS + s + NDN_CLOSE_DELIMITER;
		}

		return s;
	}
}
