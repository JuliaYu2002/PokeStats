/*
Final Project - PokeStats (Julia Yu - bs)
*/

import java.util.*;
import java.io.*;

class PokeStats{
  static Scanner in = new Scanner(System.in);
  public static void main(String[] args){
    //create the file readers
    Scanner pIn = null;
    Scanner mIn = null;
    //create arraylists for both Pokemon and moves
    List <Pokemon> Mons = new ArrayList<>();
    List <Move> Moves = new ArrayList<>();
    int menuNum; //int to control the menu
    //string that is the menu, separated out for readability purposes
    String menu = "1. Filter Pokemon by a specified type\n" +
    "2. Filter Pokemon over a specified bst\n" +
    "3. Specify a stat and a number to filter Pokemon with a number higher than the given stat\n" +
    "4. Get legendary Pokemon by specified generation\n" +
    "5. Find all mega evolution pokemon and place them in a txt file\n" +
    "6. Filter Moves by specified type\n" +
    "7. Play a guessing game about Pokemon and their types\n" +
    "0. End program";
    //read in the files, will print an error if they aren't found
    try {
      pIn = new Scanner(new File("pokemon.csv"));
      mIn = new Scanner(new File("moves.csv"));
    } catch (FileNotFoundException ex){
      System.out.println(ex);
    }
    //read in the Pokemon and put a new mon in the arraylist Mons
    while (pIn.hasNext()){
      String s = pIn.nextLine();
      String [] A = s.split(",");
      // Name, Type 1, Type 2, Total Stats, HP, Attack, Defense, Sp. Atk, Sp. Def, Speed, Generation, Legendary
      Pokemon newMon = new Pokemon(A[0], A[1], A[2], Integer.parseInt(A[3]), Integer.parseInt(A[4]), Integer.parseInt(A[5]), Integer.parseInt(A[6]), Integer.parseInt(A[7]), Integer.parseInt(A[8]), Integer.parseInt(A[9]), Integer.parseInt(A[10]), Boolean.parseBoolean(A[11]));
      Mons.add(newMon);
    }
    //read in the moves and put a new move in the arraylist Moves
    while (mIn.hasNext()){
      String s = mIn.nextLine();
      String[] A = s.split(",");
      //Name, Type, Category, PP, Power, Accuracy, Gen
      Move newMove = new Move(A[0], A[1], A[2], Integer.parseInt(A[3]), A[4], A[5], A[6]);
      Moves.add(newMove);
    }
    //menu for the program
    do{
      System.out.println(menu);
      System.out.print("Enter choice: ");
      menuNum = in.nextInt();
      switch(menuNum){
        case 1: //filter pokemon by specified type, maybe insert in a new file
        typeSort(Mons);
          break;
        case 2: //get pokemon with a certain bst above a specified number
        bstGreater(Mons);
          break;
        case 3: //specify a stat and a number to list out pokemon of that type that has a higher total
        statGreater(Mons);
          break;
        case 4: //print legendaries specified by generation
        legendaryGet(Mons);
          break;
        case 5: //find all megas
        megaGet(Mons);
          break;
        case 6: //filter move by specified type
        moveTypeSort(Moves);
          break;
        case 7: //game involving guessing Pokemon's types
        monsTypeGuess(Mons);
          break;
        case 0: System.out.print("Sayonara");
          break;
        default: System.out.println("Error");
      }
    } while(menuNum != 0); //run while menuNum isn't 0
    //close both files
    pIn.close();
    mIn.close();
  }
  //sort and insert Pokemon of a specific type into a new txt file
  static void typeSort(List <Pokemon> p){
    PrintWriter fout = null;
    System.out.print("Enter type to filter: ");
    String a = in.next();
    try {
      fout = new PrintWriter("typeMonFilter_" + a + " .txt"); //create the file to put the mons in (name dependent on user input)
    } catch (IOException ex){
      System.out.println(ex);
    }
    for (int i = 0; i < p.size(); i++){ //loop through entire list of Pokemon
      //check if either type a Pokemon has is the same as the entered type
      if (p.get(i).type1Return().toLowerCase().equals(a.toLowerCase()) || p.get(i).type2Return().toLowerCase().equals(a.toLowerCase())){
        fout.write(p.get(i).nameReturn() + "\n");
      }
    }
    fout.close(); //close the new file
  }
  //find Pokemon with a greater bst (base stat total) than entered, and put them in a new file
  static void bstGreater(List <Pokemon> p){
    PrintWriter fout = null;
    System.out.print("Enter a number: ");
    int a = in.nextInt();
    try {
      fout = new PrintWriter("bstOver" + a + ".txt"); //create the file to put the mons in (name dependent on user input)
    } catch (IOException ex){
      System.out.println(ex);
    }
    for (int i = 0; i < p.size(); i++){
      if (p.get(i).baseReturn() > a){ //check if the bst of the current Pokemon is greater than what was entered
        //add to the file if yes
        fout.write(p.get(i).nameReturn() + " " + p.get(i).baseReturn() + "\n");
      }
    }
    fout.close(); //close the new file
  }
  //gets a stat from the user and a number, and adds Pokemon with a greater stat to a new file
  static void statGreater(List <Pokemon> p){
    PrintWriter fout = null;
    String s = "";
    do{
      System.out.print("Enter a stat: ");
      s = in.next().toLowerCase(); //will only proceed after the user's string is the same as one of the below
    } while(!s.equals("hp") && !s.equals("atk") && !s.equals("def") && !s.equals("spatk") && !s.equals("spdef") && !s.equals("spd"));
    System.out.print("Enter a number: ");
    int a = in.nextInt(); //int for the threshold of higher stats
    try {
      fout = new PrintWriter(s + "Over" + a + ".txt"); //create the file to put the mons in (name dependent on user input)
    } catch (IOException ex){
      System.out.println(ex);
    }
    switch(s){ //switch depending on the stat the user selected
      case "hp":
      for (int i = 0; i < p.size(); i++){
        if (p.get(i).hpReturn() > a){
          fout.write(p.get(i).nameReturn() + " " + p.get(i).hpReturn() + "\n");
        }
      }
        break;
      case "atk":
      for (int i = 0; i < p.size(); i++){
        if (p.get(i).atkReturn() > a){
          fout.write(p.get(i).nameReturn() + " " + p.get(i).atkReturn() + "\n");
        }
      }
        break;
      case "def":
      for (int i = 0; i < p.size(); i++){
        if (p.get(i).defReturn() > a){
          fout.write(p.get(i).nameReturn() + " " + p.get(i).defReturn() + "\n");
        }
      }
        break;
      case "spatk":
      for (int i = 0; i < p.size(); i++){
        if (p.get(i).spatkReturn() > a){
          fout.write(p.get(i).nameReturn() + " " + p.get(i).spatkReturn() + "\n");
        }
      }
        break;
      case "spdef":
      for (int i = 0; i < p.size(); i++){
        if (p.get(i).spdefReturn() > a){
          fout.write(p.get(i).nameReturn() + " " + p.get(i).spdefReturn() + "\n");
        }
      }
        break;
      case "spd":
      for (int i = 0; i < p.size(); i++){
        if (p.get(i).spdReturn() > a){
          fout.write(p.get(i).nameReturn() + " " + p.get(i).spdReturn() + "\n");
        }
      }
        break;
    }
    fout.close(); //close the new file
  }
  //find and put all legendary Pokemon from a specific generation into a txt file
  static void legendaryGet(List <Pokemon> p){
    PrintWriter fout = null;
    System.out.print("Enter generation (1 - 6): ");
    int a = in.nextInt();
    try {
      fout = new PrintWriter("legendsOfGen" + a + ".txt"); //create the file to put the mons in (name dependent on user input)
    } catch (IOException ex){
      System.out.println(ex);
    }
    for (int i = 0; i < p.size(); i++){
      //if the Pokemon is a legendary (true) and the generation is the same as the user's
      if (p.get(i).legendReturn() && p.get(i).genReturn() == a){
        //add the Pokemon to the file
        fout.write(p.get(i).nameReturn() + "\n");
      }
    }
    fout.close(); //close the new file
  }
  //find all mega Pokemon and place them in their own txt file
  static void megaGet(List <Pokemon> p){
    PrintWriter fout = null;
    try {
      fout = new PrintWriter("megaMons.txt");
    } catch (IOException ex){
      System.out.println(ex);
    }
    for (int i = 0; i < p.size(); i++){
      //if the name has Mega in it, it is a Mega Evolution, and it would be placed in the file
      if (p.get(i).nameReturn().contains("Mega")) fout.write(p.get(i).nameReturn() + " " + p.get(i).type1Return() + " " + p.get(i).type2Return() + "\n");
    }
    fout.close(); //close the new file
  }
  //sort moves based off their type and put them in a new file
  static void moveTypeSort(List <Move> m){
    PrintWriter fout = null;
    System.out.print("Enter type to filter: ");
    String a = in.next();
    try {
      fout = new PrintWriter("typeMoveFilter_" + a + " .txt"); //create the file to put the moves in (name dependent on user input)
    } catch (IOException ex){
      System.out.println(ex);
    }
    for (int i = 0; i < m.size(); i++){
      //if the current move's type is the same as the user's input
      //place the move in the file
      if (m.get(i).typeReturn().toLowerCase().equals(a.toLowerCase())){
        fout.write(m.get(i).nameReturn() + " " + m.get(i).catagoryReturn() + "\n");
      }
    }
    fout.close(); //close the new file
  }
  //guessing game about Pokemon's typing, keeps track of number attempted and correct
  static void monsTypeGuess(List <Pokemon> p){
    int randM, points = 0, totalQ = 0;
    String cont;
    String guess;
    System.out.println("Guess the type of the mon given (check the readme for possible types)");
    do {
      totalQ++;
      randM = (int)(Math.random() * p.size()); //picks a random Pokemon each time the loop is run
      System.out.print(p.get(randM).nameReturn() + ": ");
      guess = in.next().toLowerCase();
      //if the type guess is the same as either the first or second type, increase the score by 1
      if (guess.equals(p.get(randM).type1Return().toLowerCase()) || guess.equals(p.get(randM).type2Return().toLowerCase())){
        points++;
        System.out.print("Correct. Enter 'y' to continue, any other key to exit: ");
      //else it's wrong and print out the first type the Pokemon has as an answer
      } else {
        System.out.println("Incorrect. A correct answer could have been " + p.get(randM).type1Return());
        System.out.print("Enter 'y' to continue, any other key to exit: ");
      }
      cont = in.next().toLowerCase(); //user's continue option
    } while(cont.equals("y")); //will keep running while cont = y
    //at the end, print the number of questions attempted and answered correctly
    System.out.println("Total questions attempted: " + totalQ);
    System.out.println("Total questions answered correctly: " + points);
  }
}

