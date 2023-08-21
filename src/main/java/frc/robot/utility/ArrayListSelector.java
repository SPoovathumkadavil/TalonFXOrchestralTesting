package frc.robot.utility;

import java.util.ArrayList;

public class ArrayListSelector<E> extends ArrayList<E> {

	private static int currentIndex;

	public ArrayListSelector() {
		super();
	}

	public Object getObject(int index) {
		return this.get(index);
	}

	public int nextIndex() {
		if (currentIndex++ >= this.size()) currentIndex = 0; else {
			currentIndex++;
		}
		return currentIndex;
	}
}
