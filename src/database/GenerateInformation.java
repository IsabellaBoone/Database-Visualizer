package database;

import java.sql.*; 


/**
 * Insert randomized information into the database. 
 * @author Isabella Boone, Chase Banyai, Joel Gingrich, Joshua Jackson
 *
 */

public class GenerateInformation {
  
  // Global Variables 
  Connection m_dbConn = null;         // Connection to the database 
  private final int GEN_MIN = 5;      // Min num of entries for each table
  private final int GEN_MAX = 10;     // Max num of entries for each table
  private final int MAX_WEIGHT = 80;  // Max weight for items
  private final int MAX_VOL = 80;     // Max volume for items
  private final int MAX_STATS = 50; // Max value for stats
  private int DEBUG = 0; 
  /**
   * List of all table names.
   */
  String tableNames[] = { "Moderator", "Manager", "Commands",
      "Player", "Location", "Characters", "Item", "Weapon", 
      "Creature", "Abilities", "Armor", "Container", "ContainerInventory",  
      "Hated_Players", "Liked_Players", "Hated_Creatures",
      "Liked_Creatures", "Areas_Willing_To_Go"};
  
  /**
   * All create table statements. 
   */
  String statements[] = {
      "CREATE TABLE IF NOT EXISTS Moderator ("
          + "Username varchar(20), "
          + "Email varchar(100),"
          + "Password varchar(30),"
          + "PRIMARY KEY(Username) );", 
          
      "CREATE TABLE IF NOT EXISTS Manager ("
          + "Username varchar(20), "
          + "Email varchar(100),"
          + "Password varchar(30), "
          + "PRIMARY KEY(Username) );", 
           
      "CREATE TABLE IF NOT EXISTS Commands ("
          + "ManagerUsername varchar(20),"
          + "ModeratorUsername varchar(20),"
          + "IdNumber INT UNSIGNED NOT NULL," 
          + "Effect varchar(20),"
          + "PRIMARY KEY (IdNumber), "
          + "FOREIGN KEY (ManagerUserName) REFERENCES Manager(Username),"
          + "FOREIGN KEY (ModeratorUserName) REFERENCES Moderator(Username)"
          + ");",
           
      "CREATE TABLE IF NOT EXISTS Player ("
          + "Username varchar(20) NOT NULL, "
          + "Email varchar(100),"
          + "Password varchar(30), "
          + "PRIMARY KEY(Username) "
          + ");", 
           
      "CREATE TABLE IF NOT EXISTS Location ("
          + "IdNumber INT UNSIGNED NOT NULL,"
          + "Size INT UNSIGNED NOT NULL,"
          + "AreaType varChar(10) NOT NULL, "
          + "PRIMARY KEY(IdNumber)"
          + "); ",
          
      "CREATE TABLE IF NOT EXISTS Characters ("
          + "Name varchar(20) NOT NULL,"
          + "MaxHP INT UNSIGNED NOT NULL,"
          + "CurrentHP INT UNSIGNED NOT NULL,"
          + "Strength INT UNSIGNED NOT NULL,"
          + "Stamina INT UNSIGNED NOT NULL,"
          + "LocationId INT UNSIGNED NOT NULL,"
          + "pUserName varChar(20),"
          + "PRIMARY KEY (Name),"
          + "FOREIGN KEY (pUserName) REFERENCES Player(Username),"
          + "FOREIGN KEY (LocationId) REFERENCES Location(IdNumber) "
          + ");",
           
      "CREATE TABLE IF NOT EXISTS Item ("
          + "ItemId INT NOT NULL, " 
          + "Weight INT UNSIGNED NOT NULL, "
          + "Volume INT UNSIGNED NOT NULL, "
          + "LocationId INT UNSIGNED,"
          + "cName varchar(20), "
          + "PRIMARY KEY(ItemId),"
          + "FOREIGN KEY(LocationId) REFERENCES Location(IdNumber),"
          + "FOREIGN KEY(cName) REFERENCES Characters(Name)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS Weapon ("
          + "WepId INT NOT NULL,"
          + "FOREIGN KEY(WepId) REFERENCES Item(ItemId) );",
//          + "PRIMARY KEY(IdNumber) );",
          
      "CREATE TABLE IF NOT EXISTS Creature ("
          + "IdNumber INT UNSIGNED NOT NULL ,"
          + "CurrentHP INT UNSIGNED NOT NULL,"
          + "MaxHP INT UNSIGNED NOT NULL,"
          + "Stamina INT UNSIGNED NOT NULL, "
          + "Strength INT UNSIGNED NOT NULL, "
          + "Protection INT UNSIGNED NOT NULL, "
          + "LocationId INT UNSIGNED ,"
          + "PRIMARY KEY(IdNumber), "
          + "FOREIGN KEY(LocationId) REFERENCES Location(IdNumber)"
          + "); ",
          
      "CREATE TABLE IF NOT EXISTS Abilities ("
          + "AbilityId INT UNSIGNED, "
          + "Type varchar(10) NOT NULL,"
          + "EffectAmount INT UNSIGNED,"
          + "WeaponId INT, "
          + "CreatureID INT UNSIGNED,"
          + "PRIMARY KEY (AbilityId),"
          + "FOREIGN KEY (WeaponId) REFERENCES Weapon(WepId),"
          + "FOREIGN KEY (CreatureId) REFERENCES Creature(IdNumber)"
          + "); ",  
          
      "CREATE TABLE IF NOT EXISTS Armor ("
          + "ArmorId INT NOT NULL, "
          + "ArmorLocation char(10) NOT NULL," 
          + "FOREIGN KEY(ArmorId) REFERENCES Item(ItemId)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS Container ("
          + "ContId INT NOT NULL,"
          + "MaxWeight INT UNSIGNED NOT NULL,"
          + "Volume INT UNSIGNED NOT NULL,"
          + "PRIMARY KEY(ContId)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS ContainerInventory ("
          + "ContainerId INT NOT NULL,"
          + "ItemId INT NOT NULL,"
          + "FOREIGN KEY(ContainerId) REFERENCES Container(ContId),"
          + "FOREIGN KEY(ItemId) REFERENCES Item(ItemId)"
          + ");",
           
      "CREATE TABLE IF NOT EXISTS Hated_Players ( "
          + "CreatureIdHates INT UNSIGNED NOT NULL, " 
          + "PlayerHated varChar(20) NOT NULL, " 
          + "FOREIGN KEY (CreatureIdHates) REFERENCES Creature(IdNumber), " 
          + "FOREIGN KEY (PlayerHated) REFERENCES Player(Username) " 
          + ");", 
            
      "CREATE TABLE IF NOT EXISTS Liked_Players ( "
          + "CreatureIdLikes INT UNSIGNED NOT NULL, " 
          + "PlayerLiked varChar(20) NOT NULL, " 
          + "FOREIGN KEY (CreatureIdLikes) REFERENCES Creature(IdNumber), " 
          + "FOREIGN KEY (PlayerLiked) REFERENCES Player(Username) " 
          + ");",     
                 
      "CREATE TABLE IF NOT EXISTS Hated_Creatures ("
          + "hateeCreatureId INT UNSIGNED NOT NULL, "
          + "HatedCreatureId INT UNSIGNED NOT NULL, "
          + "FOREIGN KEY (hateeCreatureId) REFERENCES Creature(IdNumber), "
          + "FOREIGN KEY (HatedCreatureId) REFERENCES Creature(IdNumber)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS Liked_Creatures ("
          + "LikeeCreatureId INT UNSIGNED NOT NULL, "
          + "LikedCreatureId INT UNSIGNED NOT NULL, "
          + "FOREIGN KEY (LikeeCreatureId) REFERENCES Creature(IdNumber), "
          + "FOREIGN KEY (LikedCreatureId) REFERENCES Creature(IdNumber)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS Areas_Willing_To_Go ("
          + "CreatureId INT UNSIGNED,"
          + "LocationId INT UNSIGNED, "
          + "FOREIGN KEY (CreatureId) REFERENCES Creature(IdNumber), "
          + "FOREIGN KEY (LocationId) REFERENCES Location(IdNumber)" 
          + "); " };
  
