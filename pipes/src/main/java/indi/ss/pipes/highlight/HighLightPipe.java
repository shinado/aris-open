package indi.ss.pipes.highlight;

import java.util.Map;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Keys;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.pipes.impl.interfaces.Helpable;

public class HighLightPipe extends DefaultInputActionPipe implements Helpable{

    private static final String START_WITH = Keys.PARAMETER + "s";
    private static final String END_WITH = Keys.PARAMETER + "e";

    public HighLightPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$highlight";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("high", "light");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        callback.onOutput(getHelp());
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {

    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        if (callback == getConsoleCallback()) {
            String[] params = result.getInstruction().params;
            if (params == null || params.length == 0) {
                getConsole().display("Highlight failed. You must input at least one parameter. ");
            } else {
                if (params.length == 1) {
                    String high = params[0];
                    input = input.replace(high, "<font color='#7E0009'>" + high + "</font>");
                    getConsole().display(input);
                } else {
                    String[] lines = input.split("\n");
                    Map<String, String> map = result.getInstruction().parameterMap();
                    if (!tryStartsWith(lines, map)){
                        if (!tryEndsWith(lines, map)){
                            getConsole().display("Highlight failed to parse parameters. ");
                        }
                    }
                }
            }
        }
    }

    private boolean tryEndsWith(String[] lines, Map<String, String> map) {
        String startWith = map.get(END_WITH);
        if (startWith != null) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (line.endsWith(startWith)) {
                    line = "<font color='#7E0009'>" + line + "</font>";
                }

                sb.append(line).append("\n");
            }
            getConsole().display(sb.toString());
            return true;
        }
        return false;
    }

    private boolean tryStartsWith(String[] lines, Map<String, String> map) {
        String startWith = map.get(START_WITH);
        if (startWith != null) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (line.startsWith(startWith)) {
                    line = "<font color='#7E0009'>" + line + "</font>";
                }

                sb.append(line).append("\n");
            }
            getConsole().display(sb.toString());
            return true;
        }
        return false;
    }

    @Override
    public String getHelp() {
        return "Use of highlight: '[input].highlight [your keyword]' to highlight your keyword from input.\n" +
                "[input].highlight -s [keyword] to highlight any line that starts with keyword.\n" +
                "[input].highlight -e [keyword] to highlight any line that ends with keyword.";
    }
}
