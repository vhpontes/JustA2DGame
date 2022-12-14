/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow

Class Code modifications and addons:
Victor Hugo Manata Pontes
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/
 
package main;

import entity.Entity;

public class CollisionChecker {
    
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    
    public void checkTile(Entity entity){
        
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        
        int entityLeftCol = entityLeftWorldX  / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;
        
        int tileNum1, tileNum2;
        
        entity.canMove = true;
        
        // Usando uma direção temporária quando começa o empurrão
        String direction = entity.direction;
        if(entity.knockBack == true) {
            direction = entity.knockBackDirection;
        }

//        if(entityLeftCol < 0) {entityLeftCol = 0;}
//        if(entityLeftCol >= gp.maxScreenCol) {entityLeftCol = gp.maxScreenCol-1;}
//        if(entityRightCol < 0) {entityRightCol = 0;}
//        if(entityRightCol >= gp.maxScreenCol) {entityRightCol = gp.maxScreenCol-1;}
//        if(entityTopRow < 0) {entityTopRow = 0;}
//        if(entityTopRow >= gp.maxScreenRow) {entityTopRow = gp.maxScreenRow-1;}
//        if(entityBottomRow < 0) {entityBottomRow = 0;}
//        if(entityBottomRow >= gp.maxScreenRow) {entityBottomRow = gp.maxScreenRow-1;}
//        System.out.println("entity.name:"+entity.name);
//        System.out.println("entity.direction:"+entity.direction);
        
        switch(direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
//                if(entityTopRow < 0) {entityTopRow = 0;}
//                if(entityTopRow >= gp.maxScreenRow) {entityTopRow = gp.maxScreenRow-1;}
                // TO-DO: fazer verificação de limite do mapa
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    entity.canMove = false;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
//                if(entityBottomRow < 0) {entityBottomRow = 0;}
//                if(entityBottomRow >= gp.maxScreenRow) {entityBottomRow = gp.maxScreenRow-1;}
//        System.out.println("entityBottomRow:"+entityBottomRow);
//        System.out.println("entityLeftCol:"+entityLeftCol);
//        System.out.println("entityRightCol:"+entityRightCol);
                // TO-DO: fazer verificação de limite do mapa
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    entity.canMove = false;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
//                if(entityLeftCol < 0) {entityLeftCol = 0;}
//                if(entityLeftCol >= gp.maxScreenCol) {entityLeftCol = gp.maxScreenCol-1;}
//        System.out.println("entityLeftCol:"+entityLeftCol);
//        System.out.println("entityTopRow:"+entityTopRow);
//        System.out.println("entityBottomRow:"+entityBottomRow);
                // TO-DO: fazer verificação de limite do mapa
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
//                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                if(gp.tileM.tile[tileNum1].collision == true) {
                    entity.collisionOn = true;
                    entity.canMove = false;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
//                if(entityRightCol < 0) {entityRightCol = 0;}
//                if(entityRightCol >= gp.maxScreenCol) {entityRightCol = gp.maxScreenCol-1;}
//        System.out.println("entityRightCol:"+entityRightCol);
//        System.out.println("entityTopRow:"+entityTopRow);
//        System.out.println("entityBottomRow:"+entityBottomRow);
                // TO-DO: fazer verificação de limite do mapa
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
//                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                if(gp.tileM.tile[tileNum1].collision == true) {
                    entity.collisionOn = true;
                    entity.canMove = false;
                }
                break;
        }
//        System.out.println("--------------------------");
//        System.out.println("entityLeftCol:"+entityLeftCol);
//        System.out.println("entityRightCol:"+entityRightCol);
//        System.out.println("entityTopRow:"+entityTopRow);
//        System.out.println("entityBottomRow:"+entityBottomRow);
    }
    
    // OBJECT COLLISION CHECK
    public int checkObject(Entity entity, boolean player) {
        
        int index = 999;
        
        // Usando uma direção temporária quando começa o empurrão
        String direction = entity.direction;
        if(entity.knockBack == true) {
            direction = entity.knockBackDirection;
        }        
        
        for(int i = 0; i < gp.obj[1].length; i++) {
            
            if(gp.obj[gp.currentMap][i] != null) {
                
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;
                
                switch(direction) {
                    case "up":   entity.solidArea.y -= entity.speed;break;
                    case "down": entity.solidArea.y += entity.speed;break;
                    case "left": entity.solidArea.x -= entity.speed;break;
                    case "right":entity.solidArea.x += entity.speed;break;
                }

                if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                    if(gp.obj[gp.currentMap][i].collision == true) {
                        entity.collisionOn = true;
                        entity.canMove = false;
                    }
                    if(player == true){
                        index = i;
                    }
                }
                
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // NPC OR MONSTER COLLISION CHECK
    public int checkEntity(Entity entity, Entity[][] target) {
        
        int index = 999;
        
        // Usando uma direção temporária quando começa o empurrão
        String direction = entity.direction;
        if(entity.knockBack == true) {
            direction = entity.knockBackDirection;
        }
        
        for(int i = 0; i < target[1].length; i++) {
            
            if(target[gp.currentMap][i] != null) {
                
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch(direction) {
                    case "up":entity.solidArea.y -= entity.speed;break;
                    case "down":entity.solidArea.y += entity.speed;break;
                    case "left":entity.solidArea.x -= entity.speed;break;
                    case "right":entity.solidArea.x += entity.speed;break;
                }

                if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                    if(target[gp.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        entity.canMove = false;
                        index = i;
                    }
                }
                
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    
    // PLAYER COLLISION CHECK
    public boolean checkPlayer(Entity entity){

        boolean contactPlayer = false;
        
        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get player solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        
        switch(entity.direction) {
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
        }
        
        if(entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            entity.canMove = false;
            contactPlayer = true;
        }
        
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;        
        
        return contactPlayer;
    }
}