  public GenerateInformation(Connection con) {
    this.m_dbConn = con; 
    
    dropAllTables();  // Drop tables before creating 
    createTable();    // Create tables 
    populateTables(); // Populate the tables
  }
  
  /**
   * Insert data into all tables.
   */
  private void populateTables() {
    generatePlayers(); 
    generateLocations();
    generateItems(); 
    generateCharacters(); 
  }
  
  /**
   * Create all tables. 
   */
  private void createTable() {
    try {
      Statement stmt = m_dbConn.createStatement();
      for(int i = 0; i < statements.length; i++) {
        try {
          stmt.executeUpdate(statements[i]);
//          System.out.println("Table '" + tableNames[i] + "' Created");
        } catch (SQLException e) {
          System.out.println("Failed to create i: " + i + "" + tableNames[i] + " " + statements[i]);
          e.printStackTrace();
        }   
      }
		System.out.println("Tables created.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Connection was closed."); 
    }   
  }
	
	/**
	 * Drop all tables
	 */
  private void dropAllTables() {
	  String drop = "DROP TABLE ", syntax = ";";
	  
	  try {
	    Statement stmt = m_dbConn.createStatement();
	    
	    for(int i = tableNames.length - 1; i > 0; i--) {
	    try {
	      stmt.execute("SET FOREIGN_KEY_CHECKS = 0;"); 
//	      System.out.println(drop + "" + tableNames[i] + "" + syntax);
	      stmt.execute(drop + tableNames[i] + syntax);
	    } catch (SQLException e) {
//	      e.printStackTrace();
	      if(DEBUG == 1) {
	        System.out.println(drop + "" + tableNames[i] + "" + syntax);
	      } 
	      System.out.println("Failed to drop " + tableNames[i] + ", it doesn't seem to exist."); 
	    }
	  } 
	  System.out.println("Tables dropped.");
	  } catch (SQLException e) {
	    System.out.println("Connection was closed");
	    return;
	  }
	}

