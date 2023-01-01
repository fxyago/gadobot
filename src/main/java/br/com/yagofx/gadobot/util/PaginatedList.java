package br.com.yagofx.gadobot.util;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class PaginatedList<E> extends LinkedBlockingQueue<E> {

    public List<E> getFirstPage() {
        return this.getPage(1);
    }

    public List<E> getLastPage() {
        double lastPage = Math.ceil(this.size() / 10d);
        return this.getPage((int) lastPage);
    }

    public List<E> getPage(Integer page) {
        double pages = Math.ceil(this.size() / 10d);
        if (page > (int) pages) throw new IndexOutOfBoundsException();

        int start = (page - 1) * 10;
        int end = page * 10;

        if (end > this.size()) end = this.size();

        return this.stream().toList().subList(start, end);
    }

}