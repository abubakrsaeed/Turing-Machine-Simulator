import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TuringMachine {

    public InputReader inputReader;
    public ArrayList<String> routeTaken = new ArrayList<>();
    public List<ArrayList<String>> TransitionTable;
    public ArrayList<String> inputs;

    public TuringMachine(File file) {
        inputReader = new InputReader(file);
        TransitionTable = inputReader.getTransitionTable();
        inputs = inputReader.getInputs();
    }

    public ArrayList<String> getTransition(String currentState, String pointer){
        for (ArrayList<String> strings : TransitionTable)
            if (strings.get(0).equals(currentState) && strings.get(1).equals(pointer))
                return strings;

        ArrayList<String> transition = new ArrayList<>();
        transition.add("NULL");
        return transition;
    }

    public void sim(){

        try {
            FileWriter fw = new FileWriter("output.txt");

            for (String value : inputs) {

                ArrayList<String> input = new ArrayList<>();

                input.add(inputReader.getBlank());
                for (int j = 0; j < value.length(); j++)
                    input.add(String.valueOf(value.charAt(j)));
                input.add(inputReader.getBlank());

                String currentState = inputReader.getStartState();
                routeTaken.clear();
                routeTaken.add(currentState);
                int pointerIndex = 1;
                ArrayList<String> transition;

                while (!currentState.equals(inputReader.getAcceptState()) || !currentState.equals(inputReader.getRejectState())) {
                    transition = getTransition(currentState, input.get(pointerIndex));

                    currentState = transition.get(4);
                    routeTaken.add(currentState);

                    if (currentState.equals(inputReader.getAcceptState()) || currentState.equals(inputReader.getRejectState()))
                        break;

                    input.set(pointerIndex, transition.get(2));
                    pointerIndex = PointerTM(transition.get(3), pointerIndex);
                }

                if (currentState.equals(inputReader.getAcceptState())) {
                    for (String s : routeTaken) {
                        System.out.print(s + " ");
                        fw.write(s + " ");
                    }
                    System.out.print("(Route taken)");
                    System.out.println("\nAccepted");
                    fw.write("\nAccepted\n");
                } else if (currentState.equals(inputReader.getRejectState())) {
                    for (String s : routeTaken) {
                        System.out.print(s + " ");
                        fw.write(s + " ");
                    }
                    System.out.print("(Route taken)");
                    System.out.println("\nRejected");
                    fw.write("\nRejected\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int PointerTM(String move, int pointerIndex){
        if (move.equals("R")) pointerIndex += 1;
        else if (move.equals("L")) pointerIndex -= 1;
        return pointerIndex;
    }

}
