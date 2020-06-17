import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String filePath = "/Users/OlivierLabarthe/SynologyDrive/Personnel/CNAM/L3_S2/JAVA_NFA032/Projet/Edit_PPM_File/fichiers_ppm/exemple3.ppm";
        String filePathOut = "/Users/OlivierLabarthe/SynologyDrive/Personnel/CNAM/L3_S2/JAVA_NFA032/Projet/Edit_PPM_File/fichiers_ppm/test_cut.ppm";

        //File ppmImg = new File(filePath);

        EditFile ppmImg = new EditFile(filePath);

        //ppmImg.lightenDarken(100);
        //ppmImg.greyScale();

        //ppmImg.cutOffImg(50,25 , 100, 100);
        //ppmImg.getNegative();
        //ppmImg.enlargeFile(3);
        ppmImg.fileEnlargement(2);

        ppmImg.writeFile(filePathOut);

    }
}
