package Asteroids;

/**
 * This class helps keep track of score gained by player and 
 * help check if score reach certain bar so that player get bonus or 
 * the game should turn to new mode.
 */
public class ScoringService {

   private int score = 0;

   /**
    * The score increases when a bullet hits an asteroid. When a large-size
    * asteroid is hit, score increases by 20. When a medium-size asteroid is
    * hit, score increases by 50. When a small-size asteroid is hit, score 
    * increases by 200.
    * @param asteroid the asteroid hit by bullet
    */
   public void hitAsteroid(Asteroid asteroid) {
       if (asteroid.getSize() == 4) {
           score += 20;
       }
       else if (asteroid.getSize() == 2) {
           score += 50;
       }
       else if (asteroid.getSize() == 1) {
           score += 100;
       }
   }


   /**
    * get score the player gained
    * @return score
    */
   public int getScore() {
       return score;
   }
 
}
