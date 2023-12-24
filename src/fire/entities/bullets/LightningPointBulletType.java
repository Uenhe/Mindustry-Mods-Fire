package fire.entities.bullets;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.gen.*;

public class LightningPointBulletType extends mindustry.entities.bullet.BulletType{

    /** Chance to let the lightning take effect. Using this to control lightning releasing for some reasons... */
    public float lightningChance = 0.5f;

    public LightningPointBulletType(float damage){
        super(0f, damage);
        lifetime = 1f;
        hitEffect = Fx.none;
        despawnEffect = Fx.none;
    }

    @Override
    public void init(Bullet b){
        super.init(b);
        Teamc target;

        //no ally-healing support currently.
        if(b.aimTile != null && b.aimTile.build != null && b.aimTile.build.team != b.team && collidesGround && !b.hasCollided(b.aimTile.build.id)){
            target = b.aimTile.build;
        }else{
            target = Units.closestTarget(
                b.team, b.x ,b.y, homingRange,
                e -> e != null && e.checkTarget(collidesAir, collidesGround) && !b.hasCollided(e.id),
                t -> t != null && collidesGround && !b.hasCollided(t.id)
            );
        }

        if(target != null && Mathf.chance(lightningChance)){
            Fx.chainLightning.at(b.x, b.y, 0f, lightningColor, target);

            //target is either unit or building
            if(target instanceof Unit u){
                b.collision(u, target.getX(), target.getY());
            }else{
                ((Building)target).collision(b);
            }
        }
        b.remove();
    }
}
