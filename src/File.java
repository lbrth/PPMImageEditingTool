import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class File {

    private char bufferedFile;
    private int bytesBufferedFile;
    private String filePath;
    FileInputStream file;
    PixMap pixMap = new PixMap();


    public int ppmFileLenghtImage;
    public int ppmFileHeightImage;
    public String ppmFileTitleImage = "";
    public int maximumColourScale;
    public String maximumColourScaleAsString = "";
    public String pixSeq = "";



    public File(String filePath) {

        String ppmFileLenghtString = "";
        String ppmFileHeightString = "";
        String sizeMetadata = "";


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
    }


    public void readBufferedFile() throws IOException {
        this.bytesBufferedFile = this.file.read();
        int line = 0;

        while (bytesBufferedFile != -1) {
            this.bufferedFile = (char)(this.bytesBufferedFile);
            if(bufferedFile == '\n') line++;
            if(line >= 4) {
                System.out.print(bufferedFile);
            }
            this.bytesBufferedFile = this.file.read();
        }
        //this.file.close();
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


}

class EditFile extends File {


    FileOutputStream file;

    String fileData = "P3"+ "\n" +  super.ppmFileTitleImage.trim() + "\n" ;

    public EditFile(String filePath) throws IOException {
        super(filePath);
        loadBufferedInPixMap();

    }

    public void writeFile(String filePath) {
        this.fileData = this.fileData + super.ppmFileLenghtImage+ " " + super.ppmFileHeightImage + "\n"  + super.maximumColourScaleAsString + "\n" + super.pixMap.getPixMapAsString(super.ppmFileLenghtImage, super.ppmFileHeightImage);

        try {
            this.file = new FileOutputStream(filePath);
            for(int i = 0; i < fileData.length()-1;i++) {
                System.out.print(fileData.charAt(i));
                this.file.write(fileData.charAt(i));
            }
            this.file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void lightenDarken(int valueToAdd) {
        super.pixMap.updateDominantColors("red", valueToAdd, super.maximumColourScale);
        super.pixMap.updateDominantColors("green", valueToAdd, super.maximumColourScale);
        super.pixMap.updateDominantColors("blue", valueToAdd, super.maximumColourScale);
    }

    public void lightenDarken(String colorName, int valueToAdd) {
        super.pixMap.updateDominantColors(colorName, valueToAdd, super.maximumColourScale);
    }


    public void greyScale() {
        super.pixMap.updateColorsWithAvg();
    }

    public void cutOffImg(int fromPixLine, int toPixLine) {
        super.pixMap = super.pixMap.cutOffPixMap(new PixMap(),fromPixLine, toPixLine, super.ppmFileLenghtImage);
        super.ppmFileHeightImage = toPixLine - fromPixLine +1;
    }
    public void cutOffImg(int fromPixLine, int fromColumn, int toPixLine, int toColumn) {
        super.pixMap = super.pixMap.cutOffPixMap(new PixMap(),fromPixLine, fromColumn, toPixLine, toColumn,  super.ppmFileLenghtImage);
        super.ppmFileHeightImage = toPixLine - fromPixLine +1;
        super.ppmFileLenghtImage = toColumn - fromColumn +1;
    }

    public void getNegative() {
        super.pixMap.getNegative(super.maximumColourScale);
    }

    public void enlargeFile(int xN) {
        for(int i = 0; i <= xN-1; i++) {
            super.pixMap.enlargePixMap();
            super.ppmFileLenghtImage = super.ppmFileLenghtImage*2;
        }
    }

    public void fileEnlargement(int xN) {
        for(int i = 0; i <= xN-1; i++) {
            super.pixMap = super.pixMap.pixMapEnlargement(new PixMap(), super.ppmFileLenghtImage, super.ppmFileHeightImage,  super.ppmFileHeightImage*2);
            super.ppmFileLenghtImage = super.ppmFileLenghtImage*2;
            super.ppmFileHeightImage = super.ppmFileHeightImage*2;
        }

    }
    

}

