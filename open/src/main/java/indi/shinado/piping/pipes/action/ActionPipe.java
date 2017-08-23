package indi.shinado.piping.pipes.action;

import java.util.TreeSet;

import indi.shinado.piping.pipes.BasePipe;
import indi.shinado.piping.pipes.entity.Instruction;
import indi.shinado.piping.pipes.entity.Pipe;
import indi.shinado.piping.pipes.search.translator.AbsTranslator;

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
            return null;
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
        listener.onItemsLoaded(getId(), total);
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

}
