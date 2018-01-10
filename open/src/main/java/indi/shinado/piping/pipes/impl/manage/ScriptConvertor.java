package indi.shinado.piping.pipes.impl.manage;

import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.pri.PRI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * pipe://id=1006/exe=file:///sdcard/0/download
 * pipe://id=1006/exe=$#latest
 * pipe://name=snake
 * <p>
 * input:
 * cd download/folder.latest.qq
 * <p>
 * output:
 * pipe://id=1006/exe=file://sdcard/0/download/folder->pipe://id=1006/exe=$#latest->pipe://id=2/exe=package.of.qq
 */
public class ScriptConvertor {

    public static String PIPE = "->";

    static boolean hasFormated(Pipe input) {
        return PRI.parse(input.getExecutable()) != null;
    }

    public static String getNames(Pipe input) throws UnsupportedEncodingException {
        Pipe prev = input.getPrevious().get();

        String script = input.getSearchableName().toString();

        if (prev == null) {
            return script;
        } else {
            return getScript(prev) + PIPE + script;
        }
    }

    public static String getScript(Pipe input) throws UnsupportedEncodingException {
        Pipe prev = input.getPrevious().get();

        String script;
        if (input.getId() == PConstants.ID_MANAGER) {
            script = input.getExecutable();
        } else if (input.getId() == PConstants.ID_CLIPBOARD) {
            script = new PRI("pipe", "id=" + PConstants.ID_TEXT + "/" +
                    "exe=" +
                    URLEncoder.encode(input.getExecutable(), "utf-8")).toString();
        } else {
            String params = URLEncoder.encode(input.getInstruction().params(), "utf-8");
            String exe = URLEncoder.encode(input.getExecutable(), "utf-8");
            script = new PRI("pipe", "id=" + input.getId() + "/" +
                    "exe=" + exe + (params.isEmpty() ? "" : "/params=" + params))
                    .toString();
        }

        if (prev == null) {
            return script;
        } else {
            return getScript(prev) + PIPE + script;
        }
    }

    public static String getScript(int id, String executable) throws UnsupportedEncodingException {
        return new PRI("pipe", "id=" + id + "/" +
                "exe=" +
                URLEncoder.encode(executable, "utf-8")).toString();
    }

}