package ru.krasview.kvlib.widget.lists;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

import ru.krasview.kvlib.adapter.LoadDataToGUITask;
import ru.krasview.kvlib.indep.Parser;
import ru.krasview.kvlib.indep.consts.TypeConsts;
import ru.krasview.kvlib.widget.List;
import ru.krasview.secret.ApiConst;

public class AllGenreShowList extends List {
    public AllGenreShowList(Context context){
        super(context, null);
    }

    protected void setType(Map<String, Object> map) {
        map.put("type", TypeConsts.GENRE_SERIES);
    }
    protected String getApiAddress() {
        return ApiConst.SHOW_GENRES;
    }

    protected String getType() {return "genre_series";}

    @SuppressWarnings("unchecked")
    @Override
    public void parseData(String doc, LoadDataToGUITask task) {
        Document mDocument;
        mDocument = Parser.XMLfromString(doc);
        if(mDocument == null) {
            return;
        }
        mDocument.normalizeDocument();
        NodeList nListChannel = mDocument.getElementsByTagName("unit");
        int numOfChannel = nListChannel.getLength();
        Map<String, Object> m;
        if(numOfChannel == 0) {
            m = new HashMap<>();
            m.put("name", "<пусто>");
            m.put("type", null);
            task.onStep(m);
            return;
        }
        for (int nodeIndex = 0; nodeIndex < numOfChannel; nodeIndex++) {
            Node locNode = nListChannel.item(nodeIndex);
            m = new HashMap<>();
            m.put("id", Parser.getValue("id", locNode));
            m.put("name", Parser.getValue("title", locNode));
            m.put("type", this.getType() );
            if(task.isCancelled()) {
                return;
            }
            task.onStep(m);
        }

    }
}
