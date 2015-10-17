/**
 * 
 */
package org.novadeck.jxla.data;

import java.io.Serializable;

/**
 * @author nioto
 *
 */
public class Pair<T> implements Serializable {

	private static final long serialVersionUID = -1186676757747218814L;

	
	private T left;
	private T right;
	
	/**
	 * 
	 */
	public Pair(T left, T right) {
		this.left = left;
		this.right = right;
	}

	public T getLeft() {
		return left;
	}

	public T getRight() {
		return right;
	}
}