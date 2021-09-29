/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import dtos.ProductDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class Pagination {

    private List<ProductDTO> list;
    private int offset;
    private final int COUNT = 20;
    private int length = 0;

    public Pagination() {
    }

    public Pagination(List<ProductDTO> list, int offset) {
        this.list = list;
        this.offset = offset;
    }

    public List<ProductDTO> paging() {
        List<ProductDTO> product = new ArrayList<>();
        for (int i = offset * COUNT; i < length; i++) {
            product.add(list.get(i));
        }
        return product;
    }

    private void updateSize() {
        offset = offset - 1;

        if (offset == 0) {
            length = COUNT;
        }
        if (list.size() - (offset * COUNT) <= 20) {
            length = list.size();
        } else {
            length = COUNT * (offset + 1);
        }
    }

}
