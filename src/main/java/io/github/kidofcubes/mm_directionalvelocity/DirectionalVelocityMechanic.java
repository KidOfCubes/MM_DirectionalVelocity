package io.github.kidofcubes.mm_directionalvelocity;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderFloat;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;


//the annotator registerer thing doesnt seem to pick up my class sadly D:
@MythicMechanic(
        author = "KidOfCubes",
        name = "dirvelocity",
        description = "Modifies the directional velocity on the target entity"
)
public class DirectionalVelocityMechanic implements ITargetedEntitySkill {
    protected PlaceholderFloat right;
    protected PlaceholderFloat up;
    protected PlaceholderFloat forward;
    protected VelocityMode mode;
    public DirectionalVelocityMechanic(MythicLineConfig mlc) {
        this.right = mlc.getPlaceholderFloat(new String[]{"right","r"}, 0.0F);
        this.up = mlc.getPlaceholderFloat(new String[]{"up","u"}, 0.0F);
        this.forward = mlc.getPlaceholderFloat(new String[]{"forward","f"}, 0.0F);
        String strMode = mlc.getString(new String[]{"mode","m"}, "ADD");
        switch (strMode.toUpperCase()) {
            case "ADD":
                this.mode = VelocityMode.ADD;
                break;
            case "SUBTRACT":
                this.mode = VelocityMode.SUBTRACT;
                break;
            case "MULTIPLY":
                this.mode = VelocityMode.MULTIPLY;
                break;
            case "DIVIDE":
                this.mode = VelocityMode.DIVIDE;
                break;
            default:
                this.mode = VelocityMode.SET;
        }
    }
    public static Vector right(Location location){
        return new Vector(-Math.cos(Math.toRadians(location.getYaw())),0,-Math.sin(Math.toRadians(location.getYaw())));
    }
    public static Vector up(Location location){
        return new Vector(
                -Math.sin(Math.toRadians(location.getPitch()))*Math.sin(Math.toRadians(location.getYaw())),
                Math.cos(Math.toRadians(location.getPitch())),
                Math.sin(Math.toRadians(location.getPitch()))*Math.cos(Math.toRadians(location.getYaw())));
    }
    public SkillResult castAtEntity(SkillMetadata data, AbstractEntity target) {
        Entity e = target.getBukkitEntity();
        Vector origV = e.getVelocity();
        Vector forwardV = e.getLocation().getDirection();
        Vector rightV = right(e.getLocation());
        Vector upV = up(e.getLocation());


        Vector outVec = new Vector();

        switch (mode){
            case ADD -> {
                outVec.add(forwardV.multiply(origV.dot(forwardV)+forward.get()));
                outVec.add(rightV.multiply(origV.dot(rightV)+right.get()));
                outVec.add(upV.multiply(origV.dot(upV)+up.get()));
            }
            case SUBTRACT -> {
                outVec.add(forwardV.multiply(origV.dot(forwardV)-forward.get()));
                outVec.add(rightV.multiply(origV.dot(rightV)-right.get()));
                outVec.add(upV.multiply(origV.dot(upV)-up.get()));
            }
            case MULTIPLY -> {
                outVec.add(forwardV.multiply(origV.dot(forwardV)*forward.get()));
                outVec.add(rightV.multiply(origV.dot(rightV)*right.get()));
                outVec.add(upV.multiply(origV.dot(upV)*up.get()));
            }
            case DIVIDE -> {
                outVec.add(forwardV.multiply(origV.dot(forwardV)/forward.get()));
                outVec.add(rightV.multiply(origV.dot(rightV)/right.get()));
                outVec.add(upV.multiply(origV.dot(upV)/up.get()));
            }
            default -> {
                outVec.add(forwardV.multiply(forward.get()));
                outVec.add(rightV.multiply(right.get()));
                outVec.add(upV.multiply(up.get()));
            }
        }

        if (outVec.length() > 4.0) outVec = outVec.normalize().multiply(4); //this limiter is kinda cringe, but im making this work like the VelocityMechanic soo
        if (Double.isNaN(outVec.getX())) outVec.setX(0);
        if (Double.isNaN(outVec.getY())) outVec.setY(0);
        if (Double.isNaN(outVec.getZ())) outVec.setZ(0);

        e.setVelocity(outVec);
        return SkillResult.SUCCESS;
    }
    enum VelocityMode {
        SET,
        ADD,
        MULTIPLY,
        SUBTRACT,
        DIVIDE;
    }
}
