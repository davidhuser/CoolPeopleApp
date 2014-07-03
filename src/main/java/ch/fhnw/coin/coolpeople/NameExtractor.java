package ch.fhnw.coin.coolpeople;

import it.uniroma1.dis.wsngroup.gexf4j.core.Graph;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * NameExtractor is the class which tries to extract Names with two different approaches from an input String.
 *
 * @author Igor Bosnjak
 * @author David Huser
 */
public class NameExtractor {

    private final ArrayList<Person> personPerDocument = new ArrayList<Person>();
    private final ArrayList<Node> nodePerDocument = new ArrayList<Node>();
    private static Graph graph;
    private final HashMap<Person, Node> nodemap = new HashMap<Person, Node>();

    /**
     * Constructor for NameExtractor
     *
     * @param in Input
     * @param g Graph
     */
    public NameExtractor(Input in, Graph g) {
        graph = g;
        try {
            extractNames(in.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts names with Apache openNLP models and list of prenames
     *
     *
     * @param documentStr The String to extract names
     */
    private void extractNames(String documentStr) throws IOException {
        System.out.println("EXTRACTING NAMES...\n");
        InputStream is1 = null, is2 = null, is3 = null;
        try {
            is1 = getClass().getResourceAsStream("/en-token.bin");
            TokenizerModel tModel = new TokenizerModel(is1);
            Tokenizer tokenizer = new TokenizerME(tModel);

            is2 = getClass().getResourceAsStream("/en-sent.bin");
            SentenceModel sModel = new SentenceModel(is2);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sModel);

            is3 = getClass().getResourceAsStream("/en-ner-person.bin");
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
     * @return ArrayList<Person> The Person data structure
     * @see class Person
     */
    public ArrayList<Person> returnPersonArray() {
        return personPerDocument;
    }

    /**
     * Getter method for HashMap of Persons and casted Nodes of Persons
     *
     *
     * @return HashMap<Person,Node> Map of Person to Nodes
     */
    public HashMap<Person, Node> getNodemap() {
        return nodemap;
    }

    /**
     * Cast Persons to Nodes to be more compatbile with GEXF library and initialize nodes with attributes.
     * Hashcode of the Map is used as UUID.
     *
     * @param al ArrayList<Person> ArrayList of Persons
     * @return ArrayList<Node> ArrayList of casted Nodes
     */
    public ArrayList<Node> castPersonsToNodes(ArrayList<Person> al) {

        for (Person p : al) {
            Node node = nodemap.get(p);
            if (node == null) {
                node = graph.createNode(Integer.toString(p.hashCode()));
                node.setLabel(p.getPrename() + " " + p.getLastname());
                nodemap.put(p, node);
            }
            nodePerDocument.add(node);
        }

        return nodePerDocument;
    }

    /**
     * Checks if a Person already exists in the PersonsList.
     *
     * @param p Person to check if already in personlist
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
     * Tries to find a Person with 'name' in its prename or lastname
     *
     * @param name to find a ch.fhnw.coin.coolpeople.Person with this name
     * @return boolean
     */
    private boolean hasPersonWithName(String name) {
        for (Person p : personPerDocument) {
            if (p.getPrename().equals(name)) return true;
            if (p.getLastname().equals(name)) return true;
        }
        return false;
    }

    /**
     * Checks if a token is recognized within a whitelist.
     * If yes, add a new Person with the following String as a person.
     *
     * @param tokens Token Array, basically splitted sentences of input String
     */
    private void checkAgainstList(String[] tokens) {
        BufferedReader bufReader = null;
        try {
            bufReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/person_first_name.lst")));

            String line;
            Person person;

            while ((line = bufReader.readLine()) != null) {
                for (int i = 0; i < tokens.length; ++i) {
                    //check if token is in list
                    if (tokens[i].equalsIgnoreCase(line)) {
                        //check if token and the following token are Uppercase
                        if (Character.isUpperCase(tokens[i].charAt(0)) & Character.isUpperCase(tokens[i + 1].charAt(0))) {
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