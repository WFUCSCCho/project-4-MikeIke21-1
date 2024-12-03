/*
@file: proj4.java
@description: This is the main class of my code, it takes in the input of my file reading however many lines I enter into the arg line
It creates an arraylist of my objects in my player class, I then turn that list into a sorted, shuffled and reversed version of this array list
I run these arraylist through insertions, adding every item to the table, then searching for all items in the table then deleting all the data from it
I collect the runtimes of these processes and compile them in file 'analysis.txt'
@date: September 26, 2024
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME
        ///*STEP 1 make 3 types of array LIST
        ///* we have all the code for this in last project
        ///*insert, search and delete functions

        ArrayList dataArrList = new ArrayList();
        ArrayList<player> playerArrList = new ArrayList();


        //adding to the Arraylist
        while (inputFileNameScanner.hasNextLine()) {
            dataArrList.add(inputFileNameScanner.nextLine());
        }
        //making arraylist data player data

        String playerStats;
        String[] parsedStats;
        for (int i = 0; i < numLines-1; i++) {
            playerStats = (String) dataArrList.get(i);
            parsedStats = playerStats.split(",");
            if (parsedStats.length != 14){
                //System.out.println(playerStats.length);
            }
            //removes the first line
            else if (parsedStats[0].equals("overall_pick")){

            }
            else {
                playerArrList.add(new player(Integer.parseInt(parsedStats[0]),Integer.parseInt(parsedStats[1]),parsedStats[2],parsedStats[3],parsedStats[4],Integer.parseInt(parsedStats[6]),
                        Integer.parseInt(parsedStats[7]),Double.parseDouble(parsedStats[8]),Double.parseDouble(parsedStats[9]),Double.parseDouble(parsedStats[10]),Double.parseDouble(parsedStats[11]),Double.parseDouble(parsedStats[12]),Double.parseDouble(parsedStats[13])));
            }


        }


        //SORTED ARR LIST
        ArrayList<player> sortedArrList = new ArrayList();
        sortedArrList.addAll(playerArrList);
        Collections.sort(sortedArrList);

        //random ARR List
        ArrayList<player> randArrList = new ArrayList();
        randArrList.addAll(playerArrList);
        Collections.shuffle(randArrList);

        //REVERSE ARR LIST
        ArrayList<player> revArrList = new ArrayList();
        revArrList.addAll(playerArrList);
        Collections.sort(revArrList, Collections.reverseOrder());

        ///*sanity check
        //System.out.println(sortedArrList);
        //System.out.println(randArrList);
        //System.out.println(revArrList);

        SeparateChainingHashTable<player> sortedHash = new SeparateChainingHashTable<>();
        SeparateChainingHashTable<player> randHash = new SeparateChainingHashTable<>();
        SeparateChainingHashTable<player> revHash = new SeparateChainingHashTable<>();



        ///* insert functions timing
        //Sorted Insert time
        long insertSortStart = System.nanoTime();
        for (int i = 0; i < sortedArrList.size()-1; i++) {
            sortedHash.insert(sortedArrList.get(i));
        }
        long insertSortEnd = System.nanoTime();

        //Shuffled insert time
        long insertRandStart = System.nanoTime();
        for (int i = 0; i < randArrList.size()-1; i++) {
            randHash.insert(randArrList.get(i));
        }

        long insertRandEnd = System.nanoTime();

        //Reverse insert time 
        long insertRevStart = System.nanoTime();
        for (int i = 0; i < revArrList.size()-1; i++) {
            revHash.insert(revArrList.get(i));
        }

        long insertRevEnd = System.nanoTime();

        //differences

        long sortedInsertTime = insertSortEnd-insertSortStart;
        long randInsertTime = insertRandEnd-insertRandStart;
        long revInsertTime = insertRevEnd-insertRevStart;

        //uploading to file


        ///* search functions timing
        long searchSortStart = System.nanoTime();
        //search for the last item in the last
        for (int i = 0; i < sortedArrList.size()-1; i++) {
            sortedHash.contains(sortedArrList.get(i));
        }
        sortedHash.contains(sortedArrList.get(sortedArrList.size() - 1));
        long searchSortEnd = System.nanoTime();

        long searchRandStart = System.nanoTime();
        for (int i = 0; i < randArrList.size()-1; i++) {
            randHash.contains(randArrList.get(i));
        }
        long searchRandEnd= System.nanoTime();

        long searchRevStart = System.nanoTime();
        for (int i = 0; i < revArrList.size()-1; i++) {
            revHash.contains(revArrList.get(i));
        }
        long searchRevEnd = System.nanoTime();

        long searchSortTime = searchSortEnd-searchSortStart;
        long searchRandTime = searchRandEnd-searchRandStart;
        long searchRevTime = searchRevEnd-searchRevStart;


        //uploading to file



        ///* delete functions timing

        long deleteSortStart = System.nanoTime();
        for (int i = 0; i < sortedArrList.size()-1; i++) {
            sortedHash.remove(sortedArrList.get(i));
        }
        long deleteSortEnd = System.nanoTime();

        long deleteRandStart = System.nanoTime();
        for (int i = 0; i < randArrList.size()-1; i++) {
            randHash.remove(randArrList.get(i));
        }
        long deleteRandEnd = System.nanoTime();

        long deleteRevStart = System.nanoTime();
        revHash.remove(revArrList.get(revArrList.size() - 1));
        long deleteRevEnd = System.nanoTime();

        long deleteSortTime = deleteSortEnd-deleteSortStart;
        long deleteRandTime = deleteRandEnd-deleteRandStart;
        long deleteRevTime = deleteRevEnd-deleteRevStart;

        ///* writing to file
        writeTofile( "==========RUNTIMES==========", "analysis.txt");
        writeTofile("NODES: " + numLines, "analysis.txt");
        writeTofile( "-----INSERTS-----", "analysis.txt");
        writeTofile( "Sorted: " + sortedInsertTime, "analysis.txt");
        writeTofile( "Shuffled: " + randInsertTime, "analysis.txt");
        writeTofile( "Reversed: " + revInsertTime, "analysis.txt");
        writeTofile( "-----SEARCHES-----", "analysis.txt");
        writeTofile( "Sorted: " + searchSortTime, "analysis.txt");
        writeTofile( "Shuffled: " + searchRandTime, "analysis.txt");
        writeTofile( "Reversed: " + searchRevTime, "analysis.txt");
        writeTofile("-----DELETIONS-----", "analysis.txt");
        writeTofile( "Sorted: " + deleteSortTime, "analysis.txt");
        writeTofile( "Shuffled: " + deleteRandTime, "analysis.txt");
        writeTofile( "Reversed: " + deleteRevTime, "analysis.txt");





    }

    //FILEWRITER
    private static void writeTofile(String content, String filePath) throws IOException {
        FileWriter file  = new FileWriter(filePath, true);
        file.write(content + System.lineSeparator());
        file.close();
    }
}
