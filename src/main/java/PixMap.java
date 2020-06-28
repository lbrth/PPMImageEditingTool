import java.security.PublicKey;

public class PixMap {

    Pix firstPix;

    public Pix getFirstPix() {
        return this.firstPix;
    }

    public boolean isEmpty() {
        return this.firstPix == null;
    }

    public void addPixAtFirst(int red, int green, int blue) {
        Pix oldFirstPix = this.firstPix;
        this.firstPix = new Pix(red, green, blue, oldFirstPix);
    }

    public void addPixAtLast(int red, int green, int blue) {
        if(isEmpty()) {
            this.firstPix = new Pix(red, green, blue);
        } else {
            Pix lastPix = getLastPix();
            lastPix.setNext(new Pix(red, green, blue));
        }
    }

    private Pix getLastPix() {
        Pix lastPix = getFirstPix();
        while (lastPix.getNext() != null) {
            lastPix = lastPix.getNext();
        }
        return lastPix;
    }

    public int length() {
        Pix pix = getFirstPix();
        int count = 0;
        while (pix != null) {
            count += 1;
            pix = pix.getNext();
        }
        return count;
    }

    public void displayPixMap() {
        Pix pix = getFirstPix();
        while (pix != null) {
            System.out.print(pix.getBlue() + " " + pix.getGreen() + " " + pix.getRed() + " ");
            pix = pix.getNext();
        }
    }

    public String getPixMapAsString(int lenghtPixMap, int heightPixMap) {
        String pixMapData = "";
        Pix pix = getFirstPix();
        int count = 1;
        while (pix != null) {
            if(count == lenghtPixMap) {
                pixMapData +=  pix.getRed() + " " + pix.getGreen() + " " + pix.getBlue() + " " + "\n";
                count = 0;
            } else {
                pixMapData +=   pix.getRed() + " " + pix.getGreen() + " " + pix.getBlue() + " ";
            }
            count += 1;
            pix = pix.getNext();
        }
        return pixMapData;
    }

    public void updateDominantColors(String dominantColor, int valueToAdd, int maxColorVal) {

        Pix pix = getFirstPix();
        while (pix != null) {
            String maxColor = pix.getMaxColor();
                if( maxColor.equals(dominantColor)) {
                    int red = pix.getRed() + valueToAdd;
                    int green = pix.getGreen() + valueToAdd;
                    int blue = pix.getBlue() + valueToAdd;
                    if(valueToAdd <= 0) {
                       if(red < 0) {
                           pix.setRed(0);
                       } else {
                           pix.setRed(red);
                       }
                        if(green < 0) {
                            pix.setGreen(0);
                        } else {
                            pix.setGreen(green);
                        }
                        if(blue < 0) {
                            pix.setBlue(0);
                        } else {
                            pix.setBlue(blue);
                        }
                    } else {

                        if(red >= maxColorVal) {
                            pix.setRed(maxColorVal);
                        } else {
                            pix.setRed(red);
                        }

                        if(green >= maxColorVal) {
                            pix.setGreen(maxColorVal);
                        } else {
                            pix.setGreen(green);
                        }

                        if(blue >= maxColorVal) {
                            pix.setBlue(maxColorVal);
                        } else {
                            pix.setBlue(blue);
                        }

                    }

                }
            pix = pix.getNext();
        }
    }

    public void updateColors(int newColorValue) {
        Pix pix = getFirstPix();
        while (pix != null) {
            pix.setRed(newColorValue);
            pix.setGreen(newColorValue);
            pix.setBlue(newColorValue);
            pix = pix.getNext();
        }
    }

    public void updateColorsWithAvg() {
        Pix pix = getFirstPix();
        while (pix != null) {
            int avg = pix.getAverage();
            pix.setRed(avg);
            pix.setGreen(avg);
            pix.setBlue(avg);
            pix = pix.getNext();
        }
    }

    public void updateColorsWithAvgRecursively(Pix pix) {
        if(pix != null) {
            int avg = pix.getAverage();
            pix.setRed(avg);
            pix.setGreen(avg);
            pix.setBlue(avg);
            updateColorsWithAvgRecursively(pix.getNext());

        }
    }

