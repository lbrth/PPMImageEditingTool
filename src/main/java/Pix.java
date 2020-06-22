import sun.tools.jconsole.AboutDialog;

public class Pix {

    private int red;
    private int green;
    private int blue;

    private Pix next;




    public Pix(int red, int green, int blue, Pix next) {


        this.red = red;
        this.green = green;
        this.blue = blue;
        this.next = next;



    }

    public Pix(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.next = null;

    }

    public int getRed() {
        return this.red;
    }

    public int getAverage() {
        int sum = this.red + this.green + this.blue;
        int avg = sum/3;
        return Math.round(avg);
    }


    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public Pix getNext() {
        return this.next;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setNext(Pix next) {
        this.next = next;
    }

    public int getColorValue(String color) {

        if(color == "red") {
            return getRed();
        } else if (color == "green") {
            return getGreen();
        } else if (color == "blue") {
            return getBlue();
        } else {
            throw new wrongColorName(color);
        }

    }

    public String getColorName(int value) {
        String colorName ;
        if(value == getBlue()) {
            colorName = "blue";
        } else if (value == getGreen()) {
            colorName = "green";
        } else if (value == getRed()) {
            colorName = "red";
        } else {
            throw new unknowValueColor(value);
        }
        return colorName;
    }

    public String getMaxColor() {
        int [] colors = {red, green, blue};
        int max = colors[0];
        for(int i = 0; i <= colors.length-1; i++) {
            if(max < colors[i]) {
                max = colors[i];
            }
        }
        return getColorName(max);
    }


}

class wrongColorName extends Error {
    wrongColorName(String color) {
        System.out.print(color + ", is an uncorrect color name. Only red, green, or blue are correct");
    }
}

class unknowValueColor extends Error {
    unknowValueColor(int val) {
        System.out.print(val + ", not in the pix scope");
    }
}