	/**
	 * Generate a random number of players
	 */
  private void generatePlayers() {
		int numPlayers = (int) (Math.floor(Math.random() * (GEN_MAX - GEN_MIN) + GEN_MIN));
		if(DEBUG == 1) {
		  System.out.println("Generating " + numPlayers + " players...");
		}
		
		for(int i = 0; i < numPlayers; i++) {
			generatePlayer(); 
		}
	}
	
	/**
	 * Generate a single player
	 */
  private void generatePlayer() {
	  generate g = new generate();
	  // Generate user, email, password
	  String insert = "INSERT INTO Player (Username, Email, Password) VALUES (?,?,?);", user, email, pass;
	  user = g.randomUser();
	  email = g.randomEmail();
	  pass = g.randomStr((int) Math.floor(Math.random() * 8 + 8));
	  
	  try {
		  PreparedStatement statement = m_dbConn.prepareStatement(insert);
		
		  if(DEBUG == 1) {
		    System.out.print(user + " | " + email + " | " + pass + "\t");
		  }
		  
		  statement.setString(1, user);
		  statement.setString(2, email);
		  statement.setString(3, pass);
		  try {
			  statement.execute();
		  } catch(SQLException e) {
		    /* The only reason we should get an exception here is if we attempt to
         * add a primary key that already exists, so in that scenario, we just 
         * call this function again and hope it doesn't happen again =)
         */
		    if(DEBUG == 1) {
		      System.out.println("Duplicate entry. Calling generatePlayer() again. ");
		    }
			  
			  generatePlayer(); 
		  }
	  } catch (SQLException e) {
		  e.printStackTrace();
		  System.out.println("Failure");
	  }
  }
  
	/**
	 * Generate a random number of locations. 
	 */
  private void generateLocations() {
		int numLocations = (int) (Math.floor(Math.random() * (GEN_MAX - GEN_MIN) + GEN_MIN));
		if(DEBUG == 1) {
		  System.out.println("Generating " + numLocations + " locations...");
		}
		
		for(int i = 0; i < numLocations; i++) {
		  generateLocation(); 
		}
	}
	
