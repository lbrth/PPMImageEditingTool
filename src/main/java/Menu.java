import java.awt.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;




public class Menu {

    private Image [] images = new Image[10];
    private int nbrImages = 0;

    public void loadImage(String path) throws IOException {
        nbrImages = 0;
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
        if(nbrImages == 0) {
            throw new NoImageLoaded();
        }
        for(int i = 0; i <= images.length-1;i++) {
            if(this.images[i] == null) {
                continue;
            } else {
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

    public void editImage(int imageIndex, String editingOption) throws IOException {

        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            boolean retry = false;

            if(imageIndex > images.length || images[imageIndex] == null) {
                throw new wrongImageIndex(imageIndex);
            } else if(editingOption.equalsIgnoreCase("1")) {
                System.out.println("Length : " + images[imageIndex].getPpmFileLenghtImage());
                System.out.println("Heigh : " + images[imageIndex].getPpmFileHeightImage());
                backToMainMenu = true;
            } else if(editingOption.equalsIgnoreCase("2")) {
                System.out.println("Maximum color scale : " + images[imageIndex].getMaximumColourScale());
                backToMainMenu = true;
            } else if(editingOption.equalsIgnoreCase("3")) {
                System.out.println("Please set a value to lighten or darken all the image colors");
                int value = sc.nextInt();
                System.out.println("Loading ...");
                images[imageIndex].lightenDarken(value);
                System.out.println("It's done, please save it to check the result.");
                backToMainMenu = true;
            } else if(editingOption.equalsIgnoreCase("4")) {
                String selectedColor = "";
                System.out.println("Please set a value and the color, to lighten or darken it ");
                do {
                    try {
                        System.out.println("Colors choice : Red, Green or Blue : ");
                        selectedColor = sc.nextLine();
                        System.out.println("Set a value : ");
                        int value = sc.nextInt();
                        System.out.println("Loading ...");
                        images[imageIndex].lightenDarken(selectedColor, value);
                        retry = false;
                    } catch (wrongColorName e) {
                        retry = true;
                    }
                } while (retry);
                System.out.println("It's done, please save it to check the result.");
                backToMainMenu = true;
            } else if(editingOption.equalsIgnoreCase("5")) {
                System.out.println("Loading ...");
                images[imageIndex].getBlackAndWhite();
                System.out.println("It's done, please save it to check the result.");
                backToMainMenu = true;
            } else if(editingOption.equalsIgnoreCase("6")) {
                System.out.println("Please set from what line you want to cut and to what line ");
                System.out.println("Maximum number of lines accepted  " + images[imageIndex].getPpmFileHeightImage());
                System.out.println("From Line : ");
                int fromLine = sc.nextInt();
                System.out.println("To Line : ");
                int toLine = sc.nextInt();
                System.out.println("Loading ...");
                images[imageIndex].cutOffImg(fromLine, toLine);
                System.out.println("It's done, please save it to check the result.");
                backToMainMenu = true;
            } else if(editingOption.equalsIgnoreCase("7")) {
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
                System.out.println("Loading ...");
                images[imageIndex].cutOffImg(fromLine, fromColumn,toLine , toColumn);
                System.out.println("It's done, please save it to check the result.");
                backToMainMenu = true;
            } else if(editingOption.equalsIgnoreCase("8")) {
                System.out.println("Loading ...");
                images[imageIndex].getNegative();
                System.out.println("It's done, please save it to check the result.");
                backToMainMenu = true;
            } else if(editingOption.equalsIgnoreCase("9")) {
                System.out.println("Please set a value of how many time you want to enlarge the image, horizontally.");
                int enlargeHorizontallyXZoom = sc.nextInt();
                System.out.println("Loading ...");
                images[imageIndex].enlargeFile(enlargeHorizontallyXZoom);
                System.out.println("It's done, please save it to check the result.");
                backToMainMenu = true;
            } else if (editingOption.equalsIgnoreCase("10")) {
                System.out.println("Please set a value of how many time you want to enlarge the entire image.");
                int enlargeXZoom = sc.nextInt();
                System.out.println("Loading ...");
                images[imageIndex].fileEnlargement(enlargeXZoom);
                System.out.println("It's done, please save it to check the result.");
                backToMainMenu = true;
            } else if (editingOption.equalsIgnoreCase("b")) {
                backToMainMenu = true;
            } else {
                throw new wrongEditingOption(editingOption);
            }
        }
    }

    public void displayMainMenu() {
        System.out.println("Main Menu : ");
        System.out.println(" 1 - Load Image");
        System.out.println(" 2 - Edit Image");
        System.out.println(" 3 - Save Image");
        System.out.println(" 4 - Display Images Info");
        System.out.println(" q - Quit ");
        System.out.println();
        System.out.println(this.nbrImages + " image loaded");
        System.out.println();
    }

    public void Menu() throws IOException {

        displayMainMenu();

        String userSelection = " ";
        boolean retry;
        String userChoiceToSaveImage = " ";
        boolean quit = false;


        while (!quit) {
            try {
                do {
                    try {
                        userSelection = " ";
                        System.out.println("Please select one of the options : ");
                        userSelection = sc.nextLine();
                        retry = false;
                    } catch (InputMismatchException e) {
                        System.out.println("ERROR : Incorrect input. An integer is waiting here.");
                        retry = true;
                    } catch (StringIndexOutOfBoundsException e) {
                        retry = true;
                    }
                } while (retry);

                if(userSelection.equalsIgnoreCase("1")) {

                    String userChoiceToLoadNewImage = "y";
                    do {
                        System.out.println("Please set the image path system : ");
                        do {
                            try {
                                String imagePath = sc.nextLine();
                                System.out.println("Loading ...");
                                loadImage(imagePath);
                                retry = false;
                            } catch (NumberFormatException e) {
                                System.out.println("Please retry. Set the image path system : ");
                                retry = true;
                            } catch (InputMismatchException e) {
                                System.out.println("ERROR : Incorrect input. An integer is waiting here.");
                                retry = true;
                            }
                        } while (retry);
                        userChoiceToLoadNewImage = " ";
                        while (!userChoiceToLoadNewImage.equals("y") && !userChoiceToLoadNewImage.equals("n") ) {
                            System.out.println("Do you want to load an other image ? y/n : ");
                            userChoiceToLoadNewImage = sc.nextLine();
                        }
                    } while (userChoiceToLoadNewImage.equalsIgnoreCase("y"));
                    displayMainMenu();


                } else if (userSelection.equalsIgnoreCase("2")) {
                    if(nbrImages == 0) {
                        throw new NoImageLoaded();
                    } else {
                        displayMenuOptions();
                        int indexImage = 0;
                        String featureSelected;
                        do {
                            try {
                                System.out.println("Please select editing feature to your image :  ");
                                featureSelected = sc.nextLine();
                                System.out.println("Please select the image index that you want to edit : ");
                                indexImage = sc.nextInt();
                                editImage(indexImage, featureSelected);
                                retry = false;
                            } catch (wrongImageIndex e) {
                                retry = true;
                            } catch (wrongEditingOption e) {
                                retry = true;
                            } catch (InputMismatchException e) {
                                System.out.println("ERROR : Incorrect input. An integer is waiting here.");
                                retry = true;
                            }
                        } while (retry);
                        sc.nextLine();
                        displayMainMenu();
                    }

                } else if (userSelection.equalsIgnoreCase("3")) {
                    String imageTitle = " ";
                    if(nbrImages == 0) {
                        throw new NoImageLoaded();
                    }
                    do {
                        try {
                            System.out.println("Please select the image you want to save : ");
                            displayImagesLoaded();
                            do {
                                System.out.println("Select the index image : ");
                                int indexImageToSave = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Set a title for the image : ");
                                imageTitle = sc.nextLine();
                                System.out.println("Loading ...");
                                saveImage(indexImageToSave, imageTitle);
                                System.out.println("Do you want to save an other image ? y/n : ");
                                userChoiceToSaveImage = sc.nextLine();
                            } while (!userChoiceToSaveImage.equalsIgnoreCase("n"));
                        } catch (wrongImageIndex e ) {
                            retry = true;
                        } catch (InputMismatchException e) {
                            System.out.println("ERROR : Incorrect input. An integer is wainting here.");
                            retry = true;
                        }
                    } while (retry);
                    sc.nextLine();
                    displayMainMenu();
                } else if (userSelection.equalsIgnoreCase("4")) {
                    if(nbrImages == 0) {
                        throw new NoImageLoaded();
                    }
                    displayImagesLoaded();
                    displayMainMenu();
                } else if (userSelection.equalsIgnoreCase("q")) {
                    quit = true;

                } else {
                    throw new wrongMainMenuSelected();
                }
            } catch (NoImageLoaded e) {
                displayMainMenu();
            } catch (wrongMainMenuSelected e) {
                displayMainMenu();
            }
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
    wrongEditingOption(String i ) {
        System.out.println("ERROR : " + i + ", doesn't match any editing feature");
    }
}

class wrongMainMenuSelected extends Error {
    wrongMainMenuSelected() {
        System.out.println("Not an option");
    }
}
class NoImageLoaded extends Error {
    NoImageLoaded() {
        System.out.println("No image loaded, please load an image before using the feature.");
    }
}


