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
    private static final String APP_DIR = "/Users/david/git/CoolPeopleApp/";

    //Project path
    public static final String PROJECT_DIR = APP_DIR + "examples/10_richest_people_2014/";

    //Names of input files
    public static final List<String> TEXTFILES = Arrays.asList(

    );

    public static final List<String> URLS = Arrays.asList(
            "https://en.wikipedia.org/wiki/Communist_Party_of_China",
            "https://en.wikipedia.org/wiki/Xi_Jinping"
    );

    //Output file declaration. Keep extension: .gexf
    public static final String OUTPUTFILE = "/Users/david/Desktop/CoolPeopleApp_export.gexf";

}
