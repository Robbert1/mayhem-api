package ninja.robbert.mayhem.api;

public class ActionMessage implements InputMessage {
    int hero;
    int skill;
    int target;
    boolean override;

    ActionMessage() {
        // for jackson
    }

    public ActionMessage(int hero, int skill, int target, boolean override) {
        this.hero = hero;
        this.skill = skill;
        this.target = target;
        this.override = override;
    }

    public int getHero() {
        return hero;
    }

    public int getSkill() {
        return skill;
    }

    public int getTarget() {
        return target;
    }

    public boolean isOverride() {
        return override;
    }
}
