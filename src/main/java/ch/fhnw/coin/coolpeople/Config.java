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
    public static final String APP_DIR = "/Users/igorbosnjak/FHNW/8. Semester/cose/Workspace/CoolPeopleApp/";

    //Project path
    public static final String PROJECT_DIR = APP_DIR + "examples/10_richest_people_2014/";

    //Names of input files
    public static final List<String> INPUT = Arrays.asList(
            PROJECT_DIR + "larryellison.txt",
            PROJECT_DIR + "sheldonadelson.txt"
    );

    //Output file declaration. Keep extension: .gexf
    public static final String OUTPUTFILE = "/Users/igorbosnjak/Desktop/CoolPeopleApp_export.gexf";

}
