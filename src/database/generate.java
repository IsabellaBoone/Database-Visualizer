package database;

/**
 * Just put all of the generation code into it's own 
 * class so it is out of the way. 
 * 
 * @author Isabella Boone, Chase Banyai, Joel Gingrich, Joshua Jackson
 *
 */
public class generate {
  // TODO IdNums
  
  // feel free to add to this :) 
  private String[] prefix = { "Rat", "Cat", "King", "Legend", "The",
      "Black", "White", "Red", "Orange", "Yellow", "Green", "Blue",
      "Navy", "Teal", "Josh", "Chase", "Joel", "Isabella", "Yeet", 
      "Scotty", "Mayday"},
      
      suffix = {"Ocean", "Adobe", "Eclipse", "Pewdiepie", "Tim", "Emo",
          "God", "Gary", "Mod", "Valorant", "Strike", "War", "Echo", 
          "Mccree", "Zarya", "Coil", "Snicka", "Banyai", "Jackson", 
          "Boone", "Station", "Machine", "Parade"},
      
      domains = {"OW", "WOW", "yeet", "poptart", "corsair", "amd"};
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
   * TODO not done
   * @return a string of ints for id numbers
   */
  public String randomIdNum() {
    String numbers = "1234567890";
    String idNum = "";
    for (int i = 0; i < 10; i++) {
      int rand = (int) (Math.floor(Math.random() * numbers.length()));
      idNum += numbers.indexOf(rand);
    }
    return idNum;
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
}