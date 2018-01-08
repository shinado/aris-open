package indi.ss.pipes.snake;

import android.os.Handler;
import android.text.InputType;
import android.util.TypedValue;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.ss.aris.open.console.CharacterInputCallback;
import com.ss.aris.open.console.InputCallback;
import com.ss.aris.open.console.SingleLineInputCallback;
import com.ss.aris.open.console.impl.DeviceConsole;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;
import com.ss.aris.open.util.VersionUtils;

public class SnakePipe extends DefaultInputActionPipe implements Console {

    private TextView textView;
    private int inputType;
    private final String UP = "2";
    private final String DOWN = "8";
    private final String LEFT = "4";
    private final String RIGHT = "6";

    private Handler mHandler;
    private Game game;

    public SnakePipe(int id) {
        super(id);
        game = new Game();
        mHandler = new Handler();
    }

    @Override
    public void draw(byte[][] matrix) {
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == Game.EMPTY) {
//                    sb.append("▒");
                    sb.append("█");
                } else {
//                    sb.append("　");
                    sb.append("░");
                }
            }
            sb.append("\n");
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (textView != null) textView.setText(sb.toString());
            }
        });
    }

    @Override
    public void die() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        });
    }

    @Override
    public String getDisplayName() {
        return "$snake";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("snake");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        ready(callback);
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
        ready(callback);
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {
        ready(callback);
    }

    private void ready(OutputCallback callback) {
        if (callback == getConsoleCallback()) {
            ready();
        } else {
            callback.onOutput("shr~~snake");
        }
    }

    private void ready() {
        String help = VersionUtils.isChina() ?
                "输入2控制向上，8下4左6右，建议调整键盘到数字模式。请输入任意字母以开始。" :
                "Use 2 for up, 8 for down, 4 for left and 6 for right. Please enter maze width(for instance, 30) to start.";
        getConsole().display(help);

        DeviceConsole console = (DeviceConsole) getConsole();
        inputType = console.getInputType();
        console.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        getConsole().waitForCharacterInput(new CharacterInputCallback() {
            @Override
            public void onCharacterInput(String character) {
                start();
            }
        });

//        getConsole().waitForSingleLineInput(new SingleLineInputCallback() {
//            @Override
//            public void onUserInput(String userInput) {
//                verifyInput(userInput);
//            }
//        }, false);
    }

    private void verifyInput(String character) {
        try {
            int width = Integer.parseInt(character);
            start();
        } catch (NumberFormatException e) {
            e.printStackTrace();

            String help = VersionUtils.isChina() ?
                    "请输入迷宫长度（如30）以开始。" :
                    "Please enter maze width(for instance, 30) to start.";
            getConsole().display(help);
            getConsole().waitForSingleLineInput(new SingleLineInputCallback() {
                @Override
                public void onUserInput(String userInput) {
                    verifyInput(userInput);
                }
            }, false);
        }
    }

    private void start() {
        DeviceConsole deviceConsole = (DeviceConsole) getConsole();
        textView = new TextView(context, null);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                deviceConsole.getDisplayTextView().getTextSize());
        textView.setTextColor(deviceConsole.getDisplayTextView()
                .getCurrentTextColor());
        textView.setTypeface(deviceConsole.getTypeface());

        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private boolean b = true;

            @Override
            public void onGlobalLayout() {
                if (b) b = false;
                else return;

                String space = "█";
                String text = "";
                float textWidth = 0;
                while (textWidth < textView.getMeasuredWidth()) {
                    text += space;
                    textWidth = textView.getPaint().measureText(text);
                }
                int width = (int) (text.length() * 0.85f);
                int height = (int) (width * 0.8f);

                Maze maze = new Maze(width, height);
                ((DeviceConsole) getConsole()).blindMode();

                if (getConsole() instanceof DeviceConsole){
                    ((DeviceConsole) getConsole()).clear();
                }

                game.create(maze, new Snake(), SnakePipe.this);
                game.start();
                ((DeviceConsole) getConsole()).addInputCallback(mInputCallback);
            }
        });

        ((DeviceConsole) getConsole()).replaceCurrentView(textView);
    }

    public String getTenCharPerLineString(String text) {
        String tenCharPerLineString = "";
        while (text.length() > 10) {

            String buffer = text.substring(0, 10);
            tenCharPerLineString = tenCharPerLineString + buffer + "/n";
            text = text.substring(10);
        }

        tenCharPerLineString = tenCharPerLineString + text.substring(0);
        return tenCharPerLineString;
    }

    @Override
    public void intercept() {
        stop();
    }

    private void stop() {
        game.stop();
        final DeviceConsole console = (DeviceConsole) getConsole();
        console.quitBlind();
        console.setInputType(inputType);
        console.removeInputCallback(mInputCallback);

        textView.append("\nGame Over. Score: " + game.getScore());
        getConsole().waitForCharacterInput(new CharacterInputCallback() {
            @Override
            public void onCharacterInput(String character) {
                console.reshowTerminal();
            }
        });
    }

    private InputCallback mInputCallback = new InputCallback() {
        @Override
        public void onInput(String character) {
            switch (character) {
                case UP:
                    game.getSnake().up();
                    break;
                case DOWN:
                    game.getSnake().down();
                    break;
                case LEFT:
                    game.getSnake().left();
                    break;
                case RIGHT:
                    game.getSnake().right();
                    break;
            }
        }
    };

}
