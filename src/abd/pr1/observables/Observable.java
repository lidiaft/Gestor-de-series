package abd.pr1.observables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Observable<T> implements Iterable<T> {

	private Collection<T> ob = new ArrayList<T>();

	public void addObserver(T observer) {
		ob.add(observer);
	}

	public void removeObserver(T observer) {
		ob.remove(observer);
	}

	@Override
	public Iterator<T> iterator() {
		return this.ob.iterator();
	}

}