	/**
	 * Generate a single location. 
	 */
  private void generateLocation() {
	  generate g = new generate(); 
	  int IdNumber;
	  String insert = "INSERT INTO Location (IdNumber, Size, AreaType) VALUES (?,?,?);", Size, AreaType;
//	  System.out.println(insert);
	  
    // Generate IdNumber, Size, AreaType
	  IdNumber = g.randomIdNum();
	  Size = g.randomSize(); 
	  AreaType = g.randomAreaType(); 
    
    try {
      PreparedStatement statement = m_dbConn.prepareStatement(insert);
      // Print data and add it to statement
      if(DEBUG == 1) {
        System.out.print(IdNumber + " | " + Size + " | " + AreaType + "\t");
      }
      
      statement.setInt(1, IdNumber);
      statement.setString(2, Size);
      statement.setString(3, AreaType);
      try { 
        statement.execute();
      } catch(SQLException e) {
        /* The only reason we should get an exception here is if we attempt to
         * add a primary key that already exists, so in that scenario, we just 
         * call this function again and hope it doesn't happen again =)
         */
        if(DEBUG == 1) {
          System.out.println("Duplicate entry. Calling generateLocation() again. ");
        }
        
        generateLocation(); 
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Connection d.");
    }
	}
  
	/**
   * Generate random characters for existing players
   * Side note -- location definitely does not work correctly. 
   */
  private void generateCharacters() {
    RetrieveManipulateInformation r = new RetrieveManipulateInformation(m_dbConn);
    String[] player = r.getAllPlayerUsernames(); 
    for(int i = 0; i < r.getNumPlayers(); i++) {
      // Generate a random number of characters for every player
      int randNumChars = (int) (Math.floor(Math.random() * (GEN_MAX - GEN_MIN) + GEN_MIN));
      if(DEBUG == 1 ) {
        System.out.print(player[i] + " has " + randNumChars + " characters: "); 
      }
      
      for(int j = 0; j < randNumChars; j++) {
        generateCharacter(player[i]); 
      }
      System.out.println(); 
    }
  }
  
	/**
   * Generate a single character.
	 * @param player 
   */
  private void generateCharacter(String player) {
    generate g = new generate(); 
    // Our insert statements and data being generated. 
    String insert = "INSERT INTO Characters (Name, MaxHP, CurrentHP, Strength, Stamina, LocationID, pUserName) VALUES (?,?,?,?,?,?,?);", 
        name  = g.randomName(); 
    int maxHP = MAX_STATS, 
        curHP = ((int) (Math.floor((Math.random()) * MAX_STATS))), 
        str   = ((int) (Math.floor((Math.random()) * MAX_STATS))), 
        stam  = ((int) (Math.floor((Math.random()) * MAX_STATS)));

    int[] loc = new RetrieveManipulateInformation(m_dbConn).getAllLocationIds();
    int location = loc[(int) (Math.floor(Math.random() * (loc.length)))];
        
    try {
      PreparedStatement statement = m_dbConn.prepareStatement(insert);
      if(DEBUG == 1) {
        System.out.print(name + ", ");
      }
      
//      System.out.print(name + " " + curHP + "/" + maxHP + "HP, " + stam + " Stamina, " +
//          str + " Strength, " + loc + " location" + " Player: " + player); 
      
      statement.setString(1, name);
      statement.setInt(2, maxHP);
      statement.setInt(3, curHP);
      statement.setInt(4, str);
      statement.setInt(5, stam);
      statement.setInt(6, location);
      statement.setString(7, player);
      
      try {
        statement.execute(); 
      } catch(SQLException e) {
        if(DEBUG == 1) {
          System.out.print("-- duplicate. trying again." );   
        }
       
//        e.printStackTrace();
        generateCharacter(player); 
      }
      
    } catch(SQLException e) {
      e.printStackTrace();
      System.out.print("connection closed prob idk" );
    }
  }
  
  /**
   * TODO: Implement
   * Generate random moderators
   */
  private void generateModerator() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random managers
   */
  private void generateManager() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random commands for existing manager/moderator
   */
  private void generateCommands() {
    
  }
  
  /**
   * Generate a bunch of items, which can be a weapon, 
   * armor or container
   */
  private void generateItems() {
    // Generate all the items 
    // numItems multiplied by 2 because we need more items
    int numItems = (int) (Math.floor(Math.random() * (GEN_MAX - GEN_MIN) + GEN_MIN)) * 2;
    if(DEBUG == 1) {
      System.out.println("\nGenerating " + numItems + " items...");
    }
    
    for(int i = 0; i < numItems; i++) {
      generateItem(); 
    }
    
    // Makes every item generated a container, weapon or armor 
    // we can't insert items into containers yet but like 
    // i dont think we have to for the assignment so
    for(int i = 0; i < numItems; i++) {
      String insert = "INSERT INTO ";
      String[] types = {"Weapon", "Container", "Armor"};
      int[] listIds = (new RetrieveManipulateInformation(m_dbConn)).getAllItems();
      int type = (int) Math.floor(Math.random() * 3); // 0 = wep, 1 = armor, 2 = container
      

      if(DEBUG == 1) {
        System.out.print(listIds[i] + " " + types[type]);
      }
      
      switch (type) {
      case (0): // Weapon
        insert += "Weapon VALUES (" + listIds[i] + "); ";
        break;
      case (1): // Container
        // we will add to vol when we add items to it, which we currently
        // cannot add any items to containers haha
        int maxW = 80, vol = 0;
        insert += "Container VALUES (" + listIds[i] + ", " + maxW + ", " + vol + ");";
        break;
      case (2):// Armor
        String aLoc = (new generate().randomArmorLoc());
        insert += "Armor VALUES (" + listIds[i] + ", '" + aLoc + "');";
        break;
      }

      try {
//        System.out.print(insert);
        PreparedStatement stmt = m_dbConn.prepareStatement(insert);
        stmt.execute();
      } catch (SQLException e) {
        System.out.print("Error with: " + insert);
//        e.printStackTrace();
      }
    }

  }
  
  /**
   * Generate random item in world/on players
   * TODO: Allow characters to have items. (once characters is implemented)
   * Current goal: have a randomizer determine either how many characters/locations hold
   * items, or just do a random number check and take it from there. 
   */
  private void generateItem() {
    generate g = new generate(); 
//    String insert = "INSERT INTO Item (Id, Weight, Volume, LocationId, cName) VALUES (?,?,?,?,?);";
    String insert = "INSERT INTO Item VALUES (?,?,?,?,?);";
    try {
      PreparedStatement statement = m_dbConn.prepareStatement(insert);
      int idNum = g.randomIdNum(), volume = ((int) (Math.floor((Math.random()) * MAX_VOL))),
        weight = ((int) (Math.floor((Math.random()) * MAX_WEIGHT)));
      
      // Choose a random location
      int[] loc = new RetrieveManipulateInformation(m_dbConn).getAllLocationIds();
      int location = loc[(int) (Math.floor(Math.random() * (loc.length - 1)))];

      if(DEBUG == 1) {
        System.out.print("i:" + idNum + "  w:" + weight + "  v:" + volume + "  l:" + location + "\t");
      }
      
      statement.setInt(1, idNum);
      statement.setInt(2, weight);
      statement.setInt(3, volume);
      statement.setInt(4, location);
      statement.setNull(5, java.sql.Types.VARCHAR);
      try {
        statement.execute();
      } catch (SQLException e) {
        e.printStackTrace();
//        System.out.print("This is kinda broken me tinks");
        if(DEBUG == 1) {
          System.out.println("duplicate idnum: " + idNum + ". regenerating.");
        }
        
        generateItem();
      }

    } catch (SQLException e) {
      e.printStackTrace(); 
    }
  }
  
  /**
   * TODO: Implement
   * Generate random creatures in world
   */
  private void generateCreature() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random abilities for creatures
   */
  private void generateAbilities() {
    
  }
  
  
  /**
   * TODO: Implement
   * Generate random hated players for existing creature
   */
  private void generateHatedPlayer() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random liked players for existing creatures
   */
  private void generateLikedPlayer() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random hated creatures for existing creatures
   */
  private void generateHatedCreature() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random liked creatures for existing creatures
   */
  private void generateLikedCreature() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random areas willing to go for existing creatures
   */
  private void generateAreasWillingToGo() {
    
  }
  
}
