package thread;
class titicks {
	private int tic;
	public int total;
	titicks(int tic) {
		this.tic = tic;
		this.total=tic;
	}

	void sell() {
		 {
			tic--;
		}
	}

	boolean have() {
		if (0 < tic) {
			return true;
		} else {
			return false;
		}
	}

	int gettic() {
		return tic;
	}
}

