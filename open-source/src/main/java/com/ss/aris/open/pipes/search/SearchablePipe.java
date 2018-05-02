package com.ss.aris.open.pipes.search;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import com.ss.aris.open.pipes.BasePipe;
import com.ss.aris.open.pipes.entity.Instruction;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

public abstract class SearchablePipe extends BasePipe {

    private static final String TAG = "SearchablePipe";
    protected HashMap<String, TreeSet<Pipe>> resultMap = new HashMap<>();
    protected HashMap<String, TreeSet<String>> deletedSearchKeys = new HashMap<>();

    public SearchablePipe(int id) {
        super(id);
    }

    /**
     * when inputting characters, which is, length > 0, do the search and save results in the stack
     * when deleting characters, which is length < 0, pop the stack
     */
    @Override
    public void search(String input, int length, Pipe previous, SearchResultCallback callback) {
        TreeSet<Pipe> result;

        result = search(input);

        callback.onSearchResult(result, new Instruction(input));
    }

    /**
     * fulfill with key index and instruction
     */
    protected TreeSet<Pipe> fulfill(TreeSet<Pipe> list, Instruction input) {
        TreeSet<Pipe> set = new TreeSet<>();
        if (list == null) {
            return set;
        }

        //set key index for each item
        for (Pipe item : list) {
            fulfill(item, input);
            set.add(item);
        }
        return set;
    }

    protected TreeSet<Pipe> search(String input) {
        return search(new Instruction(input));
    }

    protected TreeSet<Pipe> search(Instruction value) {
        String body = value.body;
        String key = getKey(body);

        String className = getClass().getSimpleName();
        if (className.contains("Application")) {
            Log.d("PipeSearcher", "app search key: " + key);
            Log.d("PipeSearcher", "app search body: " + body);
        }

        //if the application has been uninstalled once,
        //the searching result will be removed
        //therefore can not be searched when re-installed
        if (key == null) {
            //get nothing
            return new TreeSet<>();
        }

        if (!isParameterAllowded() && !value.isParamsEmpty()) {
            //does not take parameters
            return new TreeSet<>();
        } else {
            TreeSet<Pipe> result = search(key, body);
            result = fulfill(result, value);
            return result;
        }
    }

    protected boolean isParameterAllowded() {
        return false;
    }

    private TreeSet<Pipe> search(String key, String body) {
        TreeSet<Pipe> result = new TreeSet<>();
        if (body.isEmpty()) return result;

        String className = getClass().getSimpleName();
        TreeSet<Pipe> all = resultMap.get(key);
        if (body.equals(key)) {
            if (className.contains("Application")) {
                Log.d("PipeSearcher", "return all: " + (all == null ? 0 : all.size()));
            }
            return all;
        }

        if (all == null) {
            return new TreeSet<>();
        } else {
            //search
            for (Pipe pipe : all) {
                if (className.contains("Application")) {
                    Log.d("PipeSearcher", "found: " + pipe.getDisplayName());
                }
                if (pipe.getSearchableName().contains(body)) {
                    result.add(pipe);
                }
            }
            //put in the map
            resultMap.put(body, result);
            return result;
        }

    }

    /**
     * get the key to get from map
     *
     * @return null if get nothing
     */
    protected String getKey(String body) {
        TreeSet<Pipe> result = resultMap.get(body);
        if (result == null || result.size() == 0) {
            if (body.length() > 1) {
                body = body.substring(0, body.length() - 1);
                return getKey(body);
            } else {
                return "";
            }
        } else {
            return body;
        }
    }

    private boolean reenableSearchKeys(Pipe vo) {
        TreeSet<String> keys = deletedSearchKeys.get(vo.getExecutable());
        if (keys != null) {
            for (String key : keys) {
                TreeSet<Pipe> list = resultMap.get(key);
                if (list == null) {
                    list = new TreeSet<>();
                    resultMap.put(key, list);
                }
                list.add(vo);
            }
            return true;
        } else {
            return false;
        }
    }

    private void cacheDeletedSearchKeys(String key, Pipe vo) {
        TreeSet<String> searchKeys = deletedSearchKeys.get(vo.getExecutable());
        if (searchKeys == null) {
            searchKeys = new TreeSet<>();
            deletedSearchKeys.put(vo.getExecutable(), searchKeys);
        }
        searchKeys.add(key);
    }

    protected void clearMap() {
        resultMap.clear();
    }

    protected void removeItemInMap(Pipe vo) {
        Set<String> keys = resultMap.keySet();
        for (String key : keys) {
            TreeSet<Pipe> results = resultMap.get(key);

            //check this out
            //https://www.jianshu.com/p/7b7455aad793
            boolean contains = false;//results.contains(vo);
            for (Pipe p : results) {
                if (p.getExecutable().equals(vo.getExecutable())) {
                    contains = true;
                }
            }

            if (contains) {
                cacheDeletedSearchKeys(key, vo);
                results.remove(vo);
            }
        }
        resultMap.remove(vo.getExecutable());
    }

    /**
     * ["face", "book"] = > ["f" -> "facebook", "b" -> "facebook"]
     */
    protected void putItemInMap(Pipe vo) {
        String className = getClass().getSimpleName();
        Log.d("PipeSearcher", "put item in map: " + vo.getDisplayName());

        //if this item is found in the list that has been deleted,
        //simply put it back
        boolean b = reenableSearchKeys(vo);
        if (className.contains("Application")) {
            if (b) {
                Log.d("PipeSearcher", "re enabled");
            }
        }
        vo.setBasePipe(this);
        if (b) return;

        SearchableName searchableName = vo.getSearchableName();
        if (searchableName != null) {
            String[] name = searchableName.getNames();
            for (String n : name) {
                if (className.contains("Application")) {
                    Log.d("PipeSearcher", "name: " + n);
                }

                String c = n.isEmpty() ? "" : n.charAt(0) + "";

                TreeSet<Pipe> list = resultMap.get(c);//allItemMap.get(c);
                if (list == null) {

                    list = new TreeSet<>();
                    resultMap.put(c, list);
                }

                list.add(vo);
                if (c.equals("k")) {
                    if (className.contains("Application")) {
                        Log.d("PipeSearcher", "addK: " + ", " + vo.getDisplayName());
                    }
                }
            }
        }

        //why deleted before?
        //put on full search list
        TreeSet<Pipe> list = new TreeSet<>();
        list.add(vo);
        resultMap.put(vo.getExecutable(), list);
    }

    public TreeSet<Pipe> getFrequents(){
        return new TreeSet<>();
    }

    public ArrayList<Pipe> getAll() {
        ArrayList<Pipe> all = new ArrayList<>();
        for (String key : resultMap.keySet()) {
            if (key.length() == 1) {
                Collection<Pipe> list = resultMap.get(key);
                for (Pipe item : list) {
                    if (!all.contains(item))
                        all.add(item);
                }
            }
        }
        return all;
    }

    protected void register(Pipe... pipes) {
        for (Pipe p : pipes) {
            p.setBasePipe(this);
            putItemInMap(p);
        }
    }


}
