import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {

    private Image [] images = new Image[10];
    private int nbrImages = 0;


    public void loadImage(String path) throws IOException {
        for(int i = 0; i <= images.length-1;i++) {
            if(this.images[i] == null) {
                this.images[i] = new Image(path);
                break;
            }
        }

        for(int i = 0; i <= images.length-1;i++) {
            if(this.images[i] != null) {
                this.nbrImages += 1;
            }
        }
    }

    public void displayImagesLoaded() {
        for(int i = 0; i <= images.length-1;i++) {
            if(this.images[i] == null) {
                continue;
            } else {
                System.out.println("Image " + i + " : " + images[i].getPpmFileTitleImage());
                System.out.println("Metadata Image " + i + " : ");
                images[i].displayImageMetadata();
            }
        }
    }

    public int getNumberImageLoaded() {
        return nbrImages;
    }

    public void displayMenuOptions() {
        System.out.println("**** Editing Features ****");
        System.out.println(" 1 - Get the Length and the Height of the image");
        System.out.println(" 2 - Get the maximum color scale");
        System.out.println(" 3 - Darken or Lighten all the image");
        System.out.println(" 4 - Darken or Lighten a specific color ");
        System.out.println(" 5 - Transform the image into black and white");
        System.out.println(" 6 - Crop the image using line");
        System.out.println(" 7 - Crop the image using line and column");
        System.out.println(" 8 - Get the negative of the image");
        System.out.println(" 9 - Enlarge the Image");
        System.out.println(" 10 - Image enlargement");
        System.out.println(" b - Back to Main Menu ");
    }

    public void editImage(int imageIndex, int editingOption) {

        if(imageIndex > images.length || images[imageIndex] == null) {
            throw new wrongImageIndex(imageIndex);
        } else if(editingOption == 1) {
            System.out.println("Length : " + images[imageIndex].getPpmFileLenghtImage());
            System.out.println("Heigh : " + images[imageIndex].getPpmFileHeightImage());
        } else if(editingOption == 2) {
            System.out.println("Maximum color scale : " + images[imageIndex].getMaximumColourScale());
        } else if(editingOption == 3) {
            System.out.println("Please set a value to lighten or darken all the image colors");
            int value = sc.nextInt();
            System.out.println("Let's go. It may take a while");
            images[imageIndex].lightenDarken(value);
            System.out.println("It's done, please save it to check the result.");
        } else if(editingOption == 4) {
            System.out.println("Please set a value and the color, to lighten or darken it ");
            System.out.println("Colors choice : Red, Green, Blue : ");
            String selectedColor = sc.nextLine();
            System.out.println("Set a value : ");
            int value = sc.nextInt();
            System.out.println("Let's go. It may take a while");
            images[imageIndex].lightenDarken(selectedColor, value);
            System.out.println("It's done, please save it to check the result.");
        } else if(editingOption == 5) {
            System.out.println("Let's go. It may take a while");
            images[imageIndex].getBlackAndWhite();
            System.out.println("It's done, please save it to check the result.");
        } else if(editingOption == 6) {
            System.out.println("Please set from what line you want to cut and to what line ");
            System.out.println("Maximum number of lines accepted  " + images[imageIndex].getPpmFileHeightImage());
            System.out.println("From Line : ");
            int fromLine = sc.nextInt();
            System.out.println("To Line : ");
            int toLine = sc.nextInt();
            System.out.println("Let's go. It may take a while");
            images[imageIndex].cutOffImg(fromLine, toLine);
            System.out.println("It's done, please save it to check the result.");
        } else if(editingOption == 7) {
            System.out.println("Please set from what line you want to cut and to what line, same for column ");
            System.out.println("Maximum number of lines accepted  : " + images[imageIndex].getPpmFileHeightImage());
            System.out.println("Maximum number of columns accepted  : " + images[imageIndex].getPpmFileLenghtImage());
            System.out.println("From Line : ");
            int fromLine = sc.nextInt();
            System.out.println("To Line : ");
            int toLine = sc.nextInt();
            System.out.println("From Column : ");
            int fromColumn = sc.nextInt();
            System.out.println("To Column : ");
            int toColumn = sc.nextInt();
            System.out.println("Let's go. It may take a while");
            images[imageIndex].cutOffImg(fromLine, fromColumn,toLine , toColumn);
            System.out.println("It's done, please save it to check the result.");
        } else if(editingOption == 8) {
            System.out.println("Let's go. It may take a while");
            images[imageIndex].getNegative();
            System.out.println("It's done, please save it to check the result.");
        } else if(editingOption == 9) {
            System.out.println("Please set a value of how many time you want to enlarge the image, horizontally.");
            int enlargeHorizontallyXZoom = sc.nextInt();
            System.out.println("Let's go. It may take a while");
            images[imageIndex].enlargeFile(enlargeHorizontallyXZoom);
            System.out.println("It's done, please save it to check the result.");
        } else if (editingOption == 10) {
            System.out.println("Please set a value of how many time you want to enlarge the entire image.");
            int enlargeXZoom = sc.nextInt();
            System.out.println("Let's go. It may take a while");
            images[imageIndex].fileEnlargement(enlargeXZoom);
            System.out.println("It's done, please save it to check the result.");
        } else {
            throw new wrongEditingOption(editingOption);
        }
    }

    public void displayMainMenu() throws IOException {
        System.out.println("Main Menu : ");
        System.out.println(" 1 - Load Image");
        System.out.println(" 2 - Edit Image");
        System.out.println(" 3 - Save Image");
        System.out.println(" 4 - Display Images Info");
        System.out.println(" q - Quit ");


        int userSelection = 0;
        boolean retry;
        char userChoiceToSaveImage = 'y';

        do {
            try {
                System.out.println("Please select one of the options : ");
                userSelection = sc.nextInt();
                retry = false;

            } catch (InputMismatchException e) {
                System.out.println("ERROR : Incorrect input. An integer is wainting here.");
                retry = true;
                sc.nextLine();
            }
        } while (retry);


        if(userSelection == 1) {

            sc.nextLine();
            char userChoiceToLoadNewImage = ' ';
            do {
            System.out.println("Please set the image path system : ");

                do {
                    try {
                        String imagePath = sc.nextLine();
                        loadImage(imagePath);
                        retry = false;
                    } catch (NumberFormatException e) {
                        System.out.println("Please retry. Set the image path system : ");
                        retry = true;
                    } catch (InputMismatchException e) {
                        System.out.println("ERROR : Incorrect input. An integer is wainting here.");
                        retry = true;
                        sc.nextLine();
                    }
                } while (retry);
                userChoiceToLoadNewImage = ' ';
                while (userChoiceToLoadNewImage != 'y' && userChoiceToLoadNewImage != 'n' ) {
                    System.out.println("Do you want to load an other image ? y/n : ");
                    userChoiceToLoadNewImage = sc.nextLine().charAt(0);
                }
            } while (userChoiceToLoadNewImage != 'n');
            displayMainMenu();

        } else if (userSelection == 2) {
            displayMenuOptions();

            int indexImage = 0;
            int featureSelected = 0;
            do {
                try {
                    System.out.println("Please select the image index that you want to edit : ");
                    indexImage = sc.nextInt();
                    System.out.println("Please select editing feature to your image :  ");
                    featureSelected = sc.nextInt();
                    editImage(indexImage, featureSelected);
                    retry = false;
                } catch (wrongImageIndex e) {
                    retry = true;
                } catch (wrongEditingOption e) {
                    retry = true;
                } catch (InputMismatchException e) {
                    System.out.println("ERROR : Incorrect input. An integer is wainting here.");
                    retry = true;
                    sc.nextLine();
                }

            } while (retry);
            
            displayMainMenu();
        } else if (userSelection == 3) {

            do {
                try {
                    System.out.println("Please select the image you want to save : ");
                    displayImagesLoaded();
                    do {
                        System.out.println("Select the index image : ");
                        int indexImageToSave = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Set the path to save : ");
                        String filePathOut = sc.nextLine();
                        saveImage(indexImageToSave, filePathOut);
                        System.out.println("Do you want to save an other image ? y/n : ");
                        userChoiceToSaveImage = sc.nextLine().charAt(0);
                    } while (userChoiceToSaveImage != 'n');
                } catch (wrongImageIndex e ) {
                    retry = true;
                } catch (InputMismatchException e) {
                    System.out.println("ERROR : Incorrect input. An integer is wainting here.");
                    retry = true;
                    sc.nextLine();
                }
            } while (retry);




            displayMainMenu();


        } else if (userSelection == 4) {
            displayImagesLoaded();
            displayMainMenu();
        } else {
            throw new wrongMainMenuSelected();
        }
    }

    public void saveImage(int imageIndex, String filePathOut) {
        if(imageIndex > images.length || images[imageIndex] == null) {
            throw new wrongImageIndex(imageIndex);
        } else {
            images[imageIndex].writeFile(filePathOut);
            System.out.println("Your image is correctly save at " + images[imageIndex].getFilePathOut());
        }
    }

    static Scanner sc = new Scanner(System.in);

}

class wrongImageIndex extends Error {
    wrongImageIndex(int i) {
        System.out.println("ERROR : " + i + ", doesn't match any images");
    }
}

class wrongEditingOption extends Error {
    wrongEditingOption(int i ) {
        System.out.println("ERROR : " + i + ", doesn't match any editing feature");
    }
}

class wrongMainMenuSelected extends Error {
    wrongMainMenuSelected() {
        System.out.println("Not an option");
    }
}


