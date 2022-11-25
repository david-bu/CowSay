public class CowSay {

    private static final int DEFAULT_PADDING = 15;
    private static final int DEFAULT_WIDTH = 40;
    private static final String DEFAULT_EYES = "oo";
    private static final String BORG_EYES = "==";
    private static final String DEAD_EYES = "XX";
    private static final String GREEDY_EYES = "$$";
    private static final String PARANOID_EYES = "@@";
    private static final String STONER_EYES = "**";
    private static final String TIRED_EYES = "--";
    private static final String WIRED_EYES = "OO";
    private static final String YOUTHFUL_EYES = "--";

    private static final String[][] COW = {
            { "\\", "  ^__^" },
            { " \\", " (", /* "**", */ ")\\_______" },
            { "   (__)\\       )\\/\\" },
            { "    ", /*"U",*/ "  ||----w |" },
            { "       ||     ||" }
    };

    private static final String COLOR_RESET = "\033[0m";
    private static final String COLOR_BLACK = "\033[0;30m";
    private static final String COLOR_RED = "\033[0;31m";
    private static final String COLOR_GREEN = "\033[0;32m";
    private static final String COLOR_YELLOW = "\033[0;33m";
    private static final String COLOR_BLUE = "\033[0;34m";
    private static final String COLOR_PURPLE = "\033[0;35m";
    private static final String COLOR_CYAN = "\033[0;36m";
    private static final String COLOR_WHITE = "\033[0;37m";

    private static int width = DEFAULT_WIDTH;
    private static String[] text = { "" };
    private static String eyes = DEFAULT_EYES;
    private static boolean printTongue = false;
    private static String color = COLOR_RESET;

    public static void main(String[] args) {
        parseCmdLineArgs(args);
        printTextBox();
        printCow();
    }

    /**
     * iterates over all String elements in args and sets the values for the used eyes, if the tongue should be printed
     * and sets the text and text color
     * @param args the arguments to parse
     */
    private static void parseCmdLineArgs(String[] args) {
        if (args.length == 0) {
            System.err.println("no command line arguments provided!");
            System.exit(-1);
        }

        boolean reverseTongue = false;
        int i = 0;
        while (i < args.length - 1) {
            switch (args[i]) {
                case "-W":
                    width = Integer.parseInt(args[++i]);
                    break;
                case "-b":
                    eyes = BORG_EYES;
                    break;
                case "-d":
                    eyes = DEAD_EYES;
                    printTongue = true;
                    break;
                case "-g":
                    eyes = GREEDY_EYES;
                    break;
                case "-p":
                    eyes = PARANOID_EYES;
                    break;
                case "-s":
                    eyes = STONER_EYES;
                    printTongue = true;
                    break;
                case "-t":
                    eyes = TIRED_EYES;
                    break;
                case "-w":
                    eyes = WIRED_EYES;
                    break;
                case "-y":
                    eyes = YOUTHFUL_EYES;
                    break;
                case "-e":
                    eyes = args[++i].substring(0, 2);
                    break;
                case "-T":
                    reverseTongue = true;
                    break;
                case "-c":
                    switch (args[++i]) {
                        case "black":
                            color = COLOR_BLACK;
                            break;
                        case "red":
                            color = COLOR_RED;
                            break;
                        case "green":
                            color = COLOR_GREEN;
                            break;
                        case "yellow":
                            color = COLOR_YELLOW;
                            break;
                        case "blue":
                            color = COLOR_BLUE;
                            break;
                        case "purple":
                            color = COLOR_PURPLE;
                            break;
                        case "cyan":
                            color = COLOR_CYAN;
                            break;
                        case "white":
                            color = COLOR_WHITE;
                            break;
                        default:
                            System.err.println("unknown color: " + args[i]);
                            System.exit(-1);
                    }
                    break;
                default:
                    System.err.println("unknown command line argument: " + args[i]);
                    System.exit(-1);
            }
            i++;
        }
        if (reverseTongue)
            printTongue = !printTongue;

        if (i == args.length) {
            System.err.println("last parameter: " + args[args.length - 2] + " requires a value");
            System.exit(-1);
        }

        formatText(args[i]);
    }

    /**
     * creates substrings of inputText with a max length of width and sets the text array to this value
     * every created string except for the last has a length of width
     * @param inputText the text that should be split up into substrings with a max length of width
     */
    private static void formatText(String inputText) {
        int inputTextLength = inputText.length();
        int textArrayLength = inputTextLength/width + ((inputTextLength % width == 0)? 0: 1);
        text = new String[textArrayLength];
        for (int j = 0; j < text.length; j++) {
            int startIndex = j * width;
            int stopIndex = min((j + 1) * width, inputTextLength);
            text[j] = inputText.substring(startIndex, stopIndex);
        }
    }

    /**
     * prints a text box with text.length lines, but min 2
     * text.length has to be greater than 0
     */
    private static void printTextBox() {
        if (text == null || text.length == 0) {
            System.err.println("no text input!");
            System.exit(-1);
        }

        // printing top border with width + 1 length for 1 padding
        System.out.print(" ");
        System.out.print(repeatString("_", width + 1));
        System.out.println();

        // printing first line with corners
        System.out.print("/ ");
        System.out.print(text[0] + repeatString(" ", width - text[0].length()));
        System.out.println("\\");

        // printing lines 2 to length -1 (exclusive)
        for (int i = 1; i < text.length - 1; i++) {
            System.out.println("| " + text[i] + "|");
        }

        // printing last line with corners
        int lastIndex = text.length - 1;
        String lastLine = (lastIndex > 0) ? text[lastIndex] : "";
        System.out.print("\\ ");
        System.out.print(lastLine);
        System.out.print(repeatString(" ", width - lastLine.length()));
        System.out.println("/");

        // printing bottom border
        System.out.print(" ");
        System.out.print(repeatString("-", width + 1));
        System.out.println();
    }

    /**
     * prints a cow with a line to the text box that should be printed before
     * prints the cow with a padding of width + 1 but max DEFAULT_PADDING
     * the cow is printed with the values specified in eyes and printTongue and with the color specified in color
     */
    private static void printCow() {
        int padding = min(width + 1, DEFAULT_PADDING);
        for (int i = 0; i < COW.length; i++) {
            System.out.print(repeatString(" ", padding));
            if (i == 0) {
                System.out.print(COLOR_RESET + COW[i][0]);
                System.out.println(color + COW[i][1]);
                continue;
            }

            if (i == 1) {
                System.out.print(COLOR_RESET + COW[i][0]);
                System.out.print(color + COW[i][1]);
                System.out.print(eyes);
                System.out.println(COW[i][2]);
                continue;
            }

            if (i == 3) {
                System.out.print(COW[i][0]);
                System.out.print(printTongue? "U": " ");
                System.out.println(COW[i][1]);
                continue;
            }

            System.out.println(COW[i][0]);
        }
    }

    /**
     * returns a string containing value [times] times
     * @param value the string to repeat
     * @param times how often the string should be repeated
     * @return the resulting string containing value [times] times
     */
    private static String repeatString(String value, int times) {
        String retVal = "";
        for (int i = 0; i < times; i++) {
            retVal += value;
        }
        return retVal;
    }

    /**
     * returns the smaller value
     * @param i1 value 1
     * @param i2 value 2
     * @return the smaller value
     */
    private static int min(int i1, int i2) {
        return (i1 > i2)? i2 : i1;
    }

}