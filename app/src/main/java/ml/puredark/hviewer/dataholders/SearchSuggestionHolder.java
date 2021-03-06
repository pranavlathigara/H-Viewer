package ml.puredark.hviewer.dataholders;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ml.puredark.hviewer.utils.SharedPreferencesUtil;


/**
 * Created by PureDark on 2016/8/12.
 */

public class SearchSuggestionHolder {
    private static List<String> searchSuggestions;
    private Context mContext;

    public SearchSuggestionHolder(Context context) {
        this.mContext = context;
        String searchSuggestionStr = (String) SharedPreferencesUtil.getData(context, "SearchSuggestion","[]");
        searchSuggestions = new Gson().fromJson(searchSuggestionStr, new TypeToken<ArrayList<String>>() {
        }.getType());
        if(searchSuggestions==null)
            searchSuggestions = new ArrayList<>();
        trimSearchSuggestion();
    }

    public void removeDuplicate(){
        searchSuggestions = new ArrayList(new HashSet(searchSuggestions));
    }

    public void saveSearchSuggestion() {
        removeDuplicate();
        SharedPreferencesUtil.saveData(mContext, "SearchSuggestion", new Gson().toJson(searchSuggestions));
    }

    public void addSearchSuggestion(String item) {
        if (item == null) return;
        if(!searchSuggestions.contains(item)) {
            searchSuggestions.add(0, item.trim());
            trimSearchSuggestion();
        }
    }

    public void deleteSearchSuggestion(String item) {
        for (int i = 0, size = searchSuggestions.size(); i < size; i++) {
            if (searchSuggestions.get(i).equals(item.trim())) {
                searchSuggestions.remove(i);
                size--;
                i--;
            }
        }
    }

    public void trimSearchSuggestion() {
        while (searchSuggestions.size() > 500)
            searchSuggestions.remove(500);
    }

    public List<String> getSearchSuggestion() {
        if(searchSuggestions==null)
            return new ArrayList<>();
        else
            return searchSuggestions;
    }

    public List<String> getSearchSuggestion(String query) {
        List<String> keywords = new ArrayList<>();
        if (searchSuggestions != null) {
            for (String keyword : searchSuggestions) {
                if (keyword.startsWith(query))
                    keywords.add(keyword);
            }
        }
        return keywords;
    }

}
