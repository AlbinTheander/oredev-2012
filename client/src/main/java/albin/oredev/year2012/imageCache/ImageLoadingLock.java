package albin.oredev.year2012.imageCache;

import java.util.concurrent.atomic.AtomicBoolean;

public class ImageLoadingLock {
	
	private AtomicBoolean isLocked = new AtomicBoolean(false);
	
	public void lock() {
		synchronized (isLocked) {
			isLocked.set(true);
		}
	}
	
	public void unlock() {
		synchronized (isLocked) {
			isLocked.set(false);
			isLocked.notifyAll();
		}
	}
	
	public boolean isLocked() {
		return isLocked.get();
	}
	
	public void waitUntilUnlocked(long maxWaitingTime) {
		synchronized (isLocked) {
			if (isLocked.get()) {
				try {
					isLocked.wait(maxWaitingTime);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	

}
