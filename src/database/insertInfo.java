package database;

import java.sql.*; 


/**
 * Insert randomized information into the database. 
 * @author Isabella Boone, Chase Banyai, Joel Gingrich, Joshua Jackson
 *
 */

public class insertInfo {
  // Global Variables 
  Connection m_dbConn = null; 
  protected final int GEN_MIN = 5; // Min num of entries for each table
  protected final int GEN_MAX = 10; // Max num of entries for each table
  String tableNames[] = { "Moderator", "Manager", "Commands",
      "Player", "Location", "Characters", "Item", "Weapon", 
      "Abilities", "Creature", "Armor", "Container", "ContainerInventory",  
      "Hated_Players", "Liked_Players", "Hated_Creatures",
      "Liked_Creatures", "Areas_Willing_To_Go"};
  
  // statements TODO: Add NOT NULL where needed, be consistent, triple check with Google Doc
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
          + "IdNumber char(10) NOT NULL," 
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
          + "IdNumber char(10) NOT NULL,"
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
          + "LocationId char(10) NOT NULL,"
          + "pUserName varChar(20),"
          + "PRIMARY KEY (Name),"
          + "FOREIGN KEY (pUserName) REFERENCES Player(Username),"
          + "FOREIGN KEY (LocationId) REFERENCES Location(IdNumber) "
          + ");",
           
      "CREATE TABLE IF NOT EXISTS Item ("
          + "Id char(10) NOT NULL, " 
          + "Weight INT UNSIGNED NOT NULL, "
          + "Volume INT UNSIGNED NOT NULL, "
          + "LocationId char(10),"
          + "cName varchar(20), "
          + "PRIMARY KEY(Id),"
          + "FOREIGN KEY(LocationId) REFERENCES Location(IdNumber),"
          + "FOREIGN KEY(cName) REFERENCES Characters(Name)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS Weapon ("
          + "IdNumber char(10),"
          + "PRIMARY KEY(IdNumber) );",
           
          
      "CREATE TABLE IF NOT EXISTS Abilities ("
          + "AbilityId char(10), "
          + "Type varchar(10) NOT NULL,"
          + "EffectAmount INT UNSIGNED NOT NULL,"
          + "WeaponId char(10) NOT NULL, "
          + "CreatureID char(10) NOT NULL,"
          + "PRIMARY KEY (AbilityId),"
          + "FOREIGN KEY (WeaponId) REFERENCES Weapon(IdNumber),"
          + "FOREIGN KEY (CreatureId) REFERENCES Creature(IdNumber)"
          + "); ",
          
      "CREATE TABLE IF NOT EXISTS Creature ("
          + "IdNumber char(10),"
          + "CurrentHP INT UNSIGNED NOT NULL,"
          + "MaxHP INT UNSIGNED NOT NULL,"
          + "Stamina INT UNSIGNED NOT NULL, "
          + "Strength INT UNSIGNED NOT NULL, "
          + "Protection INT UNSIGNED NOT NULL, "
          + "LocationId char(10),"
          + "PRIMARY KEY(IdNumber), "
          + "FOREIGN KEY(LocationId) REFERENCES Location(IdNumber)"
          + "); ",
         
           
      "CREATE TABLE IF NOT EXISTS Armor ("
          + "Id char(10) NOT NULL, "
          + "ArmorLocation char(10) NOT NULL," 
          + "FOREIGN KEY(Id) REFERENCES Item(Id)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS Container ("
          + "Id char(10) NOT NULL,"
          + "MaxWeight INT UNSIGNED NOT NULL,"
          + "Volume INT UNSIGNED NOT NULL,"
          + "PRIMARY KEY(Id)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS ContainerInventory ("
          + "ContainerId char(10) NOT NULL,"
          + "ItemId char(10) NOT NULL,"
          + "FOREIGN KEY(ContainerId) REFERENCES Container(Id),"
          + "FOREIGN KEY(ItemId) REFERENCES Item(Id)"
          + ");",
           
