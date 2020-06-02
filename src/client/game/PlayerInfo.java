package client.game;

public class PlayerInfo implements Comparable<PlayerInfo> {
	private String address;
	private String name;
	private int score;
	private int health;
	
	public PlayerInfo(String address, String name) {
		this.address = address;
		this.name = name;
		this.score = 0;
		this.health = 3;
	}
	
	public String getAddress() {
		return address;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public int compareTo(PlayerInfo info) {
		int cmpScore = this.score - info.getScore();
		if (cmpScore != 0) {
			return cmpScore;
		}
		return this.name.compareTo(info.getName());
	}
}