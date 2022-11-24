public class CowSay {

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

    private static int width = DEFAULT_WIDTH;
    private static String[] text = { "" };
    private static String eyes = DEFAULT_EYES;
    private static boolean printTongue = false;

    private static final String[] COW = {
            "\\  a__a",
            " \\ (**)\\_______",
            "   (__)\\       )\\/\\",
            "    U  ||----W |",
            "       ||     ||"
    };

    public static void main(String[] args) {
        parseCmdLineArgs(args);
        printTextBox();
        printCow();
    }

    private static void parseCmdLineArgs(String[] args) {
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
                    // TODO
                    break;
                case "-T":
                    reverseTongue = true;
                    break;
            }
            i++;
        }
        if (reverseTongue)
            printTongue = !printTongue;

        String inputText = args[i];
        int inputTextLength = inputText.length();
        text = new String[inputTextLength/width + 1];
        for (int j = 0; j < text.length; j++) {
            int startIndex = j * width;
            int stopIndex = Math.min((j + 1) * width, inputTextLength);
            text[j] = inputText.substring(startIndex, stopIndex);
        }
    }

    private static void printTextBox() {
        System.out.print(" ");
        for (int i = 0; i <= width; i++)
            System.out.print("_");
        System.out.println();

        System.out.print("/");
        System.out.print(" " + text[0]);
        System.out.println("\\");

        for (int i = 1; i < text.length - 1; i++) {
            System.out.print("|");
            System.out.print(" " + text[i]);
            System.out.println("|");
        }

        System.out.print("\\");
        System.out.print(" " + text[text.length-1]);
        for (int i = text[text.length-1].length(); i < width; i++)
            System.out.print(" ");
        System.out.println("/");

        System.out.print(" ");
        for (int i = 0; i <= width; i++)
            System.out.print("-");
        System.out.println();
    }

    private static void printCow() {
        int abstand = Math.min(width + 1, 15);

        for (int i = 0; i < COW.length; i++) {
            System.out.print(" ".repeat(abstand));
            if (i == 1)
                System.out.println(COW[i].replace(DEFAULT_EYES, eyes));
            else if (i == 3)
                System.out.println(COW[i].replace("U", " "));
            else System.out.println(COW[i]);
        }
    }
}