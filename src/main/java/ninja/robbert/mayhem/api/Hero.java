package ninja.robbert.mayhem.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hero implements Cloneable {
    int id;
    String name;
    List<Skill> skills;
    Map<String, Buff> buffs;
    Map<Integer, Long> cooldowns;

    int currentSkill = -1; // no current skill active
    long currentStarted = -1;  // no current skill active
    int maxHealth;
    int maxPower;
    int health;
    int power;
    int regeneration;
    int armor;
    int resistance;
    String powerColor;

    public Hero() {
        // for jackson
    }

    public Hero(String name, int health, int power, String powerColor, int regeneration, int armor, int resistance, List<Skill> skills) {
        this.powerColor = powerColor;
        this.id = -1; // set later
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.power = power;
        this.maxPower = power;
        this.regeneration = regeneration;
        this.armor = armor;
        this.resistance = resistance;
        this.skills = new ArrayList<>(skills);
        this.buffs = new HashMap<>();
        this.cooldowns = new HashMap<>();
    }

    static Hero freshCopy(Hero hero) {
        return new Hero(hero.name, hero.health, hero.power, hero.powerColor, hero.regeneration, hero.armor, hero.resistance, hero.skills);
    }

    public Hero freshCopy(int id) {
        Hero hero = Hero.freshCopy(this);
        hero.setId(id);
        return hero;
    }

    public Hero freshCopy() {
        return Hero.freshCopy(this);
    }

    public Hero exactCopy(int newId) {
        Hero copy = new Hero();
        copy.id = newId;
        copy.name = this.name;
        copy.powerColor = this.powerColor;
        copy.skills = this.skills;
        copy.buffs = this.buffs;
        copy.cooldowns = this.cooldowns;
        copy.currentSkill = this.currentSkill;
        copy.currentStarted = this.currentStarted;
        copy.maxHealth = this.maxHealth;
        copy.maxPower = this.maxPower;
        copy.health = this.health;
        copy.power = this.power;
        copy.regeneration = this.regeneration;
        copy.armor = this.armor;
        copy.resistance = this.resistance;
        return copy;
    }

    // immutable class
    public static class Buff {
        Skill.EffectType type;
        int effect;
        long started;
        long timeout;

        Buff() {
            // for jackson
        }

        public Buff(Skill.EffectType type, int effect, long started, long timeout) {
            this.type = type;
            this.effect = effect;
            this.started = started;
            this.timeout = timeout;
        }

        public Skill.EffectType getType() {
            return type;
        }

        public int getEffect() {
            return effect;
        }

        public long getTimeout() {
            return timeout;
        }

        public long getStarted() {
            return started;
        }
    }

    // immutable class
    public static class Skill {
        int id;
        String name;
        String shout;
        int power;
        int delay;
        int cooldown;
        int duration;
        int effect;
        EffectType type;
        AllowedTarget allowedTarget;

        public enum EffectType {
            health, power, armor, resistance
        }

        public enum AllowedTarget {
            self, others, all
        }

        Skill() {
            // for jackson
        }

        public Skill(int id, String name, String shout, int power, int delay, int cooldown, int duration, int effect, EffectType type, AllowedTarget allowedTarget) {
            this.id = id;
            this.name = name;
            this.shout = shout;
            this.power = power;
            this.delay = delay;
            this.cooldown = cooldown;
            this.duration = duration;
            this.effect = effect;
            this.type = type;
            this.allowedTarget = allowedTarget;

            if (duration == 0 && (type == EffectType.armor || type == EffectType.resistance)) {
                throw new IllegalArgumentException("(de)buffs must have a duration specified");
            }
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getShout() {
            return shout;
        }

        public int getPower() {
            return power;
        }

        public int getDelay() {
            return delay;
        }

        public int getCooldown() {
            return cooldown;
        }

        public int getDuration() {
            return duration;
        }

        public int getEffect() {
            return effect;
        }

        public EffectType getType() {
            return type;
        }

        public AllowedTarget getAllowedTarget() {
            return allowedTarget;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public Map<String, Buff> getBuffs() {
        return buffs;
    }

    public Map<Integer, Long> getCooldowns() {
        return cooldowns;
    }

    @JsonIgnore
    public boolean isBusy() {
        return currentSkill != -1;
    }

    public int getCurrentSkill() {
        return currentSkill;
    }

    public void setCurrentSkill(int currentSkill) {
        this.currentSkill = currentSkill;
    }

    public long getCurrentStarted() {
        return currentStarted;
    }

    public void setCurrentStarted(final long currentStarted) {
        this.currentStarted = currentStarted;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(int maxPower) {
        this.maxPower = maxPower;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getRegeneration() {
        return regeneration;
    }

    public void setRegeneration(int regeneration) {
        this.regeneration = regeneration;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public String getPowerColor() {
        return powerColor;
    }

    @JsonIgnore
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
