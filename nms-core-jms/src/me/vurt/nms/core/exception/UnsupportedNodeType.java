package me.vurt.nms.core.exception;

/**
 * 不支持的节点类型
 * @author yanyl
 *
 */
public class UnsupportedNodeType extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381226556551558057L;
	
	public UnsupportedNodeType(String msg) {
		super(msg);
	}

}
