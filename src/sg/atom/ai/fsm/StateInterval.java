package sg.atom.ai.fsm;

public class StateInterval {
	String id;
	float stateTime;
	float maxTime;
	float minTime;
	int maxCount;
	boolean isActived;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public float getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(float maxTime) {
		this.maxTime = maxTime;
	}

	public float getMinTime() {
		return minTime;
	}

	public void setMinTime(float minTime) {
		this.minTime = minTime;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public boolean isActived() {
		return isActived;
	}

	public void setActived(boolean isActived) {
		this.isActived = isActived;
	}

}
