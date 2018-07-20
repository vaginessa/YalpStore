/*
 * Yalp Store
 * Copyright (C) 2018 Sergey Yeriomin <yeriomin@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.yeriomin.yalpstore;


import com.github.yeriomin.yalpstore.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewStorageIterator extends ReviewIterator {

    static private final int PAGE_SIZE = 3;

    private List<Review> list = new ArrayList<>();
    private ReviewRetrieverIterator iterator;

    private ReviewRetrieverIterator getRetrievingIterator() {
        if (null == iterator) {
            iterator = new ReviewRetrieverIterator();
            iterator.setContext(context);
            iterator.setPackageName(packageName);
        }
        return iterator;
    }

    @Override
    public boolean hasNext() {
        return list.size() > (PAGE_SIZE * page) || getRetrievingIterator().hasNext();
    }

    @Override
    public List<Review> next() {
        page++;
        if (list.size() < (PAGE_SIZE * (page + 1)) && getRetrievingIterator().hasNext()) {
            list.addAll(getRetrievingIterator().next());
        }
        return current();
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public List<Review> previous() {
        page--;
        return current();
    }

    private List<Review> current() {
        int from = PAGE_SIZE * page;
        int to = from + PAGE_SIZE;
        return (from < 0 || to > list.size()) ? new ArrayList<Review>() : list.subList(from, to);
    }
}
