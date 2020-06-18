import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class Image {

    private char bufferedFile;
    private int bytesBufferedFile;
    private String filePath;
    private FileOutputStream fileOut;
    private FileInputStream file;


    private PixMap pixMap = new PixMap();
    private String pixSeq = "";

    private String ppmFileTitleImage = "";
    private int ppmFileLenghtImage;
    private int ppmFileHeightImage;
    String ppmFileLenghtString = "";
    String ppmFileHeightString = "";
    private int maximumColourScale;
    private String maximumColourScaleAsString = "";


    String sizeMetadata = "";
    private String fileData = "P3"+ "\n" + ppmFileTitleImage.trim() + "\n" ;



    public Image(String filePath) throws IOException {

        this.filePath = filePath;
        try {
            this.file = new FileInputStream(this.filePath);
            this.bytesBufferedFile = this.file.read();
            int line = 0;

            while (bytesBufferedFile != -1) {
                this.bufferedFile = (char)(this.bytesBufferedFile);
                System.out.print(bufferedFile);

                if(bufferedFile == '\n') line++;
                if(line == 1) {
                    this.ppmFileTitleImage +=  Character.toString(bufferedFile);
                }
                if(line == 2) {
                    if(bufferedFile != '\n')sizeMetadata += Character.toString(bufferedFile);
                }
                if(line == 3){
                    if(bufferedFile != '\n')this.maximumColourScaleAsString += Character.toString(bufferedFile);
                }
                if(line >= 4) {
                    if(bufferedFile != '\n')this.pixSeq += Character.toString(bufferedFile);
                }
                this.bytesBufferedFile = this.file.read();
            }
            this.file.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found at this path : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        int i = 0;
        for(; i <= sizeMetadata.length()-1;) {

            if(sizeMetadata.charAt(i) == ' ')break;
            ppmFileLenghtString += sizeMetadata.charAt(i);
            i++;
        }
        this.ppmFileLenghtImage= Integer.parseInt(ppmFileLenghtString);

        for(i = i; i <= sizeMetadata.length()-1;i++) {
            if(sizeMetadata.charAt(i) == ' ')continue;
            ppmFileHeightString += sizeMetadata.charAt(i);
        }
        this.ppmFileHeightImage = Integer.parseInt(ppmFileHeightString);
        this.maximumColourScale = Integer.parseInt(maximumColourScaleAsString);

        loadBufferedInPixMap();
    }

    public void loadBufferedInPixMap() throws IOException {

        String redPix = "";
        String greenPix = "";
        String bluePix = "";

        String pixSeqNoSpace = pixSeq.replaceAll("[\n]+", "");

        int i = 0;
        while(i < pixSeqNoSpace.length()-1) {

            while (pixSeqNoSpace.charAt(i) == ' '){
                i++;
            }
            for(int j = 0;j <= this.maximumColourScaleAsString.length()-1;) {
                if(pixSeqNoSpace.charAt(i) == ' '){
                    break;
                }  else {
                    redPix += pixSeqNoSpace.charAt(i);
                    i++;
                }
                j++;

            }
            while (pixSeqNoSpace.charAt(i) == ' '){
                i++;
            }
            for(int j = 0;j <= this.maximumColourScaleAsString.length()-1;) {
                if(pixSeqNoSpace.charAt(i) == ' ' ){
                    break;
                } else {
                    greenPix += pixSeqNoSpace.charAt(i);
                    i++;
                }
                j++;

            }
            while (pixSeqNoSpace.charAt(i) == ' '){
                i++;
            }
            for(int j = 0;j <= this.maximumColourScaleAsString.length()-1;) {
                if(pixSeqNoSpace.charAt(i) == ' ' ){
                    break;
                } else {
                    bluePix += pixSeqNoSpace.charAt(i);
                    i++;
                }
                j++;
            }
            while (pixSeqNoSpace.charAt(i) == ' ' ){
                if(i ==pixSeqNoSpace.length()-1 ){
                    break;
                }
                i++;
            }
            this.pixMap.addPixAtLast(Integer.parseInt(redPix), Integer.parseInt(greenPix), Integer.parseInt(bluePix));
            redPix = "";
            greenPix = "";
            bluePix = "";
        }
    }

    public void getImageMetadata() {

        System.out.println(" Titre de l'image : " + ppmFileTitleImage);
        System.out.println(" hauteur de l'image : " + ppmFileHeightString);
        System.out.println(" Longueur de l'image : " + ppmFileLenghtString);
        System.out.println("Echel");
    }

    public void writeFile(String filePath) {
        this.fileData = this.fileData + ppmFileLenghtImage+ " " + ppmFileHeightImage + "\n"  + maximumColourScaleAsString + "\n" + pixMap.getPixMapAsString(ppmFileLenghtImage, ppmFileHeightImage);

        try {
            this.fileOut = new FileOutputStream(filePath);
            for(int i = 0; i < fileData.length()-1;i++) {
                System.out.print(fileData.charAt(i));
                this.fileOut.write(fileData.charAt(i));
            }
            this.fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lightenDarken(int valueToAdd) {

        pixMap.updateDominantColors("red", valueToAdd, maximumColourScale);
     pixMap.updateDominantColors("green", valueToAdd, maximumColourScale);
     pixMap.updateDominantColors("blue", valueToAdd, maximumColourScale);
    }

    public void lightenDarken(String colorName, int valueToAdd) {
        pixMap.updateDominantColors(colorName, valueToAdd, maximumColourScale);
    }

    public void greyScale() {
        pixMap.updateColorsWithAvg();
    }

    public void cutOffImg(int fromPixLine, int toPixLine) {

        pixMap = pixMap.cutOffPixMap(new PixMap(),fromPixLine, toPixLine, ppmFileLenghtImage);
     ppmFileHeightImage = toPixLine - fromPixLine +1;
    }

    public void cutOffImg(int fromPixLine, int fromColumn, int toPixLine, int toColumn) {

        pixMap = pixMap.cutOffPixMap(new PixMap(),fromPixLine, fromColumn, toPixLine, toColumn,  ppmFileLenghtImage);
     ppmFileHeightImage = toPixLine - fromPixLine +1;
     ppmFileLenghtImage = toColumn - fromColumn +1;
    }

    public void getNegative() {
        pixMap.getNegative(maximumColourScale);
    }

    public void enlargeFile(int xN) {
        for(int i = 0; i <= xN-1; i++) { pixMap.enlargePixMap(); ppmFileLenghtImage = ppmFileLenghtImage*2;
        }
    }

    public void fileEnlargement(int xN) {
        for(int i = 0; i <= xN-1; i++) {

            pixMap = pixMap.pixMapEnlargement(new PixMap(), ppmFileLenghtImage, ppmFileHeightImage,  ppmFileHeightImage*2); ppmFileLenghtImage = ppmFileLenghtImage*2; ppmFileHeightImage = ppmFileHeightImage*2;
        }

    }

}






}

