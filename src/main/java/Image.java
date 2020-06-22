
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



class Image {

    private char bufferedFile;
    private int bytesBufferedFile;
    private String filePath;
    private String filePathOut = "";
    private FileOutputStream fileOut;
    private FileInputStream file;
    private PixMap pixMap = new PixMap();
    private String pixSeq = "";
    private String ppmFileTitleImage = "";
    private int ppmFileLenghtImage;
    private int ppmFileHeightImage;
    private String ppmFileLenghtString = "";
    private String ppmFileHeightString = "";
    private int maximumColourScale;
    private String maximumColourScaleAsString = "";
    private String sizeMetadata = "";
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

    public void displayImageMetadata() {
        System.out.println(" Titre de l'image : " + ppmFileTitleImage);
        System.out.println(" hauteur de l'image : " + ppmFileHeightString);
        System.out.println(" Longueur de l'image : " + ppmFileLenghtString);
        System.out.println("Echelle de couleur maximal : " + maximumColourScaleAsString);
    }

    public int getPpmFileLenghtImage() {
        return ppmFileLenghtImage;
    }

    public int getPpmFileHeightImage() {
        return ppmFileHeightImage;
    }

    public int getMaximumColourScale() {
        return maximumColourScale;
    }

    public String getPpmFileTitleImage() {
        return ppmFileTitleImage;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getFilePathOut() {
        return this.filePathOut;
    }

    public void writeFile(String filePathOut) {
        this.fileData = this.fileData + ppmFileLenghtImage+ " " + ppmFileHeightImage + "\n"  + maximumColourScaleAsString + "\n" + pixMap.getPixMapAsString(ppmFileLenghtImage, ppmFileHeightImage);
        //check PPM Extension


        try {
            this.fileOut = new FileOutputStream(filePathOut);
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

    public void getBlackAndWhite() {
        pixMap.updateColorsWithAvg();
    }

    public void cutOffImg(int fromPixLine, int toPixLine) {

        if(fromPixLine > ppmFileHeightImage || toPixLine > ppmFileHeightImage || fromPixLine < toPixLine) {
            throw new incorrectLineOrColumnError();
        }
        this.pixMap = pixMap.cutOffPixMap(new PixMap(),fromPixLine, toPixLine, ppmFileLenghtImage);
        this.ppmFileHeightImage = toPixLine - fromPixLine +1;
    }

    public void cutOffImg(int fromPixLine, int fromColumn, int toPixLine, int toColumn) {
        if(fromPixLine > ppmFileHeightImage || toPixLine > ppmFileHeightImage || fromPixLine > toPixLine || fromColumn > ppmFileLenghtImage || toColumn > ppmFileLenghtImage || fromColumn > toColumn ) {
            throw new incorrectLineOrColumnError();
        }
        this.pixMap = pixMap.cutOffPixMap(new PixMap(),fromPixLine, fromColumn, toPixLine, toColumn,  ppmFileLenghtImage);
        this.ppmFileHeightImage = toPixLine - fromPixLine +1;
        this.ppmFileLenghtImage = toColumn - fromColumn +1;
    }

    public void getNegative() {
        pixMap.getNegative(maximumColourScale);
    }

    public void enlargeFile(int xN) {
        for(int i = 0; i <= xN-1; i++) {
            this.pixMap.enlargePixMap();
            this.ppmFileLenghtImage = ppmFileLenghtImage*2;
        }
    }

    public void fileEnlargement(int xN) {
        for(int i = 0; i <= xN-1; i++) {
            this.pixMap = pixMap.pixMapEnlargement(new PixMap(), ppmFileLenghtImage, ppmFileHeightImage,  ppmFileHeightImage*2);
            this.ppmFileLenghtImage = ppmFileLenghtImage*2;
            this.ppmFileHeightImage = ppmFileHeightImage*2;
        }
    }

}

class incorrectLineOrColumnError extends Error {
    incorrectLineOrColumnError() {
        System.out.print("Incorrect line");
    }
}


