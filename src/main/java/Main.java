import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


        String titleTool = "\n" +
                "  _____   _____   __  __   _____                                  ______     _  _  _    _                 _______             _ \n" +
                " |  __ \\ |  __ \\ |  \\/  | |_   _|                                |  ____|   | |(_)| |  (_)               |__   __|           | |\n" +
                " | |__) || |__) || \\  / |   | |   _ __ ___    __ _   __ _   ___  | |__    __| | _ | |_  _  _ __    __ _     | |  ___    ___  | |\n" +
                " |  ___/ |  ___/ | |\\/| |   | |  | '_ ` _ \\  / _` | / _` | / _ \\ |  __|  / _` || || __|| || '_ \\  / _` |    | | / _ \\  / _ \\ | |\n" +
                " | |     | |     | |  | |  _| |_ | | | | | || (_| || (_| ||  __/ | |____| (_| || || |_ | || | | || (_| |    | || (_) || (_) || |\n" +
                " |_|     |_|     |_|  |_| |_____||_| |_| |_| \\__,_| \\__, | \\___| |______|\\__,_||_| \\__||_||_| |_| \\__, |    |_| \\___/  \\___/ |_|\n" +
                "                                                     __/ |                                         __/ |                        \n" +
                "                                                    |___/                                         |___/                         \n";

        System.out.println(titleTool);
        System.out.println();
        System.out.println("PPM Image Editing Tool is a Java Tool Box to edit PPM image.");
        System.out.println();
        Menu menu = new Menu();
        menu.Menu();

    }
}
