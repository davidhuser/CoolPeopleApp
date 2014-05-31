package ch.fhnw.coin.coolpeople;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.*;
import java.util.ArrayList;

/**
 * ch.fhnw.coin.coolpeople.NameExtractor is the class which tries to extract Names with two different approaches from an input String.
 *
 * @author      Igor Bosnjak
 * @author      David Huser
 */
public class NameExtractor {

    private static final String BASE_DIR = "/Users/david/Desktop/";

    private ArrayList<Person> personPerDocument = new ArrayList<Person>();

    public NameExtractor(Document doc) {
        try {
            String docContent = doc.getContent();
            extractNames(docContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts names with Apache openNLP
     * <code>https://opennlp.apache.org/</code>
     * <code>http://opennlp.sourceforge.net/models-1.5/</code>
     *
     * @param documentStr The String to extract names
     */
    private void extractNames(String documentStr) throws IOException {
        System.out.println("EXTRACTING NAMES...\n");
        InputStream is1 = null, is2 = null, is3 = null;
        try {
            is1 = new FileInputStream(BASE_DIR + "en-token.bin");
            TokenizerModel tModel = new TokenizerModel(is1);
            Tokenizer tokenizer = new TokenizerME(tModel);

            is2 = new FileInputStream(BASE_DIR + "en-sent.bin");
            SentenceModel sModel = new SentenceModel(is2);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sModel);

            is3 = new FileInputStream(BASE_DIR + "en-ner-person.bin");
            TokenNameFinderModel nModel = new TokenNameFinderModel(is3);

            NameFinderME nameFinder = new NameFinderME(nModel);

            for (String sentence : sentenceDetector.sentDetect(documentStr)) {
                String tokens[] = tokenizer.tokenize(sentence);

                //check against whitelist
                checkAgainstList(tokens);

                Span nameSpans[] = nameFinder.find(tokens);

                for (Span name : nameSpans) {
                    Person person;
                    int len = name.getEnd() - name.getStart();
                    if (len > 2) {
                        // CASE 1 - more than 3 names

						/*
                         * eg. someones name is John Michael Dorian
						 * then the prename is "John Michael"
						 * and the lastname is "Dorian"
						 * that's why we need a StringBuilder
						 */
                        StringBuilder sb = new StringBuilder();
                        int i;
                        for (i = name.getStart(); i < name.getEnd() - 2; ++i) {
                            sb.append(tokens[i] + " ");
                        }
                        sb.append(tokens[i++]);

                        String prename = sb.toString();
                        String lastname = tokens[i];

                        person = new Person(prename, lastname);

                    } else if (len == 2) {
                        // CASE 2 - 2 names
                        String prename = tokens[name.getStart()];
                        String lastname = tokens[name.getEnd() - 1];
                        person = new Person(prename, lastname);

                    } else {
                        // CASE 3 - only one name
                        String token = tokens[name.getStart()];
                        if (hasPersonWithName(token)) {
                            /*
                             * eg. We found a sentence containing the name (token) Dorian.
							 * This may be John Michael Dorian (or Dan Dorian), so if we find a person called Dorian in our ArrayList, we skip this one.
							 * If no ch.fhnw.coin.coolpeople.Person with this name is in the ArrayList, we add it to the list.
							 * NOTE: name will be used as lastname!
							 */
                            continue;
                        }
                        person = new Person("", token);
                    }

                    if (!isInPersonList(person)) {
                        personPerDocument.add(person);
                    }
                }
            }

        } finally {
            if (is1 != null) try {
                is1.close();
            } catch (IOException e) { /**/ }
            if (is2 != null) try {
                is2.close();
            } catch (IOException e) { /**/ }
            if (is3 != null) try {
                is3.close();
            } catch (IOException e) { /**/ }
        }
    }

    /**
     * Getter method for ArrayList personPerDocument
     *
     * @return ArrayList<ch.fhnw.coin.coolpeople.Person> The ch.fhnw.coin.coolpeople.Person Data Structure
     * @see class ch.fhnw.coin.coolpeople.Person
     */
    public ArrayList<Person> returnPersonArray() {
        return personPerDocument;
    }

    /**
     * Checks if a persons already exists in the PersonsList.
     *
     * @param p ch.fhnw.coin.coolpeople.Person to check
     * @return boolean
     */
    private boolean isInPersonList(Person p) {
        for (Person q : personPerDocument) {
            if (!q.getPrename().equals(p.getPrename())) continue;
            if (!q.getLastname().equals(p.getLastname())) continue;
            return true;
        }
        return false;
    }

    /**
     * Tries to find a ch.fhnw.coin.coolpeople.Person with 'name' in its prename or lastname
     *
     * @param name to find a ch.fhnw.coin.coolpeople.Person with this name
     */
    private boolean hasPersonWithName(String name) {
        for (Person p : personPerDocument) {
            if (p.getPrename().equals(name)) return true;
            if (p.getLastname().equals(name)) return true;
        }
        return false;
    }

    /**
     * Checks if a token is recognized with a whitelist.
     * If yes, add a new ch.fhnw.coin.coolpeople.Person with the following String as a person.
     *
     * @param tokens Token Array, basically splitted sentences
     */
    private void checkAgainstList(String[] tokens) throws IOException {
        BufferedReader bufReader = null;

        try {
            bufReader = new BufferedReader(new FileReader(BASE_DIR + "person_first_name.lst"));

            String line;
            Person person;

            while ((line = bufReader.readLine()) != null) {
                for (int i = 0; i < tokens.length; ++i) {
                    //check if token is in list
                    if (tokens[i].equalsIgnoreCase(line)) {
                        //check if token and the following token are Uppercase
                        if(Character.isUpperCase(tokens[i].charAt(0)) & Character.isUpperCase(tokens[i + 1].charAt(0))) {
                            //add token and the following token as a new person
                            person = new Person(tokens[i], tokens[i + 1]);
                            if (!isInPersonList(person)) {
                                personPerDocument.add(person);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReader != null) bufReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}