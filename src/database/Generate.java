package database;

import java.util.Random;

/**
 * Just put all of the generation code into it's own 
 * class so it is out of the way. 
 * 
 * @author Isabella Boone, Chase Banyai, Joel Gingrich, Joshua Jackson
 *
 */
public class Generate {
  
  // feel free to add to this :) 
  private String[] prefix = { "Rat", "Cat", "King", "Legend", "The",
      "Black", "White", "Red", "Orange", "Yellow", "Green", "Blue",
      "Navy", "Teal", "Josh", "Chase", "Joel", "Isabella", "Yeet", 
      "Scotty", "Mayday", "Razer", "Hydrate", "Listen", "Emo", "Liberty",
      "Strategic", "Shy", "Rich", "Lazy", "GallonOf", "Mood", "Opposed", 
      "Elite", "Pyramid", "Respectable", "Defendant", "Welfare", "Thicc",
      "Thiccc", "Thick"},
      
      suffix = {"Ocean", "Adobe", "Eclipse", "Pewdiepie", "Tim", "Emo",
          "God", "Gary", "Mod", "Valorant", "Strike", "War", "Echo", 
          "Mccree", "Zarya", "Coil", "Snicka", "Banyai", "Jackson", 
          "Boone", "Station", "Machine", "Parade", "Garbage", "Trash",
          "Java", "Water", "Chicken", "Scheme", "Consumer", "Fish",
          "Toast", "Bird", "Riot", "Sunday"},
      
      domains = {"OW", "WOW", "yeet", "poptart", "corsair", "amd"},
      armorLoc = {"Head", "Chest", "Legs", "Feet"} ;
  String nums = "1234567890";
  /*
   * @param len how long the string will be
   * @return randomized string of len characters long
   */
  public String randomStr(int len) {
    String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    String user = "";
    for (int i = 0; i < len; i++) {
      int rand = (int) (Math.floor(Math.random() * validChars.length()));
      user += validChars.charAt(rand);
    }
    return user;
  }

  /**
   * Randomize an email
   * @return randomized email
   */
  public String randomEmail() {
    int randDomain = (int) (Math.floor(Math.random() * domains.length)) ;
    String email = randomUser(); 
    email+= "@" + domains[randDomain] + ".com"; 
    return email; 
  }

  /**
   * @return a random int for id numbers
   */
  public int randomIdNum() {
    int id = (new Random()).nextInt();
    if(id < 0) 
      return id * -1; 
    else
      return id;
    
  }
  
  /**
   * @return a string that is a random armor location
   */
  protected String randomArmorLoc() {
    int loc = (int) (Math.floor(Math.random() * armorLoc.length));
    return armorLoc[loc];
  }

  /**
   * Random effect name
   * 
   * @return
   */
  public String randomEffect() {
    String[] effects = { "Ban", "Mute", "Kick", "Deafen" };
    return effects[(int) (Math.floor(Math.random() * effects.length))];
  }

  /**
   * Random name for characters
   * 
   * @return
   */
  public String randomName() {
    int adjNum = (int) (Math.floor(Math.random() * prefix.length)), 
        nameNum = (int) (Math.floor(Math.random() * suffix.length));
    
    return (prefix[adjNum] + suffix[nameNum]);
  }
  
  /**
   * Random username
   */
  public String randomUser() {
    String user = ""; 
    int adjNum = (int) (Math.floor(Math.random() * prefix.length)), 
        nameNum = (int) (Math.floor(Math.random() * suffix.length)),
        numEnd = (int) (Math.floor(Math.random() * 4)); // ran num 0-4
    // numEnd is a randomly generated number that will be how many numbers
    // are at the end of a username
    user+= prefix[adjNum] + suffix[nameNum];
    for(int i = 0; i < numEnd; i++) {
      int s = (int) (Math.floor(Math.random() * nums.length()));
      user+= nums.toString().charAt(s);
    }
    
    return user;
  }

  public String randomAreaType() {
    String[] type = {"Mountain", "Desert", " Forest", "Jungle", "Mesa", "Mushroom", 
        "Ocean", "Plains", "Swamp", "Taiga", "Tundra" };
    return type[((int)(Math.floor(Math.random() * type.length)))];
  }

  public String randomSize() {
    // I guess we're making up area size? idk, change this however you want
    int min = 40, max = 10000; 
    
    int rand = ((int)(Math.floor(Math.random() * (max - min) + min)));
    String size = rand + ""; 
    return size;
  }
}