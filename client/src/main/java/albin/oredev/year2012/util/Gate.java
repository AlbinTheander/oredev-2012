package albin.oredev.year2012.util;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Gate is a {@link Thread} synchronization utility. A {@link Gate} can be
 * opened or closed and a {@link Thread} wanting to pass through it, may have to
 * wait until it's open.
 * 
 * @author albintheander
 * 
 */
public class Gate {

	private AtomicBoolean isOpen = new AtomicBoolean(true);

	/**
	 * creates a new instance that is open after creation.
	 */
	public Gate() {
		this(true);
	}

	/**
	 * creates a new Gate object and specifies it's initial status
	 * 
	 * @param open
	 *            <code>true</code> if the gate should be open after creation,
	 *            <code>false</code> otherwise.
	 */
	public Gate(boolean open) {
		isOpen.set(open);
	}

	/**
	 * opens the gate. If there are any threads waiting for the gate, they will
	 * be let through.
	 * 
	 * @see Gate#passThrough(long)
	 */
	public void open() {
		synchronized (isOpen) {
			isOpen.set(true);
			isOpen.notifyAll();
		}
	}

	/**
	 * closes the gate
	 */
	public void close() {
		synchronized (isOpen) {
			isOpen.set(false);
		}
	}

	/**
	 * returns the status of this gate
	 * 
	 * @return <code>true</code> if the gate is open, <code>false</code>
	 *         otherwise.
	 */
	public boolean isOpen() {
		return isOpen.get();
	}

	/**
	 * tries to pass through this gate. If the gate is locked, the thread will
	 * wait for it to be opened. This method will return when one of the
	 * following conditions are fulfilled:
	 * <ul>
	 * <li>the gate is open. If the gate is open when this method is called, the
	 * method will return immediately</li>
	 * <li>the maxWaitingTime is reached. When the maximum waiting time is
	 * reached, this method will return, regardless if the gate is open or not.</li>
	 * <li>the thread was interrupted while waiting for the gate to open. If the
	 * thread was interrupted, the interrupted flag will be set when exiting
	 * this method.</li>
	 * </ul>
	 * 
	 * @param maxWaitingTime
	 *            maximum time in ms to wait for the lock to be opened.
	 * @return true if the thread actually passed through the gate, false
	 *         otherwise. To be more exact, it returns the open state of this
	 *         Gate at the time of exiting the method.
	 */
	public boolean passThrough(long maxWaitingTime) {
		synchronized (isOpen) {
			if (!isOpen.get()) {
				try {
					isOpen.wait(maxWaitingTime);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			return isOpen.get();
		}
	}

}