    public PixMap cutOffPixMap(PixMap newPixMap, int fromLine, int toLine, int maxLength) {

        Pix pix = getFirstPix();

        int countColumn = 1;
        int countLine = 1;
        while (pix != null) {

            if(countLine >= fromLine) {
                newPixMap.addPixAtLast(pix.getRed(), pix.getGreen(), pix.getBlue());
                if(toLine == countLine) {
                    if(countColumn == maxLength) {
                        break;
                    }
                }
            }

            pix = pix.getNext();
            if(countColumn == maxLength) {
                countColumn = 0;
                countLine++;
            }
            countColumn++;

        }

        return newPixMap;
    }

    public PixMap cutOffPixMap(PixMap newPixMap, int fromLine, int fromColumn, int toLine, int toColumn, int maxLength) {

        Pix pix = getFirstPix();

        int countColumn = 1;
        int countLine = 1;

        while (pix != null) {
            if(fromLine == countLine) {
                while (fromLine <= toLine) {
                    if(fromColumn == countColumn) {
                        while (countColumn <= toColumn) {
                            newPixMap.addPixAtLast(pix.getRed(), pix.getGreen(), pix.getBlue());
                            pix = pix.getNext();
                            countColumn++;
                        }
                    } else if(countColumn > maxLength) {
                        countLine++;
                        fromLine++;
                        countColumn = 1;
                    } else {
                        countColumn++;
                        pix = pix.getNext();
                    }
                }
            } else if(countColumn > maxLength) {
                countLine++;
                countColumn = 1;

            } else {
                countColumn++;
                pix = pix.getNext();
            }
            if(countLine >= toLine) {
                break;
            }
        }
            return newPixMap;
        }

    public void getNegative(int maxColorScale) {
        Pix pix = getFirstPix();
        while (pix != null) {
            pix.setRed(maxColorScale-pix.getRed());
            pix.setGreen(maxColorScale-pix.getGreen());
            pix.setBlue(maxColorScale-pix.getBlue());
            pix = pix.getNext();
        }
    }

    public void getNegativeRecursively( Pix pix, int maxColorScale) {
        if(pix != null) {
            pix.setRed(maxColorScale-pix.getRed());
            pix.setGreen(maxColorScale-pix.getGreen());
            pix.setBlue(maxColorScale-pix.getBlue());
            getNegativeRecursively(pix.getNext(), maxColorScale);
        }

    }

    public void enlargePixMap() {
        Pix pix = getFirstPix();
        while (pix != null) {
            pix.setNext(new Pix(pix.getRed(), pix.getGreen(), pix.getBlue(), pix.getNext()));
            pix = (pix.getNext()).getNext();
        }
    }

    public void enlargePixMapRecursively(Pix pix) {
        if(pix != null) {
            pix.setNext(new Pix(pix.getRed(), pix.getGreen(), pix.getBlue(), pix.getNext()));
            enlargePixMapRecursively((pix.getNext()).getNext());
        }
    }

    public void reducePixMap() {
        Pix pix = getFirstPix();
        Pix nextPix = pix.getNext();
        while (pix.getNext() != null) {
            pix.setNext(nextPix.getNext());
            if(pix.getNext() == null) {
                break;
            }
            pix = nextPix.getNext();
            nextPix = pix.getNext();
        }
    }


    public PixMap pixMapEnlargement(PixMap newPixMap, int maxLenght, int maxheight, int newMaxheight) {

        Pix pix;
        int countLine = 1;
        int countColumn = 1;
        int newCountLine = 0;
        int countPix = 0;
        Pix first = getFirstPix();
            while (countLine <= maxheight) {
                while (newCountLine <= newMaxheight-1) {
                    pix = first;
                    if(pix ==  null)break;
                    while (countColumn <= maxLenght) {
                        while (countPix <= 1) {
                            newPixMap.addPixAtLast(pix.getRed(), pix.getGreen(), pix.getBlue());
                            countPix++;
                        }
                        pix = pix.getNext();
                        countPix = 0;
                        countColumn++;
                    }
                    if(newCountLine == 1) {
                        first = pix;
                        newCountLine = 0;
                    } else {
                        newCountLine++;
                    }
                    countColumn = 1;
                }
                countLine++;
            }

        return newPixMap;
    }

}









