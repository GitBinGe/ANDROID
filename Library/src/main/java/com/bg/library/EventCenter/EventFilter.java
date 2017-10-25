package com.bg.library.EventCenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinGe on 2016/12/6.
 */

public class EventFilter {

    private List<String> filters = new ArrayList<>();

    public EventFilter() {

    }

    public EventFilter(String filter) {
        filters.add(filter);
    }

    public EventFilter addFilter(String filter) {
        filters.add(filter);
        return this;
    }

    public boolean contains(String filter) {
        return filters.contains(filter);
    }

}
