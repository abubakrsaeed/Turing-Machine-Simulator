import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class InputReader {

    public String startState;
    public String acceptState;
    public String rejectState;
    public String blank;
    public int noInpStates;
    public int noTapeVar;
    public int noStates;
    public ArrayList<String> states = new ArrayList<>();
    public ArrayList<String> tapeAlph = new ArrayList<>();
    public ArrayList<Integer> inputAlph = new ArrayList<>();
    public ArrayList<String> tmpTT = new ArrayList<>();
    public List<ArrayList<String>> TransitionTable = new ArrayList<>();
    public ArrayList<String> inputs = new ArrayList<>();


    public List<ArrayList<String>> getTransitionTable() {
        return TransitionTable;
    }

    public ArrayList<String> getInputs() {
        return inputs;
    }

    public String getStartState() {
        return startState;
    }

    public String getAcceptState() {
        return acceptState;
    }

    public String getRejectState() {
        return rejectState;
    }

    public String getBlank() {
        return blank;
    }

    private void StringList (ArrayList<String> list, String string){
        String[] arr = string.split("\\s+");
        list.addAll(Arrays.asList(arr));
    }

    private void IntList (ArrayList<Integer> list, String string){
        String[] arr = string.split("\\s+");
        for (String s : arr) {
            list.add(Integer.parseInt(s));
        }
    }

    private List<ArrayList<String>> TuplesForTT(ArrayList<String> list){
        List<ArrayList<String>> arrayList = new ArrayList<>();
        int lineSize = 5;
        int listCount = list.size()/lineSize;
        int index = 0;
        for (int i=0; i<listCount; i++){
            ArrayList<String> tmpArray = new ArrayList<>();
            for (int j=0; j<lineSize; j++, index++)
                tmpArray.add(list.get(index));
            arrayList.add(tmpArray);
        }

        return arrayList;
    }

    public InputReader (File fileObj) {
        try {
            Scanner scan = new Scanner(fileObj);
            noInpStates = Integer.parseInt(scan.nextLine());
            noTapeVar = Integer.parseInt(scan.nextLine());
            noStates = Integer.parseInt(scan.nextLine());
            StringList(states, scan.nextLine());
            startState = scan.nextLine();
            acceptState = scan.nextLine();
            rejectState = scan.nextLine();
            blank = scan.nextLine();
            StringList(tapeAlph, scan.nextLine());
            IntList(inputAlph, scan.nextLine());

            String tmp;
            boolean TFlag = true;
            boolean IFlag = false;

            while (scan.hasNextLine()) {

                tmp = scan.nextLine();

                if (TFlag && inputAlph.contains(Character.getNumericValue(tmp.charAt(0)))) {
                    TFlag = false;
                    IFlag = true;
                }

                if (TFlag)
                    StringList(tmpTT, tmp);
                if (IFlag)
                    StringList(inputs, tmp);
            }

            TransitionTable = TuplesForTT(tmpTT);
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
