package me.vurt.nms.core.data;


/**
 * 节点注册请求，初始化时默认包含了当前节点的信息
 * @author yanyl
 *
 */
public interface RegisterRequest extends Data{
	
	/**
	 * @return the id
	 */
	public String getId() ;

	/**
	 * @return the group
	 */
	public String getGroup();
}
