package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}



	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {

	    //initialize a new scanner for the words and a hashmap for this documents keyword index
	    Scanner readWords = new Scanner(new File(docFile));
        HashMap<String,Occurrence> curDocIndex = new HashMap<String,Occurrence>(1000,2.0f);

        //loop through all the words in the document
	    while (readWords.hasNext()) {
	        String wordToCheck = readWords.next();

            //check the current word
            String keyword = getKeyword(wordToCheck);

            //skip nulls
            if (keyword != null && !keyword.equals("")) {
                //add the keyword to the list
                //if it exists add one to the Occurrence
                if (curDocIndex.containsKey(keyword)){
                    Occurrence curOcc = curDocIndex.get(keyword);
                    curDocIndex.replace(keyword, (new Occurrence(docFile,(curOcc.frequency +1))));


                    //if it doesn't exist
                }else{
                    curDocIndex.put(keyword, new Occurrence(docFile, 1) );
                }
            }
        }
        readWords.close();
        return curDocIndex;
	}




	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {

        if (kws != null && !kws.isEmpty()) {
            //loop through all the kws keys
            for (String key : kws.keySet()) {

                //make a new temp arraylist of occurrences
                ArrayList<Occurrence> curOccs = new ArrayList<Occurrence>();

                //if it doesn't exist yet
                if (!keywordsIndex.containsKey(key)) {
                    //insert the first occurrence object from the current key in the front
                    curOccs.add(0, kws.get(key));
                    //add it to the master list;
                    keywordsIndex.put(key, curOccs);


                //if it exists add to the Occurrence list
                } else {
                    //insert the keys occurrence at the end of the list to be picked up by the ILO method
                    keywordsIndex.get(key).add(kws.get(key));
                    //call the method to insert
                    insertLastOccurrence(keywordsIndex.get(key));
                }
            }
        }
    }




	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		//already null
	    if (word == null || word.equals("")){
		    return null;

		//not a match
        }else if (!word.matches("[a-zA-Z]*[.,!?:;]*")){
            return null;

        //found a match, edit and return
        }else {
            word = word.toLowerCase();
            word = word.replaceAll("[.,!?:;]", "");
        }

        //check if keyword is any noise word;
        if (noiseWords.contains(word)) {
            return null;

        }

        return word;
	}




	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {

        ArrayList<Integer> midValues = new ArrayList<Integer>();

        if (occs.size() > 1) {
            //trim it down so we know we have our object at the end
            occs.trimToSize();
            //pickup the last item
            Occurrence lastOcc = occs.get(occs.size() - 1);
            //remove it from the end of the list
            occs.remove(occs.size() - 1);
            occs.trimToSize();

            int left = 0;
            int right = occs.size() - 1;
            int insertIndex = 0;
            while (left <= right) {
                int mid = (left + right) / 2;
                midValues.add(mid);
                insertIndex = mid;
                if (occs.get(mid).frequency > lastOcc.frequency) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            //decide to add it before or after the mid point given by binary search
            if (occs.get(insertIndex).frequency <= lastOcc.frequency) {
                occs.add(insertIndex, lastOcc);
            } else {
                occs.add(insertIndex + 1, lastOcc);
            }
        }
        return midValues;
    }



	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}





	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {

	    //initialize
        ArrayList<String> docHits = new ArrayList<String>();
        ArrayList<Occurrence> kw1Occs = new ArrayList<Occurrence>();
        ArrayList<Occurrence> kw2Occs = new ArrayList<Occurrence>();
        ArrayList<Occurrence> merge = new ArrayList<Occurrence>();


        //get kw1Occs
        if(kw1 != null && !kw1.equals("") && keywordsIndex.get(kw1) != null){
            kw1Occs = keywordsIndex.get(kw1);
            Collections.reverse(kw1Occs);
        }

        //get kw2Occs
        if(kw2 != null && !kw2.equals("") && keywordsIndex.get(kw2) != null){
            kw2Occs = keywordsIndex.get(kw2);
            Collections.reverse(kw2Occs);
        }

        //add all the kw2Occs
        for(Occurrence occ : kw2Occs){
            //insert the keys occurrence at the end of the list to be picked up by the ILO method
            merge.add(occ);
            //call the method to insert
            insertLastOccurrence(merge);
        }

        //add all the kw1Occs
        for(Occurrence occ : kw1Occs){
            //insert the keys occurrence at the end of the list to be picked up by the ILO method
            merge.add(occ);
            //call the method to insert
            insertLastOccurrence(merge);
        }


        //add top 5 to the list
        int cnt = 0;
        for(Occurrence occ : merge){
            //stop at 5
            if(cnt >= 5){
                break;
            }
            //add the doc name if its not in the list already
            if (!docHits.contains(occ.document)) {
                docHits.add(occ.document);
            }
            cnt++;
        }

        return docHits;
	}

}