      "CREATE TABLE IF NOT EXISTS Hated_Players ( "
          + "CreatureIdHates char(10) NOT NULL, " 
          + "PlayerHated varchar(20) NOT NULL, " 
          + "FOREIGN KEY (CreatureIdHates) REFERENCES Creature(IdNumber), " 
          + "FOREIGN KEY (PlayerHated) REFERENCES Player(Username) " 
          + ");", 
            
      "CREATE TABLE IF NOT EXISTS Liked_Players ( "
          + "CreatureIdLikes char(10) NOT NULL, " 
          + "PlayerLiked varchar(20) NOT NULL, " 
          + "FOREIGN KEY (CreatureIdLikes) REFERENCES Creature(IdNumber), " 
          + "FOREIGN KEY (PlayerLiked) REFERENCES Player(Username) " 
          + ");",     
                 
      "CREATE TABLE IF NOT EXISTS Hated_Creatures ("
          + "hateeCreatureId char(10) NOT NULL, "
          + "HatedCreatureId char(10) NOT NULL, "
          + "FOREIGN KEY (hateeCreatureId) REFERENCES Creature(IdNumber), "
          + "FOREIGN KEY (HatedCreatureId) REFERENCES Creature(IdNumber)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS Liked_Creatures ("
          + "LikeeCreatureId char(10) NOT NULL, "
          + "LikedCreatureId char(10) NOT NULL, "
          + "FOREIGN KEY (LikeeCreatureId) REFERENCES Creature(IdNumber), "
          + "FOREIGN KEY (LikedCreatureId) REFERENCES Creature(IdNumber)"
          + "); ",
           
