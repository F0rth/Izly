package rx.subjects;

import rx.Observable;
import rx.Observable$OnSubscribe;
import rx.Observer;

public abstract class Subject<T, R> extends Observable<R> implements Observer<T> {
    protected Subject(Observable$OnSubscribe<R> observable$OnSubscribe) {
        super(observable$OnSubscribe);
    }

    public abstract boolean hasObservers();

    public final SerializedSubject<T, R> toSerialized() {
        return getClass() == SerializedSubject.class ? (SerializedSubject) this : new SerializedSubject(this);
    }
}
