package br.com.yagofx.gadobot.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PagePair {

    Integer current;
    Integer last;

    public PagePair(String... pages) {
        this(Integer.parseInt(pages[0]), Integer.parseInt(pages[1]));
    }

}