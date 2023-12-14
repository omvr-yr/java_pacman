package model;

import geometry.RealCoordinates;

/**
 * Implements Pac-Man character using singleton pattern. FIXME: check whether
 * singleton is really a good idea.
 */
public final class PacMan implements Critter {
    private Direction direction = Direction.NONE;
    private RealCoordinates pos;
    private boolean energized;
    private long energizationTime = 0;
    private long energizationDuration = 1000; // Une seconde == environ 63

    private PacMan() {
    }

    public static final PacMan INSTANCE = new PacMan();

    @Override
    public RealCoordinates getPos() {
        return pos;
    }

    @Override
    public double getSpeed() {
        return isEnergized() ? 6 : 4;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void setPos(RealCoordinates pos) {
        this.pos = pos;
    }

    public long getEnergizationTime() {
        return energizationTime;
    }

    public long getEnergizationDuration() {
        return energizationDuration;
    }

    public void setEnergizationTime(long l) {
        this.energizationTime = l;
    }

    public void setEnergizationDuration(long l) {
        this.energizationDuration = l;
    }

    /**
     *
     * @return whether Pac-Man just ate an energizer
     */
    public boolean isEnergized() {
        // TODO handle timeout!
        return energized;
    }

    public void setEnergized(boolean energized) {
        this.energized = energized;
        if (energized) {
            energizationTime = 0; // Réinitialisez le temps d'énergisation
        }
    }

}
