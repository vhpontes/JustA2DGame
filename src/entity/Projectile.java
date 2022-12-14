/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow

Class Code modifications and addons:
Victor Hugo Manata Pontes
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/
 
package entity;

import main.GamePanel;

public class Projectile extends Entity{
    
    Entity user;
    
    public Projectile(GamePanel gp) {
        super(gp);
    }
    
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
        this.stackable = true;
    }
    
    public void update() {
        
        int attackPower = attack * (gp.player.level/2); // for fireball
        
        // ARROW PROJECTILE DAMAGE
        if(user == gp.player && gp.player.currentWeapon.type == type_bow) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, this, attack, user.projectileWeapow.knockBackPower);
                generateParticle(user.projectileWeapow, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;
            }
        }
        // FIREBALL PROJECTILE DAMAGE
        if(user == gp.player && gp.player.currentWeapon.type != type_bow) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, this, attackPower, user.projectile.knockBackPower);
                generateParticle(user.projectile, gp.monster[gp.currentMap][monsterIndex]);
                alive = false;
            }
        }
        // MONSTER PROJECTILE DAMAGE
        if(user != gp.player) {
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(gp.player.invincible == false && contactPlayer == true) {
                damagePlayer(attack);
                if(gp.player.guarding == false) {
                    generateParticle(user.projectile, gp.player);
                }
                else {
                    generateParticle(user.projectile, user.projectile);
                }
                
                if(user.type == type_npcTwitch) {
                    gp.npcTW.damageNpcTwitch(attack);
                }
                alive = false;
            }
        }
        
        switch(direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }
        
        life--;
        if(life <= 0) {
            alive = false;
        }
        
        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    
    public boolean haveResource(Entity user) {

        boolean haveResource = false;
        return haveResource;
    }
    
    public void subtractResource(Entity user) {}
}