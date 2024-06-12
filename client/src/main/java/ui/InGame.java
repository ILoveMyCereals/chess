package ui;

import java.util.Scanner;

public class InGame {

    private String option = "0";

    public InGame() {}

    public void inGameUI() {
        System.out.println("""
                Select an option to continue:
                1. Make Move
                2. Redraw Board
                3. Highlight Legal Moves
                4. Help
                5. Leave Game
                6. Resign""");

        Scanner newScan = new Scanner(System.in);
        option = newScan.nextLine();

        //WHEN THE USER PICKS AN OPTION, A USERGAMECOMMAND OBJECT WILL BE SENT TO THE SERVER FACADE (ALSO A WEBSOCKET CONNECTION?)
        //THIS CLASS WILL RECEIVE SERVER MESSAGE OBJECTS?
        //SO TO MAKE A CONNECTION, THIS CLASS CALLS A METHOD IN SERVER FACADE THAT SENDS A WEBSOCKET CONNECTION?
    }
}
