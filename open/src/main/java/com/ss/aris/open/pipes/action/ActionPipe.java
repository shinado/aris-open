package com.ss.aris.open.pipes.action;

import java.util.TreeSet;

import com.ss.aris.open.pipes.BasePipe;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.search.translator.AbsTranslator;

public abstract class ActionPipe extends BasePipe{

    public ActionPipe(int id) {
        super(id);
    }

    @Override
    public void search(String input, int length, Pipe previous, SearchResultCallback callback) {
        Instruction instruction = new Instruction(input);
        Pipe result = search(instruction);
        callback(result, instruction, callback);
    }

    protected void callback(Pipe result, Instruction input, SearchResultCallback callback){
        TreeSet<Pipe> list = new TreeSet<>();
        if (result != null) {
            list.add(result);
        }
        callback.onSearchResult(list, input);
    }

    public Pipe search(Instruction input) {
        if (input.input.isEmpty()){
            if (configurations.needHistory() && asOutput()){
                return new Pipe(getResult());
            }else {
                return null;
            }
        }

        //create a new pipe
        Pipe result = new Pipe(getResult());
        fulfill(result, input);
        String body = input.body;

        //give it to TextPipe
//        if (body.startsWith(Keys.ACTION)){
//            body = body.replaceFirst(Keys.ACTION, "");
//        }

        if (!result.getSearchableName().contains(body)){
            return null;
        }
//        result.setPrevious(prev);
        return result;
    }

    @Override
    public void load(AbsTranslator translator, OnItemsLoadedListener listener, int total){
        listener.onItemsLoaded(this, total);
    }

    /**
     * @return the Pipe with display email, id, and searchable email
     */
    public abstract Pipe getResult();

    @Override
    public Pipe getDefaultPipe() {
        return getResult();
    }

    public String getDisplayName(){
        return getResult().getDisplayName();
    }

    public Pipe getByValue(String value){
        Pipe result = getResult();
        if (result.getExecutable().equals(value))
            return result;

        return null;
    }

    protected boolean asOutput(){
        return true;
    }

}
