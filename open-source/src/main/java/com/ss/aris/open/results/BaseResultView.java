package com.ss.aris.open.results;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ss.aris.open.console.Console;
import com.ss.aris.open.console.IConsoleHelper;
import com.ss.aris.open.console.functionality.IText;
import com.ss.aris.open.pipes.PConstants;
import com.ss.aris.open.pipes.entity.Keys;
import com.ss.aris.open.pipes.entity.Pipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.ss.aris.open.console.functionality.IText.ColorType.APP;
import static com.ss.aris.open.console.functionality.IText.ColorType.CONTACT;
import static com.ss.aris.open.console.functionality.IText.ColorType.PIPE;

public abstract class BaseResultView implements IResultView {

    private Context context;
    protected Console console;
    private IConsoleHelper consoleHelper;
    private ViewGroup selections;
    private Typeface typeface;

    @Override
    public void setup(Context context, Console console, IConsoleHelper consoleHelper, ViewGroup view) {
        this.context = context;
        selections = view;
        this.console = console;
        this.consoleHelper = consoleHelper;

        typeface = getTypeface();
        refresh();
    }

    protected abstract Typeface getTypeface();

    private String getDisplayName(String displayName) {
        return displayName.startsWith(Keys.ACTION) ?
                displayName.replaceFirst(Keys.ACTION_REGEX, "")
                : displayName;
    }

    @Override
    public void displayResult(List<Pipe> resultList) {
        if (resultList == null) {
            selections.removeAllViews();
            return;
        }

        selections.removeAllViews();
        int i = 0;
        boolean hasPrevious = false;
        for (final Pipe pipe : resultList) {
            if (i == 0) {
                hasPrevious = displayPreviouses(pipe);
            }

            IResultTextView item = provideResultView();
            try {
                item.setTypeface(typeface);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int id = pipe.getId();

            if (i++ == 0) {
                item.setTextColor(ContextCompat.getColor(context, getTextColor()));
                if (hasPrevious) {
//                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) item.getView().getLayoutParams();
//                        params.leftMargin = -context.getResources().getDimensionPixelSize(R.dimen.dp_4);
                    switch (id) {
                        case PConstants.ID_APPLICATION:
                            item.setup(getResultColor(APP), true, IResultTextView.Type.BOTH);
                            break;
                        case PConstants.ID_CONTACT:
                            item.setup(getResultColor(CONTACT), true, IResultTextView.Type.BOTH);
                            break;
                        default:
                            item.setup(getResultColor(PIPE), true, IResultTextView.Type.BOTH);
                    }
                } else {
                    switch (id) {
                        case PConstants.ID_APPLICATION:
                            item.setup(getResultColor(APP), true, IResultTextView.Type.NONE);
                            break;
                        case PConstants.ID_CONTACT:
                            item.setup(getResultColor(CONTACT), true, IResultTextView.Type.NONE);
                            break;
                        default:
                            item.setup(getResultColor(PIPE), true, IResultTextView.Type.NONE);
                    }
                }
            } else {
                switch (id) {
                    case PConstants.ID_APPLICATION:
                        item.setTextColor(getResultColor(APP));
                        item.setup(getResultColor(APP), false, IResultTextView.Type.NONE);
                        break;
                    case PConstants.ID_CONTACT:
                        item.setTextColor(getResultColor(CONTACT));
                        item.setup(getResultColor(CONTACT), false, IResultTextView.Type.NONE);
                        break;
                    default:
                        item.setTextColor(getResultColor(PIPE));
                        item.setup(getResultColor(PIPE), false, IResultTextView.Type.NONE);
                }
            }

            item.setText(getDisplayName(pipe.getDisplayName()));
            item.getView().setId(pipe.getId());

            item.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    consoleHelper.execute(pipe);
                }
            });
            item.getView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    consoleHelper.selectOnLongPress(pipe);
                    return true;
                }
            });
            selections.addView(item.getView());

        }
    }

    @Override
    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;

    }

    @Override
    public void clear() {
        selections.removeAllViews();
    }

    private boolean displayPreviouses(Pipe pipe) {
        Pipe previous = pipe.getPrevious().get();
        if (previous != null) {
            boolean hasPrevious = displayPreviouses(previous);

            IResultTextView previousTv = provideResultView();
            previousTv.setTypeface(typeface);
            previousTv.setText(getDisplayName(previous.getDisplayName()));
            previousTv.setTextColor(ContextCompat.getColor(context, getTextColor()));

            if (hasPrevious) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) previousTv.getView().getLayoutParams();
//                params.leftMargin = -context.getResources().getDimensionPixelSize(R.dimen.dp_4);
            }

            int id = previous.getId();
            switch (id) {
                case PConstants.ID_APPLICATION:
                    previousTv.setup(getResultColor(APP), true, hasPrevious ? IResultTextView.Type.BOTH : IResultTextView.Type.INPUT);
                    break;
                case PConstants.ID_CONTACT:
                    previousTv.setup(getResultColor(CONTACT), true, hasPrevious ? IResultTextView.Type.BOTH : IResultTextView.Type.INPUT);
                    break;
                default:
                    previousTv.setup(getResultColor(PIPE), true, hasPrevious ? IResultTextView.Type.BOTH : IResultTextView.Type.INPUT);
            }
            selections.addView(previousTv.getView());

            return true;
        } else {
            return false;
        }
    }

    protected abstract IResultTextView provideResultView();

    protected abstract int getTextColor();

    protected abstract int getResultColor(IText.ColorType type);

}