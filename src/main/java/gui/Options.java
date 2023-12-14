package gui;

public class Options {
    private String difficulty;
    private float musicVolume;

    public Options() {
        difficulty = "easy";
        musicVolume = 0.5f;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setMusicVolume(float volume) {
        musicVolume = volume;
    }

    // Autres getters et setters pour accéder aux attributs
}
