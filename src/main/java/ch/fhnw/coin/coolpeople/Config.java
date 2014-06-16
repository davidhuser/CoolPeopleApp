package ch.fhnw.coin.coolpeople;

import java.util.Arrays;
import java.util.List;
/**
 * Configuration class for managing input and output files
 *
 * @author Igor Bosnjak
 * @author David Huser
 */
class Config {

    //Path for app
    public static final String APP_DIR = "/Users/david/git/CoolPeopleApp/";

    //Project path
    public static final String PROJECT_DIR = APP_DIR + "examples/10_richest_people_2014/";

    //Names of input files
    public static final List<String> INPUT = Arrays.asList(
            PROJECT_DIR + "billgates.txt",
            PROJECT_DIR + "amancioortega.txt",
            PROJECT_DIR + "carlosslim.txt",
            PROJECT_DIR + "charleskoch.txt",
            PROJECT_DIR + "christywalton.txt",
            PROJECT_DIR + "davidkoch.txt",
            PROJECT_DIR + "jimwalton.txt",
            PROJECT_DIR + "larryellison.txt",
            PROJECT_DIR + "sheldonadelson.txt",
            PROJECT_DIR + "warrenbuffett.txt"
            );

    //Output file declaration. Keep extension: .gexf
    public static final String OUTPUTFILE = PROJECT_DIR + "export.gexf";

}