//Pokemon class
class Pokemon{
  private String name, type1, type2;
  private int baseStatTotal, hp, atk, def, spatk, spdef, spd, generation;
  private boolean legendary;
  Pokemon(String name, String type1, String type2, int baseStatTotal, int hp, int atk, int def, int spatk, int spdef, int spd, int generation, boolean legendary){
    this.name = name;
    this.type1 = type1;
    this.type2 = type2;
    this.baseStatTotal = baseStatTotal;
    this.hp = hp;
    this.atk = atk;
    this.def = def;
    this.spatk = spatk;
    this.spdef = spdef;
    this.spd = spd;
    this.generation = generation;
    this.legendary = legendary;
  }
  public String toString(){
    return this.name + " " + this.type1 + " " + this.type2;
  }
  public String nameReturn(){
    return this.name;
  }
  public String type1Return(){
    return this.type1;
  }
  public String type2Return(){
    return this.type2;
  }
  public int genReturn(){
    return this.generation;
  }
  public int baseReturn(){
    return this.baseStatTotal;
  }
  public int hpReturn(){
    return this.hp;
  }
  public int atkReturn(){
    return this.atk;
  }
  public int defReturn(){
    return this.def;
  }
  public int spatkReturn(){
    return this.spatk;
  }
  public int spdefReturn(){
    return this.spdef;
  }
  public int spdReturn(){
    return this.spd;
  }
  public boolean legendReturn(){
    return this.legendary;
  }
}

//Move class
class Move{
  private String name, type, catagory, power, accuracy, gen;
  private int pp;
  Move(String name, String type, String catagory, int pp, String power, String accuracy, String gen){
    this.name = name;
    this.type = type;
    this.catagory = catagory;
    this.pp = pp;
    this.power = power;
    this.accuracy = accuracy;
    this.gen = gen;
  }
  public String toString(){
    return this.name + " " + this.type;
  }
  public String nameReturn(){
    return this.name;
  }
  public String typeReturn(){
    return this.type;
  }
  public String catagoryReturn(){
    return this.catagory;
  }
  public String powerReturn(){
    return this.power;
  }
  public String accuracyReturn(){
    return this.accuracy;
  }
  public String genReturn(){
    return this.gen;
  }
  public int ppReturn(){
    return this.pp;
  }
}
