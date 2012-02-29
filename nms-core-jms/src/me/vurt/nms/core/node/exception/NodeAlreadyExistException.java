package me.vurt.nms.core.node.exception;

/**
 * @author yanyl
 *
 */
public class NodeAlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9037816735812506289L;
	
	public NodeAlreadyExistException(String id){
		super("id为\""+id+"\"的节点已存在");
	}

}
