package entity;

public class Coffee {

//INIT
//PAUSA para o 
//    CAFE;
//    
}
//
//              _______
//                      (
//                        )     (
//                 ___...(-------)-....___
//             .-""       )    (          ""-.
//       .-'``'|-._             )         _.-|
//      /  .--.|   `""---...........---""`   |
//     /  /    |                             |
//     |  |    |                             |
//      \  \   |                             |
//       `\ `\ |                             |
//         `\ `|                             |
//         _/ /\                             /
//        (__/  \                           /
//     _..---""` \                         /`""---.._
//  .-'           \                       /          '-.
// :               `-.__             __.-'              :
// :                  ) ""---...---"" (                 :
//  '._               `"--...___...--"`              _.'
//jgs \""--..__                              __..--""/
//     '._     """----.....______.....----"""     _.'
//        `""--..,,_____            _____,,..--""`
//        

///////

/*
        if(gameFinished == true) {
            
            g2.setFont(arial_40);
            g2.setColor(Color.white);
            
            String text;
            int textLength;
            int x;
            int y;
            
            text = "Você encontrou o Tesouro!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y);
            
            text = "Seu tempo foi: "+ dFormat.format(playTime) + " segundos!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*5);
//            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Parabéns";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*4);
            g2.drawString(text, x, y);
            
            gp.gameThread = null;
        }
        else {
            g2.setFont(arial_40);
            g2.setColor(Color.white);
//            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
//            g2.drawString("x "+ gp.player.hasKey, 74,65);

            // TIME
            playTime += (double)1/60;
//            g2.drawString("Tempo:"+ dFormat.format(playTime), gp.tileSize*11, 65);
            
            // MESSAGE
            if(messageOn == true) {
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

                messageCounter++;

                if(messageCounter > 120) {
                    messageCounter =0;
                    messageOn = false;
                }
            }            
        }





            String objectName = gp.obj[i].name;
            
            switch(objectName) {
            case "Key":
                gp.playSE(1);
                hasKey++;
                gp.obj[i] = null;
                gp.ui.showMessage("Você pegou uma chave!");
                break;
            case "Door":
                if(hasKey > 0) {
                    gp.playSE(3);
                    gp.obj[i] = null;
                    hasKey --;
                    gp.ui.showMessage("Você abriu a porta!");
                }
                else {
                    gp.ui.showMessage("Você precisa de uma chave!");                    
                }
                break;
            case "Boots":
                gp.playSE(2);
                speed += 4;
                gp.obj[i] = null;
                gp.ui.showMessage("Velocidade acelerada");
                break;
            case "Chest":
                gp.ui.gameFinished = true;
                gp.stopMusic();
                gp.playSE(4);
                gp.ui.showMessage("Você abriu a porta!");
                break;
            }





        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;
        
        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;
        
        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 37 * gp.tileSize;
        gp.obj[2].worldY = 7 * gp.tileSize;
        
        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX =  10 * gp.tileSize;
        gp.obj[3].worldY =  11 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX =  8 * gp.tileSize;
        gp.obj[4].worldY =  28 * gp.tileSize;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX =  12 * gp.tileSize;
        gp.obj[5].worldY =  22 * gp.tileSize;

        gp.obj[6] = new OBJ_Chest(gp);
        gp.obj[6].worldX =  10 * gp.tileSize;
        gp.obj[6].worldY =  7 * gp.tileSize;

        gp.obj[7] = new OBJ_Boots(gp);
        gp.obj[7].worldX =  37 * gp.tileSize;
        gp.obj[7].worldY =  42 * gp.tileSize;


*/