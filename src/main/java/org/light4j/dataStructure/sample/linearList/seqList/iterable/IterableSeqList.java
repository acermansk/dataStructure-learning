package org.light4j.dataStructure.sample.linearList.seqList.iterable;

import java.util.Iterator;

import org.light4j.dataStructure.sample.linearList.AbstractLList;
import org.light4j.dataStructure.sample.linearList.LList;

/**
 * 提供了迭代对象的线性表
 * 
 * @author longjiazuo
 */
public class IterableSeqList<E> extends AbstractLList<E> implements LList<E> { // 顺序表类，实现线性表接口
	private Object[] table; // 对象数组，私有成员
	private int n; // 顺序表长度

	/**
	 * 指定空表的默认容量
	 */
	public IterableSeqList() {
		this(16);
	}

	/**
	 * 构造方法，创建指定容量的空表 Math.abs(i)返回参数的绝对值
	 */
	public IterableSeqList(int capacity) {
		this.table = new Object[Math.abs(capacity)];
		this.n = 0;
	}

	/**
	 * 判断顺序表是否为空,若空返回true
	 */
	@Override
	public boolean isEmpty() {
		return this.n == 0;
	}

	/**
	 * 返回顺序表长度
	 */
	@Override
	public int length() {
		return this.n;
	}

	/**
	 * 返回index(初始值為0)位置的对象，若序号无效，返回null
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E get(int index) {
		if (index >= 0 && index < this.n) {
			return (E) this.table[index];
		}
		return null;
	}

	/**
	 * 设置index位置的对象为element，若操作成功，返回原对象，否则返回null
	 */
	@Override
	public E set(int index, E element) {
		if (index >= 0 && index < this.n && element != null) {
			@SuppressWarnings("unchecked")
			E old = (E) this.table[index];
			this.table[index] = element;
			return old;
		}
		return null;
	}

	/**
	 * 在index位置插入element对象，若操作成功返回true，不能插入null
	 */
	@Override
	public boolean add(int index, E element) {
		if (element == null) {
			return false; // 不能插入null
		}
		if (this.n == this.table.length) { // 若数组满，则需要扩充顺序表的容量
			Object[] temp = this.table;
			this.table = new Object[temp.length * 2]; // 重新申请一个容量更大的数组
			for (int i = 0; i < temp.length; i++) {
				this.table[i] = temp[i]; // 复制数组元素,O(n)
			}
		}

		if (index < 0) {
			index = 0; // 下标容错
		}
		if (index > this.n) {
			index = this.n; // 下标容错
		}

		for (int j = this.n - 1; j >= index; j--) { // 元素后移，平均移动n/2
			this.table[j + 1] = this.table[j];
		}
		this.table[index] = element;
		this.n++;
		return true;
	}

	/**
	 * 在顺序表最好插入element对象
	 */
	@Override
	public boolean add(E element) {
		return this.add(this.n, element);
	}

	/**
	 * 移去index位置的对象，若操作成功，则返回被移去对象，否则返回null
	 */
	@Override
	public E remove(int index) {
		if (this.n != 0 && index >= 0 && index < this.n) {
			@SuppressWarnings("unchecked")
			E old = (E) this.table[index];
			for (int i = index; i < this.n - 1; i++) { // 元素前移，平均移动n/2
				this.table[i] = this.table[i + 1];
			}
			this.table[this.n - 1] = null;
			this.n--;
			return old; // 若操作成功，则返回被移除对象
		}
		return null; // 未找到删除对象，操作不成功，返回null
	}

	/**
	 * 清空顺序表
	 */
	@Override
	public void clear() {
		if (this.n != 0) {
			for (int i = 0; i < this.n; i++) {
				this.table[i] = null;
			}
			this.n = 0;
		}
	}

	/**
	 * 返回迭代对象
	 */
	@Override
	public Iterator<E> iterator() {
		return new SeqListIterator<E>();
	}

	@SuppressWarnings("hiding")
	private class SeqListIterator<E> implements Iterator<E> {
		int cursor = 0;

		@Override
		public boolean hasNext() {
			return cursor != n;
		}

		/**
		 * 返回后继元素
		 */
		@Override
		public E next() {
			if (cursor != n) {
				@SuppressWarnings("unchecked")
				E next = (E) get(cursor);
				cursor++;
				return next;
			}
			return null;
		}

		/**
		 * 移除元素
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException();// 不支持该操作,抛出异常
		}
	}

	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		IterableSeqList<String> list = new IterableSeqList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		Iterator<String> it=list.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
}