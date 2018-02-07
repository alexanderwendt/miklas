package config;

public class MusicConfig {
	private final String relativMusicPath;

	public MusicConfig(String relativMusicPath) {
		this.relativMusicPath = relativMusicPath;
	}

	public String getRelativMusicPath() {
		return relativMusicPath;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MusicConfig [relativMusicPath=");
		builder.append(relativMusicPath);
		builder.append("]");
		return builder.toString();
	}
	
	
}