      "CREATE TABLE IF NOT EXISTS Areas_Willing_To_Go ("
          + "CreatureId char(10),"
          + "LocationId char(10), "
          + "FOREIGN KEY (CreatureId) REFERENCES Creature(IdNumber), "
          + "FOREIGN KEY (LocationId) REFERENCES Location(IdNumber)" 
          + "); " };
  
  
	
  public insertInfo(Connection con) {
    this.m_dbConn = con; 
    
    dropAllTables(); // Drop tables before creating 
    createTable();  // Create tables 
    populateTables(); // Populate the tables
  }
  
  /**
   * Insert data into all tables.
   */
  public void populateTables() {
    generatePlayers(); 
    generateLocations();
  }
  
  /**
   * Create all tables needed if they do not already exist. 
   * 
   * Used Chase & Joel's HW3P1 for this, but changed a few things
   *  - changed id numbers from positive ints to char(10)
   *  - I made it so armor/weapons/etc use a foreign key to refer to item
   */
  public void createTable() {
    try {
      Statement stmt = m_dbConn.createStatement();
      for(int i = 0; i < statements.length; i++) {
//        System.out.println(statements[i]);
        try {
          stmt.executeUpdate(statements[i]);
        } catch (SQLException e1) {
          System.out.println("Something went wrong. ");
          e1.printStackTrace();
        }
        
//        System.out.println("Table '" + tableNames[i] + "' Created");
      }
		System.out.println("Tables created.");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("aaInvalid SQL Statement. Check sytax/statement and try again."); 
    }   
  }
	
	/**
	 * Drop all tables
	 */
	public void dropAllTables() {
	  String drop = "DROP TABLE ", syntax = ";";
	  Statement stmt;
	  try {
	    stmt = m_dbConn.createStatement();
	  } catch (SQLException e1) {
	    System.out.println("'dropAllTables()' aborted");
	    return;
	  } 
    
	  /* 
	   * List of names is in order of being added, and done so
	   * we can have foreign keys. Need to start at the end so 
	   * we can drop constraints and not cause issues.  
	   */
	  for(int i = tableNames.length; i < 0; i--) {
	    try {
//	      System.out.println(drop + "" + tableNames[i] + "" + syntax);
	      stmt.execute(drop + tableNames[i] + syntax);
	    } catch (SQLException e) {
	      e.printStackTrace();
	      System.out.println(drop + "" + tableNames[i] + "" + syntax);
	      System.out.println("Failed to drop " + tableNames[i] + ", it doesn't seem to exist."); 
	    }
	    
	  } 
	  System.out.println("Tables dropped.");
	}
	
	/**
	 * Activate JDBC
	 * @return whether activation was successful
	 */
	public static boolean activateJDBC() {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return true;
	}

	
	//
	// Generation Methods
	//
	
	/**
	 * Generate a random number of players
	 */
	public void generatePlayers() {
		int numPlayers = (int) (Math.floor(Math.random() * (GEN_MAX - GEN_MIN) + GEN_MIN));
		System.out.println("Generating " + numPlayers + " players...");
		for(int i = 0; i < numPlayers; i++) {
			generatePlayer(); 
		}
	}
	
	
	/**
	 * Generate a single player
	 */
	public void generatePlayer() {
	  generate g = new generate();
	  // Generate user, email, password
	  String insert = "INSERT INTO Player (Username, Email, Password) VALUES (?,?,?);", user, email, pass;
	  user = g.randomUser();
	  email = g.randomEmail();
	  pass = g.randomStr((int) Math.floor(Math.random() * 8 + 8));
	  
	  try {
		  PreparedStatement statement = m_dbConn.prepareStatement(insert);
		// Print data and add it
		  System.out.println(user + " | " + email + " | " + pass);
		  statement.setString(1, user);
		  statement.setString(2, email);
		  statement.setString(3, pass);
		  try {
			  statement.execute();
		  } catch(SQLException e) {
			  System.out.println("Duplicate entry. Calling generatePlayer() again. ");
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
	public void generateLocations() {
		int numLocations = (int) (Math.floor(Math.random() * (GEN_MAX - GEN_MIN) + GEN_MIN));
		System.out.println("Generating " + numLocations + " locations...");
		for(int i = 0; i < numLocations; i++) {
		  generateLocation(); 
		}
	}
	
	/**
	 * Generate a single location. 
	 */
	public void generateLocation() {
	  generate g = new generate(); 
	  String insert = "INSERT INTO Location (IdNumber, Size, AreaType) VALUES (?,?,?);", IdNumber, Size, AreaType;

    // Generate IdNumber, Size, AreaType
	  IdNumber = g.randomIdNum();
	  Size = g.randomSize(); 
	  AreaType = g.randomAreaType(); 
    
    try {
      PreparedStatement statement = m_dbConn.prepareStatement(insert);
    // Print data and add it
      System.out.println(IdNumber + " | " + Size + " | " + AreaType);
      statement.setString(1, IdNumber);
      statement.setString(2, Size);
      statement.setString(3, AreaType);
      try {
        statement.execute();
      } catch(SQLException e) {
        System.out.println("Duplicate entry. Calling generateLocation() again. ");
        generateLocation(); 
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Failure");
    }
	}
  
	/**
   * Generate random characters for existing players
   */
  public void generateCharacters() {
    // Firstly, get num of existing players. 
    
    
    
//    int numChars = (int) (Math.floor(Math.random() * (GEN_MAX - GEN_MIN) + GEN_MIN));
//    System.out.println("Generating " + numLocations + " locations...");
//    for(int i = 0; i < numLocations; i++) {
//      generateLocation(); 
//    }
  }
  
  /**
   * TODO: Implement
   * Generate random moderators
   */
  public void generateModerator() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random managers
   */
  public void generateManager() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random commands for existing manager/moderator
   */
  public void generateCommands() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random item in world/on players
   */
  public void generateItem() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random creatures in world
   */
  public void generateCreature() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random weapons in world/on players
   */
  public void generateWeapon() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random abilities for creatures
   */
  public void generateAbilities() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random armor in world/on players
   */
  public void generateArmor() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random container
   */
  public void generateContainer() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random contents for a container
   */
  public void generateContainerInventory() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random hated players for existing creature
   */
  public void generateHatedPlayer() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random liked players for existing creatures
   */
  public void generateLikedPlayer() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random hated creatures for existing creatures
   */
  public void generateHatedCreature() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random liked creatures for existing creatures
   */
  public void generateLikedCreature() {
    
  }
  
  /**
   * TODO: Implement
   * Generate random areas willing to go for existing creatures
   */
  public void generateAreasWillingToGo() {
    
  }
  
}
